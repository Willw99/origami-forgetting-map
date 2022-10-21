package tests;

import java.util.Arrays;

public class TestFramework {

  public static void main(String[] args) {
    var testFramework = new TestFramework();
    testFramework.runTests();
  }

  public void runTests() {
    System.out.println("Starting Tests...");
    try {
      System.out.println("Running Basic Tests...");
      runBasicTests();
      System.out.println("Finished Basic Tests.");
      runConcurrentTests();
      System.out.println("Tests Completed.");
    } catch (TestFailureException exception) {
      System.out.printf("Tests Failed.\n%s",exception.getMessage());
    } catch (Exception exception) {
      System.out.printf(
              "Tests Failed with Unknown Exception. Exception:%s\n%s%n", exception.getClass().getName(), Arrays.toString(exception.getStackTrace()));
    }
  }

  private void runBasicTests() {
    var basicTests = new BasicTests();
    basicTests.run();
  }

  private void runConcurrentTests(){
    var concurrentTests = new ConcurrentTests();
    concurrentTests.run();
  }
}
