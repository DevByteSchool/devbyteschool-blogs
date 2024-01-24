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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class UserService implements UserDetailsService {

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
      BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
      User user=new User();
      BeanUtils.copyProperties(registerUserRequest,user);
      user.setCreatedAt(LocalDateTime.now());
      user.setUpdatedAt(LocalDateTime.now());
      user.setIsAcountVerify(0);
      user.setIsSocialRegister(0);
      user.setPassword(bCryptPasswordEncoder.encode(registerUserRequest.getPassword()));
      user.setOtp(appUtils.getFourDigitNumber());
      User saveUser=userRespository.save(user);
      String emailVerificationFullLink = mailVerificationLink+user.getOtp()+saveUser.getUserId();
      emailUtils.sendMail(fromEmailId,
              registerUserRequest.getUserName(),
              "Regarding devbyteschool account verification.",
              "Click on below link to verify your email id : \n");
      log.info("Email verification link  : "+ emailVerificationFullLink);
      return user;
   }

   public User login(LoginUserRequest loginUserRequest) throws  Exception{
      User userTemp = userRespository.findByUserName(loginUserRequest.getUserName());
      if(Objects.isNull(userTemp)) throw new RecordNotFoundException("Email id is not yet register.Please register and login again.");
      User user = userRespository.findByUserName(loginUserRequest.getUserName());
      if(user.getIsAcountVerify()==0 && loginUserRequest.getIsSocialRegister()==0) throw new
              AuthenticationFailedException("Your mail id verification is pending.Please check your mail box and verify your mail id.");
      return user;
   }

   public User verifyEmailId(Integer otp,String userId){

       User user=userRespository.findByUserIdAndOtp(userId,otp);
       if(Objects.isNull(user)){
          return null;
       }else {
          user.setIsAcountVerify(1);
          userRespository.save(user);
       }
       return user;


   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRespository.findByUserName(username);
      if(user == null){
         log.error("UserService:loadUserByUsername Username not found: " + username);
         throw new UsernameNotFoundException("could not found user..!!");
      }
      log.info("UserService:loadUserByUsername User Authenticated Successfully..!!!");
      return new UserInfoDetails(user);
   }
}
