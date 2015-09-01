package es.usal.avellano.lalonso.rna.applets.ort;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.LinkedList;

import es.usal.avellano.lalonso.rna.redes.Capa;
import es.usal.avellano.lalonso.rna.redes.Ejemplo;
import es.usal.avellano.lalonso.rna.redes.Juego;
import es.usal.avellano.lalonso.rna.redes.MLP;
import es.usal.avellano.lalonso.rna.redes.Neurona;
import es.usal.avellano.lalonso.rna.utilidades.Utilidad;

public class AppletORT extends Applet implements Runnable, ActionListener, TextListener, MouseMotionListener, MouseListener {
	private MLP multi;
	private Point puntoAnterior = null;

	private final int pixelsX = 7;

	private final int pixelsY = 8;

	double[][] ent = new double[7][8];

	LinkedList puntos = new LinkedList();

	Ejemplo[] ejs = new Ejemplo[26];

	Ejemplo[] pru = new Ejemplo[26];

	double[] e = new double[56];

	double[] s = new double[26];

	Juego[] jejs = new Juego[1];

	Juego[] jpru = new Juego[1];
	int alto;
	int ancho;
	double anchoEsc;
	double altoEsc;
	char[] letras = new char[26];

	Point[] grafico = null;

	int numIteraciones = 0;

	boolean continuar = false;

	private Thread runner = null;
	private Button button1;
	private Button button2;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button8;
	private Canvas canvas1;
	private Canvas canvas2;
	private Checkbox checkbox1;
	private Choice choice1;
	private Label label1;
	private Label label10;
	private Label label11;
	private Label label12;
	private Label label13;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;
	private Label label8;
	private Label label9;
	private Label lable1;
	private TextField textField1;
	private TextField textField2;
	private TextField textField4;
	private TextField textField5;
	private TextField textField6;
	private TextField textField7;
	private TextField textField8;

	public void init() {
		initComponents();

		this.choice1.add("Lotes");
		this.choice1.add("Individual");

		this.letras = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z' };

		this.alto = this.canvas1.getHeight();
		this.ancho = this.canvas1.getWidth();

		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 8; j++)
				this.ent[i][j] = -0.5D;

		this.anchoEsc = (this.ancho / 7);
		this.altoEsc = (this.alto / 8);

		leerEjemplos("ejemplosORT.dat");

		for (int i = 0; i < 26; i++) {
			this.pru[i] = new Ejemplo(this.ejs[i].getEntradas(),
					this.ejs[i].getSalidas());
		}
		this.jpru = new Juego[1];
		this.jpru[0] = new Juego(this.pru);
	}

	private void initComponents()
  {
    this.lable1 = new Label();
    this.label1 = new Label();
    this.button1 = new Button();
    this.label3 = new Label();
    this.label4 = new Label();
    this.label5 = new Label();
    this.textField4 = new TextField();
    this.label6 = new Label();
    this.label7 = new Label();
    this.label8 = new Label();
    this.textField5 = new TextField();
    this.textField6 = new TextField();
    this.label9 = new Label();
    this.textField7 = new TextField();
    this.label10 = new Label();
    this.textField8 = new TextField();
    this.button2 = new Button();
    this.label11 = new Label();
    this.button4 = new Button();
    this.canvas1 = new Canvas();
    this.canvas2 = new Canvas();
    this.button5 = new Button();
    this.label2 = new Label();
    this.choice1 = new Choice();
    this.button6 = new Button();
    this.label12 = new Label();
    this.textField1 = new TextField();
    this.checkbox1 = new Checkbox();
    this.button8 = new Button();
    this.label13 = new Label();
    this.textField2 = new TextField();

    setLayout(null);

    setBackground(new Color(192, 192, 192));
    this.lable1.setFont(new Font("Dialog", 1, 14));
    this.lable1.setForeground(new Color(100, 100, 150));
    this.lable1.setText("EJECUCIÓN");
    add(this.lable1);
    this.lable1.setBounds(20, 20, 110, 23);

    this.label1.setForeground(new Color(0, 0, 0));
    this.label1.setText("Entrada");
    add(this.label1);
    this.label1.setBounds(20, 50, 60, 20);

    this.button1.setLabel("Ejecutar");
//    this.button1.addActionListener(new AppletORT.1(this));
    this.button1.addActionListener(this);

    add(this.button1);
    this.button1.setBounds(110, 90, 70, 24);

    this.label3.setText("Salida");
    add(this.label3);
    this.label3.setBounds(200, 50, 50, 20);

    this.label4.setFont(new Font("Dialog", 1, 14));
    this.label4.setForeground(new Color(100, 100, 150));
    this.label4.setText("ARQUITECTURA");
    add(this.label4);
    this.label4.setBounds(30, 210, 120, 23);

    this.label5.setText("Nº de neuronas ocultas: ");
    add(this.label5);
    this.label5.setBounds(30, 240, 140, 20);

    this.textField4.setText("56");
    this.textField4.addTextListener(this);

    add(this.textField4);
    this.textField4.setBounds(170, 240, 30, 20);

    this.label6.setFont(new Font("Dialog", 1, 14));
    this.label6.setForeground(new Color(100, 100, 150));
    this.label6.setText("ENTRENAMIENTO");
    add(this.label6);
    this.label6.setBounds(310, 20, 140, 23);

    this.label7.setText("Momento");
    add(this.label7);
    this.label7.setBounds(310, 50, 70, 20);

    this.label8.setText("Factor de Aprendizaje");
    add(this.label8);
    this.label8.setBounds(310, 70, 122, 20);

    this.textField5.setText("0.1");
    add(this.textField5);
    this.textField5.setBounds(480, 50, 30, 20);

    this.textField6.setText("0.1");
    add(this.textField6);
    this.textField6.setBounds(480, 70, 30, 20);

    this.label9.setText("Nº de iteraciones");
    add(this.label9);
    this.label9.setBounds(310, 100, 110, 20);

    this.textField7.setText("300");
    add(this.textField7);
    this.textField7.setBounds(480, 100, 40, 20);

    this.label10.setText("Error máximo permitido");
    add(this.label10);
    this.label10.setBounds(310, 120, 133, 20);

    this.textField8.setText("0.1");
    add(this.textField8);
    this.textField8.setBounds(480, 120, 40, 20);

    this.button2.setLabel("Comenzar");
    this.button2.addActionListener(this);

    add(this.button2);
    this.button2.setBounds(530, 70, 70, 24);

    this.label11.setFont(new Font("Dialog", 1, 12));
    this.label11.setForeground(new Color(100, 100, 150));
    this.label11.setText("Gráfica de Error");
    add(this.label11);
    this.label11.setBounds(310, 190, 150, 20);

    this.button4.setLabel("Cargar Pesos");
    this.button4.addActionListener(this);

    add(this.button4);
    this.button4.setBounds(90, 330, 110, 24);

    this.canvas1.setBackground(new Color(255, 255, 255));
    this.canvas1.addMouseMotionListener(this);

    this.canvas1.addMouseListener(this);

    add(this.canvas1);
    this.canvas1.setBounds(20, 80, 70, 80);

    this.canvas2.setBackground(new Color(128, 128, 128));
    add(this.canvas2);
    this.canvas2.setBounds(200, 80, 70, 80);

    this.button5.setLabel("Borrar");
    this.button5.addActionListener(this);

    add(this.button5);
    this.button5.setBounds(110, 130, 70, 24);

    this.label2.setText("Tipo de Aprendizaje:");
    add(this.label2);
    this.label2.setBounds(30, 270, 120, 20);

    add(this.choice1);
    this.choice1.setBounds(170, 270, 100, 20);

    this.button6.setLabel("Pausar");
    this.button6.addActionListener(this);

    add(this.button6);
    this.button6.setBounds(530, 100, 70, 24);

    this.label12.setText("Error máximo representado");
    add(this.label12);
    this.label12.setBounds(310, 140, 160, 20);

    this.textField1.setText("1");
    add(this.textField1);
    this.textField1.setBounds(480, 140, 40, 20);

    this.checkbox1.setLabel("Mostrar pixelación");
    add(this.checkbox1);
    this.checkbox1.setBounds(20, 170, 125, 20);

    this.button8.setLabel("Reiniciar");
    this.button8.addActionListener(this);

    add(this.button8);
    this.button8.setBounds(530, 130, 70, 24);

    this.label13.setText("Error actual");
    add(this.label13);
    this.label13.setBounds(310, 160, 100, 20);

    this.textField2.setEditable(false);
    add(this.textField2);
    this.textField2.setBounds(480, 160, 40, 20);
  }

	private void textField4TextValueChanged(TextEvent evt) {
		if (this.textField4.getText().equals("56"))
			this.button4.setEnabled(true);
		else
			this.button4.setEnabled(false);
	}

	private void button8ActionPerformed(ActionEvent evt) {
		this.multi.reiniciarPesos();
		stop();
		this.button2.setLabel("Comenzar");
		this.textField1.setEnabled(true);
		this.textField7.setEnabled(true);
		this.textField2.setText("");
		this.continuar = false;
	}

	private void button6ActionPerformed(ActionEvent evt) {
		this.continuar = true;
		this.button2.setLabel("Continuar");
		stop();
	}

	private void button5ActionPerformed(ActionEvent evt) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 8; j++)
				this.ent[i][j] = -0.5D;
		}
		this.puntos = new LinkedList();

		Graphics g = this.canvas1.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, this.ancho, this.alto);
		g.setColor(Color.black);

		g = this.canvas2.getGraphics();
		g.setColor(Color.gray);
		g.fillRect(0, 0, this.ancho, this.alto);
		g.setColor(Color.black);
	}

	private void pararPintar(MouseEvent evt) {
		this.puntoAnterior = null;
	}

	private void pintar(MouseEvent evt) {
		int x = 0;
		int y = 0;

		Graphics g = this.canvas1.getGraphics();

		if (this.puntoAnterior == null) {
			this.puntoAnterior = evt.getPoint();
			this.puntos.add(this.puntoAnterior);
		} else {
			g.drawLine(new Double(this.puntoAnterior.getX()).intValue(),
					new Double(this.puntoAnterior.getY()).intValue(),
					evt.getX(), evt.getY());
			this.puntoAnterior = evt.getPoint();
			this.puntos.add(this.puntoAnterior);
		}
	}

	private void button4ActionPerformed(ActionEvent evt) {
		try {
			this.button1.setEnabled(false);
			this.button2.setEnabled(false);
			this.button4.setEnabled(false);
			this.button5.setEnabled(false);
			this.button6.setEnabled(false);
			this.textField1.setEnabled(false);
			this.textField4.setEnabled(false);
			this.textField5.setEnabled(false);
			this.textField6.setEnabled(false);
			this.textField7.setEnabled(false);
			this.textField8.setEnabled(false);
			this.choice1.setEnabled(false);

			this.multi = new MLP("12", "ORT", this.jejs, this.jpru, new Double(
					this.textField5.getText()).doubleValue(), new Double(
					this.textField6.getText()).doubleValue(), new Double(
					this.textField6.getText()).doubleValue(), 56, new Integer(
					this.textField4.getText()).intValue(), 26);
			this.multi.cargarPesos(
					getClass().getResource(
							"ort" + this.multi.getNne() + this.multi.getNno()
									+ this.multi.getNns() + "eo.pes"),
					getClass().getResource(
							"ort" + this.multi.getNne() + this.multi.getNno()
									+ this.multi.getNns() + "os.pes"));
			this.textField2.setText("" + this.multi.error());

			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button4.setEnabled(true);
			this.button5.setEnabled(true);
			this.button6.setEnabled(true);
			this.textField1.setEnabled(true);
			this.textField4.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField7.setEnabled(true);
			this.textField8.setEnabled(true);
			this.choice1.setEnabled(true);
		} catch (Exception e) {
			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button4.setEnabled(true);
			this.button5.setEnabled(true);
			this.button6.setEnabled(true);
			this.textField1.setEnabled(true);
			this.textField4.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField7.setEnabled(true);
			this.textField8.setEnabled(true);
			this.choice1.setEnabled(true);
			System.err.println("Error durante la carga: " + e.getMessage());
		}
	}

	private void button1ActionPerformed(ActionEvent evt) {
		double[] entSerie = new double[56];
		
		double redX;
		double redY;

		double maxX;
		double maxY = maxX = redX = redY = 0.0D;
		double minX = this.ancho;
		double minY = this.alto;
		int y;
		int x = y = 0;

		for (int i = 0; i < this.puntos.size(); i++) {
			Point punto = (Point) this.puntos.get(i);
			if (punto.getX() < minX)
				minX = punto.getX();
			if (punto.getX() > maxX)
				maxX = punto.getX();
			if (punto.getY() < minY)
				minY = punto.getY();
			if (punto.getY() <= maxY)
				continue;
			maxY = punto.getY();
		}

		maxY += 2.0D;
		maxX += 2.0D;
		minY -= 2.0D;
		minX -= 2.0D;

		if (maxY > this.alto)
			maxY = this.alto;
		if (maxX > this.ancho)
			maxX = this.ancho;
		if (minY < 0.0D)
			minY = 0.0D;
		if (minX < 0.0D)
			minX = 0.0D;

		redX = this.ancho / (maxX - minX);
		redY = this.alto / (maxY - minY);

		for (int i = 0; i < this.puntos.size(); i++) {
			Point punto = (Point) this.puntos.get(i);
			double posY = (punto.getY() - minY) * redY;
			double posX = (punto.getX() - minX) * redX;
			punto.setLocation(posX, posY);
			this.puntos.set(i, punto);
		}

		for (int i = 0; i < this.puntos.size(); i++) {
			Point punto = (Point) this.puntos.get(i);
			y = (int) (punto.getY() / this.altoEsc);
			x = (int) (punto.getX() / this.anchoEsc);
			System.out.println(x + ", " + y);
			if ((x > 6) || (y > 7) || (x < 0) || (y < 0))
				continue;
			this.ent[x][y] = 0.5D;
		}

		try {
			for (int j = 0; j < 7; j++) {
				for (int i = 0; i < 8; i++)
					entSerie[(i * 7 + j)] = this.ent[j][i];
			}
			this.multi.ejecutar(entSerie);

			int letra = 0;
			double valor = -0.5D;

			System.out.println("Los valores para las letras obtenidos son: ");
			for (int i = 0; i < 26; i++)
				System.out.println(this.letras[i] + "="
						+ this.multi.getCapaSalida().getNeurona(i).getSalida());
			for (int i = 0; i < 26; i++) {
				if (this.multi.getCapaSalida().getNeurona(i).getSalida() <= valor)
					continue;
				letra = i;
				valor = this.multi.getCapaSalida().getNeurona(i).getSalida();
			}

			System.out.println("La letra reconocida por la red es la "
					+ this.letras[letra]);

			Graphics g = this.canvas2.getGraphics();

			g.setColor(Color.white);
			g.fillRect(0, 0, this.ancho, this.alto);
			g.setColor(Color.black);

			for (int i = 0; i < 56; i++) {
				if (this.ejs[letra].getEntrada(i) != 0.5D)
					continue;
				int xx = (int) (i % 7 * this.anchoEsc);
				int yy = (int) (i / 7 * this.altoEsc);
				g.fillRect(xx, yy, (int) this.anchoEsc, (int) this.altoEsc);
			}

			if (this.checkbox1.getState() == true) {
				g = this.canvas1.getGraphics();

				for (int i = 0; i < 56; i++) {
					g.setColor(new Color(100, 100, 100, 128));
					if (entSerie[i] == 0.5D) {
						int xx = (int) (i % 7 * this.anchoEsc);
						int yy = (int) (i / 7 * this.altoEsc);
						g.fillRect(xx, yy, (int) this.anchoEsc,
								(int) this.altoEsc);
					}
					g.setColor(Color.black);
				}
			}
		} catch (Exception ex) {
			System.err.println("Error: " + ex.getMessage());
		}
	}

	private void button2ActionPerformed(ActionEvent evt) {
		this.runner = new Thread(this);
		this.runner.setPriority(1);
		this.runner.start();
	}

	public void entrenar() {
		try {
			if (this.multi == null) {
				this.multi = new MLP("12", "ORT", this.jejs, this.jpru,
						new Double(this.textField5.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						56, new Integer(this.textField4.getText()).intValue(),
						26);
			}

			this.button1.setEnabled(false);
			this.button2.setEnabled(false);
			this.button4.setEnabled(false);
			this.button5.setEnabled(false);
			this.button8.setEnabled(false);
			this.textField1.setEnabled(false);
			this.textField4.setEnabled(false);
			this.textField5.setEnabled(false);
			this.textField6.setEnabled(false);
			this.textField7.setEnabled(false);
			this.textField8.setEnabled(false);
			this.choice1.setEnabled(false);
			int anchoGrafo = getWidth() - this.label11.getX() - 80;
			int x0 = this.label11.getX() + 20;
			int y0 = this.label11.getY() + 35;
			int NItMax = new Integer(this.textField7.getText()).intValue();

			if (!this.continuar) {
				this.numIteraciones = 0;
				if (anchoGrafo > NItMax) {
					this.grafico = new Point[anchoGrafo];
					for (int j = 0; j < anchoGrafo; j++)
						this.grafico[j] = new Point();
				} else {
					this.grafico = new Point[NItMax + 1];
					for (int j = 0; j <= NItMax; j++)
						this.grafico[j] = new Point();
				}

				this.grafico[0].x = x0;
				this.grafico[0].y = y0;
			}

			this.grafico = aprender(getGraphics(),
					getHeight() - this.label11.getY() - 110, anchoGrafo, x0,
					y0, new Integer(this.textField7.getText()).intValue(),
					new Double(this.textField1.getText()).doubleValue(),
					new Double(this.textField8.getText()).doubleValue(),
					this.grafico, this.numIteraciones);

			this.numIteraciones = 0;
			for (int i = 0; i < this.grafico.length; i++) {
				this.numIteraciones += 1;
				if ((this.grafico[i].getX() != this.grafico[i].getY())
						|| (this.grafico[i].getY() != 0.0D))
					continue;
				i = this.grafico.length;
			}
			this.numIteraciones -= 1;

			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button4.setEnabled(true);
			this.button5.setEnabled(true);
			this.button8.setEnabled(true);
			this.textField4.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField8.setEnabled(true);
			this.choice1.setEnabled(true);
			if (!this.continuar) {
				this.textField1.setEnabled(true);
				this.textField7.setEnabled(true);
			}
		} catch (Exception e) {
			System.err.println("Error durante el entrenamiento: "
					+ e.getMessage());
			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button4.setEnabled(true);
			this.button5.setEnabled(true);
			this.button8.setEnabled(true);
			this.textField1.setEnabled(true);
			this.textField4.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField7.setEnabled(true);
			this.textField8.setEnabled(true);
			this.choice1.setEnabled(true);
		}
	}

	public void run() {
		while (this.runner != null) {
			System.out.println("Llamamos a entrenar");
			entrenar();
			paint(getGraphics());
			stop();
		}
	}

	public void stop() {
		this.runner = null;
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		Utilidad.pintarEjes(getGraphics(), getHeight() - this.label11.getY()
				- 110, getWidth() - this.label11.getX() - 80, this.label11
				.getX() + 20, this.label11.getY() + 35, new Integer(
				this.textField7.getText()).intValue(), new Integer(
				this.textField1.getText()).intValue(), new Double(
				this.textField8.getText()).doubleValue());
		if (this.grafico != null)
			Utilidad.pintarGrafico(getGraphics(), this.grafico);
	}

	/**
	 * @param fichCad
	 */
	public void leerEjemplos(String fichCad) {
		try {
			URL fich = getClass().getResource(fichCad);
			URLConnection conn = null;
			BufferedReader in = null;

			int i = 0;
			int j = 0;
			int k = 0;
			int tam = 0;

			conn = fich.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			in.readLine();

			tam = tamFichero(fichCad);
			tam /= 3;
			this.ejs = new Ejemplo[tam];
			String cad;
			while (((cad = in.readLine()) != null) && (i < tam)) {
				cad = in.readLine();
				while ((cad.indexOf(" ") != -1) && (j < 56)) {
					this.e[j] = new Double(cad.substring(0, cad.indexOf(" ")))
							.doubleValue();
					cad = cad.substring(cad.indexOf(" ") + 1);
					j++;
				}

				j = 0;
				cad = in.readLine();
				while ((cad.indexOf(" ") != -1) && (j < 26)) {
					this.s[j] = new Double(cad.substring(0, cad.indexOf(" ")))
							.doubleValue();
					cad = cad.substring(cad.indexOf(" ") + 1);
					j++;
				}
				this.ejs[i] = new Ejemplo(this.e, this.s);
				i++;
				j = 0;
			}
			in.close();
			conn = null;
			in = null;

			this.jejs = new Juego[1];
			this.jejs[0] = new Juego(this.ejs);
		} catch (Exception e) {
			System.err.println("Error en la lectura: " + e.getMessage());
		}
	}

	public int tamFichero(String fichCad) {
		URL fich = getClass().getResource(fichCad);
		URLConnection conn = null;
		BufferedReader in = null;
		int tam = 0;
		try {
			conn = fich.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();

			in = new BufferedReader(new InputStreamReader(is));
			in.readLine();
			while (in.readLine() != null)
				tam++;
			in.close();
			conn = null;
			in = null;
		} catch (Exception e) {
			System.err.println("Error en la lectura: " + e.getMessage());
			return 0;
		}
		return tam;
	}

	public Point[] aprender(Graphics g, int alto, int ancho, int x0, int y0,
			int NItMax, double errMax, double errMin, Point[] grafo, int nits) {
		double error = errMin + 1.0D;
		double errorAnt = 1.0D;

		int indEjemplo = 0;

		Utilidad.pintarEjes(g, alto, ancho, x0, y0, NItMax, errMax, errMin);
		DecimalFormat df = new DecimalFormat("0.00");

		g.setColor(Color.red);
		int i;
		if (nits == 0)
			i = 1;
		else
			i = nits;
		if (nits > 1)
			Utilidad.pintarGrafico(getGraphics(), grafo);

		while ((error > errMin) && (nits < NItMax - 1) && (this.runner != null)) {
			if (this.choice1.getSelectedIndex() == 0)
				this.multi.backpropagation();
			else
				this.multi.backpropagationNoBatch(indEjemplo, 0);
			error = this.multi.error();
			this.textField2.setText(df.format(error));

			if ((NItMax < ancho)
					|| (Math.IEEEremainder(nits, NItMax / ancho) == 0.0D)) {
				grafo[i].x = (x0 + nits * ancho / NItMax);
				if (error * alto / errMax <= alto)
					grafo[i].y = (y0 + alto - (int) (error * alto / errMax));
				else
					grafo[i].y = y0;
				g.drawLine(grafo[(i - 1)].x, grafo[(i - 1)].y, grafo[i].x,
						grafo[i].y);
				i++;
			}
			nits++;
			if (indEjemplo == this.jejs[0].getEjemplos().length - 1) {
				indEjemplo = 0;
				continue;
			}
			indEjemplo++;
		}
		g.setColor(Color.black);
		return grafo;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button1) {
			button1ActionPerformed(event);
		} else if (event.getSource() == button2) {
			button2ActionPerformed(event);
		} else if (event.getSource() == button4) {
			button4ActionPerformed(event);
		}else if (event.getSource() == button5) {
			button5ActionPerformed(event);
		}else if (event.getSource() == button6) {
			button6ActionPerformed(event);
		}else if (event.getSource() == button8) {
			button8ActionPerformed(event);
		}

	}

	@Override
	public void textValueChanged(TextEvent event) {
		if (event.getSource() == textField4) {
			textField4TextValueChanged(event);
		}

	}

	@Override
	public void mouseDragged(MouseEvent event) {
		pintar(event);
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		
	}

	@Override
	public void mouseClicked(MouseEvent eevent) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent eevent) {
		pararPintar(eevent);
		
	}


}