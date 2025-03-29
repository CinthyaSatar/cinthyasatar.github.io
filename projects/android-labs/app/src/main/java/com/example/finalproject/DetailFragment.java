package com.example.finalproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView dateTextView;
    private Button linkButton;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        titleTextView = rootView.findViewById(R.id.titleTextView);
        descriptionTextView = rootView.findViewById(R.id.descriptionTextView);
        dateTextView = rootView.findViewById(R.id.dateTextView);
        linkButton = rootView.findViewById(R.id.linkButton);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String title = arguments.getString("title");
            String description = arguments.getString("description");
            String date = arguments.getString("date");
            final String link = arguments.getString("link");

            TextView titleTextView = rootView.findViewById(R.id.titleTextView);
            TextView descriptionTextView = rootView.findViewById(R.id.descriptionTextView);
            TextView dateTextView = rootView.findViewById(R.id.dateTextView);
            Button linkButton = rootView.findViewById(R.id.linkButton);


            titleTextView.setText(title);
            descriptionTextView.setText(description);
            dateTextView.setText(date);

            linkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openWebPage(link);
                }
            });
        }

        return rootView;
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            showToast("No browser app found.");
        }
    }




    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
