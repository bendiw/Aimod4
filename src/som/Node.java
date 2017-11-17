package som;

import java.util.ArrayList;

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
