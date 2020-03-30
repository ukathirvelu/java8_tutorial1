package com.cei.java8.tutorial.functional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author onlyone
 */
public class Comparator2 {

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(2, 4, 6, 1, 9, 10, 3);

		System.out.println("由�?到大排�?：");
		list.sort(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 < o2) {
					return -1;
				} else if (o1 > o2) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		System.out.println(list);

		System.out.println("由大到�?排�?：");
		list.sort(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 < o2) {
					return 1;
				} else if (o1 > o2) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		System.out.println(list);
	}

}
