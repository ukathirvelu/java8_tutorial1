package com.cei.java8.tutorial.stream;

import java.util.ArrayList;
import java.util.List;

public class Stream_count {
    public static void main(String[] args) {
	List<String> stringCollection = new ArrayList<>();
	stringCollection.add("ddd2");
	stringCollection.add("aaa2");
	stringCollection.add("bbb1");
	stringCollection.add("aaa1");
	stringCollection.add("bbb3");
	stringCollection.add("ccc");
	stringCollection.add("bbb2");
	stringCollection.add("ddd1");

	long startsWithB = stringCollection.stream().filter((s) -> s.startsWith("b")).count();

	System.out.println(startsWithB); // 3
    }
}
