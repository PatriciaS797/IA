package aima.gui.demo.search;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.util.datastructure.XYLocation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import aima.core.search.local.HillClimbingSearch.SearchOutcome;
import aima.core.agent.Action;
import aima.core.environment.nqueens.AttackingPairsHeuristic;
import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFitnessFunction;
import aima.core.environment.nqueens.NQueensFunctionFactory;
import aima.core.environment.nqueens.NQueensGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.Individual;
import aima.core.search.local.Scheduler;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;

/**
 * @author Ravi Mohan
 * 
 */

public class NQueensLocal {

	private static final int _boardSize = 8;
	 private static final DecimalFormat df = new DecimalFormat("0.00");
	public static void main(String[] args) {
	
		
		nQueensHillClimbingSearch_Statistics(10000);
		nQueensRandomRestartHillClimbing();
		
		nQueensSimulatedAnnealing_Statistics(1000);
		nQueensHillSimulatedAnnealingRestart();
		nQueensGeneticAlgorithmSearch() ;
	}
	
	private static NQueensBoard generarTablero(){
		//generamos tablero
		Random r = new Random();
		NQueensBoard tablero = new NQueensBoard(8);
		
		// Se inserta una reina en cada fila en una columna aleatoria
		for (int j=0; j < _boardSize; j++) {
			int th = r.nextInt(8);
			XYLocation q = new XYLocation(j,th);
			tablero.addQueenAt(q);	
		}
		return tablero;
		
	}
	
	
	
	
	private static void nQueensHillClimbingSearch_Statistics(int numExperiments){
		// Array de estados iniciales
		float aciertos=0,fallos=0,medioFallos=0,medioExitos=0;
				NQueensBoard tableros[];
				tableros = new NQueensBoard[numExperiments];
			
				System.out.println("NQueens HillClimbing con " + numExperiments + " estados iniciales diferentes -->");
		for(int i=0; i < numExperiments ; i++) {
			 tableros[i] =generarTablero();
			
			try {
				Problem problem = new Problem(tableros[i],
						NQueensFunctionFactory.getCActionsFunction(), // cambiado 
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				HillClimbingSearch search = new HillClimbingSearch(
						new AttackingPairsHeuristic());
				
				SearchAgent agent = new SearchAgent(problem, search);

				if (search.getOutcome() == SearchOutcome.SOLUTION_FOUND) {
					medioExitos+=agent.getActions().size();
					aciertos++;
				}else {
					medioFallos+=agent.getActions().size();
					fallos++;}
				
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		double porcentajeF=(fallos/numExperiments)*100;
		double porcentajeA=(aciertos/numExperiments)*100;
		double costeF=(medioFallos/fallos);
		double costeA=(medioExitos/aciertos);
		System.out.println("Fallos:"+ df.format(porcentajeF) +"%");
		System.out.println("Coste medio fallos:"+ df.format(costeF) );
		System.out.println("Exitos:"+ df.format(porcentajeA)+"%");
	
		System.out.println("Coste medio exitos:"+ df.format(costeA));
		
	}
	
	
	
	
	
	
	private static void nQueensRandomRestartHillClimbing() {
		// Array de estados iniciales
				float fallos=0,medioFallos=0;
				NQueensBoard tablero;
				Integer numIntentos=1;
					boolean encontrado=false;
					
					
					while(!encontrado) {
						try {
							 tablero =generarTablero();
							Problem problem = new Problem(tablero,
									NQueensFunctionFactory.getCActionsFunction(), // cambiado 
									NQueensFunctionFactory.getResultFunction(),
									new NQueensGoalTest());
							HillClimbingSearch search = new HillClimbingSearch(
									new AttackingPairsHeuristic());
							
							SearchAgent agent = new SearchAgent(problem, search);
	
							if (search.getOutcome() == SearchOutcome.SOLUTION_FOUND) {
								encontrado=true;
								System.out.println();
								System.out.println("Search Outcome=" + search.getOutcome());
								System.out.println("Final State=\n" + search.getLastSearchState());
							}else {
								fallos++;
								medioFallos+=agent.getActions().size();
								numIntentos++;
							}
							
						
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					
				
				
				double porcentajeF=(fallos/numIntentos)*100;
				double costeF=(medioFallos/fallos);
				System.out.println("Numero de intentos:"+ numIntentos);
				
				System.out.println("Fallos:"+ df.format(porcentajeF) +"%");
				System.out.println("Coste medio fallos:"+ df.format(costeF) );

	}
			
	


	private static void nQueensSimulatedAnnealing_Statistics (int numExperiments) {
		System.out.println("\nNQueensDemo Simulated Annealing con " + numExperiments + " estados iniciales diferentes -->");
		
		float aciertos=0,fallos=0,medioFallos=0,medioExitos=0;
		NQueensBoard tableros[];
		tableros = new NQueensBoard[numExperiments];
	 for(int i=0; i<numExperiments ; i++) {
			try {
				tableros[i]=generarTablero();
				Scheduler sched=new Scheduler (10,0.1,500);
				
				Problem problem = new Problem(tableros[i],
				NQueensFunctionFactory.getCActionsFunction(),
				NQueensFunctionFactory.getResultFunction(),
				new NQueensGoalTest());
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
						new AttackingPairsHeuristic(),sched);
				SearchAgent agent = new SearchAgent(problem, search);
				
				
				if (search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					medioExitos+=agent.getActions().size();
					aciertos++;
				}else {
					medioFallos+=agent.getActions().size();
					fallos++;}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	 	}
	
	  System.out.println("Parámetros Scheduler: Scheduler ("+10+"," + 0.1 +"," + 500+")");
	  double porcentajeF=(fallos/numExperiments)*100;
		double costeF=(medioFallos/fallos);
		double porcentajeA=(aciertos/numExperiments)*100;
		double costeA=(medioExitos/aciertos);
		System.out.println("Fallos:"+ df.format(porcentajeF) +"%");
		System.out.println("Coste medio fallos:"+ df.format(costeF) );
		System.out.println("Exitos:"+ df.format(porcentajeA)+"%");
	
		System.out.println("Coste medio exitos:"+ df.format(costeA));
		
	}
	
	
	private static void nQueensHillSimulatedAnnealingRestart() {
	
		float fallos=0,medioFallos=0;
		Integer numIntentos=1;
		NQueensBoard tablero;
		boolean encontrado=false;
	 while(!encontrado) {
			try {
				tablero=generarTablero();
				Scheduler sched=new Scheduler (10,0.1,500);
				
				Problem problem = new Problem(tablero,
				NQueensFunctionFactory.getCActionsFunction(),
				NQueensFunctionFactory.getResultFunction(),
				new NQueensGoalTest());
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
						new AttackingPairsHeuristic(),sched);
				SearchAgent agent = new SearchAgent(problem, search);
				
				
				if (search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					encontrado=true;
					System.out.println();
					System.out.println("Search Outcome=" + search.getOutcome());
					System.out.println("Final State=\n" + search.getLastSearchState());
				}else {
					fallos++;
					medioFallos+=agent.getActions().size();
					numIntentos++;
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	 	}
	 	double porcentajeF=(fallos/numIntentos)*100;
		double costeF=(medioFallos/fallos);
		System.out.println("Parámetros Scheduler: Scheduler ("+10+"," + 0.1 +"," + 500+")");
		System.out.println("Numero de intentos:"+ numIntentos);
		System.out.println("Fallos:"+ df.format(porcentajeF) +"%");
		System.out.println("Coste medio fallos:"+ df.format(costeF) );

		
	
		
	}

	
	
	
	
	
	
	

	public static void nQueensGeneticAlgorithmSearch() {
		System.out.println("\nNQueensDemo GeneticAlgorithm  -->");
		try {
			NQueensFitnessFunction fitnessFunction = new NQueensFitnessFunction();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 25; i++) {//20,40
				population.add(fitnessFunction
						.generateRandomIndividual(_boardSize));
			}
			
			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(
					_boardSize,
					fitnessFunction.getFiniteAlphabetForBoardOfSize(_boardSize),
					0.28);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(
					population, fitnessFunction, fitnessFunction, 1000L);
			System.out.println("Parámetros iniciales :    Población:  " + ga.getPopulationSize()
			+ "  Probabilidad de mutación :    " + fitnessFunction.getValue(bestIndividual));
			System.out.println("Max Time (1 second) Best Individual=\n"
					+ fitnessFunction.getBoardForIndividual(bestIndividual));
			System.out.println("Board Size      = " + _boardSize);
			
			System.out.println("Fitness         = "
					+ fitnessFunction.getValue(bestIndividual));
			System.out.println("Is Goal         = "
					+ fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = "
					+ ga.getTimeInMilliseconds() + "ms.");

			// Run till goal is achieved
			bestIndividual = ga.geneticAlgorithm(population, fitnessFunction,
					fitnessFunction, 0L);

			System.out.println("");
			System.out.println("Goal Test Best Individual=\n"
					+ fitnessFunction.getBoardForIndividual(bestIndividual));
			System.out.println("Board Size      = " + _boardSize);
			
			System.out.println("Fitness         = "
					+ fitnessFunction.getValue(bestIndividual));
			System.out.println("Is Goal         = "
					+ fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = "
					+ ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}