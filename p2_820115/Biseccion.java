package aima.core.util.math;
import java.lang.Math;

public class Biseccion {
	
	
	static final float EPSILON = (float)1E-10;
	
	
	static double func(double b,int d,int n)
    {
		return (b*(Math.pow(b,d) -1)/(b-1))-n ;
    }
 
    // Prints root of func(x) with error of EPSILON
    public static double bisection(double a, double b,int d,int n)
    {
 
        double c = a;
        while ((b-a) >= EPSILON)
        {
            // Find middle point
            c = (a+b)/2;
 
            // Check if middle point is root
            if (func(c,d,n) == 0.0)
                break;
 
            // Decide the side to repeat the steps
            else if (func(a,d,n)*func(c,d,n) < 0)
                b = c;
            else
                a = c;
        }
                return c;
       
    }

  
	

}


