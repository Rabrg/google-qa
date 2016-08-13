package me.rabrg.googleqa.entity;

public final class Dependency {

    private final String reln;
    private final Word gov;
    private final Word dep;

    public Dependency(final String reln, final Word gov, final Word dep) {
        this.reln = reln;
        this.gov = gov;
        this.dep = dep;
    }

    public String getReln() {
        return reln;
    }

    public Word getGov() {
        return gov;
    }

    public Word getDep() {
        return dep;
    }

    @Override
    public String toString() {
        return "Dependency{" +
                "reln='" + reln + '\'' +
                ", gov=" + gov +
                ", dep=" + dep +
                '}';
    }
}