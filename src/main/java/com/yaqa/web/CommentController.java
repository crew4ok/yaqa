package com.yaqa.web;

import com.yaqa.exception.ValidationException;
import com.yaqa.model.Comment;
import com.yaqa.model.QuestionWithComments;
import com.yaqa.service.QuestionService;
import com.yaqa.web.model.PostCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public QuestionWithComments editComment(@PathVariable("id") Long commentId,
                                            @Valid @RequestBody PostCommentRequest postCommentRequest,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        return questionService.editComment(commentId, new Comment(postCommentRequest.getBody()));
    }
}
