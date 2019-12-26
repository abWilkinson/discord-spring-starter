package abwilkinson.discordspringstarter;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class BaseSpringBootTest {
	@MockBean
	protected JDA jda;

	protected MessageReceivedEvent getMockEvent(String rawMessage) {
		Message mockMessage = mock(Message.class);
		MessageChannel channel = mock(MessageChannel.class);
		MessageAction messageAction = mock(MessageAction.class);

		//user
		User user = mock(User.class);
		when(user.isBot()).thenReturn(false);
		when(user.getIdLong()).thenReturn(1l);
		//message content
		when(mockMessage.getContentRaw()).thenReturn(rawMessage);

		MessageReceivedEvent event = mock(MessageReceivedEvent.class);
		when(event.getAuthor()).thenReturn(user);
		when(event.getMessage()).thenReturn(mockMessage);
		when(event.getChannel()).thenReturn(channel);
		when(channel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);
		when(channel.sendMessage(any(String.class))).thenReturn(messageAction);
		when(messageAction.submit()).thenReturn(new CompletableFuture<>());



		return event;
	}
}
