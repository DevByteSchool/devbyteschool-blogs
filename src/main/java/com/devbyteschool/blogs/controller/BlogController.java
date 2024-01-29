package com.devbyteschool.blogs.controller;


import com.devbyteschool.blogs.dto.CommonPaginationRequest;
import com.devbyteschool.blogs.dto.CreateBlogRequest;
import com.devbyteschool.blogs.dto.DBSResponseEntity;
import com.devbyteschool.blogs.dto.UpdateBlogRequest;
import com.devbyteschool.blogs.exception.RecordNotFoundException;
import com.devbyteschool.blogs.model.Blog;
import com.devbyteschool.blogs.service.BlogService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@Slf4j
public class BlogController {


    @Autowired
    private BlogService blogService;


    @PostMapping("v1/blogs")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<DBSResponseEntity> createBlogCall(@Valid @RequestBody CreateBlogRequest createBlogRequest) {

        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();

        log.info("BlogController:createBlogCall request received with body : {}", createBlogRequest.toString());

        try {
            Blog createdBlog = blogService.createBlog(createBlogRequest);
            log.info("BlogService:updateBlog record save successfully with blogId : {}", createdBlog.getBlogId());
            dbsResponseEntity.setMessage("Blog created successfully.");
            dbsResponseEntity.setData(createdBlog);
            return ResponseEntity.ok(dbsResponseEntity);
        } catch (Exception exception) {
            log.debug("BlogController:createBlogCall something when wrong : {}", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("v1/blogs")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<DBSResponseEntity> updateBlogCall(@Valid @RequestBody UpdateBlogRequest updateBlogRequest) {
        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();

        log.info("BlogController:updateBlogCall request received with body : {}", updateBlogRequest.toString());

        try {
            Blog updatedBlog = blogService.updateBlog(updateBlogRequest);
            if (ObjectUtils.isEmpty(updatedBlog)) throw new RecordNotFoundException("Record not present in database.");

            dbsResponseEntity.setMessage("Blog updated successfully.");
            dbsResponseEntity.setData(updatedBlog);
            return ResponseEntity.ok(dbsResponseEntity);
        } catch (RecordNotFoundException exception) {
            log.debug("BlogController:updateBlogCall record not found for blogId : {}", exception);
            throw exception;
        } catch (Exception exception) {
            log.debug("BlogController:updateBlogCall something when wrong : {}", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("v1/blogs/{blogId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<DBSResponseEntity> getBlogCall(@PathVariable String blogId) {
        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();

        try {
            Blog getBlog = blogService.getBlog(blogId);
            dbsResponseEntity.setData(getBlog);
            return ResponseEntity.ok(dbsResponseEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping("v1/blogs/{blogId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    //@PreAuthorize("hasRole('ROLE_USER','ROLE_ADMIN')") you can used this also.
    public ResponseEntity<DBSResponseEntity> deleteBlogCall(@PathVariable String blogId) {
        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();

        try {
            blogService.deleteBlog(blogId);
            dbsResponseEntity.setMessage("Blog deleted successfully.");
            return ResponseEntity.ok(dbsResponseEntity);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("v1/blogs")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<DBSResponseEntity> getBlogsCall(@RequestParam(defaultValue = "0") Integer pageNo,
                                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "1") String userId
    ) {
        CommonPaginationRequest commonPaginationRequest = new CommonPaginationRequest();
        commonPaginationRequest.setPageNo(pageNo);
        commonPaginationRequest.setPageSize(pageSize);
        commonPaginationRequest.setValue(userId);
        commonPaginationRequest.setSortBy(sortBy);
        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();

        try {
            List<Blog> blogs = blogService.getBlogs(commonPaginationRequest);
            dbsResponseEntity.setData(blogs);
            return ResponseEntity.ok(dbsResponseEntity);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}
