package ar.com.rna.na;

import java.util.Random;

public class Perceptron {

    public static void main(String[] args) {

        //Valores Entradas
        double x1 = 1.4;
        double x2 = -0.33;

        //Valores Pesos (aleatorios)
        double w1 = new Random().nextDouble();
        double w2 = new Random().nextDouble();

        Neurona n = new Neurona(x1, x2, w1, w2);

        System.out.println("Entrada 1 (x1): " + x1);
        System.out.println("Entrada 2 (x2): " + x2);
        System.out.println("Salida 1 (y1) = " + n.getY1());
        
    }

}