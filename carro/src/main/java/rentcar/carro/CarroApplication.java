///*
package rentcar.carro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rentcar.carro.service.ICarroService;

import java.util.logging.Logger;

@SpringBootApplication
public class CarroApplication  {
    @Autowired
    ICarroService carroService;

	static Logger log = Logger.getLogger(CarroApplication.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(CarroApplication.class, args);



			log.info("logger for Carro Application is started");

	}
}
//*/
//==============================================
/*
package rentcar.carro;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
public class CarroApplication   implements CommandLineRunner  {
    @Autowired
    ICarroService carroService;

	public static void main(String[] args) {
		SpringApplication.run(CarroApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		carroService.clearUnconfirmedBookings();		
		
//==========================================================================
		Long ub1 = Timestamp.valueOf( LocalDateTime.of(2020, 1, 1, 11, 10)).getTime();	
		System.out.println("2020.1.01, 11:10 = "+ ub1.toString());
		
		Long ub2 = Timestamp.valueOf(LocalDateTime.of(2020, 1, 5, 11, 10)).getTime();
		System.out.println("2020.1.05, 11:10 = "+ ub2.toString());
		
		Long ub3 = Timestamp.valueOf(LocalDateTime.of(2020, 1, 8, 11, 10)).getTime();
		System.out.println("2020.1.08, 11:10 = "+ ub3.toString());
		
		Long ub4 = Timestamp.valueOf(LocalDateTime.of(2020, 1, 11, 11, 10)).getTime();
		System.out.println("2020.1.11, 11:10 = "+ ub4.toString());


		Long ub5 = Timestamp.valueOf(LocalDateTime.of(2020, 1, 15, 11, 10)).getTime();
		System.out.println("2020.1.15, 11:10 = "+ ub5.toString());
		
		Long ub6 = Timestamp.valueOf(LocalDateTime.of(2020, 1, 20, 11, 10)).getTime();
		System.out.println("2020.1.20, 11:10 = "+ ub6.toString());
			
//===========================================================================
//		LocalDateTime dc1 = LocalDateTime.of(2019, 11, 1, 11, 10); 	LocalDateTime dc2 = LocalDateTime.of(2019, 11, 5, 11, 10); 
//		LocalDateTime dc3 = LocalDateTime.of(2019, 11, 8, 11, 10);  LocalDateTime dc4 = LocalDateTime.of(2019, 11, 12, 11, 10); 
//		LocalDateTime dc5 = LocalDateTime.of(2019, 11, 22, 11, 10); LocalDateTime dc6 = LocalDateTime.of(2019, 11, 25, 11, 10); 
//		  
//		Long tc1 = Timestamp.valueOf(dc1).getTime(); Long tc2 = Timestamp.valueOf(dc2).getTime();
//		Long tc3 = Timestamp.valueOf(dc3).getTime(); Long tc4 = Timestamp.valueOf(dc4).getTime();
//		Long tc5 = Timestamp.valueOf(dc5).getTime(); Long tc6 = Timestamp.valueOf(dc6).getTime();	
//************************** BOOKING 72-777-72 *******************************		
//		BookingDataDto bookingData721 = BookingDataDto.builder().carNumber("72-777-72").dayPrice(49)
//				.startDateTime(tc1).endDateTime(tc2).user("721@gmail.com").build();
//		
////// 721@gmail.com: set bookingConfirmed true (direct in Atlas) ////////////
//		BookingResultDto res721 = carroService.makeReservation(bookingData721);
//		if (res721 == null) System.out.println("cannot book reservation for user 721@gmail.com");
//		else System.out.println("reservation: " + res721);
//****************************************************************************
//		BookingDataDto bookingData722 = BookingDataDto.builder().carNumber("72-777-72").dayPrice(49)
//				.startDateTime(tc3).endDateTime(tc4).user("722@gmail.com").build();
//		
//		BookingResultDto res722 = carroService.makeReservation(bookingData722);
//		if (res722 == null) System.out.println("cannot book reservation for user 722@gmail.com");
//		else System.out.println("reservation: " + res722);				
//****************************************************************************		
//		BookingDataDto bookingData723 = BookingDataDto.builder().carNumber("72-777-72").dayPrice(49)
//				.startDateTime(tc5).endDateTime(tc6).user("723@gmail.com").build();
//		
//// 723@gmail.com: set bookingDateTime 1571929598404 (direct in Atlas)
//		BookingResultDto res723 = carroService.makeReservation(bookingData723);
//		if (res723 == null) System.out.println("cannot book reservation for user 723@gmail.com");
//		else System.out.println("reservation: " + res723);		
//************************ BOOKING 77-777-77  ********************************
//		BookingDataDto bookingData771 = BookingDataDto.builder().carNumber("77-777-77").dayPrice(54)
//				.startDateTime(tc1).endDateTime(tc2).user("771@gmail.com").build();
//		
////// 771@gmail.com: set bookingConfirmed true (direct in Atlas) ////////////
//		BookingResultDto res771 = carroService.makeReservation(bookingData771);
//		if (res771 == null) System.out.println("cannot book reservation for user 771@gmail.com");
//		else System.out.println("reservation: " + res771);
//*****************************************************************************	
//		BookingDataDto bookingData772 = BookingDataDto.builder().carNumber("77-777-77").dayPrice(54)
//				.startDateTime(tc3).endDateTime(tc4).user("772@gmail.com").build();
//
//		BookingResultDto res772 = carroService.makeReservation(bookingData772);
//		if (res772 == null) System.out.println("cannot book reservation for user 772@gmail.com");
//		else System.out.println("reservation: " + res772);
//*****************************************************************************
//		BookingDataDto bookingData773 = BookingDataDto.builder().carNumber("77-777-77").dayPrice(54)
//				.startDateTime(tc5).endDateTime(tc6).user("773@gmail.com").build();
//		
//// 773@gmail.com: set bookingDateTime 1571929598404  (direct in Atlas)
//		BookingResultDto res773 = carroService.makeReservation(bookingData773);
//		if (res773 == null) System.out.println("cannot book reservation for user 773@gmail.com");
//		else System.out.println("reservation: " + res773);		
//*****************************************************************************	
//		Long threshold = Timestamp.valueOf(LocalDateTime.now()).getTime() - 6000000; 		
//		System.out.println("################## threshold: " + threshold.toString());
		
//		System.out.println("=====================");
//		System.out.println("66-666-66 bookings[0]");
//		System.out.println(new Date(1572599400000L));	
//		System.out.println(new Date(1572945000000L));
//		System.out.println("");		
//		System.out.println("66-666-66 bookings[1]");
//		System.out.println(new Date(1573204200000L));
//		System.out.println(new Date(1573549800000L));
//		System.out.println("=====================");		
//		System.out.println("77-777-77 bookings[0]");
//		System.out.println(new Date(1572599400000L));
//		System.out.println(new Date(1572945000000L));
//		System.out.println("=====================");
//		System.out.println("72-777-72 bookings[0]");
//		System.out.println(new Date(1573117800000L));
//		System.out.println(new Date(1573549800000L));
//		System.out.println("=====================");
//		System.out.println("62-666-62, 11-111-11 NO BOOKINGS");
		
//		System.out.println("1572858600000");
//		System.out.println(new Date(1572858600000L));
//		System.out.println("1573204200000");
//		System.out.println(new Date(1573204200000L));
		
//		carroService.createDateTimeIndexes();
		
//=========GEO SEARCH==========================================			
//		SearchCriteriaDto sdata = new SearchCriteriaDto ();
//		Point point6 = new Point (34.798233, 31.958455);
//		sdata.setPoint (point6);
//		sdata.setMaxDistance (0.0005);
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
//				.carNumber("62-666-62")
//				.startDateTime(1575364200000L)
//				.endDateTime(1575709800000L)
//				.user("USER62@gmail.com")
//				.build();
//
//		BookingResultDto res62 = carroService.makeReservation(bookingData62);
//		if (res62 == null) System.out.println("cannot book reservation for user USER62@gmail.com");
//		else System.out.println("reservation: " + res62);
//======================================================
//		carroService.getCarBookings("66-666-66")
//		.forEach(System.out::println);
//============================================================
//		ConfirmPaymentDto dto = new ConfirmPaymentDto("66-666-66", res62.getOrderNumber(), true);
//		carroService.confirmPayment(dto);		
//============================================================		

  }
}
*/
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
		Point location7 = new Point(34.797407, 	31.958893);
//		Point location62 = new Point(34.79944, 	31.959007);
		Point location72 = new Point(34.79698, 31.957991);
//		Point location111 = new Point(34.798984, 31.958135);
//		
		String[] image6 = new String[] {
				"https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/b0r3mfIkQlGJwrSJabE8cg.1440x700.jpg",
				"https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/IkG7bVDbR9-rv13KbsUG-g.1440x700.jpg"
				};
		String[] image7 = new String[] {
				"https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/rRas_v7uQwqRfqhT8LTpKw.1440x700.jpg",
				"https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/UD6wK_r_Tz2-EhgRzsgVqw.1440x700.jpg"
				};
		
		String[] features6 = {"Reverse Camera", "Touch Screen Menu", "Side Camera"}; 
		String[] features7 = {"Super", "Puper", "Features"}; 
//
//		Address address62 = Address.builder()
//		.country("Israel")
//		.city("city6")
//		.building(62)
//		.apartment(62)
//		.build();
//
		Address address77 = Address.builder()
		.country("Israel")
		.city("city6")
		.building(77)
		.apartment(77)
		.build();
		
		Address address72 = Address.builder()
		.country("Israel")
		.city("city6")
		.building(72)
		.apartment(72)
		.build();
		
//		Address address111 = Address.builder()
//		.country("Israel")
//		.city("city111")
//		.building(111)
//		.apartment(111)
//		.build();
//		
//		CarDto car111 = CarDto.builder()
//				.regNumber("11-111-11")  
//				.owner("111@gmail.com")  
//				.hostCity("city111")       
//				.location(location111)     
//				.address(address111)
//				.make("maker6")          
//				.model("model11")         
//				.year(2011)               
//				.engine(2.0)
//				.fuel("gas")
//				.transmission("Manual")
//				.wheelsDrive("TWD")
//				.doors(4)
//				.seats(5)
//				.carClass("C")
//				.fuelConsumption(12.5)
//				.features(features7)         
//				.dayPrice(42)               
//				.distanceIncluded(0.45)
//				.imageUrl(image6)			
//				.build();
				
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
//		CarDto car7 = CarDto.builder()
		CarDto car72 = CarDto.builder()
				.regNumber("72-777-72")  
				.owner("72@gmail.com")  
				.hostCity("city6")   
				.address(address72)
				.location(location72)     
				.make("maker6")          
				.model("model62")         
				.year(2011)               
				.engine(2.5)
				.fuel("gas")
				.transmission("Manual")
				.wheelsDrive("TWD")
				.doors(4)
				.seats(5)
				.carClass("D")
				.fuelConsumption(15.2)
				.features(features7)         
				.dayPrice(49)               
				.distanceIncluded(0.45)
				.imageUrl(image7)			
				.build();	
		
//		Car res = carroService.addCar(car7);	
//		System.out.println(res);
		
		Car res = carroService.addCar(car72);	
		System.out.println(res);
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
	}
}

*/


