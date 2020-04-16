/**
 * 
 */
package calc;

/**
 * @author stevenbarker
 *
 */
public class Whole implements OpStrat {

	private Brain brain;
	
	/**
	 * 
	 */
	public Whole(Brain b) { brain = b; }

	@Override
	public Double updateOperand(Double num, Double operand) { 
		return operand*10 + num;
		 
	}
	
	public Double decimalUpdate(Double num, Double operands) { return null; }


	public Double pmUpdate(Double num, Double operand) { return null; }

}
