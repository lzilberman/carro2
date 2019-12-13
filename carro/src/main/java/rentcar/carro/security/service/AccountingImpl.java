package rentcar.carro.security.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import rentcar.carro.accmanagement.dao.AccMngRepository;
import rentcar.carro.accmanagement.entities.AccountCarro;
import rentcar.carro.exception.ObjectNotFoundException;

@Service
@ManagedResource
public class AccountingImpl implements IAccounting {

	@Value("${experationPeriod:30}")
	int experationPeriod;
	
	@ManagedAttribute
	public int getExperationPeriod () {
		return experationPeriod;
	}
	@ManagedAttribute
	public void setExperationPeriod (int experationPeriod) {
		this.experationPeriod = experationPeriod;
	}
	@Autowired
	AccMngRepository accounts;

	@Override
	public String getPassword(String username) {
		AccountCarro account = accounts.findById(username).orElse(null);
		if (account == null) {
			throw new ObjectNotFoundException ("Account for  " + username + " not found");
		}
		LocalDate expDate = account.getDate ().plusDays(experationPeriod);
		if (LocalDate.now ().isAfter (expDate) || LocalDate.now().equals (expDate)) {
			throw new ObjectNotFoundException ("Password for  " + username + " is expired");
		}
		return account.getPassword();
	}

	@Override
	public String[] getRoles(String username) {
		AccountCarro account = accounts.findById(username).orElse(null);
		if (account == null) {
			throw new ObjectNotFoundException ("Account for  " + username + " not found");
		}
		String [] res = new String [account.getRoles().size()];
		return account.getRoles().toArray(res);	
	}

}
