package rentcar.carro.accmanagement.dto;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountDto {
	@Email
	String email;
	@NotNull(message = "password can not be empty")
	String password;
	@NotNull(message = "roles can not be empty")
	String [] roles;
      
	UserDetails details;

	public void setUserDetails (UserDetails details) {
		this.details = details;
	}   
    
}

