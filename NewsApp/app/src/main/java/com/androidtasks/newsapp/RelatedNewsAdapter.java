package com.androidtasks.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.RelatedNewsViewHolder> {

    private final List<News> relatedNewsList;

    public RelatedNewsAdapter(List<News> relatedNewsList) {
        this.relatedNewsList = relatedNewsList;
    }

    @NonNull
    @Override
    public RelatedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_related_news, parent, false);
        return new RelatedNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedNewsViewHolder holder, int position) {
        News currentNews = relatedNewsList.get(position);
        holder.newsImage.setImageResource(currentNews.getImageResource());
        holder.newsTitle.setText(currentNews.getTitle());
    }

    @Override
    public int getItemCount() {
        return relatedNewsList.size();
    }

    static class RelatedNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        TextView newsTitle;

        public RelatedNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.image_related_news);
            newsTitle = itemView.findViewById(R.id.text_related_news_title);
        }
    }
}