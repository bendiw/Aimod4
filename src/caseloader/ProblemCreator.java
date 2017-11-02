package caseloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import py4j.GatewayServer;


public class ProblemCreator {
	
	public static final int TSP = 0;
	public static final int MNIST = 1;
	
	
	
	public Problem create(String filename, int mode) throws IOException{
		if(mode==TSP) {
			filename = System.getProperty("user.dir")+"\\TSP\\"+filename+".txt";
			FileReader fr = new FileReader(new File(filename));
			BufferedReader b = new BufferedReader(fr);
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
	
	public static void main(String[] args) throws IOException {

		ProblemCreator pc = new ProblemCreator();
<<<<<<< HEAD
		TSPproblem p = (TSPproblem) pc.create("1", TSP);
		TSPvisualizer tv = new TSPvisualizer(p, 0);
		TSPvisualizer tv1 = new TSPvisualizer(p, 1);
		ArrayList<Node[][]> n = new ArrayList<Node[][]>();
		n.add(new Node[][]{{new Node(null, 200, 300), new Node(null, 400, 50)}, {}});
		Cards c = new Cards(n, Cards.TSP, p);
		tv1.display(null);
//		System.out.println(p.getCoords().get(0)[0]);
=======
		TSPproblem p = (TSPproblem) pc.create(1.txt", TSP);
		System.out.println(p.getCoords().get(0)[0]);
>>>>>>> master
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
	public int getNumCases() {
		// TODO Auto-generated method stub
		return this.cities;
	}

	@Override
	public int getInputSize() {
		return inputs;
	}

	@Override
	public int[] getCase(int i) {
		return coords.get(i);
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
	
}
	

}
