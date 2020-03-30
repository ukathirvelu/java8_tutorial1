package com.cei.java8.tutorial.methodreference;

import java.util.LinkedHashSet;
import java.util.function.BiConsumer;

import com.cei.java8.tutorial.util.Utils;

public class BiConsumer1 {

	public static void main(String[] args) {

		BiConsumer<LinkedHashSet, Object> biConsumer1 = LinkedHashSet::add;
		LinkedHashSet s1 = new LinkedHashSet();
		biConsumer1.accept(s1, "aaa");
		System.out.println(s1);

		BiConsumer<String, Long> biConsumer2 = Utils::concatStatic;
		biConsumer2.accept("first_param", 6L);

		BiConsumer<String, Long> biConsumer3 = new Utils()::concat;
		biConsumer3.accept("first_param", 7L);

		// BiConsumer<String, Long> biConsumer4 = new Utils()::concatStatic;
		// biConsumer4.accept("first_param", 8L);

	}
}
