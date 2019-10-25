
package rentcar.carro;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.lang.Boolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.*;

import rentcar.carro.dto.*;
import rentcar.carro.service.ICarroService;
import rentcar.carro.entities.*;

@SpringBootApplication
public class CarroApplication { // implements CommandLineRunner  {
//    @Autowired
//    ICarroService carroService;

	public static void main(String[] args) {
		SpringApplication.run(CarroApplication.class, args);
	}

//	@Override
	public void run(String... args) throws Exception {

		LocalDateTime dc1 = LocalDateTime.of(2019, 11, 4, 11, 10);
		LocalDateTime dc2 = LocalDateTime.of(2019, 11, 8, 11, 10);
		Long tc1 = Timestamp.valueOf(dc1).getTime();
		Long tc2 = Timestamp.valueOf(dc2).getTime();
		
		System.out.println("Start " + tc1 + " End " + tc2);
		
//=========GEO SEARCH==========================================			
//		SearchCriteriaDto sdata = new SearchCriteriaDto ();
//		Point point6 = new Point (34.798233, 31.958455);
//		sdata.setPoint (point6);
//		sdata.setMaxDistance (0.0015);
//
//		List<Car> cars = carroService.findBy(sdata);
//		cars.forEach (System.out::println);
//		System.out.println("===================================");
//
//	    SearchCriteriaDto sdata2 = new SearchCriteriaDto ();
//	    Box box6 = new Box (new Point (34.796342, 31.957405), 
//	                        new Point (34.799750, 31.959363));  
//	    sdata2.setBox (box6);
//
//	    List<Car> cars2 = carroService.findBy(sdata2);
//	    cars2.forEach (System.out::println);
//	    System.out.println("===================================");
//	}
//}	
//==========DETAILS SEARCH======================================	
//	
////		SearchCriteriaDto sdata = new SearchCriteriaDto("city6", null, null);
//		SearchCriteriaDto sdata = new SearchCriteriaDto(null, null, null);
//		
//		sdata.setMinPrice(35);
//		sdata.setMaxPrice(50);
//		sdata.setSortByPriceDirection(Sort.Direction.DESC);
//		sdata.setMake("maker6");	
//		sdata.setMinYear(2000);
//		sdata.setMaxYear(2018);
//		sdata.setMinEngineVolume(0.8);
//		sdata.setMaxEngineVolume(5.0);
//		sdata.setMinFuelConsumption(8.0);
//		sdata.setMaxFuelConsumption(14.0);
//		
//		List<Car> cars = carroService.findBy(sdata);
//		cars.forEach(System.out::println);
//	}
//}	
//==============COMMENTS & RATINGS======================		
//		
//		Comment comment1 = Comment.builder()
//				.carNumber("66-666-66")
//				.firstName("aaa6")
//				.lastName("bbb6")
//				.postDate(LocalDate.of(2019, 11, 5))
//				.post("I've rented from Nikola several times now. "
//					+ "Car is always clean and ready to go. Pick and "
//					+ "up drop off is always a breeze. Will definitely "
//					+ "be renting from Nikola again!")
//				.rating(4)
//				.build();
//		carroService.addCarComment(comment1);	
//		
//		Comment comment2 = Comment.builder()
//				.carNumber("66-666-66")
//				.firstName("ccc6")
//				.lastName("ddd6")
//				.postDate(LocalDate.of(2019, 10, 5))
//				.post("The car was not clean at all. Girlfriend and I went on a trip "
//						+ "and the car had dog hair in it and she’s allergic.")
//				.rating(3)
//				.build();
//		carroService.addCarComment(comment2);
//		
//		Comment comment3 = Comment.builder()
//				.carNumber("66-666-66")
//				.firstName("eee6")
//				.lastName("fff6")
//				.postDate(LocalDate.of(2019, 9, 5))
//				.post("I have now rented from Nikola 3 times, "
//						+ "and every time has been an absolute pleasure.")
//				.rating(5)
//				.build();
//		carroService.addCarComment(comment3);
//		
//		carroService.getCarComments("66-666-66")
//		.forEach(System.out::println);
//		System.out.println(carroService.getCarRating("66-666-66")); 	
//	}
//}			
//================ BOOKING API=================		
//		LocalDateTime da1 = LocalDateTime.of(2019, 11, 1, 11, 10);
//		LocalDateTime da2 = LocalDateTime.of(2019, 11, 5, 11, 10);
//		Long ta1 = Timestamp.valueOf(da1).getTime();
//		Long ta2 = Timestamp.valueOf(da2).getTime();
//		
//		BookingDataDto bookingData6 = BookingDataDto.builder()
//				.carNumber("66-666-66")
//				.startDateTime(ta1)
//				.endDateTime(ta2)
//				.user("USER6@gmail.com")
//				.build();
//
//		BookingDataDto bookingData7 = BookingDataDto.builder()
//				.carNumber("77-777-77")
//				.startDateTime(ta1)
//				.endDateTime(ta2)
//				.user("777@gmail.com")
//				.build();
//
//		
//		BookingResultDto res6 = carroService.makeReservation(bookingData6);
//		if (res6 == null) System.out.println("cannot book reservation");
//		else System.out.println("reservation: " + res6);
//
//		BookingResultDto res7 = carroService.makeReservation(bookingData7);
//		if (res7 == null) System.out.println("cannot book reservation");
//		else System.out.println("reservation: " + res7);
//============================================================
//		LocalDateTime db1 = LocalDateTime.of(2019, 11, 8, 11, 10); // (2019, 11, 4, 11, 10);
//		LocalDateTime db2 = LocalDateTime.of(2019, 11, 12, 11, 10); // (2019, 11, 8, 11, 10);
//		Long tb1 = Timestamp.valueOf(db1).getTime();
//		Long tb2 = Timestamp.valueOf(db2).getTime();
//		
//		BookingDataDto bookingData62 = BookingDataDto.builder()
//				.carNumber("66-666-66")
//				.startDateTime(tb1)
//				.endDateTime(tb2)
//				.user("USER62@gmail.com")
//				.build();
//
//		BookingResultDto res62 = carroService.makeReservation(bookingData62);
//		if (res62 == null) System.out.println("cannot book reservation for user USER62@gmail.com");
//		else System.out.println("reservation: " + res62);
//============================================================
//		LocalDateTime dc1 = LocalDateTime.of(2019, 11, 4, 11, 10);
//		LocalDateTime dc2 = LocalDateTime.of(2019, 11, 8, 11, 10);
//		Long tc1 = Timestamp.valueOf(dc1).getTime();
//		Long tc2 = Timestamp.valueOf(dc2).getTime();
//		
//		BookingDataDto bookingData63 = BookingDataDto.builder()
//				.carNumber("66-666-66")
//				.startDateTime(tc1)
//				.endDateTime(tc2)
//				.user("USER63@gmail.com")
//				.build();
//
//		BookingResultDto res63 = carroService.makeReservation(bookingData63);
//		if (res63 == null) System.out.println("cannot book reservation for user USER63@gmail.com");
//		else System.out.println("reservation: " + res63);	
//============================================================
//		carroService.getCarBookings("66-666-66")
//		.forEach(System.out::println);
//============================================================
//		ConfirmPaymentDto dto = new ConfirmPaymentDto("66-666-66", res62.getOrderNumber(), true);
//		carroService.confirmPayment(dto);		
//============================================================		
  }
}

//====================================================================================================================
/*
package rentcar.carro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.geo.Point;
import rentcar.carro.dto.CarDto;
import rentcar.carro.dto.UpdateCarDto;
import rentcar.carro.entities.*;
import rentcar.carro.service.ICarroService;

@SpringBootApplication
public class CarroApplication implements CommandLineRunner  {
    @Autowired
    ICarroService carroService;

	public static void main(String[] args) {
		SpringApplication.run(CarroApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		carroService.deleteCar("62-666-62");	
//		carroService.deleteCar("72-666-72");
//		
//		Point location6 = new Point(34.8047923, 31.912363);
//		Point location7 = new Point(34.797407, 	31.958893);
//		Point location62 = new Point(34.79944, 	31.959007);
//		Point location72 = new Point(34.79698, 31.957991);
//		
//		String[] image6 = new String[] {
//				"https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/b0r3mfIkQlGJwrSJabE8cg.1440x700.jpg",
//				"https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/IkG7bVDbR9-rv13KbsUG-g.1440x700.jpg"
//				};
//		String[] image7 = new String[] {
//				"https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/rRas_v7uQwqRfqhT8LTpKw.1440x700.jpg",
//				"https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/UD6wK_r_Tz2-EhgRzsgVqw.1440x700.jpg"
//				};
//		
//		String[] features6 = {"Reverse Camera", "Touch Screen Menu", "Side Camera"}; 
//		String[] features7 = {"Super", "Puper", "Features"}; 
//
//		Address address62 = Address.builder()
//		.country("Israel")
//		.city("city6")
//		.building(62)
//		.apartment(62)
//		.build();
//
//		Address address72 = Address.builder()
//		.country("Israel")
//		.city("city6")
//		.building(72)
//		.apartment(72)
//		.build();
//		
////		CarDto car6 = CarDto.builder()
//		CarDto car62 = CarDto.builder()
//				.regNumber("62-666-62")  
//				.owner("626@gmail.com")  
//				.hostCity("city6")       
//				.location(location62)     
//				.address(address62)
//				.make("maker6")          
//				.model("model62")         
//				.year(2011)               
//				.engine(2.0)
//				.fuel("gas")
//				.transmission("Manual")
//				.wheelsDrive("TWD")
//				.doors(4)
//				.seats(5)
//				.carClass("C")
//				.fuelConsumption(12.5)
//				.features(features6)         
//				.dayPrice(32)               
//				.distanceIncluded(0.45)
//				.imageUrl(image6)			
//				.build();
//		
////		CarDto car7 = CarDto.builder()
//		CarDto car72 = CarDto.builder()
//				.regNumber("77-777-77")  
//				.owner("727@gmail.com")  
//				.hostCity("city6")   
//				.address(address72)
//				.location(location72)     
//				.make("maker6")          
//				.model("model62")         
//				.year(2011)               
//				.engine(2.5)
//				.fuel("gas")
//				.transmission("Manual")
//				.wheelsDrive("TWD")
//				.doors(4)
//				.seats(5)
//				.carClass("D")
//				.fuelConsumption(15.2)
//				.features(features7)         
//				.dayPrice(54)               
//				.distanceIncluded(0.45)
//				.imageUrl(image7)			
//				.build();	
//		
//		Car res = carroService.addCar(car62);	
//		System.out.println(res);
//		
//		res = carroService.addCar(car72);	
//		System.out.println(res);
//========================================================
//		Address address6 = Address.builder()
//				.country("Israel")
//				.city("city6")
//				.building(6)
//				.apartment(6)
//				.build();
//		
//		UpdateCarDto car = UpdateCarDto.builder()
//				.regNumber("66-666-66")
//				.address(address6)
//				.build();
//		
//		Car res = carroService.updateCar(car);	
//		System.out.println(res);
//		
//		Address address7 = Address.builder()
//				.country("Israel")
//				.city("city6")
//				.building(7)
//				.apartment(7)
//				.build();
//		
//		car = UpdateCarDto.builder()
//				.regNumber("77-777-77")
//				.address(address7)
//				.build();
//		
//		res = carroService.updateCar(car);	
//		System.out.println(res);
//===============================================		
//	}
//}

*/


