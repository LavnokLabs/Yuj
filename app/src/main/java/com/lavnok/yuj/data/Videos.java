package com.lavnok.yuj.data;

import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Videos {

    private enum DifficultyLevel {
        BEGINNER, EXPERIENCED_BEGINNER, INTERMIDIATE, ADVANCED_INTERMEDIATE
    }

    private String VideoId;
    private SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
    private String TimeStamp = s.format(new Date());
    private String Name;
    private Bitmap Thumbnail;
    private String Source;
    private String Description;
    private String Benefits;
    private boolean isYouTubeLink;
    private String YouTubeId;
    private String[] Tags;
    private String[] Beneficiaries;
    private int Duration;
    private String HomePageTag;

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public Videos(String name, Bitmap thumbnail, String source, String description, String benefits, boolean isYouTubeLink, String youTubeId, String[] tags, String[] beneficiaries, int duration,String homePageTag) {
//        VideoId=setVideoId();
        Name = name;
        Thumbnail = thumbnail;
        TimeStamp = new SimpleDateFormat("ddMMyyyyhhmmss").format(new Date());
        Source = source;
        Description = description;
        Benefits = benefits;
        this.isYouTubeLink = isYouTubeLink;
        YouTubeId = youTubeId;
        Tags = tags;
        Beneficiaries = beneficiaries;
        Duration = duration;
        HomePageTag=homePageTag;
    }

    public Videos(String videoId, String timeStamp, String name, Bitmap thumbnail, String source, String description, String benefits, boolean isYouTubeLink, String youTubeId, String[] tags, String[] beneficiaries, int duration,String homePageTag) {
        VideoId = videoId;
        TimeStamp = timeStamp;
        Name = name;
        Thumbnail = thumbnail;
        Source = source;
        Description = description;
        Benefits = benefits;
        this.isYouTubeLink = isYouTubeLink;
        YouTubeId = youTubeId;
        Tags = tags;
        Beneficiaries = beneficiaries;
        Duration = duration;
        HomePageTag=homePageTag;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("VideoId", VideoId);
        result.put("TimeStamp", TimeStamp);
        result.put("Name", Name);
        result.put("Thumbnail", Thumbnail);
        result.put("Source", Source);
        result.put("Description", Description);
        result.put("Benefits", Benefits);
        result.put("isYouTubeLink", isYouTubeLink);
        result.put("YouTubeId", YouTubeId);
        result.put("Tags", Tags);
        result.put("Beneficiaries", Beneficiaries);
        result.put("Duration", Duration);
        result.put("HomePageTag", HomePageTag);

        return result;
    }


}
