package ar.com.misdosneuronas.core.v3;

/**
 * @author Vincent
 * 
 * Funcion Umbral
 * 
 */
public class FuncionActivacion {
	private double theta;
	
	/**
	 * @param theta
	 */
	public FuncionActivacion(double theta){
		this.theta = theta;
	}
	 
	/**
	 * @param Net
	 * @return
	 */
	public double calcular(double Net){
		double result = 0;
		
		result = Net - theta;
		
		return result;
	}
}
