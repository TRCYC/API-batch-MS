package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ReqListItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqListItem {
	@XmlElement(name = "User")
	private User user;

	@XmlElement(name = "Agency")
	private Agency agency;

	@XmlElement(name = "Item")
	private Item item;

	@XmlElement(name = "Availability")
	private Availability availability;

	// @XmlElement(name="ItemDetailParams")
	// private ItemDetailParams itemDetailParams;

	@XmlElement(name = "Event")
	private Event event;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Availability getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	/*
	 * public ItemDetailParams getItemDetailParams() { return itemDetailParams; }
	 * 
	 * public void setItemDetailParams(ItemDetailParams itemDetailParams) {
	 * this.itemDetailParams = itemDetailParams; }
	 */
}