/**
 * 
 */
package calc;

/**
 * @author stevenbarker
 *
 */
public class Op2 extends State {

	private Brain brain;
	
	/**
	 * 
	 */
	public Op2(Brain b) { brain = b; }

	@Override
	public Double[] operand(Double num, Double[] operands) {
		operands[1] = brain.getOpStrat().updateOperand(num, operands[1]);
		System.out.println("Operands[1]: " + operands[1]);
		brain.output(operands[1]);
		return operands;
	}

	@Override
	public Double[] evaluate(Double[] operands) {
		operands[0] = brain.getEvalStrat().evaluate();
		operands[1] = 0.0;
		brain.output(operands[0]);
		
		System.out.println("Answer: " + operands[0]);
		return operands;
	}

	@Override
	public void updateOperator(EvalStrat eval) {
		brain.equals();
		brain.switchState(this);
		brain.setEvalStrat(eval);
	}

	@Override
	public Double[] plusminus(Double[] operands) {
		operands[1] = brain.getOpStrat().pmUpdate(operands[1]);
		System.out.println("Operands[1]: " + operands[1]);
		brain.output(operands[1]);
		brain.setDecimalsPlace(0);
		return operands;
	}


}
