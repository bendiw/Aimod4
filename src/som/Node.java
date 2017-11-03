package som;

import java.util.ArrayList;

public class Node {
	
	private double[] weights;
	private Object label;
	private ArrayList<Double> winningLabels;
	
	public Node(double[] weights) {
		this.weights = weights;
		this.label = null;
		this.winningLabels = null;

	}
	
	public double[] getWeights() {
		return weights;
	}
	
	public void setWeights(double[] weights) {
		this.weights = weights;
	}
	
	
	public void setLabel(Object label) {
		this.label = label;
	}
	
	public Object getLabel() {
		return this.label;
	}
	
	public void addToWins(double label) {
		this.winningLabels.add(label);
	}
	
	public void setLabelFromWins() {
		double sum = 0;
		double count = 0;
		for (double label : this.winningLabels) {
			sum += label;
			count++;
		}
		int avg = Math.toIntExact(Math.round(sum/count));
		setLabel(avg);
		this.winningLabels.clear();
	}

}
