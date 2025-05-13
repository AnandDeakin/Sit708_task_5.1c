package com.androidtasks.newsapp;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView storiesRecyclerView;
    private RecyclerView newsRecyclerView;
    private StoriesAdapter storiesAdapter;
    private NewsAdapter newsAdapter;
    private NewsClickListener newsClickListener;

    public interface NewsClickListener {
        void onNewsClicked(News news);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NewsClickListener) {
            newsClickListener = (NewsClickListener) context;
        } else {
            throw new RuntimeException(context + " must implement NewsClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerViews
        storiesRecyclerView = view.findViewById(R.id.recycler_view_top_stories);
        newsRecyclerView = view.findViewById(R.id.recycler_view_news);

        // Setup Stories RecyclerView
        storiesRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        // Setup News RecyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        newsRecyclerView.setLayoutManager(gridLayoutManager);

        // Load and setup all news data
        loadNewsData();
    }

    private void loadNewsData() {
        List<News> allNews = News.generateDummyNews();
        List<News> topStories = new ArrayList<>();
        List<News> regularNews = new ArrayList<>();

        // Separate top stories and regular news
        for (News news : allNews) {
            if (news.isTopStory()) {
                topStories.add(news);
            } else {
                regularNews.add(news);
            }
        }

        // Setup stories adapter
        storiesAdapter = new StoriesAdapter(topStories, news -> {
            if (newsClickListener != null) {
                newsClickListener.onNewsClicked(news);
            }
        });
        storiesRecyclerView.setAdapter(storiesAdapter);

        // Setup news adapter
        newsAdapter = new NewsAdapter(regularNews, news -> {
            if (newsClickListener != null) {
                newsClickListener.onNewsClicked(news);
            }
        });
        newsRecyclerView.setAdapter(newsAdapter);
    }
}