package rentcar.carro.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class BookingDataDto {
	String carNumber;
	Long startDateTime;
	Long endDateTime;
	
	String user; 
	public String getBookingPeriod() {
		LocalDateTime start = new Timestamp(startDateTime).toLocalDateTime();
		LocalDateTime end = new Timestamp(endDateTime).toLocalDateTime();
		
		return "from " + start.toString() + " to " + end.toString();
	}
}
