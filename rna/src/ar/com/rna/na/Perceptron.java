package ar.com.rna.na;

public class Perceptron {
	
	/**
	 * Valores de entrada
	 */
	double x[];
	/**
	 * Pesos de las entradas
	 */
	double w[];	
	/**
	 * Umbral de Activación
	 */
	double theta;
	/**
	 * Coeficiente de Entrenamiento
	 */
	double alfa;
	/**
	 * Función o Regla de Activación
	 */
	ReglaActivacion activacion;
	/**
	 * Función de Salida o Transferencia
	 */
	Transferencia transferencia;
	
	
	/**
	 * @param x
	 * @param theta
	 * @param alfa
	 */
	public Perceptron(int cantidadEntradas, double theta, double alfa) {
		// asigna el valor de umbral.
		this.theta = theta;
		
		// asigna el coeficiente de entrenamiento
		this.alfa = alfa;
		
		// establece el numero de entradas.
		this.x = new double[cantidadEntradas] ;
		
		// inicializa los pesos de las entrada en 0.5
		this.w = new double[x.length];
		for (int i = 0; i < w.length; i++) {
			w[i] = 0.5;
		}
	}

	/**
	 * @param x
	 */
	public void setEntrada(double x[]){
		this.x = x;
	}
	
	/**
	 * @return
	 */
	public double getY(){
		double result;
		double Net = 0;
		
		for (int i = 0; i < x.length; i++) {
			Net = Net + x[i] * w[i];
		}
		
		result = F(G(Net));
		
		return result;
	}
	
	/**
	 * @return
	 */
	public double[] getPesos(){
		return w;
	}
	
	/**
	 * @param Net
	 * @return
	 */
	private double G(double Net){	//TODO asignar por parametro la funcion
		double result;
		
		result = Net + (-1) * theta;
		
		return result;
	}
	
	/**
	 * @param GNet
	 * @return
	 */
	private double F(double GNet){	//TODO asignar por parametro la funcion
		double result;
		
		if(GNet > 0){
			result = 1;
		}else{
			result = 0;
		}
		
		return result;
	}
	

	/**
	 * @param salidaDeseado
	 */
	public void entrenate(double salidaDeseado){
		double deltaW;
		// ajusta los pesos
		for (int i = 0; i < w.length; i++) {
			
			// Fórmula de Entrenamiento por Corrección de Error
			deltaW = (this.getY() - salidaDeseado) * x[i] * alfa;
			
			// ajuste del peso
			w[i] = w[i] - deltaW;
		}
		
	}
	
	/**
	 * @return
	 */
	public String getPesosString(){
		StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < w.length; i++) {
			buffer.append("w[").append(i).append("]=").append(w[i]);
		}
		return buffer.toString();
	}
}
