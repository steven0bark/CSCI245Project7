package calc;

/**
 * SetUp.java
 * 
 * Class to set up and start the calculator, plus
 * facilities for test-driving the calculator.
 *
 * @author Thomas VanDrunen
 * CS 245, Wheaton College
 * June 27, 2014
*/
public class SetUp {

	/**
	 * EvalStrat.java
	 * 
	 * This interface is the strategy for evaluating the 
	 * expression, each instance is defined anonymously
	 * inside the anonymous class in adding the action
	 * listeners to the operator buttons.
	 *
	 * @author Steven Barker
	 */
	public interface EvalStrat { Double evaluate(); }
	
	
	
	/**
	 * Method for initializing the calculator internals and
	 * connecting them to the calculator face.
	 * @param face The component representing the user interface of 
	 * the calculator. 
	 */
	public static void setUpCalculator(CalculatorFace face) {

		Brain brain  = new Brain(face);
		
		for(double i = 0.0; i < 10; i++) {
			final double a = i;
			face.addNumberActionListener((int)i, (e) -> {brain.operand(a);});
		}
		
		
		//When the operator buttons are pressed, it will tell the brain what strategy it needs to use 
		//when evaluating the expression. 
		face.addActionListener('+', (e) -> {brain.operator(() -> {return brain.getOp1() + brain.getOp2();});});
		face.addActionListener('-', (e) -> {brain.operator(() -> {return brain.getOp1() - brain.getOp2();});});
		face.addActionListener('*', (e) -> {brain.operator(() -> {return brain.getOp1() * brain.getOp2();});});
		face.addActionListener('/', (e) -> {brain.operator(() -> {return brain.getOp1() / brain.getOp2();});});
		
		
		face.addActionListener('C', (e) -> {brain.clear();});
		face.addActionListener('=', (e) -> {brain.equal();});
		face.addActionListener('.', (e) -> {brain.decimal();});
		face.addPlusMinusActionListener((e -> {brain.pm();}));
		
	
		
	}
	
	
	/**
	 * This main method is for your testing of your calculator.
	 * It will *not* be used during grading. Any changes you make
	 * to this method will be ignored at grading.
	 */
	public static void main(String[] args) {
		setUpCalculator(new PlainCalculatorFace());
	}

}
