package com.yaqa.web;

import com.yaqa.exception.ValidationException;
import com.yaqa.model.Comment;
import com.yaqa.model.LikeResult;
import com.yaqa.model.Question;
import com.yaqa.model.QuestionWithComments;
import com.yaqa.model.Tag;
import com.yaqa.service.QuestionService;
import com.yaqa.web.model.CreateQuestionRequest;
import com.yaqa.web.model.PostCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Question> getAllQuestions() {
        return questionService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public QuestionWithComments getQuestionById(@PathVariable("id") Long id) {
        return questionService.getByIdWithComments(id);
    }

    @RequestMapping(value = "/tag/{tag}", method = RequestMethod.GET)
    public List<Question> getQuestionsByTag(@PathVariable("tag") String tagName) {
        return questionService.getByTagName(tagName);
    }

    @RequestMapping(method = RequestMethod.POST)
    public QuestionWithComments createQuestion(@Valid @RequestBody CreateQuestionRequest questionRequest,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        final List<Tag> tags = questionRequest.getTags()
                .stream()
                .map(t -> new Tag(null, t.getTagName()))
                .collect(Collectors.toList());

        final Question question = new Question(null,
                questionRequest.getTitle(),
                questionRequest.getBody(),
                tags
        );
        return questionService.createNewQuestion(question);
    }

    @RequestMapping(value = "/{id}/like", method = RequestMethod.GET)
    public LikeResult likeQuestion(@PathVariable("id") Long id) {
        return questionService.likeQuestion(id);
    }

    @RequestMapping(value = "/{id}/comment", method = RequestMethod.POST)
    public QuestionWithComments postComment(@PathVariable("id") Long questionId,
                                            @Valid @RequestBody PostCommentRequest postCommentRequest,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        return questionService.postComment(questionId, new Comment(postCommentRequest.getBody()));
    }
}
