package ar.com.misdosneuronas.core.v3;

/**
 * Neurona Artificial
 *
 */
public class Neurona {
	/**
	 * Valores de entrada
	 */
	public double[] x;
	/**
	 * Pesos de las entradas
	 */
	public double[] w;
	/**
	 * Entrada Neta
	 */
	public double Net;
	/**
	 * Estado de activacion
	 */
	public double activacion;
	/**
	 * Valor de salida
	 */
	public double y;
	
	/**
	 * Funcion de Activacion
	 */
	private FuncionActivacion G;
	
	/**
	 * Funcion de Transferencia
	 */
	private FuncionTransferencia F;
	
	/**
	 * 
	 */
	private ReglaAprendizaje regla;
	
	/**
	 * Constructor de la clase
	 */
	public Neurona(int cantidadEntradas){
		// dimensiona el vector de entradas con el tamaño de entradas
		x = new double[cantidadEntradas];
		// dimensiona el vector de pesos asociados a las entradas
		w = new double[cantidadEntradas];
		// inicializa los pesos en con 0.5
		for (int i = 0; i < w.length; i++) {
			w[i] = 0.5d;
		}
		// asigna la Funcion de Activacion Umbral con un umbral de 0.2
		G = new FuncionActivacion(0.2);
		// asigna la Funcion de Transferencia Escalon Unitario
		F = new FuncionTransferencia();
		// asigna la Regla de Aprendizaje
		regla = new ReglaAprendizaje(0.3, this);
	}
	//--------------- Caclulos ---------------//
	/**
	 * Calcula la entrada neta de la neurona
	 */
	public void calcularNet(){
		double result = 0;
		
		for (int i = 0; i < x.length; i++) {
			result = result + ( x[i] * w[i] );
		}
		
		Net = result;
	}
	/**
	 * 
	 */
	public void calcularActivacion(){
		activacion = G(Net);
	}
	/**
	 * Calcula la salida de la neurona
	 */
	public void calcularSalida(){
		y = F(G(Net));
	}
	
	//--------------- Funciones ---------------//
	/**
	 * Obtiene el Valor de Activacion de la neurona
	 * @return
	 */
	private double G(double Net){
		return G.calcular(Net);
	}
	/**
	 * Obtiene la Salida de la neurona
	 * @return
	 */
	private double F(double GNet){
		return F.calcular(GNet);
	}
	
	/**
	 * Indica a la neurona que aprenda de la salida deseada
	 * @param salidaDeseada
	 */
	public void aprender(double salidaDeseada){
		regla.entrenar(salidaDeseada);
	}
}
