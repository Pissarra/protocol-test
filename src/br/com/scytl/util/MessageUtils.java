package br.com.scytl.util;

public class MessageUtils {

	private MessageUtils() {
	}

	public static String reverseMessage(String message) {
		return new StringBuilder(message).reverse().toString();
	}

	public static String padLengthToMultipleOf4(String message) {
		if (message.length() % 4 == 0) {
			return message;
		}
		return rightPad(message,4 - message.length() % 4);
	}

	private static String rightPad(String message, int numberOfSpaces) {
		StringBuilder messageWithSpaces = new StringBuilder(message);
		for (int i = 0; i < numberOfSpaces; i++) {
			messageWithSpaces.append(" ");
		}
		return messageWithSpaces.toString();
	}
}
