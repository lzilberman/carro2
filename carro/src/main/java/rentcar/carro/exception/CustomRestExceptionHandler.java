package rentcar.carro.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;

@Order(Ordered.HIGHEST_PRECEDENCE) // https://www.baeldung.com/spring-order
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	/***********************************************************************************************************************************
	 * https://www.mkyong.com/spring-boot/spring-rest-error-handling-example/
	 * https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
	 * https://github.com/eugenp/tutorials/blob/master/spring-security-rest/src/main/java/org/baeldung/errorhandling/CustomRestExceptionHandler.java
	 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html#handleHttpMessageNotReadable-org.springframework.http.converter.HttpMessageNotReadableException-org.springframework.http.HttpHeaders-org.springframework.http.HttpStatus-org.springframework.web.context.request.WebRequest-
	 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html
	 * 
	 * https://www.toptal.com/java/spring-boot-rest-api-error-handling
	 ***********************************************************************************************************************************/
//	@Override
//	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
//			                                                      HttpHeaders headers, 
//			                                                      HttpStatus status, 
//			                                                      WebRequest request) {
//		ApiError apiError = new ApiError(status, "Malformed JSON request", ex);
//
//		return new ResponseEntity<Object>(apiError, status);
//	}

	
    //***** 400 *****
	// @Override
	// VALIDATION FAILED. This exception is thrown when argument annotated with @Valid failed validation
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
														    		MethodArgumentNotValidException ex, 
														    		HttpHeaders headers, 
														    		HttpStatus status, 
														    		WebRequest request) {
        logger.info(ex.getClass().getName());
        List<String> errors = new ArrayList<>();
        
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "method arg not valid", errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }
    
    @Override
    protected ResponseEntity<Object> handleBindException(
												    		BindException ex, 
												    		HttpHeaders headers, 
												    		HttpStatus status, 
												    		WebRequest request) {
        logger.info(ex.getClass().getName());
        List<String> errors = new ArrayList<String>();
        
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "$$$$$$$$$$$$$$$", errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }
    
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
											    		TypeMismatchException ex, 
											    		HttpHeaders headers, 
											    		HttpStatus status, 
											    		WebRequest request) {
        logger.info(ex.getClass().getName());
        String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "&&&&&&&&&&&&&&&&&&", error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
   
	// MISSING PARAMETER
    /*
     * A HTTP multipart request is a HTTP request that HTTP clients construct to send files and data over to a HTTP Server. 
     * It is commonly used by browsers and HTTP clients to upload files to the server.
     */
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestPart(
//															    		MissingServletRequestPartException ex, 
//															    		HttpHeaders headers, 
//															    		HttpStatus status, 
//															    		WebRequest request) {
//        logger.info(ex.getClass().getName());
//        String error = ex.getRequestPartName() + " part is missing";
//        
////        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "%%%%%%%%%%%%%%%%", error);
//        return new ResponseEntity<Object>(apiError, apiError.getStatus());
//    }
    
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
																    		MissingServletRequestParameterException ex, 
																    		HttpHeaders headers, 
																    		HttpStatus status, 
																    		WebRequest request) {
        logger.info(ex.getClass().getName());
        String error = ex.getParameterName() + " parameter is missing";
        
//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "^^^^^^^^^^^^^^^^^^", error);
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }
    //@ExceptionHandler
    //
	// TYPE MISMATCH
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
														    		MethodArgumentTypeMismatchException ex, 
														    		WebRequest request) {
        logger.info(ex.getClass().getName());
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "@@@@@@@@@@@@@@@@@", error);
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }  
    
	//=== CONSTRAINT VIOLATION (NOT NULL f.e.)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation (ConstraintViolationException ex, 
												    		 WebRequest request) {
        logger.info(ex.getClass().getName());
        List<String> errors = new ArrayList<String>();
        
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "###############", errors); // ex.getLocalizedMessage()
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }
    //===
    @ExceptionHandler(InvalidUpdateCarDtoException.class)
    public ResponseEntity<Object> handleInvalidUpdateCarDtoException (InvalidUpdateCarDtoException ex) {
    	
        logger.info(ex.getClass().getName());
        String message = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message, message);
 	       
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    } 

    //===** 404 NOT_FOUND *****
    /*
     * https://stackoverflow.com/questions/51048707/spring-boot-handling-nohandlerfoundexception
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException (NoHandlerFoundException ex,
														    		HttpHeaders headers, 
														    		HttpStatus status, 
														    		WebRequest request) {
        logger.info(ex.getClass().getName());
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "******************", error); // ex.getLocalizedMessage()
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }
    //===
    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Object> handleCarNotFoundException (CarNotFoundException ex) {
    	
        logger.info(ex.getClass().getName());
        String message = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, message, message);
 	       
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    } 
    
    //***** 405 *****
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
																    		HttpRequestMethodNotSupportedException ex, 
																    		HttpHeaders headers, 
																    		HttpStatus status, 
																    		WebRequest request) {
        logger.info(ex.getClass().getName());
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

//        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, "QQQQQQQQQQQQQ", builder.toString());
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }

    //***** 415 *****
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
 															    		HttpMediaTypeNotSupportedException ex, 
															    		HttpHeaders headers, 
															    		HttpStatus status, 
															    		WebRequest request) {
        logger.info(ex.getClass().getName());
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }
    //===** 409 CONFLICT *****
    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<Object> handleDuplicateKeyException (MongoWriteException ex) {
    	
        logger.info(ex.getClass().getName());
        String error = ex.getMessage();
        String keyValue = error.substring(error.indexOf('\"')+1, error.lastIndexOf('\"'));
        String message = "Car with number " + keyValue + " already exist";
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, message, error);
 	       
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    } 
    //===
    @ExceptionHandler(IllegalBookingPeriod.class)
    public ResponseEntity<Object> handleIllegalBookingPeriod (IllegalBookingPeriod ex) {
    	
        logger.info(ex.getClass().getName());
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, error, error);
 	       
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    } 
    
    //***** 500 *****
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAll (Exception ex, WebRequest request) {
//    	
//        logger.info(ex.getClass().getName());
//        logger.error("error", ex);
//        
////        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
//        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "WWWWWWWWWWWWWWWWW", "error occurred");
//        return new ResponseEntity<Object>(apiError, apiError.getStatus());
//    }

}
