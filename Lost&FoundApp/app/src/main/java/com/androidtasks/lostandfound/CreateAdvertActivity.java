package com.androidtasks.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidtasks.lostandfound.database.DatabaseHelper;
import com.androidtasks.lostandfound.model.Item;

public class CreateAdvertActivity extends AppCompatActivity {

    private RadioGroup radioPostType;
    private RadioButton radioLost, radioFound;
    private EditText etName, etPhone, etDescription, etDate, etLocation;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        radioPostType = findViewById(R.id.radioPostType);
        radioLost = findViewById(R.id.radioLost);
        radioFound = findViewById(R.id.radioFound);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etLocation = findViewById(R.id.etLocation);
        btnSave = findViewById(R.id.btnSave);

        // Set default selection
        radioLost.setChecked(true);

        // Set click listener for save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    private void saveItem() {
        // Get form values
        String postType = radioLost.isChecked() ? "Lost" : "Found";
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        // Validate input
        if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new item
        Item item = new Item(postType, name, phone, description, date, location);

        // Save to database
        long id = dbHelper.addItem(item);

        if (id > 0) {
            Toast.makeText(this, "Item saved successfully", Toast.LENGTH_SHORT).show();
            clearForm();
            finish();
        } else {
            Toast.makeText(this, "Failed to save item", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearForm() {
        radioLost.setChecked(true);
        etName.setText("");
        etPhone.setText("");
        etDescription.setText("");
        etDate.setText("");
        etLocation.setText("");
    }
}