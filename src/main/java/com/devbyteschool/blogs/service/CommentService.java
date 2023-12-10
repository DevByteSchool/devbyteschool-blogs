package com.devbyteschool.blogs.service;


import com.devbyteschool.blogs.jpa.CommentRespository;
import com.devbyteschool.blogs.model.Blog;
import com.devbyteschool.blogs.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRespository commentRespository;

    public Comment createComment(Comment comment) throws Exception {
        return commentRespository.save(comment);
    }

    public Comment updateComment(Comment comment) throws Exception {
        return commentRespository.save(comment);
    }

    public Comment deleteComment(String commentId) throws Exception {
        return commentRespository.deleteByCommentId(commentId);
    }


    public List<Comment> getComments(String blogId, Pageable pageable) throws Exception {
        return commentRespository.findByBlogId(blogId, pageable);
    }

}
