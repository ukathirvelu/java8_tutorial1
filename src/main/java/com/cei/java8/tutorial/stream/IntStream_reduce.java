package com.cei.java8.tutorial.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntStream_reduce {

	public static void main(String[] args) {

		List<Integer> list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add(3);

		int sum1 = list.stream().mapToInt(t -> t).sum();
		System.out.println(sum1);

		int sum2 = list.stream().mapToInt(t -> t).reduce(0, (x, y) -> x + y);
		System.out.println(sum2);

		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		double bill = costBeforeTax.stream().map(cost -> cost + .12 * cost)
				.reduce((sum, cost) -> sum + cost).get();
		System.out.println("Total : " + bill);

	}
}
