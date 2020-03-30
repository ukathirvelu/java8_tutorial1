package com.cei.java8.tutorial.stream;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Streams4 {

    public static void main(String[] args) {
	for (int i = 0; i < 10; i++) {
	    if (i % 2 == 1) {
		System.out.println(i);
	    }
	}

	IntStream.range(0, 10).forEach(i -> {
	    if (i % 2 == 1)
		System.out.println(i);
	});

	IntStream.range(0, 10).filter(i -> i % 2 == 1).forEach(System.out::println);

    }
}
