/**
 * 
 */
package calc;

/**
 * @author stevenbarker
 *
 */
public class InBetween extends State{

	private Brain brain;
	
	/**
	 * 
	 */
	public InBetween(Brain b) {brain = b;}

	@Override
	public Double[] operand(Double num, Double[] operands) {
		brain.clear();
		operands[0] = num;
		brain.output(operands[0]);
		brain.switchState(new Op1(brain));
		brain.setOpStrat(new PosWhole(brain));
		return operands;
		
	}

	@Override
	public Double[] evaluate(Double[] operands) { return operands; }

	@Override
	public void updateOperator(EvalStrat s) {
		brain.setEvalStrat(s);
		brain.switchState(new Op2(brain));
	}

	@Override
	public Double[] plusminus(Double[] operands) {
		return null;
	}

}
