package rentcar.carro.accmanagement.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CredentialsDto {
    String username;
    String password;
}
