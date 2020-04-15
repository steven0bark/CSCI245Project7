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
	public abstract Double updateOperand(Double num, Double operand1, Double operand2);
	
	/**
	 * 
	 */
	public abstract void evaluate(Double operand1, Double operand2);
	
	/**
	 * 
	 */
	public abstract void updateOperator(Double operand1, Double operand2);


	
}
