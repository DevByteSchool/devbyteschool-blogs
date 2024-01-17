package com.devbyteschool.blogs.service;

import com.devbyteschool.blogs.config.EmailUtils;
import com.devbyteschool.blogs.dto.LoginUserRequest;
import com.devbyteschool.blogs.dto.RegisterUserRequest;
import com.devbyteschool.blogs.exception.AuthenticationFailedException;
import com.devbyteschool.blogs.exception.RecordNotFoundException;
import com.devbyteschool.blogs.exception.UserAlreadyRegisterException;
import com.devbyteschool.blogs.jpa.AppUserRespository;
import com.devbyteschool.blogs.model.User;
import com.devbyteschool.blogs.utils.AppUtils;
import org.springframework.beans.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class UserService {

   @Autowired
   private AppUserRespository userRespository;

   @Autowired
   private EmailUtils emailUtils;

   @Value("${spring.mail.username}")
   private String fromEmailId;

   @Value("${app.mail.verification-link}")
   private String mailVerificationLink;

   @Autowired
   private AppUtils appUtils;



   public User registorUser(RegisterUserRequest registerUserRequest) {
      User usertemp =  userRespository.findByUserName(registerUserRequest.getUserName());
      if(!Objects.isNull(usertemp)) throw  new  UserAlreadyRegisterException("Email id already present in system.Please try with forgot password.");

      User user=new User();
      BeanUtils.copyProperties(registerUserRequest,user);
      user.setCreatedAt(LocalDateTime.now());
      user.setUpdatedAt(LocalDateTime.now());
      user.setIsAcountVerify(0);
      user.setIsSocialRegister(0);
      user.setOtp(appUtils.getFourDigitNumber());
      User saveUser=userRespository.save(user);
      emailUtils.sendMail(fromEmailId,
              registerUserRequest.getUserName(),
              "Regarding devbyteschool account verification.",
              "Click on below link to verify your email id : \n"+mailVerificationLink+user.getOtp()+saveUser.getUserId());
      return user;
   }

   public User login(LoginUserRequest loginUserRequest){
      User userTemp = userRespository.findByUserName(loginUserRequest.getUserName());
      if(Objects.isNull(userTemp)) throw new RecordNotFoundException("Email id is not yet register.Please register and login again.");

      User user = userRespository.findByUserNameAndPassword(loginUserRequest.getUserName(),loginUserRequest.getPassword());

      if(user.getIsAcountVerify()==0 && loginUserRequest.getIsSocialRegister()==0) throw new
              AuthenticationFailedException("Your mail id verification is pending.Please check your mail box and verify your mail id.");

      if(Objects.isNull(user)) throw new
              AuthenticationFailedException("Password is incorrect.");

      return user;
   }

   public boolean verifyEmailId(Integer otp,String userId){
       User user = userRespository.findByUserIdAndOtp(userId,otp);
       return Objects.isNull(user);
   }

}
