package tests;

import forgetting.map.ForgettingMap;

public class BasicTests implements TestTemplate {

  @Override
  public void run() {
    addToForgettingMap();
    findFromForgettingMap();
    overFlowToForgettingMap();
    overFlowWithUseForgettingMap();
  }

  private void addToForgettingMap() {
    var forgettingMap = new ForgettingMap<String, String>(5);
    try {
      forgettingMap.add("testKey", "testObject");
    } catch (Exception exception) {
      throw new TestFailureException(
          String.format(
              "Failed to addToForgettingMap. Exception:%s", exception.getClass().getName()));
    }
  }

  private void findFromForgettingMap() {
    var forgettingMap = new ForgettingMap<String, String>(5);
    forgettingMap.add("testKey", "testObject");
    var output = forgettingMap.find("testKey");
    if (!"testObject".equals(output)) {
      throw new TestFailureException(
          String.format(
              "Failed findFromForgettingMap assertion.\nExpected: testObject\nActual:%s", output));
    }
  }

    private void overFlowToForgettingMap() {
        var forgettingMap = new ForgettingMap<String, String>(2);
        try {
            forgettingMap.add("testKey1", "testObject1");
            forgettingMap.add("testKey2", "testObject2");
            forgettingMap.add("testKey3", "testObject3");
            System.out.println(forgettingMap);
            //Forgetting map should be full
            forgettingMap.add("testKey4", "testObject4");
            System.out.println(forgettingMap);
        } catch (Exception exception) {
            throw new TestFailureException(
                    String.format(
                            "Failed to addToForgettingMap. Exception:%s", exception.getClass().getName()));
        }
    }

    private void overFlowWithUseForgettingMap() {
        var forgettingMap = new ForgettingMap<String, String>(2);
        try {
            forgettingMap.add("testKey1", "testObject1");
            forgettingMap.add("testKey2", "testObject2");
            forgettingMap.add("testKey3", "testObject3");
            System.out.println(forgettingMap);
            var output = forgettingMap.find("testKey2");
            if (!"testObject2".equals(output)) {
                throw new TestFailureException(
                        String.format(
                                "Failed findFromForgettingMap assertion.\nExpected: testObject2\nActual:%s", output));
            }
            //Forgetting map should be full
            forgettingMap.add("testKey4", "testObject4");
            System.out.println(forgettingMap);
        } catch (Exception exception) {
            throw new TestFailureException(
                    String.format(
                            "Failed to addToForgettingMap. Exception:%s", exception.getClass().getName()));
        }
    }
}
