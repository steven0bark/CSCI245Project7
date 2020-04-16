/**
 * 
 */
package calc;

/**
 * @author stevenbarker
 *
 */
public class Op1 extends State{

	private Brain brain;
	
	/**
	 * 
	 */
	public Op1(Brain b) { brain = b; }


	public Double[] operand(Double num, Double[] operands) { 
		operands[0] = brain.getOpStrat().updateOperand(num, operands[0]); 
		System.out.println("Operands[0]: " + operands[0]);
		brain.output(operands[0]);
		return operands;

	}
	
	public void decimal() {
		brain.getOpStrat().decimalUpdate();
	}

	@Override
	public Double[] evaluate(Double[] operands) { return operands; }

	@Override
	public void updateOperator(EvalStrat s) {
		brain.setEvalStrat(s);
		brain.switchState(new Op2(brain));
		brain.setOpStrat(new PosWhole(brain));
		brain.setDecimalsPlace(0);
		
	}


	@Override
	public Double[] plusminus(Double[] operands) {
		operands[0] = brain.getOpStrat().pmUpdate(operands[0]);
		System.out.println("Operands[0]: " + operands[0]);
		brain.output(operands[0]);
		return operands;
	}

}
