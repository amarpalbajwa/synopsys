package com.synopsys.functions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.synopsys.FunctionEvaluator.App;

public class FunctionEvaluator {
	private final static Logger LOGGER = Logger.getLogger(App.class);
	private EvaluateString letFunctionStringParser = new EvaluateString();
	private Map<String, BigDecimal> valueMap = new HashMap<>();

	private Object evaluateLetFunction(String strToEvaluate) throws Exception {
		return letFunctionStringParser.parse(this, strToEvaluate, valueMap);
	}

	public BigDecimal evaluateGivenString(String strToEvaluate) throws Exception {
		try {
			Object ansFound = evaluateLetFunction(strToEvaluate);
			if (ansFound instanceof BigDecimal) {
				return (BigDecimal) ansFound;
			}

			return evaluateGivenString(ansFound.toString());
		} catch (Exception e) {
			LOGGER.debug("Error parsing expression", e);
		}
		return null;
	}

}
