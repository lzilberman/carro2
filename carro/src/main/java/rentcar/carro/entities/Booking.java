package rentcar.carro.entities;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.data.annotation.Transient;
import com.google.common.collect.Range;

import lombok.*;
import rentcar.carro.dto.BookingDataDto;
import rentcar.carro.dto.BookingResultDto;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class Booking {
	public Booking(BookingDataDto dto) {
		this.carNumber = dto.getCarNumber();
		this.startDateTime = dto.getStartDateTime();
		this.endDateTime = dto.getEndDateTime();
		this.user = dto.getUser();
		this.SEQUENCE_NAME = "bookings_sequence_" + this.carNumber;
		this.bookingDateTime = Timestamp.valueOf(LocalDateTime.now()).getTime();
		paymentConfirmed = false;
	}
	
	@Transient
    public String SEQUENCE_NAME; 
	Long orderNumber;
	
	Long startDateTime;
	Long endDateTime;
	Long bookingDateTime;
	
	Boolean paymentConfirmed;
	Double  amount;
	
	String carNumber;
	String user; // User user
	
	private Long getBookingDays() {
		return ChronoUnit.DAYS.between(new Timestamp(startDateTime).toLocalDateTime(), 
				                       new Timestamp(endDateTime).toLocalDateTime()); 
	}
	public Range<Long> getBookingRange() {
		return Range.closed(startDateTime, endDateTime);
	}
	public void setAmount(Integer dayPrice) {
		this.amount  = (double) (dayPrice * this.getBookingDays());
	}
	public BookingResultDto getBookingResult() {
		return new BookingResultDto(carNumber, orderNumber, bookingDateTime, amount);
	}
}
