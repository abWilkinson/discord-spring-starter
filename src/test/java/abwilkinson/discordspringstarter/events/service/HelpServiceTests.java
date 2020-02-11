package abwilkinson.discordspringstarter.events.service;


import abwilkinson.discordspringstarter.events.InputValues;
import abwilkinson.discordspringstarter.events.help.GenericHelpProvider;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.Input;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HelpServiceTests {

    @Test
    public void testNoImplementationsAreHandled() {
        HelpService service = new HelpService(null, null);
        Assertions.assertDoesNotThrow(
                ()->  service.callHelpContexts(mock(MessageReceivedEvent.class), mock(InputValues.class))
        );
    }

    @Test
    public void testGenericHelpCalledWhenNoParameter() {
        GenericHelpProvider provider = mock(GenericHelpProvider.class);
        HelpService service = new HelpService(null, provider);
        service.callHelpContexts(mock(MessageReceivedEvent.class), mock(InputValues.class));
        verify(provider, times(1)).handleHelpCommand(any(MessageReceivedEvent.class), any(InputValues.class));
    }

    @Test
    public void testGenericHelpCalledIfNoOthersActioned() {
        GenericHelpProvider provider = mock(GenericHelpProvider.class);
        InputValues values = new InputValues("help something");
        HelpService service = new HelpService(null, provider);
        service.callHelpContexts(mock(MessageReceivedEvent.class), values);
        verify(provider, times(1)).handleHelpCommand(any(MessageReceivedEvent.class), any(InputValues.class));
    }
}
