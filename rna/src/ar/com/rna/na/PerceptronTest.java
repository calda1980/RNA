package ar.com.rna.na;

public class PerceptronTest {

	public static void main(String[] args) {
		// OR
		double entradas[][] = {
				{1, 0},
				{0, 1},
				{0, 0},
				{1, 1}
			};
		
		double salidas[] = {
				1,
				1,
				0,
				1
			};
		
//		// AND		
//		double entradas[][] = {
//				{1, 0},
//				{0, 1},
//				{0, 0},
//				{1, 1}
//		};
//		
//		double salidas[] = {
//				0,
//				0,
//				0,
//				1
//		};
		
		double x[];
		double y;
		
		// AND
		Perceptron perceptron = new Perceptron(2, 0.2, 0.3);
		
		long iteracion = 1;
		int patronActual = 0;
		
		while(patronActual < entradas.length){
			
			x = entradas[patronActual];
			y = salidas[patronActual];

			perceptron.setEntrada(x);
			
			if(perceptron.getY() != y){
				perceptron.entrenate(y);
				
				System.out.println("Nuevos Pesos: " + perceptron.getPesosString());
				patronActual = 0;
			}else{
				patronActual++;
			}
			
			System.out.println("Iteacion " + iteracion);
			iteracion++;
		}
		
		
		System.out.println("Aprendio en " + iteracion + " iteraciones");
		System.out.println("Los pesos son: ");
		double pesos[] = perceptron.getPesos();
		for (int i = 0; i < pesos.length; i++) {
			System.out.println("w[" + i + "]=" + pesos[i]);
		}

	}

}
