package calc;

import java.util.*;
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

	private CalculatorFace face;
	
	private EvalStrat eval;
	
	private OpStrat op;
	
	private State currentstate;
	
	private Double operand1 = 0.0;
	
	private Double operand2 = 0.0;
	
	private Double[] operands = new Double[2];

	private DecimalFormat form;
	
	private int dplace = 0;
	
	public Brain(CalculatorFace f){ 
		face = f; 
		form = new DecimalFormat("#.####");
		operands[0] = operand1;
		operands[1] = operand2;
		op = new PosWhole(this);
		currentstate = new Op1(this);
		
	}

	
	public void operand(Double num){ operands = currentstate.operand(num, operands); }

	public void operator(EvalStrat e) { currentstate.updateOperator(e); }
	
	public void decimal() { op.decimalUpdate();}
	
	public void pm() {currentstate.plusminus(operands);}
	
	
	
	
	public void output(Double num) { face.writeToScreen(form.format(num)); }
	
	public void equals() { 
		currentstate.evaluate(operands); 
		switchState(new InBetween(this));
	}

	public void clear() {
		operands[0] = 0.0;
		operands[1] = 0.0;
		op = new PosWhole(this);
		currentstate = new Op1(this);
		dplace = 0;
		face.writeToScreen("");
		
	}
	
	/*Getters, setters, and switchers*/
	public Double[] getOperands() { return operands; }
	
	public OpStrat getOpStrat() { return op;}
	
	public void setOpStrat(OpStrat o) { op = o; }
	
	public void setEvalStrat(EvalStrat e) { eval = e; }
	
	public EvalStrat getEvalStrat() { return eval; }

	public void switchState(State s) { currentstate = s; }
	
	public int getDecimalsPlace() {
		dplace++;
		return dplace;
	}
	
	public void setDecimalsPlace(int n) { dplace = n; } 

}
