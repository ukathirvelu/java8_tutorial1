package com.cei.java8.tutorial.functional;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.cei.java8.tutorial.util.Score;

public class Function1 {

	public static void main(String[] args) {

		Function<String, String> f1 = s -> {
			String _s = s + " world";
			System.out.println(_s);
			return _s;
		};

		Function<String, String> f2 = s -> {
			String _temp = s + " andThen logistics!";
			System.out.println(_temp);
			return _temp;
		};

		f1.andThen(f2).apply("hello");

		Supplier<Score> supplier1 = Score::new;
		System.out.println("supplier1" + supplier1.get());

		Function<String, Score> function1 = Score::new;
		System.out.println("function1" + function1.apply("Tom"));

		BiFunction<String, Long, Score> biFunction1 = Score::new;
		System.out.println("biFunction1" + biFunction1.apply("Tom", 98L));

		Function<Integer, Function<Integer, Integer>> function2 = x -> y -> x + y;
		System.out.println("function2" + function2.apply(2).apply(3));

		Function<Integer, Function<Integer, Function<Integer, Integer>>> function3 = x -> y -> z -> (x + y) * z;
		System.out.println("function3" + function3.apply(1).apply(2).apply(3));

	}
}
