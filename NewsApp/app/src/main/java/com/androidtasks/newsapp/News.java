package com.androidtasks.newsapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class News implements Serializable {
    private String title;
    private String description;
    private int imageResource;
    private boolean isTopStory;

    public News(String title, String description, int imageResource, boolean isTopStory) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
        this.isTopStory = isTopStory;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isTopStory() {
        return isTopStory;
    }

    // Static method to generate dummy news data
    public static List<News> generateDummyNews() {
        List<News> newsList = new ArrayList<>();

        // Top stories
        newsList.add(new News("Breaking: New COVID Variant Found",
                "Scientists have identified a new COVID variant that appears to be more transmissible but less severe. Health officials are closely monitoring the situation.",
                R.drawable.news1, true));
        newsList.add(new News("Global Tech Summit 2025 Announced",
                "The annual Global Tech Summit will be held in Tokyo this year, featuring keynotes from industry leaders discussing AI, robotics, and sustainable technology.",
                R.drawable.news2, true));
        newsList.add(new News("Economic Recovery Accelerates",
                "Global markets show signs of robust recovery as inflation rates stabilize and consumer spending increases across major economies.",
                R.drawable.news3, true));
        newsList.add(new News("Climate Agreement Reached",
                "World leaders have reached a landmark agreement on carbon emissions reduction, setting ambitious new targets for the next decade.",
                R.drawable.news4, true));
        newsList.add(new News("SpaceX Announces Mars Mission Date",
                "SpaceX has officially announced the launch date for its first crewed mission to Mars, set to depart in early 2027.",
                R.drawable.news5, true));

        // Regular news
        newsList.add(new News("New Breakthrough in Renewable Energy",
                "Researchers have developed a new type of solar panel that can generate electricity even at night, potentially revolutionizing renewable energy.",
                R.drawable.news6, false));
        newsList.add(new News("Global Education Report Released",
                "A comprehensive report shows improvements in global literacy rates but highlights persistent challenges in educational equality.",
                R.drawable.news7, false));
        newsList.add(new News("Major Archaeological Discovery",
                "Archaeologists have unearthed an ancient city dating back 5,000 years, providing new insights into early civilization.",
                R.drawable.news8, false));
        newsList.add(new News("Advances in AI Healthcare Applications",
                "New AI systems are showing promising results in early disease detection, particularly for cancer and neurodegenerative conditions.",
                R.drawable.news9, false));
        newsList.add(new News("Olympic Committee Announces 2032 Host City",
                "The International Olympic Committee has revealed the host city for the 2032 Summer Olympic Games after a competitive bidding process.",
                R.drawable.news10, false));
        newsList.add(new News("Elon Musk's Neuralink Receives FDA Approval",
                "Neuralink, the brain-computer interface company founded by Elon Musk, has received FDA approval for its first human trials.",
                R.drawable.news11, false));

        return newsList;
    }

    // Method to get related news (in a real app, this would use tags or categories)
    public static List<News> getRelatedNews(News currentNews) {
        List<News> allNews = generateDummyNews();
        List<News> relatedNews = new ArrayList<>();

        // For simplicity, just return 3 other news items
        int count = 0;
        for (News news : allNews) {
            if (!news.getTitle().equals(currentNews.getTitle()) && count < 3) {
                relatedNews.add(news);
                count++;
            }
        }

        return relatedNews;
    }
}