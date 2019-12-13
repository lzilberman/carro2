package rentcar.carro.dto;

import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static rentcar.carro.dto.RegExprForValidator.NUMBER_AND_SPACE;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class ConfirmPaymentDto {

    @NotNull
    @Size(min = 0, max = 9)
    @Pattern(regexp = NUMBER_AND_SPACE)
    String carNumber;
    @NotNull(message = "order number can not be empty")
    String orderNumber;
//    @AssertTrue
    boolean paymentConfirmed;

    public String getCarNumber() {
        return carNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public boolean isPaymentConfirmed() {
        return paymentConfirmed;
    }
}
