package abwilkinson.discordspringstarter.config;

import abwilkinson.discordspringstarter.config.properties.JdaProperties;
import abwilkinson.discordspringstarter.events.MessageListener;
import abwilkinson.discordspringstarter.events.ReadyEventListener;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

/**
 * Configuration for setting up JDA & discord connection.
 */
@Slf4j
@Configuration
public class JdaSetup {

	@Bean
	public JDA setupJda(JdaProperties properties, MessageListener messageListener) throws LoginException {
		return createJda(properties, messageListener);
	}

	private JDA createJda(JdaProperties properties,  MessageListener messageListener) throws LoginException {
		return new JDABuilder(properties.getSecret())
				.addEventListeners(new ReadyEventListener())
				.addEventListeners(messageListener)
				.build();
	}

}
