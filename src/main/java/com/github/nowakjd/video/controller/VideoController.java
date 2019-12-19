package com.github.nowakjd.video.controller;

import com.github.nowakjd.video.model.Video;
import com.github.nowakjd.video.repository.VideoMemory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.validation.Valid;
import java.io.IOException;

@Controller
public class VideoController {

    private VideoMemory videoMemory;

    public VideoController(VideoMemory videoMemory) {
        this.videoMemory = videoMemory;
    }

    @GetMapping("/")
    public String home(ModelMap modelMap){
        Document doc = null;
        try {
            doc = Jsoup.connect("http://5.135.218.27:8080/data/100/100").get();
            Elements videos = doc.getElementsByClass("borderedBox");
            videos.forEach(el-> {
                        try {
                            videoMemory.add(new Video(el));
                        }
                        catch (Exception exception) {

                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelMap.addAttribute("videos", videoMemory.getAll());
        return "index";
    }
    @GetMapping("/edit/https://player.vimeo.com/video/{id}")
    public String showUpdateForm(@PathVariable("id") String link, Model model) {
        link= "https://player.vimeo.com/video/" + link;
        Video video = new Video();
        video.setLink(link);
        video= videoMemory.getAll().get(videoMemory.getAll().indexOf(video));
        model.addAttribute("video", video);
        return "update-video";
    }

    @PostMapping("/update/https://player.vimeo.com/video/{id}")
    public String updateUser(@PathVariable("id") String id, @Valid Video video,
                             BindingResult result, Model model) {

        id= "https://player.vimeo.com/video/" + id;
        video.setLink(id);
        videoMemory.update(video);
        model.addAttribute("videos", videoMemory.getAll());
        return "index";
    }
    @GetMapping("/dump")
    @ResponseBody
    public String dump(){
        StringBuilder builder = new StringBuilder();
        for (Video video : videoMemory.getAll()) {
            builder.append(video.getLink());
            builder.append("\n");
            builder.append(video.getDescription());
            builder.append("\n");
            builder.append(video.getTitle());
            builder.append("\n");
        }
        return builder.toString();
    }


}
