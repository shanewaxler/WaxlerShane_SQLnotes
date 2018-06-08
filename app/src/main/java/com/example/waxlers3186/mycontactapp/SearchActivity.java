package com.example.waxlers3186.mycontactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Get the intent that started the activity
        android.content.Intent intent= getIntent();
        String message = intent.getStringExtra(main.EXTRA_MESSAGE);

        //Set the string in TextView
        TextView textView = findViewById(R.id.textView4);
        textView.setText(message);
    }
}