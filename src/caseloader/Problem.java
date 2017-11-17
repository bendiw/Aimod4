package caseloader;

import java.util.ArrayList;

public interface Problem {
	public int getNumCases();
	
	public int getInputSize();
	
	public double getLabel(int i);
	
	public double[] getCase(int i);
	
	public int[] getPrefDim();
	
	public double[] getOffset();
	
	
	public ArrayList<double[]> getAllCases();
	
}
