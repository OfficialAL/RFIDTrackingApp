package com.example.rfidtrackingapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView; // Add this import statement
import android.os.Bundle; // Add this import statement

public class LockedTagActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locked_tag);

        TextView contactInfoTextView = findViewById(R.id.contact_info_text_view);
        // Assuming contact information is passed via intent
        String contactInfo = this.getIntent().getStringExtra("CONTACT_INFO");
        contactInfoTextView.setText(contactInfo);
    }
}
