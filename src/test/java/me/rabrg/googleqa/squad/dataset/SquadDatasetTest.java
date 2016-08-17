package me.rabrg.googleqa.squad.dataset;

import me.rabrg.googleqa.entity.NamedEntity;
import me.rabrg.googleqa.entity.Sentence;

import java.io.IOException;

public class SquadDatasetTest {

    public static void main(final String[] args) throws IOException {
        final Dataset dataset = Dataset.loadDataset(SquadDatasetTest.class
                .getResourceAsStream("/dev-v1.0-know.json"));
        for (final Article article : dataset.getData()) {
            for (final Paragraph paragraph : article.getParagraphs()) {
                for (final Sentence sentence : paragraph.getContextSentences()) {
                    if (sentence.getNamedEntities() != null) {
                        for (final NamedEntity namedEntity : sentence.getNamedEntities()) {
                           System.out.println(namedEntity.getTypes());
                        }
                    }
                }
            }
        }
//        final Gson gson = new GsonBuilder().create();
//        for (final Article article : dataset.getData()) {
//            for (final Paragraph paragraph : article.getParagraphs()) {
//                for (final QuestionAnswerService qas : paragraph.getQas()) {
//                    for (final Sentence sentence : qas.getQuestionSentences()) {
//                        System.out.println(sentence.getNamedEntities());
//                    }
//                }
//            }
//        }
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
    }
}
