package com.daghlas.myvideostreamer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private final List<VideoModel> allVideos;
    private final Context context;

    public VideoAdapter(Context context, List<VideoModel> videoModels){
        this.allVideos = videoModels;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view, parent, false);
        return new VideoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(allVideos.get(position).getTitle());
        Picasso.get().load(allVideos.get(position).getImageUri()).into(holder.imageView);

        holder.view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("videoData", allVideos.get(position));
            //v.getContext().startActivity(new Intent(context, Player.class));
            Intent intent = new Intent(context, Player.class);
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return allVideos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, channel, views, days;
        View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.videoThumbnail);
            title = itemView.findViewById(R.id.videoTitle);
            channel = itemView.findViewById(R.id.videoSource);
            views = itemView.findViewById(R.id.videoViews);
            days = itemView.findViewById(R.id.videoPosted);
            view = itemView;

        }
    }
}
