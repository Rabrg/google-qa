package me.rabrg.googleqa.entity;

import me.rabrg.googleqa.util.KnowledgeUtil;

import java.util.List;
import java.util.Map;

public final class NamedEntity {

    private final String name;
    private final String type;
    private final float salience;
    private final Map<String, String> metadata;
    private final List<NamedEntityMention> mentions;

    private List<String> types;

    public NamedEntity(final String name, final String type, final float salience, final Map<String, String> metadata,
                       final List<NamedEntityMention> mentions) {
        this.name = name;
        this.type = type;
        this.salience = salience;
        this.metadata = metadata;
        this.mentions = mentions;
    }

    public void buildTypes() {
        types = KnowledgeUtil.getTypes(type);
        types.add(type);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public float getSalience() {
        return salience;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public List<NamedEntityMention> getMentions() {
        return mentions;
    }

    public List<String> getTypes() {
        return types;
    }

    @Override
    public String toString() {
        return "NamedEntity{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", salience=" + salience +
                ", metadata=" + metadata +
                ", mentions=" + mentions +
                '}';
    }
}
