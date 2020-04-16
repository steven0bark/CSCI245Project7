package calc;

/**
 * @author stevenbarker
 *
 */
public interface OpStrat {
	Double updateOperand(Double num, Double operand);
	
	Double decimalUpdate(Double num, Double operand);
	
	Double pmUpdate(Double num, Double operand);

}
