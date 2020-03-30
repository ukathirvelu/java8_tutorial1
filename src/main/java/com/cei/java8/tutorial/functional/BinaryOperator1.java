package com.cei.java8.tutorial.functional;

import java.util.Comparator;
import java.util.function.BinaryOperator;

/**
 * @author onlyone
 */
public class BinaryOperator1 {

	public static void main(String[] args) {
		int value1 = 12;
		int value2 = 6;

		System.out.println("getMin=" + getMin(value1, value2, (a, b) -> a - b));

		System.out.println("getMax=" + getMax(value1, value2, (a, b) -> a - b));

		// new ArrayList<List>().stream().mapToInt(List::size);
	}

	public static int getMin(int a, int b, Comparator<Integer> c) {
		return BinaryOperator.minBy(c).apply(a, b);
	}

	public static int getMax(int a, int b, Comparator<Integer> c) {
		return BinaryOperator.maxBy(c).apply(a, b);
	}
}
