package ar.com.misdosneuronas.core.v3;

/**
 * Entrenamiento por Correccion de Error
 * @author Vincent
 *
 */
public class ReglaAprendizaje {
	/**
	 * 
	 */
	private Neurona neurona;
	
	/**
	 * Coeficiente de Entrenamiento
	 */
	private double alpha;
	
	/**
	 * @param alpha
	 * @param neurona 
	 */
	public ReglaAprendizaje(double alpha, Neurona neurona){
		this.alpha = alpha;
		this.neurona = neurona;
	}
	
	/**
	 * @param salidaDeseada
	 */
	public void entrenar(double salidaDeseada){
		//TODO ver cuando cortar el entrenamiento
		double deltaW[] = new double[neurona.w.length];	// crea un array de deltas de peso, tantos como entradas tenga la neurona
		double x[] = neurona.x;
		double w[] = neurona.w;
		
		double y = neurona.y;	// requiere que se haya calculado previamente la salida de la neurona. 
		double yd = salidaDeseada;

		// por cada entra calcula su delta
		for(int i = 0; i < x.length; i++){
			deltaW[i] = (y - yd) * x[i] * alpha;
		}
		// calcula los pesos segun los deltas calculados
		for(int i = 0; i < w.length; i++){
			w[i] = w[i] - deltaW[i];
		}
		// actualiza los pesos de la neurona
		neurona.w = w;
	}

}
