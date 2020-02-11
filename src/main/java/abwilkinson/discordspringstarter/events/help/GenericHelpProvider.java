package abwilkinson.discordspringstarter.events.help;

import abwilkinson.discordspringstarter.events.InputValues;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * GenericHelpProvider
 *
 * Process application level !help commands which are not actioned in any particular message handler.
 */
public interface GenericHelpProvider {

    void handleHelpCommand(MessageReceivedEvent event, InputValues inputValues);
}
