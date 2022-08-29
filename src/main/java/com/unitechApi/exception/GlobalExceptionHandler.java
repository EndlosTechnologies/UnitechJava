package com.unitechApi.exception;

import com.unitechApi.exception.ExceptionService.*;
import com.unitechApi.exception.Model.ExceptionMOdel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DateMisMatchException.class)
    public ResponseEntity<?> handleDateMismatch(DateMisMatchException ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        exceptionMOdel.setTitle(" Wrong Date");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Fileincorrect.class)
    public ResponseEntity<?> handleFileAreIncorrect(Fileincorrect ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.CONFLICT.value());
        exceptionMOdel.setTitle("File Format nor Supported ! please try again ");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ImageException.class)
    public ResponseEntity<?> handleImgaeAlreadyExists(ImageException ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.CONFLICT.value());
        exceptionMOdel.setTitle("Attempted to upload that already exists in the system");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFound ex) {

        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionMOdel.setTitle("Sorry ! Resource Not Found in  Our System");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFound ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionMOdel.setTitle("Sorry ! User Not Found in this Name Or Our System");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RoleNotFound.class)
    public ResponseEntity<?> handleRoleNotFound(RoleNotFound ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionMOdel.setTitle("Sorry ! Role Found in this Name Or Our System");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(TimeExtendException.class)
    public ResponseEntity<?> handleTimeMismatch(TimeExtendException ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        exceptionMOdel.setTitle("Sorry ! Time Extend Exception");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(ProductCategoryNotFound.class)
    public ResponseEntity<?> handleProductCategoryNotFound(ProductCategoryNotFound ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        exceptionMOdel.setTitle("Sorry ! Product Category Not Found");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(ItemNotFound.class)
    public ResponseEntity<?> handleItemNotFound(ItemNotFound ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        exceptionMOdel.setTitle("Sorry ! Item Not Found");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(UnitNotFound.class)
    public ResponseEntity<?> handleUnitNotFound(UnitNotFound ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        exceptionMOdel.setTitle("Sorry ! Unit Not Found");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(OutOfStock.class)
    public ResponseEntity<?> handleUnitNotFound(OutOfStock ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionMOdel.setTitle("Sorry ! Out Of Stock");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(PasswordIncorrect.class)
    public ResponseEntity<?> PasswordIncorrect(PasswordIncorrect ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.UNAUTHORIZED.value());
        exceptionMOdel.setTitle("Sorry ! Password Incorrect");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AddItemException.class)
    public ResponseEntity<?> AddItemException(AddItemException ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value());
        exceptionMOdel.setTitle("Sorry ! Add Item Exception");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
    @ExceptionHandler(MachineNotFound.class)
    public ResponseEntity<?> MachineNotFound(MachineNotFound ex) {
        ExceptionMOdel exceptionMOdel = new ExceptionMOdel();
        exceptionMOdel.setDetails(ex.getClass().getCanonicalName());
        exceptionMOdel.setDeveloperMessage(ex.fillInStackTrace().getMessage());
        exceptionMOdel.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionMOdel.setTitle("Sorry ! MachineNotFound");
        exceptionMOdel.setTimestamp((String.valueOf(LocalDateTime.now())));
        return new ResponseEntity<>(exceptionMOdel, HttpStatus.NOT_FOUND);
    }
}
