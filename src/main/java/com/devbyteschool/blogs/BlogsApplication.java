package com.devbyteschool.blogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableCaching
public class BlogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogsApplication.class, args);
	}

}
