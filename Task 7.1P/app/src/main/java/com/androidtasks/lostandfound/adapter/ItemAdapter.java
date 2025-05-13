package com.androidtasks.lostandfound.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidtasks.lostandfound.R;
import com.androidtasks.lostandfound.model.Item;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {

    private Context context;
    private List<Item> itemList;

    public ItemAdapter(Context context, List<Item> itemList) {
        super(context, R.layout.item_list_row, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_list_row, parent, false);
        }

        Item currentItem = itemList.get(position);

        TextView tvItemName = listItem.findViewById(R.id.tvItemName);
        TextView tvItemDate = listItem.findViewById(R.id.tvItemDate);
        TextView tvItemLocation = listItem.findViewById(R.id.tvItemLocation);

        String displayName = currentItem.getPostType() + " " + currentItem.getName();
        tvItemName.setText(displayName);
        tvItemDate.setText(currentItem.getDate());
        tvItemLocation.setText("At " + currentItem.getLocation());

        // Set background color based on post type
        if (currentItem.getPostType().equals("Lost")) {
            listItem.setBackgroundResource(R.drawable.lost_item_background);
        } else {
            listItem.setBackgroundResource(R.drawable.found_item_background);
        }

        return listItem;
    }
}