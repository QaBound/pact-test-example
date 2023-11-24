package provider.api.model.upstream;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConversionRequest {

    private double amount;
    private String desiredCurrency;
    private String currentCurrency;

}
