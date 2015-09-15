package ar.com.rna;
import java.util.ArrayList;
import java.util.Random;

public class RedNeuronal2 {

    public static void main(String[] args) {

        int nNeuronas = 9;
        int nEnlaces = 50;

        ArrayList<Integer>[] S = new ArrayList[nNeuronas];
        ArrayList<Integer>[] E = new ArrayList[nNeuronas];

        AleatoriedadCondicionada2 ac = new AleatoriedadCondicionada2();
        Random r1 = new Random();        
        int nSalidas = 1;
        int neurona;

        //Crea red neuronal aleatoria
        for (int i = 0; i < S.length; i++) {
            S[i] = new ArrayList();
            E[i] = new ArrayList();

            //Salidas maxima por neurona            
            nSalidas = r1.nextInt(nEnlaces / nNeuronas) + 1;
            for (int j = 0; j < nSalidas; j++) {

                neurona = r1.nextInt(nNeuronas);

                //Aplicación de la Aleatoriedad Condicionada
                neurona = (int) Math.rint(ac.getSalida(nNeuronas, neurona));
                if (r1.nextBoolean()) {
                    //Conexión hacia delante
                    neurona = i + neurona;
                    if (neurona > nNeuronas) {
                        neurona = neurona - nNeuronas;
                    }
                } else {
                    //Conexión hacia atrás
                    neurona = i - neurona;
                    if (neurona < 0) {
                        neurona = neurona + nNeuronas;
                    }
                }
                //Evitar enlaces repetidos a la misma neurona
                if (S[i].contains(neurona) == false) {
                    S[i].add(neurona);
                }
            }
        }

        //Búsqueda enlaces de entrada de cada neurona
        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < E.length; j++) {
                if (S[i].contains(j)) {
                    E[j].add(i);
                }
            }
        }

        //Añadiendo Pesos (w) a los enlaces
        ArrayList<Double>[] w = new ArrayList[nNeuronas];
        int aux = 0;
        for (int i = 0; i < E.length; i++) {
            w[i] = new ArrayList();
            aux = E[i].size();
            for (int j = 0; j < aux; j++) {
                w[i].add(Math.rint(r1.nextDouble() * 10000) / 10000);
            }
        }

        //Cálculo de salidas (y)
        //Inicializando valores de salida
        double[] y = new double[nNeuronas];
        for (int i = 0; i < nNeuronas; i++) {
            y[i] = 1.0;
        }

        //Realización de los cálculos (y)
        double tmp = 0;
        for (int i = 0; i < nNeuronas; i++) {
            tmp = 0;
            for (int j = 0; j < E[i].size(); j++) {
                tmp += (y[E[i].get(j)] * w[i].get(j));
            }
            y[i] = Math.rint(Math.tanh(tmp) * 10000) / 10000;
        }

        //Mostrar enlaces de Entrada (E)
        System.out.println("\n* Enlaces entrada:");
        for (int i = 0; i < E.length; i++) {
            System.out.println("Enlaces entradas Neurona [" + i + "]: " + E[i].toString());
        }

        //Mostrar enlaces de Salidas (S)
        System.out.println("\n* Enlaces salida:");
        for (int i = 0; i < S.length; i++) {
            System.out.println("Enlaces salidas Neurona [" + i + "]: " + S[i].toString());
        }

        //Mostrar valor Pesos (w)
        System.out.println("\n* Pesos:");
        for (int i = 0; i < E.length; i++) {
            System.out.println("w[" + i + "]: " + w[i].toString());
        }

        //Mostrar Salidas calculadas (y):
        System.out.println("\n* Salidas:");
        for (int i = 0; i < nNeuronas; i++) {
            System.out.println("y[" + i + "] = " + y[i]);
        }

    }

}