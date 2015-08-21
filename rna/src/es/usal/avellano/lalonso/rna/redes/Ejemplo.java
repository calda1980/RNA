package es.usal.avellano.lalonso.rna.redes;

public class Ejemplo {
	private double[] entradas;
	private double[] salidas;

	public Ejemplo() {
	}

	public Ejemplo(double[] entradas, double[] salidas) {
		this.entradas = new double[entradas.length];
		this.salidas = new double[salidas.length];

		for (int i = 0; i < entradas.length; i++) {
			this.entradas[i] = entradas[i];
		}
		for (int i = 0; i < salidas.length; i++)
			this.salidas[i] = salidas[i];
	}

	public Ejemplo(Ejemplo ej) {
		this.entradas = new double[ej.getEntradas().length];
		this.salidas = new double[ej.getSalidas().length];

		for (int i = 0; i < this.entradas.length; i++) {
			this.entradas[i] = ej.getEntrada(i);
		}
		for (int i = 0; i < this.salidas.length; i++)
			this.salidas[i] = ej.getSalida(i);
	}

	public double[] getEntradas() {
		return this.entradas;
	}

	public double[] getSalidas() {
		return this.salidas;
	}

	public double getEntrada(int indice) {
		return this.entradas[indice];
	}

	public double getSalida(int indice) {
		return this.salidas[indice];
	}

	public void setEntrada(int indice, double valor) {
		this.entradas[indice] = valor;
	}

	public void setSalida(int indice, double valor) {
		this.salidas[indice] = valor;
	}
}