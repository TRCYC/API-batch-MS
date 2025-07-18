package com.rcyc.batchsystem.process;

import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemProcessor;

import com.rcyc.batchsystem.entity.RegionEntity;
import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.Itinerary;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.ResListDictionary;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.model.resco.ResListItinerary;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.util.DateUtil;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class VoyageProcessor {

    private ResListLocation pList;
    private ResListLocation oList;
    private ResListItinerary ilmaItinerary;
    private ResListItinerary evrimaItinerary;
    private ResListItinerary luminaraItinerary;

    public ItemProcessor<DefaultPayLoad<Voyage, Object, Voyage>, DefaultPayLoad<Voyage, Object, Voyage>> voyageProcessForWrite() {
        return item -> {
            System.out.println(item);
            if (item != null && item.getReader() != null) {
                List<Voyage> allVoyages = (List<Voyage>) item.getReader();

                Map<String, Object> mapObject = (Map<String, Object>) item.getReader();
                ResListDictionary listDictionary = (ResListDictionary) mapObject.get("REGIONS");
                pList = (ResListLocation) mapObject.get("PORT_P");
                oList = (ResListLocation) mapObject.get("PORT_O");
                ilmaItinerary = (ResListItinerary) mapObject.get("ITINERARY_ILMA");
                evrimaItinerary = (ResListItinerary) mapObject.get("ITINERARY_EVRIMA");
                luminaraItinerary = (ResListItinerary) mapObject.get("ITINERARY_LUMINARA");
                ResListEvent resListEvent = (ResListEvent) mapObject.get("VOYAGE");
                List<RegionEntity> regionArrayList = (List<RegionEntity>) mapObject.get("REGION_ENTITY");

                extractVoyages(resListEvent, regionArrayList);

                System.out.println("From Reader VoyageSize " + allVoyages.size());
                DefaultPayLoad<Voyage, Object, Voyage> voyagePayload = new DefaultPayLoad<>();
                voyagePayload.setResponse(allVoyages);
                System.out.println("Voyage Size " + allVoyages.size());
                return voyagePayload;
            }
            return null;
        };
    }

    private void extractVoyages(ResListEvent resListEvent, List<RegionEntity> regionArrayList) {
        List<Voyage> voyageList = new ArrayList<>();

        Map<String, RegionEntity> regionMap = regionArrayList.stream()
                .collect(Collectors.toMap(
                        RegionEntity::getRegionCode,
                        r -> r));

        if (resListEvent.getResult().getStatus().equalsIgnoreCase("A")) {
            resListEvent.getEventList().forEach(voyage -> {
                // date checking
                Voyage voyageEs = new Voyage();

                String voyageTempBegDate = voyage.getBegDate();
                String voyageTemEndDate = voyage.getEndDate();
                String voyageEndDOW = "";
                String voyageEndDate = "";
                voyageEs.setId(voyage.getEventId());
                voyageEs.setEventId(voyage.getEventId());
                voyageEs.setVoyageId(voyage.getCode());
                voyageEs.setVoyageName(voyage.getName());
                voyageEs.setYachtId(voyage.getFacilityId());
                voyageEs.setYachtName(voyage.getName());
                voyageEs.setVoyageEmbarkPortCode(voyage.getBegLocation());
                voyageEs.setVoyageDisembarkPortCode(voyage.getEndLocation());
                voyageEs.setNights(voyage.getDuration());
                voyageEs.setVoyageType(voyage.getTypeCode());
                if (StringUtils.isNotEmpty(voyageTemEndDate)) {
                    voyageEndDOW = DateUtil.rubyToJavaForDay(voyageEndDate);
                    voyageEndDate = DateUtil.rubyToJavaForEpochMilli(voyageEndDate);
                }
                voyageEs.setVoyageRegion(
                        regionMap.get(voyage.getRegionList().get(0).getCode()) != null
                                ? regionMap.get(voyage.getRegionList().get(0).getCode()).getWebRegionCode()
                                : null);
                voyageEs.setVoyageRegionExpansion(
                        regionMap.get(voyage.getRegionList().get(0).getCode()) != null
                                ? regionMap.get(voyage.getRegionList().get(0).getCode()).getRegionName()
                                : null);
                voyageEs.setVoyageUrlPath(
                        regionMap.get(voyage.getRegionList().get(0).getCode()) != null
                                ? regionMap.get(voyage.getRegionList().get(0).getCode()).getRegionUrl()
                                : null);

                Optional<Location> empLocation = pList.getLocationList().getLocations().stream()
                        .filter(e -> e.getCode().equalsIgnoreCase(voyageEs.getVoyageEmbarkPortCode()))
                        .findFirst();
                if (empLocation.isEmpty())
                    empLocation = oList.getLocationList().getLocations().stream()
                            .filter(e -> e.getCode().equalsIgnoreCase(voyageEs.getVoyageEmbarkPortCode()))
                            .findFirst();

                voyageEs.setVoyageEmbarkPort(empLocation.get().getName());
                voyageEs.setVoyageEmbarkRegion(empLocation.get().getRegion());

                Optional<Location> disLocation = pList.getLocationList().getLocations().stream()
                        .filter(e -> e.getCode().equalsIgnoreCase(voyageEs.getVoyageDisembarkPortCode()))
                        .findFirst();

                voyageEs.setVoyageDisembarkPort(disLocation.get().getName());
                voyageEs.setVoyageDisembarkRegion(disLocation.get().getRegion());

                // mapping is pending voyage empbarkation and disembrkation

                List<Itinerary> itineraryLocations = evrimaItinerary.getItineraryList().stream()
                        .filter(e -> Objects.equals(e.getEventId().intValue(), voyage.getEventId()))
                        .collect(Collectors.toList());

                for (Itinerary itinerary : itineraryLocations) {
                    String portcode = "";
                    String portname = "";
                    String portDate = "";

                    String locationType = itinerary.getType();
                    String locationValue = itinerary.getLocation();

                    if (locationType != null) {
                        if (locationValue != null || "SS".equals(locationType)) {
                            if ("SS".equals(locationType)) {
                                portcode = "XS001";
                            } else {
                                portcode = locationValue;
                            }

                            Location portData = null;
                            if (pList != null && pList.getLocationList() != null
                                    && pList.getLocationList().getLocations() != null) {
                                for (Location loc : pList.getLocationList().getLocations()) {
                                    if (portcode.equals(loc.getCode())) {
                                        portData = loc;
                                        break;
                                    }
                                }
                            }
                            if (portData == null && oList != null && oList.getLocationList() != null
                                    && oList.getLocationList().getLocations() != null) {
                                for (Location loc : oList.getLocationList().getLocations()) {
                                    if (portcode.equals(loc.getCode())) {
                                        portData = loc;
                                        break;
                                    }
                                }
                            }

                            if (portData != null && portData.getName() != null) {
                                portname = portData.getName();
                            } else {
                                portname = "";
                            }

                            if ((locationType.equals("SS") || locationType.equals("DD") || locationType.equals("PD"))
                                    && itinerary.getDepDate() != null) {
                                portDate = getQuarter(itinerary.getDepDate());
                            } else if (itinerary.getArrDate() != null) {
                                portDate = getQuarter(itinerary.getArrDate());
                            }
                        } else {
                            portcode = "";
                            portname = "";
                            portDate = "";
                        }
                    }
                     
                }

                voyageList.add(voyageEs);
            });
        }
        throw new UnsupportedOperationException("Unimplemented method 'extractVoyages'");
    }

    private String getQuarter(String dateStr) {
        if (dateStr == null)
            return "";
        LocalDate date = LocalDate.parse(dateStr); // Adjust format if needed
        int month = date.getMonthValue();
        int quarter = ((month - 1) / 3) + 1;
        return String.valueOf(quarter);
    }
}