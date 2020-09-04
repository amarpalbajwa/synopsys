package com.synopsys.functions;

import java.math.BigDecimal;
import java.util.Map;

import com.synopsys.Utils.FunctionEvaluatorConstants;
import com.synopsys.Utils.FunctionEvaluatorUtil;

public class PerformArithmeticOperation implements BaseFunctionEvaluator {

	public PerformArithmeticOperation(char operationType, Map<String, BigDecimal> valueMapForLet) {
		this.operationType = operationType;
		this.valueFromMap = valueMapForLet;
	}

	private BigDecimal paramOne;
	private BigDecimal paramTwo;
	private final char operationType;
	private Map<String, BigDecimal> valueFromMap;

	@Override
	public BigDecimal evaluate(Object[] args) throws Exception {
		if (args.length == getNumberOfParams()) {
			resolveParams(args);
			return performArithmeticOperation(paramOne, paramTwo, operationType);
		}
		return null;
	}

	private void resolveParams(Object[] args) throws Exception {
		if (args[0] instanceof BigDecimal) {
			paramOne = (BigDecimal) args[0];
		} else if (FunctionEvaluatorUtil.isNumber(args[0])) {
			paramOne = new BigDecimal(args[0].toString());
		} else {
			paramOne = valueFromMap.get(args[0]);
		}

		if (args[1] instanceof BigDecimal) {
			paramTwo = (BigDecimal) args[1];
		} else if (FunctionEvaluatorUtil.isNumber(args[1])) {
			paramTwo = new BigDecimal(args[1].toString());
		} else {
			paramTwo = valueFromMap.get(args[1]);
		}

	}

	@Override
	public int getNumberOfParams() {
		return 2;
	}

	public static BigDecimal performArithmeticOperation(BigDecimal valueOne, BigDecimal valueTwo, char operation)
			throws Exception {
		BigDecimal ans = null;
		valueOne.setScale(FunctionEvaluatorConstants.DECIMAL_SCALE);
		valueTwo.setScale(FunctionEvaluatorConstants.DECIMAL_SCALE);
		switch (operation) {
		case '+':
			ans = valueOne.add(valueTwo);
			break;
		case '/':
			ans = valueOne.divide(valueTwo, FunctionEvaluatorConstants.ROUNDING_MODE);
			break;
		case '*':
			ans = valueOne.multiply(valueTwo);
			break;
		case '-':
			ans = valueOne.subtract(valueTwo);
			break;

		}
		return ans;
	}
}
