package mk.finki.ukim.mk.lab.model.exceptions;

public class InvalidCategoryNameException extends RuntimeException {
    public InvalidCategoryNameException(String categoryName) {
        super("Invalid category name: " + categoryName);
    }
}
