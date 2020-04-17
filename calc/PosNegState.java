package calc;

/**
 * @author stevenbarker
 *
 */
public interface PosNegState {
	Double updateOperand(Double num1, Double num2);
	
	
	void pmUpdate();

}
