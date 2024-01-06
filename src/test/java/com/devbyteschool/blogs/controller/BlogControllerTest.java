package com.devbyteschool.blogs.controller;

import com.devbyteschool.blogs.controller.BlogController;
import com.devbyteschool.blogs.dto.UpdateBlogRequest;
import com.devbyteschool.blogs.exception.RecordNotFoundException;
import com.devbyteschool.blogs.jpa.BlogRepository;
import com.devbyteschool.blogs.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    @Autowired
    MongoTemplate mongoTemplate;

    @MockBean
    private BlogService blogService;

    @MockBean
    private BlogRepository blogRepository;

    @Test
    public void testBlogRecordNotFound() throws Exception {

        UpdateBlogRequest updateBlogRequest = new UpdateBlogRequest();
        updateBlogRequest.setBlogId("101");
        updateBlogRequest.setTitle("Spring Boot Blog Title.");
        updateBlogRequest.setDescription("Spring Boot Blog Description.");
        updateBlogRequest.setUserId("1001");
        updateBlogRequest.setPublish(true);

        Mockito.when(blogService.updateBlog(updateBlogRequest)).thenThrow(RecordNotFoundException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/v1/blogs")
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateBlogRequest))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Blog not found", response.getContentAsString());

    }
}
