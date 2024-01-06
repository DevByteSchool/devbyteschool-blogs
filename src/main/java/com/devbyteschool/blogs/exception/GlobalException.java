package com.devbyteschool.blogs.exception;


import com.devbyteschool.blogs.dto.DBSResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DBSResponseEntity> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        DBSResponseEntity dbsResponseEntity=new DBSResponseEntity();
        dbsResponseEntity.setMessage(errorMessage);
        return new ResponseEntity<>(dbsResponseEntity,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<DBSResponseEntity> handleRecordNotFoundExceptions(
            RecordNotFoundException  ex) {
        DBSResponseEntity dbsResponseEntity=new DBSResponseEntity();
        dbsResponseEntity.setMessage(ex.message);
        log.debug("GlobalException:handleRecordNotFoundExceptions record not found for blogId");
        return new ResponseEntity<>(dbsResponseEntity,HttpStatus.NOT_FOUND);
    }



}
