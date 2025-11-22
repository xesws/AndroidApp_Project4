package edu.cmu.tangyiq.app.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import edu.cmu.tangyiq.app.data.remote.RemoteDataSource;
import edu.cmu.tangyiq.app.data.repository.SearchRepositoryImpl;
import edu.cmu.tangyiq.app.domain.repository.SearchRepository;
import edu.cmu.tangyiq.app.domain.usecase.SearchUseCase;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            RemoteDataSource remoteDataSource = new RemoteDataSource();
            SearchRepository searchRepository = new SearchRepositoryImpl(remoteDataSource);
            SearchUseCase searchUseCase = new SearchUseCase(searchRepository);
            return (T) new MainViewModel(searchUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
