package it.idm.alpinebits.middleware;

/**
 * An ExceptionHandler is used to handle uncaught exceptions.
 */
public interface ExceptionHandler {

    /**
     * Handle given exception.
     *
     * @param e exception that should be handled
     */
    void handleException(Exception e);
}
