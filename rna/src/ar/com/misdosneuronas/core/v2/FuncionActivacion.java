package ar.com.misdosneuronas.core.v2;

/**
 * @author Vincent
 * 
 * Funcion Umbral
 * 
 */
public class FuncionActivacion {
	private double theta;
	
	public FuncionActivacion(){
		this.theta = 0.3;
	}
	
	public FuncionActivacion(double theta){
		this.theta = theta;
	}
	
	public double calcular(double Net){
		double result = 0;
		
		result = Net - theta;
		
		return result;
	}
}
