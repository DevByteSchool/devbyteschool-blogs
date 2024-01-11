package com.devbyteschool.blogs.controller;

import com.devbyteschool.blogs.dto.UpdateBlogRequest;
import com.devbyteschool.blogs.jpa.BlogRepository;
import com.devbyteschool.blogs.model.Blog;
import com.devbyteschool.blogs.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = BlogController.class)
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BlogService blogService;

    @Test
    public void testUpdateBlogValidationFailed() throws Exception {

        UpdateBlogRequest updateBlogRequest = new UpdateBlogRequest();
        updateBlogRequest.setBlogId("");
        updateBlogRequest.setTitle("Spring Boot Blog Title.");
        updateBlogRequest.setDescription("Spring Boot Blog Description.");
        updateBlogRequest.setUserId("1001");
        updateBlogRequest.setPublish(true);


        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/blogs")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBlogRequest))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("{\"message\":\"BlogId is required parameter.\"}", response.getContentAsString());

    }

    @Test
    public void testUpdateBlogRecordNotFound() throws Exception {

        UpdateBlogRequest updateBlogRequest = new UpdateBlogRequest();
        updateBlogRequest.setBlogId("101");
        updateBlogRequest.setTitle("Spring Boot Blog Title.");
        updateBlogRequest.setDescription("Spring Boot Blog Description.");
        updateBlogRequest.setUserId("1001");
        updateBlogRequest.setPublish(true);

        when(blogService.updateBlog(any(UpdateBlogRequest.class))).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/blogs")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBlogRequest))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("{\"message\":\"Record not present in database.\"}", response.getContentAsString());

    }


    @Test
    public void testUpdateBlogRecordSuccess() throws Exception {

        UpdateBlogRequest updateBlogRequest = new UpdateBlogRequest();
        updateBlogRequest.setBlogId("102");
        updateBlogRequest.setTitle("Updated Spring Boot Blog Title.");
        updateBlogRequest.setDescription("Updated Spring Boot Blog Description.");
        updateBlogRequest.setUserId("1001");
        updateBlogRequest.setPublish(false);

        Blog blog = new Blog();
        blog.setBlogId("102");
        blog.setTitle("Spring Boot Blog Title.");
        blog.setDescription("Spring Boot Blog Description.");
        blog.setUserId("1001");
        blog.setPublish(true);


        when(blogService.updateBlog(any(UpdateBlogRequest.class))).thenReturn(blog);


        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/blogs")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBlogRequest))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());


    }
}
