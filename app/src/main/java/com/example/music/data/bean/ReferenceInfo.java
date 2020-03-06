package com.example.music.data.bean;

public class ReferenceInfo implements BaseBean {

    public String title = "";
    public String cover = "";
    public String url = "";

    public ReferenceInfo() {
    }

    public ReferenceInfo(String title, String cover, String url) {
        this.title = title;
        this.cover = cover;
        this.url = url;
    }
}
