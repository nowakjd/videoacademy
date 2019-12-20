package com.github.nowakjd.video.repository;

import com.github.nowakjd.video.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    Video getByLink(String link);
}
