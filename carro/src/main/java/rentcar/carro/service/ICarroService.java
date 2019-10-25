package rentcar.carro.service;

import java.util.List;

import rentcar.carro.dto.*;
import rentcar.carro.entities.*;

public interface ICarroService {
	Car addCar(CarDto car);
	
	Car updateCar(UpdateCarDto car);
	
	Car deleteCar(String regNumber);
	
	Car getCar(String regNumber);
	
	List<Booking> getCarBookings(String regNumber); 
		
	BookingResultDto makeReservation(BookingDataDto bookingData);
	
	void confirmPayment(ConfirmPaymentDto confirm);

	public String createGeoIndex ();
	List<Car> findBy(SearchCriteriaDto searchData);
	List<String> getMakeModels(String maker);
	
	void addCarComment(Comment comment);	
	List<Comment> getCarComments(String regNumber);
    CarRatingDto getCarRating(String regNumber);
    
    public void clearUnconfirmedBookings();

}
