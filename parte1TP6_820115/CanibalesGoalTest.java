package aima.core.environment.eightpuzzle;

import aima.core.search.framework.GoalTest;

/**
 * Patricia Siwinska , NIA: 820115
 * 
 */
public class CanibalesGoalTest implements GoalTest {
	CanibalesBoard goal = new CanibalesBoard(new int[] { 0,0,1,3,3});

	public boolean isGoalState(Object state) {
		CanibalesBoard board = (CanibalesBoard) state;
		return board.equals(goal);
	}
}