/**
 * MainActivity.java
 *
 * Main activity for the Tavily Search Android application.
 * Uses MVVM pattern - observes ViewModel state and updates UI accordingly.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */

package edu.cmu.tangyiq.app.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import edu.cmu.tangyiq.app.R;
import edu.cmu.tangyiq.app.domain.model.SearchResult;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton;
    private TextView resultText;
    private ProgressBar progressBar;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        resultText = findViewById(R.id.resultText);
        progressBar = findViewById(R.id.progressBar);

        viewModel = new ViewModelProvider(this, new MainViewModelFactory())
                .get(MainViewModel.class);

        viewModel.getSearchState().observe(this, this::handleState);

        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim();
            if (query.isEmpty()) {
                resultText.setText("Please enter a search query");
                return;
            }
            viewModel.search(query);
        });
    }

    private void handleState(SearchState state) {
        switch (state.getStatus()) {
            case IDLE:
                progressBar.setVisibility(View.GONE);
                searchButton.setEnabled(true);
                break;
            case LOADING:
                progressBar.setVisibility(View.VISIBLE);
                searchButton.setEnabled(false);
                resultText.setText("Searching...");
                break;
            case SUCCESS:
                progressBar.setVisibility(View.GONE);
                searchButton.setEnabled(true);
                displayResults(state.getResults());
                break;
            case ERROR:
                progressBar.setVisibility(View.GONE);
                searchButton.setEnabled(true);
                resultText.setText("Error: " + state.getErrorMessage());
                break;
        }
    }

    private void displayResults(List<SearchResult> results) {
        if (results == null || results.isEmpty()) {
            resultText.setText("No results found");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            sb.append(i + 1).append(". ").append(result.getTitle()).append("\n");
            sb.append(result.getUrl()).append("\n");
            sb.append(result.getSnippet()).append("\n\n");
        }
        resultText.setText(sb.toString());
    }
}
