package rentcar.carro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rentcar.carro.CarroApplication;
import rentcar.carro.accmanagement.dto.AccountDto;
import rentcar.carro.accmanagement.dto.CredentialsDto;
import rentcar.carro.accmanagement.dto.UserDetails;
import rentcar.carro.accmanagement.service.IAccManagement;
import rentcar.carro.exception.UnauthorizedException;
import rentcar.carro.security.service.IAccounting;
import static rentcar.carro.dto.CarroApiConstants.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.logging.Logger;

@RestController
public class AccMngController {

	static Logger log = Logger.getLogger(CarroApplication.class.getName());

	@Autowired
	IAccManagement accManagement;

	@Autowired
	IAccounting accounting;

    // Error Response: 409 Conflict
	@PostMapping (ADD_USER)	
	boolean addNewUser (@RequestBody AccountDto account) {
		String[] roles = {"ROLE_USER"};
log.info(" account for " + account.getRoles() + " with email " + account.getEmail() + "was triggered" );
		return accManagement.addAccount (account.getEmail(), account.getPassword(), 
				                         roles, account.getDetails());  
	}
	// Error Response: 401 Unauthorized
	@GetMapping (LOGIN_USER)
	String [] loginUser (@PathVariable String email, 
			             Principal authenticatedUser) {

		LocalDate date = LocalDate.now();
		if (!email.equals(authenticatedUser.getName()) ) {
			log.warning("API " + "LOGIN_USER : " + " Unauthorized action for user  " + authenticatedUser.getName() +  " ( wrong email - " + email);
			throw new UnauthorizedException("Unauthorized action");
		}
		log.info(" user with email " + email + " end name " + authenticatedUser.getName() + " past authentication " + date);
		return accounting.getRoles(email); 
	}
    // Error Response: 401 Unauthorized
	@PutMapping (UPDATE_USER)
	void updateUser (@RequestBody UserDetails user, 
	                 Principal   authenticatedUser) {
		if (!user.getEmail().equals(authenticatedUser.getName()) ) {
			log.warning("API " + "UPDATE_USER : " + " Unauthorized action for user with email" + user.getEmail());
			throw new UnauthorizedException("Unauthorized action");
		}
		log.info(" user with email " + user.getEmail() + " updated");
	    accManagement.setUserDetails(user);
    }
    // Error Response: 401 Unauthorized
	@DeleteMapping (DELETE_USER)
	boolean deleteUser (@PathVariable String email) {

		log.info(" account by user with email " + email + " was deleted");
		return accManagement.removeAccount (email);
	}
	// Error Response: 401 Unauthorized
	@PostMapping (UPDATE_PASSWORD)
	boolean updatePassword (@RequestBody CredentialsDto credentials,
	                        Principal   authenticatedUser) { 
		if (!credentials.getUsername().equals(authenticatedUser.getName()) ) {
			log.warning("API " + "UPDATE_USER : " + " Unauthorized action for user with name" + credentials.getUsername());
			throw new UnauthorizedException("Unauthorized action");
		}
		log.info(" password for user with name  " + credentials.getUsername() + " was updated");
		return accManagement.updatePassword (credentials.getUsername(), credentials.getPassword());
	}
	
}
