package rentcar.carro.accmanagement.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rentcar.carro.accmanagement.dao.AccMngRepository;
import rentcar.carro.accmanagement.dto.UserDetails;
import rentcar.carro.accmanagement.entities.AccountCarro;
import rentcar.carro.exception.ObjectAlreadyExistException;
import rentcar.carro.exception.ObjectNotFoundException;

@Service
public class AccManagementImpl implements IAccManagement {
	@Autowired
	AccMngRepository accounts;
	@Autowired
	PasswordEncoder encoder;

	public AccManagementImpl() {}

	@Override
	public boolean addAccount(String email, String password, String[] roles, UserDetails details) {
		if (accounts.existsById(email)) {
			throw new ObjectAlreadyExistException("Account for " + email + " already exist");
		}
		String epassword = encoder.encode(password);
		Set<String> sroles = new HashSet<>(Arrays.asList(roles));
		AccountCarro account = new AccountCarro (email, epassword, sroles, details);
		account.setDate (LocalDate.now());
		accounts.save (account);
		return true;
	}

	@Override
	public boolean removeAccount(String email) {
		if (!accounts.existsById(email)) {
			throw new ObjectNotFoundException ("Account for  " + email + " not found");
		}
		accounts.deleteById(email);
		return true;
	}

	@Override
	public boolean updatePassword(String email, String password) {
		AccountCarro account = accounts.findById (email).orElse (null);
		if (account == null) {
			throw new ObjectNotFoundException ("Account for  " + email + " not found");
		}
		if (encoder.matches (password, account.getPassword())) {
			throw new ObjectAlreadyExistException ("Password for  "+ email + " does not changed");
		}
		account.setPassword (encoder.encode (password));
		account.setDate (LocalDate.now());
		accounts.save (account);
		return true;
	}

	@Override
	public boolean addRole(String email, String role) {
		AccountCarro account = accounts.findById (email).orElse(null);
		if (account == null) {
			throw new ObjectNotFoundException ("Account for  " + email + " not found");
		}
		boolean res = account.getRoles().add(role);
		if (res) accounts.save (account);
		return res;
	}

	@Override
	public boolean removeRole(String email, String role) {
		AccountCarro account = accounts.findById(email).orElse(null);
		if (account == null) {
			throw new ObjectNotFoundException ("Account for  " + email + " not found");
		}
		boolean res = account.getRoles().remove(role);		
		if (res) accounts.save(account);
		return res;
	}

	@Override
	public String[] getUserRoles(String email) {
		AccountCarro account = accounts.findById(email).orElse(null);
		if (account == null) {
			throw new ObjectNotFoundException ("Account for  " + email + " not found");
		}
		String [] res = new String [account.getRoles().size()];
		return account.getRoles().toArray(res);
	}

	@Override
	public UserDetails getUserDetails(String email) {
		AccountCarro account = accounts.findById(email).orElse(null);
		if (account == null) {
			throw new ObjectNotFoundException ("Account for  " + email + " not found");
		}
		return account.getDetails();
	}

	@Override
	public void setUserDetails(UserDetails details) {
        AccountCarro account = accounts.findById(details.getEmail()).orElse(null);
        if (account == null) {
        	throw new ObjectNotFoundException ("Account for  " + details.getEmail()+ " not found");
        }
        account.setDetails(details);	
        accounts.save(account);
	}

}
