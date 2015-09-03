package es.usal.avellano.lalonso.rna.applets.hop;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintStream;

import es.usal.avellano.lalonso.rna.redes.Hopfield;
import es.usal.avellano.lalonso.rna.utilidades.Utilidad;

public class AppletHOP extends Applet implements MouseMotionListener, MouseListener, ActionListener, ItemListener{
	private Hopfield hop;
	int numMemorizados = 0;

	int maxMemorizados = 6;

	Point[] posMemoria = new Point[this.maxMemorizados];

	double[] puntosEnergia = new double[10];

	double[][] puntosCoincidencia = new double[this.maxMemorizados][10];

	boolean pintadoNuevo = false;

	private int pixelsX = 6;

	private int pixelsY = 6;

	double[][] ent = new double[this.pixelsX][this.pixelsY];

	double[] entradas = new double[this.pixelsX * this.pixelsY];

	double[][] memoria = new double[this.maxMemorizados][this.pixelsX
			* this.pixelsY];

	int nits = 0;
	int alto;
	int ancho;
	double anchoCuadro;
	double altoCuadro;
	int altoMemoria;
	int anchoMemoria;
	double altoCuadroMemoria;
	double anchoCuadroMemoria;
	int xImg;
	int yImg;
	double altoGE;
	double anchoGE;
	double altoGC;
	double anchoGC;
	int xGE;
	int yGE;
	int xGC;
	int yGC;
	private Button button1;
	private Button button3;
	private Button button4;
	private Button button5;
	private Choice choice1;
	private Label label1;
	private Label label11;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;
	private Label label8;

	public void init() {
		initComponents();

		this.alto = 200;
		this.ancho = 200;
		this.anchoCuadro = (this.ancho / this.pixelsX);
		this.altoCuadro = (this.alto / this.pixelsY);
		System.out.print(this.ancho + " " + this.alto + " " + this.anchoCuadro
				+ " " + this.altoCuadro);
		this.xImg = 30;
		this.yImg = 40;

		this.anchoGE = (getWidth() - this.label2.getX() - 30);
		this.altoGE = (getHeight() - this.label2.getY() - 50);
		this.xGE = (this.label2.getX() + 10);
		this.yGE = (this.label2.getY() + 20);

		this.anchoGC = (getWidth() - this.label11.getX() - 40);
		this.altoGC = (this.label2.getY() - this.label11.getY() - 50);
		this.xGC = (this.label11.getX() + 10);
		this.yGC = (this.label11.getY() + 20);

		this.altoMemoria = 50;
		this.anchoMemoria = 50;
		this.posMemoria[0] = new Point(50, 300);
		this.posMemoria[1] = new Point(110, 300);
		this.posMemoria[2] = new Point(170, 300);
		this.posMemoria[3] = new Point(50, 380);
		this.posMemoria[4] = new Point(110, 380);
		this.posMemoria[5] = new Point(170, 380);

		this.altoCuadroMemoria = (this.altoMemoria / this.pixelsY);
		this.anchoCuadroMemoria = (this.anchoMemoria / this.pixelsX);

		this.choice1.add("4x4");
		this.choice1.add("5x5");
		this.choice1.add("6x6");
		this.choice1.add("7x7");
		this.choice1.add("8x8");
		this.choice1.add("9x9");
		this.choice1.add("10x10");
		this.choice1.select(2);

		this.hop = new Hopfield("27", "HOP", this.pixelsY * this.pixelsX);
		for (int i = 0; i < 10; i++)
			this.puntosEnergia[i] = 0.0D;

		button1ActionPerformed(null);
	}

	private void initComponents()
  {
    this.button1 = new Button();
    this.label5 = new Label();
    this.label11 = new Label();
    this.button5 = new Button();
    this.button3 = new Button();
    this.button4 = new Button();
    this.label2 = new Label();
    this.label3 = new Label();
    this.label6 = new Label();
    this.label7 = new Label();
    this.choice1 = new Choice();
    this.label1 = new Label();
    this.label4 = new Label();
    this.label8 = new Label();

    setLayout(null);

    setBackground(new Color(192, 192, 192));
    addMouseMotionListener(this);

    addMouseListener(this);

    this.button1.setName("Borra el contenido del cuadro");
    this.button1.setLabel("Borrar");
    this.button1.addActionListener(this);

    add(this.button1);
    this.button1.setBounds(280, 90, 70, 25);

    this.label5.setText("Tamaño de la cuadrícula");
    add(this.label5);
    this.label5.setBounds(40, 260, 140, 21);

    this.label11.setText("Grafo de Coincidencia");
    add(this.label11);
    this.label11.setBounds(380, 20, 130, 21);

    this.button5.setName("Memoriza el contenido del cuadro en la siguiente posición libre");
    this.button5.setLabel("Memorizar");
    this.button5.addActionListener(this);

    add(this.button5);
    this.button5.setBounds(280, 130, 70, 25);

    this.button3.setName("Borra toda la información almacenada");
    this.button3.setLabel("Olvidar");
    this.button3.addActionListener(this);

    add(this.button3);
    this.button3.setBounds(280, 170, 70, 25);

    this.button4.setName("Asocia la imagen del cuadro a la de memoria más apropiada");
    this.button4.setLabel("Asociar");
    this.button4.addActionListener(this);

    add(this.button4);
    this.button4.setBounds(280, 210, 70, 25);

    this.label2.setText("Grafo de Energía");
    add(this.label2);
    this.label2.setBounds(380, 240, 110, 21);

    this.label3.setBackground(new Color(255, 0, 0));
    add(this.label3);
    this.label3.setBounds(50, 350, 50, 10);

    this.label6.setBackground(new Color(0, 255, 0));
    add(this.label6);
    this.label6.setBounds(110, 350, 50, 10);

    this.label7.setBackground(new Color(0, 0, 255));
    add(this.label7);
    this.label7.setBounds(170, 350, 50, 10);

    this.choice1.addItemListener(this);

    add(this.choice1);
    this.choice1.setBounds(190, 260, 60, 21);

    this.label1.setBackground(new Color(255, 200, 0));
    add(this.label1);
    this.label1.setBounds(50, 430, 50, 10);

    this.label4.setBackground(new Color(255, 255, 0));
    add(this.label4);
    this.label4.setBounds(110, 430, 50, 10);

    this.label8.setBackground(new Color(255, 0, 255));
    add(this.label8);
    this.label8.setBounds(170, 430, 50, 10);
  }

	private void choice1ItemStateChanged(ItemEvent evt) {
		this.pixelsX = (this.choice1.getSelectedIndex() + 4);
		this.pixelsY = this.pixelsX;
		this.anchoCuadro = (this.ancho / this.pixelsX);
		this.altoCuadro = (this.alto / this.pixelsY);
		this.altoCuadroMemoria = (this.altoMemoria / this.pixelsY);
		this.anchoCuadroMemoria = (this.anchoMemoria / this.pixelsX);
		this.ent = new double[this.pixelsX][this.pixelsY];
		this.entradas = new double[this.pixelsX * this.pixelsY];
		this.memoria = new double[this.maxMemorizados][this.pixelsX
				* this.pixelsY];
		this.hop = new Hopfield("27", "HOP", this.pixelsY * this.pixelsX);
		button1ActionPerformed(null);
		button3ActionPerformed(null);
		borrarCuadricula(getGraphics());
		imprimirCuadricula(getGraphics());
	}

	private void formMouseClicked(MouseEvent evt) {
		int x = 0;
		int y = 0;
		Graphics g = getGraphics();
		Point punto = evt.getPoint();

		y = (int) ((punto.getY() - this.yImg) / this.altoCuadro);
		x = (int) ((punto.getX() - this.xImg) / this.anchoCuadro);
		if ((x <= this.pixelsX - 1) && (y <= this.pixelsY - 1) && (x >= 0)
				&& (y >= 0)) {
			if (this.ent[x][y] == -1.0D) {
				this.ent[x][y] = 1.0D;
				pintarCuadro(x, y, Color.red, g);
			} else {
				this.ent[x][y] = -1.0D;
				pintarCuadro(x, y, Color.white, g);
			}
		}
		imprimirCuadricula(g);
		this.pintadoNuevo = true;
	}

	private void button4ActionPerformed(ActionEvent evt) {
		double[] salidas = new double[this.pixelsX * this.pixelsY];
		Graphics g = getGraphics();
		boolean distintas = false;

		for (int j = 0; j < this.pixelsX; j++) {
			for (int i = 0; i < this.pixelsY; i++) {
				this.entradas[(i * this.pixelsX + j)] = this.ent[j][i];
			}
		}
		if (this.pintadoNuevo) {
			this.nits = 0;
			for (int k = 0; k < 10; k++)
				this.puntosEnergia[k] = 0.0D;
			for (int i = 0; i < this.maxMemorizados; i++)
				for (int j = 0; j < 10; j++)
					this.puntosCoincidencia[i][j] = 0.0D;
		} else {
			this.nits += 1;
		}
		this.hop.setEntradas(this.entradas);

		for (int i = 0; i < this.numMemorizados; i++)
			this.puntosCoincidencia[i][this.nits] = Utilidad.coincidencia(
					this.entradas, this.memoria[i]);
		this.puntosEnergia[this.nits] = this.hop.energia();

		this.hop.ejecutar();
		salidas = this.hop.getSalidas();

		button1ActionPerformed(null);

		g.setColor(Color.red);
		for (int i = 0; i < this.pixelsX * this.pixelsY; i++) {
			int x = i % this.pixelsX;
			int y = i / this.pixelsX;
			if (salidas[i] == 1.0D) {
				g.fillRect((int) (x * this.anchoCuadro) + this.xImg,
						(int) (y * this.altoCuadro) + this.yImg,
						(int) this.anchoCuadro, (int) this.altoCuadro);
				this.ent[x][y] = 1.0D;
			} else {
				this.ent[x][y] = -1.0D;
			}
		}
		imprimirCuadricula(g);

		for (int i = 0; i < this.numMemorizados; i++) {
			this.puntosCoincidencia[i][(this.nits + 1)] = Utilidad
					.coincidencia(salidas, this.memoria[i]);
		}
		this.puntosEnergia[(this.nits + 1)] = this.hop.energia();
		this.pintadoNuevo = false;
		paint(getGraphics());
	}

	private void button3ActionPerformed(ActionEvent evt) {
		Graphics g = getGraphics();
		this.hop.reiniciarPesos();

		g.setColor(Color.white);
		for (int i = 0; i < this.maxMemorizados; i++) {
			g.fillRect((int) this.posMemoria[i].getX(),
					(int) this.posMemoria[i].getY(), this.anchoMemoria,
					this.altoMemoria);
		}
		for (int i = 0; i < this.maxMemorizados; i++) {
			for (int j = 0; j < this.pixelsX * this.pixelsY; j++)
				this.memoria[i][j] = -1.0D;
		}
		this.numMemorizados = 0;

		this.button5.setEnabled(true);
	}

	private void button5ActionPerformed(ActionEvent evt) {
		for (int j = 0; j < this.pixelsX; j++) {
			for (int i = 0; i < this.pixelsY; i++) {
				this.entradas[(i * this.pixelsX + j)] = this.ent[j][i];
				this.memoria[this.numMemorizados][(i * this.pixelsX + j)] = this.ent[j][i];
			}
		}
		this.hop.memorizarPatron(this.entradas);
		pintarMemoria(getGraphics(), this.numMemorizados);
		this.numMemorizados += 1;
		if (this.numMemorizados >= this.maxMemorizados)
			this.button5.setEnabled(false);
	}

	private void formMouseDragged(MouseEvent evt) {
		int x = 0;
		int y = 0;
		Graphics g = getGraphics();
		Point punto = evt.getPoint();

		y = (int) ((punto.getY() - this.yImg) / this.altoCuadro);
		x = (int) ((punto.getX() - this.xImg) / this.anchoCuadro);
		if ((x <= this.pixelsX - 1) && (y <= this.pixelsY - 1) && (x >= 0)
				&& (y >= 0)) {
			this.ent[x][y] = 1.0D;
			pintarCuadro(x, y, Color.red, g);
		}
		imprimirCuadricula(g);
		this.pintadoNuevo = true;
	}

	private void button1ActionPerformed(ActionEvent evt) {
		for (int i = 0; i < this.pixelsX; i++)
			for (int j = 0; j < this.pixelsY; j++)
				this.ent[i][j] = -1.0D;
		for (int i = 0; i < this.pixelsX * this.pixelsY; i++)
			this.entradas[i] = -1.0D;

		Graphics g = getGraphics();
		g.setColor(Color.white);
		g.fillRect(this.xImg, this.yImg, this.ancho, this.alto);

		imprimirCuadricula(g);
	}

	public void imprimirCuadricula(Graphics g) {
		g.setColor(Color.lightGray);
		g.drawRect(this.xImg, this.yImg, this.ancho - 1, this.alto - 1);

		g.setColor(Color.lightGray);
		for (int i = this.xImg; i < this.ancho + this.xImg; i = (int) (i + this.anchoCuadro))
			g.drawLine(i, this.yImg, i, this.yImg + this.alto - 1);
		for (int j = this.yImg; j < this.alto + this.yImg; j = (int) (j + this.altoCuadro))
			g.drawLine(this.xImg, j, this.xImg + this.ancho, j);
		g.setColor(Color.red);
	}

	public void pintarCuadro(int x, int y, Color c, Graphics g) {
		g.setColor(c);
		g.fillRect((int) (x * this.anchoCuadro) + this.xImg,
				(int) (y * this.altoCuadro) + this.yImg,
				(int) this.anchoCuadro, (int) this.altoCuadro);
	}

	public void pintarMemoria(Graphics g, int numMemoria) {
		int x = (int) this.posMemoria[numMemoria].getX();
		int y = (int) this.posMemoria[numMemoria].getY();

		g.setColor(Color.red);
		for (int i = 0; i < this.pixelsX; i++)
			for (int j = 0; j < this.pixelsY; j++) {
				if (this.memoria[numMemoria][(j * this.pixelsX + i)] == 1.0D)
					g.fillRect(x + (int) (i * this.anchoCuadroMemoria), y
							+ (int) (j * this.altoCuadroMemoria),
							(int) this.anchoCuadroMemoria,
							(int) this.altoCuadroMemoria);
			}
	}

	public void paint(Graphics g) {
		for (int i = 0; i < this.maxMemorizados; i++) {
			g.setColor(Color.white);
			g.fillRect((int) this.posMemoria[i].getX(),
					(int) this.posMemoria[i].getY(), this.anchoMemoria,
					this.altoMemoria);
			pintarMemoria(g, i);
		}

		g.setColor(Color.white);
		g.fillRect(this.xGC, this.yGC + 2, (int) this.anchoGC + 5,
				(int) this.altoGC + 5);
		g.setColor(Color.black);

		g.drawLine(this.xGC + 15, this.yGC + (int) (0.5D * this.altoGC),
				this.xGC + (int) this.anchoGC - 5, this.yGC
						+ (int) (0.5D * this.altoGC));
		g.drawLine(this.xGC + 15, this.yGC + 5, this.xGC + 15, this.yGC
				+ (int) (this.altoGC - 5.0D));
		g.drawLine(this.xGC + 12, this.yGC + 5, this.xGC + 18, this.yGC + 5);
		g.drawLine(this.xGC + 12, this.yGC + (int) (this.altoGC - 5.0D),
				this.xGC + 18, this.yGC + (int) (this.altoGC - 5.0D));
		g.drawString("1", this.xGC + 2, this.yGC + 12);
		g.drawString("-1", this.xGC + 2, this.yGC + (int) (this.altoGC - 5.0D));

		for (int i = 1; i < 10; i++) {
			g.drawLine((int) (this.xGC + 15 + i * 0.1D * this.anchoGC),
					this.yGC + (int) (0.5D * this.altoGC) + 3,
					(int) (this.xGC + 15 + i * 0.1D * this.anchoGC), this.yGC
							+ (int) (0.5D * this.altoGC) - 3);
		}
		for (int i = 0; i < this.maxMemorizados; i++) {
			switch (i) {
			case 0:
				g.setColor(Color.red);
				break;
			case 1:
				g.setColor(Color.green);
				break;
			case 2:
				g.setColor(Color.blue);
				break;
			case 3:
				g.setColor(Color.orange);
				break;
			case 4:
				g.setColor(Color.yellow);
				break;
			case 5:
				g.setColor(Color.magenta);
				break;
			}

			for (int j = 1; j < 10; j++) {
				if (this.puntosCoincidencia[i][j] > 0.0D) {
					if (j == 1)
						g.drawLine((int) (this.xGC + 10 + 5 + (j - 1) * 0.1D
								* this.anchoGC), (int) (this.yGC + 0.5D
								* this.altoGC
								- this.puntosCoincidencia[i][(j - 1)] * 0.5D
								* this.altoGC + 5.0D), (int) (this.xGC + 15 + j
								* 0.1D * this.anchoGC), (int) (this.yGC + 0.5D
								* this.altoGC - this.puntosCoincidencia[i][j]
								* 0.5D * this.altoGC + 5.0D));
					else
						g.drawLine((int) (this.xGC + 15 + (j - 1) * 0.1D
								* this.anchoGC), (int) (this.yGC + 0.5D
								* this.altoGC
								- this.puntosCoincidencia[i][(j - 1)] * 0.5D
								* this.altoGC + 5.0D), (int) (this.xGC + 15 + j
								* 0.1D * this.anchoGC), (int) (this.yGC + 0.5D
								* this.altoGC - this.puntosCoincidencia[i][j]
								* 0.5D * this.altoGC + 5.0D));
				}
				if (this.puntosCoincidencia[i][j] >= 0.0D)
					continue;
				if (j == 1)
					g.drawLine((int) (this.xGC + 10 + 5 + (j - 1) * 0.1D
							* this.anchoGC), (int) (this.yGC + 0.5D
							* this.altoGC - this.puntosCoincidencia[i][(j - 1)]
							* 0.5D * this.altoGC - 5.0D),
							(int) (this.xGC + 15 + j * 0.1D * this.anchoGC),
							(int) (this.yGC + 0.5D * this.altoGC
									- this.puntosCoincidencia[i][j] * 0.5D
									* this.altoGC - 5.0D));
				else
					g.drawLine((int) (this.xGC + 15 + (j - 1) * 0.1D
							* this.anchoGC), (int) (this.yGC + 0.5D
							* this.altoGC - this.puntosCoincidencia[i][(j - 1)]
							* 0.5D * this.altoGC - 5.0D),
							(int) (this.xGC + 15 + j * 0.1D * this.anchoGC),
							(int) (this.yGC + 0.5D * this.altoGC
									- this.puntosCoincidencia[i][j] * 0.5D
									* this.altoGC - 5.0D));
			}
		}
		int escala;
		if (this.pixelsX < 7)
			escala = 1000;
		else {
			escala = 10000;
		}
		g.setColor(Color.white);
		g.fillRect(this.xGE, this.yGE + 2, (int) this.anchoGE + 5,
				(int) this.altoGE + 5);
		g.setColor(Color.black);
		g.drawLine(this.xGE + 5, this.yGE + (int) (0.9D * this.altoGE),
				this.xGE + (int) this.anchoGE - 5, this.yGE
						+ (int) (0.9D * this.altoGE));
		g.drawLine(this.xGE + 5, this.yGE + 5, this.xGE + 5, this.yGE
				+ (int) (0.9D * this.altoGE));
		for (int i = 1; i < 10; i++) {
			g.drawLine((int) (this.xGE + i * 0.1D * this.anchoGE), this.yGE
					+ (int) (0.9D * this.altoGE) + 3, (int) (this.xGE + i
					* 0.1D * this.anchoGE), this.yGE
					+ (int) (0.9D * this.altoGE) - 3);
		}
		g.setColor(Color.red);
		for (int i = 0; i < 10; i++)
			System.out.print(this.puntosEnergia[i] + " ");
		System.out.println();

		for (int i = 1; i < 10; i++) {
			if (this.puntosEnergia[i] != 0.0D) {
				if (i == 1)
					g.drawLine(
							(int) (this.xGE + 5 + (i - 1) * 0.1D * this.anchoGE),
							(int) (this.yGE + Math
									.abs(this.puntosEnergia[(i - 1)])
									/ escala
									* this.altoGE), (int) (this.xGE + i * 0.1D
									* this.anchoGE),
							(int) (this.yGE + Math.abs(this.puntosEnergia[i])
									/ escala * this.altoGE));
				else {
					g.drawLine(
							(int) (this.xGE + (i - 1) * 0.1D * this.anchoGE),
							(int) (this.yGE + Math
									.abs(this.puntosEnergia[(i - 1)])
									/ escala
									* this.altoGE), (int) (this.xGE + i * 0.1D
									* this.anchoGE),
							(int) (this.yGE + Math.abs(this.puntosEnergia[i])
									/ escala * this.altoGE));
				}
			}
		}
		g.setColor(Color.white);
		g.fillRect(this.xImg, this.yImg, this.ancho, this.alto);
		g.setColor(Color.red);
		for (int i = 0; i < this.pixelsX * this.pixelsY; i++) {
			int x = i % this.pixelsX;
			int y = i / this.pixelsX;
			if (this.ent[x][y] == 1.0D)
				g.fillRect(this.xImg + (int) (x * this.anchoCuadro), this.yImg
						+ (int) (y * this.altoCuadro), (int) this.anchoCuadro,
						(int) this.altoCuadro);
		}
		imprimirCuadricula(g);
	}

	public void borrarCuadricula(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(this.xImg, this.yImg, this.ancho, this.alto);
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		formMouseDragged(event); 		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		formMouseClicked(event);
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
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == button1){
			button1ActionPerformed(event);
		}else if(event.getSource() == button5){
			button5ActionPerformed(event);
		}else if(event.getSource() == button3){
			button3ActionPerformed(event);
		}else if(event.getSource() == button4){
			button4ActionPerformed(event);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if(event.getSource() == choice1){
			choice1ItemStateChanged(event);
		}
	}
}