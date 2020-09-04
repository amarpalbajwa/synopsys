package com.synopsys.functions;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.synopsys.Utils.FunctionEvaluatorUtil;

public class EvaluateString {
	private final static Logger LOGGER = Logger.getLogger(EvaluateString.class);

	public Object parse(FunctionEvaluator functionEvaluator, String givenExpression, Map<String, BigDecimal> contextMap)
			throws Exception {
		char[] tokens = givenExpression.toCharArray();
		int currentIndex = 0;

		// retrieving FunctionName
		StringBuilder functionName = new StringBuilder();
		currentIndex = getString(tokens, currentIndex, functionName, '(');
		String functionNameStr = functionName.toString();

		if (FunctionEvaluatorUtil.startsWithLetFunction(functionNameStr)) {
			return evaluateLetExpression(functionEvaluator, contextMap, tokens, currentIndex);
		} else {
			return evaluateAritmaticExpression(functionEvaluator, contextMap, tokens, currentIndex, functionNameStr);
		}
	}

	private Object evaluateAritmaticExpression(FunctionEvaluator functionEvaluator, Map<String, BigDecimal> contextMap,
			char[] tokens, int currentIndex, String functionNameStr) throws Exception {
		Object valueOne, valueTwo;

		StringBuilder firstParam = new StringBuilder();
		currentIndex = getExpression(tokens, currentIndex + 1, firstParam, ',');
		String firstParamStr = firstParam.toString();

		if (FunctionEvaluatorUtil.isNumber(firstParamStr)) {
			valueOne = new BigDecimal(firstParamStr);
		} else if (FunctionEvaluatorUtil.startsWithArithmeticFunction(firstParamStr)
				|| FunctionEvaluatorUtil.startsWithLetFunction(firstParamStr)) {
			valueOne = functionEvaluator.evaluateGivenString(firstParamStr);
		} else {
			valueOne = firstParamStr;
		}

		StringBuilder secondParam = new StringBuilder();
		currentIndex = getExpression(tokens, currentIndex + 1, secondParam, ')');
		String secondParamStr = secondParam.toString();

		if (FunctionEvaluatorUtil.isNumber(secondParamStr)) {
			valueTwo = new BigDecimal(secondParamStr);
		} else if (FunctionEvaluatorUtil.startsWithArithmeticFunction(secondParamStr)
				|| FunctionEvaluatorUtil.startsWithLetFunction(secondParamStr)) {
			valueTwo = functionEvaluator.evaluateGivenString(secondParamStr);
		} else {
			valueTwo = secondParamStr;
		}

		return performOperation(functionNameStr, valueOne, valueTwo, contextMap);
	}

	private Object evaluateLetExpression(FunctionEvaluator functionEvaluator, Map<String, BigDecimal> contextMap,
			char[] tokens, int currentIndex) throws Exception {
		// assuming let function always, so going to get first variable name
		StringBuilder currVariableName = new StringBuilder();
		currentIndex = getString(tokens, currentIndex + 1, currVariableName, ',');
		String currentVariableName = currVariableName.toString();

		// next going for value parameter
		StringBuilder valueParameter = new StringBuilder();
		currentIndex = getExpression(tokens, currentIndex + 1, valueParameter, ',');
		if (FunctionEvaluatorUtil.isNumber(valueParameter.toString())) {
			contextMap.put(currentVariableName, new BigDecimal(valueParameter.toString()));
		} else {
			contextMap.put(currentVariableName, functionEvaluator.evaluateGivenString(valueParameter.toString()));
		}

		// next going for evaluation of last parameter
		StringBuilder expressionToBeEvaluated = new StringBuilder();
		currentIndex = getExpression(tokens, currentIndex + 1, expressionToBeEvaluated, ')');
		String expression = expressionToBeEvaluated.toString();
		return expression;
	}

	private int getExpression(char[] tokens, int index, StringBuilder expression, char breakChar) {
		Stack<Character> charStack = new Stack<>();
		while (index < tokens.length) {
			if (breakChar == tokens[index] && charStack.size() == 0) {
				break;
			} else {
				char currentChar = tokens[index++];
				if (currentChar == '(') {
					charStack.push(currentChar);
					expression.append(currentChar);
				} else if (currentChar == ',')
					expression.append(currentChar);
				else if (currentChar == ')') {
					if (charStack.peek() == '(') {
						charStack.pop();
					}
					expression.append(currentChar);
				} else {
					expression.append(currentChar);
				}
			}
		}
		return index;
	}

	private int getString(char[] tokens, int index, StringBuilder string, char breakChar) {
		while (index < tokens.length && tokens[index] != breakChar) {
			string.append(tokens[index++]);
		}
		return index;
	}

	public BigDecimal performOperation(String functionName, Object valueOne, Object valueTwo,
			Map<String, BigDecimal> valueMap) {
		BaseFunctionEvaluator functionEvaluator = FunctionExecutorProvider.getFunctionExecutor(functionName, valueMap);
		try {
			Object[] args = { valueOne, valueTwo };
			return functionEvaluator.evaluate(args);
		} catch (Exception e) {
			LOGGER.error("something went wrong while evaluating the function", e);
		}

		return null;
	}
}
