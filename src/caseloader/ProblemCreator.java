package caseloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import py4j.GatewayServer;
import som.Node;
import som.Tools;
import visuals.TSPvisualizer;
import visuals.Cards;


public class ProblemCreator {
	
	public static final int TSP = 0;
	public static final int MNIST = 1;
	
	
	
	public Problem create(String filename, int mode) throws IOException{
		filename = System.getProperty("user.dir")+"\\TSP\\"+filename+".txt";
		if(mode==TSP) {
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
			FileReader fr = new FileReader(new File(filename));
//			b.close();
			return new MNISTproblem();
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {

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
		// TODO Auto-generated method stub
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
	
}

public class MNISTproblem implements Problem{

	@Override
	public int getNumCases() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInputSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double[] getCase(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getPrefDim() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<double[]> getAllCases() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
	

}
