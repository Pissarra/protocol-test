package br.com.scytl.service;

import br.com.scytl.model.Protocol;
import br.com.scytl.util.MessageUtils;

public class EncodeService {

	private static final byte MASK_4 = (byte) 0x0F;

	private static final byte MASK_8 = (byte) 0xFF;

	private static final byte MASK_1 = (byte) 0x01;

	private static final int PACKAGE_LENGTH = 7;

	private EncodeService() {
	}

	public static byte[] encode(String message, Protocol protocol) {
		String paddedMessage = MessageUtils.padLengthToMultipleOf4(message);
		byte[] paddedMessageBytes = paddedMessage.getBytes();
		return encode(paddedMessageBytes, protocol);
	}

	public static byte[] encode(byte[] message, Protocol protocol) {
		byte[] messageDividedBy4Bits = divideBy4Bits(message);
		byte[] encodedMessage = encodeByProtocol(messageDividedBy4Bits, protocol);
		byte[] encodedMessageBy8Bits = groupBy8Bits(encodedMessage);
		return createPackages(encodedMessageBy8Bits, protocol);
	}

	private static byte[] divideBy4Bits(byte[] message) {
		byte[] dividedBy4Bytes = new byte[message.length * 2];
		for (int i = 0, j = 0; i < message.length; i++, j += 2) {
			byte temp = message[i];
			byte secondByte = (byte) (temp & MASK_4);
			temp = (byte) (temp >> 4);
			byte firstByte = (byte) (temp & MASK_4);
			dividedBy4Bytes[j] = firstByte;
			dividedBy4Bytes[j + 1] = secondByte;
		}
		return dividedBy4Bytes;
	}

	private static byte[] encodeByProtocol(byte[] message, Protocol protocol) {
		byte[] encodedValues = new byte[message.length];
		for (int i = 0; i < message.length; i++) {
			encodedValues[i] = (byte) protocol.getOut(message[i]);
		}
		return encodedValues;
	}

	private static byte[] groupBy8Bits(byte[] encodedMessage) {
		// adicionou todos os bits em um unico array
		long[] messageBits = new long[encodedMessage.length * 5];
		for (int i = encodedMessage.length-1, j = messageBits.length-1; i >= 0; i--, j -= 5) {
		

				byte temp = encodedMessage[i];
				byte fifthByte = (byte) (temp & MASK_1);
				temp = (byte) (temp >> 1);
				byte fourByte = (byte) (temp & MASK_1);
				temp = (byte) (temp >> 1);
				byte thirdByte = (byte) (temp & MASK_1);
				temp = (byte) (temp >> 1);
				byte secondByte = (byte) (temp & MASK_1);
				temp = (byte) (temp >> 1);
				byte firstByte = (byte) (temp & MASK_1);
				messageBits[j] = fifthByte;
				messageBits[j - 1] = fourByte;
				messageBits[j - 2] = thirdByte;
				messageBits[j - 3] = secondByte;
				messageBits[j - 4] = firstByte;

		}

		byte[] groupedBy8Bits = new byte[messageBits.length / 8];
		for (int i = 0, j = 0; i <= groupedBy8Bits.length-1; i++, j += 8) {
			groupedBy8Bits[i] = (byte) messageBits[j];
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] << 1);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] | messageBits[j + 1]);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] << 1);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] | messageBits[j + 2]);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] << 1);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] | messageBits[j + 3]);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] << 1);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] | messageBits[j + 4]);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] << 1);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] | messageBits[j + 5]);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] << 1);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] | messageBits[j + 6]);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] << 1);
			groupedBy8Bits[i] = (byte) (groupedBy8Bits[i] | messageBits[j + 7]);
		}
		for (byte b : groupedBy8Bits) {
			System.out.printf("%#x ", b);
		}
		return groupedBy8Bits;

	}

	private static byte[] createPackages(byte[] encodedValues, Protocol protocol) {
		byte[] packages = new byte[(encodedValues.length / 5) * PACKAGE_LENGTH];

		for (int i = 0, j = 0; i < encodedValues.length; i += 5, j += 7) {
			packages[j] = (byte) protocol.getStartPackageBinary();
			packages[j + 1] = encodedValues[i];
			packages[j + 2] = encodedValues[i + 1];
			packages[j + 3] = encodedValues[i + 2];
			packages[j + 4] = encodedValues[i + 3];
			packages[j + 5] = encodedValues[i + 4];
			if (packages.length-1 == j + 6) {
				packages[j + 6] = (byte) protocol.getEndTransmissionPackageBinary();
			} else {
				packages[j + 6] = (byte) protocol.getEndPackageBinary();
			}

		}
		System.out.println();
		for (byte b : packages) {
			System.out.printf("%#x ", b);
		}
		return packages;
	}
}
