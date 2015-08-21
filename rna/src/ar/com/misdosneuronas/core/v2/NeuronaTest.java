package ar.com.misdosneuronas.core.v2;

public class NeuronaTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// crea la neruona indicando que tendra 2 entradas
		Neurona U = new Neurona(2);
		// asigna valores a sus 2 entradas
		U.x[0] = 0;
		U.x[1] = 1;
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
