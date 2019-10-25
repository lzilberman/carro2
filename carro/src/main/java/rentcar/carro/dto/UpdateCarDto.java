package rentcar.carro.dto;


import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import lombok.*;
import rentcar.carro.entities.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UpdateCarDto {
	
	  public UpdateCarDto(String regNumber) {
		this.regNumber = regNumber;
	}

	  @Id
	  String regNumber;
	  
	  // Location
	  String hostCity;
	  Address address;
	  Point location;
	  
	  // Feachers&Price&Images
	  String [] features;
	  
	  Integer dayPrice;	  
	  Double distanceIncluded;
	  
	  String [] imageUrl;
	  
}
