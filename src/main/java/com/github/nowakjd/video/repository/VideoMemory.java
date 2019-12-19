package com.github.nowakjd.video.repository;

import com.github.nowakjd.video.model.Video;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class VideoMemory  {
    List<Video> videos = new ArrayList<>();

    public void add(Video video) {
        load();
        if (!videos.contains(video)){
            videos.add(video);
        }
        save();
    }

    public List<Video> getAll(){
        load();
        return new ArrayList<Video>(videos);
    }

    public void update(Video video) {
       int index= videos.indexOf(video);
       videos.remove(index);
       videos.add(index,video);
       save();
    }

    private void save()   {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("t.tmp");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(videos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void load()  {
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
    }
}
