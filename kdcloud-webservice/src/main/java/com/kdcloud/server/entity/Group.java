package com.kdcloud.server.entity;

import java.util.Collection;
import java.util.HashSet;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.kdcloud.lib.domain.DataSpecification;

import weka.core.Instances;

@PersistenceCapable
public class Group {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	
//	@Persistent
//	@Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
//	private Long id;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String name;
	
	@Persistent
	private Collection<DataTable> data = new HashSet<DataTable>();
	
	@Persistent(serialized="true")
	private DataSpecification inputSpecification;

	public Group() {
		// TODO Auto-generated constructor stub
	}
	
	public Group(String name) {
		this.name = name;
	}

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}
	
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<DataTable> getData() {
		return data;
	}

	public void setData(Collection<DataTable> data) {
		this.data = data;
	}
	
	public DataTable addEntry(User user, Instances instances) {
		DataTable table = new DataTable();
		table.setOwner(user);
		table.setName(user.getName());
		table.setInstances(instances);
		data.add(table);
		return table;
	}

	public DataSpecification getInputSpecification() {
		return inputSpecification;
	}

	public void setInputSpecification(DataSpecification inputSpecification) {
		this.inputSpecification = inputSpecification;
	}
	

}
