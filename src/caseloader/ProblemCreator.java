package caseloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ProblemCreator {
	
	public static final int TSP = 0;
	public static final int MNIST = 1;
	public static final int MNISTlen = 784;
	public static final int MNISTtrain = 60000;
	public static final int MNISTtest = 10000;
	
	
	
	public Problem create(String filename, int mode) throws IOException{
		if(mode==TSP) {
			filename = System.getProperty("user.dir")+"\\TSP\\"+filename+".txt";
			FileReader fr = new FileReader(new File(filename));
			BufferedReader b = new BufferedReader(fr);
			ArrayList<double[]> coords = new ArrayList<double[]>();
			String[] data = b.readLine().trim().split(" ");
			int numCities = Integer.parseInt(data[data.length-1]);
			b.readLine();
			for (int i = 0; i < numCities; i++) {
				data = b.readLine().trim().split(" ");
				coords.add(new double[] {Double.parseDouble(data[1]), Double.parseDouble(data[2])});
			}
			b.close();
			return new TSPproblem(numCities, coords);
		}else if(mode == MNIST) {
			//training set
			ArrayList<double[]> train = new ArrayList<double[]>();
			ArrayList<Double> trainLabels = new ArrayList<Double>(); 
			filename = System.getProperty("user.dir")+"\\all_flat_mnist_training_cases_text.txt";
			FileReader fr = new FileReader(new File(filename));
			BufferedReader b = new BufferedReader(fr);
			String[] tlab = b.readLine().trim().split(" ");
			for (int i = 0; i < tlab.length; i++) {
				trainLabels.add((double)Integer.parseInt(tlab[i]));
			} 
			for (int i = 0; i < MNISTtrain; i++) {
				double[] img = new double[MNISTlen];
				String[] tcase = b.readLine().trim().split(" ");
				for (int j = 0; j < tcase.length; j++) {
					img[j] = (double) Integer.parseInt(tcase[j]);
				}
				train.add(img);
			}
			b.close();
			
			//test set
			ArrayList<double[]> test = new ArrayList<double[]>();
			ArrayList<Double> testLabels = new ArrayList<Double>(); 
			filename = System.getProperty("user.dir")+"\\all_flat_mnist_testing_cases_text.txt";
			fr = new FileReader(new File(filename));
			b = new BufferedReader(fr);
			tlab = b.readLine().trim().split(" ");
			for (int i = 0; i < tlab.length; i++) {
				testLabels.add((double)Integer.parseInt(tlab[i]));
			} 
			for (int i = 0; i < MNISTtest; i++) {
				double[] img = new double[MNISTlen];
				String[] tcase = b.readLine().trim().split(" ");
				for (int j = 0; j < tcase.length; j++) {
					img[j] = (double) Integer.parseInt(tcase[j]);
				}
				test.add(img);
			}
			//test set

			b.close();

			return new MNISTproblem(train, test, trainLabels, testLabels);
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		ProblemCreator pc = new ProblemCreator();
		MNISTproblem m = (MNISTproblem)pc.create("", MNIST);
		System.out.println(m.getNumCases());
	}
	
public class TSPproblem implements Problem{
	private final int inputs=2;
	private int cities;
	private ArrayList<double[]> coords;
	private int[] prefDim;
	private double[] offset;
	
	public TSPproblem(int cities, ArrayList<double[]> coords) {
		this.coords = coords;
		this.cities = cities;
		int pref[] = new int[] {0,0};
		double[] offset = new double[] {0,0};
		for (double[] c : coords) {
			pref[0] = (int)Math.max(c[0], pref[0]);
			pref[1] = (int)Math.max(c[1], pref[1]);
			offset[0] = Math.min(c[0], offset[0]);
			offset[1] = Math.min(c[1], offset[1]);
		}
		this.prefDim = pref;
		offset[0] = Math.abs(offset[0]);
		offset[1] = Math.abs(offset[1]);
		this.offset = offset;
	}
	
	public ArrayList<double[]> getCoords() {
		return this.coords;
	}
	
	public double[] getOffset() {
		return this.offset;
	}
	
	@Override
	public int getNumCases() {
		return this.cities;
	}

	@Override
	public int getInputSize() {
		return inputs;
	}

	@Override
	public double[] getCase(int i) {
		return coords.get(i);
	}
	
	public ArrayList<double[]> getAllCases(){
		return this.coords;
	}

	@Override
	public int[] getPrefDim() {
		return this.prefDim;
	}

	@Override
	public double getLabel(int i) {
		return 0;
	}
	
	
}

public class MNISTproblem implements Problem{
	
	private ArrayList<double[]> train;
	private ArrayList<double[]> test;
	private ArrayList<Double> trainLabels;
	private ArrayList<Double> testLabels;
	
	
	public MNISTproblem(ArrayList<double[]> train, ArrayList<double[]> test, ArrayList<Double> trainLabels, ArrayList<Double> testLabels) {
		this.train = train;
		this.test = test;
		this.trainLabels = trainLabels;
		this.testLabels = testLabels;
	}

	@Override
	public int getNumCases() {
		return this.train.size();
	}

	@Override
	public int getInputSize() {
		return MNISTlen;
	}

	@Override
	public double[] getCase(int i) {
		return this.train.get(i);
	}

	@Override
	public int[] getPrefDim() {
		return new int[] {28, 28};
	}

	@Override
	public ArrayList<double[]> getAllCases() {
		return this.train;
	}

	@Override
	public double getLabel(int i) {
		return this.trainLabels.get(i);
	}

	public ArrayList<double[]> getTest() {
		return test;
	}

	public ArrayList<Double> getTestLabels() {
		return testLabels;
	}

	
	
}
	

}
