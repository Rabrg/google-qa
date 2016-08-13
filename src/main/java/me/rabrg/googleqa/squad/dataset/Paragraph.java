package me.rabrg.googleqa.squad.dataset;

import me.rabrg.googleqa.entity.Sentence;
import me.rabrg.googleqa.util.GoogleLanguageUtil;

import java.util.ArrayList;
import java.util.List;

public final class Paragraph {

    private String context;
    private List<QuestionAnswerService> qas;

    private List<Sentence> contextSentences;

    public String getContext() {
        return context;
    }

    public List<QuestionAnswerService> getQas() {
        return qas;
    }

    public List<Sentence> getContextSentences() {
        return contextSentences;
    }

    @Override
    public String toString() {
        return "Paragraph{" +
                "context='" + context + '\'' +
                ", qas=" + qas +
                ", contextSentences=" + contextSentences +
                '}';
    }
}
