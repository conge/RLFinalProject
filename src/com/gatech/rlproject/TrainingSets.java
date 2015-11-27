package com.gatech.rlproject;

import java.util.ArrayList;
import java.util.Random;

public class TrainingSets {

	ArrayList<ArrayList<String[]>> trainingSets = new ArrayList<ArrayList<String[]>>();
	final String[] s = {"a","b","c","d","e","f","g"};
    
	public TrainingSets() {
		
	}
	
	/**
	 * return 100 trainingSets, each set has 10 sequences
	 * @return the trainSets
	 * 
	 */

	public ArrayList<ArrayList<String[]>> getTrainSets() {
	
		return this.trainingSets;
	}

	/**
	 * Generates the 100 training sets of randomly chosen
	 * @param trainSets the trainSets to set
	 */
	public void genTrainSets(Random randomNum ) {
		// Change seed if want another group of training sets
		
		for (int i = 0; i < 100; i++) {
			this.trainingSets.add(genSet(randomNum));
		}
		
	}

	/**
	 * generates a training set with 10 sequences randomly
	 * @param randomNum 
	 * @return trainingSet, on trainingSet with 10 sequences
	 */
	private ArrayList<String[]> genSet(Random randomNum) {
		int count = 0;
		ArrayList<String[]> trainingSet= new ArrayList<String[]>();
		
		while (count < 10) {
			ArrayList<String> sequence = new ArrayList<String>();
			sequence.add("d");
			int i =sequence.size() -1;
			while (!(sequence.get(i).equals("a") || sequence.get(i).equals("g")) ){
				for (int j = 1; j < this.s.length; j++) {
					if ((sequence.get(i).equals(this.s[j]))) {
						sequence.add(s[j+flipCoin(randomNum)]);
						break;
					}
				}
				
				i =sequence.size() -1;
			}
			
			// Change list to Array of strings
			String[] sequenceArr = new String[sequence.size()];
			sequenceArr = sequence.toArray(sequenceArr);

			trainingSet.add(sequenceArr);
			count++;
			
		}
		
		return trainingSet;
	}

	/**
	 * flip a coin to decide which direction should the agent walk
	 * If return 1, then agent go right
	 * If return -1, then agent go left
	 * @param randomNum 
	 * @return 1 or -1, by chance
	 */
	private int flipCoin(Random randomNum) {
		
		int result = randomNum.nextInt(2);
		
		if(result == 0){
			return 1;
		}
		return -1;
	}
	
	/** testing and see the actual training sets.
	 
	public static void main(String[] args) {
		TrainingSets t = new TrainingSets();
		t.genTrainSets();
		for (int i = 0; i < 100; i++) {
			for (int j=0; j<10;j++) {
				System.out.println(Arrays.deepToString((Object[]) t.getTrainSets().get(i).get(j)));
			}
		}
		
	}
	**/

}

