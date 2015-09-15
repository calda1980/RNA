package ar.com.rna;
import java.util.ArrayList;
import java.util.Random;

public class RedNeuronal1 {

    public static void main(String[] args) {

        int nNeuronas = 50;
        int nEnlaces = 500;

        ArrayList<Integer>[] neuroS = new ArrayList[nNeuronas];
        ArrayList<Integer>[] neuroE = new ArrayList[nNeuronas];

        AleatoriedadCondicionada1 ac = new AleatoriedadCondicionada1();
        Random r1 = new Random();
        neuroS[0] = new ArrayList();
        int nSalidas;
        int neurona;

        //Crea red neuronal aleatoria
        for (int i = 0; i < neuroS.length; i++) {
            neuroS[i] = new ArrayList();
            neuroE[i] = new ArrayList();
            nSalidas = r1.nextInt(nEnlaces / nNeuronas) + 1;
            for (int j = 0; j < nSalidas; j++) {

                neurona = r1.nextInt(nNeuronas);

                //Aplicación de la Aleatoriedad Condicionada
                neurona = (int) Math.rint(ac.getSalida(nNeuronas, neurona));
                if (r1.nextBoolean()) {
                    //Conexion hacia delante
                    neurona = i + neurona;
                    if (neurona > nNeuronas) {
                        neurona = neurona - nNeuronas;
                    }
                } else {
                    //Conexion hacia atrás
                    neurona = i - neurona;
                    if (neurona < 0) {
                        neurona = neurona + nNeuronas;
                    }
                }
                //evita repeticiones
                if (neuroS[i].contains(neurona) == false) { 
                    neuroS[i].add(neurona); //salida
                }
            }
        }

        //Traspaso entradas a partir de las salidas
        for (int i = 0; i < neuroS.length; i++) {
            for (int j = 0; j < neuroE.length; j++) {
                if (neuroS[i].contains(j)) {
                    neuroE[j].add(i);
                }
            }
        }

        //mostrar resultados
        for (int i = 0; i < neuroS.length; i++) {
            System.out.println("neuroS[" + i + "]: " + neuroS[i].toString());
        }
        System.out.println("\n");
        for (int i = 0; i < neuroE.length; i++) {
            System.out.println("neuroE[" + i + "]: " + neuroE[i].toString());
        }

        //Recuento número de enlaces usados
        int nSinapsis = 0;
        for (int i = 0; i < neuroS.length; i++) {
            nSinapsis += neuroS[i].size();
        }
        System.out.println("\nNúmero de enlaces: " + nSinapsis);

    }

}