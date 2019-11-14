package com.bunnyxt.njauinfonewsspider.dao;

import com.bunnyxt.njauinfonewsspider.model.News;

import java.util.List;

public interface NewsDao {

    public News queryNewsById(int id);

    public List<News> queryNewsAll();

    public List<News> queryLatestNews(int tid, int limit);

    public void insertNews(News news);

    public void updateNews(News news);

    public void deleteNews(int id);

}
