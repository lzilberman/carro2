package rentcar.carro.accmanagement.dto;

import lombok.*;
import rentcar.carro.dto.RegExprForValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CredentialsDto {
    @NotNull(message = "name can not be empty")
    @Pattern(regexp = RegExprForValidator.LETTERS, message = "you can use only letters in the username")
    String username;
    @NotNull(message = "password can not be empty")
    String password;
}
