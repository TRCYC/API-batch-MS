package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Event")
@XmlAccessorType(XmlAccessType.FIELD)
public class Event {
	@XmlElement(name = "Disabled")
	private int disabled;

	@XmlElement(name = "BegEventId")
	private String begEventId;

	public Event() {
	}

	public Event(int disabled) {
		this.disabled = disabled;
	}

	public Event(String begEventId) {
		this.begEventId = begEventId;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	public String getBegEventId() {
		return begEventId;
	}

	public void setBegEventId(String begEventId) {
		this.begEventId = begEventId;
	}
}