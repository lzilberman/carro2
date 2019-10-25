package rentcar.carro.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import rentcar.carro.entities.Car;
@Repository
public interface CarroRepository extends MongoRepository<Car, String> {

}
