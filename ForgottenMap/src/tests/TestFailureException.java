package tests;

public class TestFailureException extends RuntimeException {
  public TestFailureException(String message) {
    super(message);
  }
}
