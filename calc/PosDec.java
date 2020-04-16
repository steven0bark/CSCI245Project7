/**
 * 
 */
package calc;

import java.math.*;

/**
 * @author stevenbarker
 *
 */
public class PosDec implements OpStrat {

	private Brain brain;
	
	/**
	 * 
	 */
	public PosDec(Brain b) { brain = b; }

	@Override
	public Double updateOperand(Double num, Double operand) { return operand + num * Math.pow(10, -brain.getdecimalsplace()); }

	@Override
	public Double decimalUpdate(Double num, Double operand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double pmUpdate(Double num, Double operand) {
		// TODO Auto-generated method stub
		return null;
	}

}
