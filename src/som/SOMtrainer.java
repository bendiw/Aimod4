package som;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.plaf.synth.SynthSeparatorUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import caseloader.Problem;
import caseloader.ProblemCreator;
import caseloader.ProblemCreator.MNISTproblem;
import caseloader.ProblemCreator.TSPproblem;
import visuals.Cards;
import visuals.IMGvisualizer;
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
	private int iterations;
	private int mbs;
	private boolean train[];
	private double timeConstant;
	private boolean writeToFile;
	private double[][][][] distMatrix;
	private SOMvisualizer v;
	
	public SOMtrainer(String filename, int mode, int mbs, int numNodesX, int numNodesY, double[] weightLimits, int iterations, double initRad, double initLearningRate, int displayInterval) throws IOException {
		this(filename, false, false, mode, mbs, numNodesX, numNodesY, weightLimits, iterations,initRad, initLearningRate, displayInterval);
	}
	
	public SOMtrainer(String filename, boolean initFromFile, boolean writeToFile, int mode, int mbs, int numNodesX, int numNodesY, double[] weightLimits, int iterations, double initRad, double initLearningRate, int displayInterval) throws IOException {
		ProblemCreator pc = new ProblemCreator();
		this.mode = mode;
		this.distMatrix = new double[numNodesX][numNodesY][numNodesX][numNodesY];
		initDistMatrix();
		this.initRadius = initRad;
		this.writeToFile = writeToFile;
		this.displayInterval = displayInterval;
		this.initLearningRate = initLearningRate;
		this.timeConstant = iterations/Math.log(initRad);
		this.learningRate = this.initLearningRate;
		this.iterations = iterations;
		this.mbs = mbs;
		this.p = pc.create(filename, mode);
		this.r = new Random();
		if (! initFromFile) {
			initNodes(numNodesX, numNodesY, weightLimits);
			
		} else {
			loadNodes();
			this.v = new IMGvisualizer((MNISTproblem)p, 1, this.nodes);
			v.display(0);
		}
	}
		
	public void run() {
		ArrayList<Node[][]> states = new ArrayList<Node[][]>();
		if(mode==Tools.TSP) {
//			this.v = new TSPvisualizer((TSPproblem)p, this.nodes, 0);		
			states.add(Tools.cloneNodes(nodes));
//			v.display(0);
			System.out.println("Distance: "+getTotalDistance());
		}else {
			train = new boolean[p.getNumCases()];
			System.out.println("learning rate: "+this.learningRate);
			System.out.println("radius: "+Tools.getNeighborhoodRadius(this.initRadius, 0, this.timeConstant)+"\n\n");
			this.v = new IMGvisualizer((MNISTproblem)p, 1, this.nodes);
			v.display(0);

		}
		for (int i = 0; i < this.iterations; i++) {
			if (this.mode == Tools.IMG) {
				imageLoop(mbs, this.iterations);
			} else {
				double[] sample = SampleCases();
				int[] winner = getWinnerIndex(sample);
				UpdateWeights(winner, i, iterations, sample);
			}
			double mult = Math.exp(-(i/(double)iterations));
			this.learningRate = this.initLearningRate*mult;
			if(i%displayInterval==0 || i==iterations-1) {
				if(mode==Tools.TSP) {
//					this.v = new TSPvisualizer((TSPproblem)p, this.nodes, 0);		
//					v.display(i);
					if(i!=0) {
						states.add(Tools.cloneNodes(this.nodes));
						System.out.println("Distance: "+getTotalDistance());
					}
				}else {
					System.out.println("iter "+i);
					System.out.println("learning rate: "+this.learningRate);
					System.out.println("radius: "+Tools.getNeighborhoodRadius(this.initRadius, i, this.timeConstant)+"\n\n");
					this.v = new IMGvisualizer((MNISTproblem)p, 1, this.nodes);
					v.display(i);
				}
			}
		}
		if (writeToFile) {
			saveNodes();
		}
		if(mode==Tools.TSP) {
			TSPvisualizer TSPv = new TSPvisualizer((TSPproblem)p, this.nodes,1);
			TSPv.display(0);
			Cards c = new Cards(states, this.mode, p);
		}else {
			System.out.println("Training set accuracy: "+testTrainIMG());
			System.out.println("Test set accuracy: "+testIMG());
		}
	}
	
	private void initDistMatrix() {
		for (int i = 0; i < distMatrix.length; i++) {
			for (int j = 0; j < distMatrix[0].length; j++) {
				for (int j2 = 0; j2 < distMatrix[0][0].length; j2++) {
					for (int k = 0; k < distMatrix[0][0][0].length; k++) {
						distMatrix[i][j][j2][k] = Tools.getEuclidian(new int[] {i,j}, new int[] {j2,k});
					}
				}
			}
		}
	}
	
	private double testIMG() {
		int correct = 0;
		ArrayList<double[]> test = ((MNISTproblem) p).getTest();
		ArrayList<Double> lab = ((MNISTproblem) p).getTestLabels();
		for (int i = 0; i < test.size(); i++) {
			int[] winner = getWinnerIndex(test.get(i));
			double num = (Math.round((double)nodes[winner[0]][winner[1]].getLabel()));
			if(num==lab.get(i)) {
				correct++;
			}
		}
		return (double)correct/(double)test.size();
	}
	
	private double testTrainIMG() {
		int correct = 0;
		ArrayList<double[]> train = new ArrayList<double[]>();
		ArrayList<Double> lab = new ArrayList<Double>();
		for (int i = 0; i < p.getNumCases(); i++) {
			if(this.train[i]) {
				train.add(p.getCase(i));
				lab.add(p.getLabel(i));
			}
		}
		for (int i = 0; i < train.size(); i++) {
			int[] winner = getWinnerIndex(train.get(i));
			double num = (Math.round((double)nodes[winner[0]][winner[1]].getLabel()));
			if(num==lab.get(i)) {
				correct++;
			}
		}
		return (double)correct/(double)train.size();
	}
	
	private void imageLoop(int mbs, int iterations) {
		int sampleIndex = r.nextInt(p.getNumCases()-mbs);
		for (int i = 0; i < mbs; i++) {
			this.train[sampleIndex] = true;
			double[] sample = p.getCase(sampleIndex);
			int[] winner = getWinnerIndex(sample);
			UpdateWeights(winner, i, iterations, sample);
			nodes[winner[0]][winner[1]].addToWins(p.getLabel(sampleIndex));
			sampleIndex++;
		}
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
				nodes[i][j].setLabelFromWins();
			}
		}
	}
	
	private void UpdateWeights(int[] winner, int iter, int maxIter, double[] sample) {
		double nRad = Tools.getNeighborhoodRadius(this.initRadius, iter, this.timeConstant);
		for (int i = 0; i < this.nodes.length; i++) {
			for (int j = 0; j < this.nodes[0].length; j++) {
//				double dist = Tools.getDiscriminant(, v2)
				double dist;
				if(mode==Tools.TSP) {
					dist = Tools.getTSPeuclidian(winner, new int[] {i,j}, this.nodes.length);
				}else {
					dist = distMatrix[winner[0]][winner[1]][i][j];
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
	//returns winners location in output grid
	public int[] getWinnerIndex(double[] sample) {
		Double bestValue = Double.POSITIVE_INFINITY;
//		Node bestNode = null;
		int [] index = new int[2];
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
				double dist = Tools.getDiscriminant(sample, nodes[i][j].getWeights());
				if (dist < bestValue) {
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
		double[] origo = new double[2];
		double ang=0;
		double radius=0;
		if(mode==Tools.TSP) {
			origo[0] = (this.p.getPrefDim()[0]+this.p.getOffset()[0])/2;
			origo[1] = (this.p.getPrefDim()[1]+this.p.getOffset()[1])/2;
			ang = (360/((double)numNodesX))*(Math.PI/180.0);
			System.out.println(ang);
			radius=(this.p.getPrefDim()[0]+this.p.getOffset()[0])/3.5;
		}
		double[] weights = new double[p.getInputSize()];
		for (int i = 0; i < numNodesX; i++) {
			for (int j = 0; j < numNodesY; j++) {
				if(this.mode==Tools.TSP) {
					weights[0] = origo[0]+radius*Math.cos(i*ang);
					weights[1] = origo[1]+radius*Math.sin(i*ang);
				}else {
					for (int j2 = 0; j2 < p.getInputSize(); j2++) {
						weights[j2] = weightLimits[0] + r.nextDouble()*(weightLimits[1]-weightLimits[0]);
					}
				}
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
			nodes[i][0].sortLabel();
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
	
	public void saveNodes() {
		StringBuilder sb = new StringBuilder();
//		String saveString = "";
		for (int i = 0; i < nodes.length; i++) {
//			if (i>0) {saveString += "\n";}
			if(i>0) {sb.append("\n");}
			for (int j = 0; j < nodes[0].length; j++) {
//				if (j > 0) { saveString += " ";}
				if(j>0) {sb.append(" ");}
//				saveString += nodes[i][j].getLabel() +";";
				sb.append(nodes[i][j].getLabel()).append(";");
				double[] w = nodes[i][j].getWeights();
//				saveString+= Arrays.toString(w);
//				sb.append(Arrays.toString(w));
				for (int j2 = 0; j2 < w.length; j2++) {
//					if (j2 > 0) { saveString += ",";}
//					saveString+= w[j2];
					if(j2>0) {sb.append(",");}
					sb.append(w[j2]);
				}
			}
		}
		BufferedWriter writer = null;
		try {
		    writer = new BufferedWriter( new FileWriter(System.getProperty("user.dir")+"\\saved_nodes.txt"));
//		    writer.write(saveString);
		    writer.write(sb.toString());
		}
		catch ( IOException e){}
		finally {
		    try {
		        if ( writer != null)
		        writer.close( );
		    }
		    catch ( IOException e) {}
		}
	}
	
	public void loadNodes() {
		BufferedReader reader = null;
		ArrayList<String[]> stringList = new ArrayList<String[]>();
		try {
			reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\saved_nodes.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				stringList.add(line.split("\\s+"));
			}
		} catch (IOException e) {}
		finally {
		    try {
		        if ( reader != null)
		        reader.close( );
		    }
		    catch ( IOException e) {}
		}
		this.nodes = new Node[stringList.size()][stringList.get(0).length];
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
				String[] labW = stringList.get(i)[j].split(";");
				String[] weightString = labW[1].split(",");
				double[] weights = new double[weightString.length];
				for (int k = 0; k < weightString.length; k++) {
					weights[k] = Double.parseDouble(weightString[k]);
				}
				nodes[i][j] = new Node(weights);
				nodes[i][j].setLabel(Double.parseDouble(labW[0]));
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		SOMtrainer som = new SOMtrainer("8",false, false,Tools.IMG, 200, 20,20, new double[] {0,255},1000, 2, 0.2, 200);
		som.run();
	}
}
