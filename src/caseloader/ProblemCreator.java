package caseloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProblemCreator {
	
	public static final int TSP = 0;
	public static final int MNIST = 1;
	
	
	public Problem create(String filename, int mode) throws IOException{
		FileReader fr = new FileReader(new File(filename));
		BufferedReader b = new BufferedReader(fr);
		if(mode==TSP) {
			ArrayList<int[]> coords = new ArrayList<int[]>();
			String[] data = b.readLine().trim().split(" ");
			int numCities = Integer.parseInt(data[2]);
			b.readLine();
			for (int i = 0; i < numCities; i++) {
				data = b.readLine().split(" ");
				coords.add(new int[] {Integer.parseInt(data[1]), Integer.parseInt(data[2])});
			}
			b.close();
			return new TSPproblem(numCities, coords);
		}else if(mode == MNIST) {
			b.close();
			return new MNISTproblem();
		}
		return null;
	}
	
	public class TSPproblem implements Problem{
		private final int inputs=2;
		private int cities;
		private ArrayList<int[]> coords;
		
		public TSPproblem(int cities, ArrayList<int[]> coords) {
			this.coords = coords;
			this.cities = cities;
		}
		
		public ArrayList<int[]> getCoords() {
			return this.coords;
		}
		
		@Override
		public int[] getGridSize() {
			// TODO Auto-generated method stub
			return new int[] {this.cities};
		}

		@Override
		public int getInputSize() {
			return inputs;
		}
		
	}
	
	public class MNISTproblem implements Problem{

		@Override
		public int[] getGridSize() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getInputSize() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		ProblemCreator pc = new ProblemCreator();
		TSPproblem p = pc.create("C:\\Users\\Bendik\\Documents\\GitHub\\Aimod4\\TSP\\1.txt", TSP);
		System.out.println();
	}
}