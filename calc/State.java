package calc;

/**
 * 
 * 
 * @author stevenbarker
 */
public abstract class State {

	CalculatorFace face;
	
	/**
	 * 
	 */
	public State() {}
	
	/**
	 * 
	 */
	public abstract Double[] operand(Double num, Double[] operands);
	
	/**
	 * 
	 */
	public abstract Double[] evaluate(Double[] operands);
	
	/**
	 * 
	 */
	public abstract void updateOperator(EvalStrat s);
	
	
}
