package abwilkinson.discordspringstarter.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Used in spring.factories to enable beans used in library
 */
@Configuration
@EnableConfigurationProperties()
@ComponentScan(basePackages = { "abwilkinson.*" })
public class SpringStarterConfig {
}
