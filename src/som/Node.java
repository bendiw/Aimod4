package som;

public class Node {
	
	private double[] weights;
	private double[] pos;
	private Object label;
	
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
	
	public void setLabel(Object label) {
		this.label = label;
	}
	
	public Object getLabel() {
		return this.label;
	}
	
	public static void main(String[] args) {
//		double[] w = {4,2};
//		double x = 6.3;
//		double y = -4.0;
//		String mode = "mnist";
//		Node n = new Node(w,x,y,mode);
//		n.setLabel("hest");
//		System.out.println(n.getLabel());
	}
}
