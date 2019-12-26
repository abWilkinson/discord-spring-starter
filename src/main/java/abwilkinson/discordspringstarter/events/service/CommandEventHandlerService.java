package abwilkinson.discordspringstarter.events.service;

import abwilkinson.discordspringstarter.events.CommandEventHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CommandEventHandlerService
 *
 * Service for collecting and processing CommandEventHandlers
 */
@Component
@AllArgsConstructor
public class CommandEventHandlerService extends BaseEventHandlerService<CommandEventHandler>{
	private final List<CommandEventHandler> eventHandlers;

	@Override
	public List<CommandEventHandler> getEventHandlers() {
		return eventHandlers;
	}
}
