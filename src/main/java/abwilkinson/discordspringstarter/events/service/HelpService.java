package abwilkinson.discordspringstarter.events.service;

import abwilkinson.discordspringstarter.events.InputValues;
import abwilkinson.discordspringstarter.events.help.GenericHelpProvider;
import abwilkinson.discordspringstarter.events.help.HelpContext;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Service which will call through any handlers registered with the HelpContext interface.
 */
@Service
public class HelpService {
    private List<HelpContext> helpContexts;
    private GenericHelpProvider genericHelpProvider;

    public HelpService(@Nullable List<HelpContext> helpContexts, @Nullable GenericHelpProvider genericHelpProvider) {
        this.helpContexts = helpContexts;
        this.genericHelpProvider = genericHelpProvider;
    }

    public void callHelpContexts(MessageReceivedEvent event, InputValues inputValues) {
        if (StringUtils.isEmpty(inputValues.getParameter())) {
            if (genericHelpProvider != null) {
                genericHelpProvider.handleHelpCommand(event, inputValues);
            }
        } else {
            checkSpecificHelpContexts(event, inputValues);
        }
    }

    private void checkSpecificHelpContexts(MessageReceivedEvent event, InputValues inputValues) {
        boolean handlerCalled = false;
        if (helpContexts != null) {
            handlerCalled = helpContexts.stream().anyMatch(context -> context.helpCommandReceived(event, inputValues));
        }
        if (!handlerCalled) {
            genericHelpProvider.handleHelpCommand(event, inputValues);
        }
    }

}
