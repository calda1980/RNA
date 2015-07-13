package ar.com.rna;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import org.jfree.data.xy.XYSeries;

public class RedNeuronalG extends javax.swing.JFrame {

    int nNeuronas = 50;
    int nEnlaces = 600;
    int nVer = 5;
    int FrecuenciaRefresco;
    int ciclos = 1;

    ArrayList<Integer>[] E;
    ArrayList<Integer>[] S;
    ArrayList<Double>[] w;
    double[] y;
    Grafica g;
    XYSeries[] series;
    Timer timer;

    public RedNeuronalG() {
        initComponents();
        restablecer();
        actualizar();
    }

    private void initComponents() { 
    	//TODO
    }// </editor-fold>    
   
    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {                                             
        try {
            timer.stop();
        } catch (Exception ex) { }

        restablecer();
        jPanelGrafica1.removeAll();
        jPanelGrafica1.repaint();
        g = new Grafica(jPanelGrafica1, series, nVer, false);
    }                                             

    private void jButtonCrearRedActionPerformed(java.awt.event.ActionEvent evt) {                                                
        if (jButtonPlay.isEnabled() == false && jButtonPausa.isEnabled() == false) {
            jButtonPlay.setEnabled(true);
            jButtonPausa.setEnabled(true);
            jButtonReset.setEnabled(true);
        }
        actualizar();
        crearRed();
    }                                               


    private void jButtonPlayActionPerformed(java.awt.event.ActionEvent evt) {                                            
        if (jButtonPlay.isEnabled()) {
            jButtonPlay.setEnabled(false);
            jButtonPausa.setEnabled(true);
        }
        actualizar();
        timer = new Timer(FrecuenciaRefresco, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Calcular();
            }
        });
        timer.start();
    }                                           

    private void jButtonPausaActionPerformed(java.awt.event.ActionEvent evt) {                                             
        if (jButtonPausa.isEnabled()) {
            jButtonPausa.setEnabled(false);
            jButtonPlay.setEnabled(true);
        }
        timer.stop();
    }                                            

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RedNeuronalG().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButtonCrearRed;
    private javax.swing.JButton jButtonPausa;
    private javax.swing.JButton jButtonPlay;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JLabel jLabelEnlaces;
    private javax.swing.JLabel jLabelFrecuenciaRefresco;
    private javax.swing.JLabel jLabelNeuronas;
    private javax.swing.JLabel jLabelNeuronasMostrar;
    private javax.swing.JLabel jLabelTiempo;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelGrafica1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSpinner jSpinnerCiclos;
    private javax.swing.JSpinner jSpinnerFrecuenciaRefresco;
    private javax.swing.JSpinner jSpinnerNeuronas;
    private javax.swing.JSpinner jSpinnerNeuronasMostrar;
    private javax.swing.JSpinner jSpinnerNumeroSinapsis;
    // End of variables declaration                   

    private void actualizar() {
        nNeuronas = (int) jSpinnerNeuronas.getValue();
        nEnlaces = (int) jSpinnerNumeroSinapsis.getValue();
        nVer = (int) jSpinnerNeuronasMostrar.getValue();
        ciclos = (int) jSpinnerCiclos.getValue();
        FrecuenciaRefresco = (int) jSpinnerFrecuenciaRefresco.getValue();
        // Control rango maximos minimos jSpinners
        if (nNeuronas <= 1) {
            jSpinnerNeuronas.setValue(1);
        } else if (nEnlaces <= 1) {
            jSpinnerNumeroSinapsis.setValue(1);
        } else if (ciclos <= 0) {
            jSpinnerCiclos.setValue(1);
        } else if (nVer >= nNeuronas){
            jSpinnerNeuronasMostrar.setValue(nNeuronas);
            nVer = nNeuronas;
        }
    }

    private void restablecer() {
        //Configuracion inicial por defecto
        jButtonPlay.setEnabled(false);
        jButtonPausa.setEnabled(false);
        jSpinnerNeuronas.setValue(50);
        jSpinnerNumeroSinapsis.setValue(600);
        jSpinnerCiclos.setValue(100);
        jSpinnerNeuronasMostrar.setValue(5);
    }

    private void crearRed() {
        S = new ArrayList[nNeuronas];
        E = new ArrayList[nNeuronas];
        AleatoriedadCondicionadaG ac = new AleatoriedadCondicionadaG();
        Random r1 = new Random();
        int nSalidas = 1; //como minimo debe tener una salida
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
                neurona = (int) Math.rint(ac.getSalida((double) nNeuronas, neurona));
                if (r1.nextBoolean()) {
                    // Conexion hacia delante
                    neurona = i + neurona;
                    if (neurona > nNeuronas) {
                        neurona = neurona - nNeuronas;
                    }
                } else {
                    // Conexion hacia atras
                    neurona = i - neurona;
                    if (neurona < 0) {
                        neurona = neurona + nNeuronas;
                    }
                }
                //evita repeticiones de enlaces a la misma neurona
                if (S[i].contains(neurona) == false) {
                    S[i].add(neurona);
                }
            }
        }
        //Busqueda enlaces de entrada de cada neurona.
        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < E.length; j++) {
                if (S[i].contains(j)) {
                    E[j].add(i);
                }
            }
        }
        //Añadiendo Pesos (w) a los enlaces
        w = new ArrayList[nNeuronas];
        int aux = 0;
        for (int i = 0; i < E.length; i++) {
            w[i] = new ArrayList();
            aux = E[i].size();
            for (int j = 0; j < aux; j++) {
                //Rango bipolar entre -1 y 1
                int tmp = r1.nextBoolean() ? 1 : -1;
                w[i].add(r1.nextDouble() * tmp);
            }
        }
        y = new double[nNeuronas];
        //Inicializando valores de salida (y)
        for (int i = 0; i < nNeuronas; i++) {
            y[i] = 1;
        }
        Calcular();
    }

    private void Calcular() {
        series = new XYSeries[nNeuronas];
        //Inicializando array de series
        for (int i = 0; i < nNeuronas; i++) {
            series[i] = new XYSeries("" + i);
        }
        int cont = 0;
        while (cont < (int) jSpinnerCiclos.getValue()) {
            for (int i = 0; i < nNeuronas; i++) {
                double tmp = 0;
                for (int j = 0; j < E[i].size(); j++) {
                    tmp += (y[E[i].get(j)] * w[i].get(j));
                }
                y[i] = Math.tanh(tmp);
                series[i].add(cont, y[i]);
            }
            cont++;
        }
        nVer = (int)jSpinnerNeuronasMostrar.getValue();
        nVer = nVer >= nNeuronas ? nNeuronas:nVer;
        jSpinnerNeuronasMostrar.setValue(nVer);
        g = new Grafica(jPanelGrafica1, series, nVer, true);
    }

}
