package model.personalization;

import org.jetbrains.annotations.NotNull;

/**
 * This specialization of {@link RuntimeException} is thrown when an operation is attempted on
 * a {@link RecommendedProductList} instance, while the getList() method has been not called yet,
 * so the actual product list hasn't been initialized yet.
 */
public class ListNotInitializedException extends RuntimeException {

    /**
     * Constructs a {@link ListNotInitializedException} with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ListNotInitializedException() {
        super("Error: you can't attempt operations on a RecommendedProductList while the list is "
                + "not initialized yet failed!");
    }

    /**
     * Constructs a new {@link ListNotInitializedException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ListNotInitializedException(@NotNull String message) {
        super(message);
    }

    /**
     * Constructs a new {@link ListNotInitializedException} with the specified detail message and
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
    public ListNotInitializedException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@link ListNotInitializedException} with the specified cause and a
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
    public ListNotInitializedException(@NotNull Throwable cause) {
        super(cause);
    }
}
