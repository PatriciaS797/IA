package aima.gui.sudoku.csp;
/**
 * AvailableCells crea el juego del sudoku con un número de celdas dadas.
 * Cada celda juega el papel de una variable en el problema CSP.
 */
import java.util.ArrayList;

import java.util.List;
import aima.core.search.csp.Variable;
public class AvailableCells {
     private int numOfAvailable;
     private List<Variable> list;

     public AvailableCells(int num) {
          this.numOfAvailable = num;
          list = new ArrayList<Variable>(numOfAvailable);
     }

     public void insert(int i, int j, int value)  {
          SudokuVariable variable = new SudokuVariable("Cell at [" + i +"]["+j+"]",i,j);
          variable.setValue(value);
          list.add(variable);
     }

     public int getNumOfAvailable() {
          return numOfAvailable;
     }

     public List<Variable> getList() {
          return list;
     }
}