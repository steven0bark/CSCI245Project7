package calc;


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
	public Double updateOperand(Double num, Double operand) { return operand + (num * Math.pow(10, -brain.getDecimalsPlace())); }

	@Override
	public void decimalUpdate() {}

	@Override
	public Double pmUpdate(Double operand) {
		brain.setOpStrat(new NegDec(brain));
		return operand * -1;
	}

}
