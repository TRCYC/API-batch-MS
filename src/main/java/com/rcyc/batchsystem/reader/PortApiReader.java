package com.rcyc.batchsystem.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Port;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;

 
public class PortApiReader implements ItemReader<DefaultPayLoad<Port, Object, Port>> {
    private static final Logger logger = LoggerFactory.getLogger(PortApiReader.class);

    private final RescoClient rescoClient;
    private final Long jobId;
    private boolean alreadyRead = false;
    private final ScheduledJobService scheduledJobService;
    private final AuditService auditService;

    public PortApiReader(RescoClient rescoClient,Long jobId, ScheduledJobService scheduledJobService, AuditService auditService){
        this.rescoClient = rescoClient;
        this.jobId = jobId;
        this.scheduledJobService= scheduledJobService;
        this.auditService =auditService;
    }

    @Override
    public DefaultPayLoad<Port, Object, Port> read() {
        DefaultPayLoad<Port, Object, Port> portPayLoad = new DefaultPayLoad<>();
        try {
            boolean flag = scheduledJobService.isJobAvailableForExecution(jobId, auditService);
            if (!flag){
                logger.info("Condition failed, so reader return null, trying to exit from job");
                return null;
            }
            portPayLoad.setReader(getPortFromResco());
            alreadyRead = true;
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return portPayLoad;
    }

    private ResListLocation getPortFromResco() {
        ResListLocation listLocation = new ResListLocation();
        try {
            ResListLocation pList = rescoClient.getAllPorts("P");
            ResListLocation oList = rescoClient.getAllPorts("O");
            logger.info(" pList is null {}",pList==null);
            logger.info(" oList is null {}",oList==null);
            System.out.println( pList.getLocationList()!=null );
            System.out.println( pList.getLocationList().getLocations()!=null );

            if(pList!=null && pList.getLocationList()!=null&&pList.getLocationList().getLocations()!=null)
                 listLocation.getLocationList().getLocations().addAll(pList.getLocationList().getLocations());
            listLocation.getLocationList().getLocations().addAll(oList.getLocationList().getLocations());
            
            System.out.println("From Reader >> "+listLocation.getLocationList().getLocations().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLocation;
    }

}
