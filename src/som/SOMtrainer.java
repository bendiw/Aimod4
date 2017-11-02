package som;

import java.io.IOException;

import caseloader.Problem;
import caseloader.ProblemCreator;

public class SOMtrainer {

	private int displayInterval;
	private Problem p;
	private Node[][] nodes;
	
	
	public SOMtrainer(String filename, int mode, int numNodesX, int numNodesY) throws IOException {
		ProblemCreator pc = new ProblemCreator();
		Problem problem = pc.create(filename, mode);
		if (numNodesY > 0) {
			nodes = new Node[numNodesX][numNodesY];
		}
		 
		for (int i = 0; i < nodes.length; i++) {
			
		}
	}
	public static void main(String[] args) {
		int[][] nodes = new int[3][0];
		nodes[0][0] = 1;
		nodes[1][0] = 2;
		nodes[2][0] = 3;
		System.out.println(nodes.toString());
	}
}
