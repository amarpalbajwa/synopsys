package com.synopsys.Utils;

import java.util.regex.Pattern;

public class FunctionEvaluatorUtil {
	private static final Pattern pattern = Pattern.compile("\\d+");

	public static boolean isNumber(Object str) {
		return pattern.matcher(str.toString()).matches();
	}

	public static boolean startsWithArithmeticFunction(String expression) {
		String trimmed = expression.trim().toUpperCase();
		return trimmed.startsWith(FunctionEvaluatorConstants.ADD_FUNCTION)
				|| trimmed.startsWith(FunctionEvaluatorConstants.DIVIDE_FUNCTION)
				|| trimmed.startsWith(FunctionEvaluatorConstants.MULTIPLY_FUNCTION)
				|| trimmed.startsWith(FunctionEvaluatorConstants.DIVIDE_FUNCTION);
	}

	public static boolean startsWithLetFunction(String expression) {
		String trimmed = expression.trim().toUpperCase();
		return trimmed.startsWith(FunctionEvaluatorConstants.LET_FUNCTION);
	}

}
