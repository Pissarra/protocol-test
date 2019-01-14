package br.com.scytl.service;

import br.com.scytl.model.Protocol;

public class DecodeService {

	private static final byte MASK_1 = (byte) 0x01;

	private DecodeService() {
	}

	public static byte[] decode(String message, Protocol protocol) {
		return decode(message.getBytes(), protocol);
	}

	public static byte[] decode(byte[] message, Protocol protocol) {
		byte[] messageWithoutPackageBytes = removePackageBytes(message, protocol);
		byte[] messageDividedBy5Bits = groupBy5Bits(messageWithoutPackageBytes);
		byte[] decodedMessage = decodeByProtocol(messageDividedBy5Bits, protocol);
		byte[] messageGroupBy8Bits = groupBy8Bits(decodedMessage);
		return messageGroupBy8Bits;
	}

	private static byte[] removePackageBytes(byte[] encodedMessage, Protocol protocol) {
		byte[] encodedMessageWithouPackageBytes = new byte[encodedMessage.length - ((encodedMessage.length / 7) * 2)];

		for (int i = 0, j = 0; i < encodedMessage.length; i++) {
			if (encodedMessage[i] != protocol.getEndTransmissionPackageBinary()
					&& encodedMessage[i] != protocol.getEndPackageBinary()
					&& encodedMessage[i] != protocol.getStartPackageBinary()) {
				encodedMessageWithouPackageBytes[j++] = encodedMessage[i];
			}
		}
		return encodedMessageWithouPackageBytes;
	}

	private static byte[] groupBy5Bits(byte[] message) {
		// adicionou todos os bits em um unico array
		long[] messageBits = new long[message.length * 8];
		for (int i = message.length - 1, j = messageBits.length - 1; i >= 0; i--, j -= 8) {

			byte temp = message[i];
			byte eigththByte = (byte) (temp & MASK_1);
			temp = (byte) (temp >> 1);
			byte seventhByte = (byte) (temp & MASK_1);
			temp = (byte) (temp >> 1);
			byte sixthByte = (byte) (temp & MASK_1);
			temp = (byte) (temp >> 1);
			byte fifthByte = (byte) (temp & MASK_1);
			temp = (byte) (temp >> 1);
			byte fourByte = (byte) (temp & MASK_1);
			temp = (byte) (temp >> 1);
			byte thirdByte = (byte) (temp & MASK_1);
			temp = (byte) (temp >> 1);
			byte secondByte = (byte) (temp & MASK_1);
			temp = (byte) (temp >> 1);
			byte firstByte = (byte) (temp & MASK_1);
			messageBits[j] = eigththByte;
			messageBits[j - 1] = seventhByte;
			messageBits[j - 2] = sixthByte;
			messageBits[j - 3] = fifthByte;
			messageBits[j - 4] = fourByte;
			messageBits[j - 5] = thirdByte;
			messageBits[j - 6] = secondByte;
			messageBits[j - 7] = firstByte;

		}

		byte[] groupedBy5Bits = new byte[messageBits.length / 5];
		for (int i = 0, j = 0; i <= groupedBy5Bits.length - 1; i++, j += 5) {
			groupedBy5Bits[i] = (byte) messageBits[j];
			groupedBy5Bits[i] = (byte) (groupedBy5Bits[i] << 1);
			groupedBy5Bits[i] = (byte) (groupedBy5Bits[i] | messageBits[j + 1]);
			groupedBy5Bits[i] = (byte) (groupedBy5Bits[i] << 1);
			groupedBy5Bits[i] = (byte) (groupedBy5Bits[i] | messageBits[j + 2]);
			groupedBy5Bits[i] = (byte) (groupedBy5Bits[i] << 1);
			groupedBy5Bits[i] = (byte) (groupedBy5Bits[i] | messageBits[j + 3]);
			groupedBy5Bits[i] = (byte) (groupedBy5Bits[i] << 1);
			groupedBy5Bits[i] = (byte) (groupedBy5Bits[i] | messageBits[j + 4]);
		}
		return groupedBy5Bits;
	}

	private static byte[] decodeByProtocol(byte[] message, Protocol protocol) {
		byte[] decodedValues = new byte[message.length];
		for (int i = 0; i < message.length; i++) {
			decodedValues[i] = (byte) protocol.getIn(message[i]);
		}
		return decodedValues;
	}

	private static byte[] groupBy8Bits(byte[] message) {
		byte[] groupedBy8Bytes = new byte[message.length / 2];
		for (int i = 0, j = 0; i < message.length; i += 2, j++) {
			byte temp = (byte) (message[i] );
			temp = (byte) (temp << 4);
			temp = (byte) (temp | message[i + 1]);
			groupedBy8Bytes[j] = temp;
		}
		return groupedBy8Bytes;
	}
}
