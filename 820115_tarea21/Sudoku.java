package aima.gui.sudoku.csp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Variable;
import java.io.FileReader;
import java.util.Scanner;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Variable;

public class Sudoku {
	final static int FILAS=9;
	final static int COLUMNAS=9;
	
	private int [][] numeros= new int [FILAS][COLUMNAS];
	private boolean correcto=false;
	private boolean completo=false;
	private int celdasInicialesAsignadas=0;
	
	public Sudoku(){
	}
	public Sudoku (Assignment valores){
		for (Variable var : 	valores.getVariables()) {
			SudokuVariable v = (SudokuVariable) var;
			this.numeros[v.getX()][v.getY()]= (int)valores.getAssignment(v);//v.getValue();
			//System.out.print(v.getX());System.out.print(" "); 
			//System.out.print(  v.getY()); System.out.print(" =  ");
			//System.out.println( valores.getAssignment(v));
		}
		this.asigna_completo();
	    if (this.completo()) this.asigna_correccion();	
	}
	public boolean correcto() {
		return correcto;
	}
	
	public boolean completo() {
		return completo;
	}
	
	public int celdasInicialesAsignadas() {
		return celdasInicialesAsignadas;
	}
	
	public  boolean fila_correcta(int fila){
		boolean[] conjunto =new boolean[9];
		for (int i=0; i<9; i++) conjunto[this.numeros[fila][i]-1]=true;
		boolean correcto=true;
		int i=0;
		while (correcto && i<9) {
			correcto=conjunto[i];
			i++;
		}
		return correcto;
	}
	
	public  boolean columna_correcta(int columna){
		boolean[] conjunto =new boolean[9];
		for (int i=0; i<9; i++) conjunto[this.numeros[i][columna]-1]=true;
		boolean correcto=true;
		int i=0;
		while (correcto && i<9) {
			correcto=conjunto[i];
			i++;
		}
		return correcto;
	}
	
	public  boolean cuadro_correcto(int cuadro){
		boolean[] conjunto =new boolean[9];
		int fila = (cuadro/3)*3;
		int columna = (cuadro % 3)*3;
		for (int i=fila; i<fila+3; i++)
			for (int j= columna; j<columna+3; j++)
				conjunto[this.numeros[i][j]-1]=true;
		boolean correcto=true;
		int i=0;
		while (correcto && i<9) {
			correcto=conjunto[i];
			i++;
		}
		return correcto;
	}
	
	public  void asigna_completo(){
		int i=0;
		int j=0;
		boolean completo=true;
		while (i<9  && completo){
			j=0;
			while (j<9 && completo){
				completo = this.numeros[i][j]!=0 ;
				j++;
			}
			i++;
		}
		this.completo=completo;
	}

	public  void asigna_correccion(){
	 boolean correcto=true;
	 int fila=0;
	 while (correcto && fila <9){
		 correcto=this.fila_correcta(fila);
		// if (correcto) System.out.println("Fila "+ fila + " correcta");
		// else System.out.println("Fila "+ fila + " incorrecta");
		 fila++;
	 }
	 int columna=0;
	 while (correcto && columna <9){
		 correcto=this.columna_correcta(columna);
		 //if (correcto) System.out.println("Columna "+ columna + " correcta");
		// else System.out.println("Columna "+ columna + " incorrecta");
		 columna++;
	 }
	 
	 int cuadrado=0;
	 while (correcto && cuadrado <9){
		 correcto=this.cuadro_correcto(cuadrado);
		 //if (correcto) System.out.println("Cuadrado "+ cuadrado + " correcta");
		// else System.out.println("Cuadrado "+ cuadrado + " incorrecta");
		 cuadrado++;
	 }
	 this.correcto=correcto;
	}
	public static Sudoku leerSudoku(Scanner entrada){
		Sudoku s= new Sudoku();
		boolean completo=true;
		for (int fila=0; fila<9; fila++)
			for (int columna=0; columna<9; columna++){
				int numero=entrada.nextInt();
				if (numero==0) completo = false;
				else s.celdasInicialesAsignadas++;
				s.numeros[fila][columna]=numero;
			}
		s.completo=completo;
		if (completo) s.asigna_correccion();
		return s;
	}
	
	public static Sudoku leerSudoku2(Scanner entrada){
		Sudoku s= new Sudoku();
		boolean completo=true;
		int i=0;
		int numero;
		String linea = entrada.nextLine();
		for (int fila=0; fila<9; fila++)
			for (int columna=0; columna<9; columna++){
				char letra= linea.charAt(i++);
				if (letra=='.'){ 
					completo = false;
					numero=0;
				}
				else {
					s.celdasInicialesAsignadas++;
					numero=(int)letra-(int)'0';
				}
				s.numeros[fila][columna]=numero;
			}
		s.completo=completo;
		if (completo) s.asigna_correccion();
		return s;
	}
	
	public static Sudoku[] listaSudokus(String nombreFichero){
		Scanner entrada;
		Sudoku[] lista=null;
		try {
			entrada = new Scanner(new FileReader(nombreFichero));
			int numSudokus=0;
			if (entrada.hasNext()) numSudokus=Integer.parseInt(entrada.nextLine());
			lista = new Sudoku[numSudokus];
			int i=0;
			while(entrada.hasNext()){
				lista[i] =leerSudoku(entrada);
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public static Sudoku[] listaSudokus2(String nombreFichero){
		Scanner entrada;
		Sudoku[] lista=null;
		try {
			entrada = new Scanner(new FileReader(nombreFichero));
			int numSudokus=0;
			while(entrada.hasNext()){
				entrada.nextLine();
				numSudokus++;
			}
			entrada.close();
			lista = new Sudoku[numSudokus];
			int i=0;
			entrada = new Scanner(new FileReader(nombreFichero));
			while(entrada.hasNext()){
				lista[i] =leerSudoku2(entrada);
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public  void imprimeSudoku(){
		for (int i=0; i<9; i++){
			for (int j=0;j<9; j++){
				if (this.numeros[i][j]==0) System.out.print(".");
				else System.out.print(this.numeros[i][j]);
			}
			System.out.println();
		}
	}
	
	public AvailableCells pack_celdasAsignadas(){
		AvailableCells pack = new AvailableCells(this.celdasInicialesAsignadas());
		for (int i=0; i<9; i++)
			for (int j=0;j<9; j++)
				if (this.numeros[i][j]!=0) pack.insert(i, j, numeros[i][j]);
		return pack;
	}
}