package ar.com.misdosneuronas.core.v3;

public class NeuronaTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// crea la neruona indicando que tendra 2 entradas
		Neurona U = new Neurona(2);
		// asigna valores a sus 2 unicas entradas
		U.x[0] = 1;
		U.x[1] = 0;
		// hace los calculos correspondientes
		U.calcularNet();
		U.calcularActivacion();
		U.calcularSalida();
		// imprime por consola
		printEntradas(U);
		printPesos(U);
		printEntradaNeta(U);
		printValorActivacion(U);
		printSalida(U);
		
		//---------------------
		//-- AND
		double patrones[][]= {
					{1, 0},
					{1, 1},
					{0, 0},
					{0, 1}
				};
		double salidaDeseada[] = {
					0,
					1,
					0,
					0
				};
		
		// recorre los patrones
		int numeroPatron = 0;
	
		while(numeroPatron < patrones.length){
			U.x = patrones[numeroPatron];
			
			U.calcularNet();
			U.calcularActivacion();
			U.calcularSalida();
			
			printEntradas(U);
			printPesos(U);
			printEntradaNeta(U);
			printValorActivacion(U);
			printSalida(U);
			
			System.out.println("Salida Deseada: " + salidaDeseada[numeroPatron]);
			
			if(U.y == salidaDeseada[numeroPatron]){ // aprendio
				numeroPatron = numeroPatron + 1;	// va por el siguiente patron
				System.out.println("Patron aprendido.");
			}else{	// seguir aprendiendo
				System.out.println("Aprendiendo...");
				U.aprender(salidaDeseada[numeroPatron]);
			}
			
		}
		//-----------------
		for (int i = 0; i < patrones.length; i++) {
			U.x = patrones[i];
			
			U.calcularNet();
			U.calcularActivacion();
			U.calcularSalida();
			
			printArray("Entrada " + i, U.x);
			System.out.println(" -> " + U.y);
			
		}
	}
	
	//--------------- Impresion por consola ---------------//
	/**
	 * @param U
	 */
	private static void printEntradas(Neurona U){
		printArray("entradas", U.x);
	}
	/**
	 * @param U
	 */
	private static void printPesos(Neurona U){
		printArray("pesos", U.w);
	}
	
	/**
	 * @param U
	 */
	private static void printEntradaNeta(Neurona U){
		System.out.println("Net: " + U.Net);
	}
	
	/**
	 * @param U
	 */
	private static void printValorActivacion(Neurona U){
		System.out.println("Valor de Activacion: " + U.activacion);
	}
	
	/**
	 * @param U
	 */
	private static void printSalida(Neurona U){
		System.out.println("salida: " + U.y);
	}
	
	/**
	 * @param array
	 */
	private static void printArray(String nombre, double[] array){
		System.out.print(nombre + ": [");
		for (int i = 0; i < array.length-1; i++) {
			System.out.print(array[i] + ", ");
		}
		System.out.print(array[array.length-1]);
		System.out.println("]");		
	}
}
