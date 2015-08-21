package ar.com.misdosneuronas.core.v2;

public class FuncionTransferencia {
	
	public double calcular(double GNet){
		double result = 0;
		//TODO implementar
		
		if(GNet >= 0){
			result = 1;
		}else{
			result = -1;
		}
				
		return result;
	}
}
