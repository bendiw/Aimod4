package som;

public class Tools {
	static float getEuclidian(int[] v1, int[] v2) {
		float res = 0;
		for (int i = 0; i < v2.length; i++) {
			res+= Math.sqrt((v1[i]-v2[i])^2);
		}
		return res;
	}
}
