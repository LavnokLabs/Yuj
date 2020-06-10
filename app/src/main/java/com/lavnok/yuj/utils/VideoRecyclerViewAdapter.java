package com.lavnok.yuj.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lavnok.yuj.R;
import com.lavnok.yuj.data.Videos;

import java.util.List;


public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder> {

    private List<Videos> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public VideoRecyclerViewAdapter(Context context, List<Videos> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.video_recycler_element, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        String animal = mData.get(position);
//        holder.myTextView.setText(animal);

        Videos entry=mData.get(position);
        holder.imThumbNail.setImageBitmap(entry.getBitmapFromString());
        holder.tvName.setText(entry.getName());
        holder.tvBenefits.setText(entry.getBenefits());
        holder.tvDifficulty.setText(entry.getDifficultyLevel());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imThumbNail;
        TextView tvName, tvBenefits, tvDifficulty;


        ViewHolder(View itemView) {
            super(itemView);
            imThumbNail=itemView.findViewById(R.id.imvThumbnail);
            tvName=itemView.findViewById(R.id.tvVideoName);
            tvBenefits=itemView.findViewById(R.id.tvBenefits);
            tvDifficulty=itemView.findViewById(R.id.tvDifficultyLevel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Videos getItem(int id) {
        return mData.get(id);
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
