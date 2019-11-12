package com.bunnnyxt.njauinfonewsclient.db;

import com.bunnnyxt.njauinfonewsclient.db.models.News;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Operation {

    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/njau_info_news?characterEncoding=utf-8&useSSL=false";
    private static String username = "root";
    private static String password = "root";

    private static Connection getConn() {

        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static List<News> getLatestNews(int limit) {

        List<News> newsList = new ArrayList<>();

        Connection connection = getConn();

        try {
            String sql = "SELECT * FROM news order by ctime desc limit ?";
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setInt(1, limit);

                    ResultSet rs = ps.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            News news = new News();
                            news.setId(rs.getInt(1));
                            news.setTitle(rs.getString(2));
                            news.setAuthor(rs.getString(3));
                            // set date time
                            news.setContent(rs.getString(5));
                            news.setTid(rs.getInt(6));
                            news.setIid(rs.getInt(7));
                            news.setSid(rs.getInt(8));
                            news.setNid(rs.getInt(9));
                            news.setPid(rs.getInt(10));
                            newsList.add(news);
                        }
                        connection.close();
                        ps.close();
                        return newsList;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
