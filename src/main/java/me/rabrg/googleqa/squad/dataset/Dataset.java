package me.rabrg.googleqa.squad.dataset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.List;

public final class Dataset {

    private List<Article> data;
    private String version;

    public static Dataset loadDataset(final InputStream stream) throws IOException {
        final Gson gson = new GsonBuilder().create();
        return gson.fromJson(new InputStreamReader(stream), Dataset.class);
    }

    public List<Article> getData() {
        return data;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "data=" + data +
                ", version='" + version + '\'' +
                '}';
    }
}
