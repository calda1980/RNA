package ar.com.rna;

public class AleatoriedadCondicionada1 {
	double c = Math.atan(10);
	double entrada, salida;

	public double getSalida(double nNeuro, double num) {
		entrada = num * 100 / nNeuro;
		salida = (Math.atan((entrada / 10) - 10) * 100 / c) + 100;
		salida = salida * nNeuro / 100;
		return salida;
	}

}