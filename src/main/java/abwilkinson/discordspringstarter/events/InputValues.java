package abwilkinson.discordspringstarter.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * InputValues
 *
 * Helper for accessing parts of the received message. Splits the message into the command
 * and any separate parameters based on spaces.
 */
@Getter
@EqualsAndHashCode
public class InputValues {
	private String originalMessage;
	private String command;
	private String parameter;

	public InputValues(String message) {
		originalMessage = message;
		if (!message.contains(" ")) {
			command = message;
		} else {
			String[] messageAndParam = message.split(" ", 2);
			command = messageAndParam[0];
			parameter = messageAndParam[1];
		}
	}

}
