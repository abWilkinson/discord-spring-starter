package abwilkinson.discordspringstarter.events;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReadyEventListenerTests {
    @Mock
    private Appender<ILoggingEvent> mockAppender;
    private ReadyEventListener readyEventListener = new ReadyEventListener();

    @BeforeEach
    public void setUp() {
        Logger logger = (Logger) LoggerFactory.getLogger(ReadyEventListener.class.getName());
        logger.addAppender(mockAppender);
    }

    @Test
    public void testReadyEventTriggersLogMessage() {
        Event event = mock(ReadyEvent.class);
        readyEventListener.onEvent(event);
        verify(mockAppender).doAppend(ArgumentMatchers.argThat(argument -> {
            assertThat(argument.getMessage(), containsString("Bot is ready!"));
            return true;
        }));
    }

    @Test
    public void testOtherEventsDontTriggerMessage() {
        Event event = mock(Event.class);
        readyEventListener.onEvent(event);
        verify(mockAppender, never()).doAppend(Mockito.any());
    }
}
