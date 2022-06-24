package cv.pn.apitransito.exceptions;

import java.util.ArrayList;
import java.util.List;

import cv.pn.apitransito.utilities.APIResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;




@RestController
@ControllerAdvice
public class DefExceptionHandler extends ResponseEntityExceptionHandler {

	private final String RECORD_NOT_FOUND = "RECORD NOT FOUND";
	private final String VALIDATION_FAILED = "VALIDATION FAILED";

	 @ExceptionHandler(RecordNotFoundException.class)
	    public final ResponseEntity<Object> handleUserNotFoundException
	                        (RecordNotFoundException ex, WebRequest request) {

	        List<Object> details = new ArrayList<>();
	        details.add(ex.getLocalizedMessage());

	        APIResponse error =  APIResponse.builder()
	        		.status(false)
	        		.statusText(RECORD_NOT_FOUND)
	        		.details(details).build();

	        return new ResponseEntity<Object>(error , HttpStatus.NOT_FOUND);
	    }

	 @Override
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	    		HttpHeaders headers, HttpStatus status, WebRequest request) {
	        List<Object> details = new ArrayList<>();

	        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
	            details.add(error.getDefaultMessage());
	        }
	        APIResponse error =  APIResponse.builder()
	        		.status(false)
	        		.statusText(VALIDATION_FAILED)
	        		.details(details).build();

	        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	    }

	 @Override
	 protected ResponseEntity<Object> handleHttpMessageNotReadable(
				HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		 List<Object> details = new ArrayList<>();

	            details.add(ex.getMessage());

	        APIResponse error =  APIResponse.builder()
	        		.status(false)
	        		.statusText(VALIDATION_FAILED)
	        		.details(details).build();

	        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
		}

	 @Override
	 protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
				HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		 List<Object> details = new ArrayList<>();

		 	details.add(ex.getMessage());
	        APIResponse error =  APIResponse.builder()
	        		.status(false)
	        		.statusText(VALIDATION_FAILED)
	        		.details(details).build();

	        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
		}

}
