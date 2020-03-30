package com.cei.java8.tutorial.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Stream_flatMap {

	public static void main(String[] args) {

//  test1();
		test2();
	}

	private static void test1() {
		List<String> lists = Arrays.asList("Hello", "World");
		lists.stream().map(word -> word.split("")).distinct().forEach(System.out::println);
	}

	private static void test2() {
		List<String> lists = Arrays.asList("Hello", "World");
		lists.stream().flatMap(word -> Stream.of(word.split(""))).distinct().forEach(System.out::println);
	}
}
