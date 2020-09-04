package com.synopsys.FunctionEvaluator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class App {
	private final static Logger LOGGER = Logger.getLogger(App.class);

	public static void main(String[] args) {
		configureLogger();
		List<String> givenExps = new ArrayList<>();
		for (String arg : args) {
			if (!arg.isEmpty()) {
				givenExps.add(arg);
			}
		}
//		//TODO: comment these lines before sending
//		givenExps.add("add(1, 2)");
//		givenExps.add("add(1, mult(2, 3))");
//		givenExps.add("mult(add(2, 2), div(9, 3))");
//		givenExps.add("let(a, 5, add(a, a))");
//		givenExps.add("let(a, 5, let(b, mult(a, 10), add(b, a)))");
//		givenExps.add("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))");
//		givenExps.add("add(1, let(a, 5, add(a, a)))");
//		givenExps.add("add(let(a, 5, add(a, a)), let(a, 5, add(a, a)))");
		LOGGER.debug("Input expression(s) = " + givenExps);
		RequestHandler oHandler = new RequestHandler();
		oHandler.handleRequest(givenExps);
	}

	private static void configureLogger() {
		BasicConfigurator.configure();
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources" + File.separator + "log4j.xml";
		String log4jConfigFile2 = System.getProperty("user.dir") + File.separator + "log4j.xml";
		if (new File(log4jConfigFile).exists()) {
			DOMConfigurator.configure(log4jConfigFile);
		} else if (new File(log4jConfigFile2).exists()) {
			DOMConfigurator.configure(log4jConfigFile2);
		}
	}
}
