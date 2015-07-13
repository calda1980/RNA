package ar.com.rna;
public class AleatoriedadCondicionada2 {    
    public double getSalida(double x, double num) {
        double c = Math.atan(10);
        return ((Math.atan(((num*100/x)/10)-10)*100/c)+100)*x/100;
    }
}