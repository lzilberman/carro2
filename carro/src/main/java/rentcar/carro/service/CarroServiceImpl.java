package rentcar.carro.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Range;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import rentcar.carro.dto.*;
import rentcar.carro.entities.*;
import rentcar.carro.exception.CarNotFoundException;
import rentcar.carro.exception.IllegalBookingPeriod;
import rentcar.carro.exception.InvalidUpdateCarDtoException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CarroServiceImpl implements ICarroService {
	@Autowired
	MongoOperations mongoOperations;
	
	@Override
	public Car addCar(CarDto carDto) {
		return mongoOperations.insert(new Car(carDto));
	}

	@Override
	public Car updateCar(UpdateCarDto updateData) {
		Query query = new Query();
		query.addCriteria(Criteria.where("regNumber").is(updateData.getRegNumber()));
		
		Update update = new Update();

		if (updateData.getHostCity() != null && updateData.getHostCity().trim().isEmpty()) {
			throw new InvalidUpdateCarDtoException("host city for update car " + updateData.getRegNumber() + " has invalid value.");
		}		
		update = updateData.getHostCity() == null? update : 
			update.set("hostCity", updateData.getHostCity().trim() ); 
		
		update = updateData.getAddress() == null? update : 
			update.set("address", updateData.getAddress() ); 

		update = updateData.getLocation() == null? update : 
			update.set("location", updateData.getLocation() ); 
		
		update = updateData.getFeatures() == null? update : 
			update.set("features", updateData.getFeatures() ); 
		
		if (updateData.getDayPrice() <= 0) {
			throw new InvalidUpdateCarDtoException("day price for update car " + updateData.getRegNumber() + " has invalid value.");
		}
		update = updateData.getDayPrice() == null? update : 
			update.set("dayPrice", updateData.getDayPrice() ); 
		
		update = updateData.getDistanceIncluded() == null? update : 
			update.set("distanceIncluded", updateData.getDistanceIncluded() ); 
		
		update = updateData.getImageUrl() == null? update : 
			update.set("imageUrl", updateData.getImageUrl() ); 
		
		mongoOperations.upsert(query, update, Car.class);	
		return mongoOperations.findById(updateData.getRegNumber(), Car.class); 
	}

	@Override
	public Car deleteCar(String regNumber) {
		Query query = new Query(Criteria.where("regNumber").is(regNumber));
		Car car = mongoOperations.findAndRemove(query, Car.class);
		if (car == null) throw new CarNotFoundException("Car " + regNumber + " not found");
		return car;
	}

	@Override
	public Car getCar(String regNumber) {
		Car car = mongoOperations.findById(regNumber, Car.class);
		if (car == null) throw new CarNotFoundException("Car " + regNumber + " not found");
		return car;
	}
	
	@Override
	public List<Booking> getCarBookings(String regNumber) {
		return getCar(regNumber).getBookings();
	}
	
	// https://www.baeldung.com/spring-boot-mongodb-auto-generated-field
	public long generateSequence(String seqName) {

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(seqName));
		Update update = new Update();
		update.inc("seq",1);
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true).upsert(true);
		
	    DatabaseSequence counter = mongoOperations.findAndModify(query, update, options, DatabaseSequence.class);
	    return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}
	
	@Override
	public BookingResultDto makeReservation(BookingDataDto dto) {
		Car car = getCar(dto.getCarNumber());
		Booking booking = new Booking(dto);
		if(!car.canBook(booking.getBookingRange())) {
			throw new IllegalBookingPeriod("Cannot book car " + dto.getCarNumber() + " in period " + dto.getBookingPeriod());
		}
		
		booking.setOrderNumber(generateSequence(booking.SEQUENCE_NAME));
		car.addBooking(booking);
		
		car = mongoOperations.save(car);
		return car.getBookingResult(booking);
	}

	@Override
	public void confirmPayment(ConfirmPaymentDto dto) {
		Car car = getCar(dto.getCarNumber());
		car.confirmPaymemt(dto);
		mongoOperations.save(car);
	}

	@Override
	public List<Car> findBy(SearchCriteriaDto searchData) {		
        Query query = new Query();
        
        query = searchData.getCity() == null? query : 
        	query.addCriteria(Criteria.where("hostCity").is(searchData.getCity())); 
        
        query = searchData.getMinPrice() == null? query : 
        	query.addCriteria(Criteria.where("dayPrice").lte(searchData.getMaxPrice()).gte(searchData.getMinPrice())); 
        
        query = searchData.getSortByPriceDirection() == null? query : 
        	query.with(new Sort(searchData.getSortByPriceDirection(), "dayPrice")); 

        query = searchData.getMake() == null? query : 
        	query.addCriteria(Criteria.where("make").is(searchData.getMake())); 
        
        query = searchData.getModel() == null? query : 
        	query.addCriteria(Criteria.where("model").is(searchData.getModel())); 
        
        query = searchData.getMinYear() == null? query : 
        	query.addCriteria(Criteria.where("year").lte(searchData.getMaxYear()).gte(searchData.getMinYear())); 
        
        query = searchData.getMinEngineVolume() == null? query : 
        	query.addCriteria(Criteria.where("engine").lte(searchData.getMaxEngineVolume()).gte(searchData.getMinEngineVolume())); 
        
        query = searchData.getFuel() == null? query : 
        	query.addCriteria(Criteria.where("fuel").is(searchData.getFuel())); 
        
        query = searchData.getTransmission() == null? query : 
        	query.addCriteria(Criteria.where("transmission").is(searchData.getTransmission())); 
        
        query = searchData.getWheelsDrive() == null? query : 
        	query.addCriteria(Criteria.where("wheelsDrive").is(searchData.getWheelsDrive())); 
        
        query = searchData.getMinFuelConsumption() == null? query : 
        	query.addCriteria(Criteria.where("fuelConsumption").lte(searchData.getMaxFuelConsumption()).gte(searchData.getMinFuelConsumption())); 
        // geo
        query = searchData.getPoint() == null? query :         	
        	query.addCriteria(Criteria.where("location").near(searchData.getPoint()).maxDistance(searchData.getMaxDistance()));    
        
        query = searchData.getBox() == null? query : 
        	query.addCriteria(Criteria.where("location").within (searchData.getBox())); 

        Long startDateTime = searchData.getStartDateTime() == null? 0L : searchData.getStartDateTime();
        Long endDateTime = searchData.getEndDateTime() == null? 0L : searchData.getEndDateTime();
        boolean filterBooking = startDateTime != 0L && endDateTime != 0L;
        
        Range<Long> bookingPeriod = Range.closed(startDateTime, endDateTime);     
        
        return filterBooking? mongoOperations.find(query, Car.class).stream()
        		.filter(c->c.canBook(bookingPeriod))
        		.collect(Collectors.toList()) : mongoOperations.find(query, Car.class); 
	}
	/*
	 * https://stackoverflow.com/questions/23188875/mongodb-unable-to-find-index-for-geonear-query
	 * https://docs.mongodb.com/manual/reference/operator/aggregation/geoNear/
	 * https://docs.mongodb.com/manual/geospatial-queries/#geospatial-indexes
	 */
	public String createGeoIndex () {
		MongoCollection<Document> collection = mongoOperations.getCollection ("cars");
//		String indexName = collection.createIndex (Indexes.geo2dsphere("location"));
		String indexName = collection.createIndex (Indexes.geo2d("location"));      	
		return indexName;
	}
	
	@Override
	public List<String> getMakeModels(String maker) {
        Query query = new Query(); 
        query.with(new Sort(Sort.Direction.DESC, "model")); 
       	query.addCriteria(Criteria.where("make").is(maker)); 
       	
        return mongoOperations.find(query, Car.class).stream()
        		.map(c->c.getModel())
        		.distinct()
        		.collect(Collectors.toList());
	}
	
	@Override
	public void addCarComment(Comment comment) {
		Car car = getCar(comment.getCarNumber());
		if(car == null) return;
		car.addComment(comment);
		mongoOperations.save(car);		
	}

	@Override
	public List<Comment> getCarComments(String regNumber) {
		return getCar(regNumber).getComments();
	}

	@Override
	public CarRatingDto getCarRating(String regNumber) {
		return getCar(regNumber).getCarRating();
	}

	@Override
	public void clearUnconfirmedBookings() {
		// TODO Auto-generated method stub
		
	}

}
