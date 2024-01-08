package com.devbyteschool.blogs.controller;

import com.devbyteschool.blogs.dto.UpdateBlogRequest;
import com.devbyteschool.blogs.jpa.BlogRepository;
import com.devbyteschool.blogs.model.Blog;
import com.devbyteschool.blogs.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BlogController.class)
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogService blogService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BlogRepository blogRepository;


    private String updateBlogSuccessRequest="";


    @Test
    public void testUpdateBlogRecordNotFound() throws Exception {

        UpdateBlogRequest updateBlogRequest = new UpdateBlogRequest();
        updateBlogRequest.setBlogId("101");
        updateBlogRequest.setTitle("Spring Boot Blog Title.");
        updateBlogRequest.setDescription("Spring Boot Blog Description.");
        updateBlogRequest.setUserId("1001");
        updateBlogRequest.setPublish(true);

        Mockito.when(blogService.updateBlog(updateBlogRequest)).thenReturn(null);


        try {
            RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/blogs")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(updateBlogRequest))
                    .contentType(MediaType.APPLICATION_JSON);

            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
            assertEquals("{\"message\":\"Record not present in database.\"}", response.getContentAsString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }


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
        updateBlogRequest.setPublish(true);

        Mockito.when(blogService.updateBlog(updateBlogRequest)).thenReturn(blog);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/blogs")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBlogRequest))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());


    }
}
