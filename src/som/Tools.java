package som;

public class Tools {
	
    public final static int TSP = 0;
    public final static int IMG = 1;
    
	static double getEuclidian(double[] v1, double[] v2) {
		float res = 0;
		for (int i = 0; i < v2.length; i++) {
			res+= Math.sqrt(Math.pow(v1[i]-v2[i],2));
		}
		return res;
	}
	
	static double getTSPeuclidian(double[] v1, double[]v2, int len) {
		double[] first;
		double[] second;
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
	
	static double getNeighborhoodRadius(double mapRadius, int iteration, int numIterations) {
		double timeConstant = numIterations/Math.log(mapRadius);
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
