package com.cei.java8.tutorial.functional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author onlyone
 */
public class Predicate2 {

    public static void main(String[] args) {
	List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

	System.out.println("Languages which starts with J :");
	filter(languages, (str) -> str.startsWith("J"));

	System.out.println("Languages which ends with a ");
	filter(languages, (str) -> str.endsWith("a"));

	System.out.println("Print all languages :");
	filter(languages, (str) -> true);

	System.out.println("Print no language : ");
	filter(languages, (str) -> false);

	System.out.println("Print language whose length greater than 4:");
	filter(languages, (str) -> str.length() > 4);

	// 多个判断�?�件组�?�
	System.out.print("多个判断�?�件组�?�");
	Predicate<String> startsWithJ = (n) -> n.startsWith("J");
	Predicate<String> fourLetterLong = (n) -> n.length() == 4;
	languages.stream().filter(startsWithJ.and(fourLetterLong))
		.forEach((n) -> System.out.print(", which starts with 'J' and four letter long is : " + n));
    }

    /**
     * 对�?�一数�?��?采用�?�?�的判断�?�件处�?�
     */
    public static void filter(List<String> names, Predicate<String> condition) {
	for (String name : names) {
	    if (condition.test(name)) {
		System.out.println(name + " ");
	    }
	}
    }
}
