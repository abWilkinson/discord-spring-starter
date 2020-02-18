package abwilkinson.discordspringstarter.events.help;

import abwilkinson.discordspringstarter.events.InputValues;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Can be implemented to handle a standard help command, with optional parameters. e.g. !help or !help something
 */
public interface HelpContext {

    /**
     * helpCommandReceived
     *
     * @param event The original message received
     * @param message The values extracted from the message
     * @return Has the help command been actioned. True will prevent this being propagated further.
     */
    boolean helpCommandReceived(MessageReceivedEvent event, InputValues message);
}
