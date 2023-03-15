package aima.gui.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.CanibalesBoard;
import aima.core.environment.eightpuzzle.CanibalesFunctionFactory;
import aima.core.environment.eightpuzzle.CanibalesGoalTest;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;

/**
 * @author Patricia Siwinska , NIA: 820115
 * 
 */

public class CanibalesApp {
	static CanibalesBoard board = new CanibalesBoard(
			new int[] { 3,3,0,0,0});;



	public static void main(String[] args) {
		// Búsqueda primero en anchura
		
				System.out.println("Misioneros y canibales BFS-G --> \n");
				CanibalesSearch(new BreadthFirstSearch(new GraphSearch()),board);
				System.out.format("%n");
				
				
				// Búsqueda primero en profundidad
				
				System.out.println("Misioneros y canibales DFS-G --> \n");
				CanibalesSearch(new DepthFirstSearch(new GraphSearch()),board);
				System.out.format("%n");
				
				System.out.println("Misioneros y canibales DFS-T -->\n");
				// Tiempo de resolución no razonable
				System.out.println("--- (1)");
				// CanibalesSearch(new DepthFirstSearch(new)),board);
				System.out.format("%n");  
				
				
				// Búsqueda limitada en profundidad
				System.out.println("Misioneros y canibales DLS(11) --> \n");
				CanibalesSearch(new DepthLimitedSearch(11),board);
				System.out.format("%n");
				
				// Búsqueda en profundidad iterativa
				System.out.println("Misioneros y canibales IDLS -->\n");
				CanibalesSearch(new IterativeDeepeningSearch(),board);
				System.out.format("%n");
				
				// Búsqueda de coste uniforme
				System.out.println("Misioneros y canibales UCS-G -->");
				CanibalesSearch(new UniformCostSearch(new GraphSearch()),board);
				/*
				System.out.format("%n");
				System.out.println("Misioneros y canibales UCS-T -->");
				CanibalesSearch(new UniformCostSearch(new TreeSearch()),board);
				*/
		
		
		
	}
	
	private static void  CanibalesSearch(Search search,CanibalesBoard p) {
		Problem problem = new Problem(p, CanibalesFunctionFactory
				.getActionsFunction(), CanibalesFunctionFactory
				.getResultFunction(), new CanibalesGoalTest());
		Integer depth,expandedNodes,queueSize,maxQueueSize;
		Long t;
		SearchAgent agent;
		try {
			t= System.currentTimeMillis();
			agent = new SearchAgent(problem, search);
			t=System.currentTimeMillis()-t;
			String pathcostM =agent.getInstrumentation().getProperty("pathCost");
			if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
			else depth = 0;
			if (agent.getInstrumentation().getProperty("nodesExpanded")==null) expandedNodes= 0;
			else expandedNodes =
			(int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));
			if (agent.getInstrumentation().getProperty("queueSize")==null) queueSize=0;
			else queueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize"));
			if (agent.getInstrumentation().getProperty("maxQueueSize")==null) maxQueueSize= 0;
			else maxQueueSize =
			(int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));
			
			

			System.out.println("pathCost: "+depth+"\n");
			System.out.printf("nodesExpanded: "+expandedNodes+"\n");
			System.out.printf("queueSize: "+queueSize+"\n");
			System.out.printf("maxQueueSize: "+maxQueueSize+"\n");
			System.out.printf("Tiempo: "+t+"mls\n");
			 System.out.println("\n");
			 System.out.println("SOLUCIÓN:\n");
			 System.out.println("GOAL STATE\n");
			 System.out.println("RIBERA-IZQ --RIO-- BOTE M M M C C C RIBERA-DCH\n");
			 System.out.println("CAMINO ENCONTRADO\n");
			executeActions(agent.getActions(), problem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
	}
	
	public static void executeActions(List<Action> actions, Problem problem) {
		Object initialState = problem.getInitialState();
		ResultFunction resultFunction = problem.getResultFunction();
		Object state = initialState;
		System.out.println("INITIAL STATE");
		System.out.println(state);
		for (Action action : actions) {
			
			System.out.format(action.toString());
			 state = resultFunction.result(state, action);
			 System.out.format("%65s"+ "%n",state);
			 
		}
	}

	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}
		
	}

	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}

}