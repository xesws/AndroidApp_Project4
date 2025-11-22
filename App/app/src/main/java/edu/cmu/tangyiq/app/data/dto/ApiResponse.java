/**
 * ApiResponse - DTO for API response wrapper.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.app.data.dto;

import java.util.List;

public class ApiResponse {
    private String status;
    private List<SearchResultDto> results;
    private String error;

    public String getStatus() {
        return status;
    }

    public List<SearchResultDto> getResults() {
        return results;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccess() {
        return "success".equals(status);
    }
}
