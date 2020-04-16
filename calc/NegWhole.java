/**
 * 
 */
package calc;

/**
 * @author stevenbarker
 *
 */
public class NegWhole implements OpStrat {

	Brain brain;
	
	/**
	 * 
	 */
	public NegWhole(Brain b) {brain = b;}

	@Override
	public Double updateOperand(Double num, Double operand) { return (operand*10) - num; }

	@Override
	public void decimalUpdate() { brain.setOpStrat(new NegDec(brain)); }

	@Override
	public Double pmUpdate(Double operand) { 
		brain.setOpStrat(new PosWhole(brain));
		return operand * -1;
	}

}
