package abwilkinson.discordspringstarter.events;

import abwilkinson.discordspringstarter.events.service.CommandEventHandlerService;
import abwilkinson.discordspringstarter.events.service.StatefulEventHandlerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * MessageListener
 *
 * Main entry point for incoming messages to the bot. Messages will be forwarded to the appropriate
 * {@link EventHandler} implementation where the message matches the {@link EventHandler#matches(InputValues)} method.
 */
@Component
@AllArgsConstructor
@Slf4j
public class MessageListener extends ListenerAdapter {
	private final CommandEventHandlerService commandEventHandlerService;
	private final StatefulEventHandlerService statefulEventHandlerService;

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {
		if (event.getAuthor().isBot()) {
			return;
		}
		String rawMessage = event.getMessage().getContentRaw();
		String[] split = rawMessage.split("^!");
		boolean actionTaken;
		if (split.length > 1) {
			actionTaken = handleCommandMessage(event, split[1]);
		} else {
			actionTaken = callStatefulHandlers(event, rawMessage, false);
		}
		if (!actionTaken) {
			log.info("No action taken for message: " + rawMessage);
		}

	}

	private boolean handleCommandMessage(MessageReceivedEvent event, String message) {
		boolean actionTaken = callCommandHandlers(event, new InputValues(message));
		if (!actionTaken) {
			actionTaken = callStatefulHandlers(event, message, true);
		}
		return actionTaken;
	}

	private boolean callCommandHandlers(MessageReceivedEvent event, InputValues inputValues) {
		List<CommandEventHandler> appropriateHandlers = commandEventHandlerService.findAppropriateHandler(inputValues);
		return appropriateHandlers.stream().anyMatch(handler -> handler.messageReceived(event, inputValues));

	}

	private boolean callStatefulHandlers(MessageReceivedEvent event, String message, boolean isCommand) {
		List<StatefulEventHandler> appropriateHandlers = statefulEventHandlerService.findAppropriateHandler(new InputValues(message));
		if (isCommand) {
			return appropriateHandlers.stream().anyMatch(handler -> handler.handleCommand(event, new InputValues(message), event.getAuthor().getIdLong()));
		}
		return statefulEventHandlerService.getEventHandlers().stream().anyMatch(handler -> handler.handleNoneCommand(event, message, event.getAuthor().getIdLong()));
	}

}