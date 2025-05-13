package com.androidtasks.newsapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class NewsDetailFragment extends Fragment {

    private static final String ARG_NEWS = "news";
    private News news;

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    public static NewsDetailFragment newInstance(News news) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEWS, news);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            news = (News) getArguments().getSerializable(ARG_NEWS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView newsImageView = view.findViewById(R.id.image_news_detail);
        TextView titleTextView = view.findViewById(R.id.text_news_title_detail);
        TextView descriptionTextView = view.findViewById(R.id.text_news_description);
        RecyclerView relatedNewsRecyclerView = view.findViewById(R.id.recycler_view_related_news);

        // Set news details
        if (news != null) {
            newsImageView.setImageResource(news.getImageResource());
            titleTextView.setText(news.getTitle());
            descriptionTextView.setText(news.getDescription());

            // Setup related news recycler view
            relatedNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

            // Get related news
            List<News> relatedNews = News.getRelatedNews(news);

            // Create adapter for related news
            RelatedNewsAdapter adapter = new RelatedNewsAdapter(relatedNews);
            relatedNewsRecyclerView.setAdapter(adapter);
        }
    }
}