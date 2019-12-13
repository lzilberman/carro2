package rentcar.carro.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import lombok.*;

import rentcar.carro.entities.*;

import javax.validation.constraints.*;

import java.time.LocalDate;

import static rentcar.carro.dto.RegExprForValidator.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class CarDto {


    public CarDto(String regNumber, String owner) {
        this.regNumber = regNumber;
        this.owner = owner;
    }

    @Id
    @NotNull
    @Size(min = 0, max = 9)
    @Pattern(regexp = NUMBER_AND_SPACE)
    String regNumber;
    @NotNull
    String owner; //email OR User owner
    // Location
    @NotNull(message = "city can not be empty")
    @Pattern(regexp = NUMBERS_LETTERS, message = "you can use only numbers and letters")
    String hostCity;
    Address address;
    @NotNull(message = "location can not be empty")
    Point location; // [ longitude, latitude ]
    // Car details
    @NotNull(message = "make can not be empty")
    @Pattern(regexp = NUMBERS_LETTERS, message = "you can use only numbers, letters ")
    String make;
    @NotNull(message = "model can not be empty")
    String model;
    @NotNull(message = "year can not be empty")
    @Min(value = 1900)    //@Pattern(regexp = RegExprForValidator.NUMBERS, message = "year can contains only numbers")
            Integer year;
    @NotNull(message = "engine can not be empty")
    Double engine;
    @NotNull(message = "fuel can not be empty")
    String fuel;
    @NotNull(message = "transmission can not be empty")
    @Pattern(regexp = RegExprForValidator.LETTERS, message = "you can use only letter int the transmission")
    String transmission;
    @NotNull(message = "wheelsDrive can not be empty")
    @Pattern(regexp = BIG_LETTERS, message = "you can use only big letter int the wheelsDrive")
    String wheelsDrive;
    @Max(value = 7)
    Integer doors;
    @Max(value = 36)
    Integer seats;
    @Pattern(regexp = LETTERS)
    String carClass;
    Double fuelConsumption;
    // Feachers&Price&Images
    String[] features;
    @NotNull(message = "price can not be empty")
    Integer dayPrice;
    @NotNull(message = "distance can not be empty")
    Double distanceIncluded;
    String[] imageUrl;
}
