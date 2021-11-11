package com.pankajkcodes.aqhiquistatus.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pankajkcodes.aqhiquistatus.R;
import com.pankajkcodes.aqhiquistatus.models.StatusModel;
import com.pankajkcodes.aqhiquistatus.utils.DownloadTask;

import java.io.File;
import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {

    ArrayList<StatusModel> videos;

    Context context;

    public StatusAdapter(ArrayList<StatusModel> videos) {
        this.videos = videos;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_row, parent, false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {

        StatusModel model = videos.get(position);
        holder.setData(videos.get(position));
        holder.download.setOnClickListener(v -> {
            try {
                File myDirectory = new File(Environment.getExternalStorageDirectory(),
                        "status");

                if(!myDirectory.exists()) {
                    myDirectory.mkdirs();
                }
                long current = System.currentTimeMillis();

                DownloadTask.download(model.getUrl(),
                        myDirectory.getPath(), v.getContext(), String.valueOf(current));
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        TextView title, desc;
        ProgressBar progressBar;
        ImageView download, share;

        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            title = itemView.findViewById(R.id.textViewTitle);
            desc = itemView.findViewById(R.id.textViewDesc);
            progressBar = itemView.findViewById(R.id.progressBar);
            download = itemView.findViewById(R.id.download);
            share = itemView.findViewById(R.id.share);
        }

        void setData(StatusModel videoModel) {
            videoView.setVideoPath(videoModel.getUrl());
            title.setText(videoModel.getTitle());
            desc.setText(videoModel.getDesc());


            videoView.setOnPreparedListener(mediaPlayer -> {
                progressBar.setVisibility(View.INVISIBLE);
                mediaPlayer.start();
            });

            videoView.setOnCompletionListener(mediaPlayer -> {
                progressBar.setVisibility(View.INVISIBLE);
                mediaPlayer.start();
            });
        }
    }
}
