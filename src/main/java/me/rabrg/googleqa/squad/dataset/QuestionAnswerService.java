package me.rabrg.googleqa.squad.dataset;

import me.rabrg.googleqa.entity.Sentence;
import me.rabrg.googleqa.util.GoogleLanguageUtil;

import java.util.ArrayList;
import java.util.List;

public final class QuestionAnswerService {

    private List<Answer> answers;
    private String id;
    private String question;

    private List<Sentence> questionSentences;

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<Sentence> getQuestionSentences() {
        return questionSentences;
    }

    @Override
    public String toString() {
        return "QuestionAnswerService{" +
                "answers=" + answers +
                ", id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", questionSentences=" + questionSentences +
                '}';
    }
}
