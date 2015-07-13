//Aprendizaje puerta logica AND
package ar.com.rna.na;

import java.util.Random;
import java.util.Scanner;

public class NeuronaAND {

    public static void main(String[] args) {
        //Tabla de la verdad puerta AND (X1,X2,Y1)
        int[][] tv = {
        		{ 1,  1,  1}, 
        		{ 1, -1, -1}, 
        		{-1,  1, -1}, 
        		{-1, -1, -1}
        	};
        
        double w1 = new Random().nextDouble();
        double w2 = new Random().nextDouble();
        double u = -0.4;

        double y = 0;
        final double E = 0.6;//Factor de aprendizaje  

        System.out.println("Iniciando fase de aprendizaje puerta logica AND...");
        int i = 0;
        int cont = 1;
        while (i < tv.length && cont < 10000) {
            y = Math.tanh((tv[i][0] * w1) + (tv[i][1] * w2) + (-1 * u));
            y = (y >= u) ? 1 : -1;            
            if (y == tv[i][2]) {
                i++;
            } else {
                //Ajuste de pesos
                w1 = w1 + 2 * E * tv[i][2] * tv[i][0];
                w2 = w2 + 2 * E * tv[i][2] * tv[i][1];
                u = u + 2 * E * tv[i][2] * (-1);
                cont++;
                i = 0;
            }
        }

        if (cont <= 9999) {
            System.out.println("Fase de aprendizaje terminado con exito ");
            
            System.out.println("\nIntroduce Entrada 1 (X1): ");
            Scanner leerX1 = new Scanner(System.in);
            double x1 = Double.parseDouble(leerX1.next());

            System.out.println("Introduce Entrada 2 (X2): ");
            Scanner leerX2 = new Scanner(System.in);
            double x2 = Double.parseDouble(leerX2.next());

            y = Math.tanh((x1 * w1) + (x2 * w2) + (-1 * u));
            y = (y >= u) ? 1 : -1;

            System.out.println("\nSalida: " + y);
        } else {
            System.out.println("\nFase de aprendizaje ha fallado\n");
        }

    }
}