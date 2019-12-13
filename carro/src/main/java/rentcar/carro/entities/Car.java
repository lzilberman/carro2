package rentcar.carro.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.geo.Point;
import com.google.common.collect.Range;
import lombok.*;
import rentcar.carro.dto.*;
import rentcar.carro.exception.ObjectNotFoundException;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Document("cars")
public class Car {
	
	  @Id
	  String regNumber;
	  // https://stackoverflow.com/questions/42292359/spring-data-mongodb-not-null-annotation-like-spring-data-jpa
	  @NotNull(message = "Car's owner must not be null")
	  String owner; //email OR User owner
	  
	  // Location
	  @NotNull(message = "Car's host city must not be null")
	  String hostCity;
	  Address address;
	  Point location;   // [ longitude, latitude ]
	  
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
	  @NotNull(message = "Car's day price doesn't defined")
	  Integer dayPrice;
	  
	  Double distanceIncluded;
	  String [] imageUrl;
	  
	  // Bookings
	  List<Booking> bookings;
	  List<BookingBase> bookingPeriods; // simplified booking presentation
	  // Comments
	  List<Comment> comments;

	  public Car() {
		  this.bookings = new ArrayList<>();
		  this.bookingPeriods = new ArrayList<>();
		  this.comments = new ArrayList<>();
	  }
	  public Car(CarDto dto) {
			this.regNumber = dto.getRegNumber();
			this.owner = dto.getOwner();
			this.hostCity = dto.getHostCity();
			this.address = dto.getAddress();
			this.location = dto.getLocation();
			this.make = dto.getMake();
			this.model = dto.getModel();
			this.year = dto.getYear();
			this.engine = dto.getEngine();
			this.fuel = dto.getFuel();
			this.transmission = dto.getTransmission();
			this.wheelsDrive = dto.getWheelsDrive();
			this.doors = dto.getDoors();
			this.seats = dto.getSeats();
			this.carClass = dto.getCarClass();
			this.fuelConsumption = dto.getFuelConsumption();
			this.features = dto.getFeatures();
			this.dayPrice = dto.getDayPrice();
			this.distanceIncluded = dto.getDistanceIncluded();
			this.imageUrl = dto.getImageUrl();	
			
			this.bookings = new ArrayList<>();
			this.bookingPeriods = new ArrayList<>();
			this.comments = new ArrayList<>();
	  }
	  
	  private void createComments() {
		  if (this.comments == null)
			  this.comments = new ArrayList<>();
	  }	  
	  private void createBookings() {
		  if (this.bookings == null) {
			  this.bookings = new ArrayList<>();
			  this.bookingPeriods = new ArrayList<>();
		  }
	  }
	  
	  /*
	   * https://www.baeldung.com/guava-rangemap
	   * https://javabot.evanchooly.com/javadoc/guava/22.0/com/google/common/collect/Range.html
	   */	  
	  public boolean canBook(Range<Long> period) {
		    createBookings();
		    Long nowTime = Timestamp.valueOf(LocalDateTime.now()).getTime();
		    // if the period is in the past - no book
		    if (period.lowerEndpoint().compareTo(nowTime) <= 0) {
		    	return false;
		    }
		    boolean[] res = {true};
		    
		    bookings.stream()
		    		.filter(b->b.getStartDateTime().compareTo(nowTime) >= 0)
		            .map(b->b.getBookingRange())
		            .peek(range -> {if (range.isConnected(period)) res[0] = false;})
		            .collect(Collectors.toList());
		    
			return res[0];
	  }

	  public void confirmPaymemt(ConfirmPaymentDto dto) {
		 Booking order = bookings.stream()
				 .filter(b->b.getOrderNumber() == dto.getOrderNumber())
				 .findFirst()
				 .orElse(null);
		 
		  if (order == null) {
			  throw new ObjectNotFoundException("Car " + dto.getCarNumber() + 
					  " booking order number " + dto.getOrderNumber() + " not found");
		  }
		  order.setPaymentConfirmed(dto.isPaymentConfirmed());
	  }
	  public Car addComment(Comment comment) {
		  createComments();
		  this.comments.add(comment);
		  return this;
	  }
	  public CarRatingDto getCarRating() {
		  Integer [] count = {0};
		  
		  Integer total  = comments.stream()
		  .filter(c->c.rating > 0)
		  .peek(c->++count[0])
		  .mapToInt(c->c.rating)
		  .reduce(0, (result, current)-> result+current);
		  
		  return count[0]==0? null : new CarRatingDto(Math.round(total/count[0]), count[0] );
	  }
	  	  
}
