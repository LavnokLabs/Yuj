package com.lavnok.yuj;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.lavnok.yuj.data.Videos;
import com.lavnok.yuj.ui.videos.VideoViewModel;
import com.lavnok.yuj.utils.Config;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewVideoFragment extends Fragment{
    final String TAG="com.lavnok.yuj-Logger";
    VideoViewModel mViewModel;
    Videos mVideo;

    //youtube props
    private static final int RECOVERY_REQUEST=1;
    private YouTubePlayer youTubePlayer;

//    private YouTubePlayerFragment youTubePlayerFragment;

    public ViewVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_view_video, container, false);

        mViewModel = ViewModelProviders.of(this).get(VideoViewModel.class);
        mVideo=mViewModel.getCurrentVideo();
        YouTubePlayerSupportFragmentX youTubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b){
                    youTubePlayer.cueVideo(mVideo.getVideoId());
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                if (youTubeInitializationResult.isUserRecoverableError()) {
                    youTubeInitializationResult.getErrorDialog(getActivity(),1).show();
                } else {
                    Toast.makeText(getActivity(),
                            "YouTubePlayer.onInitializationFailure(): " + youTubeInitializationResult.toString(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        transaction.replace(R.id.frame_fragment, youTubePlayerFragment).commit();

        return root;
    }

}
