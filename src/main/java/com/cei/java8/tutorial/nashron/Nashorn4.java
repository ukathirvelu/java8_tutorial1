package com.cei.java8.tutorial.nashron;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Working with java types from javascript.
 *
 */
public class Nashorn4 {

	public static void main(String[] args) throws Exception {
		ScriptEngine engine = new ScriptEngineManager()
				.getEngineByName("nashorn");
		engine.eval("loadWithNewGlobal('res/nashorn4.js')");
	}

}