package com.rcyc.batchsystem.process;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Transfer;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.Item;
import com.rcyc.batchsystem.model.resco.ItemList;
import com.rcyc.batchsystem.model.resco.ResListItem;

@Component
public class TransferProcess {

	public ItemProcessor<DefaultPayLoad<Item, Object, Item>, DefaultPayLoad<Item, Object, Item>> transferProcessForWrite() {
        return item -> {
            if (item != null && item.getReader() != null) {
            	ResListItem transferList = (ResListItem) item.getReader();
                ItemList itemList = transferList.getItemList();
                List<Transfer> processedList = null;
                if (itemList != null && itemList.getItemList() != null) {
                    processedList = itemList.getItemList().stream()
                        .map(this::getAsTransfer)
                        .collect(Collectors.toList());
                } else {
                    processedList = java.util.Collections.emptyList();
                }
                System.out.println(processedList.size());
                //item.setResponse(processedList); // bind back
            }
            return item;
        };
    }
	
	private Transfer getAsTransfer(Item item) {
        Transfer transfer = new Transfer();
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
        return transfer;
    }
}
