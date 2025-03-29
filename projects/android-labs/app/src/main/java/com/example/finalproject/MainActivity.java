package com.example.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FEED_URL = "https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";

    private ArrayList<NewsItem> newsItems;
    private ArrayAdapter<NewsItem> newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.newsListView);
        newsItems = new ArrayList<>();
        newsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newsItems);
        listView.setAdapter(newsAdapter);

        new DownloadNewsTask().execute(FEED_URL);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsItem selectedNews = newsItems.get(position);
                showNewsDetails(selectedNews);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            new DownloadNewsTask().execute(FEED_URL);
            return true;
        } else if (id == R.id.action_favorites) {
            openFavoritesActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openFavoritesActivity() {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    private void showNewsDetails(NewsItem newsItem) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("title", newsItem.getTitle());
        intent.putExtra("description", newsItem.getDescription());
        intent.putExtra("date", newsItem.getDate());
        intent.putExtra("link", newsItem.getLink());
        startActivity(intent);
    }

    private class DownloadNewsTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {

        @Override
        protected ArrayList<NewsItem> doInBackground(String... urls) {
            ArrayList<NewsItem> downloadedNews = new ArrayList<>();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conn.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();
                NewsItem currentNews = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        String tagName = parser.getName();
                        if ("item".equals(tagName)) {
                            currentNews = new NewsItem();
                        } else if ("title".equals(tagName) && currentNews != null) {
                            currentNews.setTitle(parser.nextText());
                        } else if ("description".equals(tagName) && currentNews != null) {
                            currentNews.setDescription(parser.nextText());
                        } else if ("pubDate".equals(tagName) && currentNews != null) {
                            currentNews.setDate(parser.nextText());
                        } else if ("link".equals(tagName) && currentNews != null) {
                            currentNews.setLink(parser.nextText());
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        String tagName = parser.getName();
                        if ("item".equals(tagName) && currentNews != null) {
                            downloadedNews.add(currentNews);
                            currentNews = null;
                        }
                    }
                    eventType = parser.next();
                }

                inputStream.close();
                conn.disconnect();
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            return downloadedNews;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> downloadedNews) {
            newsItems.clear();
            newsItems.addAll(downloadedNews);
            newsAdapter.notifyDataSetChanged();
        }
    }
}
