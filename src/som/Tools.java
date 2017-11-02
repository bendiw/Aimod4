package som;

public class Tools {
	static float getEuclidian(int[] v1, int[] v2) {
		float res = 0;
		for (int i = 0; i < v2.length; i++) {
			res+= Math.sqrt((v1[i]-v2[i])^2);
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
