package calc;
import java.text.DecimalFormat;


/**
 * Brain.java
 *
 * This is the brain behind the calc and the controller for the states.
 *
 * @author Steven Barker
 * CS 245, Wheaton College
 * April 14th, 2020
 */


public class Brain {


//{{{Controller
//{{{Instance Variables	

	/**
	 * The reference to the calculator face
	 */
	private CalculatorFace face;

	/**
	 * The current strategy for calculating
	 */
	private SetUp.EvalStrat eval;	

	/**
	 * The state for updating a positive operand
	 */
	private Positive pos = new Positive();

	/**
	 * The state for updating a negative operand
	 */
	private Negative neg = new Negative();

	/**
	 * The state for updating a whole operand
	 */
	private Whole whole = new Whole();
	
	/**
	 * The state for updating a non-whole number
	 */
	private Decimal dec = new Decimal();

	/**
	 * The state for updating the first operand
	 */
	private OpState op1 = new Op1();
	
	/**
	 * The state for operating the second operand
	 */
	private OpState op2 = new Op2();

	/**
	 * The inbetween state for updating operands
	 */
	private OpState inbetween = new InBetween();

	/**
	 * The current state for updating either positive or negative operands
	 */
	private PosNegState posnegstate = pos;

	/**
	 * The current state for updating either whole or non whole opernads
	 */
	private WholeDecState wholedecstate = whole;
	
	/**
	 * The current state for updating operands
	 */
	private OpState operandstate = op1;

	/**
	 * The format for writing numbers to the screen
	 */
	private DecimalFormat form = new DecimalFormat("#.####");
	
	/**
	 * The current decimals place
	 */
	private int dplace = 0;
//}}}
	

//{{{Brain Methods
		
	/**
	 * Constructor
	 *
	 * @param f the CalculatorFace
	 */
	public Brain(CalculatorFace f){ face = f; }
	
	/**
	 * @return the first operand for the EvalStrat classes in Setup
	 */
	public Double getOp1() { return op1.getOp(); }
	
	/**
	 * @return the second operand for the EvalStrat classes in Setup
	 */
	public Double getOp2() { return op2.getOp(); }
	
	/**
	 * When a button is pressed, the orperand with be updated.
	 *
	 * @param num The number that is pressed
	 */
	public void operand(Double num){ operandstate.operand(num); }

	/**
	 * When an operator is pressed, it will update the 
	 * operator in the current operand state
	 *
	 * @param e The strategy for evaluating the expression
	 */
	public void operator(SetUp.EvalStrat e) { operandstate.updateOperator(e); }
	
	/**
	 * When the decimal is pressed, it will ha
	 */
	public void decimal() { wholedecstate = dec; }
	
	/**
	 * When the plus minus button is pressed, the operand will be updated
	 * and the posneg state will be updated.
	 */
	public void pm() { operandstate.plusminus(); posnegstate.pmUpdate(); }
	
	/**
	 * This method is for effiency in writing numbers to the screen 
	 *
	 * @param num The number to be outputted
	 */
	public void output(Double num) { face.writeToScreen(form.format(num)); }
	
	/**
	 * When the equals button is pressed, the expression will be evaluated, 
	 * everything will be reset and the operandstate will be set to the 
	 * inbetween state
	 */
	public void equal() { 
		operandstate.evaluate(); 
		posnegstate = pos;
		wholedecstate.resetDPlace();
		wholedecstate = whole;
		operandstate = inbetween;
	}

	/**
	 * When the clear button is pressed this will reset everything
	 */
	public void clear() {
		op1.setOp(0.0);
		op2.setOp(0.0);
		posnegstate = pos;
		wholedecstate.resetDPlace();		
		wholedecstate = whole;
		operandstate = op1;
		face.writeToScreen("");	
	}
//}}}
//}}}

	
//{{{OpState
	
	/**
	 * OpState.java
	 *
	 * This is the superclass for the operand state
	 * 
	 * @author stevenbarker
	 */
	private abstract class OpState {
		
		/**
		 * The operand
		 */
		protected Double op = 0.0;
	
		/**
		 * 
		 */
		protected void operand(Double num) {
			op = posnegstate.updateOperand(wholedecstate.modifyFirst(op), 
					wholedecstate.modifySecond(num));
			output(op);
		}
		protected void plusminus() { op *= -1; output(op); }
		protected Double getOp() { return op; }
		protected void setOp(Double num) { op=num; }
		protected void evaluate() {}
		public abstract void updateOperator(SetUp.EvalStrat s);
	}

		
		
		/**
		 * Op1.java
		 *
		 * This is a subclass of OpState that is active when 
		 * the calculator is working with the first operand
		 *
		 * @author stevenbarker
		 */
	private class Op1 extends OpState{

		public void updateOperator(SetUp.EvalStrat s) {
			eval = s;
			posnegstate = pos;
			wholedecstate.resetDPlace();
			wholedecstate = whole;
			operandstate = op2;
		}
	}
		
		/**
		 * Op2.java
		 *
		 * This the subclass of OpState that is active when
		 * the calculator is working with the second operand
		 *
		 * @author stevenbarker
		 */
	private class Op2 extends OpState {

		@Override
		public void evaluate() {
			op1.setOp(eval.evaluate());  
			op= 0.0;
			output(op1.getOp());
		}

		public void updateOperator(SetUp.EvalStrat e) {
			equal();
			operandstate = this;
			eval = e;	
		}

	}
		
		/**
		 * InBetween.java
		 *
		 * This is the subclass of OpState that is active
		 * between doing a calculation and doing something else
		 *
		 * @author stevenbarker
		 */
	public class InBetween extends OpState{
			
		@Override
		public void operand(Double num) {
			clear();
			op1.setOp(num);
			output(op1.getOp());
		}

		public void updateOperator(SetUp.EvalStrat s) { eval = s; operandstate = op2; }
	}
		
//}}}		
		
		
//{{{PosNegState		
		
		
		/**
		 * @author stevenbarker
		 *
		 */
		private abstract class PosNegState {
			public abstract Double updateOperand(Double num1, Double num2);
			public abstract void pmUpdate();
		}

			
		/**
		 * @author stevenbarker
		 *
		 */
		private class Negative extends PosNegState {
			public Double updateOperand(Double num1, Double num2) { return num1 - num2; }
			public void pmUpdate() { posnegstate = pos; }			
		}


		/**
		 * 
		 * @author stevenbarker
		 *
		 */
		private class Positive extends PosNegState{
			public Double updateOperand(Double num1, Double num2) { return num1 + num2; }
			public void pmUpdate() { posnegstate = neg; }
		}
///}}}			
			
			
//{{{WholeDecState			
			

		private abstract class WholeDecState {
			protected int dplace = 0;
			protected void resetDPlace() { dplace = 0; }
			public abstract Double modifyFirst(Double num);
			public abstract Double modifySecond(Double num);
		}	
			
		/**
		 * @author stevenbarker
		 *
		 */
		private class Whole extends WholeDecState {
			public Double modifyFirst(Double num) { return num*10; }
			public Double modifySecond(Double num) { return num; }
		}
				
		/**
		* @author stevenbarker
		*
		*/
		private class Decimal extends WholeDecState {
			public Double modifyFirst(Double num) { return num; }
			public Double modifySecond(Double num) { dplace++; return num * Math.pow(10, -dplace); }
		}
				
//}}}
		
}
