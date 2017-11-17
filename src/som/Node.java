package som;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Node {
	
	private double[] weights;
	private Object label;
	private double winSum;
	private int winNum;
	
	public Node(double[] weights) {
		this.weights = weights;
		this.label = 0.0;
		this.winSum = 0.0;
		this.winNum = 0;

	}
	
	public double[] getWeights() {
		return weights;
	}
	
	public void setWeights(double[] weights) {
		this.weights = weights;
	}
	
	@SuppressWarnings("unchecked")
	public void sortLabel() {
		if(this.label==null) {
			return;
		}
		ArrayList<double[]> lab = (ArrayList<double[]>)this.label;
		ArrayList<double[]> sortlab = new ArrayList<double[]>();
		Map<Integer,Double> dist = new HashMap<Integer,Double>(lab.size());
		for (int i = 0; i < lab.size(); i++) {
			dist.put(i, Tools.getDiscriminant(weights, lab.get(i)));
		}
		Set<Integer> sorted = Tools.sortByValue(dist).keySet();
		for (Integer integer : sorted) {
			sortlab.add(lab.get(integer));
		}
		this.label=sortlab;
	}
	
	
	public void setLabel(Object label) {
		this.label = label;
	}
	
	public Object getLabel() {
		return this.label;
	}
	
	public void addToWins(double label) {
		this.winSum+=label;
		this.winNum+=1;
	}
	
	public void setLabelFromWins() {
		if(winNum>0) {
			setLabel(winSum/(double)winNum);
			this.winNum = 0;
			this.winSum = 0;
    }
	}

}
