package rentcar.carro.dto;

public interface CarroApiConstants {

    String ADD_USER = "/registration";
    String LOGIN_USER = "/user/login/{email}"; 
    String UPDATE_USER = "/user";
    
    String UPDATE_PASSWORD = "/password";              // POST
    String ADD_ROLE = "/role";                         // POST
    String DELETE_ROLE = "/role/{username}/{role}";    // DELETE
    
	String DELETE_USER = "/user/{email}";
	String SHUTDOWN = "/actuator/shutdown";
	
    String ADD_CAR = "/car";
    String UPDATE_CAR = "/car";
    String DELETE_CAR = "/car/{regNumber}";
    String GET_CAR = "/car/{regNumber}";

    String OWNER_GET_CARS = "/user/{email}/cars";
    String OWNER_GET_CAR_BY_ID = "/user/cars/{regNumber}"; 
    String OWNER_GET_CAR_BOOKINGS = "/user/cars/periods/{regNumber}"; 
    String FIND_BY = "/search";  
    
    String BOOK_CAR = "/car/reservation";
    String CONGIRM_PAYMENT = "/reservation/confirm";
    
    String BEST_BOOKED = "/car/best"; 
    String GET_CAR_COMMENTS = "/comments/{regNumber}";
    String ADD_COMMENT = "/comment";   
}
