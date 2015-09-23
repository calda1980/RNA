package ar.com.misdosneuronas.core.v3;

/**
 * @author Vincent
 *  
 * Funcion Escalon Unitario
 *
 */
public class FuncionTransferencia {
	
	public double calcular(double GNet){
		double result = 0;
		
		if(GNet > 0){
			result = 1;
		}else{	// GNext <= 0
			result = -1;
		}
		
		return result;
	}
}
