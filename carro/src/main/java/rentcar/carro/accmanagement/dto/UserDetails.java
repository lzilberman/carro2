package rentcar.carro.accmanagement.dto;

import lombok.*;
import rentcar.carro.dto.RegExprForValidator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDetails {

    @Email
    String email; // id
    @NotNull(message = "name can not be empty")
    String firstName;
    @Pattern(regexp = RegExprForValidator.LETTERS)
    @NotNull(message = "family can not be empty")
    @Pattern(regexp = RegExprForValidator.LETTERS)
    String lastName;
    @NotNull(message = "phone can not be empty")
    @Pattern(regexp = RegExprForValidator.NUMBER_AND_SPACE)
    String phone;
}
