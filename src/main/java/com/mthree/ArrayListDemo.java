package com.mthree;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArrayListDemo {
    public static void main(String[] args) {


        List<Integer> list = new ArrayList<>();
//        List<Integer> list = new CopyOnWriteArrayList<>();
        list.add(new Integer(100));
        list.add(200); // Auto-Boxing
        list.add(300); // Auto-Boxing
        list.add(400); // Auto-Boxing

        for (int i=0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        for(int i: list) { // Un-Boxing
            System.out.println(i);
        }

//        Iterator<Integer> it = list.iterator();
//
//        while (it.hasNext()) {
//            System.out.println(it.next());
//            list.add(1000);
//        }

        Iterator<Integer> it = list.iterator();

        while (it.hasNext()) {
            int a = it.next();

            if (a == 100) it.remove();
        }

        System.out.println(list);

        Set<String> hs = new HashSet<>();

        hs.add("abc");
        hs.add("abc");
        System.out.println(hs);

        Map<String, String> contacts = new HashMap<>();
        contacts.put("test1", "info");
        contacts.put("test2", "info2");
        Set<String> keys = contacts.keySet();
        Iterator<String> iterator = keys.iterator();

        StringBuilder sb = new StringBuilder();

        while(iterator.hasNext()) {
            sb.append(iterator.next());
        }

        System.out.println(sb.toString());

        String s1 = "test";
        String s2 = "java";
        String s3 = s1.concat(" ").concat(s2);

        String s4 = "hello";
        String s5 = new String("hello");
        String s6 = "hello";

        System.out.println(s3);
        System.out.println(s4 == s5);
        System.out.println(s4.equals(s5));
        System.out.println(s4==s6);

        System.out.println(s4.hashCode() + " " + s5.hashCode());


    }
}
