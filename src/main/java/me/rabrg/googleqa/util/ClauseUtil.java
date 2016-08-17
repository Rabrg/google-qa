package me.rabrg.googleqa.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.rabrg.googleqa.entity.Dependency;
import me.rabrg.googleqa.entity.Sentence;
import me.rabrg.googleqa.entity.Word;
import me.rabrg.googleqa.squad.dataset.Article;
import me.rabrg.googleqa.squad.dataset.Dataset;
import me.rabrg.googleqa.squad.dataset.Paragraph;
import me.rabrg.googleqa.squad.dataset.QuestionAnswerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ClauseUtil {

    private static final List<String> IGNORE_LIST = Arrays.asList("P", "PARATAXIS", "CC", "CONJ");

    private ClauseUtil() {

    }

    public static void main(final String[] args) throws IOException {
//        final Sentence sentence = GoogleLanguageUtil.getSentence("The war was lost; consequently, the whole country was occupied.");
//        System.out.println(sentence.getText());
//        System.out.println(extractClasuses(sentence));
//        System.out.println();

        final Dataset dataset = Dataset.loadDataset(ClauseUtil.class.getResourceAsStream("/dev-v1.0-ner.json"));
        for (final Article article : dataset.getData()) {
            for (final Paragraph paragraph : article.getParagraphs()) {
                for (final Sentence sentence : paragraph.getContextSentences()) {
                    final List<String> clauses = extractClasuses(sentence);
                    if (clauses.size() > 1) {
                        System.out.print(sentence.getText() + "\t");
                        for (final String clause : clauses) {
                            System.out.print(clause + "\t");
                        }
                        System.out.println();
                    }
                }
                break;
            }
        }
    }

    public static List<String> extractClasuses(final Sentence sentence) {
        final List<String> clauses = new ArrayList<>();

        final List<Dependency> subjectDependencies = new ArrayList<>();
        for (final Dependency dependency : sentence.getDependencies()) {
            if (dependency.getReln().contains("SUBJ")) {
                subjectDependencies.add(dependency);
            }
//            System.out.println(dependency);
        }

        for (final Dependency subjectDependency : subjectDependencies) {
            final List<Word> words = new ArrayList<>();
            final List<Word> ignoreWords = new ArrayList<>();
            words.add(subjectDependency.getDep());
            words.add(subjectDependency.getGov());
            int size;
            do {
                size = words.size();
                for (final Dependency dependency : sentence.getDependencies()) {
                    if (words.contains(dependency.getGov()) && !words.contains(dependency.getDep())) {
                        boolean valid = true;
                        for (final Dependency dependency2 : subjectDependencies) {
                            if (dependency2.getDep().getIndex() > subjectDependency.getDep().getIndex() && dependency.getDep().getIndex() > dependency2.getDep().getIndex()) {
                                valid = false;
                                break;
                            }
                        }
                        if (valid && "ADVCL".equals(dependency.getReln()) && ignoreWords.contains(dependency.getDep())) {
                            ignoreWords.add(dependency.getDep());
                        } else if (valid) {
                            words.add(dependency.getDep());
                        }
                    }
                }
            } while (size != words.size());
            words.addAll(ignoreWords);
            Collections.sort(words);
            final String clause = String.join(" ", words.stream().map(Word::getWord).collect(Collectors.toList()));
            if (!clauses.contains(clause)) {
                clauses.add(clause);
            }
        }
        return clauses;
    }
}
