package com.bunnyxt.njauinfonewsspider.spider;

import com.bunnyxt.njauinfonewsspider.model.News;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.commons.codec.Charsets;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllNewsSpider {

    private static String getTypeUrlPath(int tid, int page) {
        return "http://info.njau.edu.cn/TypeNews/ajaxContent/" + tid + "/" + page;
    }

    private static List<News> handleResponseData(String data) {
        List<News> newsList = new ArrayList<News>();
        Pattern p = Pattern.compile("<a href=\"/Details/Index/(\\d+)/(\\d+)/(\\d+)/(\\d+)\"\\s+title=\"(.+)\">");
        Matcher m = p.matcher(data);
        while (m.find()) {
            int iid = Integer.parseInt(m.group(1));
            int sid = Integer.parseInt(m.group(2));
            int nid = Integer.parseInt(m.group(3));
            int pid = Integer.parseInt(m.group(4));
            String title = m.group(5);

            News news = new News();
            news.setIid(iid);
            news.setSid(sid);
            news.setNid(nid);
            news.setPid(pid);
            news.setTitle(title);

            newsList.add(news);
        }
        return newsList;
    }

    private static String getNewsUrlPath(int iid, int sid, int nid, int pid) {
        return "http://info.njau.edu.cn/Details/Index/" + iid + "/" + sid + "/" + nid + "/" + pid;
    }

    public static void main(String[] args) {
        System.out.println("All News Crawler");

        HttpClient httpClient = HttpClientBuilder.create().build();

        SqlSession sqlSession = null;
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int tid = 1; tid <= 6; tid++) {
            int page = 0;
            while (true) {
                String typeUrlPath = getTypeUrlPath(tid, page);
                try {
                    HttpGet httpGet = new HttpGet(typeUrlPath);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK) {
                        String data = EntityUtils.toString(httpResponse.getEntity(), Charsets.UTF_8);
                        List<News> newsList = handleResponseData(data);
                        if (newsList.size() == 0) {
                            System.out.println("No news got! Now get news from next type.");
                            break;
                        } else {
                            for (News news : newsList) {
                                // get news author, ctime, content from url
                                String newsUrlPath = getNewsUrlPath(news.getIid(), news.getSid(),
                                        news.getNid(), news.getPid());
                                httpGet = new HttpGet(newsUrlPath);
                                httpResponse = httpClient.execute(httpGet);
                                statusCode = httpResponse.getStatusLine().getStatusCode();
                                if (statusCode == HttpStatus.SC_OK) {
                                    String newsHtml = EntityUtils.toString(httpResponse.getEntity(), Charsets.UTF_8);

                                    // get author and ctime
                                    Pattern p = Pattern.compile("<span class=\"span\"> 作者:</span>\\s*(.+)\\s\\s<span class=\"span\">&nbsp;提交时间：</span>(\\d+)/(\\d+)/(\\d+) (\\d+):(\\d+):(\\d+) <span class=\"span\">&nbsp;浏览");
                                    Matcher m = p.matcher(newsHtml);
                                    String author = "undefined author";
                                    Date ctime = new Date();
                                    if (m.find()) {
                                        author = m.group(1);
                                        int year = Integer.parseInt(m.group(2));
                                        int month = Integer.parseInt(m.group(3));
                                        int day = Integer.parseInt(m.group(4));
                                        int hour = Integer.parseInt(m.group(5));
                                        int minute = Integer.parseInt(m.group(6));
                                        int second = Integer.parseInt(m.group(7));
                                        ctime.setYear(year - 1900);
                                        ctime.setMonth(month - 1);
                                        ctime.setDate(day);
                                        ctime.setHours(hour);
                                        ctime.setMinutes(minute);
                                        ctime.setSeconds(second);
                                    } else {
                                        System.out.println("Cannot find author or ctime from url " + newsUrlPath + ", skip.");
                                        continue;
                                    }

                                    // get content
                                    // TODO fix pattern to get content from every news
                                    p = Pattern.compile("<div class=\"content\">([\\s\\S]+)</div>");
                                    m = p.matcher(newsHtml);
                                    String content = "undefined content";
                                    if (m.find()) {
                                        content = m.group(1);
                                    } else {
                                        System.out.println("Cannot find news content from url " + newsUrlPath + ", skip.");
                                        continue;
                                    }

                                    news.setTid(tid);
                                    news.setAuthor(author);
                                    news.setCtime(ctime);
                                    news.setContent(content);

                                    // insert news into db
                                    try {
                                        sqlSession.insert("NewsDaoMapper.insertNews", news);
                                        sqlSession.commit();
                                        System.out.println("Finish insert news " + news.getTitle());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    System.out.println("Fail to get news from url " + newsUrlPath + ", skip.");
                                    continue;
                                }

                                Thread.sleep(50);

                            }
                            System.out.println("Type " + tid + " page " + page + " done!");
                        }
                    } else {
                        System.out.println("Status code " + statusCode + " got, retry...");
                        page--;
                    }
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error occur when tid = " + tid + " and page = " + page);
                } finally {
                    page++;
                }
            }
        }

    }
}
