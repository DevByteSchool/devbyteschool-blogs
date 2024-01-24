package com.devbyteschool.blogs.controller;

import com.devbyteschool.blogs.config.JwtService;
import com.devbyteschool.blogs.dto.*;
import com.devbyteschool.blogs.exception.AuthenticationFailedException;
import com.devbyteschool.blogs.exception.RecordNotFoundException;
import com.devbyteschool.blogs.exception.UserAlreadyRegisterException;
import com.devbyteschool.blogs.model.User;
import com.devbyteschool.blogs.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PutMapping("v1/register")
    public ResponseEntity<DBSResponseEntity<JwtResponse>> registerCall(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();
        log.info("BlogController:loginCall request received with body : {}", registerUserRequest.toString());
        try {
            User user = userService.registorUser(registerUserRequest);
            dbsResponseEntity.setMessage("Registration done successfully.Please check your mail for email verification.");
            return ResponseEntity.ok(dbsResponseEntity);
        } catch (UserAlreadyRegisterException exception) {
            log.debug("UserController:registerCall user already present in system : {}", exception);
            throw exception;
        } catch (Exception exception) {
            log.debug("UserController:registerCall something when wrong : {}", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("v1/login")
    public ResponseEntity<DBSResponseEntity<JwtResponse>> loginCall(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();
        log.info("BlogController:loginCall request received with body : {}", loginUserRequest.toString());
        try {
            User user = userService.login(loginUserRequest);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserRequest.getUserName(), loginUserRequest.getPassword()));
            if(!authentication.isAuthenticated()) throw new AuthenticationFailedException("Password is incorrect.");
            JwtResponse jwtResponse = new JwtResponse( jwtService.GenerateToken(user.getUserName()));
            dbsResponseEntity.setData(jwtResponse);
            dbsResponseEntity.setMessage("User login successfully.");
            return ResponseEntity.ok(dbsResponseEntity);
        } catch (RecordNotFoundException exception) {
            log.debug("UserController:loginCall user not yet register : {}", exception);
            throw exception;
        } catch (AuthenticationFailedException exception) {
            log.debug("UserController:loginCall Authentication failed exception : {}", exception);
            throw exception;
        } catch (Exception exception) {
            log.debug("UserController:loginCall something when wrong : {}", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("v1/verifyEmailId/{code}")
    public ResponseEntity<DBSResponseEntity<JwtResponse>> verifyEmailIdCall(@PathVariable String code) {
        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();
        log.info("UserController:verifyEmailIdCall request received with body : {}", code);
        try {
            User user = userService.verifyEmailId(Integer.parseInt(code.substring(0,4)),code.substring(4));
            if(Objects.isNull(user)){
                  throw new RecordNotFoundException("Record not present in system.");
            }else {
                JwtResponse jwtResponse = new JwtResponse(jwtService.GenerateToken(user.getUserName()));
                dbsResponseEntity.setData(jwtResponse);
                dbsResponseEntity.setMessage("User login successfully.");
                return ResponseEntity.ok(dbsResponseEntity);
            }
        } catch (RecordNotFoundException exception) {
            log.debug("UserController:verifyEmailIdCall user not yet register : {}", exception);
            throw exception;
        } catch (AuthenticationFailedException exception) {
            log.debug("UserController:verifyEmailIdCall Authentication failed exception : {}", exception);
            throw exception;
        } catch (Exception exception) {
            log.debug("UserController:verifyEmailIdCall something when wrong : {}", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}
