package aima.core.environment.eightpuzzle;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * Patricia Siwinska , NIA: 820115
 */
public class CanibalesFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new CActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new CResultFunction();
		}
		return _resultFunction;
	}

	private static class CActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			CanibalesBoard board = (CanibalesBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.realizarMovimiento(CanibalesBoard.M1C)) {
				actions.add(CanibalesBoard.M1C);
			}
			if (board.realizarMovimiento(CanibalesBoard.M2C)) {
				actions.add(CanibalesBoard.M2C);
			}
			if (board.realizarMovimiento(CanibalesBoard.M2M)) {
				actions.add(CanibalesBoard.M2M);
			}
			if (board.realizarMovimiento(CanibalesBoard.M1M1C)) {
				actions.add(CanibalesBoard.M1M1C);
			}
			if (board.realizarMovimiento(CanibalesBoard.M1M)) {
				actions.add(CanibalesBoard.M1M);
			}
			
			
			
			return actions;
		}
	}

	private static class CResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			CanibalesBoard board = (CanibalesBoard) s;

	
			 if (CanibalesBoard.M1C.equals(a)
					&& board.realizarMovimiento(CanibalesBoard.M1C)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.M1C();
				return newBoard;
			} else if (CanibalesBoard.M2C.equals(a)
					&& board.realizarMovimiento(CanibalesBoard.M2C)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.M2C();
				return newBoard;
			}else if (CanibalesBoard.M2M.equals(a)
					&& board.realizarMovimiento(CanibalesBoard.M2M)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.M2M();
				return newBoard;
			}else if (CanibalesBoard.M1M1C.equals(a)
					&& board.realizarMovimiento(CanibalesBoard.M1M1C)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.M1M1C();
				return newBoard;
			}
			else if (CanibalesBoard.M1M.equals(a)
					&& board.realizarMovimiento(CanibalesBoard.M1M)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.M1M1C();
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}