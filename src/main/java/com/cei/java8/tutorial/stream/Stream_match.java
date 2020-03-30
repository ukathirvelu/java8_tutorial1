package com.cei.java8.tutorial.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Stream_match {

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

	boolean anyStartsWithA = stringCollection.stream().anyMatch((s) -> s.startsWith("a"));
	System.out.println("anyMatch" + anyStartsWithA); // true

	boolean allStartsWithA = stringCollection.stream().allMatch((s) -> s.startsWith("a"));
	System.out.println("allMatch" + allStartsWithA); // false

	boolean noneStartsWithZ = stringCollection.stream().noneMatch((s) -> s.startsWith("z"));
	System.out.println("noneMatch" + noneStartsWithZ); // true

	Optional<String> anyE = stringCollection.stream().findAny();
	System.out.println("findAny" + anyE.get());

	Optional<String> firstE = stringCollection.stream().findFirst();
	System.out.println("findFirst" + firstE.get());

    }

}
