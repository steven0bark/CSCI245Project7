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

//{{{Main
	private CalculatorFace face;
	
	private SetUp.EvalStrat eval;
	
	private PosNegState posnegstate = new Positive();
	
	private WholeDecState wholedecstate = new Whole();
	
	private OpState operandstate = new Op1();
	
	private Double operand1 = 0.0;
	
	private Double operand2 = 0.0;
	
	private Double[] operands = new Double[2];

	private DecimalFormat form;
	
	private int dplace = 0;
	
	public Brain(calc.CalculatorFace f){ 
		face = f; 
		form = new DecimalFormat("#.####");
		operands[0] = operand1;
		operands[1] = operand2;
		
		
	}

	
	public void operand(Double num){ operandstate.operand(num,0); }

	public void operator(SetUp.EvalStrat e) { operandstate.updateOperator(e); }
	
	public void decimal() { wholedecstate = new Decimal(); }
	
	public void pm() {
		operandstate.plusminus();
		posnegstate.pmUpdate();
	}
	
	public void output(Double num) { face.writeToScreen(form.format(num)); }
	
	public void equal() { 
		operandstate.evaluate(); 
		posnegstate = new Positive();
		wholedecstate = new Whole();
		operandstate = new InBetween();
	}

	public void clear() {
		operands[0] = 0.0;
		operands[1] = 0.0;
		posnegstate = new Positive();
		wholedecstate = new Whole();
		operandstate = new Op1();
		dplace = 0;
		face.writeToScreen("");
		
	}
	
	public Double[] getOperands() {return operands;}


	
	/*Getters, setters, and switchers*/
	//public void setEvalStrat(SetUp.EvalStrat e) { eval = e; }
	
	public SetUp.EvalStrat getEvalStrat() { return eval; }
	
//}}}	
	
	
//{{{OpState
	
	/**
	 * 
	 * 
	 * @author stevenbarker
	 */
	public abstract class OpState {

		public void operand(Double num, int i) {
			operands[i] = posnegstate.updateOperand(wholedecstate.modifyFirst(operands[i]), wholedecstate.modifySecond(num));
			output(operands[i]);
		}
		
		public abstract void evaluate(); 

		public abstract void updateOperator(SetUp.EvalStrat s);
		
		public abstract void plusminus();

	}

		
		
		/**
		 * @author stevenbarker
		 *
		 */
		public class Op1 extends OpState{
			
			public void operand(Double num, int i) { super.operand(num, 0); }

			public void evaluate() {} 

			public void updateOperator(SetUp.EvalStrat s) {
				eval = s;
				dplace = 0;
				posnegstate = new Positive();
				wholedecstate = new Whole();
				operandstate = new Op2();
				
			}

			public void plusminus() {
				operands[0] *= -1;
				output(operands[0]);
			}

		}
		
		/**
		 * @author stevenbarker
		 *
		 */
		public class Op2 extends OpState {
			
			public void operand(Double num, int i) { super.operand(num, 1); }

			public void evaluate() {
				operands[0] = eval.evaluate();
				operands[1] = 0.0;
				output(operands[0]);
			}

			public void updateOperator(SetUp.EvalStrat e) {
				equal();
				operandstate = this;
				eval = e;
				
			}

			public void plusminus() {
				operands[1] *= -1; 
				output(operands[1]);
			}

		}
		
		/**
		 * @author stevenbarker
		 *
		 */
		public class InBetween extends OpState{
			
			public void operand(Double num, int i) {
				clear();
				operands[0] = num;
				output(operands[0]);
				operandstate = new Op1();
				wholedecstate = new Whole();
			}

			public void evaluate() {} 

			public void updateOperator(SetUp.EvalStrat s) {
				eval = s;
				operandstate = new Op2();
			}

			public void plusminus() {} 

		}
		
//}}}		
		
		
//{{{PosNegState		
		
		
		/**
		 * @author stevenbarker
		 *
		 */
		public interface PosNegState {
			
			Double updateOperand(Double num1, Double num2);
			
			void pmUpdate();
		}

			
			/**
			 * @author stevenbarker
			 *
			 */
			public class Negative implements PosNegState {

		
				public Double updateOperand(Double num1, Double num2) { return num1 - num2; }


				public void pmUpdate() { posnegstate = new Positive(); }


			}


			/**
			 * 
			 * @author stevenbarker
			 *
			 */
			public  class Positive implements PosNegState{
				
				
				public Positive() {}
				
				public Double updateOperand(Double num1, Double num2) { return num1 + num2; }

				public void pmUpdate() { posnegstate = new Negative();}



			}
///}}}			
			
			
//{{{WholeDecState			
			

			public interface WholeDecState {
				Double modifyFirst(Double num);
				Double modifySecond(Double num);
			}	
				
			/**
			 * @author stevenbarker
			 *
			 */
			public class Whole implements WholeDecState {
				
				public Double modifyFirst(Double num) { return num*10; }
				public Double modifySecond(Double num) { return num; }

			}
				
			/**
			* @author stevenbarker
			*
			*/
			public class Decimal implements WholeDecState {
				public Double modifyFirst(Double num) { return num; }
				public Double modifySecond(Double num) {dplace++; return num * Math.pow(10, -dplace);}

			}
				
//}}}
		

}
