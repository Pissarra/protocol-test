package br.com.scytl.model;

import java.util.HashMap;
import java.util.Map;

public class ProtocolX extends Protocol {

	private static final byte START_PACKAGE = (byte) 0xC6;

	private static final byte END_PACKAGE = (byte) 0x6B;

	private static final byte END_TRANSMISSION = (byte) 0x21;

	private Map<Byte, Byte> protocolXValues = new HashMap<Byte, Byte>();

	private Map<Byte, Byte> protocolXInverseValues = new HashMap<Byte, Byte>();

	private ProtocolX() {
	}

	private static Protocol protocol = new ProtocolX();

	public static Protocol getInstance() {
		return protocol;
	}

	@Override
	public byte getOut(byte binaryIn) {
		return getProtocolValues().get(binaryIn);
	}

	@Override
	public byte getIn(byte binaryOut) {
		return getProtocolXInverseValues().get(binaryOut);
	}

	@Override
	public byte getStartPackageBinary() {
		return START_PACKAGE;
	}

	@Override
	public byte getEndPackageBinary() {
		return END_PACKAGE;
	}

	@Override
	public byte getEndTransmissionPackageBinary() {
		return END_TRANSMISSION;
	}

	public Map<Byte, Byte> getProtocolValues() {
		if (protocolXValues.isEmpty()) {
			populateProtocolValues();
		}
		return protocolXValues;
	}

	private void populateProtocolValues() {
		protocolXValues.put((byte) 0x0, (byte) 0x1E);
		protocolXValues.put((byte) 0x01, (byte) 0x9);
		protocolXValues.put((byte) 0x2, (byte) 0x14);
		protocolXValues.put((byte) 0x3, (byte) 0x15);
		protocolXValues.put((byte) 0x4, (byte) 0xA);
		protocolXValues.put((byte) 0x5, (byte) 0xB);
		protocolXValues.put((byte) 0x6, (byte) 0xE);
		protocolXValues.put((byte) 0x7, (byte) 0xF);
		protocolXValues.put((byte) 0x8, (byte) 0x12);
		protocolXValues.put((byte) 0x9, (byte) 0x13);
		protocolXValues.put((byte) 0xA, (byte) 0x16);
		protocolXValues.put((byte) 0xB, (byte) 0x17);
		protocolXValues.put((byte) 0xC, (byte) 0x1A);
		protocolXValues.put((byte) 0xD, (byte) 0x1B);
		protocolXValues.put((byte) 0xE, (byte) 0x1C);
		protocolXValues.put((byte) 0xF, (byte) 0x1D);
	}

	public Map<Byte, Byte> getProtocolXInverseValues() {
		if (protocolXInverseValues.isEmpty()) {
			populateProtocolInverseValues();
		}
		return protocolXInverseValues;
	}

	private void populateProtocolInverseValues() {
		for (Map.Entry<Byte, Byte> entry : getProtocolValues().entrySet()) {
			protocolXInverseValues.put(entry.getValue(), entry.getKey());
		}
	}

}
