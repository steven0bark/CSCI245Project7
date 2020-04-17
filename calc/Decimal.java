/**
 * 
 */
package calc;

/**
 * @author stevenbarker
 *
 */
public class Decimal implements WholeDecState {

	private Brain brain;
	
	public Decimal(Brain b) {brain = b;}

	@Override
	public Double modifyFirst(Double num) { return num; }

	@Override
	public Double modifySecond(Double num) {return num * Math.pow(10, -brain.getDecimalsPlace());}

}
