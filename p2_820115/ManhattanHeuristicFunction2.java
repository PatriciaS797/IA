package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class ManhattanHeuristicFunction2 implements HeuristicFunction {

	public EightPuzzleBoard objetivo;
	
	public ManhattanHeuristicFunction2(EightPuzzleBoard fin){
		objetivo = fin;
	}
	
	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		int retVal = 0;
		for (int i = 1; i < 9; i++) {
			XYLocation loc = board.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(i, loc);
		}
		return retVal;

	}

	public int evaluateManhattanDistanceOf(int i, XYLocation loc) {
		int retVal = -1;
		int xpos = loc.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();
		
		retVal = Math.abs(xpos - objetivo.getLocationOf(i).getXCoOrdinate()) + Math.abs(ypos - objetivo.getLocationOf(i).getYCoOrdinate());
		
		return retVal;
	}
}