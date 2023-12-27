package com.devbyteschool.blogs.controller;


import com.devbyteschool.blogs.dto.CommonPaginationRequest;
import com.devbyteschool.blogs.dto.CreateBlogRequest;
import com.devbyteschool.blogs.dto.DBSResponseEntity;
import com.devbyteschool.blogs.dto.UpdateBlogRequest;
import com.devbyteschool.blogs.model.Blog;
import com.devbyteschool.blogs.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class BlogController {

    @Autowired
    private BlogService blogService;


    @PostMapping("v1/blogs")
    public ResponseEntity<DBSResponseEntity> createBlogCall(@Valid @RequestBody CreateBlogRequest createBlogRequest) {
        Blog blog = new Blog();
        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();

        try {
            BeanUtils.copyProperties(createBlogRequest, blog);
            Blog createdBlog = blogService.createBlog(blog);
            dbsResponseEntity.setMessage("Blog created successfully.");
            dbsResponseEntity.setData(createdBlog);
            return ResponseEntity.ok(dbsResponseEntity);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("v1/blogs")
    public ResponseEntity<DBSResponseEntity> updateBlogCall(@Valid @RequestBody UpdateBlogRequest updateBlogRequest) {
        Blog blog = new Blog();
        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();

        try {
            BeanUtils.copyProperties(updateBlogRequest, blog);
            Blog updatedBlog = blogService.updateBlog(blog);
            dbsResponseEntity.setMessage("Blog updated successfully.");
            dbsResponseEntity.setData(updatedBlog);
            return ResponseEntity.ok(dbsResponseEntity);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("v1/blogs/{blogId}")
    public ResponseEntity<DBSResponseEntity> getBlogCall(@PathVariable String blogId) {
        Blog blog = new Blog();
        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();

        try {
            Blog getBlog = blogService.getBlog(blogId);
            dbsResponseEntity.setData(getBlog);
            return ResponseEntity.ok(dbsResponseEntity);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping("v1/blogs/{blogId}")
    public ResponseEntity<DBSResponseEntity> deleteBlogCall(@PathVariable String blogId) {
        Blog blog = new Blog();
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
    public ResponseEntity<DBSResponseEntity> getBlogsCall(@RequestParam(defaultValue = "0") Integer pageNo,
                                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "1") String userId
    ) {
        Blog blog = new Blog();
        CommonPaginationRequest commonPaginationRequest = new CommonPaginationRequest();
        commonPaginationRequest.setPageNo(pageNo);
        commonPaginationRequest.setPageSize(pageSize);
        commonPaginationRequest.setValue(userId);
        commonPaginationRequest.setSortBy(sortBy);
        DBSResponseEntity dbsResponseEntity = new DBSResponseEntity();

        try {
            List<Blog> Blogs = blogService.getBlogs(commonPaginationRequest);
            dbsResponseEntity.setData(Blogs);
            return ResponseEntity.ok(dbsResponseEntity);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}
