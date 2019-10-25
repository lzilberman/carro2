package rentcar.carro.dto;

public class CarroApiConstants {
    public static final String ADD_CAR = "/car";
    public static final String UPDATE_CAR = "/car";
    public static final String DELETE_CAR = "/car/{regNumber}";
    public static final String GET_CAR = "/car/{regNumber}";
    
    public static final String OWNER_GET_CAR_BY_ID = "/user/cars/{regNumber}"; 
    public static final String CAR_ID = "regNumber"; //?

    public static final String ADD_USER = "/registration";
    public static final String UPDATE_USER = "/user/{email}";
    public static final String LOGIN_USER = "user/login";

    public static final String GET_CAR_BOOKINGS = "/user/cars/{regNumber}/periods"; // !
    public static final String BOOK_CAR = "/car/reservation";
    public static final String CONGIRM_PAYMENT = "/reservation/confirm";
    
    public static final String GET_CAR_COMMENTS = "/comments/{regNumber}";
    public static final String ADD_COMMENT = "/comment";
 
    public static final String FIND_BY = "/search";  
    
    public static final String BEST_BOOKED = "/car/best"; //?
    
}
