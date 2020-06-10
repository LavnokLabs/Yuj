package com.lavnok.yuj.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Videos {
    String TAG = "com.lavnok.yuj-Logger";


    private String VideoId;
    private String TimeStamp;
    private String Name;
    private String Thumbnail;
    private String YouTubeUrl;
    private String Description;
    private String Benefits;
    private ArrayList<String> Tags;
    private int Duration;
    private String DifficultyLevel;

    public void setVideoId() {
        VideoId = UUID.randomUUID().toString();
    }

    public void setTimeStamp() {
        String ts = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        Log.d(TAG, "Setting video timestamp for id " + VideoId + ":" + ts);
        TimeStamp = ts;
    }

    public String getVideoId() {
        return VideoId;
    }

    public Videos(){}

    public Videos(String name, Bitmap thumbnail, String youTubeURL, String description, String benefits, String[] tags, int duration, String difficultyLevel) {
        setVideoId();
        setTimeStamp();
        Name = name;
        Thumbnail = getStringFromBitmap(thumbnail);
        YouTubeUrl = youTubeURL;
        Description = description;
        Benefits = benefits;
        Tags = new ArrayList();
        for (String tag : tags)
            Tags.add(tag);
        Duration = duration;
        DifficultyLevel = difficultyLevel;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("VideoId", VideoId);
        result.put("TimeStamp", TimeStamp);
        result.put("Name", Name);
        result.put("Thumbnail", Thumbnail);
        result.put("Description", Description);
        result.put("Benefits", Benefits);
        result.put("Tags", Tags);
        result.put("YouTubeUrl", YouTubeUrl);
        result.put("Duration", Duration);
        result.put("DifficultyLevel", DifficultyLevel);

        return result;
    }

    private String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    public Bitmap getBitmapFromString() {
        byte[] decodedString = Base64.decode(Thumbnail, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }


    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getYouTubeUrl() {
        return YouTubeUrl;
    }

    public void setYouTubeUrl(String youTubeUrl) {
        YouTubeUrl = youTubeUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBenefits() {
        return Benefits;
    }

    public void setBenefits(String benefits) {
        Benefits = benefits;
    }

    public ArrayList<String> getTags() {
        return Tags;
    }

    public void setTags(ArrayList<String> tags) {
        Tags = tags;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public String getDifficultyLevel() {
        return DifficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        DifficultyLevel = difficultyLevel;
    }


}
