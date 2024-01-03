package com.devbyteschool.blogs.exception;


import com.devbyteschool.blogs.dto.DBSResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DBSResponseEntity> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        DBSResponseEntity dbsResponseEntity=new DBSResponseEntity();
        dbsResponseEntity.setMessage(errorMessage);
        return new ResponseEntity<>(dbsResponseEntity,HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<DBSResponseEntity> handleRecordNotFoundExceptions(
            RecordNotFoundException  ex) {
        DBSResponseEntity dbsResponseEntity=new DBSResponseEntity();
        dbsResponseEntity.setMessage(ex.message);
        return new ResponseEntity<>(dbsResponseEntity,HttpStatus.NOT_FOUND);
    }



}
