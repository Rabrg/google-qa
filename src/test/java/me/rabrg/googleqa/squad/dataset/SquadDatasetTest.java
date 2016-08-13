package me.rabrg.googleqa.squad.dataset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.rabrg.googleqa.entity.Sentence;
import me.rabrg.googleqa.entity.Word;
import me.rabrg.googleqa.util.GoogleLanguageUtil;

import java.io.IOException;
import java.util.*;

public class SquadDatasetTest {

    private static final List<String> QUESTION_WORDS = Arrays.asList("what", "how", "who", "when", "which", "where",
            "why", "whose", "can", "do", "be", "name", "blank", "whom", "have"); // lemma of is, are, were, was = be

    public static void main(final String[] args) throws IOException {
        final Dataset dataset = Dataset.loadDataset(SquadDatasetTest.class
                .getResourceAsStream("/dev-v1.0-ner.json"));
        final Gson gson = new GsonBuilder().create();
        for (final Article article : dataset.getData()) {
            for (final Paragraph paragraph : article.getParagraphs()) {
                for (final QuestionAnswerService qas : paragraph.getQas()) {
                    for (final Sentence sentence : qas.getQuestionSentences()) {
                        System.out.println(sentence.getNamedEntities());
                    }
                }
            }
        }
//        for (final Article article : dataset.getData()) {
//            for (final Paragraph paragraph : article.getParagraphs()) {
//                for (final Sentence sentence : paragraph.getContextSentences()) {
//                    final List<String> strings = sentence.getApposStrings();
//                    if (strings.size() > 0) {
//                        System.out.print(sentence.getName() + "\t");
//                        for (final String string : strings) {
//                            System.out.print(string + "\t");
//                        }
//                        System.out.println();
//                    }
//                }
//            }
//        }

//        for (final Article article : dataset.getData()) {
//            for (final Paragraph paragraph : article.getParagraphs()) {
//                for (final QuestionAnswerService qas : paragraph.getQas()) {
//                    String type = null;
//                    for (final Sentence sentence : qas.getQuestionSentences()) {
//                        type = getType(sentence);
//                        if (type != null) {
//                            break;
//                        }
//                    }
//                    if ("who".equals(type) || "whose".equals(type) || "whom".equals(type))
//                        System.out.println(type + "\t\t" + qas.getQuestion());
//                }
//            }
//        }
    }

    private static String getType(final Sentence sentence) {
        final List<Word> words = sentence.getWords();
        final List<Word> questionWords = new ArrayList<>();
        for (final Word word : words) {
            if (QUESTION_WORDS.contains(lemma(word))) {
                questionWords.add(word);
            }
        }

        if (questionWords.size() == 1) {
            return lemma(questionWords.get(0));
        } else if (questionWords.size() > 1) {
            final String first = lemma(questionWords.get(0));
            final String second = lemma(questionWords.get(1));
            final String third = questionWords.size() > 2 ? lemma(questionWords.get(2)) : null;
            if ("how".equals(lemma(questionWords.get(questionWords.size() - 1)))) {
                return "how";
            } else if (QUESTION_WORDS.contains(lemma(words.get(words.size() - 2)))) {
                return lemma(words.get(words.size() - 2));
            } else if ("where".equals(first) && "did".equals(second)) {
                return "where";
            } else if ("what".equals(first) && "be".equals(second)) {
                return "what";
            } else if ("what".equals(first) && "do".equals(second)) {
                return "what";
            } else if ("where".equals(first) && "do".equals(second)) {
                return "where";
            } else if ("who".equals(first) && "do".equals(second)) {
                return "who";
            } else if ("which".equals(first) && "do".equals(second)) {
                return "which";
            } else if ("why".equals(first) && "be".equals(second)) {
                return "why";
            } else if ("who".equals(first) && "was".equals(second)) {
                return "who";
            } else if ("be".equals(first) && "what".equals(second)) {
                return "what";
            } else if ("where".equals(first) && "be".equals(second)) {
                return "where";
            } else if ("which".equals(first) && "be".equals(second)) {
                return "which";
            } else if ("who".equals(first) && "be".equals(second)) {
                return "who";
            } else if (sentence.getText().toLowerCase().contains("how many")) {
                return "how";
            } else if (sentence.getText().toLowerCase().contains("how much")) {
                return "how";
            } else if ("when".equals(first) && "do".equals(second)) {
                return "when";
            } else if ("when".equals(first) && "be".equals(second)) {
                return "when";
            } else if ("when".equals(first) && "who".equals(second)) {
                return "who";
            } else if ("how".equals(first) && "be".equals(second)) {
                return "how";
            } else if ("how".equals(first) && "do".equals(second)) {
                return "how";
            } else if ("be".equals(first) && "which".equals(second)) {
                return "which";
            } else if ("which".equals(first) && "who".equals(second)) {
                return "which";
            } else if ("whose".equals(first) && "be".equals(second)) {
                return "whose";
            } else if ("who".equals(first) && "do".equals(second)) {
                return "who";
            } else if ("who".equals(first) && "when".equals(second)) {
                return "who";
            } else if ("which".equals(first) && "which".equals(second)) {
                return "which";
            } else if ("what".equals(first) && "when".equals(second)) {
                return "what";
            } else if ("where".equals(first) && "can".equals(second)) {
                return "where";
            } else if ("why".equals(first) && "do".equals(second)) {
                return "why";
            } else if ("who".equals(first) && "what".equals(second)) {
                return "who";
            } else if ("why".equals(first) && "do".equals(second)) {
                return "why";
            } else if ("what".equals(first) && "can".equals(second)) {
                return "what";
            } else if ("whose".equals(first) && "do".equals(second)) {
                return "whose";
            } else if ("why".equals(first) && "do".equals(second)) {
                return "why";
            } else if ("do".equals(first) && "what".equals(second)) {
                return "what";
            } else if ("what".equals(first) && "why".equals(second)) {
                return "what";
            } else if ("what".equals(first) && "why".equals(second)) {
                return "what";
            } else if ("when".equals(first) && "what".equals(second)) {
                return "what";
            } else if ("how".equals(first) && "can".equals(second)) {
                return "how";
            } else if ("what".equals(first) && "who".equals(second)) {
                return "what";
            } else if ("who".equals(first) && "be".equals(second)) {
                return "who";
            } else if ("who".equals(first) && "which".equals(second)) {
                return "who";
            } else if ("what".equals(first) && "be".equals(second)) {
                return "what";
            } else if ("what".equals(first) && "be".equals(second)) {
                return "what";
            } else if ("can".equals(first) && "be".equals(second) && "what".equals(third)) {
                return "what";
            } else if ("which".equals(first) && "can".equals(second) && "be".equals(third)) {
                return "which";
            } else if ("how".equals(first) && "have".equals(second)) {
                return "how";
            } else if ("which".equals(first) && "have".equals(second)) {
                return "which";
            } else if (first.equals(second)) {
                return first;
            } else if ("name".equals(first)) {
                return first;
            } else if ("who".equals(first)) {
                return first;
            } else if ("what".equals(first)) {
                return first;
            }
        }
        return null;
    }

    private static String lemma(final Word word) {
        return word.getLemma().toLowerCase();
    }
}
