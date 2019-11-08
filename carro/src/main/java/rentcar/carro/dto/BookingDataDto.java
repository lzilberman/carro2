package rentcar.carro.dto;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class BookingDataDto {
	String carNumber;
	Integer dayPrice;
	Long startDateTime;
	Long endDateTime;	
	String user; 
	
	private Long getBookingDays() {
		return ChronoUnit.DAYS.between(new Timestamp(startDateTime).toLocalDateTime(), 
				                       new Timestamp(endDateTime).toLocalDateTime()); 
	}
	public double getAmount() {
		return (double) (this.dayPrice * this.getBookingDays());
	}
	
}
