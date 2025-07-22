package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlElement;

public class Item {

	private String comments;
	private String bookingId;
	private String eventId;
	private String itemId;
	private String details;
	private String begDate;
	private String type;
	private String amount;
	private String saleId;
	private String endDate;
	private String tripId;
	private String transactionId;
	private String reason;
	private String groupType;
	private String rate;
	private String surcharges;
	private String items;
	private String media;
	private String code;
	private String name;
	private String sort;
	private String availItems;
	private String itemTypeCode;
	private String itemTypeName;
	private String deliveryType;
	private String groupCode;
	private String groupName;
	private String bandCode;
	private String bandName;
	private String folioId;
	private String duration;
	private String durationHours;

	@XmlElement(name = "FolioId")
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

	/**
	 * @return the comments
	 */
	@XmlElement(name = "Comments")
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
	@XmlElement(name = "BookingId")
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
	@XmlElement(name = "EventId")
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
	@XmlElement(name = "ItemId")
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
	@XmlElement(name = "Details")
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
	@XmlElement(name = "BegDate")
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
	@XmlElement(name = "Type")
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
	@XmlElement(name = "Amount")
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
	@XmlElement(name = "SaleId")
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
	@XmlElement(name = "EndDate")
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
	@XmlElement(name = "TripId")
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
	@XmlElement(name = "TransactionId")
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
	@XmlElement(name = "Reason")
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	@XmlElement(name = "GroupType")
	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	@XmlElement(name = "Rate")
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@XmlElement(name = "Surcharges")
	public String getSurcharges() {
		return surcharges;
	}

	public void setSurcharges(String surcharges) {
		this.surcharges = surcharges;
	}

	@XmlElement(name = "Items")
	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	@XmlElement(name = "Media")
	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	@XmlElement(name = "Code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@XmlElement(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "Sort")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@XmlElement(name = "AvailItems")
	public String getAvailItems() {
		return availItems;
	}

	public void setAvailItems(String availItems) {
		this.availItems = availItems;
	}

	@XmlElement(name = "ItemTypeCode")
	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	@XmlElement(name = "ItemTypeName")
	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	@XmlElement(name = "DeliveryType")
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	@XmlElement(name = "GroupCode")
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@XmlElement(name = "GroupName")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@XmlElement(name = "BandCode")
	public String getBandCode() {
		return bandCode;
	}

	public void setBandCode(String bandCode) {
		this.bandCode = bandCode;
	}

	@XmlElement(name = "BandName")
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

}
