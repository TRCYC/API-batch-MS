package com.rcyc.batchsystem.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;

import com.rcyc.batchsystem.model.elastic.ExcursionVoyage;
import com.rcyc.batchsystem.model.elastic.Pricing;
import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.model.resco.Dictionary;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.resco.Item;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.Page;
import com.rcyc.batchsystem.model.resco.Rate;
import com.rcyc.batchsystem.model.resco.ResListDictionary;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.util.DateUtil;

public class ExcursionProcess {
 private Long jobId;
    private AuditService auditService;

    public ExcursionProcess(Long jobId,AuditService auditService){
        this.jobId = jobId;
        this.auditService = auditService;
    }

    public ItemProcessor<DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>, DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>> excursionProcessForWrite() {
        return processItem -> {
            List<ExcursionVoyage> excursionVoyages = new ArrayList<>();
            if (processItem != null && processItem.getReader() != null) {
                auditService.logAudit(jobId, "feed_type", "Processing");
                Map<String,Object> parentMap = (Map<String, Object>) processItem.getReader();
                List<EventDetail> voyageList = (List<EventDetail> )parentMap.get("VOYAGES");
                 Map<String, List<Item>> excursionMap = (Map<String, List<Item>>)parentMap.get("EXCURSION");
                 Map<String, List<Item>> excursionNoteMap = (Map<String, List<Item>>)parentMap.get("EXCURSION_NOTE");
                ResListLocation oTypePorts = (ResListLocation) parentMap.get("PORT_TYPE_O");
                ResListLocation pTypePorts = (ResListLocation) parentMap.get("PORT_TYPE_P");

                for(EventDetail event  : voyageList){
                    List<Item> excursions = excursionMap.get(String.valueOf(event.getEventId()));
                    if(excursions!=null && excursions.size()>0){
                        for(Item item : excursions){
                             ExcursionVoyage excursionVoyage = new ExcursionVoyage();
                              excursionVoyage.setVoyageId(String.valueOf(event.getEventId()));
                              excursionVoyage.setVoyageCode(event.getCode());
                              excursionVoyage.setVoyageStartDate(Long.valueOf(DateUtil.rubyToJavaForEpochMilli(event.getBegDate())));
                             excursionVoyage.setVoyageEndDate(Long.valueOf(DateUtil.rubyToJavaForEpochMilli(event.getEndDate())));
                            excursionVoyage.setRescoItemId(item.getItemId());
                            excursionVoyage.setTourCd(item.getCode());
                            excursionVoyage.setTourName(item.getName());
                            excursionVoyage.setTourStartDateStr(item.getBegDate());
                            excursionVoyage.setTourEndDateStr(item.getEndDate());
                            excursionVoyage.setExternalId(item.getExternalId());
                            excursionVoyage.setCode(item.getCode());
                            excursionVoyage.setAvailItems(item.getAvailItems());
                            excursionVoyage.setSortOrder(item.getSort());
                            excursionVoyage.setItemCatCD(item.getItemTypeCode());
                            excursionVoyage.setItemCatName(item.getItemTypeName());
                            excursionVoyage.setItemType(item.getGroupType());
                            excursionVoyage.setStoreRoomCd(item.getGroupCode());
                            excursionVoyage.setDurationDays(item.getDuration());
                            excursionVoyage.setDurationHrs(item.getDurationHours());
                            excursionVoyage.setTourCatCd(item.getFlex01());
                            excursionVoyage.setTourCategory(item.getFlex02());
                            excursionVoyage.setActivityLvl(item.getFlex03());
                            excursionVoyage.setNonPrepaidUSDPrice(item.getFlex04());
                            excursionVoyage.setDetailedDesc(item.getInfoText());
                            excursionVoyage.setDeliveryType(item.getDeliveryType());
                            excursionVoyage.setSummaryDesc(item.getComments());
                            excursionVoyage.setPortName(item.getPortName());
                            excursionVoyage.setPortRegion(item.getPortRegion());
                            excursionVoyage.setCountryCode(item.getCountry());
                            if(item.getBegDate()!=null)
                              excursionVoyage.setTourStartDate(Long.valueOf(DateUtil.rubyToJavaForEpochMilli(item.getBegDate())));
                            if(item.getEndDate()!=null)
                              excursionVoyage.setTourEndDate(Long.valueOf(DateUtil.rubyToJavaForEpochMilli(item.getEndDate())));
                            List<Pricing> pricing = new ArrayList<>();
                            List<Item> noteItems = excursionNoteMap.get(event.getEventId()+"#"+item.getExternalId()+"#"+item.getCode());
                            if(noteItems!=null && noteItems.size()>0){
                                Page page = noteItems.get(0).getPageList().get(0);
                                excursionVoyage.setSpecialNote(page.getText());
                            }

                            if(item.getFlexItemList()!=null && item.getFlexItemList().size()>0){
                                if(item.getBegLocationList()!=null && item.getBegLocationList().size()>0 && item.getBegLocationList().get(0).getBegLocation()!=null){
                                    excursionVoyage.setPortCode(item.getBegLocationList().get(0).getBegLocation());
                                }
                            }
                             Location lPort = null;
                            if(oTypePorts !=null && oTypePorts.getLocationList()!=null && oTypePorts.getLocationList().getLocations()!=null){
                                 lPort =  oTypePorts.getLocationList().getLocations().stream().filter(port->port.getCode().equalsIgnoreCase(excursionVoyage.getPortCode())).findFirst().orElse(null);
                                
                            }
                            if(lPort==null){
                                lPort =  pTypePorts.getLocationList().getLocations().stream().filter(port->port.getCode().equalsIgnoreCase(excursionVoyage.getPortCode())).findFirst().orElse(null);
                            }
                            if(lPort!=null)
                                excursionVoyage.setPortName(lPort.getName());

                            for(Rate rate: item.getRateList()){
                                Pricing obj = new Pricing();
                                obj.setRateId(Long.valueOf(rate.getRateId()));
                                obj.setPriceCurrency(rate.getPriceCurrency());
                                obj.setPrice(rate.getPrice());
                                pricing.add(obj);
                            }
                            excursionVoyage.setPricing(pricing);
                            excursionVoyage.setId(item.getItemId());

                            excursionVoyages.add(excursionVoyage);
                        }
                    }

                }
                 

                processItem.setResponse(excursionVoyages); // bind back
            }
            return processItem;
        };
    }
 
}
