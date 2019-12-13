package rentcar.carro.accmanagement.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import rentcar.carro.accmanagement.entities.AccountCarro;

@Repository
public interface AccMngRepository extends MongoRepository<AccountCarro, String> {

}
