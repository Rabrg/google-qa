package me.rabrg.googleqa.entity;

public final class NamedEntityMention {

    private final String text;
    private final int beginOffset;

    public NamedEntityMention(final String text, final int beginOffset) {
        this.text = text;
        this.beginOffset = beginOffset;
    }

    public String getText() {
        return text;
    }

    public int getBeginOffset() {
        return beginOffset;
    }

    @Override
    public String toString() {
        return "NamedEntityMention{" +
                "text='" + text + '\'' +
                ", beginOffset=" + beginOffset +
                '}';
    }
}
