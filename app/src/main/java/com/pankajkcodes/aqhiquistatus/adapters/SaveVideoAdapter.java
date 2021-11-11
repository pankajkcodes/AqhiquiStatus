package com.pankajkcodes.aqhiquistatus.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pankajkcodes.aqhiquistatus.R;
import com.pankajkcodes.aqhiquistatus.models.VideoModel;

import java.util.ArrayList;

public class SaveVideoAdapter extends RecyclerView.Adapter<SaveVideoAdapter.SaveVideoViewHolder> {

    private Context context;
    private ArrayList<VideoModel> filesList;

    public SaveVideoAdapter(Context context, ArrayList<VideoModel> filesList) {
        this.context = context;
        this.filesList = filesList;
    }

    @NonNull
    @Override
    public SaveVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.save_video_item,parent,false);
        return new SaveVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveVideoViewHolder holder, int position) {
        final  VideoModel model = filesList.get(position);

        Glide.with(context).load(model.getUri())
                .into(holder.imageView);
        holder.textView.setText(model.getFilename());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getPath()));
            intent.setDataAndType(Uri.parse(model.getPath()), "video/mp4");

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public static class SaveVideoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public SaveVideoViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
