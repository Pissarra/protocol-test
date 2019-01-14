package br.com.scytl.main;

import br.com.scytl.model.Protocol;
import br.com.scytl.model.ProtocolX;
import br.com.scytl.service.DecodeService;
import br.com.scytl.service.EncodeService;
import br.com.scytl.util.MessageUtils;

public class Main {

	public static void main(String[] args) {
		Protocol protocol = ProtocolX.getInstance();
		String message = "BSB KAO";

		// String invertededMessage = MessageUtils.reverseMessage(message);
		// System.out.println(new String(invertededMessage));

		byte[] encodedMessage = EncodeService.encode(message, protocol);

		byte[] messagebytes = { (byte) 0xc6, 0x55, 0x17, 0x55, 0x52, (byte) 0x9e, 0x6b, (byte) 0xc6, 0x55, (byte) 0xd4,
				(byte) 0x95, 0x76, (byte) 0x9e, 0x21 };
		byte[] decodedMessage = DecodeService.decode(encodedMessage, protocol);
		System.out.println();
		 System.out.println(new String(decodedMessage));
	}

}
