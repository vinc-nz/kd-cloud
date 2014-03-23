/**
 * Copyright (C) 2012, Vincenzo Pirrone <pirrone.v@gmail.com>

 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 */
package com.kdcloud.lib.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.restlet.data.Parameter;

@XmlAccessorType(XmlAccessType.FIELD)
public class ServerParameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String REFERENCE_PREFIX = "xpath:";
	
	
	public static String newReference(String reference) {
		if (reference.contains(REFERENCE_PREFIX))
			return reference;
		return REFERENCE_PREFIX + reference;
	}
	
	private final String name;
	private final String value;
	private final Reference reference;
	
	public static class Reference implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@XmlAttribute
		String xpath;
		
		@XmlAttribute
		ReferenceType type = ReferenceType.CHOICE;
		
		public Reference() {
		}

		public Reference(String xpath, ReferenceType type) {
			super();
			this.xpath = xpath;
			this.type = type;
		}
		
		
		
	}
	
	public enum ReferenceType {
		CHOICE,
		MAP
	}

	public ServerParameter() {
		this(null, null);
	}

	public ServerParameter(String representation) {
		this.value = null;
		if (representation.contains(REFERENCE_PREFIX)) {
			this.name = null;
			this.reference = new Reference();
			this.reference.xpath = representation.replace(REFERENCE_PREFIX, "");
		}
		else {
			this.name = representation;
			this.reference = null;
		}
	}
	
	public ServerParameter(String name, String value) {
		super();
		this.name = name;
		this.value = value;
		this.reference = null;
	}
	
	public ServerParameter(String name, String value, String reference) {
		super();
		this.name = name;
		this.value = value;
		this.reference = new Reference();
		this.reference.xpath = reference;
	}
	

	public ServerParameter(String name, String reference,
			ReferenceType referenceType) {
		this.name = name;
		this.value = null;
		this.reference = new Reference();
		this.reference.xpath = reference;
		this.reference.type = referenceType;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getReferenceExpression() {
		return reference.xpath;
	}
	
	public ReferenceType getReferenceType() {
		return reference.type;
	}

	public boolean hasReference() {
		return reference != null;
	}
	
	public boolean hasValue() {
		return value != null;
	}
	
	public ServerParameter toReference(String reference) {
		return new ServerParameter(name, null, reference);
	}
	
	public ServerParameter toValue(String value) {
		return new ServerParameter(name, value);
	}
	
	public Parameter toRestletParameter() {
		return new Parameter(name, value);
	}

	@Override
	public String toString() {
		if (value != null)
			return value;
		String repr = (hasReference() ? REFERENCE_PREFIX + reference.xpath : name);
		return "{" + repr + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ServerParameter) {
			ServerParameter other = (ServerParameter) obj;
			if (this.name == null) {
				if (other.name == null)
					return this.reference.equals(other.reference);
				return false;
			}
			return this.name.equals(other.name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (name != null)
			return name.hashCode();
		return reference.hashCode();
	};

}
