package me.rabrg.googleqa.entity;

public final class Word implements Comparable<Word> {

    private final String word;
    private final int index;
    private final String lemma;
    private final String pos;

    public Word(final String word, final int index, final String lemma, final String pos) {
        this.word = word;
        this.index = index;
        this.lemma = lemma;
        this.pos = pos;
    }

    public String getWord() {
        return word;
    }

    public int getIndex() {
        return index;
    }

    public String getLemma() {
        return lemma;
    }

    public String getPos() {
        return pos;
    }

    @Override
    public int compareTo(Word o) {
        if (getIndex() < o.getIndex()) {
            return -1;
        } else if (getIndex() < o.getIndex()) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;

        if (index != word1.index) return false;
        if (!word.equals(word1.word)) return false;
        if (!lemma.equals(word1.lemma)) return false;
        return pos.equals(word1.pos);

    }

    @Override
    public int hashCode() {
        int result = word.hashCode();
        result = 31 * result + index;
        result = 31 * result + lemma.hashCode();
        result = 31 * result + pos.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", index=" + index +
                '}';
    }
}