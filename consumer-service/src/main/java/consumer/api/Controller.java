package consumer.api;

import consumer.api.model.downstream.ConversionRequest;
import consumer.api.model.upstream.BankBalanceRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

    @Value("${downstreamservice.url}")
    private String conversionServiceUrl;

    private final double DEMO_DEFAULT_HARDCODED_VALUE = 101;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getBankBalance(@RequestBody BankBalanceRequest bankBalanceRequest) {
        String desiredCurrencyDisplay = bankBalanceRequest.getDisplayCurrency();
        double fundAvailableInGBP = getAvailableFunds();

        ConversionRequest requestEntity = ConversionRequest.builder()
                .amount(fundAvailableInGBP)
                .desiredCurrency(desiredCurrencyDisplay)
                .currentCurrency("GBP")
                .build();

        Object response = new RestTemplate().postForEntity(conversionServiceUrl, requestEntity, Object.class);
        return response;
    }


    public double getAvailableFunds() {
        return DEMO_DEFAULT_HARDCODED_VALUE;
    }
}
