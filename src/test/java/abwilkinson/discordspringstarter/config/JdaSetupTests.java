package abwilkinson.discordspringstarter.config;


import abwilkinson.discordspringstarter.config.properties.JdaProperties;
import abwilkinson.discordspringstarter.events.MessageListener;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JdaSetupTests {
    private final JdaProperties properties = new JdaProperties();
    private RestTemplateBuilder restTemplateBuilder;

    public JdaSetupTests() {
        properties.setSecret("123");
        restTemplateBuilder = createMockRestTemplateBuilder();
    }

    private RestTemplateBuilder createMockRestTemplateBuilder() {
        restTemplateBuilder = mock(RestTemplateBuilder.class);
        when(restTemplateBuilder.basicAuthentication(anyString(), anyString())).thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(mock(RestTemplate.class));
        return restTemplateBuilder;
    }

    @Test
    public void testShutdownCalledWhenValuesProvided() {
        JdaSetup jdaSetup = new JdaSetup("http://test", "test", "test", restTemplateBuilder);
        doCreateJda(jdaSetup);
        verify(restTemplateBuilder, times(1)).build();
    }

    @Test
    public void testErrorsFromShutdownDoNotEffectApplication() {
        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        RestTemplateBuilder mockRestTemplateBuilder = createMockRestTemplateBuilder();
        when(mockRestTemplateBuilder.build()).thenReturn(mockRestTemplate);
        when(mockRestTemplate.postForEntity("http://test", null, ResponseEntity.class)).thenThrow(new RuntimeException("An error"));
        JdaSetup jdaSetup = new JdaSetup("http://test", "test", "test", restTemplateBuilder);
        Assertions.assertThatCode(() -> doCreateJda(jdaSetup))
                .doesNotThrowAnyException();
        verify(restTemplateBuilder, times(1)).build();

    }


    @Test
    public void testJdaCreatedWithNoShutdownValues() {
        JdaSetup jdaSetup = new JdaSetup(null, null, null, restTemplateBuilder);
        doCreateJda(jdaSetup);
        verify(restTemplateBuilder, never()).build();
    }

    @Test
    public void testShutdownNotCalledWithMissingUrl() {
        JdaSetup jdaSetup = new JdaSetup(null, "test", "test", restTemplateBuilder);
        doCreateJda(jdaSetup);
        verify(restTemplateBuilder, never()).build();
    }

    @Test
    public void testShutdownNotCalledWithMissingUsername() {
        JdaSetup jdaSetup = new JdaSetup("test", null, "test", restTemplateBuilder);
        doCreateJda(jdaSetup);
        verify(restTemplateBuilder, never()).build();
    }

    @Test
    public void testShutdownNotCalledWithMissingPassword() {
        JdaSetup jdaSetup = new JdaSetup("test", "test", null, restTemplateBuilder);
        doCreateJda(jdaSetup);
        verify(restTemplateBuilder, never()).build();
    }


    private void doCreateJda(JdaSetup jdaSetup) {
        try {
            jdaSetup.setupJda(properties, mock(MessageListener.class));
        } catch (LoginException e) {
            //expected
        }
    }
}
