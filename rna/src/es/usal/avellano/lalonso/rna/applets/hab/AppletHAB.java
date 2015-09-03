package es.usal.avellano.lalonso.rna.applets.hab;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import es.usal.avellano.lalonso.rna.redes.Ejemplo;
import es.usal.avellano.lalonso.rna.redes.Juego;
import es.usal.avellano.lalonso.rna.redes.MLP;
import es.usal.avellano.lalonso.rna.utilidades.Utilidad;

public class AppletHAB extends Applet implements Runnable, ActionListener,
		ItemListener {

	private MLP multi;
	int numEjs;
	int numPru;
	Ejemplo[] ejs;
	Ejemplo[] pru;
	double[] e;
	double[] s;
	Juego[] jejs;
	Juego[] jpru;
	Point[] puntos;
	int numIteraciones;
	boolean continuar;
	String[] digitos;
	private Thread runner;
	private Button button1;
	private Button button2;
	private Button button5;
	private Button button7;
	private Button button8;
	private Choice choice1;
	private Choice choice2;
	private Label label1;
	private Label label10;
	private Label label11;
	private Label label12;
	private Label label13;
	private Label label14;
	private Label label15;
	private Label label16;
	private Label label17;
	private Label label18;
	private Label label19;
	private Label label2;
	private Label label20;
	private Label label21;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;
	private Label label8;
	private Label label9;
	private Label lable1;
	private TextField textField1;
	private TextField textField3;
	private TextField textField4;
	private TextField textField5;
	private TextField textField6;
	private TextField textField7;
	private TextField textField8;

	public AppletHAB() {
		this.ejs = new Ejemplo[this.numEjs];

		this.pru = new Ejemplo[this.numPru];

//		this.e = new double['?'];
		this.e = new double[128];

		this.s = new double[10];

		this.jejs = new Juego[1];

		this.jpru = new Juego[1];

		this.numIteraciones = 0;

		this.continuar = false;

		this.digitos = new String[] { "Cero", "Uno", "Dos", "Tres", "Cuatro",
				"Cinco", "Seis", "Siete", "Ocho", "Nueve" };

		this.runner = null;
	}

	public void init() {
		initComponents();

		for (int i = 0; i < 10; i++)
			this.choice1.add(this.digitos[i]);

		this.choice2.add("Lotes");
		this.choice2.add("Individual");

		this.button1.setEnabled(false);

		this.jejs = leerEjemplos("ejemplosHAB.dat");
		this.jpru = leerEjemplos("pruebasHAB.dat");
	}

	private void initComponents() {
		this.lable1 = new Label();
		this.button1 = new Button();
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
		this.choice1 = new Choice();
		this.label12 = new Label();
		this.textField1 = new TextField();
		this.label13 = new Label();
		this.choice2 = new Choice();
		this.button7 = new Button();
		this.button8 = new Button();
		this.button5 = new Button();
		this.label1 = new Label();
		this.label2 = new Label();
		this.label3 = new Label();
		this.label14 = new Label();
		this.label15 = new Label();
		this.label16 = new Label();
		this.label17 = new Label();
		this.label18 = new Label();
		this.label19 = new Label();
		this.label20 = new Label();
		this.label21 = new Label();
		this.textField3 = new TextField();

		setLayout(null);

		setBackground(new Color(192, 192, 192));
		setForeground(new Color(0, 0, 255));
		this.lable1.setBackground(new Color(192, 192, 192));
		this.lable1.setFont(new Font("Dialog", 1, 14));
		this.lable1.setForeground(new Color(102, 102, 153));
		this.lable1.setText("EJECUCIÓN");
		add(this.lable1);
		this.lable1.setBounds(20, 20, 110, 23);

		this.button1.setActionCommand("Disparar");
		this.button1.setForeground(new Color(0, 0, 0));
		this.button1.setLabel("Hablar");
		this.button1.addActionListener(this);

		add(this.button1);
		this.button1.setBounds(60, 120, 70, 24);

		this.label4.setFont(new Font("Dialog", 1, 14));
		this.label4.setForeground(new Color(102, 102, 153));
		this.label4.setText("ARQUITECTURA");
		add(this.label4);
		this.label4.setBounds(30, 260, 120, 21);

		this.label5.setForeground(new Color(0, 0, 0));
		this.label5.setText("Nº de neuronas ocultas: ");
		add(this.label5);
		this.label5.setBounds(40, 300, 140, 20);

		this.textField4.setText("16");
		this.textField4.setForeground(new Color(0, 0, 0));
		add(this.textField4);
		this.textField4.setBounds(180, 300, 30, 20);

		this.label6.setFont(new Font("SansSerif", 1, 14));
		this.label6.setForeground(new Color(102, 102, 153));
		this.label6.setText("ENTRENAMIENTO");
		add(this.label6);
		this.label6.setBounds(410, 20, 130, 23);

		this.label7.setForeground(new Color(0, 0, 0));
		this.label7.setText("Momento");
		add(this.label7);
		this.label7.setBounds(410, 50, 70, 20);

		this.label8.setForeground(new Color(0, 0, 0));
		this.label8.setText("Factor de Aprendizaje");
		add(this.label8);
		this.label8.setBounds(410, 70, 122, 20);

		this.textField5.setText("0.01");
		this.textField5.setForeground(new Color(0, 0, 0));
		add(this.textField5);
		this.textField5.setBounds(570, 50, 30, 20);

		this.textField6.setText("0.01");
		this.textField6.setForeground(new Color(0, 0, 0));
		add(this.textField6);
		this.textField6.setBounds(570, 70, 30, 20);

		this.label9.setForeground(new Color(0, 0, 0));
		this.label9.setText("Nº de iteraciones");
		add(this.label9);
		this.label9.setBounds(410, 100, 110, 20);

		this.textField7.setText("400");
		this.textField7.setForeground(new Color(0, 0, 0));
		add(this.textField7);
		this.textField7.setBounds(570, 100, 40, 20);

		this.label10.setForeground(new Color(0, 0, 0));
		this.label10.setText("Error máximo permitido");
		add(this.label10);
		this.label10.setBounds(410, 120, 140, 20);

		this.textField8.setText("1");
		this.textField8.setForeground(new Color(0, 0, 0));
		add(this.textField8);
		this.textField8.setBounds(570, 120, 40, 20);

		this.button2.setForeground(new Color(0, 0, 0));
		this.button2.setLabel("Comenzar");
		this.button2.addActionListener(this);

		add(this.button2);
		this.button2.setBounds(640, 70, 80, 24);

		this.label11.setFont(new Font("Dialog", 1, 12));
		this.label11.setForeground(new Color(102, 102, 153));
		this.label11.setText("Gráfica de Error");
		add(this.label11);
		this.label11.setBounds(420, 190, 130, 20);

		this.choice1.setForeground(new Color(0, 0, 0));
		this.choice1.addItemListener(this);

		add(this.choice1);
		this.choice1.setBounds(50, 90, 90, 20);

		this.label12.setForeground(new Color(0, 0, 0));
		this.label12.setText("Error máximo representado");
		add(this.label12);
		this.label12.setBounds(410, 140, 160, 20);

		this.textField1.setText("20");
		this.textField1.setForeground(new Color(0, 0, 0));
		add(this.textField1);
		this.textField1.setBounds(570, 140, 40, 20);

		this.label13.setForeground(new Color(0, 0, 0));
		this.label13.setText("Tipo de Aprendizaje:");
		add(this.label13);
		this.label13.setBounds(40, 330, 120, 20);

		this.choice2.setForeground(new Color(0, 0, 0));
		add(this.choice2);
		this.choice2.setBounds(180, 330, 110, 20);

		this.button7.setForeground(new Color(0, 0, 0));
		this.button7.setLabel("Pausar");
		this.button7.addActionListener(this);

		add(this.button7);
		this.button7.setBounds(640, 100, 80, 24);

		this.button8.setForeground(new Color(0, 0, 0));
		this.button8.setLabel("Cargar Pesos");
		this.button8.addActionListener(this);

		add(this.button8);
		this.button8.setBounds(120, 390, 110, 24);

		this.button5.setForeground(new Color(0, 0, 0));
		this.button5.setLabel("Reiniciar");
		this.button5.addActionListener(this);

		add(this.button5);
		this.button5.setBounds(640, 150, 80, 24);

		this.label1.setBackground(new Color(255, 255, 255));
		this.label1.setFont(new Font("Dialog", 1, 18));
		this.label1.setForeground(new Color(102, 102, 153));
		this.label1.setText("  1");
		add(this.label1);
		this.label1.setBounds(220, 60, 30, 30);

		this.label2.setBackground(new Color(255, 255, 255));
		this.label2.setFont(new Font("Dialog", 1, 18));
		this.label2.setForeground(new Color(102, 102, 153));
		this.label2.setText("  2");
		add(this.label2);
		this.label2.setBounds(260, 60, 30, 30);

		this.label3.setBackground(new Color(255, 255, 255));
		this.label3.setFont(new Font("Dialog", 1, 18));
		this.label3.setForeground(new Color(102, 102, 153));
		this.label3.setText("  3");
		add(this.label3);
		this.label3.setBounds(300, 60, 30, 30);

		this.label14.setBackground(new Color(255, 255, 255));
		this.label14.setFont(new Font("Dialog", 1, 18));
		this.label14.setForeground(new Color(102, 102, 153));
		this.label14.setText("  4");
		add(this.label14);
		this.label14.setBounds(220, 100, 30, 30);

		this.label15.setBackground(new Color(255, 255, 255));
		this.label15.setFont(new Font("Dialog", 1, 18));
		this.label15.setForeground(new Color(102, 102, 153));
		this.label15.setText("  5");
		add(this.label15);
		this.label15.setBounds(260, 100, 30, 30);

		this.label16.setBackground(new Color(255, 255, 255));
		this.label16.setFont(new Font("Dialog", 1, 18));
		this.label16.setForeground(new Color(102, 102, 153));
		this.label16.setText("  6");
		add(this.label16);
		this.label16.setBounds(300, 100, 30, 30);

		this.label17.setBackground(new Color(255, 255, 255));
		this.label17.setFont(new Font("Dialog", 1, 18));
		this.label17.setForeground(new Color(102, 102, 153));
		this.label17.setText("  7");
		add(this.label17);
		this.label17.setBounds(220, 140, 30, 30);

		this.label18.setBackground(new Color(255, 255, 255));
		this.label18.setFont(new Font("Dialog", 1, 18));
		this.label18.setForeground(new Color(102, 102, 153));
		this.label18.setText("  8");
		add(this.label18);
		this.label18.setBounds(260, 140, 30, 30);

		this.label19.setBackground(new Color(255, 255, 255));
		this.label19.setFont(new Font("Dialog", 1, 18));
		this.label19.setForeground(new Color(102, 102, 153));
		this.label19.setText("  9");
		add(this.label19);
		this.label19.setBounds(300, 140, 30, 30);

		this.label20.setBackground(new Color(255, 255, 255));
		this.label20.setFont(new Font("Dialog", 1, 18));
		this.label20.setForeground(new Color(102, 102, 153));
		this.label20.setText("  0");
		add(this.label20);
		this.label20.setBounds(260, 180, 30, 30);

		this.label21.setForeground(new Color(0, 0, 0));
		this.label21.setText("Error actual");
		add(this.label21);
		this.label21.setBounds(410, 160, 120, 20);

		this.textField3.setEditable(false);
		this.textField3.setForeground(new Color(0, 0, 0));
		add(this.textField3);
		this.textField3.setBounds(570, 160, 40, 20);
	}

	private void button5ActionPerformed(ActionEvent evt) {
		this.multi.reiniciarPesos();
		stop();
		this.button2.setLabel("Comenzar");
		this.textField1.setEnabled(true);
		this.textField7.setEnabled(true);
		this.textField3.setText("");
		this.continuar = false;
	}

	private void choice1ItemStateChanged(ItemEvent evt) {
		this.button1.setEnabled(true);
	}

	private void button8ActionPerformed(ActionEvent evt) {
		try {
			this.button1.setEnabled(false);
			this.button2.setEnabled(false);
			this.button5.setEnabled(false);
			this.button8.setEnabled(false);
			this.textField4.setEnabled(false);
			this.textField5.setEnabled(false);
			this.textField6.setEnabled(false);
			this.textField7.setEnabled(false);
			this.textField8.setEnabled(false);
			this.textField1.setEnabled(false);
			this.choice1.setEnabled(false);
			this.choice2.setEnabled(false);
			// AppletHAB.DialogFrame df;
			Frame df;
			if (this.textField4.getText().equals("16")) {
				this.multi = new MLP("24", "HAB", this.jejs, this.jpru,
						new Double(this.textField5.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						128, new Integer(this.textField4.getText()).intValue(),
						10);
				this.multi.cargarPesos(
						getClass().getResource(
								"hab" + this.multi.getNne()
										+ this.multi.getNno()
										+ this.multi.getNns() + "eo.pes"),
						getClass().getResource(
								"hab" + this.multi.getNne()
										+ this.multi.getNno()
										+ this.multi.getNns() + "os.pes"));
				this.textField3.setText("" + this.multi.error());
			} else {
				// df = new AppletHAB.DialogFrame(this, "Error de carga",
				// "Sólo existen pesos para 16 neuronas ocultas");
				df = new Frame("Error de carga./n"
						+ "Sólo existen pesos para 16 neuronas ocultas");
			}

			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button5.setEnabled(true);
			this.button8.setEnabled(true);
			this.textField4.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField1.setEnabled(true);
			this.textField7.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField8.setEnabled(true);
			this.choice1.setEnabled(true);
			this.choice2.setEnabled(true);
		} catch (Exception e) {
			System.err.println("Error durante la carga: " + e.getMessage());
			// AppletHAB.DialogFrame df = new AppletHAB.DialogFrame(this,
			// "Error de carga", "Error: " + e.getMessage());
			Frame df = new Frame("Error de carga.\n" + "Error: "
					+ e.getMessage());
			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button5.setEnabled(true);
			this.button8.setEnabled(true);
			this.textField4.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField1.setEnabled(true);
			this.textField7.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField8.setEnabled(true);
			this.choice1.setEnabled(true);
			this.choice2.setEnabled(true);
			return;
		}
	}

	private void button7ActionPerformed(ActionEvent evt) {
		this.continuar = true;
		this.button2.setLabel("Continuar");
		stop();
	}

	private void button1ActionPerformed(ActionEvent evt) {
		AudioClip numero = null;

//		numero = getAudioClip(getDocumentBase(), this.choice1.getSelectedItem().toUpperCase() + ".wav");
		java.net.URL numeroURL = getClass().getResource(this.choice1.getSelectedItem().toUpperCase() + ".wav");
		numero = getAudioClip(numeroURL);
		
		
		
		
		numero.play();

		int numPru = this.choice1.getSelectedIndex()
				+ (int) (Math.random() * 200.0D) / 10 * 10;

		this.multi.ejecutar(0, numPru);

		System.out.println("Los valores para los dígitos obtenidos son: ");
		int digito = 0;
		double valor = -0.5D;
		for (int i = 0; i < 10; i++)
			System.out.println(this.digitos[i] + "="
					+ this.multi.getCapaSalida().getNeurona(i).getSalida());
		for (int i = 0; i < 10; i++) {
			if (this.multi.getCapaSalida().getNeurona(i).getSalida() <= valor)
				continue;
			digito = i;
			valor = this.multi.getCapaSalida().getNeurona(i).getSalida();
		}

		System.out
				.println("El dígito reconocido es el " + this.digitos[digito]);

		for (int i = 0; i < 10; i++) {
			valor = this.multi.getCapaSalida().getNeurona(i).getSalida();
			int color = (int) ((Math.abs(valor) + 0.5D) * 255.0D);
			System.out.println(color);
			switch (i) {
			case 0:
				if (valor > 0.0D)
					this.label20.setBackground(new Color(0, color, 0));
				else
					this.label20.setBackground(new Color(color, 0, 0));
				break;
			case 1:
				if (valor > 0.0D)
					this.label1.setBackground(new Color(0, color, 0));
				else
					this.label1.setBackground(new Color(color, 0, 0));
				break;
			case 2:
				if (valor > 0.0D)
					this.label2.setBackground(new Color(0, color, 0));
				else
					this.label2.setBackground(new Color(color, 0, 0));
				break;
			case 3:
				if (valor > 0.0D)
					this.label3.setBackground(new Color(0, color, 0));
				else
					this.label3.setBackground(new Color(color, 0, 0));
				break;
			case 4:
				if (valor > 0.0D)
					this.label14.setBackground(new Color(0, color, 0));
				else
					this.label14.setBackground(new Color(color, 0, 0));
				break;
			case 5:
				if (valor > 0.0D)
					this.label15.setBackground(new Color(0, color, 0));
				else
					this.label15.setBackground(new Color(color, 0, 0));
				break;
			case 6:
				if (valor > 0.0D)
					this.label16.setBackground(new Color(0, color, 0));
				else
					this.label16.setBackground(new Color(color, 0, 0));
				break;
			case 7:
				if (valor > 0.0D)
					this.label17.setBackground(new Color(0, color, 0));
				else
					this.label17.setBackground(new Color(color, 0, 0));
				break;
			case 8:
				if (valor > 0.0D)
					this.label18.setBackground(new Color(0, color, 0));
				else
					this.label18.setBackground(new Color(color, 0, 0));
				break;
			case 9:
				if (valor > 0.0D)
					this.label19.setBackground(new Color(0, color, 0));
				else {
					this.label19.setBackground(new Color(color, 0, 0));
				}
			}
		}
		numero = null;
	}

	private void button2ActionPerformed(ActionEvent evt) {
		this.runner = new Thread(this);
		this.runner.setPriority(1);
		this.runner.start();
	}

	public void entrenar() {
		try {
			if (this.multi == null) {
				this.multi = new MLP("24", "HAB", this.jejs, this.jpru,
						new Double(this.textField5.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						128, new Integer(this.textField4.getText()).intValue(),
						10);
			}
			this.button1.setEnabled(false);
			this.button2.setEnabled(false);
			this.button5.setEnabled(false);
			this.button8.setEnabled(false);
			this.textField4.setEnabled(false);
			this.textField5.setEnabled(false);
			this.textField6.setEnabled(false);
			this.textField7.setEnabled(false);
			this.textField8.setEnabled(false);
			this.textField1.setEnabled(false);
			this.choice1.setEnabled(false);
			this.choice2.setEnabled(false);

			int anchoGrafo = getWidth() - this.label11.getX() - 80;
			int x0 = this.label11.getX() + 20;
			int y0 = this.label11.getY() + 35;
			int NItMax = new Integer(this.textField7.getText()).intValue();

			if (!this.continuar) {
				this.numIteraciones = 0;
				if (anchoGrafo > NItMax) {
					this.puntos = new Point[anchoGrafo];
					for (int j = 0; j < anchoGrafo; j++)
						this.puntos[j] = new Point();
				} else {
					this.puntos = new Point[NItMax + 1];
					for (int j = 0; j <= NItMax; j++)
						this.puntos[j] = new Point();
				}

				this.puntos[0].x = x0;
				this.puntos[0].y = y0;
			}

			this.puntos = aprender(getGraphics(),
					getHeight() - this.label11.getY() - 110, anchoGrafo, x0,
					y0, NItMax,
					new Double(this.textField1.getText()).doubleValue(),
					new Double(this.textField8.getText()).doubleValue(),
					this.puntos, this.numIteraciones);

			this.numIteraciones = 0;
			for (int i = 0; i < this.puntos.length; i++) {
				this.numIteraciones += 1;
				if ((this.puntos[i].getX() != this.puntos[i].getY())
						|| (this.puntos[i].getY() != 0.0D))
					continue;
				i = this.puntos.length;
			}
			this.numIteraciones -= 1;

			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button5.setEnabled(true);
			this.button8.setEnabled(true);
			this.textField4.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField8.setEnabled(true);
			this.choice1.setEnabled(true);
			this.choice2.setEnabled(true);
			if (!this.continuar) {
				this.textField1.setEnabled(true);
				this.textField7.setEnabled(true);
			}
		} catch (Exception ex) {
			System.err.println("Error en el Aprendizaje: " + ex.getMessage());

			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button5.setEnabled(true);
			this.button8.setEnabled(true);
			this.textField4.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField7.setEnabled(true);
			this.textField8.setEnabled(true);
			this.textField1.setEnabled(true);
			this.choice1.setEnabled(true);
			this.choice2.setEnabled(true);
		}
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
			if (this.choice2.getSelectedIndex() == 0) {
				this.multi.backpropagation();
			} else {
				this.multi.backpropagationNoBatch(indEjemplo, 0);
				if (indEjemplo == this.jejs[0].getEjemplos().length - 1)
					indEjemplo = 0;
				else
					indEjemplo++;
			}
			error = this.multi.error();
			this.textField3.setText(df.format(error));
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
		}
		g.setColor(Color.black);
		g.clearRect(x0 - 40, y0 + alto + 20, ancho + 40, 30);

		return grafo;
	}

	public void run() {
		while (this.runner != null) {
			entrenar();
			paint(getGraphics());
			stop();
		}
	}

	public void stop() {
		this.runner = null;
	}

	public void paint(Graphics g) {
		Utilidad.pintarEjes(getGraphics(), getHeight() - this.label11.getY()
				- 110, getWidth() - this.label11.getX() - 80, this.label11
				.getX() + 20, this.label11.getY() + 35, new Integer(
				this.textField7.getText()).intValue(), new Double(
				this.textField1.getText()).doubleValue(), new Double(
				this.textField8.getText()).doubleValue());
		if (this.puntos != null)
			Utilidad.pintarGrafico(getGraphics(), this.puntos);
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
			System.err.println("Error en la lectura de ejemplos: "
					+ e.getMessage());
			return 0;
		}
		return tam;
	}

	public Juego[] leerEjemplos(String fichCad) {
		try {
			Juego[] juego = null;
			URLConnection conn = null;
			BufferedReader in = null;

			int i = 0;
			int j = 0;
			int k = 0;
			int tam = 0;

			tam = tamFichero(fichCad);
			tam /= 6;
			this.ejs = new Ejemplo[tam];

			URL fich = getClass().getResource(fichCad);
			conn = fich.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			String cad;
			while (((cad = in.readLine()) != null) && (i < tam)) {
				cad = in.readLine();
				cad = in.readLine();

				while ((cad.indexOf(" ") != -1) && (j < 128)) {
					this.e[j] = (new Double(cad.substring(0, cad.indexOf(" ")))
							.doubleValue() - 0.5D);
					cad = cad.substring(cad.indexOf(" ") + 1);
					j++;
				}

				j = 0;
				cad = in.readLine();
				cad = in.readLine();
				cad = in.readLine();
				while ((cad.indexOf(" ") != -1) && (j < 10)) {
					this.s[j] = (new Double(cad.substring(0, cad.indexOf(" ")))
							.doubleValue() - 0.5D);
					cad = cad.substring(cad.indexOf(" ") + 1);
					j++;
				}
				this.ejs[i] = new Ejemplo(this.e, this.s);
				i++;
				j = 0;
			}
			System.out.println("Acabamos de leer");
			in.close();
			conn = null;
			in = null;

			juego = new Juego[1];
			juego[0] = new Juego(this.ejs);
			return juego;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error en la lectura de ejemplos: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button1) {
			button1ActionPerformed(event);
		} else if (event.getSource() == button2) {
			button2ActionPerformed(event);
		} else if (event.getSource() == button7) {
			button7ActionPerformed(event);
		} else if (event.getSource() == button8) {
			button8ActionPerformed(event);
		} else if (event.getSource() == button5) {
			button5ActionPerformed(event);
		}

	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource() == choice1) {
			choice1ItemStateChanged(event);
		}
	}
}