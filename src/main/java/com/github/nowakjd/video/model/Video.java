package com.github.nowakjd.video.model;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.Serializable;
import java.util.Objects;

public class Video implements Serializable {
    String title;
    String link;
    String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return link.equals(video.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }

    public Video(Element element) {
        description="";
        title= element.select("h2").toString();
        Elements links= element.select("a[href]");
        link= links.first().attr("href");
    }

    public Video() {
    }
}
