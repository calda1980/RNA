package es.usal.avellano.lalonso.rna.redes;

public class Juego
{
  private Ejemplo[] ejemplos;

  public Juego()
  {
    this.ejemplos = null;
  }

  public Juego(Ejemplo[] ejemplos)
  {
    this.ejemplos = new Ejemplo[ejemplos.length];
    for (int i = 0; i < ejemplos.length; i++) this.ejemplos[i] = ejemplos[i];
  }

  public Ejemplo getEjemplo(int indice)
  {
    return this.ejemplos[indice];
  }

  public Ejemplo[] getEjemplos()
  {
    return this.ejemplos;
  }
}