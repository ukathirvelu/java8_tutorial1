package com.cei.java8.tutorial.stream;

import java.util.stream.Stream;

public class Stream_iterate {
	public static void main(String[] args) {
		test1();
	}

	private static void test1() {
		Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);
	}
}
