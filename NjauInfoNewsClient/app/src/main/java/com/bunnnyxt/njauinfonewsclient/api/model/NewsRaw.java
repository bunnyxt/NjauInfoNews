package com.bunnnyxt.njauinfonewsclient.api.model;

public class NewsRaw {

    private int id;
    private String title;
    private String author;
    private String ctime;
    private String content;
    private int tid;
    private int iid;
    private int sid;
    private int nid;
    private int pid;

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

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
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

    public void setAttri(int id, String title, String author, String ctime, String content,
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

    @Override
    public String toString() {
        return "NewsRaw\nid: " + id +
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
