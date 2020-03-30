package com.cei.java8.tutorial.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cei.java8.tutorial.util.Student;


public class Stream_collect {

    public static void main(String[] args) {
	List<Student> studentList = new ArrayList<>();
	studentList.add(new Student("Tom", "男", 18));
	studentList.add(new Student("Lily", "女", 30));
	studentList.add(new Student("John", "男", 29));
	studentList.add(new Student("Lucy", "女", 21));
	studentList.add(new Student("Jack", "男", 38));

	// test1();
	// test2(studentList);
	// test3(studentList);
//        test4(studentList);
	// test5(studentList);
	// test6(studentList);
//        test7(studentList);
	test8(studentList);
    }

    private static void test1() {
	List<String> citys = Arrays.asList("USA", "Japan", "France");
	String cityS = citys.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
	System.out.println(cityS);
    }

    private static void test2(List<Student> studentList) {
	Map<String, List<Student>> maps = studentList.stream().collect(Collectors.groupingBy(Student::getSex));
	System.out.println(maps);
    }

    private static void test3(List<Student> studentList) {
	Map<String, Map<String, List<Student>>> maps = studentList.stream()
		.collect(Collectors.groupingBy(Student::getSex, Collectors.groupingBy(s -> {
		    if (s.getAge() < 20) {
			return "低age";
		    } else {
			return "高age";
		    }
		})));
	System.out.println(maps);
    }

    private static void test4(List<Student> studentList) {
	Map<Boolean, List<Student>> maps = studentList.stream().collect(Collectors.partitioningBy(s -> {
	    if (s.getAge() < 25) {
		return true;
	    } else {
		return false;
	    }
	}));
	System.out.println(maps);
    }

    private static void test5(List<Student> studentList) {
	Optional<Student> optional1 = studentList.stream()
		.collect(Collectors.maxBy(Comparator.comparing(Student::getAge)));
	optional1.ifPresent(System.out::println);

	Optional<Student> optional2 = studentList.stream()
		.collect(Collectors.minBy(Comparator.comparing(Student::getAge)));
	optional2.ifPresent(System.out::println);
    }

    private static void test6(List<Student> studentList) {
	int sum = studentList.stream().collect(Collectors.reducing(0, Student::getAge, Integer::sum));
	System.out.println(sum);
    }

    // https://cloud.tencent.com/developer/article/1351931
    private static void test7(List<Student> studentList) {
	Map<String, String> sexNameMap = studentList.stream().collect(Collectors.toMap(p -> {
	    return p.getSex();
	}, p2 -> {
	    return p2.getName();
	}));
	System.out.println(sexNameMap);
    }

    private static void test8(List<Student> studentList) {
	Map<String, String> sexNameMap = studentList.stream().collect(Collectors.toMap(p -> {
	    return p.getSex();
	}, p2 -> {
	    return p2.getName();
	}, (oldValue, newValue) -> newValue));
	System.out.println(sexNameMap);
    }
}
