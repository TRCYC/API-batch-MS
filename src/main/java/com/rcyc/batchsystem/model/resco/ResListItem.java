package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResListItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResListItem {
	@XmlElement(name = "Result")
	private Result Result;

	@XmlElement(name = "ItemList")
	private ItemList ItemList;

	public Result getResult() {
		return Result;
	}

	public void setResult(Result result) {
		Result = result;
	}

	public ItemList getItemList() {
		return ItemList;
	}

	public void setItemList(ItemList itemList) {
		ItemList = itemList;
	}
}
