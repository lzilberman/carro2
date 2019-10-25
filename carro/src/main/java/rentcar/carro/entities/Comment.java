package rentcar.carro.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Comment {
	String carNumber;
	String firstName;
	String lastName;
	@JsonFormat(pattern="yyyy-MM-dd")
	LocalDate postDate;
	String post;
	Integer rating;
	
	public Comment(String carNumber, String firstName, String lastName, LocalDate postDate, String post) {
		this.carNumber = carNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.postDate = postDate;
		this.post = post;
	}
		
}
