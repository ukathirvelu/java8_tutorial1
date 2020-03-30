package com.cei.java8.tutorial.functional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cei.java8.tutorial.util.Score;

public class Comparator1 {

	public static void main(String[] args) {

		List<Score> list = new ArrayList<>();
		list.add(new Score("xiaohong", 90L, 91L));
		list.add(new Score("xiaoming", 85L, 90L));
		list.add(new Score("wanggang", 90L, 96L));
		list.add(new Score("xiaoma", 85L, 70L));

		Collections.sort(list, Comparator.comparing(Score::getYuwen).thenComparing(Score::getShuxue));
		list.forEach(System.out::println);

		Comparator c1 = Comparator.comparing(Score::getYuwen).reversed();
		Comparator c2 = Comparator.comparing(Score::getShuxue).reversed();
		Collections.sort(list, c1.thenComparing(c2));
		list.forEach(System.out::println);

	}
}
