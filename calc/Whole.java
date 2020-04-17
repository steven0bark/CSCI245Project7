package calc;

/**
 * @author stevenbarker
 *
 */
public class Whole implements WholeDecState {
	
	public Whole() {}

	public Double modifyFirst(Double num) { return num*10; }


	public Double modifySecond(Double num) { return num; }

}
