package caseloader;

import java.util.ArrayList;

public interface Problem {
	public int getNumCases();
	
	public int getInputSize();
	
	public double[] getCase(int i);
	
	public int[] getPrefDim();
	
	public ArrayList<double[]> getAllCases();
	
}
