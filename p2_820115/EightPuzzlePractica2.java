package aima.gui.demo.search;
import aima.core.search.uninformed.*;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.util.math.*;
import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest2;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction2;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction2;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.framework.*;
/**
 * 
 * @author Ravi Mohan
 * 
 */

public class EightPuzzlePractica2 {
	
	static int BFSNodos = 0, IDSNodos = 0, AManhattanNodos = 0, AMisplacedNodos = 0;
	static double BFS_b = 0, IDS_b = 0, AManhattan_b = 0, AMisplaced_b = 0;
	

	public static void main(String[] args) {
	
		
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.println("||    ||      Nodos Generados                  ||                  b*                   ||");
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.println("||   d||    BFS  |    IDS  |  A*h(1)   |  A*h(2)   ||    BFS  |    IDS  |  A*h(1)   |  A*h(2)   ||");
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.println("------------------------------------------------------------------------------------------");
		experimentos();
		System.out.println("------------------------------------------------------------------------------------------");
		
		
	}
	
	private static void  experimentos()  {
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
			for(int i = 2 ; i <=24 ; i++) {
				for(int j = 0 ; j < 100 ; j++) {
					EightPuzzleBoard ini =GenerateInitialEightPuzzleBoard.randomIni();

					EightPuzzleBoard goal = GenerateInitialEightPuzzleBoard.random(i,ini);
					EightPuzzleGoalTest2.setGoalState(goal);
					
					
					Problem problem = new Problem(ini, EightPuzzleFunctionFactory
							.getActionsFunction(), EightPuzzleFunctionFactory
							.getResultFunction(), new EightPuzzleGoalTest2());
					boolean encontrado = false;
					while(!encontrado){
						try{
							SearchAgent agent = new SearchAgent(problem, new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction2(goal)));
							if(i==((int)Float.parseFloat(agent.getInstrumentation().getProperty("pathCost")))){ // Se comprueba que es la profunidad deseada
								
								encontrado = true;
							}
							else{ // Es que ha fallado y se busca otro puzzleFin
								
								goal = GenerateInitialEightPuzzleBoard.random(i,ini);
								EightPuzzleGoalTest2.setGoalState(goal);
								problem = new Problem(ini, EightPuzzleFunctionFactory.getActionsFunction(),
										EightPuzzleFunctionFactory.getResultFunction(), new EightPuzzleGoalTest2());
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
					
					puzzleNodos(problem, new BreadthFirstSearch(new GraphSearch()), 0);
					if (i<11){
						puzzleNodos(problem, new IterativeDeepeningSearch(), 1);
					}
					puzzleNodos(problem, new AStarSearch(new GraphSearch(), new MisplacedTilleHeuristicFunction2(goal)), 2);
					puzzleNodos(problem, new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction2(goal)), 3);
					try {
					SearchAgent agent = new SearchAgent(problem, new AStarSearch(new GraphSearch(),new ManhattanHeuristicFunction2(goal)));}
					catch(Exception e){e.printStackTrace();}
					
					
				}
				
				BFS_b =Biseccion.bisection(1.00000001, 4, i,BFSNodos/100);
				// IDS
				if ( i < 11 ){
					
					IDS_b = Biseccion.bisection(1.00000001, 4, i,IDSNodos/100);
				}
				// AManhattan
				
				AManhattan_b = Biseccion.bisection(1.00000001, 4, i,AManhattanNodos/100);
				// AMisplaced
				
				AMisplaced_b = Biseccion.bisection(1.00000001, 4, i,AMisplacedNodos/100);
				// MOSTRAR LA TABLA
				
				System.out.format("||" + "% 4d" + "||", i);
				System.out.format("% 7d" + "  |", BFSNodos/100);
				if (i < 11 ) {System.out.format("% 7d" + "  |", IDSNodos/100);}
				else {System.out.format("    ---  |");}
				System.out.format("% 7d" + "  |", AMisplacedNodos/100);
				System.out.format("% 7d" + "  ||", AManhattanNodos/100);
				System.out.format("% 7.2f" + "  |", BFS_b);
				if(i < 11) {System.out.format("% 7.2f" + "  |", IDS_b);}
				else{System.out.format("    ---  |");}
				System.out.format("% 7.2f" + "  |", AManhattan_b);
				System.out.format("% 7.2f" + "  |\n", AMisplaced_b);
				
			}
			
		}
	
						
				
				
				
		private static void puzzleNodos(Problem problem, Search busqueda, int busq) {
			try {
				SearchAgent agent = new SearchAgent(problem, busqueda);
				int nodos = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));
				if (busq == 0){
					BFSNodos = BFSNodos + nodos;
				}
				else if (busq == 1){
					IDSNodos += nodos;
				}
				else if (busq == 2){
					AMisplacedNodos += nodos;
				}
				else if (busq == 3){
					AManhattanNodos += nodos;
				}
			}		
			catch (Exception e) {
				e.printStackTrace();
			}
				
		
		
	}
	
		
		
		
	

}