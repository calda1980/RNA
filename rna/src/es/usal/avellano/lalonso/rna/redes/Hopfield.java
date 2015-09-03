package es.usal.avellano.lalonso.rna.redes;


public class Hopfield extends RedNeuronal {
	private Capa neuronas;
	private int n;
	private double[][] pesos;

	public Hopfield(String codigo, String nombre) {
		super(codigo, nombre);
	}

	public Hopfield(String codigo, String nombre, int numNeuronas) {
		super(codigo, nombre);

		this.n = numNeuronas;
		this.neuronas = new Capa(this.n);
		this.pesos = new double[this.n][this.n];

		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.n; j++)
				this.pesos[i][j] = 0.0D;
	}

	public void imprimirPesos() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++)
				System.out.print(this.pesos[i][j] + " ");
			System.out.println();
		}
	}

	public void reiniciarPesos() {
		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.n; j++)
				this.pesos[i][j] = 0.0D;
	}

	public void setEntradas(double[] entradas) {
		if (entradas.length != this.n) {
			System.err
					.println("Error: Longitud del patrón de entrada distinto al número de neuronas");
			return;
		}

		for (int i = 0; i < this.n; i++) {
			this.neuronas.getNeurona(i).setEntrada(entradas[i]);
			this.neuronas.getNeurona(i).setSalida(entradas[i]);
		}
	}

	public double[] getSalidas() {
		double[] estado = new double[this.n];
		for (int i = 0; i < this.n; i++)
			estado[i] = this.neuronas.getNeurona(i).getSalida();
		return estado;
	}

	public int[] getSalidasEnteras() {
		int[] estado = new int[this.n];
		for (int i = 0; i < this.n; i++)
			estado[i] = (int) this.neuronas.getNeurona(i).getSalida();
		return estado;
	}

	public void memorizarPatron(double[] patron) {
		if (patron.length != this.n) {
			System.err
					.println("Error: Longitud del patrón de entrada distinto al número de neuronas");
			return;
		}

		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.n; j++) {
				if (i == j)
					continue;
				this.pesos[i][j] += patron[i] * patron[j];
			}
	}

	public void imprimirSalida() {
		for (int i = 0; i < this.n; i++)
			System.out.print(this.neuronas.getNeurona(i).getSalida() + " ");
		System.out.println();
	}

	public Capa getNeuronas() {
		return this.neuronas;
	}

	public double[][] getPesos() {
		return this.pesos;
	}

	public double getPeso(int i, int j) {
		return this.pesos[i][j];
	}

	public void ejecutar() {
		double sum = 0.0D;
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++)
				sum += this.neuronas.getNeurona(j).getSalida()
						* this.pesos[i][j];
			this.neuronas.getNeurona(i).setEntrada(sum);
			this.neuronas.getNeurona(i).activarSigno();
			sum = 0.0D;
		}
	}

	public double energia() {
		double sum = 0.0D;

		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.n; j++)
				sum += this.neuronas.getNeurona(i).getSalida()
						* this.neuronas.getNeurona(j).getSalida()
						* this.pesos[i][j];
		return -0.5D * sum;
	}
}