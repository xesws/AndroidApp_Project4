/**
 * SearchRepositoryImpl - Implementation of SearchRepository.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.app.data.repository;

import java.util.List;

import edu.cmu.tangyiq.app.data.remote.RemoteDataSource;
import edu.cmu.tangyiq.app.domain.model.SearchResult;
import edu.cmu.tangyiq.app.domain.repository.RepositoryCallback;
import edu.cmu.tangyiq.app.domain.repository.SearchRepository;

public class SearchRepositoryImpl implements SearchRepository {
    private final RemoteDataSource remoteDataSource;

    public SearchRepositoryImpl(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void search(String query, RepositoryCallback<List<SearchResult>> callback) {
        remoteDataSource.fetchResults(query, new RemoteDataSource.DataSourceCallback() {
            @Override
            public void onSuccess(List<SearchResult> results) {
                callback.onSuccess(results);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }
}
