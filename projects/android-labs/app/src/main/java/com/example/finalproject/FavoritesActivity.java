package com.example.finalproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private ListView favoritesListView;
    private ArrayList<NewsItem> favoritesList;
    private FavoritesListAdapter favoritesAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoritesListView = findViewById(R.id.favoritesListView);
        favoritesList = new ArrayList<>();
        favoritesAdapter = new FavoritesListAdapter(this, R.layout.list_item_news, favoritesList);
        favoritesListView.setAdapter(favoritesAdapter);

        // Initialize the DBHelper
        dbHelper = new DBHelper(this);

        // Load favorites from the database and display them in the ListView
        loadFavorites();
    }

    private void loadFavorites() {
        favoritesList.clear();
        favoritesList.addAll(dbHelper.getAllFavoriteItems());
        favoritesAdapter.notifyDataSetChanged();
    }
}
