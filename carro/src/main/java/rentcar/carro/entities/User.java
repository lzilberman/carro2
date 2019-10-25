package rentcar.carro.entities;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@NoArgsConstructor
@Getter
@Builder
@ToString
public class User {
	@Id
	String email;
	String firstName;
	String lastName;
	@JsonFormat(pattern="yyyy-MM-dd")
	LocalDate regDate;
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public User(String email, String firstName, String lastName, LocalDate regDate) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.regDate = regDate;
	}
}
