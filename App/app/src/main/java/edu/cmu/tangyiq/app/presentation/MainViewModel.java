package edu.cmu.tangyiq.app.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.cmu.tangyiq.app.domain.model.SearchResult;
import edu.cmu.tangyiq.app.domain.repository.RepositoryCallback;
import edu.cmu.tangyiq.app.domain.usecase.SearchUseCase;

/**
 * MainViewModel - ViewModel for MainActivity.
 * Manages UI state and orchestrates search operations through the use case.
 */
public class MainViewModel extends ViewModel {
    private final SearchUseCase searchUseCase;
    private final MutableLiveData<SearchState> searchState = new MutableLiveData<>(SearchState.idle());

    public MainViewModel(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    public LiveData<SearchState> getSearchState() {
        return searchState;
    }

    public void search(String query) {
        searchState.setValue(SearchState.loading());

        searchUseCase.execute(query, new RepositoryCallback<List<SearchResult>>() {
            @Override
            public void onSuccess(List<SearchResult> data) {
                searchState.postValue(SearchState.success(data));
            }

            @Override
            public void onError(String message) {
                searchState.postValue(SearchState.error(message));
            }
        });
    }
}
