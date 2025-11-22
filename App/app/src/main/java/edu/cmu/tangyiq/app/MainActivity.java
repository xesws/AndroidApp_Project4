/**
 * MainActivity.java
 *
 * Main activity for the Tavily Search Android application.
 * This app allows users to search for information using the Tavily API.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */

package edu.cmu.tangyiq.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton;
    private TextView resultText;
    private ProgressBar progressBar;

    // TODO: Replace with your deployed web service URL
    private static final String WEB_SERVICE_URL = "https://your-codespace-url.github.dev/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        resultText = findViewById(R.id.resultText);
        progressBar = findViewById(R.id.progressBar);

        // Set up button click listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        String query = searchInput.getText().toString().trim();

        if (query.isEmpty()) {
            resultText.setText("Please enter a search query");
            return;
        }

        // Show progress, disable button
        progressBar.setVisibility(View.VISIBLE);
        searchButton.setEnabled(false);
        resultText.setText("Searching...");

        // TODO: Implement HTTP request to web service
        // For now, just show a placeholder message

        // Simulating a response (replace with actual HTTP call)
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // TODO: Make actual HTTP request here
                    // HttpURLConnection or OkHttp to WEB_SERVICE_URL

                    Thread.sleep(1000); // Simulate network delay

                    final String result = "Search results for: " + query + "\n\n" +
                            "(Implement web service call to display actual results)";

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultText.setText(result);
                            progressBar.setVisibility(View.GONE);
                            searchButton.setEnabled(true);
                        }
                    });

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultText.setText("Error: " + e.getMessage());
                            progressBar.setVisibility(View.GONE);
                            searchButton.setEnabled(true);
                        }
                    });
                }
            }
        }).start();
    }
}
