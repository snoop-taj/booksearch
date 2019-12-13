package com.mthree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class BookSearch {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

//        Runnable runnable = () -> {
//            long startTime = System.nanoTime();
//
//            String line = null, word = "";
//            int count;
//
//            List<String> words = new ArrayList<>();
////            Map<String, Integer> hashWords = new HashMap<>();
//            Map<String, Integer> hashWords = new TreeMap<>();
//
//            //Opens file in read mode
//            FileReader file = null;
//            try {
//                file = new FileReader("/Users/abdul.taj/Documents/workspace/Java/junit/src/main/resources/book.txt");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            BufferedReader br = new BufferedReader(file);
//
//            //Reads each line
//            while(true) {
//                try {
//                    if (!((line = br.readLine()) != null)) break;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                String[] string = line.toLowerCase().split("([():{}!\"\'-?,.\\s]+)");
//
//                for(String s : string){
//                    words.add(s);
//                }
//            }
//
//            //Determine the most repeated word in a file
//            for(int i = 0; i < words.size(); i++){
//                count = 1;
//
//                for(int j = i+1; j < words.size(); j++){
//                    if(words.get(i).equals(words.get(j))){
//                        count++;
//                    }
//
//                }
//
//                if (hashWords.containsKey(words.get(i))) {
//                    Integer value = hashWords.get(words.get(i));
//                    if (value <= count) {
//                        hashWords.put(words.get(i), count);
//                    }
//                } else {
//
//                    hashWords.put(words.get(i), count);
//                }
//            }
//
//            try {
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Map<String, Integer> map = hashWords
//                    .entrySet()
//                    .stream()
//                    .sorted(Map.Entry.comparingByValue())
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//
//            List<String> keyList = new ArrayList<>(map.keySet());
//            int keySize = keyList.size();
//            for (int i =keySize - 1 ; i >= keySize - 3; --i) {
//                String key = keyList.get(i);
//                System.out.println(key + " " + map.get(key));
//            }
//
//            long time = System.nanoTime() - startTime;
//
//            System.out.println("execution time: " + time + " ns");
//
//        };
//
//        Thread t = new Thread(runnable);
//        t.start();
//
//        System.out.println("My thread " + t.getId());

        long startTime = System.nanoTime();

        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<List> future = executorService.submit(() -> {
            System.out.printf("[%s] thread starting\n", Thread.currentThread().toString());
            String line;

            List<String> words = new ArrayList<>();

            //Opens file in read mode
            FileReader file;
            file = new FileReader("/Users/abdul.taj/Documents/workspace/Java/junit/src/main/resources/book.txt");
            BufferedReader br = new BufferedReader(file);

            //Reads each line
            while((line = br.readLine()) != null) {
                String[] string = line.toLowerCase().split("([():{}!\"\'-?,.\\s]+)");

                words.addAll(Arrays.asList(string));
            }

            br.close();

            return words;
        });

        future.get(5, TimeUnit.SECONDS);

        System.out.printf("[%s] thread reached\n", Thread.currentThread().toString());

        if (future.isDone()) {
            Future<Map> future1 = executorService.submit(() -> {
                    System.out.printf("[%s] thread starting\n", Thread.currentThread().toString());

                    int count;
                    Map<String, Integer> hashWords = new TreeMap<>();
                    List words = future.get();

                    //Determine the most repeated word in a file
                    for(int i = 0; i < words.size(); i++){
                        count = 1;

                        for(int j = i+1; j < words.size(); j++){
                            if(words.get(i).equals(words.get(j))){
                                count++;
                            }

                        }
                        String word = (String) words.get(i);
                        if (hashWords.containsKey(word)) {
                            Integer value = hashWords.get(word);
                            if (value <= count) {
                                hashWords.put(word, count);
                            }
                        } else {

                            hashWords.put(word, count);
                        }
                    }


                    return hashWords
                            .entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            });

            future1.get();

            System.out.printf("[%s] thread reached\n", Thread.currentThread().toString());

            if (future1.isDone()) {
                executorService.execute(
                        () ->  {
                            System.out.printf("[%s] thread starting\n", Thread.currentThread().toString());

                            Map map = null;
                            try {
                                map = future1.get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }

                            List keyList = new LinkedList(map.keySet());
                            int keySize = keyList.size();
                            for (int i =keySize - 1 ; i >= keySize - 3; --i) {
                                String key = (String) keyList.get(i);
                                System.out.println(key + " " + map.get(key));
                            }

                            long time = System.nanoTime() - startTime;
                            System.out.println("execution time: " + time + " ns");
                        }
                );
            }
        }
    }
}
