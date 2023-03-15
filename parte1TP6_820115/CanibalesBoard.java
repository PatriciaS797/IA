package aima.core.environment.eightpuzzle;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;

/**
 * Patricia Siwinska , NIA: 820115
 */
public class CanibalesBoard {

	public static Action M1C = new DynamicAction("M1C");

	public static Action M2C = new DynamicAction("M2C");
	
	public static Action M1M1C = new DynamicAction("M1M1C");

	public static Action M2M = new DynamicAction("M2M");

	public static Action M1M = new DynamicAction("M1M");
	

	private int[] state;

	//
	// PUBLIC METHODS
	//

	public CanibalesBoard() {
		// state[0] canibales a la izquierda
		//state [1] misioneros a la izquierda
		//state[2] posicion bote (0 izquierda ,1 dcha)
		//state[3] canibales a la derecha
		//state [4] misioneros a la derecha
		state = new int[] { 3, 3, 0, 0, 0 };
	}

	public CanibalesBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public CanibalesBoard(CanibalesBoard copyBoard) {
		this(copyBoard.getState());
	}

	public int[] getState() {
		return state;
	}
	public int getBoard() {
		return state[2];
	}
	

	public void M1C() {
		boolean sufMinDcha, sufMinIzq;
		//el bote está a la derecha
		if (state[2]==1) {
			// si muevo 1 caníbal a la izquierda y no supera a los misioneros
			 sufMinDcha=true;
			 sufMinIzq=(state[0] +1 <= state [1]);
			if ( (state[1]==0 || sufMinIzq) && sufMinDcha && state[3]>=1 ) {
				state[0] +=1;
				state[2]=0;
				state[3]-=1;
			}
			
		}else { // el bote está a la izquierda
			sufMinDcha=(state[3] +1 <= state [4]);
			 sufMinIzq=true;
			if((sufMinDcha || state[4]==0) && sufMinIzq && state[0]>=1) {
				state[3] +=1;
				state[2]=1;
				state[0]-=1;
			}
		}

	}
	
	public void M2C() {
		boolean sufMinDcha, sufMinIzq;
		if (state[2]==1) {
			// si muevo 2 caníbales a la izquierda y no supera a los misioneros
			 sufMinDcha=true;
			 sufMinIzq=(state[0] +2 <= state [1]);
			
			if ((sufMinIzq|| state[1]==0) && sufMinDcha && state[3]>=2) {
				state[3]-=2;
				state[0]+=2;
				state[2]=0;
				
			}
			
		}else { // el bote está a la izquierda
			sufMinDcha=(state[4] >= state[3]+2); 
			sufMinIzq=true;
			if((sufMinDcha || state[4]==0) && sufMinIzq && state[0]>=2) {
				state[0]-=2;
				state[3]+=2;
				state[2]=1;
				
			}
		}

	}
	public void M1M1C() {
		if (state[2]==1) {
			// si muevo 1 caníbal y un misionero a la izquierda y no supera a los misioneros
			if ((state[0]  <= state [1] ) && (state[4] >= state[3]) && state[4]>=1 && state[3] >=1 ){
				state[0] +=1;
				state[1] +=1;
				state[2]=0;
				state[3]-=1;
				state[4]-=1;
			}
			
		}else { // el bote está a la izquierda
			if((state[3]  <= state [4]) && (state[1] >= state[0])&& state[0]>=1 && state[1] >=1 ) {
				state[3] +=1;
				state[4]+=1;
				state[2]=1;
				state[0]-=1;
				state[1]-=1;
			}
		}

	}
	
	public void M2M() {
		boolean sufMinDcha,sufMinIzq;
		if (state[2]==1) {// el bote está a la dcha
			// si muevo 2 misioneros mirar que en ese lado y en el actual no haya más caníbales 
			sufMinDcha=(state[4] -2 >= state[3]);
			sufMinIzq=(state[1] +2 >= state [0]);
			if ( sufMinIzq && (sufMinDcha || state[4]-2==0) && state[4]>=2 ) {
				state[4] -=2;
				state[2]=0;
				state[1]+=2;
			}
			
		}else { // el bote está a la izquierda
			sufMinDcha=(state[4] + 2 >= state[3]);
			sufMinIzq=(state[1] -2 >= state [0]);
			if( sufMinDcha && ( sufMinIzq || state[1]-2==0)&& state[1]>=2) {
				state[1] -=2; 
				state[2]=1;
				state[4]+=2;
			}
		}

	}
	
	public void M1M() {
		boolean sufMinDcha;
		boolean sufMinIzq;
		if (state[2]==1) { // el bote está a la derecha
			 sufMinDcha=(state[4] - 1 >= state[3]);
			 sufMinIzq=(state[1] +1 >= state [0]);
			// si muevo 1 misionero mirar que en ese lado y en el actual no haya más caníbales 
			if ( state[4]>=1 && sufMinIzq && (sufMinDcha || state[4]-1==0) ) {
				state[4] -=1;
				state[2]=0;
				state[1]+=1;
			}
			
		}else { // el bote está a la izquierda
			sufMinDcha=(state[4] + 1 >= state[3]);
			sufMinIzq=(state[1] - 1 >= state [0]);
			if(sufMinDcha&& state[1]>=1 && (sufMinIzq || state[1]-1==0)) {
				state[1] -=1;
				state[2]=1;
				state[4]+=1;
			}
		}

	}




	public boolean realizarMovimiento(Action where) {
		boolean retVal = true;
		boolean sufMinDcha,sufMinIzq;
		
		if (where.equals(M1C)) {
			
			if(state[2]==1) {sufMinDcha=true;	sufMinIzq=(state[0] +1 <= state [1]);}
			
			else {sufMinDcha=(state[3] +1 <= state [4]);	sufMinIzq=true;}
			
			
			retVal = (((state[1]==0 || sufMinIzq) && sufMinDcha && state[3]>=1   && state[2]==1)  ||
								((sufMinDcha || state[4]==0)  && sufMinIzq && state[0]>=1 && state[2]==0));
			
		}
		
		else if (where.equals(M2C)) {
			
			
			if(state[2]==1) {	sufMinDcha=true;	sufMinIzq=(state[0] +2 <= state [1]);}
			
			else {sufMinDcha=(state[4] >= state[3]+2); 
			sufMinIzq=true;}
			 
			retVal = ((state[2]==1 && (sufMinIzq|| state[1]==0) && sufMinDcha && state[3]>=2  )  ||  
						(state[2]==0 && (sufMinDcha || state[4]==0) && sufMinIzq && state[0]>=2 ));
		}
		
		
		else if (where.equals(M2M)) {
			
			if(state[2]==1) {	sufMinDcha=(state[4] -2 >= state[3]);
			sufMinIzq=(state[1] +2 >= state [0]);}
			
			else {	sufMinDcha=(state[4] + 2 >= state[3]);
			sufMinIzq=(state[1] -2 >= state [0]);}
			
			retVal = ((state[2]==1 && sufMinIzq && (sufMinDcha || state[4]-2==0) && state[4]>=2)  || 
					(state[2]==0 && sufMinDcha && ( sufMinIzq || state[1]-2==0)&& state[1]>=2	));
		}
		
		
		else if (where.equals(M1M1C)) {
			retVal = ((state[2]==1 && (state[0]  <= state [1] ) && (state[4] >= state[3]) && state[4]>=1 && state[3] >=1)  
						||   (state[2]==0 && (state[3]  <= state [4]) && (state[1] >= state[0])&& state[0]>=1 && state[1] >=1 ));
		}
		
		
		else if (where.equals(M1M)) {
			
			if(state[2]==1) {	 sufMinDcha=(state[4] - 1 >= state[3]);	sufMinIzq=(state[1] +1 >= state [0]);}
			
			else {	sufMinDcha=(state[4] + 1 >= state[3]);	sufMinIzq=(state[1] - 1 >= state [0]);}
			
			
			retVal = ((state[2]==1 && state[4]>=1 && sufMinIzq && (sufMinDcha || state[4]-1==0))  
						||   (state[2]==0 && sufMinDcha && state[1]>=1 && (sufMinIzq || state[1]-1==0) ));
			
		}
		
		
		return retVal;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CanibalesBoard other = (CanibalesBoard) obj;
		return Arrays.equals(state, other.state);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(state);
		return result;
	}

	@Override
	public String toString() {
		
	
		String retVal = "RIBERA-IZQ ";
		//escribimos los caníbales
		if (state[0]==3) {
			retVal+= "C C C ";
		}
		else if (state[0]==2) {
			retVal+= "C C ";
		}
		else if (state[0]==1) {
			retVal+= "C ";
		}
		else {
			retVal = retVal + "      ";
		}
		
		// escribimos los misioneros
		if (state[1] == 3) {
			retVal = retVal + "M M M ";
		}
		else if (state[1] == 2) {
			retVal = retVal + "  M M ";
		}
		else if (state[1] == 1) {
			retVal = retVal + "    M ";
		}
		else {
			retVal = retVal + "      ";
		}
		//imprimos el bote
		if (state[2] == 0) {
			retVal = retVal + "BOTE ";
		}
		else {
			retVal = retVal + "     ";
		}
		
		retVal = retVal + "--RIO-- ";
		
		if (state[2] == 1) {
			retVal = retVal + "BOTE ";
		}
		else {
			retVal = retVal + "     ";
		}
		
		if (state[3] == 3) {
			retVal = retVal + "C C C ";
		}
		else if (state[3] == 2) {
			retVal = retVal + "  C C "; 
		}
		else if (state[3] == 1) {
			retVal = retVal + "    C ";
		}
		else {
			retVal = retVal + "      ";
		}
		
		if (state[4] == 3) {
			retVal = retVal + "M M M ";
		}
		else if (state[4] == 2) {
			retVal = retVal + "  M M ";
		}
		else if (state[4] == 1) {
			retVal = retVal + "    M ";
		}
		else {
			retVal = retVal + "      ";
		}
		
		retVal = retVal + "RIBERA-DCH";
		
		return retVal;
	}
		
}	
	
		
	

