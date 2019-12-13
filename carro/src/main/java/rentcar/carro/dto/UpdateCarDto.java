package rentcar.carro.dto;


import org.springframework.data.geo.Point;
import lombok.*;
import rentcar.carro.entities.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UpdateCarDto {

    public UpdateCarDto(String regNumber) {
        this.regNumber = regNumber;
    }

    @NotNull
    @Size(min = 0, max = 9)
    @Pattern(regexp = RegExprForValidator.NUMBER_AND_SPACE)

    String regNumber;

    // Location

    @Pattern(regexp = RegExprForValidator.NUMBERS_LETTERS, message = "you can use only numbers, letters, sign ' - ' and sign '_' ")

    String hostCity;

    Address address;

    Point location;

    // Feachers&Price&Images
    String[] features;
    Integer dayPrice;


    Double distanceIncluded;

    String[] imageUrl;

}
