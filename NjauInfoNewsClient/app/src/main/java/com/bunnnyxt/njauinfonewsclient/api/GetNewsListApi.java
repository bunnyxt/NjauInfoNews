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

public class GetNewsListApi {

    private int tid;
    private int pn;
    private int ps;

    public GetNewsListApi(int tid, int pn, int ps) {
        this.tid = tid;
        this.pn = pn;
        this.ps = ps;
    }

    public List<News> GetNewsList() {
        List<News> newsList = null;

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://api.bunnyxt.com/njauinfonews/get_news_list.php?tid="+tid+"&pn="+pn+"&ps="+ps);
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
            List<NewsRaw> newsRawList = gson.fromJson(response.toString(), new TypeToken<List<NewsRaw>>(){}.getType());

            newsList = new ArrayList<News>();
            for (int i = 0; i < newsRawList.size(); i++) {
                News news = new News(newsRawList.get(i));
                newsList.add(news);
            }
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

        return newsList;
    }

}
