package calc;

/**
 * SetUp
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
	 * Method for initializing the calculator internals and
	 * connecting them to the calculator face.
	 * @param face The component representing the user interface of 
	 * the calculator. 
	 */
	public static void setUpCalculator(CalculatorFace face) {

		Brain brian  = new Brain(face);
		
		for(double i = 0.0; i < 10; i++) {
			final double a = i;
			face.addNumberActionListener((int)i, (e) -> {brian.operand(a);});
		}
		
		Character[] chars = {'+', '-', '*', '/'};
		for(int i = 0; i < 4; i++) {
			final char a = chars[i];
			face.addActionListener(a, (e) -> {brian.operator();});
		}
		
		face.addActionListener('C', (e) -> {brian.clear();});
		
		face.addPlusMinusActionListener((e -> {brian.pm();}));
		
	
		
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
