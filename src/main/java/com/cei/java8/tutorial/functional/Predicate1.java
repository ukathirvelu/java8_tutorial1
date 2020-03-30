package com.cei.java8.tutorial.functional;

import java.util.function.Predicate;

import com.cei.java8.tutorial.util.Score;

public class Predicate1 {

	public static void main(String[] args) {
		Score s1 = new Score("xiaohong", 90L, 91L);

		Predicate<Score> p1 = score -> {
			return score.getYuwen() > 90;
		};

		Predicate<Score> p2 = score -> {
			return score.getShuxue() > 89;
		};

		System.out.println(p1.and(p2).test(s1));

		System.out.println(p1.or(p2).test(s1));
	}
}
