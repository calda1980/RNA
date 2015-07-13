//Perceptron multicapa
package ar.com.rna.na;

import java.util.Random;
import java.util.Scanner;

public class PerceptronMultiCapa {

    public static void main(String[] args) {
        //Introduccion manual de las entradas x1, x2.
        System.out.println("Introduce Entrada 1 (X1): ");
        Scanner leerX1 = new Scanner(System.in);
        double x1 = Double.parseDouble(leerX1.next());
        System.out.println("Introduce Entrada 2 (X2): ");
        Scanner leerX2 = new Scanner(System.in);
        double x2 = Double.parseDouble(leerX2.next());
        //Inicializar pesos con valores aleatorios.
        double w1 = new Random().nextDouble();
        double w2 = new Random().nextDouble();
        double w3 = new Random().nextDouble();
        double w4 = new Random().nextDouble();
        double w5 = new Random().nextDouble();
        double w6 = new Random().nextDouble();
        //Pesos de la constante ? (ayuda en la precisión).
        double u1 = new Random().nextDouble();
        double u2 = new Random().nextDouble();
        double u3 = new Random().nextDouble();
        //Salidas primera capa y ultima (y3)
        double y1 = Math.tanh((x1 * w1) + (x2 * w3) - u1);
        double y2 = Math.tanh((x1 * w2) + (x2 * w4) - u2);
        double y3 = Math.tanh((y1 * w5) + (y2 * w6) - u3);
        //Mostrar resultados
        System.out.println("Salida (y3) = " + y3);
    }
    
}