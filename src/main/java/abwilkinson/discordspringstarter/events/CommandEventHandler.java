package abwilkinson.discordspringstarter.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * CommandEventHandler
 *
 * Interface which should be used to handle commands received from the bot.
 * e.g. !ping
 */
public interface CommandEventHandler extends EventHandler {

	/**
	 * messageReceived
	 *
	 * Will be called when a command is received which passes the implemented
	 * {@link EventHandler#matches(InputValues)} method
	 *
	 * @param event The received event
	 * @param message Extracted command and parameters
	 * @return If the command has been actioned, and no other handlers should be called.
	 */
	boolean messageReceived(MessageReceivedEvent event, InputValues message);
}
