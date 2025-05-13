package com.androidtasks.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryViewHolder> {

    private final List<News> storiesList;
    private final OnStoryClickListener listener;

    public interface OnStoryClickListener {
        void onStoryClick(News news);
    }

    public StoriesAdapter(List<News> storiesList, OnStoryClickListener listener) {
        this.storiesList = storiesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_top, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        News currentStory = storiesList.get(position);
        holder.storyImage.setImageResource(currentStory.getImageResource());
        holder.storyTitle.setText(currentStory.getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onStoryClick(currentStory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storiesList.size();
    }

    static class StoryViewHolder extends RecyclerView.ViewHolder {
        ImageView storyImage;
        TextView storyTitle;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            storyImage = itemView.findViewById(R.id.image_news);
            storyTitle = itemView.findViewById(R.id.text_news_title);
        }
    }
}