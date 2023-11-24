package provider.api;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import provider.api.model.upstream.ConversionRequest;

import java.util.Arrays;

@RestController
@CrossOrigin(origins = "http://localhost:*")
@Log4j2
public class Controller {


    @PostMapping(path = "/v1/convert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object convert(@RequestBody ConversionRequest bankBalanceRequest) {
        log.info(bankBalanceRequest);
        SupportedCurrency supportedCurrency;
        try {
            supportedCurrency = SupportedCurrency.getConversionInfo(bankBalanceRequest.getCurrentCurrency(), bankBalanceRequest.getDesiredCurrency());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        String value = supportedCurrency.symbol + (supportedCurrency.rate * bankBalanceRequest.getAmount());
        return ResponseEntity.ok().body("{\"funds\":\"" + value + "\"}");


    }


    private enum SupportedCurrency {
        //Rate hardcoded for demo purposes
        GBP_TO_EURO("GBP", "EURO", 1.15, "€"),
        GBP_TO_DOLLAR("GBP", "USD", 1.25, "$");

        private final String currentCurrency;
        private final String desiredCurrency;
        private final double rate;
        private final String symbol;

        SupportedCurrency(String currentCurrency, String desiredCurrency, double rate, String symbol) {
            this.currentCurrency = currentCurrency;
            this.desiredCurrency = desiredCurrency;
            this.rate = rate;
            this.symbol = symbol;
        }

        public static SupportedCurrency getConversionInfo(String currentCurrency, String desiredCurrency) {
            return Arrays.stream(SupportedCurrency.values())
                    .filter(r -> r.currentCurrency.equalsIgnoreCase(currentCurrency) && r.desiredCurrency.equalsIgnoreCase(desiredCurrency))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Conversion not supported"));
        }
    }


}
