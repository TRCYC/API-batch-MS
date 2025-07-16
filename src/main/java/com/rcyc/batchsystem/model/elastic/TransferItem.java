package com.rcyc.batchsystem.model.elastic;

import java.util.List;

import com.rcyc.batchsystem.model.resco.Item;

public class TransferItem {

	private int voyageId;
	private String voyageCode;
	private String portCode;
	private String portName;
	private String countryCode;
	private String transferTfResultStatus;
	private List<Item> itemList;

	public int getVoyageId() {
		return voyageId;
	}

	public void setVoyageId(int voyageId) {
		this.voyageId = voyageId;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public String getPortCode() {
		return portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getVoyageCode() {
		return voyageCode;
	}

	public void setVoyageCode(String voyageCode) {
		this.voyageCode = voyageCode;
	}

	@Override
	public String toString() {
		return "TransferItem [voyageId=" + voyageId + ", voyageCode=" + voyageCode + ", portCode=" + portCode
				+ ", portName=" + portName + ", countryCode=" + countryCode + ", itemList=" + itemList + "]";
	}

	public String getTransferTfResultStatus() {
		return transferTfResultStatus;
	}

	public void setTransferTfResultStatus(String transferTfResultStatus) {
		this.transferTfResultStatus = transferTfResultStatus;
	}

}
