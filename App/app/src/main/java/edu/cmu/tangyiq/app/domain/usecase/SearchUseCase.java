/**
 * SearchUseCase - Core business logic for search operations.
 * Orchestrates the search flow between presentation and data layers.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.app.domain.usecase;

import java.util.List;

import edu.cmu.tangyiq.app.domain.model.SearchResult;
import edu.cmu.tangyiq.app.domain.repository.RepositoryCallback;
import edu.cmu.tangyiq.app.domain.repository.SearchRepository;
public class SearchUseCase {
    private final SearchRepository searchRepository;

    public SearchUseCase(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public void execute(String query, RepositoryCallback<List<SearchResult>> callback) {
        if (query == null || query.trim().isEmpty()) {
            callback.onError("Query cannot be empty");
            return;
        }
        searchRepository.search(query.trim(), callback);
    }
}
