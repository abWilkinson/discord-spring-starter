package abwilkinson.discordspringstarter.events.service;


import abwilkinson.discordspringstarter.events.InputValues;
import abwilkinson.discordspringstarter.events.help.GenericHelpProvider;
import abwilkinson.discordspringstarter.events.help.HelpContext;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testHelpContextsCalledUntilActioned() {
        HelpContext context1 = mock(HelpContext.class);
        HelpContext context2 = mock(HelpContext.class);
        HelpContext context3 = mock(HelpContext.class);

        when(context1.helpCommandReceived(any(MessageReceivedEvent.class), any(InputValues.class))).thenReturn(false);
        when(context2.helpCommandReceived(any(MessageReceivedEvent.class), any(InputValues.class))).thenReturn(true);
        when(context3.helpCommandReceived(any(MessageReceivedEvent.class), any(InputValues.class))).thenReturn(false);

        InputValues values = new InputValues("help something");
        List<HelpContext> contexts = new ArrayList<>();
        contexts.add(context1);
        contexts.add(context2);
        contexts.add(context3);

        HelpService service = new HelpService(contexts, null);
        service.callHelpContexts(mock(MessageReceivedEvent.class), values);
        verify(context1, times(1)).helpCommandReceived(any(MessageReceivedEvent.class), any(InputValues.class));
        verify(context2, times(1)).helpCommandReceived(any(MessageReceivedEvent.class), any(InputValues.class));
        verify(context3, times(0)).helpCommandReceived(any(MessageReceivedEvent.class), any(InputValues.class));

    }

    @Test
    public void testGenericHelpNotCalledAsFallbackWhenNotImplemented() {
        HelpContext context = mock(HelpContext.class);
        when(context.helpCommandReceived(any(MessageReceivedEvent.class), any(InputValues.class))).thenReturn(false);
        InputValues values = new InputValues("help something");
        List<HelpContext> contexts = new ArrayList<>();
        contexts.add(context);
        HelpService service = new HelpService(contexts, null);
        service.callHelpContexts(mock(MessageReceivedEvent.class), values);
        verify(context, times(1)).helpCommandReceived(any(MessageReceivedEvent.class), any(InputValues.class));
    }

}
