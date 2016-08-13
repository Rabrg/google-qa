package me.rabrg.googleqa.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Sentence {

    private final String text;
    private final List<Word> words;
    private final List<Dependency> dependencies;
    private List<NamedEntity> namedEntities;

    public Sentence(final String text, final List<Word> words, final List<Dependency> dependencies, final List<NamedEntity> namedEntities) {
        this.text = text;
        this.words = words;
        this.dependencies = dependencies;
        this.namedEntities = namedEntities;
    }

    public String getText() {
        return text;
    }

    public List<Word> getWords() {
        return words;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public List<NamedEntity> getNamedEntities() {
        return namedEntities;
    }

    public void setNamedEntities(List<NamedEntity> namedEntities) {
        this.namedEntities = namedEntities;
    }

    public List<String> getApposStrings() {
        final List<String> apposStrings = new ArrayList<>();

        final List<Dependency> apposDependencies = new ArrayList<>();
        for (final Dependency dependency : dependencies) {
            if ("APPOS".equals(dependency.getReln())) {
                apposDependencies.add(dependency);
            }
        }

        for (final Dependency apposDependency : apposDependencies) {
            final List<Word> subject = new ArrayList<>();
            final List<Integer> subjectIndicies = new ArrayList<>();
            subject.add(apposDependency.getGov());
            subjectIndicies.add(apposDependency.getGov().getIndex());
            int size;
            do {
                size = subject.size();
                for (final Dependency dependency : dependencies) {
                    if (!"P".equals(dependency.getReln()) && !subjectIndicies.contains(dependency.getDep().getIndex()) && !dependency.equals(apposDependency)
                            && subjectIndicies.contains(dependency.getGov().getIndex()) && dependency.getDep().getIndex() < apposDependency.getGov().getIndex()) {
                        subject.add(dependency.getDep());
                        subjectIndicies.add(dependency.getDep().getIndex());
                    }
                }
            } while (size != subject.size());
            Collections.sort(subject);

            final List<Word> appositive = new ArrayList<>();
            final List<Integer> appositiveIndicies = new ArrayList<>();
            appositive.add(apposDependency.getDep());
            appositiveIndicies.add(apposDependency.getDep().getIndex());
            do {
                size = appositive.size();
                for (final Dependency dependency : dependencies) {
                    if (!"P".equals(dependency.getReln()) && !appositiveIndicies.contains(dependency.getDep().getIndex())
                            && appositiveIndicies.contains(dependency.getGov().getIndex()) && !dependency.equals(apposDependency) && dependency.getDep().getIndex() > apposDependency.getGov().getIndex()) {
                        appositive.add(dependency.getDep());
                        appositiveIndicies.add(dependency.getDep().getIndex());
                    }
                }
            } while (size != appositive.size());
            Collections.sort(appositive);

            final StringBuilder builder = new StringBuilder();
            for (final Word word : subject) {
                builder.append(word.getWord()).append(' ');
            }
            builder.append("is ");
            for (final Word word : appositive) {
                builder.append(word.getWord()).append(' ');
            }
            apposStrings.add(builder.toString().trim());
        }
        return apposStrings;
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "text='" + text + '\'' +
                ", words=" + words +
                ", dependencies=" + dependencies +
                ", namedEntities=" + namedEntities +
                '}';
    }
}