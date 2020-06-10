package com.lavnok.yuj.ui.videos;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lavnok.yuj.data.Videos;

import java.util.ArrayList;
import java.util.List;

public class VideoViewModel extends ViewModel {
    public List<Videos> getVideosList() {
        return videosList;
    }

    public void setVideosList(List<Videos> videosList) {
        this.videosList = videosList;
    }

    public Videos getCurrentVideo() {
        return currentVideo;
    }

    public void setCurrentVideo(Videos currentVideo) {
        this.currentVideo = currentVideo;
    }

    List<Videos> videosList;
    Videos currentVideo;
    public VideoViewModel() {
        videosList=new ArrayList<Videos>();

    }



    void doAction() {

    }
}
