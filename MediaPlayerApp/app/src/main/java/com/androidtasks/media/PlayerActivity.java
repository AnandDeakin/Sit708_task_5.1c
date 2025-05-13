package com.androidtasks.media;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    private WebView webViewYoutubePlayer;
    private Button buttonBackFromPlayer;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // Get video ID from intent
        videoId = getIntent().getStringExtra("VIDEO_ID");

        // Initialize UI elements
        webViewYoutubePlayer = findViewById(R.id.webViewYoutubePlayer);
        buttonBackFromPlayer = findViewById(R.id.buttonBackFromPlayer);

        // Configure WebView with better settings for YouTube
        WebSettings webSettings = webViewYoutubePlayer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setSupportZoom(true);

        webViewYoutubePlayer.setWebChromeClient(new WebChromeClient());
        webViewYoutubePlayer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Page loaded
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // Load YouTube video
        loadYoutubeVideo(videoId);

        // Back button click listener
        buttonBackFromPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Load YouTube video into WebView using direct embed
    private void loadYoutubeVideo(String videoId) {
        if (videoId == null || videoId.isEmpty()) {
            Toast.makeText(this, "Invalid video ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Direct embed and autoplay it
        String embedUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=1";
        webViewYoutubePlayer.loadUrl(embedUrl);
    }

    @Override
    protected void onDestroy() {
        if (webViewYoutubePlayer != null) {
            webViewYoutubePlayer.destroy();
        }
        super.onDestroy();
    }
}