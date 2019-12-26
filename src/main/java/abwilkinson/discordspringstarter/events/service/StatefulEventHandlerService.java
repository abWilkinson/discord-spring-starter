package abwilkinson.discordspringstarter.events.service;

import abwilkinson.discordspringstarter.events.StatefulEventHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * StatefulEventHandlerService
 *
 * Service for collecting and processing StatefulEventHandlers
 */
@Component
@AllArgsConstructor
public class StatefulEventHandlerService extends BaseEventHandlerService<StatefulEventHandler> {
	private final List<StatefulEventHandler> eventHandlers;

	@Override
	public List<StatefulEventHandler> getEventHandlers() {
		return eventHandlers;
	}
}
