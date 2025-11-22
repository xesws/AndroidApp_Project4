/**
 * SearchRepository - Repository interface for search operations.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.app.domain.repository;

import java.util.List;

import edu.cmu.tangyiq.app.domain.model.SearchResult;

public interface SearchRepository {
    void search(String query, RepositoryCallback<List<SearchResult>> callback);
}
