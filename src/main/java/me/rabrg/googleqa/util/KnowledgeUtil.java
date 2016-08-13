package me.rabrg.googleqa.util;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class KnowledgeUtil {

    private KnowledgeUtil() {

    }

    public static void main(String[] args) {
        try {
            final String query = "Berlin";
            System.out.println(getTypes(query));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<String> getTypes(final String query) {
        final List<String> types = new ArrayList<>();
        try {
            getGoogleTypes(types, query);
            getDBPediaTypes(types, query);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        types.replaceAll(String::toUpperCase);
        return types;
    }

    private static List<String> getGoogleTypes(final List<String> types, final String query) throws IOException {
        final HttpTransport httpTransport = new NetHttpTransport();
        final HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
        final GenericUrl url = new GenericUrl("https://kgsearch.googleapis.com/v1/entities:search");
        url.put("query", query);
        url.put("limit", "1");
        url.put("indent", "true");
        url.put("key", "AIzaSyB1UgiRQKAELYBpjaetM4d0k3i7ZvKgNjE");
        for (final String type : (List<String>) ((Map<String, Object>) ((Map<String, Object>)((List<Map<String, Object>>)
                new Gson().fromJson(requestFactory.buildGetRequest(url).execute().parseAsString(), HashMap.class)
                        .get("itemListElement")).get(0)).get("result")).get("@type")) {
            if (!types.contains(type)) {
                types.add(type);
            }
        }
        return types;
    }

    private static List<String> getDBPediaTypes(final List<String> types, final String query) throws IOException {
        final HttpTransport httpTransport = new NetHttpTransport();
        final HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
        final GenericUrl url = new GenericUrl("http://lookup.dbpedia.org/api/search.asmx/KeywordSearch");
        url.put("QueryString", query);
        url.put("MaxHits", "1");
        HttpRequest request = requestFactory.buildGetRequest(url);
        request.getHeaders().setContentType("application/json");
        final String[] lines = request.execute().parseAsString().split("\n");
        boolean classes = false;
        for (final String line : lines) {
            if (line.contains("<Classes>"))
                classes = true;
            else if (line.contains("</Classes>"))
                classes = false;
            if (classes && line.contains("<Label>")) {
                final String type = line.split("<Label>")[1].split("</Label>")[0];
                if (!types.contains(type))
                    types.add(type);
            }
        }
        return types;
    }
}