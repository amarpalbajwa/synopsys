package com.synopsys.FunctionEvaluator;

import java.util.List;

import org.apache.log4j.Logger;

import com.synopsys.functions.FunctionEvaluator;

public class RequestHandler {
	private final static Logger LOGGER = Logger.getLogger(RequestHandler.class);

	public void handleRequest(List<String> givenExpression) {
		if (givenExpression == null || givenExpression.size() == 0) {
			return;
		}
		for (String currExp : givenExpression) {
			if (org.apache.commons.lang3.StringUtils.isNotBlank(currExp)) {
				Object ans = null;
				try {
					currExp = currExp.replaceAll(" ", "");
					FunctionEvaluator functionEvaluator = new FunctionEvaluator();
					ans = functionEvaluator.evaluateGivenString(currExp);
				} catch (Exception e) {
					LOGGER.error("something went wrong", e);
				}
				System.out.println("java calculator.Main " + currExp + "\n" + ans);
			} else {
				LOGGER.error("givenExpression found to be null or blank");
			}
		}

	}
}
