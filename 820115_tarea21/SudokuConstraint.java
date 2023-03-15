package aima.gui.sudoku.csp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
public class SudokuConstraint implements Constraint{
	
		 private Variable var1;
		 private Variable var2;
		 private List<Variable> scope;
	
		 public SudokuConstraint(Variable var1, Variable var2) {
			 this.var1 = var1;
			 this.var2 = var2;
			 scope = new ArrayList<Variable>(2);
	         scope.add(var1);
	         scope.add(var2);
		 
		 }


		public boolean isSatisfiedWith(Assignment assignment) {
			 Integer value1 = (Integer)assignment.getAssignment(var1);
		     Integer value2 = (Integer)assignment.getAssignment(var2);
		        return value1 == null || value2 == null ||
		                  value1.intValue()!=value2.intValue();
		 }


		@Override
		public List<Variable> getScope() {
			// TODO Auto-generated method stub
			return scope;
		}
		
	 
	
}