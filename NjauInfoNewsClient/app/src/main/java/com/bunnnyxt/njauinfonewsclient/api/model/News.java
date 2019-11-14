package com.bunnnyxt.njauinfonewsclient.api.model;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class News {

    private int id;
    private String title;
    private String author;
    private Date ctime;
    private String content;
    private int tid;
    private int iid;
    private int sid;
    private int nid;
    private int pid;

    public News() {

    }

    public News(NewsRaw newsRaw) {
        this.id = newsRaw.getId();
        this.title = newsRaw.getTitle();
        this.author = newsRaw.getAuthor();

        Date ctime = null;
        try {
            ctime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(newsRaw.getCtime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ctime == null) {
            this.ctime = new Date();
            this.ctime.setTime(0);
        } else {
            this.ctime = ctime;
        }

        if (newsRaw.getContent() == null) {
            this.content = newsRaw.getContent();
        } else {
            String content = "";
            Base64.Decoder decoder = Base64.getDecoder();

            try {
                content = new String(decoder.decode(newsRaw.getContent()), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.content = content;
        }
        this.tid = newsRaw.getTid();
        this.iid = newsRaw.getIid();
        this.sid = newsRaw.getSid();
        this.nid = newsRaw.getNid();
        this.pid = newsRaw.getPid();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setAttri(int id, String title, String author, Date ctime, String content,
                         int tid, int iid, int sid, int nid, int pid) {
        this.setId(id); // will not actually add this manual assigned id to db, id is AI in db
        this.setTitle(title);
        this.setAuthor(author);
        this.setCtime(ctime);
        this.setContent(content);
        this.setTid(tid);
        this.setIid(iid);
        this.setSid(sid);
        this.setNid(nid);
        this.setPid(pid);
    }

    public String getOriUrl() {
        return "http://info.njau.edu.cn/Details/Index/" + iid + "/" + sid + "/" + nid + "/" + pid;
    }

    public String getCategory() {
        String categoty = "未知分类";
        switch (tid) {
            case 1:
                categoty = "通知公告";
                break;
            case 2:
                categoty = "教研动态";
                break;
            case 3:
                categoty = "学生动态";
                break;
            case 4:
                categoty = "就业实践";
                break;
            case 5:
                categoty = "图片新闻";
                break;
            case 6:
                categoty = "其他";
                break;
            default:
                break;
        }
        return categoty;
    }

    public String getCtimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(ctime);
    }

    @Override
    public String toString() {
        return "News\nid: " + id +
                "\ntitle: " + title +
                "\nauthor: " + author +
                "\nctime: " + ctime +
                "\ncontent: " + content +
                "\ntid: " + tid +
                "\niid: " + iid +
                "\nsid: " + sid +
                "\nnid: " + nid +
                "\npid: " + pid + "\n";
    }
}
