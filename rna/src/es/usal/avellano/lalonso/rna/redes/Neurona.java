package es.usal.avellano.lalonso.rna.redes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Neurona
{
  private double entrada;
  private double salida;
  private double k;

  public Neurona(double entrada, double salida)
  {
    this.entrada = entrada;
    this.salida = salida;
    this.k = -0.5D;
  }

  public Neurona(double entrada, double salida, double k)
  {
    this.entrada = entrada;
    this.salida = salida;
    this.k = k;
  }

  public Neurona(ResultSet rs)
    throws SQLException
  {
    this.entrada = new Double(rs.getString("entrada")).doubleValue();
    this.salida = new Double(rs.getString("salida")).doubleValue();
  }

  public double getEntrada()
  {
    return this.entrada;
  }

  public double getSalida()
  {
    return this.salida;
  }

  public void setEntrada(double in)
  {
    this.entrada = in;
  }

  public void setSalida(double out)
  {
    this.salida = out;
  }

  public void activar()
  {
    this.salida = (Math.pow(1.0D + Math.exp(-this.entrada), -1.0D) + this.k);
  }

  public void activarSigno()
  {
    if (this.entrada >= 0.0D) this.salida = 1.0D; else
      this.salida = -1.0D;
  }

  public double activarDerivada()
  {
    return Math.pow(1.0D + Math.exp(-this.entrada), -1.0D) * (1.0D - Math.pow(1.0D + Math.exp(-this.entrada), -1.0D));
  }
}