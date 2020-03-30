package com.cei.java8.tutorial.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.cei.java8.tutorial.util.Score;

public class Stream_sorted {

	public static void main(String[] args) {

//        test1();
		test2();
	}

	private static void test1() {
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");

		stringCollection.stream().sorted().forEach(System.out::println);
		System.out.println(stringCollection);

		stringCollection.stream().map(String::toUpperCase).sorted((a, b) -> b.compareTo(a))
				.forEach(System.out::println);
		// "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"
	}

	private static void test2() {
		List<Score> list = new ArrayList<>();
		list.add(new Score("xiaohong", 90L, 91L));
		list.add(new Score("xiaoming", 85L, 90L));
		list.add(new Score("wanggang", 90L, 96L));
		list.add(new Score("xiaoma", 85L, 70L));

		list.stream().sorted(Comparator.comparing(Score::getYuwen)).forEach(System.out::println);

		list.stream().sorted(Comparator.comparing(Score::getYuwen).reversed()).forEach(System.out::println);
	}

}
