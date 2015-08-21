package es.usal.avellano.lalonso.rna.redes;

public abstract class RedNeuronal
{
  protected String codigo;
  protected String nombre;
  protected Juego[] ensayo;
  protected Juego[] prueba;
  protected double mu;
  protected double CAO;
  protected double CAS;

  public RedNeuronal()
  {
    this.codigo = null;
    this.nombre = null;
    this.ensayo = null;
    this.prueba = null;
  }

  public RedNeuronal(String codigo, String nombre)
  {
    this.nombre = nombre;
    this.codigo = codigo;
  }

  public RedNeuronal(String codigo, String nombre, double mu, double CAO, double CAS)
  {
    this.nombre = nombre;
    this.codigo = codigo;
    this.mu = mu;
    this.CAO = CAO;
    this.CAS = CAS;
  }

  public RedNeuronal(String codigo, String nombre, double mu)
  {
    this.nombre = nombre;
    this.codigo = codigo;
    this.mu = mu;
  }

  public RedNeuronal(String codigo, String nombre, Juego[] ensayo, Juego[] prueba, double mu)
  {
    this.nombre = nombre;
    this.codigo = codigo;
    this.ensayo = new Juego[ensayo.length];
    this.prueba = new Juego[prueba.length];

    for (int i = 0; i < ensayo.length; i++) this.ensayo[i] = ensayo[i];
    for (int i = 0; i < prueba.length; i++) this.prueba[i] = prueba[i];

    this.mu = mu;
    this.CAO = this.CAO;
    this.CAS = this.CAS;
  }

  public RedNeuronal(String codigo, String nombre, Juego[] ensayo, Juego[] prueba, double mu, double CAO, double CAS)
  {
    this.nombre = nombre;
    this.codigo = codigo;
    this.ensayo = new Juego[ensayo.length];
    this.prueba = new Juego[prueba.length];

    for (int i = 0; i < ensayo.length; i++) this.ensayo[i] = ensayo[i];
    for (int i = 0; i < prueba.length; i++) this.prueba[i] = prueba[i];

    this.mu = mu;
    this.CAO = CAO;
    this.CAS = CAS;
  }

  public String getNombre()
  {
    return this.nombre;
  }

  public String getCodigo()
  {
    return this.codigo;
  }

  public double getMu()
  {
    return this.mu;
  }

  public double getCAO()
  {
    return this.CAO;
  }

  public double getCAS()
  {
    return this.CAS;
  }

  public Juego getPrueba(int indice)
  {
    return this.prueba[indice];
  }

  public Juego getEnsayo(int indice)
  {
    return this.ensayo[indice];
  }

  public Juego[] getPruebas()
  {
    return this.prueba;
  }

  public Juego[] getEnsayos()
  {
    return this.ensayo;
  }
}