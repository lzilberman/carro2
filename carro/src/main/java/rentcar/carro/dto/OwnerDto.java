package rentcar.carro.dto;

import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OwnerDto {
    String first_name;
    String second_name;
    //    TODO Date - rightFormate
    Date registration_date;
}
