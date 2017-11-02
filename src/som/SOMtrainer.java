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
	private double initLearningRate;
	private int mode;
	private SOMvisualizer v;
	
	
	public SOMtrainer(String filename, int mode, int numNodesX, int numNodesY, double[] weightLimits, int iterations, int initRad, double initLearningRate, int displayInterval) throws IOException {
		ProblemCreator pc = new ProblemCreator();
		this.mode = mode;
		this.initRadius = initRad;
		this.displayInterval = displayInterval;
		this.initLearningRate = initLearningRate;
		this.learningRate = this.initLearningRate;
		p = pc.create(filename, mode);
		r = new Random();
		initNodes(numNodesX, numNodesY, weightLimits);
		for (int i = 0; i < iterations; i++) {
			double[] sample = SampleCases();
//			double[][] distArray = MatchCases(sample);
			int[] winner = getWinnerIndex(sample);
			UpdateWeights(winner, i, iterations, sample);
			double mult = Math.exp(-(i/(double)iterations));
			this.learningRate = this.initLearningRate*mult;
			if(i%displayInterval==0 || i==iterations-1) {
				if(mode==Tools.TSP) {
					this.v = new TSPvisualizer((TSPproblem)p, this.nodes, 0);			
					v.display(i);
					if(i!=0) {
						System.out.println("Distance: "+getTotalDistance());
					}
				}
//				System.out.println(mult);
			}

		}
		TSPvisualizer TSPv = new TSPvisualizer((TSPproblem)p, this.nodes,1);
		TSPv.display(0);
	}
	
	private void UpdateWeights(int[] winner, int iter, int maxIter, double[] sample) {
		double nRad = Tools.getNeighborhoodRadius(this.initRadius, iter, maxIter);
		for (int i = 0; i < this.nodes.length; i++) {
			for (int j = 0; j < this.nodes[0].length; j++) {
//				double dist = Tools.getDiscriminant(, v2)
				double dist;
				if(mode==Tools.TSP) {
					dist = Tools.getTSPeuclidian(winner, new int[] {i,j}, this.nodes.length);
				}else {
					dist = Tools.getEuclidian(winner, new int[] {i,j});
				}
				if(dist <=nRad) {
					nodes[i][j].setWeights(Tools.getAdjustedWeight(nodes[i][j].getWeights(), sample, nRad, this.learningRate, dist));
				}
			}
		}
	}

	public double[] SampleCases() {
		return p.getCase(r.nextInt(p.getNumCases()));
	}
	
//	public Node MatchCases(double[] sample) {
//		double[][] distances = new double[nodes.length][nodes[0].length];
//		double dist;
//		for (int i = 0; i < distances.length; i++) {
//			for (int j = 0; j < distances[0].length; j++) {
//				dist = Tools.getEuclidian(sample, nodes[i][j].getWeights());
//				distances[i][j] = dist;
//			}
//		}
//		return distances;
//	}
	
	public int[] getWinnerIndex(double[] sample) {
		Double bestValue = Double.POSITIVE_INFINITY;
//		Node bestNode = null;
		int [] index = new int[2];
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
				double dist = Tools.getDiscriminant(sample, nodes[i][j].getWeights());

				if (dist< bestValue) {
//					bestNode = nodes[i][j];
					index[0] = i;
					index[1] = j;
					bestValue = dist;
				}
			}
		}
		return index;
	}
	
	public void initNodes(int numNodesX, int numNodesY, double[] weightLimits) {
		nodes = new Node[numNodesX][numNodesY];
		double[] weights = new double[p.getInputSize()];
		double xPos;
		double yPos;
		for (int i = 0; i < numNodesX; i++) {
			for (int j = 0; j < numNodesY; j++) {
				for (int j2 = 0; j2 < p.getInputSize(); j2++) {
					weights[j2] = weightLimits[0] + r.nextDouble()*(weightLimits[1]-weightLimits[0]);
				}
//				xPos = posLimits[0] + r.nextDouble()*(posLimits[1]-posLimits[0]);
//				yPos = posLimits[0] + r.nextDouble()*(posLimits[1]-posLimits[0]);
				nodes[i][j] = new Node(weights.clone());
			}
		}
	}
	
	private void clearLabels() {
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
				nodes[i][j].setLabel(null);
			}
		}
	}
	
	private double getTotalDistance() {
		clearLabels();
		double dist = 0;
		double[] prevCoords=null;
		double[] firstCoords=null;
		ArrayList<double[]> cities = p.getAllCases();
		for (double[] c : cities) {
			int[] closest = getWinnerIndex(c);
			if(this.nodes[closest[0]][closest[1]].getLabel()==null) {
				ArrayList<double[]> label = new ArrayList<double[]>();
				label.add(c);
				this.nodes[closest[0]][closest[1]].setLabel(label);
			}else {
				ArrayList<double[]> label = (ArrayList<double[]>)this.nodes[closest[0]][closest[1]].getLabel();
				label.add(c);
			}
		}
		for (int i = 0; i < nodes.length; i++) {
			ArrayList<double[]> lab = (ArrayList<double[]>)nodes[i][0].getLabel();
			if(lab != null) {
				for (double[] ds : lab) {
					if(prevCoords==null) {
						prevCoords = ds;
						firstCoords = ds;
					}else {
						dist += Math.sqrt(Tools.getDiscriminant(prevCoords, ds));
						prevCoords = ds;
					}
				}
			}
		}
		dist += Math.sqrt(Tools.getDiscriminant(firstCoords, prevCoords));
		return dist;
	}
	
	public static void main(String[] args) throws IOException {
		SOMtrainer som = new SOMtrainer("4",Tools.TSP, 200, 1, new double[] {200,1000}, 1500000, 20, 0.1, 100000);
		int[][] nodes = new int[3][1];
//		System.out.println(nodes.length);
//		System.out.println(nodes[0].length);
		nodes[0][0] = 1;
		nodes[1][0] = 2;
		nodes[2][0] = 3;
//		System.out.println(nodes[0][0] +" "+ nodes[1][0]);
	}
}
