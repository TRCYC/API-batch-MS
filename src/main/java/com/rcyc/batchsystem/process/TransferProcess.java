package com.rcyc.batchsystem.process;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rcyc.batchsystem.model.elastic.Transfer;
import com.rcyc.batchsystem.model.elastic.TransferItem;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.Item;
import com.rcyc.batchsystem.model.resco.ItemList;
import com.rcyc.batchsystem.model.resco.ResListItem;

@Component
public class TransferProcess {

	public ItemProcessor<DefaultPayLoad<Transfer, Object, Transfer>, DefaultPayLoad<Transfer, Object, Transfer>> transferProcessForWrite() {
        return item -> {
            if (item != null && item.getReader() != null) {
            	Map<String, Object> map = (Map<String, Object>) item.getReader();
            	List<Transfer> processedList = new ArrayList<Transfer>();
            	int count = 0;
            	for(String key : map.keySet()) {
            		//System.out.println("Loop " + (++count) + "   voyageCode--" +key);
            		TransferItem transferItem = (TransferItem) map.get(key);
            		if (transferItem != null && transferItem.getItemList()!=null ) {
            			/*for(Item itemObj : transferItem.getItemList()) {
            				Transfer transfer = getAsTransfer(itemObj, transferItem, key);
            				processedList.add(transfer);
            			}*/
            			
            			processedList.addAll(transferItem.getItemList().stream()
                        .map(itemObj -> getAsTransfer(itemObj, transferItem, key))
                        .collect(Collectors.toList()));

            		} else {
            			System.out.println("TransferItem for the voyage is empty");
            		}
            	}
                /*ItemList itemList = transferList.getItemList();
                List<Transfer> processedList = null;
                if (itemList != null && itemList.getItemList() != null) {
                    processedList = itemList.getItemList().stream()
                        .map(this::getAsTransfer)
                        .collect(Collectors.toList());
                } else {
                    processedList = java.util.Collections.emptyList();
                }*/
                System.out.println("Transfer Process --"+processedList.size());
                item.setResponse(processedList); // bind back
            }
            return item;
        };
    }
	
	private Transfer getAsTransfer(Item item, TransferItem transferItem, String voyageCode) {
        Transfer transfer = new Transfer();
		transfer.setVoyageId(String.valueOf(transferItem.getVoyageId()));
		transfer.setVoyageCode(voyageCode);
		transfer.setPortCode(transferItem.getPortCode());
		transfer.setPortName(transferItem.getPortName());
		transfer.setCountryCode(transferItem.getCountryCode());
		transfer.setTransferTfResultStatus(transferItem.getTransferTfResultStatus());
		transfer.setEventId(item.getItemId() + String.valueOf(transfer.getVoyageId()));
        transfer.setRescoItemID(item.getItemId());
        transfer.setAvailItems(item.getAvailItems());
        transfer.setTransferCode(item.getCode());
        transfer.setTransferName(item.getName());
        transfer.setSortOrder(item.getSort());
        transfer.setDuration(item.getDuration());
        transfer.setDurationHours(item.getDurationHours());
        transfer.setBegDate(item.getBegDate());
        transfer.setEndDate(item.getEndDate());
        transfer.setDeliveryType(item.getDeliveryType());
        transfer.setItemTypeCode(item.getItemTypeCode());
        transfer.setItemTypeName(item.getItemTypeName());
        transfer.setGroupCode(item.getGroupCode());
        transfer.setGroupName(item.getGroupName());
        transfer.setBandCode(item.getBandCode());
        transfer.setBandName(item.getBandName());
        transfer.setCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        /*System.out.println("Beg Date--"+ item.getBegDate());
        System.out.println("End Date--"+ item.getEndDate());
        System.out.println("Duration--"+ item.getDuration());
        System.out.println("Duration Hours--"+ item.getDurationHours());
        transfer.setBegDate("2025-07-16");
        transfer.setEndDate(null);
        transfer.setDuration("0");
        transfer.setDurationHours(null);*/
        return transfer;
    }
}
