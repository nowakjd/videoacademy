package com.github.nowakjd.video.repository;

import com.github.nowakjd.video.model.Video;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class VideoService {
    VideoRepository videoRepository;
    List<Video> videos = new ArrayList<>();

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public void add(Video video) {
        if (videoRepository.getByLink(video.getLink())==null){
            videoRepository.save(video);
        }
    }

    public List<Video> getAll(){
        List<Video> result = new ArrayList<>();
        for (Video video : videoRepository.findAll()) {
            result.add(0,video);
        }
        return result;
    }

    public void update(Video video) {
       videoRepository.save(video);
    }


    public void load()  {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("t.tmp");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            videos = (List<Video>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Video video : videos) {
            add(video);
        }
    }
}
