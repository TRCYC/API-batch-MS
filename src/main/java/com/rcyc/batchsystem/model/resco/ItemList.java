package com.rcyc.batchsystem.model.resco;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ItemList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemList {

	private List<Item> itemList;

	/**
	 * @return the item
	 */
	@XmlElement(name = "Item")
	public List<Item> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		return "ItemList [itemList=" + itemList + "]";
	}
}
