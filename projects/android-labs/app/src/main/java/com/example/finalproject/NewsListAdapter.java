package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsListAdapter extends ArrayAdapter<NewsItem> {

    private LayoutInflater inflater;

    public NewsListAdapter(Context context, ArrayList<NewsItem> newsItems) {
        super(context, 0, newsItems);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_news, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = convertView.findViewById(R.id.titleTextView);
            viewHolder.dateTextView = convertView.findViewById(R.id.dateTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewsItem currentNews = getItem(position);

        if (currentNews != null) {
            viewHolder.titleTextView.setText(currentNews.getTitle());
            viewHolder.dateTextView.setText(currentNews.getDate());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
    }
}
