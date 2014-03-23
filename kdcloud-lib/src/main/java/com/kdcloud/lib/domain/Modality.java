package com.kdcloud.lib.domain;

public interface Modality {

	public ServerAction getInitAction();

	public ServerAction getAction();

	public DataSpecification getInputSpecification();

	public DataSpecification getOutputSpecification();

}
