package com.bunnnyxt.njauinfonewsclient.api;

import com.bunnnyxt.njauinfonewsclient.api.model.News;
import com.bunnnyxt.njauinfonewsclient.api.model.NewsRaw;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetNewsApi {

    private int id;

    public GetNewsApi(int id) {
        this.id = id;
    }

    public News GetNews() {
        News news = null;

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://api.bunnyxt.com/njauinfonews/get_news.php?id="+id);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            Gson gson = new Gson();
            NewsRaw newsRaw = gson.fromJson(response.toString(), NewsRaw.class);

            news = new News(newsRaw);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return news;
    }

}
