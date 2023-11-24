package contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import consumer.api.model.downstream.ConversionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {

    @Pact(provider = "provider_service", consumer = "consumer_service")
    public RequestResponsePact createPact(PactDslWithProvider builder) throws JsonProcessingException {

        Map<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        ConversionRequest requestEntity = ConversionRequest.builder()
                .amount(100.00)
                .desiredCurrency("EURO")
                .currentCurrency("GBP")
                .build();

        return builder
                .uponReceiving("ExampleJavaConsumerPactTest test interaction")
                .path("/v1/convert")
                .method("POST")
                .body(new ObjectMapper().writeValueAsString(requestEntity))
                .headers(headers)
                .willRespondWith()
                .headers(headers)
                .body("{\"funds\":\"€115.00\"}")
                .status(200)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "provider_service")
    public void test(MockServer mockServer) {
        ConversionRequest requestEntity = ConversionRequest.builder()
                .amount(100.00)
                .desiredCurrency("EURO")
                .currentCurrency("GBP")
                .build();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.postForEntity(mockServer.getUrl() + "/v1/convert", requestEntity, Object.class);

        System.out.println(response);

    }


}
