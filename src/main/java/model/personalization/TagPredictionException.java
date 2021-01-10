package model.personalization;

import org.jetbrains.annotations.NotNull;

/**
 * This specialization of {@link RuntimeException} is thrown when an error occurs tag prediction
 * process.
 */
public class TagPredictionException extends RuntimeException {

    /**
     * Constructs a {@link TagPredictionException} with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public TagPredictionException() {
        super("Error: tag prediction failed!");
    }

    /**
     * Constructs a new {@link TagPredictionException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public TagPredictionException(@NotNull String message) {
        super(message);
    }

    /**
     * Constructs a new {@link TagPredictionException} with the specified detail message and
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
     * @since 1.4
     */
    public TagPredictionException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@link TagPredictionException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public TagPredictionException(@NotNull Throwable cause) {
        super(cause);
    }
}
