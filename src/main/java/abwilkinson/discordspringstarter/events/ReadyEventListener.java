package abwilkinson.discordspringstarter.events;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

/**
 * ReadyEventListener
 *
 * Simple implementation just to show when the bot is connected and ready to receive messages.
 */
@Slf4j
public class ReadyEventListener implements EventListener {

	@Override
	public void onEvent(GenericEvent event) {
		if (event instanceof ReadyEvent) {
			log.info("Bot is ready!");
		}
	}

}