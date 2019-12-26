package abwilkinson.discordspringstarter.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JdaProperties
 *
 * Configuration properties for the JDA library
 */
@ConfigurationProperties("jda")
@Component
@Getter
@Setter
public class JdaProperties {
	private String secret;
}
