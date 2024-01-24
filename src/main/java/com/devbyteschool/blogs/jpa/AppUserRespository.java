package com.devbyteschool.blogs.jpa;

import com.devbyteschool.blogs.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRespository extends MongoRepository<User, String> {
    User findByUserNameAndPassword(String userName, String password);

    User findByUserName(String userName);

    User findByUserIdAndOtp(String userId, Integer otp);

}
