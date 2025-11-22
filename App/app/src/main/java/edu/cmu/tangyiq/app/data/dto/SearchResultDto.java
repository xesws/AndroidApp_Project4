/**
 * SearchResultDto - DTO for search result from API.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.app.data.dto;

public class SearchResultDto {
    private String title;
    private String url;
    private String snippet;

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
