package som;

public class Node {
	
	private double[] weights;
	private double[] pos;
	
	public Node(double[] weights, double xPos, double yPos) {
		this.weights = weights;
		pos = new double[2];
		this.pos[0] = xPos;
		this.pos[1] = yPos;
	}
	
	public double[] getWeights() {
		return weights;
	}
	
	public void setWeights(double[] weights) {
		this.weights = weights;
	}
	
	public double[] getPos() {
		return this.pos;
	}
	
	public double getXPos() {
		return this.pos[0];
	}
	
	public double getYPos() {
		return this.pos[1];
	}
}
