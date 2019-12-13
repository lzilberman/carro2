package rentcar.carro.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import static rentcar.carro.dto.CarroApiConstants.*;
import static rentcar.carro.dto.CarroSecurityConstants.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.httpBasic().and() 
	    .csrf().disable()
	    .authorizeRequests()
//	           .antMatchers (UPDATE_CAR, DELETE_CAR, OWNER_GET_CARS, 
//	        		         OWNER_GET_CAR_BY_ID, OWNER_GET_CAR_BOOKINGS ).hasRole (OWNER)
	           
	           .antMatchers (DELETE_USER, SHUTDOWN).hasRole (ADMIN)
		
	           .antMatchers (UPDATE_CAR, DELETE_CAR, OWNER_GET_CARS,
	        		         OWNER_GET_CAR_BY_ID, OWNER_GET_CAR_BOOKINGS,
	        		         LOGIN_USER, UPDATE_USER, UPDATE_PASSWORD, ADD_CAR,   
	        		         BOOK_CAR, CONGIRM_PAYMENT, ADD_COMMENT).authenticated ()	
		
	           .antMatchers (ADD_USER, GET_CAR, FIND_BY, BEST_BOOKED, GET_CAR_COMMENTS).permitAll ();

		super.configure(http);
	}

}
