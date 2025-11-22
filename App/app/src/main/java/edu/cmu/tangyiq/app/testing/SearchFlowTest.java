/**
 * SearchFlowTest - Unit tests for search flow.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.app.testing;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.tangyiq.app.domain.model.SearchResult;
import edu.cmu.tangyiq.app.domain.repository.RepositoryCallback;
import edu.cmu.tangyiq.app.domain.repository.SearchRepository;
import edu.cmu.tangyiq.app.domain.usecase.SearchUseCase;

public class SearchFlowTest {

    public static void main(String[] args) {
        testSearchUseCaseWithValidQuery();
        testSearchUseCaseWithEmptyQuery();
        System.out.println("All tests passed!");
    }

    private static void testSearchUseCaseWithValidQuery() {
        SearchRepository mockRepository = (query, callback) -> {
            List<SearchResult> results = new ArrayList<>();
            results.add(new SearchResult("Test Title", "https://test.com", "Test snippet"));
            callback.onSuccess(results);
        };

        SearchUseCase useCase = new SearchUseCase(mockRepository);
        final boolean[] success = {false};

        useCase.execute("test query", new RepositoryCallback<List<SearchResult>>() {
            @Override
            public void onSuccess(List<SearchResult> data) {
                success[0] = data != null && data.size() == 1;
            }

            @Override
            public void onError(String message) {
                success[0] = false;
            }
        });

        assert success[0] : "testSearchUseCaseWithValidQuery failed";
        System.out.println("✓ testSearchUseCaseWithValidQuery passed");
    }

    private static void testSearchUseCaseWithEmptyQuery() {
        SearchRepository mockRepository = (query, callback) -> {
            callback.onSuccess(new ArrayList<>());
        };

        SearchUseCase useCase = new SearchUseCase(mockRepository);
        final boolean[] errorReceived = {false};

        useCase.execute("", new RepositoryCallback<List<SearchResult>>() {
            @Override
            public void onSuccess(List<SearchResult> data) {
                errorReceived[0] = false;
            }

            @Override
            public void onError(String message) {
                errorReceived[0] = message.contains("empty");
            }
        });

        assert errorReceived[0] : "testSearchUseCaseWithEmptyQuery failed";
        System.out.println("✓ testSearchUseCaseWithEmptyQuery passed");
    }
}
