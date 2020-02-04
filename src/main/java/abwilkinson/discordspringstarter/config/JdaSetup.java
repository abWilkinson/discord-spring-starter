package abwilkinson.discordspringstarter.config;

import abwilkinson.discordspringstarter.config.properties.JdaProperties;
import abwilkinson.discordspringstarter.events.MessageListener;
import abwilkinson.discordspringstarter.events.ReadyEventListener;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;

/**
 * Configuration for setting up JDA & discord connection.
 */
@Slf4j
@Configuration
public class JdaSetup {

	private String shutdownUrl;

	private String adminUsername;

	private String adminPassword;

	private RestTemplateBuilder restTemplateBuilder;

	public JdaSetup(@Value("${app.shutdownUrl:#{null}}") String shutdownUrl, @Value("${security.admin.username:#{null}}") String adminUsername,
					@Value("${security.admin.password:#{null}}") String adminPassword, RestTemplateBuilder restTemplateBuilder) {
		this.shutdownUrl = shutdownUrl;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.restTemplateBuilder = restTemplateBuilder;
	}

	@Bean
	public JDA setupJda(JdaProperties properties,  MessageListener messageListener) throws LoginException {
		if (shutdownUrl != null && adminUsername != null && adminPassword != null) {
			RestTemplate restTemplate = restTemplateBuilder.basicAuthentication(adminUsername, adminPassword).build();
			try {
				restTemplate.postForEntity(shutdownUrl, null, ResponseEntity.class);
				log.info("Existing bot shutdown call complete");
			} catch (Exception e) {
				log.warn("Failed to shutdown existing bot.", e);
			}
		}
		return createJda(properties, messageListener);
	}

	private JDA createJda(JdaProperties properties,  MessageListener messageListener) throws LoginException {
		return new JDABuilder(properties.getSecret())
				.addEventListeners(new ReadyEventListener())
				.addEventListeners(messageListener)
				.build();
	}

}
