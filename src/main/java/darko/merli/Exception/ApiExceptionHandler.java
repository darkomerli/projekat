package darko.merli.Exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    //for example, user not found
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> entityNotFoundMethod(EntityNotFoundException e){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    //for example, password has less than 8 characters
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> constraintViolationMethod(ConstraintViolationException e){
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    //if a user already exists
    @ExceptionHandler(value = {EntityExistsException.class})
    public ResponseEntity<Object> entityExistsMethod(EntityExistsException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    //cant delete another users channel
    @ExceptionHandler(value = {IllegalAccessException.class})
    public ResponseEntity<Object> illegalAccess(IllegalAccessException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }

    //bad arguments while creating an user
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> badArguments(IllegalArgumentException e){
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        ApiException apiException = new ApiException(e.getMessage(), httpStatus, LocalDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
