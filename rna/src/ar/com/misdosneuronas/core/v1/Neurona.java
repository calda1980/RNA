package ar.com.misdosneuronas.core.v1;

/**
 * Neurona Artificial
 * @author Vincent
 */
public class Neurona {
	/**
	 * Valores de entrada
	 */
	public double[] x;
	/**
	 * Pesos de las entradas
	 */
	public double[] w;
	/**
	 * Entrada Neta
	 */
	public double Net;
	/**
	 * Estado de activacion
	 */
	public double activacion;
	/**
	 * Valor de salida
	 */
	public double y;
	
	/**
	 * Constructor de la clase
	 */
	public Neurona(int cantidadEntradas){
		// dimensiona el vector de entradas con el tamaño de entradas
		x = new double[cantidadEntradas];
		// dimensiona el vector de pesos asociados a las entradas
		w = new double[cantidadEntradas];
		// inicializa los pesos en con 0.5
		for (int i = 0; i < w.length; i++) {
			w[i] = 0.5d;
		}
	}
	//--------------- Caclulos ---------------//
	/**
	 * Calcula la entrada neta de la neurona
	 */
	public void calcularNet(){
		double result = 0;
		
		for (int i = 0; i < x.length; i++) {
			result = result + ( x[i] * w[i] );
		}
		
		Net = result;
	}
}
