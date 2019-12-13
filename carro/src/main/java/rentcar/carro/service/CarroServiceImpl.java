package rentcar.carro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Range;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import rentcar.carro.dto.*;
import rentcar.carro.entities.*;
import rentcar.carro.exception.ObjectNotFoundException;
import rentcar.carro.exception.IllegalBookingPeriod;
import rentcar.carro.exception.InvalidUpdateCarDtoException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.sql.Timestamp;

//https://habr.com/ru/company/jugru/blog/439796/ # @ManagedResource  + @EnableAsync = NO JMX
// https://github.com/toparvion/joker-2018-samples/tree/master/jmx-resource
@ManagedResource(objectName="Carro:name=parameters")
@Service
@EnableScheduling
@EnableAsync
public class CarroServiceImpl implements ICarroService {
	@Autowired
	MongoOperations mongoOperations;
	
	@ManagedAttribute
	public int getMillisWaitForPayment() {
		return millisWaitForPayment;
	}
	@ManagedAttribute
	public void setMillisWaitForPayment(int millisWaitForPayment) { 
		this.millisWaitForPayment = millisWaitForPayment;
	}
	@Value("${millisWaitForPayment:120000}")
	int millisWaitForPayment = 90000;
	
	public CarroServiceImpl() {
		System.out.println("from constructor");
		System.out.printf("millisWaitForPayment = %d\n\n", millisWaitForPayment);		
	}
	@PostConstruct
	public void postConstruct() {
		System.out.println("from postConstruct");
		System.out.printf("millisWaitForPayment = %d\n\n", millisWaitForPayment);		
	}
	@PreDestroy
	public void preDestroy() {
		System.out.println("from preDestroy");
		System.out.printf("millisWaitForPayment = %d\n\n", millisWaitForPayment);		
	}
	
	
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
		if (car == null) throw new ObjectNotFoundException("Car " + regNumber + " not found");
		return car;
	}
	@Override
	public Car userGetCar(String regNumber) {
		Car car = mongoOperations.findById(regNumber, Car.class);
		if (car == null) throw new ObjectNotFoundException("Car " + regNumber + " not found");
		
		List<BookingBase> periods = car.getBookings().stream()
		.map(b->b.toBookingBase())
		.collect(Collectors.toList());
		car.setBookingPeriods(periods);
		car.setBookings(null);
		
		return car;
	}
	@Override
	public Car ownerGetCar(String regNumber) {
		Car car = mongoOperations.findById(regNumber, Car.class);
		if (car == null) throw new ObjectNotFoundException("Car " + regNumber + " not found");
		return car;
	}
	@Override
	public List<Car> getOwnerCars(String email) {
        Query query = new Query(); 
        query.with(new Sort(Sort.Direction.ASC, "regNumber")); 
       	query.addCriteria(Criteria.where("owner").is(email)); 
		
		return mongoOperations.find(query, Car.class);
	}
// https://www.baeldung.com/spring-boot-mongodb-auto-generated-field
//	public long generateSequence(String seqName) {
	
	@Override
	public List<Booking> getCarBookings(String regNumber) {
		return ownerGetCar(regNumber).getBookings();
	}
	@Override
	public BookingResultDto makeReservation(BookingDataDto dto) {
		
		Long start = dto.getStartDateTime();
		Long finish = dto.getEndDateTime();
		
		Criteria one = Criteria.where("bookings").not().elemMatch(Criteria.where("startDateTime").lte(start)
				.and("endDateTime").gte(start));
		Criteria two = Criteria.where("bookings").not().elemMatch(Criteria.where("startDateTime").lte(finish)
				.and("endDateTime").gte(finish));
		Criteria three = Criteria.where("bookings").not().elemMatch(Criteria.where("startDateTime").gte(finish)
				.and("endDateTime").lte(finish));
		Criteria four = Criteria.where("bookings").not().elemMatch(Criteria.where("startDateTime").lte(finish)
				.and("endDateTime").gte(finish));		
		Criteria five = Criteria.where("regNumber").is(dto.getCarNumber());		

		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(one, two, three, four, five));
		
		Booking booking = new Booking(dto);
		Update update = new Update();	
		update.addToSet("bookings",  booking );
				
		Car updatedCar = this.mongoOperations.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Car.class);
        if (updatedCar == null) {
			throw new IllegalBookingPeriod("Car number " + booking.getCarNumber() + 
					" doesn't exist or already booked in period " + booking.getBookingPeriod());        	
        }
		return booking.getBookingResult(); 
	}
/* https://docs.mongodb.com/manual/reference/operator/update/pull/#up._S_pull
 * https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/query/Update.html#pull-java.lang.String-java.lang.Object-
 * https://github.com/gaiandb/gaiandb/blob/master/java/Asset/VTIs/com/ibm/db2j/MongoDB.java
 * https://www.baeldung.com/spring-scheduled-tasks
 * https://howtodoinjava.com/spring-core/spring-scheduled-annotation/
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/Scheduled.html#fixedRate--
 * https://recordings.join.me/oL2YbbSdaU-1XC-AaJsvIQ
 */
	@Override
	@Async
	@Scheduled(fixedRateString = "${millisWaitForPayment}", initialDelayString = "${millisWaitForPayment}")	
	public void clearUnconfirmedBookings() {
		
		Long threshold = Timestamp.valueOf(LocalDateTime.now()).getTime() - millisWaitForPayment*60000; 
		Update update = new Update();
		
		BasicDBObject conditions = new BasicDBObject("paymentConfirmed", false);	
		conditions.append("bookingDateTime", new BasicDBObject("$lte", threshold));  // 1573133345966L
		update.pull("bookings", conditions);

		UpdateResult updated = this.mongoOperations.updateMulti(new Query(), update, Car.class);
		System.out.println("##################### Removed count: " + updated.getModifiedCount());		
	}
	@Override
	public void testElement() {
       // TODO: for common testing purpose		
	}	

	@Override
	public void confirmPayment(ConfirmPaymentDto dto) {
		Car car = ownerGetCar(dto.getCarNumber());
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
	/*
	 * https://docs.mongodb.com/manual/reference/operator/query/or/
	 * https://mongodb.github.io/mongo-java-driver/3.4/driver/tutorials/indexes/
	 */
	public void createDateTimeIndexes () {		
		MongoCollection<Document> collection = mongoOperations.getCollection ("cars");
		collection.createIndex (Indexes.ascending("bookings.startDateTime"));
		collection.createIndex (Indexes.ascending("bookings.endDateTime"));      	
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
//		Car car = getCar(comment.getCarNumber());
//		car.addComment(comment);
//		mongoOperations.save(car);	
		mongoOperations.save(ownerGetCar(comment.getCarNumber()).addComment(comment));
	}

	@Override
	public List<Comment> getCarComments(String regNumber) {
		return ownerGetCar(regNumber).getComments();
	}

	@Override
	public CarRatingDto getCarRating(String regNumber) {
		return ownerGetCar(regNumber).getCarRating();
	}

}
