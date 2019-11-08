package rentcar.carro.entities;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.google.common.collect.Range;
import org.bson.types.ObjectId;

import lombok.*;
import rentcar.carro.dto.BookingDataDto;
import rentcar.carro.dto.BookingResultDto;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class Booking {
	
	private static int idcounter = 0;
	String orderNumber;
	
	Long startDateTime;
	Long endDateTime;
	Long bookingDateTime;
	
	Boolean paymentConfirmed;
	Double  amount;
	
	String carNumber;
	String user; // User user

	public Booking(BookingDataDto dto) {
		this.carNumber = dto.getCarNumber();
		this.startDateTime = dto.getStartDateTime();
		this.endDateTime = dto.getEndDateTime();
		this.amount = dto.getAmount();
		this.user = dto.getUser();
		this.bookingDateTime = Timestamp.valueOf(LocalDateTime.now()).getTime();
		this.orderNumber = generateOrderNumber(this.bookingDateTime);
		this.paymentConfirmed = false;
	}

	private String generateOrderNumber(Long timestamp) {
		ObjectId objectId = ObjectId.createFromLegacyFormat(
				                         timestamp.intValue(), 
				                         ObjectId.getGeneratedMachineIdentifier(), 
				                         ++idcounter);
		return objectId.toString();
	}
	public Range<Long> getBookingRange() {
		return Range.closed(startDateTime, endDateTime);
	}
	public BookingResultDto getBookingResult() {
		return new BookingResultDto(carNumber, orderNumber, bookingDateTime, amount);
	}
	public String getBookingPeriod() {
		LocalDateTime start = new Timestamp(startDateTime).toLocalDateTime();
		LocalDateTime end = new Timestamp(endDateTime).toLocalDateTime();
		
		return "from " + start.toString() + " to " + end.toString();
	}

}
