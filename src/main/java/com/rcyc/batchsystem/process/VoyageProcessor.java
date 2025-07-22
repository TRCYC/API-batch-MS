package com.rcyc.batchsystem.process;

import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemProcessor;

import com.rcyc.batchsystem.entity.RegionEntity;
import com.rcyc.batchsystem.model.elastic.PortData;
import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.Category;
import com.rcyc.batchsystem.model.resco.Itinerary;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.Rate;
import com.rcyc.batchsystem.model.resco.ResListCategory;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.HashMap;

@Component
public class VoyageProcessor {

    private ResListLocation pList;
    private ResListLocation oList;
    private ResListItinerary ilmaItinerary;
    private ResListItinerary evrimaItinerary;
    private ResListItinerary luminaraItinerary;
    private Map<String, List<ResListCategory>> allSuites;

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
                allSuites = (Map<String, List<ResListCategory>>) mapObject.get("SUITES");

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

                List<PortData> portData = new ArrayList<>();
                Set<String> portCodes = new HashSet<>();
                Set<String> ports = new HashSet<>();
                int sequenceNo = 1;

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

                            Location portDataObj = null;
                            if (pList != null && pList.getLocationList() != null
                                    && pList.getLocationList().getLocations() != null) {
                                for (Location loc : pList.getLocationList().getLocations()) {
                                    if (portcode.equals(loc.getCode())) {
                                        portDataObj = loc;
                                        break;
                                    }
                                }
                            }
                            if (portDataObj == null && oList != null && oList.getLocationList() != null
                                    && oList.getLocationList().getLocations() != null) {
                                for (Location loc : oList.getLocationList().getLocations()) {
                                    if (portcode.equals(loc.getCode())) {
                                        portDataObj = loc;
                                        break;
                                    }
                                }
                            }

                            if (portDataObj != null && portDataObj.getName() != null) {
                                portname = portDataObj.getName();
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

                    PortData field = new PortData();
                    field.setPortCode(portcode);
                    field.setPortName(portname);
                    field.setSequenceNo(sequenceNo);
                    field.setPortDate(Long.valueOf(DateUtil.rubyToJavaForEpochMilli(portDate)));

                    // Add to collections
                    sequenceNo++;
                    portCodes.add(portcode);
                    portData.add(field);
                    ports.add(portname);
                }

                List<Itinerary> ilmaItineraryLocations = ilmaItinerary.getItineraryList().stream()
                        .filter(it -> it.getEventId() != null && it.getEventId().equals(voyage.getEventId()))
                        .collect(Collectors.toList());

                for (Itinerary location : ilmaItineraryLocations) {
                    String portcode = "";
                    String portname = "";
                    String portDate = "";

                    String locationType = location.getType();
                    String locationValue = location.getLocation();

                    if (locationType != null) {
                        if (locationValue != null || "SS".equals(locationType)) {
                            if ("SS".equals(locationType)) {
                                portcode = "XS001";
                            } else {
                                portcode = locationValue;
                            }

                            Location portDataObj = null;
                            if (pList != null && pList.getLocationList() != null
                                    && pList.getLocationList().getLocations() != null) {
                                for (Location loc : pList.getLocationList().getLocations()) {
                                    if (portcode.equals(loc.getCode())) {
                                        portDataObj = loc;
                                        break;
                                    }
                                }
                            }
                            if (portDataObj == null && oList != null && oList.getLocationList() != null
                                    && oList.getLocationList().getLocations() != null) {
                                for (Location loc : oList.getLocationList().getLocations()) {
                                    if (portcode.equals(loc.getCode())) {
                                        portDataObj = loc;
                                        break;
                                    }
                                }
                            }

                            if (portDataObj != null && portDataObj.getName() != null) {
                                portname = portDataObj.getName();
                            } else {
                                portname = "";
                            }

                            // Set portDate
                            if ((locationType.equals("SS") || locationType.equals("DD") || locationType.equals("PD"))
                                    && location.getDepDate() != null) {
                                portDate = getQuarter(location.getDepDate());
                            } else if (location.getArrDate() != null) {
                                portDate = getQuarter(location.getArrDate());
                            }
                        } else {
                            portcode = "";
                            portname = "";
                            portDate = "";
                        }
                    }

                    PortData field = new PortData();
                         field.setPortCode(portcode);
                         field.setPortName(portname);
                         field.setSequenceNo(sequenceNo);
                         field.setPortDate(Long.valueOf(DateUtil.rubyToJavaForEpochMilli(portDate)));

                    sequenceNo++;
                    portCodes.add(portcode);
                    portData.add(field);
                    ports.add(portname);
                }

                    List<Itinerary> luminaraItineraryLocations = luminaraItinerary.getItineraryList().stream()
                            .filter(it -> it.getEventId() != null && it.getEventId().equals(voyage.getEventId()))
                            .collect(Collectors.toList());

                    for (Itinerary location : luminaraItineraryLocations) {
                        String portcode = "";
                        String portname = "";
                        String portDate = "";

                        String locationType = location.getType();
                        String locationValue = location.getLocation();

                        if (locationType != null) {
                            if (locationValue != null || "SS".equals(locationType)) {
                                if ("SS".equals(locationType)) {
                                    portcode = "XS001";
                                } else {
                                    portcode = locationValue;
                                }

                                Location portDataObj = null;
                                if (pList != null && pList.getLocationList() != null
                                        && pList.getLocationList().getLocations() != null) {
                                    for (Location loc : pList.getLocationList().getLocations()) {
                                        if (portcode.equals(loc.getCode())) {
                                            portDataObj = loc;
                                            break;
                                        }
                                    }
                                }
                                if (portDataObj == null && oList != null && oList.getLocationList() != null
                                        && oList.getLocationList().getLocations() != null) {
                                    for (Location loc : oList.getLocationList().getLocations()) {
                                        if (portcode.equals(loc.getCode())) {
                                            portDataObj = loc;
                                            break;
                                        }
                                    }
                                }

                                if (portDataObj != null && portDataObj.getName() != null) {
                                    portname = portDataObj.getName();
                                } else {
                                    portname = "";
                                }

                                // Set portDate
                                if ((locationType.equals("SS") || locationType.equals("DD")
                                        || locationType.equals("PD"))
                                        && location.getDepDate() != null) {
                                    portDate = getQuarter(location.getDepDate());
                                } else if (location.getArrDate() != null) {
                                    portDate = getQuarter(location.getArrDate());
                                }
                            } else {
                                portcode = "";
                                portname = "";
                                portDate = "";
                            }
                        }

                        // Map<String, Object> field = new HashMap<>();
                        // field.put("portCode", portcode);
                        // field.put("portName", portname);
                        // field.put("sequenceNo", sequenceNo);
                        // field.put("portDate", portDate);

                         PortData field = new PortData();
                         field.setPortCode(portcode);
                         field.setPortName(portname);
                         field.setSequenceNo(sequenceNo);
                         field.setPortDate(Long.valueOf(DateUtil.rubyToJavaForEpochMilli(portDate)));

                        sequenceNo++;
                        portCodes.add(portcode);
                        portData.add(field);
                        ports.add(portname);
                    }

                   
              

                 // Set these on your event/result object as needed
                    // event.setPortData(portData);
                    // event.setPortCodes(new ArrayList<>(portCodes));
                    // event.setPorts(new ArrayList<>(ports));
                   

                List<ResListCategory> usdSuites = allSuites.get("USD");
                List<ResListCategory> eurSuites = allSuites.get("EUR");
                List<ResListCategory> gbpSuites = allSuites.get("GBP");
                List<ResListCategory> audSuites = allSuites.get("AUD");

                int totalAvailUnits = 0;

                // USD
                int usdAmount = Integer.parseInt(getFirstAmount(usdSuites, false));
                int usdBaseAmount = Integer.parseInt(getFirstAmount(usdSuites, true));
                int usd_portRate = (usdAmount - usdBaseAmount) / 2;
                int usm_portRate = usd_portRate;

                // EUR
                int eurAmount = Integer.parseInt(getFirstAmount(eurSuites, false));
                int eurBaseAmount = Integer.parseInt(getFirstAmount(eurSuites, true));
                int eur_portRate = (eurAmount - eurBaseAmount) / 2;

                // GBP
                int gbpAmount = Integer.parseInt(getFirstAmount(gbpSuites, false));
                int gbpBaseAmount = Integer.parseInt(getFirstAmount(gbpSuites, true));
                int gbp_portRate = (gbpAmount - gbpBaseAmount) / 2;

                // AUD
                int audAmount = Integer.parseInt(getFirstAmount(audSuites, false));
                int audBaseAmount = Integer.parseInt(getFirstAmount(audSuites, true));
                int aud_portRate = (audAmount - audBaseAmount) / 2;

                // USD starting price and suite availability
                int usd_min = Integer.MAX_VALUE;
                int usm_min = Integer.MAX_VALUE;
                if (usdSuites != null && !usdSuites.isEmpty()) {
                    List<Category> categories = usdSuites.get(0).getCategoryList();
                    for (Category tempCategory : categories) {
                        // USD min (BaseAmount)
                        if (tempCategory.getRateList() != null) {
                            for (Rate rate : tempCategory.getRateList()) {
                                if (rate.getBaseAmount() != null) {
                                    int value = Integer.parseInt(rate.getBaseAmount());
                                    usd_min = Math.min(usd_min, value);
                                }
                                if (rate.getAmount() != null) {
                                    int value = Integer.parseInt(rate.getAmount());
                                    usm_min = Math.min(usm_min, value);
                                }
                            }
                        }
                        // Suite availability
                        if (tempCategory.getAvailUnits() > 0) {
                            totalAvailUnits += tempCategory.getAvailUnits();
                        }
                    }
                }

                if (usd_min == Integer.MAX_VALUE)
                    usd_min = 0;
                if (usm_min == Integer.MAX_VALUE)
                    usm_min = 0;

                // EUR starting price
                int eur_min = Integer.MAX_VALUE;
                if (eurSuites != null && !eurSuites.isEmpty()) {
                    List<Category> categories = eurSuites.get(0).getCategoryList();
                    for (Category tempCategory : categories) {
                        if (tempCategory.getRateList() != null) {
                            for (Rate rate : tempCategory.getRateList()) {
                                if (rate.getAmount() != null) {
                                    int value = Integer.parseInt(rate.getAmount());
                                    eur_min = Math.min(eur_min, value);
                                }
                            }
                        }
                    }
                }
                if (eur_min == Integer.MAX_VALUE)
                    eur_min = 0;

                // GBP starting price
                int gbp_min = Integer.MAX_VALUE;
                if (gbpSuites != null && !gbpSuites.isEmpty()) {
                    List<Category> categories = gbpSuites.get(0).getCategoryList();
                    for (Category tempCategory : categories) {
                        if (tempCategory.getRateList() != null) {
                            for (Rate rate : tempCategory.getRateList()) {
                                if (rate.getAmount() != null) {
                                    int value = Integer.parseInt(rate.getAmount());
                                    gbp_min = Math.min(gbp_min, value);
                                }
                            }
                        }
                    }
                }
                if (gbp_min == Integer.MAX_VALUE)
                    gbp_min = 0;

                // AUD starting price
                int aud_min = Integer.MAX_VALUE;
                if (audSuites != null && !audSuites.isEmpty()) {
                    List<Category> categories = audSuites.get(0).getCategoryList();
                    for (Category tempCategory : categories) {
                        if (tempCategory.getRateList() != null) {
                            for (Rate rate : tempCategory.getRateList()) {
                                if (rate.getAmount() != null) {
                                    int value = Integer.parseInt(rate.getAmount());
                                    aud_min = Math.min(aud_min, value);
                                }
                            }
                        }
                    }
                }
                if (aud_min == Integer.MAX_VALUE)
                    aud_min = 0;
                Map<String, Integer> portFeeMap = new HashMap<>();
                portFeeMap.put("EUR", eur_portRate);
                portFeeMap.put("GBP", gbp_portRate);
                portFeeMap.put("USD", usd_portRate);
                portFeeMap.put("USM", usm_portRate);
                portFeeMap.put("AUD", aud_portRate);

                Map<String, Integer> startingPriceMap = new HashMap<>();
                startingPriceMap.put("EUR", eur_min / 2);
                startingPriceMap.put("GBP", gbp_min / 2);
                startingPriceMap.put("USD", usd_min / 2);
                startingPriceMap.put("USM", usm_min / 2);
                startingPriceMap.put("AUD", aud_min / 2);

                voyageEs.setStartingPriceMap(startingPriceMap);
                voyageEs.setPortFeeMap(portFeeMap);
                voyageEs.setStartingPrice(usd_min / 2);
                voyageEs.setSuiteAvailability(totalAvailUnits);
                // Print all ports
                System.out.println("ports: " + ports);

                // If you want to set these on an object, do so here (already present):
                voyageEs.setPortData(portData);
                voyageEs.setPorts(ports);
                voyageEs.setPortCodes(portCodes);

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

    String getFirstAmount(List<ResListCategory> suites, boolean useBaseAmount) {
        if (suites != null && !suites.isEmpty()) {
            List<Category> categories = suites.get(0).getCategoryList();
            if (categories != null && !categories.isEmpty()) {
                Category firstCategory = categories.get(0);
                List<Rate> rates = firstCategory.getRateList();
                if (rates != null && !rates.isEmpty()) {
                    Rate firstRate = rates.get(0);
                    if (firstRate != null) {
                        return useBaseAmount ? firstRate.getBaseAmount() : firstRate.getAmount();
                    }
                }
            }
        }
        return "0";
    }
}