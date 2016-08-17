package me.rabrg.googleqa.squad.dataset;

import me.rabrg.googleqa.entity.Dependency;
import me.rabrg.googleqa.entity.NamedEntity;
import me.rabrg.googleqa.entity.Sentence;
import me.rabrg.googleqa.entity.Word;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class QuestionClassificationTest {

    private static final List<String> QUESTION_WORDS = Arrays.asList("what", "how", "who", "when", "which", "where",
            "why", "whose", "can", "do", "be", "name", "blank", "whom", "have");

    public static void main(final String[] args) throws IOException {
//        final List<String> lines = Files.readAllLines(Paths.get("question-label.tsv"));
        final Dataset dataset = Dataset.loadDataset(SquadDatasetTest.class
                .getResourceAsStream("/dev-v1.0-ner.json"));
        final Map<String, Map<String, Integer>> relationCountMap = new HashMap<>();
        final Map<String, Integer> relationTotals = new HashMap<>();

        for (final Article article : dataset.getData()) {
            for (final Paragraph paragraph : article.getParagraphs()) {
                for (final QuestionAnswerService qas : paragraph.getQas()) {
                    for (final Sentence sentence : qas.getQuestionSentences()) {
                        final String type = getType(sentence);
                        if ("who".equals(type) || "whose".equals(type) || "whom".equals(type)) {
                            final String subject = sentence.getSubject() != null ? String.join(" ", sentence.getSubject().stream().map(Word::getWord).collect(Collectors.toList())) : null;
                            final String root = sentence.getRoot() != null ? sentence.getRoot().getWord() : null;
                            final String object = sentence.getObject() != null ? String.join(" ", sentence.getObject().stream().map(Word::getWord).collect(Collectors.toList())) : null;

                            if (subject != null && root != null) {
                                final Map<String, Integer> innerCountMap = relationCountMap.getOrDefault(root, new HashMap<>());
                                final int count = innerCountMap.getOrDefault(subject, 0);
                                innerCountMap.put(subject, count + 1);
                                relationCountMap.putIfAbsent(root, innerCountMap);

                                relationTotals.put(root, relationTotals.getOrDefault(root, 0) + 1);
                            }
                            break;
                        }
                    }
                }
            }
        }

        for (final Map.Entry<String, Map<String, Integer>> entry : relationCountMap.entrySet()) {
            for (final Map.Entry<String, Integer> subEntry : entry.getValue().entrySet()) {
                if (subEntry.getValue() >= 3) {
                    System.out.println(entry.getKey() + "\t" + subEntry.getKey() + "\t" + (((double) subEntry.getValue()) / relationTotals.get(entry.getKey())));
                }
            }
        }
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
