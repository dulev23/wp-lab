package mk.finki.ukim.mk.lab.model.exceptions;

public class InvalidLocationException extends RuntimeException {
  public InvalidLocationException(Long id) {
    super("ID " + id + " not valid");
  }
}
