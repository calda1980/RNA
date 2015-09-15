package ar.com.rna;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Grafica {

    public Grafica(JPanel jPanel1, XYSeries[] series, int mostrar, boolean x) {

        XYSeriesCollection dataset = new XYSeriesCollection();

        //Rango maximo y mínimo del eje Y de la grafica (1 a -1)
        series[0].add(0, 0.999);
        series[0].add(0, -0.999);

        if (x) {
            for (int i = 0; i < mostrar; i++) {
                dataset.addSeries(series[i]);
            }
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Gráfica Red Neuronal",
                "Ciclos ->",
                "Amplitud Señal->",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );
        //Mostramos la grafica dentro del jPanel1
        ChartPanel panel = new ChartPanel(chart);
        jPanel1.removeAll();
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(panel);
        jPanel1.validate();
    }

}