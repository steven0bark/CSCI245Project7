package calc;

public class NegDec implements OpStrat {

	private Brain brain;
	
	public NegDec(Brain b) { brain = b; }

	@Override
	public Double updateOperand(Double num, Double operand) {return operand - (num * (Math.pow(10, -brain.getDecimalsPlace())));}

	@Override
	public void decimalUpdate() {}

	@Override
	public Double pmUpdate(Double operand) {
		brain.setOpStrat(new PosDec(brain));
		return operand * -1;
	}

}
