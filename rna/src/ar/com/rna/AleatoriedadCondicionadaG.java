package ar.com.rna;

public class AleatoriedadCondicionadaG {
	public double getSalida(double x, double y) {
		double c = Math.atan(10);
		return ((Math.atan(((y * 100 / x) / 10) - 10) * 100 / c) + 100) * x
				/ 100;
	}
}