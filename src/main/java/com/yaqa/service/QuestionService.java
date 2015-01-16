package com.yaqa.service;

import com.yaqa.model.Comment;
import com.yaqa.model.LikeResult;
import com.yaqa.model.Question;
import com.yaqa.model.QuestionWithComments;

import java.util.List;

public interface QuestionService {

    List<Question> getAll();

    Question getById(Long id);

    QuestionWithComments getByIdWithComments(Long id);

    List<Question> getByTagName(String tagName);

    List<Question> getByUserSubscription();

    QuestionWithComments createNewQuestion(Question question);

    QuestionWithComments postComment(Long questionId, Comment comment);

    QuestionWithComments editComment(Long commentId, Comment comment);

    LikeResult likeQuestion(Long id);
}
