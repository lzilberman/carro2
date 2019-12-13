package rentcar.carro.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

import static rentcar.carro.dto.RegExprForValidator.LETTERS;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OwnerDto {
    @NotNull(message = "name can not be empty")
    @Pattern(regexp = LETTERS)
    String first_name;

    @NotNull(message = "family can not be empty")
    @Pattern(regexp = LETTERS)
    String second_name;
    //    TODO Date - rightFormate
    @Past
    Date registration_date;
}
