package com.example.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the data from the Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String date = intent.getStringExtra("date");
        String link = intent.getStringExtra("link");

        // Set the data to the views
        TextView titleTextView = findViewById(R.id.textView_title);
        TextView descriptionTextView = findViewById(R.id.textView_description);
        TextView dateTextView = findViewById(R.id.textView_date);
        Button linkButton = findViewById(R.id.button_link);
        Button favButton = findViewById(R.id.addToFavoritesButton);

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        dateTextView.setText(date);

        // Open the article link in the web browser
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(link);
            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites(title, description, date, link);
            }
        });
    }

    private void addToFavorites(String title, String description, String date, String link) {
        // Create a NewsItem object with the data
        NewsItem newsItem = new NewsItem();
        newsItem.setTitle(title);
        newsItem.setDescription(description);
        newsItem.setDate(date);
        newsItem.setLink(link);

        // Add the NewsItem to favorites using the DBHelper
        DBHelper dbHelper = new DBHelper(this);
        long id = dbHelper.addFavoriteItem(newsItem);

        if (id != -1) {
            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
        } else {
            View DetailActivity = null;
            Snackbar snackbar = Snackbar
                    .make(DetailActivity, "Unable to add to favourites", Snackbar.LENGTH_LONG);
            snackbar.show();        }
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
