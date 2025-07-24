package com.rcyc.batchsystem.reader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.rcyc.batchsystem.entity.FeedDateRangeEntity;
import com.rcyc.batchsystem.model.elastic.ExcursionVoyage;
import com.rcyc.batchsystem.model.elastic.Hotel;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.resco.Item;
import com.rcyc.batchsystem.model.resco.ResListDictionary;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.model.resco.ResListItem;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.repository.FeedDateRangeRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;

public class ExcursionReader implements ItemReader<DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>> {
    private static final Logger logger = LoggerFactory.getLogger(ExcursionReader.class);

    private final RescoClient rescoClient;
    private final Long jobId;
    private boolean alreadyRead = false;
    private AuditService auditService;
    private LocalDateTime today = LocalDateTime.now();
    private ScheduledJobService scheduledJobService;
    @Autowired
    private FeedDateRangeRepository feedDateRangeRepository;

    public ExcursionReader(RescoClient rescoClient, Long jobId, AuditService auditService,
            ScheduledJobService scheduledJobService) {
        this.rescoClient = rescoClient;
        this.jobId = jobId;
        this.auditService = auditService;
        this.scheduledJobService = scheduledJobService;
    }

    @Override
    public DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage> read() {
        DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage> excursionPayLoad = new DefaultPayLoad<>();
        try {
            boolean flag = scheduledJobService.isJobAvailableForExecution(jobId, auditService);
            if (!flag) {
                logger.info("Condition failed, so reader return null, trying to exit from job");
                auditService.logAudit(jobId, null, "Excursion voyage reader failed");
                return null;
            }
            excursionPayLoad.setReader(getExcursionFromResco());
            alreadyRead = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excursionPayLoad;
    }

    private Map<String,Object>  getExcursionFromResco() { 
         Map<String,Object> parentMap = new HashMap<>();
        try {
            logger.info("Excursion Rreader starting from Resco");
             List<FeedDateRangeEntity> dateRanges = feedDateRangeRepository.findByType("EXV");
            auditService.logAudit(jobId, "feed_type", today, today, today, "Resco call initiated");
            ResListEvent voyagesEvent = rescoClient.getAllVoyages(0);
            logger.info("Voyages>>"+voyagesEvent.getEventList().size());
             logger.info(" Port started O type");
            ResListLocation oTypePorts = rescoClient.getAllPorts("O");
              logger.info(" Port started P type");
            ResListLocation pTypePorts = rescoClient.getAllPorts("P");
              logger.info(" End Resco response");

            Map<String, List<Item>> excursionMap = new HashMap<>();
            Map<String, List<Item>> excursionNoteMap = new HashMap<>(); 
            List<EventDetail> voyageList = new ArrayList<>();

            if (voyagesEvent != null && voyagesEvent.getEventList()!=null &&voyagesEvent.getEventList().size()>0) {
                FeedDateRangeEntity dateRangeEntity = dateRanges.get(0);
                int i= 0;
                voyagesEvent.getEventList().parallelStream()
                .filter(eventDetail -> dateRangeEntity.isBegDateOnOrAfterStartAt(eventDetail.getBegDate()))
                .forEach(eventDetail -> {
                    voyageList.add(eventDetail); // thread-safe list

                    try {
                        ResListItem resListItem = rescoClient.getExcursionByVoyage(String.valueOf(eventDetail.getEventId()));
                        if (resListItem != null && "A".equalsIgnoreCase(resListItem.getResult().getStatus())) {
                            List<Item> items = resListItem.getItemList().getItemList();
                            excursionMap.put(String.valueOf(eventDetail.getEventId()), items);
                            logger.info("Excursion list for eventId {}: {}", eventDetail.getEventId(), items.size());

                            items.parallelStream().forEach(item -> {
                                if (item.getExternalId() != null) {
                                    logger.info("Calling for note {}#{}#{}", eventDetail.getEventId(), item.getExternalId(), item.getCode());
                                    ResListItem resNoteListItem = rescoClient.getNoteByEventIdExternalIdAndItemCode(
                                            String.valueOf(eventDetail.getEventId()), item.getExternalId(), item.getCode());

                                    if (resNoteListItem != null && "A".equalsIgnoreCase(resNoteListItem.getResult().getStatus())) {
                                        String key = eventDetail.getEventId() + "#" + item.getExternalId() + "#" + item.getCode();
                                        excursionNoteMap.put(key, resNoteListItem.getItemList().getItemList());
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        logger.error("Error processing eventId {}", eventDetail.getEventId(), e);
                    }
                });
                System.out.println(voyageList.size());
                Thread.sleep(2000);
                parentMap.put("VOYAGES",voyageList);
                parentMap.put("EXCURSION", excursionMap);
                parentMap.put("EXCURSION_NOTE", excursionNoteMap);
                parentMap.put("PORT_TYPE_O", oTypePorts);
                parentMap.put("PORT_TYPE_P", pTypePorts);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return parentMap;
    }

}
