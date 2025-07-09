package com.rcyc.batchsystem.process;

import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemProcessor;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.elastic.Hotel;

 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HotelProcessor {
    public ItemProcessor<DefaultPayLoad<Hotel, Object, Hotel>, DefaultPayLoad<Hotel, Object, Hotel>> hotelProcessForWrite() {
        return item -> {
            if (item != null && item.getReader() != null) {
                ResListEvent resListEvent = (ResListEvent) item.getReader();
                List<EventDetail> hotelEvents = resListEvent.getEventList();
                List<Hotel> hotels = new ArrayList<>();
                System.out.println("Before Hotel Process ");
                if(!hotelEvents.isEmpty()){
                    hotels = hotelEvents.stream().map(this::getElasticModel)
                    .collect(Collectors.toList());
                }
                System.out.println("After Hotel Process "+hotels.size());
                item.setResponse(hotels);
            }
            return item;
        };
    }

    private Hotel getElasticModel(EventDetail hotelEventDetail){
        Hotel hotel = new Hotel();
        hotel.setOperatorId(String.valueOf(hotelEventDetail.getProviderId()));
        hotel.setDuration(hotelEventDetail.getDuration());
        hotel.setEndDate(hotelEventDetail.getEndDate());
        hotel.setSalePeriod(hotelEventDetail.getSalePeriod());
        hotel.setCreatedDate(new Date().toString());
        hotel.setFacilityName(hotelEventDetail.getFacilityName());
        hotel.setFacilityCode(hotelEventDetail.getFacilityCode());
        hotel.setModifyDate(hotelEventDetail.getModifyDate());
        hotel.setHotelName(hotelEventDetail.getComments());
        hotel.setBegDate(hotelEventDetail.getBegDate());
        hotel.setEndTimeZone(String.valueOf(hotelEventDetail.getEndTimeZone()));
        hotel.setFacilityId(String.valueOf(hotelEventDetail.getFacilityId()));
        hotel.setBegTimeZone(String.valueOf(hotelEventDetail.getBegTimeZone()));
        hotel.setEnabled(String.valueOf(hotelEventDetail.getEnabled()));
        hotel.setProviderName(hotelEventDetail.getProviderName());
        hotel.setHotelCode(hotelEventDetail.getCode());
        hotel.setProviderCode(hotelEventDetail.getProviderCode());
        hotel.setInfoPrice(String.valueOf(hotelEventDetail.getInfoPrice()));
        hotel.setProviderId(String.valueOf(hotelEventDetail.getProviderId()));
        hotel.setEventId(hotelEventDetail.getEventId());
        hotel.setSort(hotelEventDetail.getSort());

        return hotel;
    }
} 