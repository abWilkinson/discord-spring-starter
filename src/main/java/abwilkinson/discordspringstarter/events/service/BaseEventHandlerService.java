package abwilkinson.discordspringstarter.events.service;

import abwilkinson.discordspringstarter.events.EventHandler;
import abwilkinson.discordspringstarter.events.InputValues;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BaseEventHandlerService
 *
 * Call the appropriate handler for the received event, based on the
 * result of the matches method.
 *
 * @param <T> EventHandler implementation
 */
public abstract class BaseEventHandlerService<T extends EventHandler> {

	public List<T> findAppropriateHandler(InputValues inputValues) {
		return getEventHandlers().stream()
				.filter(handler -> handler.matches(inputValues))
				.collect(Collectors.toList());
	}

	public abstract List<T> getEventHandlers();
}
