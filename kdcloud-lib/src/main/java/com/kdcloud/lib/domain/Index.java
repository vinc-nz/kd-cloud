package com.kdcloud.lib.domain;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="index")
public class Index {
	
	static class Link {
		
		@XmlAttribute
		String href;
		
		public Link() {
		}

		public Link(String href) {
			super();
			this.href = href;
		}
		
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	static class Item {
		Link reference;
		Link metadata;
	}
	
	@XmlElement(name="item")
	List<Item> items = new LinkedList<Index.Item>();
	

	public void add(String referenceUrl, String metadataUrl) {
		Item item = new Item();
		item.reference = new Link(referenceUrl);
		item.metadata = new Link(metadataUrl);
		items.add(item);
	}
	
	public void addAll(Index other) {
		items.addAll(other.items);
	}
	
	public int size() {
		return items.size();
	}

}
