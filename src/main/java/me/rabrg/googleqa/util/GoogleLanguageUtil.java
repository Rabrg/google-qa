package me.rabrg.googleqa.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.language.v1beta1.CloudNaturalLanguageAPI;
import com.google.api.services.language.v1beta1.CloudNaturalLanguageAPIScopes;
import com.google.api.services.language.v1beta1.model.*;
import me.rabrg.googleqa.entity.*;
import me.rabrg.googleqa.entity.Sentence;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class GoogleLanguageUtil {

    private static final String APPLICATION_NAME = "Google-Question-Answer";

    private static CloudNaturalLanguageAPI languageApi;

    private GoogleLanguageUtil() {

    }

    private static CloudNaturalLanguageAPI getLanguageService() throws IOException, GeneralSecurityException {
        final GoogleCredential credential = GoogleCredential.getApplicationDefault()
                .createScoped(CloudNaturalLanguageAPIScopes.all());
        final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new CloudNaturalLanguageAPI.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory,
                credential).setApplicationName(APPLICATION_NAME).build();
    }

    public static Sentence getSentence(final String text) {
        try {
            final AnnotateTextRequest request = new AnnotateTextRequest()
                    .setDocument(new Document().setContent(text).setType("PLAIN_TEXT").setLanguage("en"))
                    .setFeatures(new Features().setExtractSyntax(true).setExtractEntities(true))
                    .setEncodingType("UTF16");
            final CloudNaturalLanguageAPI.Documents.AnnotateText analyze =
                    (languageApi != null ? languageApi : (languageApi = getLanguageService())).documents().annotateText(request);
            final AnnotateTextResponse response = analyze.execute();

            final List<Word> words = new ArrayList<>();
            final List<Dependency> dependencies = new ArrayList<>();
            int i = 0;
            for (Token token : response.getTokens()) {
                words.add(new Word(token.getText().getContent(), i, token.getLemma(), token.getPartOfSpeech().getTag()));
                i++;
            }
            i = 0;
            for (Token token : response.getTokens()) {
                dependencies.add(new Dependency(token.getDependencyEdge().getLabel(),
                        words.get(token.getDependencyEdge().getHeadTokenIndex()), words.get(i)));
                i++;
            }
            Thread.sleep(105);
            return new Sentence(text, words, dependencies, getEntities(text)); // TODO: replace entities
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(final String[] args) {
        System.out.println(getEntities("Who did the Broncos prevent from going to the Super Bowl?"));
    }

    public static List<NamedEntity> getEntities(final String text) {
        final List<NamedEntity> namedEntities = new ArrayList<>();
        try {
            AnalyzeEntitiesRequest request =
                    new AnalyzeEntitiesRequest()
                            .setDocument(new Document().setContent(text).setType("PLAIN_TEXT").setLanguage("en"))
                            .setEncodingType("UTF16");
            final CloudNaturalLanguageAPI.Documents.AnalyzeEntities analyze =
                    (languageApi != null ? languageApi : (languageApi = getLanguageService())).documents().analyzeEntities(request);

            AnalyzeEntitiesResponse response = analyze.execute();
            for (Entity entity : response.getEntities()) {
                final List<NamedEntityMention> entityMentions = new ArrayList<>();
                if (entity.getMentions() != null) {
                    for (final EntityMention mention : entity.getMentions()) {
                        entityMentions.add(new NamedEntityMention(mention.getText().getContent(), mention.getText().getBeginOffset()));
                    }
                }
                namedEntities.add(new NamedEntity(entity.getName(), entity.getType(), entity.getSalience(), entity.getMetadata(), entityMentions));
            }
            Thread.sleep(105);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return namedEntities;
    }
}
