package calc;

/**
 * @author stevenbarker
 *
 */
public interface OpStrat {
	Double updateOperand(Double num, Double operand);
	
	void decimalUpdate();
	
	Double pmUpdate(Double operand);

}
