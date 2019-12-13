package rentcar.carro.security.service;

public interface IAccounting {
	String getPassword (String username);
	String [] getRoles (String username);

}
