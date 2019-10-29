package com.bunnyxt.njauinfonewsspider.dao;

import com.bunnyxt.njauinfonewsspider.dao.NewsDao;
import com.bunnyxt.njauinfonewsspider.model.News;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class NewsDaoImpl implements NewsDao {

    public SqlSession sqlSession;

    public NewsDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public News queryNewsById(int id) {
        return this.sqlSession.selectOne("NewsDao.queryNewsById", id);
    }

    public List<News> queryNewsAll() {
        return this.sqlSession.selectList("NewsDao.queryNewsAll");
    }

    public void insertNews(News news) {
        this.sqlSession.insert("NewsDao.insertNews", news);
    }

    public void updateNews(News news) {
        this.sqlSession.update("NewsDao.updateNews", news);
    }

    public void deleteNews(int id) {
        this.sqlSession.delete("NewsDao.deleteNews", id);
    }
}
