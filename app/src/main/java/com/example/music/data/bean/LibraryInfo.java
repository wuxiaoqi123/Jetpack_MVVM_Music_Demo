package com.example.music.data.bean;

public class LibraryInfo implements BaseBean {

    public String title = "";
    public String summary = "";
    public String url = "";

    public LibraryInfo() {
    }

    public LibraryInfo(String title, String summary, String url) {
        this.title = title;
        this.summary = summary;
        this.url = url;
    }
}
