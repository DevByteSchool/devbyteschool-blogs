package com.devbyteschool.blogs.service;

import com.devbyteschool.blogs.dto.LoginUserRequest;
import com.devbyteschool.blogs.dto.RegisterUserRequest;
import com.devbyteschool.blogs.exception.AuthenticationFailedException;
import com.devbyteschool.blogs.exception.RecordNotFoundException;
import com.devbyteschool.blogs.exception.UserAlreadyRegisterException;
import com.devbyteschool.blogs.jpa.AppUserRespository;
import com.devbyteschool.blogs.model.User;
import org.springframework.beans.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class UserService {

   @Autowired
   private AppUserRespository userRespository;


   public User registorUser(RegisterUserRequest registerUserRequest) {
      User usertemp =  userRespository.findByUserName(registerUserRequest.getUserName());
      if(!Objects.isNull(usertemp)) throw  new  UserAlreadyRegisterException("Email id already present in system.Please try with forgot password.");

      User user=new User();
      BeanUtils.copyProperties(registerUserRequest,user);
      user.setCreatedAt(LocalDateTime.now());
      user.setUpdatedAt(LocalDateTime.now());
      return userRespository.save(user);
   }

   public User login(LoginUserRequest loginUserRequest){
      User userTemp = userRespository.findByUserName(loginUserRequest.getUserName());
      if(Objects.isNull(userTemp)) throw new RecordNotFoundException("Email id is not yet register.Please register and login again.");

      User user = userRespository.findByUserNameAndPassword(loginUserRequest.getUserName(),loginUserRequest.getPassword());

      if(Objects.isNull(user)) throw new AuthenticationFailedException("Password is incorrect.");

      return user;
   }

}
