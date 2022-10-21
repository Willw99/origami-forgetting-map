package tests;

import forgetting.map.ForgettingMap;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentTests implements TestTemplate {
    ForgettingMap<String,String> forgettingMap;

    class AddToForgettingMap implements Runnable{
        private final String key;
        private final String value;

        public AddToForgettingMap(String key, String value){
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            try {
                forgettingMap.add(key, value);
            } catch (Exception exception) {
                throw new TestFailureException(
                        String.format(
                                "Failed to addToForgettingMap. Exception:%s", exception.getClass().getName()));
            }
        }
    }

    class GetFromForgettingMap implements Runnable{
        private final String key;
        public GetFromForgettingMap(String key){
            this.key = key;
        }

        @Override
        public void run() {
            try {
                var value = forgettingMap.find(key);
                 System.out.printf("Located: %s=%s\n",key,value);
            } catch (Exception exception) {
                throw new TestFailureException(
                        String.format(
                                "Failed to getFromForgettingMap. Exception:%s", exception.getClass().getName()));
            }
        }
    }

    @Override
    public void run() {
        addToForgettingMap();
        getFromForgettingMap();
        usesTestForForgettingMap();
    }

    private void addToForgettingMap() {
        forgettingMap = new ForgettingMap<>(5);
        var executor = Executors.newFixedThreadPool(5);
    for (int i = 1; i <= 5; i++) {
        executor.submit(new AddToForgettingMap(String.format("testKey%s",i),String.format("testObject%s",i)));
    }
    executor.shutdown();
    System.out.println(forgettingMap);
    }

    private void getFromForgettingMap() {
        forgettingMap = new ForgettingMap<>(5);
        var executor = Executors.newFixedThreadPool(6);

        for (int i = 1; i <= 3; i++) {
            executor.submit(new AddToForgettingMap(String.format("testKey%s",i),String.format("testObject%s",i)));
        }
        for (int i = 1; i <= 3; i++) {
            executor.submit(new GetFromForgettingMap(String.format("testKey%s",i)));
        }
        executor.shutdown();
        System.out.println(forgettingMap);
    }

    private void usesTestForForgettingMap() {
        forgettingMap = new ForgettingMap<>(5);
        var executor = Executors.newFixedThreadPool(6);

        for (int i = 1; i <= 1; i++) {
            executor.submit(new AddToForgettingMap(String.format("testKey%s",i),String.format("testObject%s",i)));
        }
        for (int i = 1; i <= 5; i++) {
            executor.submit(new GetFromForgettingMap(String.format("testKey%s",1)));
        }
        executor.shutdown();
        System.out.println(forgettingMap);
    }

}
