package es.usal.avellano.lalonso.rna.redes;

public class Capa {
	Neurona[] neuronas;

	public Capa() {
		this.neuronas = null;
	}

	public Capa(int numeroNeuronas) {
		this.neuronas = new Neurona[numeroNeuronas];
		for (int i = 0; i < numeroNeuronas; i++)
			this.neuronas[i] = new Neurona(0.0D, 0.0D);
	}

	public Capa(int numeroNeuronas, double k) {
		this.neuronas = new Neurona[numeroNeuronas];
		for (int i = 0; i < numeroNeuronas; i++)
			this.neuronas[i] = new Neurona(0.0D, 0.0D, k);
	}

	public Neurona[] getNeuronas() {
		return this.neuronas;
	}

	public Neurona getNeurona(int indice) {
		return this.neuronas[indice];
	}
}