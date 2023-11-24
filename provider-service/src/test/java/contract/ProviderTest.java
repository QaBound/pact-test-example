package contract;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.context.WebApplicationContext;
import provider.api.Controller;
import provider.api.SecurityConfig;

import java.net.MalformedURLException;
import java.net.URL;


@Provider("provider_service")
@EnableAutoConfiguration
@PactBroker(host = "localhost", scheme = "http", port = "9292")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SecurityConfig.class, Controller.class})
@SpringBootConfiguration
public class ProviderTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @Before
    void allowPublishing() {


    }

    @BeforeEach
    void before(PactVerificationContext context) throws MalformedURLException {
        System.setProperty("pact.verifier.publishResults", "true");
        System.setProperty("pact.provider.version", "latest");
        context.setTarget(HttpTestTarget.fromUrl(new URL("http://localhost:" + port)));
        // or something like
        // context.setTarget(new HttpTestTarget("localhost", myProviderPort, "/"));
    }

}
