package abwilkinson.discordspringstarter.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * BotProperties
 *
 * Configuration properties for the the main bot application
 */
@ConfigurationProperties("bot")
@Component
@Getter
@Setter
public class BotProperties {
    private String helpCommand = "help";
}
