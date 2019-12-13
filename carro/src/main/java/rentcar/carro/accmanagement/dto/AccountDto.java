package rentcar.carro.accmanagement.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountDto {
	String email;
	String password;
	String [] roles;
      
	UserDetails details;

	public void setUserDetails (UserDetails details) {
		this.details = details;
	}   
    
}

