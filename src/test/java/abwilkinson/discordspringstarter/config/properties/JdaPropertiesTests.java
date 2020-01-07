package abwilkinson.discordspringstarter.config.properties;

import abwilkinson.discordspringstarter.BaseSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdaPropertiesTests extends BaseSpringBootTest {

    @Autowired
    private JdaProperties properties;

    @Test
    public void testJdaSecretIsSet() {
        assertEquals("test", properties.getSecret());
    }
}
