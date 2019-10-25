package rentcar.carro.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class ConfirmPaymentDto {

	String carNumber;
	Long orderNumber;
	boolean paymentConfirmed;
	
	public String getCarNumber() {
		return carNumber;
	}
	public Long getOrderNumber() {
		return orderNumber;
	}
	public boolean isPaymentConfirmed() {
		return paymentConfirmed;
	}	
}
