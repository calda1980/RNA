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
				{9.3522, 1.4997},
				{6.2190, 8.2343},
				{9.0813, 7.6227},
				{0.7293, 5.8239},
				{1.1914, 5.2893},
				{0.6817, 5.5753},
				{7.0765, 7.7356},
				{7.8801, 4.5565},
				{8.5190, 0.2734},
				{5.8246, 4.5739},
				{6.5297, 7.1245},
				{6.0636, 3.5706},
				{6.9176, 1.0590},
				{6.2692, 8.6971},
				{4.5916, 4.3946},
				{2.4259, 3.2770},
				{7.6391, 5.2959},
				{9.1453, 0.6366},
				{4.6062, 4.1823},
				{8.2531, 1.9686},
				{4.6599, 3.0235},
				{2.5049, 8.8621},
				{8.7532, 2.6306},
				{7.0369, 2.0378},
				{7.1110, 9.2334}
			};

		double salidaDeseada[] = {
				-1,
				1,
				1,
				-1,
				-1,
				-1,
				1,
				-1,
				-1,
				-1,
				1,
				-1,
				-1,
				1,
				-1,
				-1,
				-1,
				-1,
				-1,
				-1,
				-1,
				-1,
				-1,
				-1,
				1
			};
		
		//-- patrones de prueba --
		double patronesPrueba[][]= {
				{1.9056, 0.4483},
				{2.0759, 1.8804},
				{1.1528, 3.2227},
				{1.2566, 2.7118},
				{2.1578, 7.8186},
				{5.5318, 9.1591},
				{5.5620, 1.5395},
				{1.5773, 9.9517},
				{0.0743, 5.9013},
				{4.1410, 6.1012},
				{2.0867, 0.8355},
				{7.4112, 9.4304},
				{0.7083, 4.0053},
				{8.3660, 9.6676},
				{0.7422, 4.6473},
				{0.2741, 5.1756},
				{7.1870, 5.9165},
				{9.8643, 5.3022},
				{5.9966, 0.6973},
				{3.5041, 3.2584},
				{5.5025, 1.1931},
				{4.4727, 3.9147},
				{8.9436, 5.4907},
				{6.9924, 1.0493},
				{3.8201, 3.0095}
			};
		/* Salidas reales de las pruebas
				-1
				-1
				-1
				-1
				-1
				1
				-1
				-1
				-1
				-1
				-1
				1
				-1
				1
				-1
				-1
				-1
				-1
				-1
				-1
				-1
				-1
				-1
				-1
				-1
	 
		 */
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
			
			printArray("Entrada ", U.x);
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
