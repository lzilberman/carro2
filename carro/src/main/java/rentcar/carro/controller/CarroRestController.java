package rentcar.carro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rentcar.carro.dto.*;
import rentcar.carro.entities.*;
import rentcar.carro.exception.UnauthorizedException;
import rentcar.carro.service.ICarroService;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
public class CarroRestController {

    @Autowired
    ICarroService carroService;

    // CAR OPERATIONS. Error Response: 400 Bad Request, 409 Conflict;      401 Unauthorized, 403 Forbidden
    @PostMapping(value = CarroApiConstants.ADD_CAR)
    Car addCar(@RequestBody CarDto car) {
        return carroService.addCar(car);
    }
    // Error Response: 400 Bad Request, 409 Conflict;      401 Unauthorized
    @PutMapping(value = CarroApiConstants.UPDATE_CAR)
    Car updateCar(@RequestBody UpdateCarDto car, Principal authenticatedUser) {
    	
    	String carOwner = carroService.ownerGetCar(car.getRegNumber()).getOwner();
    	if (!carOwner.equals(authenticatedUser.getName())) {
    		throw new UnauthorizedException("Unauthorized action");
    	}
        return carroService.updateCar(car);
    }
    // Code: 409 Conflict ?404, 401 Unauthorized
    @DeleteMapping(value = CarroApiConstants.DELETE_CAR)
    Car deleteCar(@PathVariable String regNumber, Principal authenticatedUser) {
    	
    	String carOwner = carroService.ownerGetCar(regNumber).getOwner();
    	if (!carOwner.equals(authenticatedUser.getName())) {
    		throw new UnauthorizedException("Unauthorized action");
    	}    	
        return carroService.deleteCar(regNumber);
    }
    // Code:  404 Not Found
    @GetMapping(value = CarroApiConstants.GET_CAR)
    Car getCar(@PathVariable String regNumber) {
        return carroService.userGetCar(regNumber);
    }
    //
    @GetMapping(value = CarroApiConstants.OWNER_GET_CAR_BY_ID) 
    Car ownerGetCar(@PathVariable String regNumber, Principal authenticatedUser) {
    	
    	String carOwner = carroService.ownerGetCar(regNumber).getOwner();
    	if (!carOwner.equals(authenticatedUser.getName())) {
    		throw new UnauthorizedException("Unauthorized action");
    	}    	    	
        return carroService.ownerGetCar(regNumber);
    }
    //
    @GetMapping(value=CarroApiConstants.OWNER_GET_CARS)
    List<Car> getOwnerCar(@PathVariable String email, Principal authenticatedUser) {
    	if (!email.equals(authenticatedUser.getName())) {
    		throw new UnauthorizedException("Unauthorized action");
    	}    	    	    	
    	return carroService.getOwnerCars(email);
    }
    // BOOKING OPERATIONS
    // Code: 409 Conflict? 404, 401 Unauthorized
    @GetMapping(value = CarroApiConstants.OWNER_GET_CAR_BOOKINGS) 
	List<Booking> getCarBookings(@PathVariable String regNumber, Principal authenticatedUser) {
    	
    	String carOwner = carroService.ownerGetCar(regNumber).getOwner();
    	if (!carOwner.equals(authenticatedUser.getName())) {
    		throw new UnauthorizedException("Unauthorized action");
    	}    	    	    	
    	return carroService.getCarBookings(regNumber);
    }
    //Code: 404 Not Found, 409 Conflict
    @PostMapping(value = CarroApiConstants.BOOK_CAR)
    BookingResultDto makeReservation(@RequestBody @Valid BookingDataDto bookingData) {
        return carroService.makeReservation(bookingData);
    }
    // Code: 409 Conflict?
    @PostMapping(value = CarroApiConstants.CONGIRM_PAYMENT)
    void confirmPayment(@RequestBody ConfirmPaymentDto confirm){
        carroService.confirmPayment(confirm);
    }
    // SERCH
    @PostMapping(value = CarroApiConstants.FIND_BY)
    List<Car> findBy(@RequestBody SearchCriteriaDto searchData) {
    	return carroService.findBy(searchData);
    }
//    @GetMapping(value = CarroApiConstants.BEST_BOOKED)
//    Response bestBooked(){
//        return carroService.bestBooked();
//    }
    // COMMENTS OPERATIONS
    @PostMapping(value = CarroApiConstants.ADD_COMMENT)
    void addCarComment(@RequestBody Comment comment){
        carroService.addCarComment(comment);
    }
    
    @GetMapping(value = CarroApiConstants.GET_CAR_COMMENTS)
    List<Comment> getCarComments(@PathVariable String regNumber) {
        return carroService.getCarComments(regNumber);
    }

}
