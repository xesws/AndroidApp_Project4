/**
 * SearchState - UI state wrapper for search operations.
 * Represents the different states: IDLE, LOADING, SUCCESS, ERROR.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.app.presentation;

import java.util.List;

import edu.cmu.tangyiq.app.domain.model.SearchResult;
public class SearchState {
    public enum Status { IDLE, LOADING, SUCCESS, ERROR }

    private final Status status;
    private final List<SearchResult> results;
    private final String errorMessage;

    private SearchState(Status status, List<SearchResult> results, String errorMessage) {
        this.status = status;
        this.results = results;
        this.errorMessage = errorMessage;
    }

    public static SearchState idle() {
        return new SearchState(Status.IDLE, null, null);
    }

    public static SearchState loading() {
        return new SearchState(Status.LOADING, null, null);
    }

    public static SearchState success(List<SearchResult> results) {
        return new SearchState(Status.SUCCESS, results, null);
    }

    public static SearchState error(String message) {
        return new SearchState(Status.ERROR, null, message);
    }

    public Status getStatus() {
        return status;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
