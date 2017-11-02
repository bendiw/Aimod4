package som;

import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

import caseloader.Problem;
import caseloader.ProblemCreator;
import caseloader.ProblemCreator.TSPproblem;
import visuals.SOMvisualizer;
import visuals.TSPvisualizer;

public class SOMtrainer {

	private int displayInterval;
	private Problem p;
	private Random r;
	private Node[][] nodes;
	private double initRadius;
	private double learningRate;
	private SOMvisualizer v;
	
	
	public SOMtrainer(String filename, int mode, int numNodesX, int numNodesY, double[] weightLimits, double[] posLimits, int iterations, int initRad, double learningRate) throws IOException {
		ProblemCreator pc = new ProblemCreator();
		this.initRadius = initRad;
		this.learningRate = learningRate;
		p = pc.create(filename, mode);
		if(mode==Tools.TSP) {
			this.v = new TSPvisualizer((TSPproblem)p);			
		}
		r = new Random();
		initNodes(numNodesX, numNodesY, weightLimits, posLimits);
		for (int i = 0; i < iterations; i++) {
			double[] sample = SampleCases();
			Node winner = MatchCases(sample);
			UpdateWeights(winner, i, iterations, distArray, sample);
			
			if(i%displayInterval==0) {
				
			}
		}
	}
	
	
	private void UpdateWeights(Node winner, int iter, int maxIter, double[][] dist, double[] sample) {
		double nRad = Tools.getNeighborhoodRadius(this.initRadius, iter, maxIter);
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist[0].length; j++) {
				nodes[i][j].setWeights(Tools.getAdjustedWeight(nodes[i][j].getWeights(), sample, nRad, this.learningRate, dist[i][j]));
			}
		}
	}

	public double[] SampleCases() {
		return p.getCase(r.nextInt(p.getNumCases()));
	}
	
	public Node MatchCases(double[] sample) {
		double[][] distances = new double[nodes.length][nodes[0].length];
		int best_i;
		int best_j;
		double dist;
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
