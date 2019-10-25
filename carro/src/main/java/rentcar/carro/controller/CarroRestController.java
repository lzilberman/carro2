package rentcar.carro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rentcar.carro.dto.*;
import rentcar.carro.entities.*;

import rentcar.carro.service.ICarroService;

import java.util.List;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
public class CarroRestController {

    @Autowired
    ICarroService carroService;

    // CAR OPERATIONS. Error Response: 400 Bad Request, 409 Conflict;      401 Unauthorized, 403 Forbidden
    @PostMapping(value = CarroApiConstants.ADD_CAR)
    Car addCar(@RequestBody @Valid CarDto car) {
        return carroService.addCar(car);
    }
    // Error Response: 400 Bad Request, 409 Conflict;      401 Unauthorized
    @PutMapping(value = CarroApiConstants.UPDATE_CAR)
    Car updateCar(@RequestBody UpdateCarDto car ) {
        return carroService.updateCar(car);
    }
    // Code: 409 Conflict ?404, 401 Unauthorized
    @DeleteMapping(value = CarroApiConstants.DELETE_CAR)
    Car deleteCar(@PathVariable String regNumber) {
        return carroService.deleteCar(regNumber);
    }
    // Code:  404 Not Found
    @GetMapping(value = CarroApiConstants.GET_CAR)
    Car getCar(@PathVariable String regNumber) {
        return carroService.getCar(regNumber);
    }
    // BOOKING OPERATIONS
    // Code: 409 Conflict? 404, 401 Unauthorized
    @GetMapping(value = CarroApiConstants.GET_CAR_BOOKINGS) 
	List<Booking> getCarBookings(@PathVariable String regNumber) {
    	return carroService.getCarBookings(regNumber);
    }
    //Code: 404 Not Found, 409 Conflict
    @PostMapping(value = CarroApiConstants.BOOK_CAR)
    BookingResultDto makeReservation(@RequestBody @Valid BookingDataDto bookingData) {
        return carroService.makeReservation(bookingData);
    }

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
