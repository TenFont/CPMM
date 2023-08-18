package dev.tenfont.cpmm.util;

import com.google.gson.Gson;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class FunctionKeywords {
    @Getter
    private static final HashSet<String> keywords;

    static {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/thesaurus?word=fun");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
            keywords = new HashSet<>((Collection<String>) new Gson().fromJson(reader.lines().collect(Collectors.joining()), HashMap.class).get("synonyms"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
