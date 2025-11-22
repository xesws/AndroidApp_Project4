/**
 * RepositoryCallback - Generic callback interface for repository operations.
 *
 * Author: Tangyi Qian
 * Andrew ID: tangyiq
 */
package edu.cmu.tangyiq.app.domain.repository;

public interface RepositoryCallback<T> {
    void onSuccess(T data);
    void onError(String message);
}
