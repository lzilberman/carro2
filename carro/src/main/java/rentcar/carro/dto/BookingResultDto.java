package rentcar.carro.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BookingResultDto {

	String carNumber;
	String orderNumber;
	Long bookingDateTime;
	Double amount;
}
