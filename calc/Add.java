package calc;

/**
 * @author stevenbarker
 *
 */
public class Add extends State{
	
	Brain brain;
	
	/**
	 * 
	 */
	public Add(CalculatorFace f, Brain b){
		face = f;
		brain = b;
	}

	@Override
	public Double updateOperand(Double num, Double operand1, Double operand2) { 
		operand2 = (operand2 * 10) + num; 
		return operand2;
	}

	@Override
	public void evaluate(Double operand1, Double operand2) { brain.output(operand1 + operand2); }

	@Override
	public void updateOperator(Double operand1, Double operand2) {
		evaluate(operand1, operand2);
		brain.setEvalState(this);
	}


}
