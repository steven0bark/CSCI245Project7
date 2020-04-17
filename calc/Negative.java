/**
 * 
 */
package calc;

/**
 * @author stevenbarker
 *
 */
public class Negative implements PosNegState {

	private Brain brain;
	
	/**
	 * 
	 */
	public Negative(Brain b) { brain = b; }

	@Override
	public Double updateOperand(Double num1, Double num2) { return num1 - num2; }

	@Override
	public void pmUpdate() { brain.setPosNegState(new Positive(brain)); }


	

}
