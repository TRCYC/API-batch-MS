package com.rcyc.batchsystem.model.resco;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
@XmlElement(name = "Comments")
	private String comments;
	@XmlElement(name = "BookingId")
	private String bookingId;
	@XmlElement(name = "EventId")
	private String eventId;
	@XmlElement(name = "ItemId")
	private String itemId;
	@XmlElement(name = "Details")
	private String details;
	@XmlElement(name = "BegDate")
	private String begDate;
	@XmlElement(name = "Type")
	private String type;
	@XmlElement(name = "Amount")
	private String amount;
	@XmlElement(name = "SaleId")
	private String saleId;
	@XmlElement(name = "EndDate")
	private String endDate;
	@XmlElement(name = "TripId")
	private String tripId;
	@XmlElement(name = "TransactionId")
	private String transactionId;
	@XmlElement(name = "Reason")
	private String reason;
	@XmlElement(name = "GroupType")
	private String groupType;
	@XmlElement(name = "Rate")
	private String rate;
	@XmlElement(name = "Surcharges")
	private String surcharges;
	@XmlElement(name = "Items")
	private String items;
	@XmlElement(name = "Media")
	private String media;
	@XmlElement(name = "Code")
	private String code;
	@XmlElement(name = "Name")
	private String name;
	@XmlElement(name = "Sort")
	private String sort;
	@XmlElement(name = "AvailItems")
	private String availItems;
	@XmlElement(name = "ItemTypeCode")
	private String itemTypeCode;
	@XmlElement(name = "ItemTypeName")
	private String itemTypeName;
	@XmlElement(name = "DeliveryType")
	private String deliveryType;
	@XmlElement(name = "GroupCode")
	private String groupCode;
	@XmlElement(name = "GroupName")
	private String groupName;
	@XmlElement(name = "BandCode")
	private String bandCode;
	@XmlElement(name = "BandName")
	private String bandName;
	@XmlElement(name = "Flex01")
	private String flex01;
	@XmlElement(name = "Flex02")
	private String flex02;
	@XmlElement(name = "Flex03")
	private String flex03;
    @XmlElement(name = "Flex04")
	private String flex04;
	@XmlElement(name = "FolioId")
	private String folioId;
	@XmlElement(name="InfoText")
	private String infoText;
    @XmlElement(name="PortName")
	private String portName;    
 	@XmlElement(name="PortRegion")
	private String portRegion;   
	@XmlElement(name="Country")
	private String country;   
@XmlElement(name="Duration")
	private String duration;
	@XmlElement(name="DurationHours")
	private String durationHours;

	@XmlElement(name = "Events")
	private int events;

	@XmlElement(name = "Locations")
	private int locations;
	@XmlElement(name = "ExternalId")
	private String externalId;
	@XmlElementWrapper(name = "RateList")
	@XmlElement(name = "Rate")
	private List<Rate> rateList;
	@XmlElementWrapper(name = "PageList")
	@XmlElement(name = "Page")
	private List<Page> pageList;

	@XmlElementWrapper(name = "FlexItemList")
	@XmlElement(name = "FlexItem")
	private List<FlexItem> flexItemList;

	
	@XmlElementWrapper(name = "BegLocationList")
	@XmlElement(name = "BegLocation")
	private List<BegLocation> begLocationList;

	public String getFolioId() {
		return folioId;
	}

	public void setFolioId(String folioId) {
		this.folioId = folioId;
	}

	public Item() {
		super();
	}

	/**
	 * @param saleId
	 * @param reason
	 */
	public Item(String saleId, String reason) {
		super();
		this.saleId = saleId;
		this.reason = reason;
	}

	/**
	 * @param comments
	 * @param bookingId
	 * @param eventId
	 * @param itemId
	 * @param details
	 */
	public Item(String comments, String bookingId, String eventId, String itemId, String details) {
		super();
		this.comments = comments;
		this.bookingId = bookingId;
		this.eventId = eventId;
		this.itemId = itemId;
		this.details = details;
	}
	

	public String getInfoText() {
		return infoText;
	}

	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}

	/**
	 * @return the comments
	 */
	
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the bookingId
	 */
	
	public String getBookingId() {
		return bookingId;
	}

	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * @return the eventId
	 */
	
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the itemId
	 */
	
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the details
	 */
	
	public String getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * @return the begDate
	 */
	
	public String getBegDate() {
		return begDate;
	}

	/**
	 * @param begDate the begDate to set
	 */
	public void setBegDate(String begDate) {
		this.begDate = begDate;
	}

	/**
	 * @return the type
	 */
	
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the saleId
	 */
	
	public String getSaleId() {
		return saleId;
	}

	/**
	 * @param saleId the saleId to set
	 */
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the tripId
	 */
	public String getTripId() {
		return tripId;
	}

	/**
	 * @param tripId the tripId to set
	 */
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getSurcharges() {
		return surcharges;
	}

	public void setSurcharges(String surcharges) {
		this.surcharges = surcharges;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getAvailItems() {
		return availItems;
	}

	public void setAvailItems(String availItems) {
		this.availItems = availItems;
	}

	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getBandCode() {
		return bandCode;
	}

	public void setBandCode(String bandCode) {
		this.bandCode = bandCode;
	}

	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	@Override
	public String toString() {
		return "Item [comments=" + comments + ", bookingId=" + bookingId + ", eventId=" + eventId + ", itemId=" + itemId
				+ ", details=" + details + ", begDate=" + begDate + ", type=" + type + ", amount=" + amount
				+ ", saleId=" + saleId + ", endDate=" + endDate + ", tripId=" + tripId + ", transactionId="
				+ transactionId + ", reason=" + reason + ", groupType=" + groupType + ", rate=" + rate + ", surcharges="
				+ surcharges + ", items=" + items + ", media=" + media + ", code=" + code + ", name=" + name + ", sort="
				+ sort + ", availItems=" + availItems + ", itemTypeCode=" + itemTypeCode + ", itemTypeName="
				+ itemTypeName + ", deliveryType=" + deliveryType + ", groupCode=" + groupCode + ", groupName="
				+ groupName + ", bandCode=" + bandCode + ", bandName=" + bandName + "]";
	}

	public String getDurationHours() {
		return durationHours;
	}

	public void setDurationHours(String durationHours) {
		this.durationHours = durationHours;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getEvents() {
		return events;
	}

	public void setEvents(int events) {
		this.events = events;
	}

	public int getLocations() {
		return locations;
	}

	public void setLocations(int locations) {
		this.locations = locations;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public List<Rate> getRateList() {
		return rateList;
	}

	public void setRateList(List<Rate> rateList) {
		this.rateList = rateList;
	}

	public List<Page> getPageList() {
		return pageList;
	}

	public void setPageList(List<Page> pageList) {
		this.pageList = pageList;
	}

	public List<FlexItem> getFlexItemList() {
		return flexItemList;
	}

	public void setFlexItemList(List<FlexItem> flexItemList) {
		this.flexItemList = flexItemList;
	}

	public List<BegLocation> getBegLocationList() {
		return begLocationList;
	}

	public void setBegLocationList(List<BegLocation> begLocationList) {
		this.begLocationList = begLocationList;
	}

	public String getFlex01() {
		return flex01;
	}

	public void setFlex01(String flex01) {
		this.flex01 = flex01;
	}

	public String getFlex02() {
		return flex02;
	}

	public void setFlex02(String flex02) {
		this.flex02 = flex02;
	}

	public String getFlex03() {
		return flex03;
	}

	public void setFlex03(String flex03) {
		this.flex03 = flex03;
	}

	public String getFlex04() {
		return flex04;
	}

	public void setFlex04(String flex04) {
		this.flex04 = flex04;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getPortRegion() {
		return portRegion;
	}

	public void setPortRegion(String portRegion) {
		this.portRegion = portRegion;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	

}
