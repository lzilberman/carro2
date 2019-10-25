package rentcar.carro.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import lombok.*;

import rentcar.carro.entities.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class CarDto {
	
	  public CarDto(String regNumber, String owner) {
		this.regNumber = regNumber;
		this.owner = owner;
	}

	  @Id
	  String regNumber;
	  String owner; //email OR User owner
	  
	  // Location
	  String hostCity;
	  Address address;
	  Point location; // [ longitude, latitude ]
	  
	  // Car details	  
	  String make;
	  String model;
	  Integer year;
	  Double engine;
	  String fuel;
	  String transmission;
	  String wheelsDrive;
	  Integer doors;
	  Integer seats;
	  String carClass;
	  Double fuelConsumption;
	  
	  // Feachers&Price&Images
	  String [] features;
	  
	  Integer dayPrice;  
	  Double distanceIncluded;
	  
	  String [] imageUrl;  	  
}
