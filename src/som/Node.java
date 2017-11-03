package som;

public class Node {
	
	private double[] weights;
	private Object label;
	
	public Node(double[] weights) {
		this.weights = weights;
		this.label = null;

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
	public static void main(String[] args) {
	}

}
