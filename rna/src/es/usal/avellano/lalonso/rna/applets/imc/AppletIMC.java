package es.usal.avellano.lalonso.rna.applets.imc;

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
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;

import es.usal.avellano.lalonso.rna.redes.Ejemplo;
import es.usal.avellano.lalonso.rna.redes.Juego;
import es.usal.avellano.lalonso.rna.redes.MLP;
import es.usal.avellano.lalonso.rna.utilidades.Utilidad;

public class AppletIMC extends Applet implements Runnable, ActionListener, ItemListener {
	private MLP multi;
	int numeroOcultas = 16;

	Ejemplo[] ejs = new Ejemplo[256];

	Ejemplo[] pru = new Ejemplo[256];

	double[] e = new double[64];

	double[] s = new double[64];

	Juego[] jejs = new Juego[1];

	Juego[] jpru = new Juego[1];
	Point[] puntos;
	int numIteraciones = 0;

	boolean continuar = false;

	ImageIcon ie = null;

	int altoimg = 0;

	int anchoimg = 0;

	int altoNuevo = 0;

	int anchoNuevo = 0;

	int[][] pixels = (int[][]) null;

	int[][][][] imagenRec = (int[][][][]) null;

	int[][][][] imagenComprimida = (int[][][][]) null;

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
//			this.ie = new ImageIcon(getImage(getDocumentBase(), "face.jpg"));
			java.net.URL imgURL = getClass().getResource("face.jpg");
			this.ie = new ImageIcon(imgURL);
			
//			URL documentBase = new URL("http://avellano.usal.es/~lalonso/RNA/applets/face.jpg");
//			this.ie = new ImageIcon(getImage(documentBase));

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

			this.pixels = new int[this.altoimg][this.anchoimg];
			for (int i = 0; i < this.altoimg; i++)
				for (int j = 0; j < this.altoimg; j++)
					this.pixels[i][j] = 0;
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
    this.label1.setBounds(50, 50, 90, 20);

    this.button1.setLabel("Ejecutar");
    this.button1.addActionListener(this);

    add(this.button1);
    this.button1.setBounds(180, 230, 70, 24);

    this.label3.setFont(new Font("Dialog", 0, 10));
    this.label3.setText("Imagen Comprimida");
    add(this.label3);
    this.label3.setBounds(170, 80, 100, 18);

    this.label4.setFont(new Font("Dialog", 1, 14));
    this.label4.setForeground(new Color(100, 100, 150));
    this.label4.setText("ARQUITECTURA");
    add(this.label4);
    this.label4.setBounds(50, 340, 120, 23);

    this.label5.setText("% de Compresión:");
    add(this.label5);
    this.label5.setBounds(70, 370, 140, 20);

    this.label6.setFont(new Font("Dialog", 1, 14));
    this.label6.setForeground(new Color(100, 100, 150));
    this.label6.setText("ENTRENAMIENTO");
    add(this.label6);
    this.label6.setBounds(450, 20, 140, 23);

    this.label7.setText("Momento");
    add(this.label7);
    this.label7.setBounds(450, 50, 70, 20);

    this.label8.setText("Factor de Aprendizaje");
    add(this.label8);
    this.label8.setBounds(450, 70, 122, 20);

    this.textField5.setText("0.01");
    add(this.textField5);
    this.textField5.setBounds(590, 50, 30, 20);

    this.textField6.setText("0.01");
    add(this.textField6);
    this.textField6.setBounds(590, 70, 30, 20);

    this.label9.setText("Nº de iteraciones");
    add(this.label9);
    this.label9.setBounds(450, 100, 110, 20);

    this.textField7.setText("400");
    add(this.textField7);
    this.textField7.setBounds(610, 100, 40, 20);

    this.label10.setText("Error máximo permitido");
    add(this.label10);
    this.label10.setBounds(450, 120, 140, 20);

    this.textField8.setText("0.1");
    add(this.textField8);
    this.textField8.setBounds(610, 120, 40, 20);

    this.button2.setLabel("Comenzar");
    this.button2.addActionListener(this);

    add(this.button2);
    this.button2.setBounds(660, 50, 80, 24);

    this.label11.setFont(new Font("Dialog", 1, 12));
    this.label11.setForeground(new Color(100, 100, 150));
    this.label11.setText("Gráfica de Error");
    add(this.label11);
    this.label11.setBounds(450, 190, 120, 20);

    this.label2.setText("Imagen Descomprimida");
    add(this.label2);
    this.label2.setBounds(280, 50, 150, 20);

    add(this.choice1);
    this.choice1.setBounds(70, 300, 110, 20);

    this.button6.setLabel("Cargar");
    this.button6.addActionListener(this);

    add(this.button6);
    this.button6.setBounds(190, 300, 70, 24);

    this.label12.setText("Error máximo representado");
    add(this.label12);
    this.label12.setBounds(450, 140, 160, 20);

    this.textField1.setText("30");
    add(this.textField1);
    this.textField1.setBounds(610, 140, 40, 20);

    this.label13.setText("Tipo de Aprendizaje:");
    add(this.label13);
    this.label13.setBounds(70, 400, 140, 20);

    add(this.choice2);
    this.choice2.setBounds(210, 400, 110, 20);

    this.button7.setLabel("Reiniciar");
    this.button7.addActionListener(this);

    add(this.button7);
    this.button7.setBounds(660, 130, 80, 24);

    this.button8.setLabel("Cargar Pesos");
    this.button8.addActionListener(this);

    add(this.button8);
    this.button8.setBounds(140, 440, 110, 24);

    this.choice3.addItemListener(this);

    add(this.choice3);
    this.choice3.setBounds(210, 370, 60, 20);

    this.button5.setLabel("Pausar");
    this.button5.addActionListener(this);

    add(this.button5);
    this.button5.setBounds(660, 90, 80, 24);

    this.label14.setText("Error actual");
    add(this.label14);
    this.label14.setBounds(450, 160, 110, 20);

    this.textField2.setEditable(false);
    add(this.textField2);
    this.textField2.setBounds(610, 160, 40, 20);
  }

	private void button5ActionPerformed(ActionEvent evt) {
		this.continuar = true;
		this.button2.setLabel("Continuar");
		stop();
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
			this.textField5.setEnabled(false);
			this.textField6.setEnabled(false);
			this.textField7.setEnabled(false);
			this.textField8.setEnabled(false);
			this.textField1.setEnabled(false);
			this.choice1.setEnabled(false);
			this.choice2.setEnabled(false);
			this.choice3.setEnabled(false);
			this.multi = new MLP("12", "IMC", this.jejs, this.jpru, new Double(
					this.textField5.getText()).doubleValue(), new Double(
					this.textField6.getText()).doubleValue(), new Double(
					this.textField6.getText()).doubleValue(), 16,
					this.numeroOcultas, 16);
			this.multi.cargarPesos(
					getClass().getResource(
							"imc" + this.multi.getNne() + this.multi.getNno()
									+ this.multi.getNns() + "eo.pes"),
					getClass().getResource(
							"imc" + this.multi.getNne() + this.multi.getNno()
									+ this.multi.getNns() + "os.pes"));
			crearJuegos();
			DecimalFormat df = new DecimalFormat("0.000");
			this.textField2.setText("" + df.format(this.multi.error()));

			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button6.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField7.setEnabled(true);
			this.textField8.setEnabled(true);
			this.textField1.setEnabled(true);
			this.choice1.setEnabled(true);
			this.choice2.setEnabled(true);
			this.choice3.setEnabled(true);
		} catch (Exception e) {
			this.button1.setEnabled(true);
			this.button2.setEnabled(true);
			this.button6.setEnabled(true);
			this.textField5.setEnabled(true);
			this.textField6.setEnabled(true);
			this.textField7.setEnabled(true);
			this.textField8.setEnabled(true);
			this.textField1.setEnabled(true);
			this.choice1.setEnabled(true);
			this.choice2.setEnabled(true);
			this.choice3.setEnabled(true);
			System.err.println("Error durante la carga: " + e.getMessage());
		}
	}

	private void button7ActionPerformed(ActionEvent evt) {
		this.multi.reiniciarPesos();
		stop();
		this.button2.setLabel("Comenzar");
		this.textField1.setEnabled(true);
		this.textField7.setEnabled(true);
		this.textField2.setText("");
		this.continuar = false;
	}

	/**
	 * @param evt
	 * Cargar imagen del combo
	 */
	private void button6ActionPerformed(ActionEvent evt) {
		
//		this.ie = new ImageIcon(getImage(getDocumentBase(),
//				this.choice1.getSelectedItem()));
		java.net.URL imgURL = getClass().getResource(this.choice1.getSelectedItem());
		this.ie = new ImageIcon(imgURL);		
		
		this.altoimg = this.ie.getImage().getHeight(this);
		this.anchoimg = this.ie.getImage().getWidth(this);
		this.ie.paintIcon(this, getGraphics(), this.label1.getX() - 10,
				this.label1.getY() + 20);
	}

	private void button1ActionPerformed(ActionEvent evt) {
		double[] entrada = new double[this.altoNuevo];

		Graphics g = getGraphics();

		crearJuegos();

		int dim = (int) Math.sqrt(this.altoNuevo);
		this.imagenRec = new int[this.anchoimg][this.altoimg][dim][dim];
		int comX = 0;
		int comY = 0;
		switch (this.multi.getNno()) {
		case 16:
			comX = comY = 4;
			break;
		case 12:
			comX = 4;
			comY = 3;
			break;
		case 8:
			comX = 4;
			comY = 2;
			break;
		case 4:
			comX = 2;
			comY = 2;
			break;
		}

		this.imagenComprimida = new int[this.anchoimg][this.altoimg][comX][comY];
		try {
			for (int i = 0; i < this.anchoimg; i += dim) {
				for (int j = 0; j < this.altoimg; j += dim) {
					for (int k = 0; k < dim; k++) {
						for (int r = 0; r < dim; r++)
							entrada[(k * dim + r)] = (this.pixels[(k + i)][(r + j)] / 255.0D - 0.5D);
					}
					this.multi.ejecutar(entrada);

					for (int k = 0; k < dim; k++) {
						for (int r = 0; r < dim; r++) {
							int color = (int) ((this.multi.getCapaSalida()
									.getNeurona(k * dim + r).getSalida() + 0.5D) * 255.0D);
							this.imagenRec[i][j][k][r] = color;
						}
					}
					int n = 0;
					for (int k = 0; k < comX; k++)
						for (int r = 0; r < comY; r++) {
							int color = (int) ((this.multi.getCapaOculta()
									.getNeurona(n).getSalida() + 0.5D) * 255.0D);
							n++;
							this.imagenComprimida[i][j][k][r] = color;
						}
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

	public void procesarImagen() {
		try {
			int[] pix = new int[this.anchoimg * this.altoimg];
			Graphics g = getGraphics();

			Image im = this.ie.getImage();
			PixelGrabber pg = new PixelGrabber(im, 0, 0, this.anchoimg,
					this.altoimg, pix, 0, this.anchoimg);

			pg.grabPixels(0L);

			if ((pg.getStatus() & 0x80) != 0) {
				System.err.println("Captura de la imagen abortada o errónea");
				return;
			}

			for (int i = 0; i < this.anchoimg; i++) {
				for (int j = 0; j < this.altoimg; j++)
					this.pixels[i][j] = 0;
			}
			for (int i = 0; i < this.anchoimg; i++)
				for (int j = 0; j < this.altoimg; j++) {
					int a = pix[(i * this.anchoimg + j)] >> 24 & 0xFF;
					int r = pix[(i * this.anchoimg + j)] >> 16 & 0xFF;
					int v = pix[(i * this.anchoimg + j)] >> 8 & 0xFF;
					int z = pix[(i * this.anchoimg + j)] & 0xFF;
					this.pixels[j][i] = z;
				}
		} catch (InterruptedException ioe) {
			System.err.println("Error, escaneo interrumpido: "
					+ ioe.getMessage());
			return;
		}
	}

	public void crearJuegos() {
		try {
			procesarImagen();

			int dim = (int) Math.sqrt(this.altoNuevo);
			int k = 0;
			int[][] matriz = new int[this.altoNuevo][this.anchoNuevo];
			int largo = (int) Math.sqrt(this.anchoNuevo);
			Graphics g = getGraphics();

			for (int i = 0; i < this.anchoNuevo; i++) {
				int x = (int) (Math.random() * (this.anchoimg - dim));
				int y = (int) (Math.random() * (this.anchoimg - dim));

				for (int j = 0; j < this.altoNuevo; j++) {
					if (Math.IEEEremainder(j, dim) == 0.0D)
						k = 0;
					matriz[j][i] = this.pixels[(x + j / dim)][(y + k)];
					k++;
				}

			}

			for (int i = 0; i < this.anchoNuevo; i++) {
				for (int j = 0; j < this.altoNuevo; j++) {
					this.e[j] = (matriz[j][i] / 255.0D - 0.5D);
				}

				this.ejs[i] = new Ejemplo(this.e, this.e);
				this.pru[i] = new Ejemplo(this.e, this.e);
			}

			this.jejs = new Juego[1];
			this.jpru = new Juego[1];

			this.jejs[0] = new Juego(this.ejs);
			this.jpru[0] = new Juego(this.pru);
			if (this.multi != null)
				this.multi.setJuegos(this.jejs, this.jpru);

			this.button2.setEnabled(true);
		} catch (Exception e) {
			System.err.println("Error durante la creación de los juegos: "
					+ e.getMessage());
		}
	}

	public void pintarImagen(Graphics g, boolean barrido) {
		for (int i = 0; i < this.anchoimg; i += 4)
			for (int j = 0; j < this.altoimg; j += 4) {
				for (int k = 0; k < 4; k++)
					for (int r = 0; r < 4; r++) {
						g.setColor(new Color(this.imagenRec[i][j][k][r],
								this.imagenRec[i][j][k][r],
								this.imagenRec[i][j][k][r]));
						int x = this.label2.getX() + k + i;
						int y = this.label2.getY() + 20 + r + j;
						g.drawLine(x, y, x, y);
					}
				if (!barrido)
					continue;
				g.setColor(Color.white);
				g.drawRect(this.label2.getX() + i, this.label2.getY() + 20 + j,
						3, 3);
				try {
					Thread.sleep(5L);
				} catch (Exception e) {
				}
				for (int k = 0; k < 4; k++)
					for (int r = 0; r < 4; r++) {
						g.setColor(new Color(this.imagenRec[i][j][k][r],
								this.imagenRec[i][j][k][r],
								this.imagenRec[i][j][k][r]));
						int x = this.label2.getX() + k + i;
						int y = this.label2.getY() + 20 + r + j;
						g.drawLine(x, y, x, y);
					}
			}
	}

	public void pintarImagenComprimida(Graphics g) {
		int incremento = this.multi.getNno() / 4;
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
						g.setColor(new Color(this.imagenComprimida[i][j][k][r],
								this.imagenComprimida[i][j][k][r],
								this.imagenComprimida[i][j][k][r]));
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
			if (this.jejs[0] == null) {
				crearJuegos();
			}
			if (this.multi == null) {
				System.out.println(this.numeroOcultas);
				this.multi = new MLP("12", "IMC", this.jejs, this.jpru,
						new Double(this.textField5.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						16, this.numeroOcultas, 16);
			}

			this.button1.setEnabled(false);
			this.button2.setEnabled(false);
			this.button6.setEnabled(false);
			this.button7.setEnabled(false);
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
			this.button6.setEnabled(true);
			this.button7.setEnabled(true);
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
			this.button6.setEnabled(true);
			this.button7.setEnabled(true);
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
		this.ie.paintIcon(this, g, this.label1.getX() - 10,
				this.label1.getY() + 20);
		Utilidad.pintarEjes(getGraphics(), getHeight() - this.label11.getY()
				- 110, getWidth() - this.label11.getX() - 80, this.label11
				.getX() + 20, this.label11.getY() + 35, new Integer(
				this.textField7.getText()).intValue(), new Double(
				this.textField1.getText()).doubleValue(), new Double(
				this.textField8.getText()).doubleValue());
		if (this.puntos != null)
			Utilidad.pintarGrafico(getGraphics(), this.puntos);
		if ((this.imagenRec != null) && (this.imagenRec[0][0][0][0] != 0))
			pintarImagen(g, false);
		if ((this.imagenComprimida != null)
				&& (this.imagenComprimida[0][0][0][0] != 0))
			pintarImagenComprimida(g);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button1) {
			button1ActionPerformed(event);
		}else if (event.getSource() == button2) {
			button2ActionPerformed(event);
		}else if (event.getSource() == button6) {
			button6ActionPerformed(event);
		}else if (event.getSource() == button8) {
			button8ActionPerformed(event);
		}else if (event.getSource() == button7) {
			button7ActionPerformed(event);
		}else if (event.getSource() == button5) {
			button5ActionPerformed(event);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource() == choice3) {
			choice3ItemStateChanged(event);
		}
		
	}
}