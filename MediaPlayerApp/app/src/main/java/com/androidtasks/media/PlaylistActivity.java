package com.androidtasks.media;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidtasks.media.database.DatabaseHelper;

public class PlaylistActivity extends AppCompatActivity {

    private ListView listViewPlaylist;
    private Button buttonBack;
    private TextView textViewPlaylistTitle;
    private String username;
    private DatabaseHelper dbHelper;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        // Get username from intent
        username = getIntent().getStringExtra("USERNAME");

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        listViewPlaylist = findViewById(R.id.listViewPlaylist);
        buttonBack = findViewById(R.id.buttonBackFromPlaylist);
        textViewPlaylistTitle = findViewById(R.id.textViewPlaylistTitle);

        // Set playlist title
        textViewPlaylistTitle.setText(username + "'s Playlist");

        // Load playlist data
        loadPlaylistData();

        // Set item click listener
        listViewPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the cursor from the adapter
                Cursor cursor = (Cursor) adapter.getItem(position);

                // Get video ID from cursor
                int videoIdColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_VIDEO_ID);
                String videoId = cursor.getString(videoIdColumnIndex);

                // Navigate to player activity
                Intent intent = new Intent(PlaylistActivity.this, PlayerActivity.class);
                intent.putExtra("VIDEO_ID", videoId);
                startActivity(intent);
            }
        });

        // Back button click listener
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Load playlist data from database
    private void loadPlaylistData() {
        Cursor cursor = dbHelper.getPlaylistVideos(username);

        // Map cursor columns to view IDs
        String[] fromColumns = {DatabaseHelper.COLUMN_VIDEO_URL};
        int[] toViews = {android.R.id.text1};

        // Create adapter
        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                fromColumns,
                toViews,
                0);

        // Set adapter to list view
        listViewPlaylist.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null && adapter.getCursor() != null) {
            adapter.getCursor().close();
        }
    }
}