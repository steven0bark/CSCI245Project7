package calc;

import java.util.*;
import java.text.DecimalFormat;


/**
 * Brain.java
 *
 * This is the brain behind the calc and the controller for the states
 *
 *@author Steven Barker
 * CS 245, Wheaton College
 * April 14th, 2020
 */


public class Brain {

	private CalculatorFace face;
	
	private State evalstate;
	
	private State currentstate;
	
	private HashSet<State> currentmode;

	private HashSet<State> mode1;
	
	private HashSet<State> mode2;
	
	private Double operand1;
	
	private Double operand2;

	private DecimalFormat form;
	
	public Brain(CalculatorFace f){ 
		face = f; 
		form = new DecimalFormat("#.####");
		
	}

	
	public void operand(Double num){ currentstate.updateOperand(num, operand1, operand2); }

	public void operator() { currentstate.updateOperator(operand1,operand2); }
	
	public void decimal() {}
	
	public void pm() {}
	
	public void clear() {}
	
	public void output(Double num) { face.writeToScreen(form.format(num)); }
	
	public void setEvalState(State s) { evalstate = s; }
	


}
