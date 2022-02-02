package RentCalculator.common.exceptions;

public class NoSuchProductException extends RuntimeException{

    public NoSuchProductException(String errorMessage) {
        super(errorMessage);
    }
}
