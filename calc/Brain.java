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
	public void operand(Double num){ output(operandstate.operand(num)); }

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
	public void pm() { operandstate.plusminus(); posnegstate = posnegstate.pmUpdate(); }
	
	/**
	 * This method is for efficency in writing numbers to the screen 
	 *
	 * @param num The number to be outputed
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

	
	
	/**
	 * OpState.java.
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
		 * Updates the operand
		 * @return the updated operand
		 */
		protected Double operand(Double num) {
			op = posnegstate.updateOperand(wholedecstate.modifyFirst(op), wholedecstate.modifySecond(num));
			return op;
		}

		/**
		 * Turns the operand either positive or negative
		 */
		protected void plusminus() { op *= -1; output(op); }

		/**
		 * @return The operand
		 */
		protected Double getOp() { return op; }

		/**
		 * Sets the operand
		 */
		protected void setOp(Double num) { op=num; }

		/**
		 * The evaluate method will usually do nothing unless it is 
		 * the second operand
		 */
		protected void evaluate() {}

		/**
		 * Determines the functionality when an operator is pressed
		 */
		public abstract void updateOperator(SetUp.EvalStrat s);
	}

		
		
		/**
		 * Op1.java.
		 *
		 * This is a subclass of OpState that is active when 
		 * the calculator is working with the first operand
		 *
		 * @author stevenbarker
		 */
	private class Op1 extends OpState{

		/**
		 * When an operand is pressed, it switches the OpState to Op2
		 */
		public void updateOperator(SetUp.EvalStrat s) {
			eval = s;
			posnegstate = pos;
			wholedecstate.resetDPlace();
			wholedecstate = whole;
			operandstate = op2;
		}
	}
		
		/**
		 * Op2.java.
		 *
		 * This the subclass of OpState that is active when
		 * the calculator is working with the second operand
		 *
		 * @author stevenbarker
		 */
	private class Op2 extends OpState {

		/**
		 * This evaluates the expression
		 */
		public void evaluate() {
			op1.setOp(eval.evaluate());  
			op= 0.0;
			output(op1.getOp());
		}

		/**
		 * When an operator is pressed, the expression will be evaluated
		 */
		public void updateOperator(SetUp.EvalStrat e) {
			equal();
			operandstate = this;
			eval = e;	
		}

	}
		
		/**
		 * InBetween.java.
		 *
		 * This is the subclass of OpState that is active
		 * between doing a calculation and doing something else
		 *
		 * @author stevenbarker
		 */
	public class InBetween extends OpState{
	
		/**
		 * When an operand is pressed, it will clear the calculator and set the operand
		 */
		public Double operand(Double num) {
			clear();
			op1.setOp(num);
			return op1.getOp();
		}

		/**
		 * When an operator is pressed, it will set the state to Op2
		 */
		public void updateOperator(SetUp.EvalStrat s) { eval = s; operandstate = op2; }

	}
		
		
		
		
		/**
		 * PosNegState.java.
		 *
		 * This class is the state to determine 
		 * 
		 * @author stevenbarker
		 */
		private abstract class PosNegState {
			
			/**
			 * Will either add or subtract the two numbers
			 *
			 * @return The number 
			 */
			public abstract Double updateOperand(Double num1, Double num2);
			
			/**
			 * Switches the states
			 */
			public abstract PosNegState pmUpdate();
		}

			
		/**
		 * Negative.java.
		 *
		 * This class is for updating a negative operand
		 *
		 * @author stevenbarker
		 */
		private class Negative extends PosNegState {
			
			/**
			 * Subtracts the two numbers
			 *
			 * @return The updated operand
			 */
			public Double updateOperand(Double num1, Double num2) { return num1 - num2; }

			/**
			 * Switches the state
			 */
			public PosNegState pmUpdate() { return pos; }
		}


		/**
		 * Positive.java.
		 *
		 * This class is for updating a positive operand
		 *
		 * @author stevenbarker
		 */
		private class Positive extends PosNegState{
			/**
			 * Adds the two numbers
			 *
			 * @return the operand
			 */
			public Double updateOperand(Double num1, Double num2) { return num1 + num2; }

			/**
			 * Switches the states
			 */
			public PosNegState pmUpdate() { return  neg; }
		}
			
			

		/**
		 * WholeDecState.java.
		 *
		 * This class is the state for modifying two numbers to be
		 * given to the posnegstate to either be added or subtracte
		 *
		 * @author stevenbarker
		 *
		 */
		private abstract class WholeDecState {

			/**
			 * The current decimals place 
			 */
			protected int dplace = 0;

			/**
			 * Resets the decimals place
			 */
			protected void resetDPlace() { dplace = 0; }

			/**
			 * Changes the first number in updating the operand into a form where it
			 * can be given to another method to simply added or subtracted from the 
			 * second number
			 *
			 * @return the modified number
			 */
			public abstract Double modifyFirst(Double num);
			
			/**
			 * Changes the second  number in updating the operand into a form where it
			 * can be given to another method to simply added or subtracted from the 
			 * first number
			 *
			 * @return The modified number
			 */
			public abstract Double modifySecond(Double num);

		}	
			
		/**
		 * Whole.java.
		 * 
		 * This class modfiies the number for a whole operand
		 *
		 * @author stevenbarker
		 *
		 */
		private class Whole extends WholeDecState {

			/**
			 * Multiplies the operand by 10
			 */
			public Double modifyFirst(Double num) { return num*10; }

			/**
			 * Just returns the same number
			 */
			public Double modifySecond(Double num) { return num; }
		}
				
		/**
		* Decimal.java.
		*
		* This class is for modifying numbers if the operand is not whole
		*
		* @author stevenbarker
		*/
		private class Decimal extends WholeDecState {

			/**
			 * Just returns the number
			 */
			public Double modifyFirst(Double num) { return num; }

			/**
			 * Multilplies the number by 10^-decimal places so it can be properly added
			 * to the operand
			 */
			public Double modifySecond(Double num) { dplace++; return num * Math.pow(10, -dplace); }
			
		}
				
		
}
