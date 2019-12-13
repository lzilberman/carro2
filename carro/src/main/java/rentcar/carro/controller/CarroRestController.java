package rentcar.carro.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rentcar.carro.CarroApplication;
import rentcar.carro.dto.*;
import rentcar.carro.entities.*;
import rentcar.carro.exception.UnauthorizedException;
import rentcar.carro.service.ICarroService;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "*")
@RestController
public class CarroRestController {

    static Logger log = Logger.getLogger(CarroRestController.class.getName());

    @Autowired
    ICarroService carroService;
    // CAR OPERATIONS. Error Response: 400 Bad Request, 409 Conflict;      401 Unauthorized, 403 Forbidden
    @PostMapping(value = CarroApiConstants.ADD_CAR)
    Car addCar(@RequestBody @Valid CarDto car) {
        log.info("car with registration number " + car.getRegNumber() + " was added in to the DB ");
        return carroService.addCar(car);
    }
    // Error Response: 400 Bad Request, 409 Conflict;      401 Unauthorized
    @PutMapping(value = CarroApiConstants.UPDATE_CAR)
    Car updateCar(@RequestBody @Valid UpdateCarDto car, Principal authenticatedUser) {
    	
    	String carOwner = getOwner(car.getRegNumber());
    	if (!carOwner.equals(authenticatedUser.getName())) {
            getLogForErrorOfAutentification(authenticatedUser);
            throw new UnauthorizedException("Unauthorized action");
    	}
    	log.info(" car with registration number " + car.getRegNumber() + " by user " + carOwner + " was updated");
        return carroService.updateCar(car);
    }
    // Code: 409 Conflict ?404, 401 Unauthorized
    @DeleteMapping(value = CarroApiConstants.DELETE_CAR)
    Car deleteCar(@PathVariable String regNumber, Principal authenticatedUser) {

    	String carOwner = getOwner(regNumber);
    	if (!carOwner.equals(authenticatedUser.getName())) {
            getLogForErrorOfAutentification(authenticatedUser);
            throw new UnauthorizedException("Unauthorized action");
    	}
    	log.info(" car with registration number " + regNumber + " was deleted");
        return carroService.deleteCar(regNumber);
    }

    @NotNull(message = "Car's owner must not be null")
    private String getOwner(@PathVariable String regNumber) {
        return carroService.ownerGetCar(regNumber).getOwner();
    }

    // Code:  404 Not Found
    @GetMapping(value = CarroApiConstants.GET_CAR)
    Car getCar(@PathVariable String regNumber) {

        log.info(" getting car with registration number " + regNumber);
        return carroService.userGetCar(regNumber);
    }
    //
    @GetMapping(value = CarroApiConstants.OWNER_GET_CAR_BY_ID) 
    Car ownerGetCar(@PathVariable String regNumber, Principal authenticatedUser) {

    	String carOwner = getOwner(regNumber);
    	if (!carOwner.equals(authenticatedUser.getName())) {
            getLogForErrorOfAutentification(authenticatedUser);
            throw new UnauthorizedException("Unauthorized action");
    	}
    	log.info(" owner with name " + authenticatedUser.getName() + " got car with registration number " + regNumber);
        return carroService.ownerGetCar(regNumber);
    }

    private void getLogForErrorOfAutentification(Principal authenticatedUser) {
        log.warning(" unauthorized action for user " + authenticatedUser.getName());
    }

    //
    @GetMapping(value=CarroApiConstants.OWNER_GET_CARS)
    List<Car> getOwnerCar(@PathVariable String email, Principal authenticatedUser) {
    	if (!email.equals(authenticatedUser.getName())) {
            getLogForErrorOfAutentification(authenticatedUser);
    		throw new UnauthorizedException("Unauthorized action");
    	}
    	log.info(" getting car owner by email " + email);
    	return carroService.getOwnerCars(email);
    }
    // BOOKING OPERATIONS
    // Code: 409 Conflict? 404, 401 Unauthorized
    @GetMapping(value = CarroApiConstants.OWNER_GET_CAR_BOOKINGS) 
	List<Booking> getCarBookings(@PathVariable String regNumber, Principal authenticatedUser) {
    	
    	String carOwner = getOwner(regNumber);
    	if (!carOwner.equals(authenticatedUser.getName())) {
            getLogForErrorOfAutentification(authenticatedUser);
    		throw new UnauthorizedException("Unauthorized action");
    	}
    	log.info(" owner  " + authenticatedUser.getName() + " got car with registration number " + regNumber);
    	return carroService.getCarBookings(regNumber);
    }
    //Code: 404 Not Found, 409 Conflict
    @PostMapping(value = CarroApiConstants.BOOK_CAR)
    BookingResultDto makeReservation(@RequestBody @Valid BookingDataDto bookingData) {
        LocalDate startDate =
                Instant.ofEpochMilli(bookingData.getStartDateTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate finishDate =
                Instant.ofEpochMilli(bookingData.getEndDateTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        log.info(" car with registration number" + bookingData.getCarNumber() + " was booked " +
                " from " + startDate + " to " + finishDate);
        return carroService.makeReservation(bookingData);
    }
    // Code: 409 Conflict?
    @PostMapping(value = CarroApiConstants.CONGIRM_PAYMENT)
    void confirmPayment(@RequestBody @Valid ConfirmPaymentDto confirm){
        log.info(" car with registration number " + confirm.getCarNumber() + " was confirmed ");
        carroService.confirmPayment(confirm);
    }
    // SERCH
    @PostMapping(value = CarroApiConstants.FIND_BY)
    List<Car> findBy(@RequestBody @Valid SearchCriteriaDto searchData) {
    	log.info(" car was found " );
        return carroService.findBy(searchData);
    }
//    @GetMapping(value = CarroApiConstants.BEST_BOOKED)
//    Response bestBooked(){
//        return carroService.bestBooked();
//    }
    // COMMENTS OPERATIONS
    @PostMapping(value = CarroApiConstants.ADD_COMMENT)
    void addCarComment(@RequestBody @Valid Comment comment){
        log.info(" comment about car " + comment.getCarNumber() + " from user " + comment.getFirstName() );
        carroService.addCarComment(comment);
    }
    
    @GetMapping(value = CarroApiConstants.GET_CAR_COMMENTS)
    List<Comment> getCarComments(@PathVariable String regNumber) {
        log.info(" got comments for car " + regNumber);
        return carroService.getCarComments(regNumber);
    }

}
