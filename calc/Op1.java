/**
 * 
 */
package calc;

/**
 * @author stevenbarker
 *
 */
public class Op1 extends OpState{

	private Brain brain;
	
	/**
	 * 
	 */
	public Op1(Brain b) { brain = b; }


	public Double[] operand(Double num, Double[] operands) { 
		WholeDecState wholedec = brain.getWholeDecState();
		operands[0] = brain.getPosNegState().updateOperand(wholedec.modifyFirst(operands[0]), wholedec.modifySecond(num));
		System.out.println("Operands[0]: " + operands[0]);
		brain.output(operands[0]);
		return operands;

	}
	

	@Override
	public Double[] evaluate(Double[] operands) { return operands; }

	@Override
	public void updateOperator(EvalStrat s) {
		brain.setEvalStrat(s);
		brain.setOpState(new Op2(brain));
		brain.setDecimalsPlace(0);
		brain.setPosNegState(new Positive(brain));
		brain.setWholeDecState();
		
	}


	@Override
	public Double[] plusminus(Double[] operands) {
		operands[0] *= -1;
		System.out.println("Operands[0]: " + operands[0]);
		brain.output(operands[0]);
		return operands;
	}

}
