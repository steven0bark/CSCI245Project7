/**
 * 
 */
package calc;

/**
 * @author stevenbarker
 *
 */
public class PosWhole implements OpStrat {

	private Brain brain;
	
	/**
	 * 
	 */
	public PosWhole(Brain b) { brain = b; }

	@Override
	public Double updateOperand(Double num, Double operand) { return operand*10 + num; }
	
	public void decimalUpdate() { brain.setOpStrat(new PosDec(brain)); }


	public Double pmUpdate(Double operand) { 
		brain.setOpStrat(new NegWhole(brain)); 
		return operand * -1;
	}

}
