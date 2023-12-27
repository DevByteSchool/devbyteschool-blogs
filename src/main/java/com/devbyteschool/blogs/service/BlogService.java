package com.devbyteschool.blogs.service;

import com.devbyteschool.blogs.dto.CommonPaginationRequest;
import com.devbyteschool.blogs.jpa.BlogRepository;
import com.devbyteschool.blogs.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;


    public Blog createBlog(Blog blog) throws Exception {
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Blog blog) throws Exception {
        return blogRepository.save(blog);
    }

    public void deleteBlog(String blidId) throws Exception {
        blogRepository.deleteByBlogId(blidId);
    }


    public Blog getBlog(String blidId) throws Exception {
        return blogRepository.findByBlogId(blidId);
    }

    public List<Blog> getBlogs(CommonPaginationRequest commonPaginationRequest) throws Exception {
        Pageable pageable = PageRequest.of(commonPaginationRequest.getPageNo(), commonPaginationRequest.getPageSize(),
                Sort.by(commonPaginationRequest.getSortBy()).descending());
        return blogRepository.findByUserId(commonPaginationRequest.getValue(), pageable);
    }

}
