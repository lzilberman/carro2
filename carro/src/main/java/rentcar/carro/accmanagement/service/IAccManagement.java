package rentcar.carro.accmanagement.service;

import rentcar.carro.accmanagement.dto.UserDetails;

public interface IAccManagement {
	
	   boolean addAccount (String email, String password, String [] roles, UserDetails details);
	   boolean removeAccount (String email);
	   boolean updatePassword (String email, String password);
	   boolean addRole (String email, String role);
	   boolean removeRole (String email, String role);
	   String [] getUserRoles (String email);
	   UserDetails getUserDetails(String email);
	   void setUserDetails (UserDetails details);
}
