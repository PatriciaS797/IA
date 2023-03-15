package aima.gui.sudoku.csp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
public class SudokuVariable extends Variable{
	private int coordenadaYcelda;
	private int coordenadaXcelda;
	private int valorCelda=0;
	
	public SudokuVariable(String name,int x,int y) {
		super(name);
		coordenadaXcelda=x;
		coordenadaYcelda=y;
		// TODO Auto-generated constructor stub
	}
	
	public int getX(){
		return coordenadaXcelda;
		
	}
	
	public int getY(){
		return coordenadaYcelda;
		
	}
	
	public int getValue(){
		return valorCelda;
		
	}
	
	public void setValue(int nuevoValor) {
		valorCelda=nuevoValor;
		
	}
	 
	
}