package com.rcyc.batchsystem.process;

import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.elastic.Itinerary;
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
                if (!itineraries.isEmpty()) {

                }
                item.setResponse(processedList);
            }
            return item;
        };
    }

    private Itinerary getAsItinerary(com.rcyc.batchsystem.model.resco.Itinerary rescoItinerary) {
        Itinerary itineraryEs = new Itinerary();
        itineraryEs.setId(rescoItinerary.getEventId());
        itineraryEs.setFacilityId(rescoItinerary.getFacilityId());
        itineraryEs.setPortCode(rescoItinerary.getLocation());
        itineraryEs.setDepDateStr(rescoItinerary.getDepDate());
        itineraryEs.setArrDateStr(rescoItinerary.getArrDate());
        itineraryEs.setDepTimeZone(rescoItinerary.getDepTimeZone());
        itineraryEs.setArrTimeZone(rescoItinerary.getArrTimeZone());
        itineraryEs.setEnabled(rescoItinerary.getEnabled());
        itineraryEs.setComment(null);
        itineraryEs.setType(rescoItinerary.getType());
        itineraryEs.setDepPosition(rescoItinerary.getDepPosition());
        itineraryEs.setArrPosition(rescoItinerary.getArrPosition());
        if (itineraryEs.getDepDateStr() != null) {
            String depDateStr = itineraryEs.getDepDateStr();
            LocalDate depDate = LocalDate.parse(depDateStr);
            long departureTime = depDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
            itineraryEs.setDepartureTime(departureTime);

            itineraryEs.setDay(depDate.format(DateTimeFormatter.ofPattern("E", Locale.ENGLISH)));
            itineraryEs.setDate(departureTime);

        }
        if (itineraryEs.getArrDateStr() != null) {
            String arrDateStr = itineraryEs.getArrDateStr();
            LocalDate arrDate = LocalDate.parse(arrDateStr);
            long arrivalTime = arrDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
            itineraryEs.setArrivalTime(arrivalTime);

            itineraryEs.setDay(arrDate.format(DateTimeFormatter.ofPattern("E", Locale.ENGLISH)));
            itineraryEs.setDate(arrivalTime);

        }

        if (itineraryEs.getArrTimeZone() != null && itineraryEs.getDepartureTime()!=null) {
            String arrDateStr = itineraryEs.getArrDateStr(); // e.g., "2020-06-14"
            String depDateStr = itineraryEs.getDepDateStr(); // e.g., "2020-06-10"

            LocalDate arrDate = LocalDate.parse(arrDateStr);
            LocalDate depDate = LocalDate.parse(depDateStr);

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



        return itineraryEs;
    }
}