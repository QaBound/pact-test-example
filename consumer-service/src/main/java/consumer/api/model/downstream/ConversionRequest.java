package consumer.api.model.downstream;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRequest {


    private double amount;
    private String desiredCurrency;
    private String currentCurrency;

}
