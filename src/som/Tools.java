package som;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Tools {
	
    public final static int TSP = 0;
    public final static int IMG = 1;
    
	static double getEuclidian(int[] v1, int[] v2) {
		float res = 0;
		for (int i = 0; i < v2.length; i++) {
			res+= Math.sqrt(Math.pow(v1[i]-v2[i],2));
		}
		return res;
	}
	
	static Node[][] cloneNodes(Node[][] nodes, int mode){
		Node[][] ret = new Node[nodes.length][nodes[0].length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[0].length; j++) {
				
				double[] orig = nodes[i][j].getWeights();
				double[] w = new double[orig.length];
				
				for (int k = 0; k < orig.length; k++) {
					w[k] = new Double(orig[k]);
				}
				ret[i][j] = new Node(w);
				if(mode==Tools.IMG) {
					ret[i][j].setLabel(new Double((double)nodes[i][j].getLabel()));
				}
			}
		}
		return ret;
	}
	
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1, 
	                LinkedHashMap::new
	              ));
	}
	
	
	
	static double getTSPeuclidian(int[] v1, int[]v2, int len) {
		int[] first;
		int[] second;
		if(v1[0]<v2[0]) {
			first = v1;
			second = v2;
		}else {
			first = v2;
			second = v1;
		}
		double normalDist = getEuclidian(v1, v2);
		double crossDist = first[0]+len-second[0];
		return Math.min(normalDist, crossDist);
	}
	
	static double getDiscriminant(double[] v1, double[] v2) {
		float res = 0;
		for (int i = 0; i < v2.length; i++) {
			res+= (Math.pow(v1[i]-v2[i],2));
		}
		return res;
	}
	
	static double getNeighborhoodRadius(double mapRadius, int iteration, double timeConstant) {
		return mapRadius * Math.exp(-(double)iteration/timeConstant);
	}
	
	static double[] getAdjustedWeight(double[] weights, double[] inputs, double neighborhoodRadius, double learningRate, double dist) {
		double theta = Math.exp(-Math.pow(dist,2)/(2*Math.pow(neighborhoodRadius, 2)));
		for (int i = 0; i < weights.length; i++) {
			weights[i] += theta*learningRate*(inputs[i]-weights[i]);
		}
		return weights;
	}
}
