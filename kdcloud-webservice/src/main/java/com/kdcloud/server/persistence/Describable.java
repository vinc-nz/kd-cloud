package com.kdcloud.server.persistence;

import com.kdcloud.lib.domain.Metadata;

public interface Describable extends Entity {

	public Metadata getMetadata();
}
