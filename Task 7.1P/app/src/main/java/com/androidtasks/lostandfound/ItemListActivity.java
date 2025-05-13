package com.androidtasks.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.androidtasks.lostandfound.adapter.ItemAdapter;
import com.androidtasks.lostandfound.database.DatabaseHelper;
import com.androidtasks.lostandfound.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    private ListView listViewItems;
    private SearchView searchView;
    private DatabaseHelper dbHelper;
    private List<Item> itemList;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        listViewItems = findViewById(R.id.listViewItems);
        searchView = findViewById(R.id.searchView);

        // Load data
        loadItems();

        // Set click listener for list items
        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = itemList.get(position);
                Intent intent = new Intent(ItemListActivity.this, ItemDetailActivity.class);
                intent.putExtra("ITEM_ID", selectedItem.getId());
                startActivity(intent);
            }
        });

        // Set up search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterItems(newText);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning to this activity
        loadItems();
    }

    private void loadItems() {
        // Get all items from database
        itemList = dbHelper.getAllItems();

        // Set up adapter
        adapter = new ItemAdapter(this, itemList);
        listViewItems.setAdapter(adapter);
    }

    private void filterItems(String query) {
        List<Item> filteredList = new ArrayList<>();

        if (query.isEmpty()) {
            filteredList.addAll(itemList);
        } else {
            String lowerCaseQuery = query.toLowerCase();

            for (Item item : itemList) {
                // Filter by name, description or location
                if (item.getName().toLowerCase().contains(lowerCaseQuery) ||
                        item.getDescription().toLowerCase().contains(lowerCaseQuery) ||
                        item.getLocation().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(item);
                }
            }
        }

        adapter = new ItemAdapter(this, filteredList);
        listViewItems.setAdapter(adapter);
    }
}