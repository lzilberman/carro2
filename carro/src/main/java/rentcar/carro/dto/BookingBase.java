package rentcar.carro.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BookingBase {
	String regNumber;
	Long startDateTime;
	Long endDateTime;	
}
