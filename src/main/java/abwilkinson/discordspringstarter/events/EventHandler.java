package abwilkinson.discordspringstarter.events;

/**
 * EventHandler
 *
 * Base interface for {@link CommandEventHandler} and {@link StatefulEventHandler}. This should not be used
 * directly but one of these interfaces should be used to create an Event Handler.
 */
public interface EventHandler {

	boolean matches(InputValues inputValues);
}
