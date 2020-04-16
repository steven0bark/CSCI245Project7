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

	@Override
	public Double[] evaluate(Double[] operands) { return operands; }

	@Override
	public void updateOperator(EvalStrat s) {
		brain.setEvalStrat(s);
		brain.switchState(new Op2(brain));
		
	}

}
