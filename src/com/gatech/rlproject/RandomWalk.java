package com.gatech.rlproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
/**
 * Repeat the Random-walk experiments in Sutton 1998 paper
 * @author Qingyang Li
 * 
 */

public class RandomWalk {

	
	public static void main(String[] args) {
		// change seed for different sets of sequences
		Random randomNum = new Random(1);
		TrainingSets t = new TrainingSets();
		t.genTrainSets(randomNum);
		
		ArrayList<ArrayList<String[]>> sets = t.getTrainSets();
		double[] lambdas = {0.0, 0.1, 0.3, 0.5, 0.7, 1.0};
		double[] rmsE = new double[lambdas.length];
		for (int i = 0; i < lambdas.length; i++) {
			rmsE[i] = exp1(sets, lambdas[i]);
		}
		System.out.println("ln27: RMSE = " + Arrays.toString(rmsE));
		exp2(sets);

	}


	private static void exp2(ArrayList<ArrayList<String[]>> sets) {
		// TODO Auto-generated method stub
		
	}

	private static double exp1(ArrayList<ArrayList<String[]>> sets, double lambda) {
		int nstates 	= 5; // number of non-absorbing states (B to F)
		double[] T = {1.0/6,1.0/3,1.0/2,2.0/3,5.0/6};		// transition possibility= new double[] {1:nstates}/(nstates+1);
		double[] w		= new double[nstates];  //rand(1,nstates);
		double[] dw		= new double[nstates];  //zeros(size(w));	% change in w.
		double alpha	= 0.3;	// learning rate
		int nSets		= 100;	// number sets of random walks sequences
		int intervalw	= 10;	// number of walks between weight update.
		double epsilon = 0.0000001;
		
		;
		boolean converged = false;
		int count = 0;
		
		while (!converged) {
			for (int n = 0; n < nSets; n++) {
				//System.out.println("ln54: w = " + Arrays.toString(w) );
				double[] oldw = Arrays.copyOf(w, nstates);
				
				dw = learningFromSet(sets.get(n), nstates, w, lambda);
				
				
				// Update weights w
				for (int i = 0; i < w.length;i++) {
					w[i] = w[i] + alpha*dw[i];
				}
				count++;
				double deltaW = RMSE(oldw,w);
				System.out.println("ln64: deltaW = " + deltaW );
				
				if (deltaW< epsilon) {
					converged = true;
					break;
				}
				
				
			}
		
			/*
			if (count >= 100) {
				converged = true;
			}
			*/
		}
		
		double rmse = RMSE(w,T);
		System.out.println("ln81: w = " + Arrays.toString(w) );
		System.out.println("ln82: T = " + Arrays.toString(T) );
		
		
		System.out.println("lambda = "+lambda+"; rmse = " + rmse + "; w updated "+ count + " times.");
		return rmse;
	}

	private static double RMSE(double[] predictions, double[] targets) {
		double rmse = 0;
        double diff;
        for (int i = 0; i < targets.length; i++) {
                diff = targets[i] - predictions[i];
                rmse += diff * diff;
        }
        rmse = Math.sqrt(rmse / targets.length);
        return rmse;
		
	}


	/**
	 * @param sets
	 * @param nstates
	 * @param P
	 * @param w
	 * @param dw
	 * @param elast
	 * @param lambda
	 * @return
	 */
	private static double[] learningFromSet(ArrayList<String[]> set,
			int nstates, double[] w,  
			double lambda) {
		double[] dw = new double[nstates];
		
		for (int i=0; i < set.size(); i++) {
			String[] sequence =  set.get(i);
			dw = new double[nstates];
					
			double[] elast 		= new double[nstates];
			double[] xnext 		= new double[nstates];;
			
			int seqID = 0;
			while (seqID < sequence.length-1) { //while walking
				double Pnext;
				int stateid	= idConvert(sequence[seqID]);
				
				double[] xt 		= new double[nstates];
				xt[stateid-1] = 1;
				double Pt 		= weightedSum(w,xt);
				
				int next_stateid = idConvert(sequence[seqID + 1]);
				
				//  Find Pnext
				if (next_stateid==0 ) {
					Pnext = 0; 
					
				} else if (next_stateid==nstates+1) {
					Pnext = 1; 
					 
				} else	{
					xnext = new double[nstates];
					
					xnext[next_stateid-1] = 1;
					
					Pnext = weightedSum(w, xnext);
				}
				
				// Find change in w.
				double dP = Pnext-Pt;				// Change in predicted final reward made at time t+1. 
				double[] et = new double[xt.length]; // error at time t
				for (int t = 0; t < xt.length; t++) {
					et[t] = lambda * elast[t] + xt[t];
					dw[t] = dw[t] + dP*et[t];
				}
				//debug
				boolean db = false;
				if (db) {
					System.out.println("[stateid next_stateid Pnext Pt dP ]");
					System.out.println("[ " + stateid + " " + next_stateid+ " " +  Pnext+ " " +  Pt+ " " +  dP + " ]");
					
				}
				// Update variables.
				
				elast 	= et;
				/*
				//Pt		= Pnext;
				// xt		= xnext;
				// stateid = next_stateid;
				 */
				seqID++;
			}		    
			
			
		}
		return dw;
	}
			

	private static int idConvert(String string) {
		if (string.equals("d"))
			return 3;
		if (string.equals("e"))
			return 4;
		if (string.equals("f"))
			return 5;
		if (string.equals("c"))
			return 2;
		if (string.equals("b"))
			return 1;
		if (string.equals("a"))
			return 0;
		if (string.equals("g"))
			return 6;
		
		throw  new IllegalArgumentException("string must be in {a, b, c, d, e, f,g}");
		
	}


	

	private static double weightedSum(double[] a, double[] b) {
		double value	= 0.0;
	    double sum		= 0.0 ;

	    for (int i = 0 ; i < a.length ; i++)
	    {
	        value = a[i] * b [i];
	        sum = sum +value ;

	    }

	    return sum;
		
	}
				

			
		
}
