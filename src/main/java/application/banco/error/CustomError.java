package application.banco.error;

public class CustomError extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CustomError(String message) {
        super(message);
    }
}
