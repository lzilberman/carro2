package rentcar.carro.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BookingResultDto {

	String carNumber;
	Long orderNumber;
	Long bookingDateTime;
	Double amount;
}
