package rentcar.carro.accmanagement.entities;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import rentcar.carro.accmanagement.dto.UserDetails;
import lombok.*;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Document (collection = "accounts")
public class AccountCarro {

	@Id
	String email;
	String password;
	Set<String> roles;
	LocalDate date;
	UserDetails details;	
	
	public AccountCarro(String email, String password, Set<String> roles, UserDetails details) {
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.details = details;
	}
	
}
