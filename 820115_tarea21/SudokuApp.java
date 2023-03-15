package aima.gui.sudoku.csp;

import java.io.FileNotFoundException;

import aima.gui.sudoku.csp.*;
import java.io.FileReader;
import aima.core.search.csp.Assignment;
import aima.core.search.csp.BacktrackingStrategy;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CSPStateListener;
import aima.core.search.csp.ImprovedBacktrackingStrategy;
import aima.core.search.csp.Variable;
import aima.core.search.csp.Assignment;

import java.util.Arrays;
import java.util.stream.Stream;

public class SudokuApp extends CSP{

	public SudokuApp() {
		
	}
	public static void main(String[] args) {
		int numSudokusTratados=0,numSudokusSolucionados=0; 
		double tiempo=0;
		//leemos de los ficheros los sudokus
		Sudoku [] lista1 = Sudoku.listaSudokus2("easy50.txt");
		Sudoku [] lista2=	 Sudoku.listaSudokus2("top95.txt");
		Sudoku [] lista3 =  Sudoku.listaSudokus2("hardest.txt");
		Sudoku[] union= union(lista1,lista2,lista3);	
		
		for (Sudoku s : union) {
			
			if(!s.completo()) {
				numSudokusTratados++;
				
                AvailableCells pack = s.pack_celdasAsignadas();
                //creamos el problema del sudoku con los dominios y restricciones 
                SudokuProblem problem = new SudokuProblem(pack);
                
                ImprovedBacktrackingStrategy bts = new ImprovedBacktrackingStrategy(true,true,true, true);
                bts.addCSPStateListener(new CSPStateListener(){

                @Override
                public void stateChanged(Assignment assignment, CSP csp) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void stateChanged(CSP csp) {
                    // TODO Auto-generated method stub

                }


            });
            double start = System.currentTimeMillis();
            //resolvemos el sudoku 
			Assignment sol = bts.solve(problem);
			double end = System.currentTimeMillis();
			tiempo+=(end-start);
			
			if(sol!=null) {
				numSudokusSolucionados++;
			
				
			}
			
		}
			
		}
			//mostramos el resultado
		  System.out.println("Numero de sudokus tratados: "+numSudokusTratados);
		  System.out.println("Numero de sudokus solucionados: "+numSudokusSolucionados);
		  System.out.println("Time to solve: "+tiempo+" ms");
		
	}
	
	
	
	
	
	private static Sudoku[] union(Sudoku[] lista1, Sudoku[] lista2,Sudoku[] lista3) {
		Sudoku[] union= new Sudoku[lista1.length + lista2.length];
		 
		    // copy first array to object array
		    System.arraycopy(lista1, 0, union, 0, lista1.length);
		 
		    // copy the second array to the object array
		    System.arraycopy(lista2, 0, union, lista1.length, lista2.length);
		    
		    
	
		    Sudoku [] union2 = new Sudoku[lista3.length + union.length];
		    // copy first array to object array
		    System.arraycopy(lista3, 0, union2, 0, lista3.length);
		 
		    // copy the second array to the object array
		    System.arraycopy(union, 0, union2, lista3.length, union.length);
		    return union2;
	}
}
	
