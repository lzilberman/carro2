package rentcar.carro.service;

import java.util.List;

import rentcar.carro.dto.*;
import rentcar.carro.entities.*;

public interface ICarroService {
	
	public Car addCar(CarDto car);
	public Car updateCar(UpdateCarDto car);	
	public Car deleteCar(String regNumber);	
	public Car getCar(String regNumber);
	
	public BookingResultDto makeReservation(BookingDataDto bookingData);
	public void confirmPayment(ConfirmPaymentDto confirm);
    public void clearUnconfirmedBookings();
	public List<Booking> getCarBookings(String regNumber); 		
	
	//==========================
	public void testElement();
	//==========================	
	
	public String createGeoIndex ();
	public void createDateTimeIndexes ();
	
	public List<Car> findBy(SearchCriteriaDto searchData);
	public List<String> getMakeModels(String maker);
	
	public void addCarComment(Comment comment);	
	public List<Comment> getCarComments(String regNumber);
	public CarRatingDto getCarRating(String regNumber);
    
}
