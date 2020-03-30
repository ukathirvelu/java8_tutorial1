package com.cei.java8.tutorial.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class Stream_reduce {

	@Data
	@AllArgsConstructor
	static class Person {

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		String name;
		int age;

	}

	public static void main(String[] args) {
		List<Person> persons = Arrays.asList(new Person("Max", 18),
				new Person("Peter", 28), new Person("Pamela", 23),
				new Person("David", 12));
		// test1(persons);
		// test2(persons);
		// test3(persons);
		// test4(persons);
		// test5(persons);
		// test6(persons);
		test7();
	}

	private static void test1(List<Person> persons) {
		persons.stream().reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
				.ifPresent(System.out::println); // Pamela

		persons.stream().max(Comparator.comparing(Person::getAge))
				.ifPresent(System.out::println);
	}

	private static void test2(List<Person> persons) {
		Person result = persons.stream().reduce(new Person("", 0), (p1, p2) -> {
			p1.age += p2.age;
			p1.name += p2.name;
			return p1;
		});

		System.out.format("name=%s; age=%s", result.name, result.age);
	}

	private static void test3(List<Person> persons) {
		Integer ageSum = persons.stream().reduce(0, (sum, p) -> sum += p.age,
				(sum1, sum2) -> sum1 + sum2);

		System.out.println(ageSum);
	}

	private static void test4(List<Person> persons) {
		Integer ageSum = persons.stream().reduce(0, (sum, p) -> {
			System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
			return sum += p.age;
		}, (sum1, sum2) -> {
			System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
			return sum1 + sum2;
		});

		System.out.println(ageSum);
	}

	private static void test5(List<Person> persons) {
		Integer ageSum = persons.parallelStream().reduce(0, (sum, p) -> {
			System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
			return sum += p.age;
		}, (sum1, sum2) -> {
			System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
			return sum1 + sum2;
		});

		System.out.println(ageSum);
	}

	private static void test6(List<Person> persons) {
		Integer ageSum = persons.parallelStream().reduce(0, (sum, p) -> {
			System.out.format("accumulator: sum=%s; person=%s; thread=%s\n",
					sum, p, Thread.currentThread().getName());
			return sum += p.age;
		}, (sum1, sum2) -> {
			System.out.format("combiner: sum1=%s; sum2=%s; thread=%s\n", sum1,
					sum2, Thread.currentThread().getName());
			return sum1 + sum2;
		});

		System.out.println(ageSum);

		// accumulator: sum=0; person=David; thread=
		// ForkJoinPool.commonPool-worker-3
		// accumulator: sum=0; person=Pamela; thread=main
		// accumulator: sum=0; person=Peter;
		// thread=ForkJoinPool.commonPool-worker-1
		// accumulator: sum=0; person=Max;
		// thread=ForkJoinPool.commonPool-worker-2
		// combiner: sum1=18; sum2=23; thread=ForkJoinPool.commonPool-worker-2
		// combiner: sum1=23; sum2=12; thread=main
		// combiner: sum1=41; sum2=35; thread=main
		// 76
	}

	private static void test7() {
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		Optional<String> reduced = stringCollection.stream().sorted()
				.reduce((s1, s2) -> s1 + "#" + s2);
		reduced.ifPresent(System.out::println);

		OptionalInt reduced1 = IntStream.range(0, 10).reduce((a, b) -> a + b);
		System.out.println(reduced1.getAsInt());

		int reduced2 = IntStream.range(0, 10).reduce(7, (a, b) -> a + b);
		System.out.println(reduced2);

		int reduced3 = IntStream.range(0, 10).reduce(7, Integer::sum);
		System.out.println(reduced3);

	}

}
