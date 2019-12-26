package abwilkinson.discordspringstarter.events;

import abwilkinson.discordspringstarter.BaseSpringBootTest;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;

import static org.mockito.Mockito.*;

public class MessageListenerTests extends BaseSpringBootTest {

	@Autowired
	private MessageListener messageListener;

	@MockBean
	private TestCommandEventHandler testCommandEventHandler;

	@MockBean
	private TestStatefulEventHandler testStatefulEventHandler;

	@Test
	public void testCommandHandlerIsUsed() {
		String expectedMessage = "test";
		String rawMessage = "!" + expectedMessage;
		MessageReceivedEvent event = getMockEvent(rawMessage);
		when(testCommandEventHandler.matches(Mockito.any(InputValues.class))).thenReturn(true);
		messageListener.onMessageReceived(event);

		verify(testCommandEventHandler, times(1)).messageReceived(event, new InputValues(expectedMessage));
	}

	@Test
	public void setTestStatefulEventHandlerReceivesCommands() {
		String expectedMessage = "test";
		String rawMessage = "!" + expectedMessage;
		MessageReceivedEvent event = getMockEvent(rawMessage);
		when(testStatefulEventHandler.matches(Mockito.any(InputValues.class))).thenReturn(true);
		when(event.getAuthor().getIdLong()).thenReturn(1L);

		messageListener.onMessageReceived(event);

		verify(testStatefulEventHandler, times(1)).handleCommand(event, new InputValues(expectedMessage), 1L);
	}

	@Test
	public void setTestStatefulEventHandlerReceivesNonCommands() {
		String expectedMessage = "test";
		MessageReceivedEvent event = getMockEvent(expectedMessage);
		when(testStatefulEventHandler.matches(Mockito.any(InputValues.class))).thenReturn(true);
		when(event.getAuthor().getIdLong()).thenReturn(1L);

		messageListener.onMessageReceived(event);

		verify(testStatefulEventHandler, times(1)).handleNoneCommand(event, expectedMessage, 1L);
	}

	@Test
	public void testInvalidMessageCallsNothing() {
		String expectedMessage = "asdasdasd";
		String rawMessage = "!" + expectedMessage;
		MessageReceivedEvent event = getMockEvent(rawMessage);
		when(testCommandEventHandler.matches(Mockito.any(InputValues.class))).thenReturn(false);
		messageListener.onMessageReceived(event);

		verify(testCommandEventHandler, never()).messageReceived(event, new InputValues(expectedMessage));
	}

	@Test
	public void messagesFromBotsAreIgnored() {
		MessageReceivedEvent event = getMockEvent("!bot");
		when(event.getAuthor().isBot()).thenReturn(true);
		messageListener.onMessageReceived(event);
		verify(testCommandEventHandler, never()).matches(Mockito.any(InputValues.class));
	}


	protected MessageReceivedEvent getMockEvent(String rawMessage) {
		Message mockMessage = mock(Message.class);
		User user = mock(User.class);
		when(user.isBot()).thenReturn(false);
		when(mockMessage.getContentRaw()).thenReturn(rawMessage);
		when(mockMessage.getAuthor()).thenReturn(user);
		return new MessageReceivedEvent(jda, 1, mockMessage);
	}

	@Component
	public static class TestCommandEventHandler implements CommandEventHandler {

		@Override
		public boolean messageReceived(MessageReceivedEvent event, InputValues message) {
			return true;
		}

		@Override
		public boolean matches(InputValues inputValues) {
			return "test".equals(inputValues.getCommand());
		}
	}

	@Component
	public static class TestStatefulEventHandler implements StatefulEventHandler {

		@Override
		public boolean handleNoneCommand(MessageReceivedEvent event, String message, long userId) {
			return false;
		}

		@Override
		public boolean handleCommand(MessageReceivedEvent event, InputValues inputValues, long userId) {
			return false;
		}

		@Override
		public boolean matches(InputValues inputValues) {
			return false;
		}
	}
}
