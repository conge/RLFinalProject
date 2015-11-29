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
		Random randomNum = new Random(55);
		TrainingSets t = new TrainingSets();
		t.genTrainSets(randomNum);
		ArrayList<ArrayList<String[]>> sets = t.getTrainSets();

		
		double[] lambdas1	= {0.0, 0.1, 0.3, 0.5,  0.7,  0.9, 1.0};
		double[] lambdas2	= {.0,.1,.2,.3,.4,.5,.6,.7,.8,.9,1.0};
		double[] alphas		= {0.0, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6};
		
		double[] rmseExp1 = new double[lambdas1.length];
		double[][] rmseExp2 = new double[lambdas2.length][alphas.length];
		
		
		/*
		 * Experiment 1
		 */
		for (int i = 0; i < lambdas1.length; i++) {
			rmseExp1[i] = exp1(sets, lambdas1[i]);
		}		
		System.out.println("ln36: rmseExp1 = " + Arrays.toString(rmseExp1));
		/*
		 * Experiment 2
		 */
		for (int i = 0; i < lambdas2.length; i++) {
			
			for (int j = 0; j < alphas.length; j++) {
				rmseExp2[i][j] = exp2(sets, lambdas2[i], alphas[j]);
			}
		}
		System.out.println("ln42: rmseExp2 = " + Arrays.deepToString(rmseExp2));
		System.out.println("ln42: rmseExp2 = " + rmseExp2[0][0] +"; "+ rmseExp2[0][1] + "; " + rmseExp2[0][2]);
	}


	private static double exp2(ArrayList<ArrayList<String[]>> sets, double lambda, double alpha) {
		int nstates 	= 5; // number of non-absorbing states (B to F)
		double[] T = {1.0/6,1.0/3,1.0/2,2.0/3,5.0/6};		// transition possibility= new double[] {1:nstates}/(nstates+1);
		double[] w		= new double[nstates];  //rand(1,nstates);
		double[] dw		= new double[nstates];  //zeros(size(w));	% change in w.
		
		int nSets		= sets.size();	// number sets of random walks sequences
		double[] rmses	= new double[nSets];
		double meanRmse	= 0.0;
		
		for (int n = 0; n < nSets; n++) {
			Arrays.fill(w,0.5); // initialize weights
			
			for (int k = 0; k < sets.get(n).size();k++) {

				
				dw = learningFromSequence(sets.get(n).get(k), nstates, w, lambda);
				
				
				// Update weights w
				for (int i = 0; i < w.length;i++) {
					w[i] = w[i] + alpha*dw[i];
				}

			}
			
			 rmses[n] = RMSE(w,T);
			 
			
		}
		meanRmse = calArrayMean(rmses);

		return meanRmse;
		
	}

	/**
	 * The repeated presentation training paradigm
	 * 1. Presents a training set to the learning, updates the weight vector
	 *  only when the learner sees the whole set.
	 * 2.Each training set was presented repeatedly to each learning procedure 
	 *   - the procedure converge when no significant changes in weight vector. 
	 *   - For small alpha, the weight vector always converged in this way, and always to the same final value, independent, of its initial value. 
	 * @param sets
	 * @param lambda
	 * @return
	 */

	private static double exp1(ArrayList<ArrayList<String[]>> sets, double lambda) {
		int nstates 	= 5; // number of non-absorbing states (B to F)
		double[] T = {1.0/6,1.0/3,1.0/2,2.0/3,5.0/6};		// transition possibility= new double[] {1:nstates}/(nstates+1);
		double[] w		= new double[nstates];  //rand(1,nstates);
		double[] dw		= new double[nstates];  //zeros(size(w));	% change in w.
		double alpha	= 0.05;	// learning rate
		int nSets		= sets.size();	// number sets of random walks sequences
		double[] rmses	= new double[nSets];
		double meanRmse	= 0.0;
		double epsilon = 0.0005;	
		
		for (int n = 0; n < nSets; n++) {
			boolean converged = false;
			int count = 0;
			Arrays.fill(w,0.5);

			while (!converged) {
				
				double[] oldw = Arrays.copyOf(w, w.length);
				
				dw = learningFromSet(sets.get(n), nstates, w, lambda);	
				
				// Update weights w
				for (int i = 0; i < w.length;i++) {
					w[i] = w[i] + alpha*dw[i];
				}
				
				double deltaW = RMSE(oldw,w);
				count++;
					
				if (deltaW < epsilon) {
					converged = true;
					System.out.println("Debug: ln 133: set #"+n+".Converged in  " + count+ " repeats");
					System.out.println("");
					
					break;
				}
				
			}
			
			 rmses[n] = RMSE(w,T);

		}
		meanRmse = calArrayMean(rmses);

		return meanRmse;
	}


	/**
	 * @param set a list of training set contains 10 sequences of walking result
	 * @param nstates, int number of states of the random walking problem
	 * @param w double[] weights
	 * @param lambda double, parameter of TD(lambda)
	 * @return dw, double[] change in weights
	 */
	private static double[] learningFromSet(ArrayList<String[]> set,
			int nstates, double[] w, double lambda) {
		
		double[] dw = new double[nstates];
		
		for (int i=0; i < set.size(); i++) {
			String[] sequence =  set.get(i);
			double[] newdw = learningFromSequence(sequence, nstates, w, lambda);
		
			// accumulate dw for the sets
			for (int j = 0; j < dw.length; j ++) {
				dw[j]= dw[j] + newdw[j];
			}
					
		}
		// not sure if this is correct or not, but averaging helps with converge
		for (int i = 0; i < dw.length; i ++) {
			dw[i]=dw[i]/10.0;
		}
		
		return dw;
	}
	
	private static double[] learningFromSequence(String[] sequence, int nstates,
			double[] w, double lambda) {
		
		double[] dw = new double[nstates];
				
		double[] elast 		= new double[nstates];
		double[] xnext 		= new double[nstates];
		
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
	
			seqID++;
		}		    
		return dw;
	}	

	
	private static double calArrayMean(double[] m) {
		double sum = 0;
	    for (int i = 0; i < m.length; i++) {
	        sum += m[i];
	    }
	    return sum / m.length;
	}


	private static double RMSE(double[] predictions, double[] targets) {
		double rmse = 0.;
        double diff;
        for (int i = 0; i < targets.length; i++) {
                diff = targets[i] - predictions[i];
                rmse += diff * diff;
        }
        rmse = Math.sqrt(rmse / targets.length);
        return rmse;
		
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
