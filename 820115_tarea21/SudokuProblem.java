package aima.gui.sudoku.csp;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;


public class SudokuProblem extends CSP {
     private static final int cells = 81;
     private static List<Variable> variables = null;

     /**
      *
      * @return Devuelve la lista de variables del Sudoku.
      *         Nombre Cell at [i][j], y coordenadas i,j
      */
     private static List<Variable> collectVariables() {
          variables = new ArrayList<Variable>();
          for (int i = 0; i < 9; i++)
               for (int j = 0; j < 9; j++) {
                    variables.add(new SudokuVariable("Cell at [" + i + "][" + j
                              + "]", i, j));
               }
          return variables;
     }
     /**
      *
      * @param var variable del Sudoku
      * @return Dominio de la variable,
      *         si tiene valor el domio es el valor. Sino el domino 1-9
      */
     private static List<Integer> getSudokuDomain(SudokuVariable var) {
          List<Integer> list = new ArrayList<Integer>();
          if (var.getValue() != 0) {
               list.add(new Integer(var.getValue()));
               return list;
          } else
               for (int i = 1; i <= 9; i++)
                    list.add(new Integer(i));
          return list;
     }

     /**
      * Define como un CSP. Define variables, sus dominios y restricciones.
      * @param pack
      */
     public SudokuProblem(AvailableCells pack) {
    	     //variables
          super(collectVariables());
          initialize(pack);
          for (int i=0;i<81;i++) {
               SudokuVariable x = (SudokuVariable) variables.get(i);
          }
          //Define dominios de variables
          Domain domain;
          for (Variable var : getVariables()) {
               domain = new Domain(getSudokuDomain((SudokuVariable) var));
               setDomain(var, domain);
          }
          //restricciones
          doConstraint();
     }
     /**
      * Inicializa las variables a partir de las celdas disponibles, que
      * tienen valor. Recorren las listas de variables del Sudoku y del
      * pack, y si tienen las mismas coordenadas, les da el valor que tiene.
      * @param pack
      */
     private void initialize(AvailableCells pack) {
          List<Variable> alList = pack.getList();
          Domain domain;
          for (int i = 0; i < cells; i++) {
               SudokuVariable var1 = (SudokuVariable) variables.get(i);
               for (int j = 0; j < pack.getNumOfAvailable(); j++) {
                    SudokuVariable var2 = (SudokuVariable) alList.get(j);
                    if (var1.getX() == var2.getX() && var1.getY() == var2.getY()) {
                         var1.setValue(var2.getValue());
                    }
               }
          }
     }
     private void doConstraint() {
          int index,h,x,y;
          for (int i = 0; i < 9; i++)
               for (int j = 0; j < 9; j++) {
                     index = i * 9 + j;   // índice de la variable en un vector 0..80
                     for (int k=0; k<9; k++){   // recorro columnas de la  fila i
                         // Elementos de FILAS DISTINTOS
                         h = i*9 + k; 		   // En la misma fila
                         if (k != j ) {         // No es la propia variable
                              addConstraint(new SudokuConstraint(variables.get(index), variables.get(h)));
                         }
                         // Elementos de COLUMNAS DISTINTOS
                         h = k*9 + j;  			//recorro filas de la columna j
                         if (k != i) {          // No es la propia variable
                              addConstraint(new SudokuConstraint(variables.get(index), variables.get(h)));
                         }
                     }
                     // Elementos de CELDAS DISTINTOS

                     x = (i/3) * 3; 			// fila inicial de la celda  0, 3 o 6
                     y = (j / 3) * 3;       // columna inicial de la celda 0,3, 6
                     for (int k=x; k< x+3;k++)
                          for (int l=y; l< y+3;l++) {  // Recorro elementos celda
                               if (k!=i || l != j) {   // Que no sean la propia variable
                                    addConstraint(new SudokuConstraint(variables.get(index), variables.get(k*9 + l)));
                               }
                          }
               }
     }
}