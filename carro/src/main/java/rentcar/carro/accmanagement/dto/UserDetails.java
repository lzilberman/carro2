package rentcar.carro.accmanagement.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDetails {
	String email; // id
    String firstName;
    String lastName;
    String   phone;
}
