package ar.com.rna;
import java.util.Scanner;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Atan {

    public static void main(String[] args) {

        XYSeries series = new XYSeries("Atan (x) -> -10 to 0");

        // Introduccion de datos
        double c = Math.abs(Math.atan(-10));
        double esc; // escala %

        for (int i = 0; i < 11; i++) {
            esc = Math.atan(i - 10) * 100 / c;
            series.add(i * 10, esc + 100);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Escala aTangente", // Título
                "Número Entrada (x) ->", // Etiqueta Coordenada X
                "Número Salida  (y) ->", // Etiqueta Coordenada Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                false, 
                false,
                false
        );

        // Mostramos la grafica en pantalla
        ChartFrame frame = new ChartFrame("Ejemplo Grafica Lineal", chart);
        frame.pack();
        frame.setVisible(true);
        
        for (int i = 0; i < 4; i++) {
            System.out.println("\nIngrese número 1 al 100");
            Scanner entrada = new Scanner(System.in);
            double num = entrada.nextInt();

            esc = Math.atan((num / 10) - 10) * 100 / c;

            System.out.println("entrada = " + num);
            System.out.println("salida  = " + (esc + 100));
        }

    }

}
