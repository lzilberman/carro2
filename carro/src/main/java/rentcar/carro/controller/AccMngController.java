package rentcar.carro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rentcar.carro.accmanagement.dto.AccountDto;
import rentcar.carro.accmanagement.dto.CredentialsDto;
import rentcar.carro.accmanagement.dto.UserDetails;
import rentcar.carro.accmanagement.service.IAccManagement;
import rentcar.carro.exception.UnauthorizedException;
import rentcar.carro.security.service.IAccounting;
import static rentcar.carro.dto.CarroApiConstants.*;

import java.security.Principal;

@RestController
public class AccMngController {
	@Autowired
	IAccManagement accManagement;

	@Autowired
	IAccounting accounting;

    // Error Response: 409 Conflict
	@PostMapping (ADD_USER)	
	boolean addNewUser (@RequestBody AccountDto account) {
		String[] roles = {"ROLE_USER"};
		return accManagement.addAccount (account.getEmail(), account.getPassword(), 
				                         roles, account.getDetails());  
	}
	// Error Response: 401 Unauthorized
	@GetMapping (LOGIN_USER)
	String [] loginUser (@PathVariable String email, 
			             Principal authenticatedUser) {
		if (!email.equals(authenticatedUser.getName()) ) {
			throw new UnauthorizedException("Unauthorized action");
		}
		return accounting.getRoles(email); 
	}
    // Error Response: 401 Unauthorized
	@PutMapping (UPDATE_USER)
	void updateUser (@RequestBody UserDetails user, 
	                 Principal   authenticatedUser) {
		if (!user.getEmail().equals(authenticatedUser.getName()) ) {
			throw new UnauthorizedException("Unauthorized action");
		}
	    accManagement.setUserDetails(user);
    }
    // Error Response: 401 Unauthorized
	@DeleteMapping (DELETE_USER)
	boolean deleteUser (@PathVariable String email) {
		return accManagement.removeAccount (email);
	}
	// Error Response: 401 Unauthorized
	@PostMapping (UPDATE_PASSWORD)
	boolean updatePassword (@RequestBody CredentialsDto credentials,
	                        Principal   authenticatedUser) { 
		if (!credentials.getUsername().equals(authenticatedUser.getName()) ) {
			throw new UnauthorizedException("Unauthorized action");
		}
		return accManagement.updatePassword (credentials.getUsername(), credentials.getPassword());
	}
	
}
