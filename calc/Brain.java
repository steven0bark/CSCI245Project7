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
	
	private PosNegState posnegstate = new Positive(this);
	
	private WholeDecState wholedecstate = new Whole();
	
	private OpState operandstate = new Op1(this);
	
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
		
		
	}

	
	public void operand(Double num){ operands = operandstate.operand(num, operands); }

	public void operator(EvalStrat e) { operandstate.updateOperator(e); }
	
	public void decimal() { wholedecstate = new Decimal(this); }
	
	public void pm() {
		operandstate.plusminus(operands);
		posnegstate.pmUpdate();
	}
	
	public void output(Double num) { face.writeToScreen(form.format(num)); }
	
	public void equals() { 
		operandstate.evaluate(operands); 
		setOpState(new InBetween(this));
		setWholeDecState();
		posnegstate = new Positive(this);
	}

	public void clear() {
		operands[0] = 0.0;
		operands[1] = 0.0;
		operandstate = new Op1(this);
		posnegstate = new Positive(this);
		wholedecstate = new Whole();
		dplace = 0;
		face.writeToScreen("");
		
	}
	
	/*Getters, setters, and switchers*/
	public Double[] getOperands() { return operands; }
	
	public PosNegState getPosNegState() { return posnegstate; }
	
	public void setPosNegState(PosNegState s) { posnegstate = s; }
	
	public void setEvalStrat(EvalStrat e) { eval = e; }
	
	public EvalStrat getEvalStrat() { return eval; }

	public void setOpState(OpState s) { operandstate = s; }
	
	public WholeDecState getWholeDecState() { return wholedecstate; }
	
	public void setWholeDecState() { wholedecstate = new Whole(); }
	
	public int getDecimalsPlace() {
		dplace++;
		return dplace;
	}
	
	public void setDecimalsPlace(int n) { dplace = n; } 

}
