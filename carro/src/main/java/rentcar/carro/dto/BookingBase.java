package rentcar.carro.dto;

import lombok.*;

import javax.validation.constraints.*;

import static rentcar.carro.dto.RegExprForValidator.NUMBER_AND_SPACE;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BookingBase {
    @NotNull
    @Size( max = 9)
    @Pattern(regexp = NUMBER_AND_SPACE)
    String regNumber;
    @NotNull
//    @Past
    Long startDateTime;
    @NotNull
//    @Future
    Long endDateTime;
}
