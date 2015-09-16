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
//		double patrones[][]= {
//					{1, 0},
//					{1, 1},
//					{0, 0},
//					{0, 1}
//				};
		//-- AND
//		double salidaDeseada[] = {
//					0,
//					1,
//					0,
//					0
//				};
		
		//-- OR
//		double salidaDeseada[] = {
//				1,
//				1,
//				0,
//				1
//			};

		//----  Ejemlplo mas real
		
		//-- patrones de entrenamiento
		double patrones[][]= {
				{0.327, 7.4855},
				{6.4505, 9.6332},
				{6.2047, 8.9893},
				{2.8275, 6.9276},
				{5.634, 3.5734},
				{2.7716, 4.397},
				{6.3081, 9.034},
				{1.9555, 2.1077},
				{4.0764, 2.1918},
				{7.0321, 8.8069},
				{5.2385, 8.4124},
				{1.4607, 5.5413},
				{8.8504, 9.4517},
				{4.2137, 8.52},
				{3.7609, 8.4336},
				{4.6339, 6.6351},
				{8.3234, 3.8246},
				{5.5015, 7.1647},
				{3.8554, 0.3339},
				{1.5004, 1.3634},
				{4.8731, 9.8356},
				{9.0213, 3.3702},
				{2.7358, 3.998},
				{8.0527, 3.0137},
				{1.6941, 0.1531}
			};

		double salidaDeseada[] = {
				0,
				1,
				1,
				0,
				0,
				0,
				1,
				0,
				0,
				1,
				0,
				0,
				1,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0
			};
		
		//-- patrones de prueba --
		double patronesPrueba[][]= {
				{7.0312, 5.5573},
				{9.1694, 1.4101},
				{0.7363, 7.5605},
				{4.0451, 4.3407},
				{6.0674, 4.7834},
				{9.7094, 1.6381},
				{2.5346, 5.9599},
				{0.3784, 1.1007},
				{1.6659, 3.4697},
				{0.3719, 9.1327},
				{9.3127, 6.0554},
				{7.8402, 0.7947},
				{6.4291, 6.4433},
				{9.2916, 0.6667},
				{3.9861, 3.8253},
				{2.8691, 1.472},
				{9.4077, 8.6412},
				{3.1588, 9.5751},
				{7.7446, 2.8214},
				{0.558, 1.8099},
				{4.4126, 9.7658},
				{0.6564, 6.7893},
				{6.9083, 5.0563},
				{0.6516, 2.9176},
				{4.7789, 1.0161}
			};
		
		// recorre los patrones
		int numeroPatron = 0;
		int limite = 0;
		int iteraciones = 0;
//		while(numeroPatron < patrones.length){
		while(limite < patrones.length){
			
			System.out.println("Iteracion ----> " + iteraciones++);
			System.out.println("Patron Nro ----> " + numeroPatron);
			
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
				
				if(numeroPatron > limite){
					limite++;
					numeroPatron = 0;	// vuelve a entrenar los patrones anteriores
					System.out.println("----> Vuelvo al inicio !!! [" + limite + "]" );
				}
				
				System.out.println("Patron aprendido.");
			}else{	// seguir aprendiendo
				System.out.println("Aprendiendo...");
				U.aprender(salidaDeseada[numeroPatron]);
			}
			
			System.out.println("-------------------------------------------");
		}
		//----------------- Resultado del entrenamiento --------------//
		System.out.println("----------------- Resultado del entrenamiento --------------");
		for (int i = 0; i < patrones.length; i++) {
			U.x = patrones[i];
			
			U.calcularNet();
			U.calcularActivacion();
			U.calcularSalida();
			
			printArray("Entrada " + i, U.x);
			System.out.println(" -> " + U.y);
			
		}
		//----------------- Prueba de la neurona --------------//
		System.out.println("----------------- Prueba de la neurona --------------");
		for (int i = 0; i < patronesPrueba.length; i++) {
			U.x = patronesPrueba[i];
			
			U.calcularNet();
			U.calcularActivacion();
			U.calcularSalida();
			
			printArray("Entrada: ", U.x);
			System.out.println(" -> " + U.y);
			
		}
	}
	
	//--------------- Impresion por consola ---------------//
	/**
	 * @param U
	 */
	private static void printEntradas(Neurona U){
		printArray("entradas", U.x);
		System.out.println();
	}
	/**
	 * @param U
	 */
	private static void printPesos(Neurona U){
		printArray("pesos", U.w);
		System.out.println();
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
		System.out.print("]");		
	}
}
