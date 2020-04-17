package calc;

public class Positive implements PosNegState{
	
	private Brain brain;
	
	public Positive(Brain b) { brain = b; }
	
	public Double updateOperand(Double num1, Double num2) { return num1 + num2; }

	@Override
	public void pmUpdate() { brain.setPosNegState(new Negative(brain));}

}
