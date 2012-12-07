package com.kdcloud.server.entity;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.kdcloud.lib.domain.Metadata;
import com.kdcloud.lib.domain.ModalitySpecification;
import com.kdcloud.server.persistence.Describable;

@PersistenceCapable
public class Modality implements Describable {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String uuid;
	
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
	private String name;
	
	@Persistent(serialized="true")
	ModalitySpecification modality;
	
	@Persistent(serialized="true")
	Metadata metadata;

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public String getUUID() {
		return uuid;
	}

	public void setUUID(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ModalitySpecification getModality() {
		return modality;
	}

	public void setModality(ModalitySpecification modality) {
		this.modality = modality;
	}
	

}
