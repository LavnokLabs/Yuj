package com.lavnok.yuj.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.sql.Array;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Videos {
    String TAG="com.lavnok.yuj-Logger";


    private String VideoId;
    private String TimeStamp;
    private String Name;
    private String Thumbnail;
    private String YouTubeURL;
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
        Log.d(TAG,"Setting video timestamp for id "+VideoId+":"+ts);
        TimeStamp = ts;
    }

    public String getVideoId(){
        return VideoId;
    }

    public Videos(String name, Bitmap thumbnail, String youTubeURL, String description, String benefits, String[] tags, int duration, String difficultyLevel) {
        setVideoId();
        setTimeStamp();
        Name = name;
        Thumbnail = getStringFromBitmap(thumbnail);
        YouTubeURL = youTubeURL;
        Description = description;
        Benefits = benefits;
        Tags = new ArrayList();
        for(String tag:tags)
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
        result.put("YouTubeUrl", YouTubeURL);
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


}
