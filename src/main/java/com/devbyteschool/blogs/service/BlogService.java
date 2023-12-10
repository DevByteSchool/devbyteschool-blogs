package com.devbyteschool.blogs.service;

import com.devbyteschool.blogs.jpa.BlogRepository;
import com.devbyteschool.blogs.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;


    public Blog createBlog(Blog blog) throws  Exception{
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Blog blog) throws  Exception{
        return blogRepository.save(blog);
    }

    public Blog deleteBlog(String blidId) throws  Exception{
        return blogRepository.deleteByBlogId(blidId);
    }


    public Blog getBlog(String blidId) throws  Exception{
        return blogRepository.findByBlogId(blidId);
    }

    public List<Blog> getBlogs(String userId, Pageable pageable) throws  Exception{
        return blogRepository.findByUserId(userId,pageable);
    }

}
