package com.cei.java8.tutorial.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

public class IntStream_summaryStatistics {

	public static void main(String[] args) {

		List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23,
				29);

		IntSummaryStatistics stats = primes.stream().mapToInt(x -> x)
				.summaryStatistics();

		System.out.println("Max: " + stats.getMax());
		System.out.println("Min: " + stats.getMin());
		System.out.println("Sum: " + stats.getSum());
		System.out.println("Average: " + stats.getAverage());
	}
}
