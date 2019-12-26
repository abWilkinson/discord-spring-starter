package abwilkinson.discordspringstarter.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * StatefulEventHandler
 *
 * Can be used to handle messages where the command token has not been used, so all messages in the channel
 * will be forwarded to these EventHandlers.
 */
public interface StatefulEventHandler extends EventHandler {

	boolean handleNoneCommand(MessageReceivedEvent event, String message, long userId);

	boolean handleCommand(MessageReceivedEvent event, InputValues inputValues, long userId);
}
