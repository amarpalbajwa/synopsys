package com.synopsys.functions;

import com.synopsys.Utils.FunctionEvaluatorConstants;

import java.math.BigDecimal;
import java.util.Map;

public class FunctionExecutorProvider {

	public static BaseFunctionEvaluator getFunctionExecutor(String functionName,
			Map<String, BigDecimal> valueMapForLet) {
		if (functionName.equalsIgnoreCase(FunctionEvaluatorConstants.ADD_FUNCTION)) {
			return new PerformArithmeticOperation('+', valueMapForLet);
		} else if (functionName.equalsIgnoreCase(FunctionEvaluatorConstants.SUBTRACT_FUNCTION)) {
			return new PerformArithmeticOperation('-', valueMapForLet);
		} else if (functionName.equalsIgnoreCase(FunctionEvaluatorConstants.DIVIDE_FUNCTION)) {
			return new PerformArithmeticOperation('/', valueMapForLet);
		} else if (functionName.equalsIgnoreCase(FunctionEvaluatorConstants.MULTIPLY_FUNCTION)) {
			return new PerformArithmeticOperation('*', valueMapForLet);
		}
		return null;
	}
}
