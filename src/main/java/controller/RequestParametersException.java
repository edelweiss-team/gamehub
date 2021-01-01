package controller;

import javax.servlet.ServletException;
import org.jetbrains.annotations.NotNull;

/**
 * This exception class, subclass of {@link javax.servlet.ServletException},
 * represents an exception that may occur when a servlet is called with missing
 * request parameters, or with wrong ones.
 */
public class RequestParametersException extends ServletException {
    /**
     * Constructs a new {@link RequestParametersException} with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public RequestParametersException() {
    }

    /**
     * Constructs a new {@link RequestParametersException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public RequestParametersException(@NotNull String message) {
        super(message);
    }

    /**
     * Constructs a new {@link RequestParametersException} with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     */
    public RequestParametersException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@link RequestParametersException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     */
    public RequestParametersException(@NotNull Throwable cause) {
        super(cause);
    }
}
