package com.androidtasks.media;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidtasks.media.database.DatabaseHelper;

public class HomeActivity extends AppCompatActivity {

    private TextView textViewWelcome;
    private EditText editTextYoutubeUrl;
    private Button buttonPlay, buttonAddToPlaylist, buttonViewPlaylist, buttonLogout;
    private String username;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get username from intent
        username = getIntent().getStringExtra("USERNAME");

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        textViewWelcome = findViewById(R.id.textViewWelcome);
        editTextYoutubeUrl = findViewById(R.id.editTextYoutubeUrl);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonAddToPlaylist = findViewById(R.id.buttonAddToPlaylist);
        buttonViewPlaylist = findViewById(R.id.buttonViewPlaylist);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Set welcome message
        textViewWelcome.setText("Welcome, " + username + "!");

        // Play video button click listener
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtubeUrl = editTextYoutubeUrl.getText().toString().trim();
                if (youtubeUrl.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Extract video ID from URL
                String videoId = extractYouTubeId(youtubeUrl);
                if (videoId == null) {
                    Toast.makeText(HomeActivity.this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Navigate to player activity
                Intent intent = new Intent(HomeActivity.this, PlayerActivity.class);
                intent.putExtra("VIDEO_ID", videoId);
                startActivity(intent);
            }
        });

        // Add to playlist button click listener
        buttonAddToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtubeUrl = editTextYoutubeUrl.getText().toString().trim();
                if (youtubeUrl.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Extract video ID from URL
                String videoId = extractYouTubeId(youtubeUrl);
                if (videoId == null) {
                    Toast.makeText(HomeActivity.this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add video to playlist in database
                long result = dbHelper.addVideoToPlaylist(username, videoId, youtubeUrl);
                if (result > 0) {
                    Toast.makeText(HomeActivity.this, "Added to playlist successfully", Toast.LENGTH_SHORT).show();
                    editTextYoutubeUrl.setText("");
                } else {
                    Toast.makeText(HomeActivity.this, "Failed to add to playlist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // View playlist button click listener
        buttonViewPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to playlist activity
                Intent intent = new Intent(HomeActivity.this, PlaylistActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        // Logout button click listener
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to login screen
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    // Helper method to extract YouTube video ID from URL
    private String extractYouTubeId(String url) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }

        // Standard YouTube URL patterns
        String[] patterns = {
                "youtu.be/([a-zA-Z0-9_-]+)",
                "youtube.com/watch\\?v=([a-zA-Z0-9_-]+)",
                "youtube.com/embed/([a-zA-Z0-9_-]+)",
                "youtube.com/v/([a-zA-Z0-9_-]+)"
        };

        for (String pattern : patterns) {
            java.util.regex.Pattern compiledPattern = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher matcher = compiledPattern.matcher(url);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        // For simplicity, if no patterns match, try to use the URL as is (for testing purposes)
        if (url.length() <= 20) {
            return url;
        }

        return null;
    }
}