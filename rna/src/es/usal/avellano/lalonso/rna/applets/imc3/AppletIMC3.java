package es.usal.avellano.lalonso.rna.applets.imc3;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.PixelGrabber;
import java.io.PrintStream;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;

import es.usal.avellano.lalonso.rna.redes.Capa;
import es.usal.avellano.lalonso.rna.redes.Ejemplo;
import es.usal.avellano.lalonso.rna.redes.Juego;
import es.usal.avellano.lalonso.rna.redes.MLP;
import es.usal.avellano.lalonso.rna.redes.Neurona;
import es.usal.avellano.lalonso.rna.utilidades.Utilidad;

public class AppletIMC3 extends Applet implements Runnable, ActionListener, ItemListener {
	private MLP multiR;
	private MLP multiG;
	private MLP multiB;
	int numeroOcultas = 16;

	Ejemplo[] ejsR = new Ejemplo[256];

	Ejemplo[] pruR = new Ejemplo[256];

	Ejemplo[] ejsG = new Ejemplo[256];

	Ejemplo[] pruG = new Ejemplo[256];

	Ejemplo[] ejsB = new Ejemplo[256];

	Ejemplo[] pruB = new Ejemplo[256];

	double[] eR = new double[64];

	double[] sR = new double[64];

	double[] eG = new double[64];

	double[] sG = new double[64];

	double[] eB = new double[64];

	double[] sB = new double[64];

	Juego[] jejsR = new Juego[1];

	Juego[] jpruR = new Juego[1];

	Juego[] jejsG = new Juego[1];

	Juego[] jpruG = new Juego[1];

	Juego[] jejsB = new Juego[1];

	Juego[] jpruB = new Juego[1];
	Point[] puntos;
	int numIteraciones = 0;

	boolean continuar = false;

	ImageIcon ie = null;

	int altoimg = 0;

	int anchoimg = 0;

	int altoNuevo = 0;

	int anchoNuevo = 0;

	int[][] pixelsR = (int[][]) null;

	int[][] pixelsG = (int[][]) null;

	int[][] pixelsB = (int[][]) null;

	int[][][][][] imagenRec = (int[][][][][]) null;

	int[][][][][] imagenComprimida = (int[][][][][]) null;

	private Thread runner = null;
	private Button button1;
	private Button button2;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;
	private Choice choice1;
	private Choice choice2;
	private Choice choice3;
	private Label label1;
	private Label label10;
	private Label label11;
	private Label label12;
	private Label label13;
	private Label label14;
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
	private TextField textField5;
	private TextField textField6;
	private TextField textField7;
	private TextField textField8;

	public void init() {
		try {
//			this.ie = new ImageIcon(getImage(getDocumentBase(), "art.jpg"));
			java.net.URL imgURL = getClass().getResource("art.jpg");
			this.ie = new ImageIcon(imgURL);

			System.out.println(this.ie.getDescription());
			this.altoimg = this.ie.getImage().getHeight(this);
			this.anchoimg = this.ie.getImage().getWidth(this);
			System.out.println("Imagen " + this.altoimg + "x" + this.anchoimg);

			if (this.altoimg == 64) {
				this.altoNuevo = (this.altoimg / 4);
				this.anchoNuevo = (this.anchoimg * 4);
			}
			if (this.altoimg == 128) {
				this.altoNuevo = (this.altoimg / 8);
				this.anchoNuevo = (this.anchoimg * 2);
			}

			this.pixelsR = new int[this.altoimg][this.anchoimg];
			this.pixelsG = new int[this.altoimg][this.anchoimg];
			this.pixelsB = new int[this.altoimg][this.anchoimg];

			for (int i = 0; i < this.altoimg; i++)
				for (int j = 0; j < this.altoimg; j++) {
					int tmp256_255 = (this.pixelsB[i][j] = 0);
					this.pixelsG[i][j] = tmp256_255;
					this.pixelsR[i][j] = tmp256_255;
				}
		} catch (Exception e) {
			System.err.println("Error en la URL: " + e.getMessage());
		}
		initComponents();

		this.choice1.add("face.jpg");
		this.choice1.add("meg.jpg");
		this.choice1.add("hat.jpg");
		this.choice1.add("clavo.jpg");
		this.choice1.add("art.jpg");
		this.choice1.add("spiff.jpg");

		this.choice2.add("Lotes");
		this.choice2.add("Individual");

		this.choice3.add("0%");
		this.choice3.add("25%");
		this.choice3.add("50%");
		this.choice3.add("75%");
		this.choice3.select(0);
	}

	private void initComponents()
  {
    this.lable1 = new Label();
    this.label1 = new Label();
    this.button1 = new Button();
    this.label3 = new Label();
    this.label4 = new Label();
    this.label5 = new Label();
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
    this.label2 = new Label();
    this.choice1 = new Choice();
    this.button6 = new Button();
    this.label12 = new Label();
    this.textField1 = new TextField();
    this.label13 = new Label();
    this.choice2 = new Choice();
    this.button7 = new Button();
    this.button8 = new Button();
    this.choice3 = new Choice();
    this.button5 = new Button();
    this.label14 = new Label();
    this.textField2 = new TextField();

    setLayout(null);

    setBackground(new Color(192, 192, 192));
    this.lable1.setFont(new Font("Dialog", 1, 14));
    this.lable1.setForeground(new Color(100, 100, 150));
    this.lable1.setText("EJECUCIÓN");
    add(this.lable1);
    this.lable1.setBounds(20, 20, 110, 23);

    this.label1.setText("Imagen Original");
    add(this.label1);
    this.label1.setBounds(30, 40, 100, 20);

    this.button1.setLabel("Ejecutar");
    this.button1.addActionListener(this);

    add(this.button1);
    this.button1.setBounds(200, 230, 61, 24);

    this.label3.setFont(new Font("Dialog", 0, 10));
    this.label3.setText("Imagen Comprimida");
    add(this.label3);
    this.label3.setBounds(180, 90, 100, 18);

    this.label4.setFont(new Font("Dialog", 1, 14));
    this.label4.setForeground(new Color(100, 100, 150));
    this.label4.setText("ARQUITECTURA");
    add(this.label4);
    this.label4.setBounds(50, 330, 120, 21);

    this.label5.setText("% de Compresión:");
    add(this.label5);
    this.label5.setBounds(70, 360, 140, 20);

    this.label6.setFont(new Font("Dialog", 1, 14));
    this.label6.setForeground(new Color(100, 100, 150));
    this.label6.setText("ENTRENAMIENTO");
    add(this.label6);
    this.label6.setBounds(470, 20, 130, 23);

    this.label7.setText("Momento");
    add(this.label7);
    this.label7.setBounds(470, 50, 70, 20);

    this.label8.setText("Factor de Aprendizaje");
    add(this.label8);
    this.label8.setBounds(470, 70, 122, 20);

    this.textField5.setText("0.01");
    add(this.textField5);
    this.textField5.setBounds(640, 50, 30, 20);

    this.textField6.setText("0.01");
    add(this.textField6);
    this.textField6.setBounds(640, 70, 30, 20);

    this.label9.setText("Nº de iteraciones");
    add(this.label9);
    this.label9.setBounds(470, 110, 110, 20);

    this.textField7.setText("400");
    add(this.textField7);
    this.textField7.setBounds(640, 110, 50, 20);

    this.label10.setText("Error máximo permitido");
    add(this.label10);
    this.label10.setBounds(470, 130, 140, 20);

    this.textField8.setText("0.1");
    add(this.textField8);
    this.textField8.setBounds(640, 130, 50, 20);

    this.button2.setLabel("Comenzar");
    this.button2.addActionListener(this);

    add(this.button2);
    this.button2.setBounds(710, 60, 80, 24);

    this.label11.setFont(new Font("Dialog", 1, 12));
    this.label11.setForeground(new Color(100, 100, 150));
    this.label11.setText("Gráfica de Error");
    add(this.label11);
    this.label11.setBounds(480, 200, 110, 20);

    this.label2.setText("Imagen Descomprimida");
    add(this.label2);
    this.label2.setBounds(280, 40, 150, 20);

    add(this.choice1);
    this.choice1.setBounds(70, 280, 140, 20);

    this.button6.setLabel("Cargar");
    this.button6.addActionListener(this);

    add(this.button6);
    this.button6.setBounds(220, 280, 60, 24);

    this.label12.setText("Error máximo representado");
    add(this.label12);
    this.label12.setBounds(470, 150, 160, 20);

    this.textField1.setText("30");
    add(this.textField1);
    this.textField1.setBounds(640, 150, 50, 20);

    this.label13.setText("Tipo de Aprendizaje:");
    add(this.label13);
    this.label13.setBounds(70, 390, 140, 20);

    add(this.choice2);
    this.choice2.setBounds(220, 390, 140, 20);

    this.button7.setLabel("Pausar");
    this.button7.addActionListener(this);

    add(this.button7);
    this.button7.setBounds(710, 100, 80, 24);

    this.button8.setLabel("Cargar Pesos");
    this.button8.addActionListener(this);

    add(this.button8);
    this.button8.setBounds(150, 430, 110, 24);

    this.choice3.addItemListener(this);

    add(this.choice3);
    this.choice3.setBounds(220, 360, 60, 20);

    this.button5.setLabel("Reiniciar");
    this.button5.addActionListener(this);

    add(this.button5);
    this.button5.setBounds(710, 140, 80, 24);

    this.label14.setText("Error actual");
    add(this.label14);
    this.label14.setBounds(470, 170, 80, 20);

    this.textField2.setEditable(false);
    add(this.textField2);
    this.textField2.setBounds(640, 170, 50, 20);
  }

	private void button5ActionPerformed(ActionEvent evt) {
		this.multiR.reiniciarPesos();
		this.multiG.reiniciarPesos();
		this.multiB.reiniciarPesos();
		stop();
		this.button2.setLabel("Comenzar");
		this.textField1.setEnabled(true);
		this.textField7.setEnabled(true);
		this.textField2.setText("");
		this.continuar = false;
	}

	private void choice3ItemStateChanged(ItemEvent evt) {
		switch (this.choice3.getSelectedIndex()) {
		case 0:
			this.numeroOcultas = 16;
			break;
		case 1:
			this.numeroOcultas = 12;
			break;
		case 2:
			this.numeroOcultas = 8;
			break;
		case 3:
			this.numeroOcultas = 4;
		}
	}

	private void button8ActionPerformed(ActionEvent evt) {
		try {
			this.button1.setEnabled(false);
			this.button2.setEnabled(false);
			this.button6.setEnabled(false);
			this.button5.setEnabled(false);
			this.button7.setEnabled(false);
			this.textField5.setEnabled(false);
			this.textField6.setEnabled(false);
			this.textField7.setEnabled(false);
			this.textField8.setEnabled(false);
			this.textField1.setEnabled(false);
			this.choice1.setEnabled(false);
			this.choice2.setEnabled(false);
			this.choice3.setEnabled(false);

			this.multiR = new MLP("12", "IMCR", this.jejsR, this.jpruR,
					new Double(this.textField5.getText()).doubleValue(),
					new Double(this.textField6.getText()).doubleValue(),
					new Double(this.textField6.getText()).doubleValue(), 16,
					this.numeroOcultas, 16);
			this.multiG = new MLP("12", "IMCG", this.jejsG, this.jpruG,
					new Double(this.textField5.getText()).doubleValue(),
					new Double(this.textField6.getText()).doubleValue(),
					new Double(this.textField6.getText()).doubleValue(), 16,
					this.numeroOcultas, 16);
			this.multiB = new MLP("12", "IMCB", this.jejsB, this.jpruB,
					new Double(this.textField5.getText()).doubleValue(),
					new Double(this.textField6.getText()).doubleValue(),
					new Double(this.textField6.getText()).doubleValue(), 16,
					this.numeroOcultas, 16);

			this.multiR.cargarPesos(
					getClass().getResource(
							"imcR" + this.multiR.getNne()
									+ this.multiR.getNno()
									+ this.multiR.getNns() + "eo.pes"),
					getClass().getResource(
							"imcR" + this.multiR.getNne()
									+ this.multiR.getNno()
									+ this.multiR.getNns() + "os.pes"));
			this.multiG.cargarPesos(
					getClass().getResource(
							"imcG" + this.multiG.getNne()
									+ this.multiG.getNno()
									+ this.multiG.getNns() + "eo.pes"),
					getClass().getResource(
							"imcG" + this.multiG.getNne()
									+ this.multiG.getNno()
									+ this.multiG.getNns() + "os.pes"));
			this.multiB.cargarPesos(
					getClass().getResource(
							"imcB" + this.multiB.getNne()
									+ this.multiB.getNno()
									+ this.multiB.getNns() + "eo.pes"),
					getClass().getResource(
							"imcB" + this.multiB.getNne()
									+ this.multiB.getNno()
									+ this.multiB.getNns() + "os.pes"));

			crearJuegos();
			DecimalFormat df = new DecimalFormat("0.000");
			this.textField2
					.setText(""
							+ df.format((this.multiR.error()
									+ this.multiG.error() + this.multiB.error()) / 3.0D));

			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button6.setEnabled(true);
			this.button5.setEnabled(true);
			this.button7.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField7.setEnabled(true);
			this.textField8.setEnabled(true);
			this.textField1.setEnabled(true);
			this.choice1.setEnabled(true);
			this.choice2.setEnabled(true);
			this.choice3.setEnabled(true);
		} catch (Exception e) {
			System.err.println("Error durante la carga: " + e.getMessage());
		}
	}

	private void button7ActionPerformed(ActionEvent evt) {
		this.continuar = true;
		this.button2.setLabel("Continuar");
		stop();
	}

	private void button6ActionPerformed(ActionEvent evt) {
//		this.ie = new ImageIcon(getImage(getDocumentBase(),
//		this.choice1.getSelectedItem()));
		java.net.URL imgURL = getClass().getResource(this.choice1.getSelectedItem());
		this.ie = new ImageIcon(imgURL);		

		this.altoimg = this.ie.getImage().getHeight(this);
		this.anchoimg = this.ie.getImage().getWidth(this);
		this.ie.paintIcon(this, getGraphics(), this.label1.getX() + 10,
				this.label1.getY() + 25);
	}

	private void button1ActionPerformed(ActionEvent evt) {
		crearJuegos();

		double[] entradaR = new double[this.altoNuevo];
		double[] entradaG = new double[this.altoNuevo];
		double[] entradaB = new double[this.altoNuevo];

		Graphics g = getGraphics();

		int dim = (int) Math.sqrt(this.altoNuevo);

		this.imagenRec = new int[this.anchoimg][this.altoimg][dim][dim][3];
		this.imagenComprimida = new int[this.anchoimg][this.altoimg][this.multiR
				.getNno() / 2][this.multiR.getNno() / 2][3];
		try {
			for (int i = 0; i < this.anchoimg; i += dim)
				for (int j = 0; j < this.altoimg; j += dim) {
					for (int k = 0; k < dim; k++) {
						for (int r = 0; r < dim; r++) {
							entradaR[(k * dim + r)] = (this.pixelsR[(k + i)][(r + j)] / 255.0D - 0.5D);
							entradaG[(k * dim + r)] = (this.pixelsG[(k + i)][(r + j)] / 255.0D - 0.5D);
							entradaB[(k * dim + r)] = (this.pixelsB[(k + i)][(r + j)] / 255.0D - 0.5D);
						}
					}
					this.multiR.ejecutar(entradaR);
					this.multiG.ejecutar(entradaG);
					this.multiB.ejecutar(entradaB);

					for (int k = 0; k < dim; k++) {
						for (int r = 0; r < dim; r++) {
							int rojo = (int) ((this.multiR.getCapaSalida()
									.getNeurona(k * dim + r).getSalida() + 0.5D) * 255.0D);
							this.imagenRec[i][j][k][r][0] = rojo;
							int verde = (int) ((this.multiG.getCapaSalida()
									.getNeurona(k * dim + r).getSalida() + 0.5D) * 255.0D);
							this.imagenRec[i][j][k][r][1] = verde;
							int azul = (int) ((this.multiB.getCapaSalida()
									.getNeurona(k * dim + r).getSalida() + 0.5D) * 255.0D);
							this.imagenRec[i][j][k][r][2] = azul;
						}
					}
					for (int k = 0; k < this.multiR.getNno() / 4; k++)
						for (int r = 0; r < this.multiR.getNno() / 4; r++) {
							int rojo = (int) ((this.multiR
									.getCapaOculta()
									.getNeurona(
											k * (this.multiR.getNno() / 4) + r)
									.getSalida() + 0.5D) * 255.0D);
							int verde = (int) ((this.multiG
									.getCapaOculta()
									.getNeurona(
											k * (this.multiG.getNno() / 4) + r)
									.getSalida() + 0.5D) * 255.0D);
							int azul = (int) ((this.multiB
									.getCapaOculta()
									.getNeurona(
											k * (this.multiB.getNno() / 4) + r)
									.getSalida() + 0.5D) * 255.0D);
							this.imagenComprimida[i][j][k][r][0] = rojo;
							this.imagenComprimida[i][j][k][r][1] = verde;
							this.imagenComprimida[i][j][k][r][2] = azul;
						}
				}
			pintarImagenComprimida(g);
			pintarImagen(g, true);
		} catch (Exception ex) {
			System.err.println("Error en la Ejecución: " + ex.getMessage());
		}
	}

	private void button2ActionPerformed(ActionEvent evt) {
		this.runner = new Thread(this);
		this.runner.setPriority(1);
		this.runner.start();
	}

	public void pintarImagen(Graphics g, boolean barrido) {
		for (int i = 0; i < this.anchoimg; i += 4)
			for (int j = 0; j < this.altoimg; j += 4) {
				for (int k = 0; k < 4; k++)
					for (int r = 0; r < 4; r++) {
						g.setColor(new Color(this.imagenRec[i][j][k][r][0],
								this.imagenRec[i][j][k][r][1],
								this.imagenRec[i][j][k][r][2]));
						int x = this.label2.getX() + 10 + k + i;
						int y = this.label2.getY() + 25 + r + j;
						g.drawLine(x, y, x, y);
					}
				if (!barrido)
					continue;
				g.setColor(Color.white);
				g.drawRect(this.label2.getX() + 10 + i, this.label2.getY() + 25
						+ j, 3, 3);
				try {
					Thread.sleep(1L);
				} catch (Exception e) {
				}
				for (int k = 0; k < 4; k++)
					for (int r = 0; r < 4; r++) {
						g.setColor(new Color(this.imagenRec[i][j][k][r][0],
								this.imagenRec[i][j][k][r][1],
								this.imagenRec[i][j][k][r][2]));
						int x = this.label2.getX() + 10 + k + i;
						int y = this.label2.getY() + 25 + r + j;
						g.drawLine(x, y, x, y);
					}
			}
	}

	public void pintarImagenComprimida(Graphics g) {
		int incremento = this.multiR.getNno() / 4;
		if (incremento == 4)
			incremento--;
		int incX = 15;
		if (incremento == 2)
			incX = 25;
		if (incremento == 1)
			incX = 35;

		g.setColor(Color.lightGray);
		g.fillRect(this.label3.getX(), this.label3.getY() + 20,
				this.anchoimg - 25, this.altoimg - 25);
		for (int i = 0; i < this.anchoimg; i += 4)
			for (int j = 0; j < this.altoimg; j += 4) {
				for (int k = 0; k < incremento; k++)
					for (int r = 0; r < incremento; r++) {
						g.setColor(new Color(
								this.imagenComprimida[i][j][k][r][0],
								this.imagenComprimida[i][j][k][r][1],
								this.imagenComprimida[i][j][k][r][2]));
						int x = this.label3.getX() + incX + k + i
								/ (5 - incremento);
						int y = this.label3.getY() + 20 + r + j
								/ (5 - incremento);
						g.drawLine(x, y, x, y);
					}
			}
	}

	public void entrenar() {
		try {
			if ((this.jejsR[0] == null) || (this.jejsG[0] == null)
					|| (this.jejsB[0] == null)) {
				button1ActionPerformed(null);
			}
			if (this.multiR == null)
				this.multiR = new MLP("12", "IMCR", this.jejsR, this.jpruR,
						new Double(this.textField5.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						16, this.numeroOcultas, 16);
			if (this.multiG == null)
				this.multiG = new MLP("12", "IMCG", this.jejsG, this.jpruG,
						new Double(this.textField5.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						16, this.numeroOcultas, 16);
			if (this.multiB == null) {
				this.multiB = new MLP("12", "IMCB", this.jejsB, this.jpruB,
						new Double(this.textField5.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						16, this.numeroOcultas, 16);
			}

			this.button1.setEnabled(false);
			this.button2.setEnabled(false);
			this.button5.setEnabled(false);
			this.button6.setEnabled(false);
			this.textField5.setEnabled(false);
			this.textField6.setEnabled(false);
			this.textField8.setEnabled(false);
			this.choice1.setEnabled(false);
			this.choice2.setEnabled(false);
			this.choice3.setEnabled(false);
			if (!this.continuar) {
				this.textField1.setEnabled(true);
				this.textField7.setEnabled(true);
			}

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
			this.button6.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField7.setEnabled(true);
			this.textField8.setEnabled(true);
			this.textField1.setEnabled(true);
			this.choice1.setEnabled(true);
			this.choice2.setEnabled(true);
			this.choice3.setEnabled(true);
		} catch (Exception ex) {
			System.err.println("Error en el Aprendizaje: " + ex.getMessage());
			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button5.setEnabled(true);
			this.button6.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField7.setEnabled(true);
			this.textField8.setEnabled(true);
			this.textField1.setEnabled(true);
			this.choice1.setEnabled(true);
			this.choice2.setEnabled(true);
			this.choice3.setEnabled(true);
		}
	}

	public Point[] aprender(Graphics g, int alto, int ancho, int x0, int y0,
			int NItMax, double errMax, double errMin, Point[] grafo, int nits) {
		double error = errMin + 1.0D;
		double errorAnt = 1.0D;
		int i = 1;
		int indEjemplo = 0;

		Utilidad.pintarEjes(g, alto, ancho, x0, y0, NItMax, errMax, errMin);
		DecimalFormat df = new DecimalFormat("0.00");

		if (nits == 0)
			i = 1;
		else
			i = nits;
		if (nits > 1)
			Utilidad.pintarGrafico(getGraphics(), grafo);

		g.setColor(Color.red);

		while ((error > errMin) && (nits < NItMax - 1) && (this.runner != null)) {
			if (this.choice2.getSelectedIndex() == 0) {
				this.multiR.backpropagation();
				this.multiG.backpropagation();
				this.multiB.backpropagation();
			} else {
				this.multiR.backpropagationNoBatch(indEjemplo, 0);
				this.multiG.backpropagationNoBatch(indEjemplo, 0);
				this.multiB.backpropagationNoBatch(indEjemplo, 0);
				if (indEjemplo == this.jejsR[0].getEjemplos().length - 1)
					indEjemplo = 0;
				else {
					indEjemplo++;
				}
			}
			error = (this.multiR.error() + this.multiG.error() + this.multiB
					.error()) / 3.0D;
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
		}
		g.setColor(Color.black);
		if (nits >= NItMax)
			nits = 0;
		return grafo;
	}

	public void procesarImagen() {
		try {
			int[] pix = new int[this.anchoimg * this.altoimg];

			Image im = this.ie.getImage();
			PixelGrabber pg = new PixelGrabber(im, 0, 0, this.anchoimg,
					this.altoimg, pix, 0, this.anchoimg);

			pg.grabPixels(0L);

			if ((pg.getStatus() & 0x80) != 0) {
				System.err.println("image fetch aborted or errored");
				return;
			}

			for (int i = 0; i < this.anchoimg; i++)
				for (int j = 0; j < this.altoimg; j++) {
					int tmp126_125 = (this.pixelsB[i][j] = 0);
					this.pixelsG[i][j] = tmp126_125;
					this.pixelsR[i][j] = tmp126_125;
				}
			for (int i = 0; i < this.anchoimg; i++) {
				for (int j = 0; j < this.altoimg; j++) {
					int a = pix[(i * this.anchoimg + j)] >> 24 & 0xFF;
					int r = pix[(i * this.anchoimg + j)] >> 16 & 0xFF;
					int v = pix[(i * this.anchoimg + j)] >> 8 & 0xFF;
					int z = pix[(i * this.anchoimg + j)] & 0xFF;

					this.pixelsR[j][i] = r;
					this.pixelsG[j][i] = v;
					this.pixelsB[j][i] = z;
				}
			}
			System.out.println("Píxeles recogidos");
		} catch (InterruptedException ioe) {
			System.err.println("Error, escaneo interrumpido: "
					+ ioe.getMessage());
		} catch (Exception e) {
			System.err.println("Error en el procesamiento de la imagen: "
					+ e.getMessage());
			return;
		}
	}

	public void crearJuegos() {
		int dim = (int) Math.sqrt(this.altoNuevo);
		int largo = (int) Math.sqrt(this.anchoNuevo);
		int[][] matrizR = new int[this.altoNuevo][this.anchoNuevo];
		int[][] matrizG = new int[this.altoNuevo][this.anchoNuevo];
		int[][] matrizB = new int[this.altoNuevo][this.anchoNuevo];
		int k = 0;
		Graphics g = getGraphics();

		procesarImagen();
		try {
			System.out.println("Guardamos en matriz " + this.altoNuevo + "x"
					+ this.anchoNuevo);
			for (int i = 0; i < this.anchoNuevo; i++) {
				int x = (int) (Math.random() * (this.anchoimg - dim));
				int y = (int) (Math.random() * (this.anchoimg - dim));

				for (int j = 0; j < this.altoNuevo; j++) {
					if (Math.IEEEremainder(j, dim) == 0.0D)
						k = 0;
					matrizR[j][i] = this.pixelsR[(x + j / dim)][(y + k)];
					matrizG[j][i] = this.pixelsG[(x + j / dim)][(y + k)];
					matrizB[j][i] = this.pixelsB[(x + j / dim)][(y + k)];
					k++;
				}
			}
			System.out.println("Píxeles en cuadrados aleatorios");

			for (int i = 0; i < this.anchoNuevo; i++) {
				for (int j = 0; j < this.altoNuevo; j++) {
					this.eR[j] = (matrizR[j][i] / 255.0D - 0.5D);
					this.eG[j] = (matrizG[j][i] / 255.0D - 0.5D);
					this.eB[j] = (matrizB[j][i] / 255.0D - 0.5D);
				}

				this.ejsR[i] = new Ejemplo(this.eR, this.eR);
				this.pruR[i] = new Ejemplo(this.eR, this.eR);
				this.ejsG[i] = new Ejemplo(this.eG, this.eG);
				this.pruG[i] = new Ejemplo(this.eG, this.eG);
				this.ejsB[i] = new Ejemplo(this.eB, this.eB);
				this.pruB[i] = new Ejemplo(this.eB, this.eB);
			}

			this.jejsR = new Juego[1];
			this.jpruR = new Juego[1];
			this.jejsG = new Juego[1];
			this.jpruG = new Juego[1];
			this.jejsB = new Juego[1];
			this.jpruB = new Juego[1];

			this.jejsR[0] = new Juego(this.ejsR);
			this.jpruR[0] = new Juego(this.pruR);
			this.jejsG[0] = new Juego(this.ejsG);
			this.jpruG[0] = new Juego(this.pruG);
			this.jejsB[0] = new Juego(this.ejsB);
			this.jpruB[0] = new Juego(this.pruB);

			if (this.multiR != null)
				this.multiR.setJuegos(this.jejsR, this.jpruR);
			if (this.multiG != null)
				this.multiG.setJuegos(this.jejsG, this.jpruG);
			if (this.multiB != null)
				this.multiB.setJuegos(this.jejsB, this.jpruB);

			this.button2.setEnabled(true);
		} catch (Exception e) {
			System.err
					.println("Error durante la creación de los juegos de entrenamiento: "
							+ e.getMessage());
			return;
		}
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
		this.ie.paintIcon(this, g, this.label1.getX() + 10,
				this.label1.getY() + 25);
		Utilidad.pintarEjes(getGraphics(), getHeight() - this.label11.getY()
				- 110, getWidth() - this.label11.getX() - 80, this.label11
				.getX() + 20, this.label11.getY() + 35, new Integer(
				this.textField7.getText()).intValue(), new Double(
				this.textField1.getText()).doubleValue(), new Double(
				this.textField8.getText()).doubleValue());
		if (this.puntos != null)
			Utilidad.pintarGrafico(getGraphics(), this.puntos);
		if ((this.imagenRec != null) && (this.imagenRec[0][0][0][0][0] != 0))
			pintarImagen(g, false);
		if ((this.imagenComprimida != null)
				&& (this.imagenComprimida[0][0][0][0][0] != 0))
			pintarImagenComprimida(g);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == button1){
			button1ActionPerformed(event);
		}else if(event.getSource() == button2){
			button2ActionPerformed(event);
		}else if(event.getSource() == button6){
			button6ActionPerformed(event);
		}else if(event.getSource() == button7){
			button7ActionPerformed(event);
		}else if(event.getSource() == button8){
			button8ActionPerformed(event);
		}else if(event.getSource() == button5){
			button5ActionPerformed(event);
		} 
		
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if(event.getSource() == choice3){
			choice3ItemStateChanged(event);
		}
		
	}
}