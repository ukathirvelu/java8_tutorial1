package com.cei.java8.tutorial.functional;

import java.util.function.BiFunction;

import com.cei.java8.tutorial.util.Score;


public class BiFunction1 {

    public static void main(String[] args) {
	Score s1 = new Score("xiaohong", 90L, 91L);

	BiFunction<String, Long, String> biFunction = (t1, t2) -> {
	    return t1 + "BIFUNCTION" + t2;
	};
	s1.printYuwenScore(biFunction);

    }
}
