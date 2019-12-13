package rentcar.carro.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BookingResultDto {


	@NotNull
	@Size(max = 9)
	@Pattern(regexp = RegExprForValidator.NUMBER_AND_SPACE)
	String carNumber;
	@NotNull(message = "order can not be empty")
	String orderNumber;
	@NotNull(message = "booking date can not be empty")
	Long bookingDateTime;
	@NotNull(message = "amount can not be empty")
	Double amount;
}
