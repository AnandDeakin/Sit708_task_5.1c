package com.androidtasks.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtasks.lostandfound.database.DatabaseHelper;
import com.androidtasks.lostandfound.model.Item;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView tvItemTitle, tvDate, tvLocation;
    private Button btnRemove;
    private DatabaseHelper dbHelper;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        tvItemTitle = findViewById(R.id.tvItemTitle);
        tvDate = findViewById(R.id.tvDate);
        tvLocation = findViewById(R.id.tvLocation);
        btnRemove = findViewById(R.id.btnRemove);

        // Get item ID from intent
        itemId = getIntent().getIntExtra("ITEM_ID", -1);

        if (itemId == -1) {
            Toast.makeText(this, "Error: Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load item details
        loadItemDetails();

        // Set click listener for remove button
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem();
            }
        });
    }

    private void loadItemDetails() {
        Item item = dbHelper.getItem(itemId);

        // Display item details
        String title = item.getPostType() + " " + item.getName();
        tvItemTitle.setText(title);
        tvDate.setText(item.getDate());
        tvLocation.setText("At " + item.getLocation());
    }

    private void removeItem() {
        // Delete item from database
        dbHelper.deleteItem(itemId);

        Toast.makeText(this, "Item removed successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}