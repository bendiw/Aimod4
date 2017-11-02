package som;

<<<<<<< HEAD
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

import caseloader.Problem;
import caseloader.ProblemCreator;

public class SOMtrainer {

	private int displayInterval;
	private Problem p;
	private Random r;
	private Node[][] nodes;
	
	
	public SOMtrainer(String filename, int mode, int numNodesX, int numNodesY, double[] weightLimits, double[] posLimits, int iterations) throws IOException {
		ProblemCreator pc = new ProblemCreator();
		p = pc.create(filename, mode);
		r = new Random();
		initNodes(numNodesX, numNodesY, weightLimits, posLimits);
		for (int i = 0; i < iterations; i++) {
			double[] sample = SampleCases();
			Node winner = MatchCases(sample);
			UpdateWeights();
		}
	}
	
	public double[] SampleCases() {
		return p.getCase(r.nextInt(p.getNumCases()));
	}
	
	public Node MatchCases(double[] sample) {
		double[][] distances = new double[nodes.length][nodes[0].length];
		int best_i;
		int best_j;
		int dist;
		double best = Double.POSITIVE_INFINITY;
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances[0].length; j++) {
				dist = Tools.getEuclidian(sample, nodes[i][j].getWeights());
			}
		}
	}
	
	public void initNodes(int numNodesX, int numNodesY, double[] weightLimits, double[] posLimits) {
		nodes = new Node[numNodesX][numNodesY];
		double[] weights = new double[p.getInputSize()];
		double xPos;
		double yPos;
		for (int i = 0; i < numNodesX; i++) {
			for (int j = 0; j < numNodesY; j++) {
				weights[0] = weightLimits[0] + r.nextDouble()*(weightLimits[1]-weightLimits[0]);
				weights[1] = weightLimits[0] + r.nextDouble()*(weightLimits[1]-weightLimits[0]);
				xPos = posLimits[0] + r.nextDouble()*(posLimits[1]-posLimits[0]);
				yPos = posLimits[0] + r.nextDouble()*(posLimits[1]-posLimits[0]);
				nodes[i][j] = new Node(weights, xPos, yPos);
			}
		}
	}
	
	public static void main(String[] args) {
		int[][] nodes = new int[3][1];
		System.out.println(nodes.length);
		System.out.println(nodes[0].length);
		nodes[0][0] = 1;
		nodes[1][0] = 2;
		nodes[2][0] = 3;
		System.out.println(nodes[0][0] +" "+ nodes[1][0]);
	}
}
