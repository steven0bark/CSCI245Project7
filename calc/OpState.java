package calc;

/**
 * 
 * 
 * @author stevenbarker
 */
public abstract class OpState {

	CalculatorFace face;
	
	/**
	 * 
	 */
	public OpState() {}
	
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
	
	/**
	 * 
	 */
	public abstract Double[] plusminus(Double [] operands);

}
