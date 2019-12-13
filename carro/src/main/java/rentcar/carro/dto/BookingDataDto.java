package rentcar.carro.dto;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;

import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class BookingDataDto {

    @NotNull
    @Size( max = 9)
    @Pattern(regexp = RegExprForValidator.NUMBER_AND_SPACE, message = "car number not valid")
    String carNumber;

    @NotNull(message = "price can not be empty")
    Integer dayPrice;

    @NotNull(message = "date of start booking can not be empty")

    Long startDateTime;
    @NotNull(message = "date of end booking can not be empty")

    Long endDateTime;
    @NotNull(message = "user ")

    String user;

    public BookingDataDto(String carNumber, Long startDateTime, Long endDateTime) {
        this.carNumber = carNumber;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private Long getBookingDays() {
        return ChronoUnit.DAYS.between(new Timestamp(startDateTime).toLocalDateTime(),
                new Timestamp(endDateTime).toLocalDateTime());
    }

    public double getAmount() {
        return (double) (this.dayPrice * this.getBookingDays());
    }

}
