import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.synopsys.functions.FunctionEvaluator;

public class validateFunctionEvaluator {

	@Test
	public void test() throws Exception {
		String currExp = "add(1, 2)";
		currExp = currExp.replaceAll(" ", "");
		FunctionEvaluator functionEvaluator = new FunctionEvaluator();
		BigDecimal ans = functionEvaluator.evaluateGivenString(currExp);
		Assert.assertEquals(new BigDecimal(3), ans);
	}

	@Test
	public void validateLetFunction() throws Exception {
		String currExp = "add(let(a, 5, add(a, a)), let(a, 5, add(a, a)))";
		currExp = currExp.replaceAll(" ", "");
		FunctionEvaluator functionEvaluator = new FunctionEvaluator();
		BigDecimal ans = functionEvaluator.evaluateGivenString(currExp);
		Assert.assertEquals(new BigDecimal(20), ans);
	}

	@Test
	public void validateFailsGracefully() throws Exception {
		String currExp = "add(let(a, 5, add(a, b)), let(a, 5, add(a, a)))";
		currExp = currExp.replaceAll(" ", "");
		FunctionEvaluator functionEvaluator = new FunctionEvaluator();
		BigDecimal ans = functionEvaluator.evaluateGivenString(currExp);
		Assert.assertTrue(ans == null);
	}

}
