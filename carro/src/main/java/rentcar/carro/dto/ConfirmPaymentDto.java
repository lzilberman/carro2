package rentcar.carro.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class ConfirmPaymentDto {

	String carNumber;
	String orderNumber;
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
