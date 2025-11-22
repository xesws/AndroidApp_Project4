/**
 * SearchResult - Domain model for search result.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.app.domain.model;

public class SearchResult {
    private String title;
    private String url;
    private String snippet;

    public SearchResult(String title, String url, String snippet) {
        this.title = title;
        this.url = url;
        this.snippet = snippet;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getSnippet() {
        return snippet;
    }
}
