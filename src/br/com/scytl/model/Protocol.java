package br.com.scytl.model;


public abstract class Protocol {

	public abstract byte getOut(byte binaryIn);

	public abstract byte getIn(byte binaryOut);

	public abstract byte getStartPackageBinary();

	public abstract byte getEndPackageBinary();

	public abstract byte getEndTransmissionPackageBinary();
	
}
