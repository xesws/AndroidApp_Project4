package edu.cmu.tangyiq.app.domain.repository;

import java.util.List;

import edu.cmu.tangyiq.app.domain.model.SearchResult;

public interface SearchRepository {
    void search(String query, RepositoryCallback<List<SearchResult>> callback);
}
