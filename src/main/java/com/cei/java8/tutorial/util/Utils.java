package com.cei.java8.tutorial.util;

public class Utils {

    public String concat(String param1, Long param2) {
	String result = param1 + " " + param2;
	System.out.println(result);
	return result;
    }

    public static String concatStatic(String param1, Long param2) {
	String result = param1 + " " + param2;
	System.out.println(result);
	return result;
    }
}
