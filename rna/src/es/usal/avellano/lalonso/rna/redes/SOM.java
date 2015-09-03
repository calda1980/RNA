package es.usal.avellano.lalonso.rna.redes;

import java.io.PrintStream;
import java.util.LinkedList;

public class SOM extends RedNeuronal {
	private Capa entrada;
	private Capa salida;
	private int nns;
	private int nne;
	private double ratio;
	private double[][] pesos;
	private LinkedList dimensiones;

	public SOM(String codigo, String nombre) {
		super(codigo, nombre);
	}

	public SOM(String codigo, String nombre, Juego[] ensayo, Juego[] prueba,
			double mu) {
		super(codigo, nombre, ensayo, prueba, mu);
	}

	public SOM(String codigo, String nombre, Juego[] ensayo, Juego[] prueba,
			double mu, double ratio, LinkedList dimensiones) {
		super(codigo, nombre, ensayo, prueba, mu);

		this.nns = 1;
		this.dimensiones = dimensiones;
		for (int i = 0; i < dimensiones.size(); i++) {
			Integer dim = Integer.valueOf((String) dimensiones.get(i));
			this.nns *= dim.intValue();
		}
		this.nne = 2;

		this.salida = new Capa(this.nns);
		this.entrada = new Capa(this.nne);
		this.pesos = new double[this.nns][this.nne];

		this.ratio = ratio;

		for (int i = 0; i < this.nns; i++)
			for (int j = 0; j < this.nne; j++)
				this.pesos[i][j] = (Math.random() - 0.5D);
	}

	public SOM(String codigo, String nombre, double mu, double ratio,
			LinkedList dimensiones) {
		super(codigo, nombre, mu);

		this.nns = 1;
		this.dimensiones = dimensiones;
		for (int i = 0; i < dimensiones.size(); i++) {
			Integer dim = (Integer) dimensiones.get(i);
			this.nns *= dim.intValue();
		}
		this.nne = 2;

		this.salida = new Capa(this.nns);
		this.entrada = new Capa(this.nne);
		this.pesos = new double[this.nns][this.nne];

		this.ratio = ratio;

		for (int i = 0; i < this.nns; i++)
			for (int j = 0; j < this.nne; j++)
				this.pesos[i][j] = (Math.random() - 0.5D);
	}

	public SOM(String codigo, String nombre, double mu, double ratio, int nne,
			int nns) {
		super(codigo, nombre, mu);

		this.nns = nns;
		this.dimensiones = new LinkedList();
		this.dimensiones.add(new Integer(nns));
		this.nne = nne;

		this.salida = new Capa(nns);
		this.entrada = new Capa(nne);
		this.pesos = new double[nns][nne];

		this.ratio = ratio;

		double delta = 6.283185307179586D / nns;
		for (int i = 0; i < nns; i++) {
			this.pesos[i][0] = (0.2D * Math.cos(delta * i));
			this.pesos[i][1] = (0.2D * Math.sin(delta * i));
		}
	}

	public Capa getCapaSalida() {
		return this.salida;
	}

	public double[][][] getPesos() {
		int ancho = ((Integer) this.dimensiones.get(0)).intValue();
		int alto = ((Integer) this.dimensiones.get(1)).intValue();
		double[][][] p = new double[ancho][alto][2];
		for (int i = 0; i < ancho; i++)
			for (int j = 0; j < alto; j++)
				System.arraycopy(this.pesos[(i * ancho + j)], 0, p[i][j], 0, 2);
		return p;
	}

	public double[][] getPesos1D() {
		int ancho = ((Integer) this.dimensiones.get(0)).intValue();
		double[][] p = new double[ancho][2];
		for (int i = 0; i < ancho; i++)
			System.arraycopy(this.pesos[i], 0, p[i], 0, 2);
		return p;
	}

	public double calcularSalida(int i, int k) {
		if ((this.dimensiones.size() == 2) || (this.dimensiones.size() == 3)) {
			if (vecindad(i, k) < this.ratio)
				return Math.exp(-vecindad(i, k)
						/ (2.0D * this.ratio * this.ratio));
			return 0.0D;
		}

		if (vecindad1D(i, k) < this.ratio)
			return Math.exp(-vecindad1D(i, k)
					/ (2.0D * this.ratio * this.ratio));
		return 0.0D;
	}

	public double vecindad(int i, int j) {
		int numCols = ((Integer) this.dimensiones.get(1)).intValue();
		int numFils = ((Integer) this.dimensiones.get(0)).intValue();
		int x1 = i / numCols;
		int x2 = j / numCols;
		int y1 = i % numCols;
		int y2 = j % numCols;
		int d1 = x1 - x2;
		int d2 = y1 - y2;
		return Math.sqrt(d1 * d1 + d2 * d2);
	}

	public double vecindad1D(int i, int j) {
		int numCols = ((Integer) this.dimensiones.get(0)).intValue();
		return Math.min(Math.abs(Math.IEEEremainder(i - j, numCols)),
				Math.abs(Math.IEEEremainder(j - i, numCols)));
	}

	public int ejecutar(double[] entradaN) {
		int posGanadora = 0;
		double disGanadora = 0.0D;
		double intensidadEntrada = 0.0D;

		for (int i = 0; i < this.nne; i++) {
			this.entrada.getNeurona(i).setEntrada(entradaN[i]);
			this.entrada.getNeurona(i).setSalida(
					this.entrada.getNeurona(i).getEntrada());
		}

		disGanadora = distanciaEuclidea(entradaN, this.pesos[0]);

		for (int i = 1; i < this.nns; i++) {
			intensidadEntrada = distanciaEuclidea(entradaN, this.pesos[i]);

			if (intensidadEntrada >= disGanadora)
				continue;
			disGanadora = intensidadEntrada;
			posGanadora = i;
		}

		for (int i = 0; i < this.nns; i++) {
			this.salida.getNeurona(i).setSalida(calcularSalida(i, posGanadora));
		}
		for (int i = 0; i < this.nns; i++) {
			double sum = 0.0D;
			for (int k = 0; k < this.nne; k++) {
				this.pesos[i][k] += this.mu * (entradaN[k] - this.pesos[i][k])
						* this.salida.getNeurona(i).getSalida();
			}
		}
		this.mu = (0.1D + (this.mu - 0.1D) * 0.99D);
		this.ratio = (1.0D + (this.ratio - 1.0D) * 0.999D);
		return posGanadora;
	}

	public double distanciaEuclidea(double[] x, double[] w) {
		double distancia = 0.0D;
		for (int k = 0; k < x.length; k++) {
			distancia += Math.pow(w[k] - x[k], 2.0D);
		}
		return Math.sqrt(distancia);
	}

	public void imprimirPesos() {
		for (int i = 0; i < this.nns; i++)
			for (int j = 0; j < this.nne; j++)
				System.out.println("pesos[S" + i + "]" + "[E" + j + "]: "
						+ this.pesos[i][j]);
	}

	public void imprimirSalidas() {
		for (int i = 0; i < this.nns; i++)
			System.out.println("salida" + i + " = "
					+ this.salida.getNeurona(i).getSalida());
	}
}