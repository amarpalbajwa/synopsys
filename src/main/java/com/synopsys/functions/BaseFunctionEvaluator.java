package com.synopsys.functions;

import java.math.BigDecimal;

public interface BaseFunctionEvaluator {

	BigDecimal evaluate(Object[] args) throws Exception;
	int getNumberOfParams() ;
}
