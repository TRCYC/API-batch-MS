package com.rcyc.batchsystem.process;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.elastic.Itinerary;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.ResListItenarary;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.temporal.ChronoUnit;

// NOTE: ResListItenarary must have a method: public List<Itinerary> getItineraries()
// for this processor to work correctly.
@Component
public class ItineraryProcess {
    public ItemProcessor<DefaultPayLoad<Itinerary, Object, Itinerary>, DefaultPayLoad<Itinerary, Object, Itinerary>> itineraryProcessForWrite() {
        return item -> {
            if (item != null && item.getReader() != null) {
                Map<String, Object> map = (Map<String, Object>) item.getReader();
                List<com.rcyc.batchsystem.model.resco.Itinerary> itineraries = (List<com.rcyc.batchsystem.model.resco.Itinerary>) map
                        .get("ITINERARIES");
                        List<Location> ptypList = (List<Location>) map.get("P_TYPE_PORTS");
                        List<Location> otypList = (List<Location>) map.get("O_TYPE_PORTS");
                        List<Itinerary> processedList = null;
                 if (!itineraries.isEmpty()) {
                    processedList = itineraries.stream()
                        .map(rescoItinerary -> getAsItinerary(rescoItinerary, ptypList, otypList))
                        .collect(Collectors.toList());
                }
                item.setResponse(processedList);
            }
            return item;
        };
    }

    private Itinerary getAsItinerary(com.rcyc.batchsystem.model.resco.Itinerary rescoItinerary,List<Location> ptypList,List<Location> otypList) {
        Itinerary itineraryEs = new Itinerary();
        itineraryEs.setId(rescoItinerary.getEventId() != null ? rescoItinerary.getEventId().intValue() : 0);
        itineraryEs.setFacilityId(rescoItinerary.getFacilityId() != null ? rescoItinerary.getFacilityId().toString() : "");
        itineraryEs.setPortCode(rescoItinerary.getLocation());
        itineraryEs.setDepDateStr(rescoItinerary.getDepDate());
        itineraryEs.setArrDateStr(rescoItinerary.getArrDate());
        itineraryEs.setDepTimeZone(rescoItinerary.getDepTimeZone() != null ? rescoItinerary.getDepTimeZone().intValue() : 0);
        itineraryEs.setArrTimeZone(rescoItinerary.getArrTimeZone() != null ? rescoItinerary.getArrTimeZone().intValue() : 0);
        itineraryEs.setEnabled(String.valueOf(rescoItinerary.getEnabled()));
        itineraryEs.setComment(null);
        itineraryEs.setType(rescoItinerary.getType());
        itineraryEs.setDepPosition(rescoItinerary.getDepPosition());
        itineraryEs.setArrPosition(rescoItinerary.getArrPosition());
        if (itineraryEs.getDepDateStr() != null) {
            String depDateStr = itineraryEs.getDepDateStr();
            // Extract date part only (before 'T' if present)
            String dateOnly = depDateStr.split("T")[0];
            LocalDate depDate = LocalDate.parse(dateOnly);
            long departureTime = depDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
            itineraryEs.setDepartureTime(departureTime);

            itineraryEs.setDay(depDate.format(DateTimeFormatter.ofPattern("E", Locale.ENGLISH)));
            itineraryEs.setDate(departureTime);

        }
        if (itineraryEs.getArrDateStr() != null) {
            String arrDateStr = itineraryEs.getArrDateStr();
            // Extract date part only (before 'T' if present)
            String dateOnly = arrDateStr.split("T")[0];
            LocalDate arrDate = LocalDate.parse(dateOnly);
            long arrivalTime = arrDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
            itineraryEs.setArrivalTime(arrivalTime);

            itineraryEs.setDay(arrDate.format(DateTimeFormatter.ofPattern("E", Locale.ENGLISH)));
            itineraryEs.setDate(arrivalTime);

        }

        if (itineraryEs.getArrTimeZone() !=0 && itineraryEs.getDepartureTime()!=0) {
            String arrDateStr = itineraryEs.getArrDateStr(); // e.g., "2020-06-14"
            String depDateStr = itineraryEs.getDepDateStr(); // e.g., "2020-06-10"

            // Extract date part only (before 'T' if present)
            String arrDateOnly = arrDateStr.split("T")[0];
            String depDateOnly = depDateStr.split("T")[0];

            LocalDate arrDate = LocalDate.parse(arrDateOnly);
            LocalDate depDate = LocalDate.parse(depDateOnly);

            long night = ChronoUnit.DAYS.between(depDate, arrDate); // (arrDate - depDate)
            itineraryEs.setNight((int) night);

        }

        if(itineraryEs.getType().equals("SS")){
            itineraryEs.setPortCode("XS001");
            itineraryEs.setPortName("Sailing Time");
            itineraryEs.setDayType("Sea Day");
        }else if(itineraryEs.getType().equals("AA")){
            itineraryEs.setDayType("Arrival");
        }else if(itineraryEs.getType().equals("DD")){
            itineraryEs.setDayType("Departure");
        }else if(itineraryEs.getType().equals("XX")){
            itineraryEs.setDayType("none");
        }else
             itineraryEs.setDayType("Port Day");

        if(!itineraryEs.getType().equals("SS")){
            Location portLocation = Location.findFirstByCode(ptypList, itineraryEs.getPortCode());
            if (portLocation != null && portLocation.getName() != null) {
                String portName = portLocation.getName();
                itineraryEs.setPortName(portName);
            }else{
                portLocation = Location.findFirstByCode(otypList, itineraryEs.getPortCode());
                if (portLocation != null && portLocation.getName() != null) {
                    String portName = portLocation.getName();
                    itineraryEs.setPortName(portName);
                }
            }
            
        }

        return itineraryEs;
    }
}