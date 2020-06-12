package com.lavnok.yuj.ui.videos;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lavnok.yuj.data.Videos;

public class VideoViewModel extends ViewModel {

    public final MutableLiveData<Videos> currentVideo=new MutableLiveData<Videos>();


    public void setVideo(Videos video) {
        currentVideo.setValue(video);
    }

}
