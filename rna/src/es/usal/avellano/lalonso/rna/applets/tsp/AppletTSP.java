package es.usal.avellano.lalonso.rna.applets.tsp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import es.usal.avellano.lalonso.rna.redes.SOM;

public class AppletTSP extends JApplet implements Runnable, ActionListener {
	SOM mapa;
	double[][] e;
	double[][] pes;
	int numNeuronas = 0;
	int ancho;
	int alto;
	int anchoCuadro;
	int altoCuadro;
	int anchoMapa;
	int altoMapa;
	int numCiudad = 0;

	int numCiudades = 0;

	private Thread runner = null;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JButton jButton5;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JTextField jTextField3;

	public void init() {
		initComponents();

		this.numCiudades = Integer.valueOf(this.jTextField1.getText()).intValue();
		this.numNeuronas = (this.numCiudades * 3);

		this.ancho = 400;
		this.alto = 400;
		this.anchoCuadro = (this.ancho / this.numCiudades);
		this.altoCuadro = (this.alto / this.numCiudades);
		System.out.println(this.anchoCuadro + " X " + this.altoCuadro + ", " + this.numNeuronas);

		this.mapa = new SOM("44", "Prueba", 0.1D, this.numNeuronas / 3, 2, this.numNeuronas);
		System.out.println("Mapa creado VERSIÓN 2 " + this.ancho + "x" + this.alto);
		this.pes = this.mapa.getPesos1D();
		jButton5ActionPerformed(null);
		paintComponent(getGraphics());
	}

	private void initComponents() {
		this.jLabel2 = new JLabel();
		this.jLabel3 = new JLabel();
		this.jTextField1 = new JTextField();
		this.jLabel4 = new JLabel();
		this.jButton1 = new JButton();
		this.jButton2 = new JButton();
		this.jButton3 = new JButton();
		this.jButton4 = new JButton();
		this.jTextField3 = new JTextField();
		this.jLabel1 = new JLabel();
		this.jTextField2 = new JTextField();
		this.jButton5 = new JButton();

		getContentPane().setLayout(null);

		setBackground(new Color(192, 192, 192));
		this.jLabel2.setText("Número de Ciudades: ");
		this.jLabel2.setToolTipText("Número de ciudades a recorrer");
		getContentPane().add(this.jLabel2);
		this.jLabel2.setBounds(420, 90, 130, 17);

		this.jLabel3.setText("ARQUITECTURA");
		getContentPane().add(this.jLabel3);
		this.jLabel3.setBounds(420, 50, 110, 20);

		this.jTextField1
				.setToolTipText("Nº de neuronas de la red en cada fila");
		this.jTextField1.setText("10");
		getContentPane().add(this.jTextField1);
		this.jTextField1.setBounds(550, 90, 30, 21);

		this.jLabel4.setText("ENTRENAMIENTO");
		getContentPane().add(this.jLabel4);
		this.jLabel4.setBounds(420, 160, 110, 17);

		this.jButton1.setToolTipText("Continúa el entrenamiento");
		this.jButton1.setText("Comenzar");
		this.jButton1.addActionListener(this);

		getContentPane().add(this.jButton1);
		this.jButton1.setBounds(440, 200, 110, 27);

		this.jButton2.setToolTipText("Pausa el entrenamiento");
		this.jButton2.setText("Pausar");
		this.jButton2.addActionListener(this);

		getContentPane().add(this.jButton2);
		this.jButton2.setBounds(440, 230, 110, 27);

		this.jButton3.setToolTipText("Reinicia los pesos del mapa");
		this.jButton3.setText("Reiniciar");
		this.jButton3.addActionListener(this);

		getContentPane().add(this.jButton3);
		this.jButton3.setBounds(440, 260, 110, 27);

		this.jButton4.setText("Salto");
		this.jButton4.addActionListener(this);

		getContentPane().add(this.jButton4);
		this.jButton4.setBounds(440, 320, 70, 30);

		this.jTextField3.setText("1000");
		getContentPane().add(this.jTextField3);
		this.jTextField3.setBounds(510, 320, 40, 30);

		this.jLabel1.setText("Distancia:");
		this.jLabel1.setToolTipText("Distancia del camino calculado");
		getContentPane().add(this.jLabel1);
		this.jLabel1.setBounds(420, 120, 70, 17);

		this.jTextField2.setToolTipText("Distancia del camino más corto");
		getContentPane().add(this.jTextField2);
		this.jTextField2.setBounds(520, 120, 60, 21);

		this.jButton5.setToolTipText("Calcula nuevas ciudades");
		this.jButton5.setText("Relocalizar");
		this.jButton5.addActionListener(this);

		getContentPane().add(this.jButton5);
		this.jButton5.setBounds(440, 290, 110, 27);
	}

	private void jButton5ActionPerformed(ActionEvent evt) {
		this.e = new double[this.numCiudades][2];
		for (int i = 0; i < this.numCiudades; i++) {
			this.e[i][0] = (Math.random() - 0.5D);
			this.e[i][1] = (Math.random() - 0.5D);
		}
		jButton3ActionPerformed(null);
		paintComponent(getGraphics());
	}

	private void jButton4ActionPerformed(ActionEvent evt) {
		int salto = Integer.valueOf(this.jTextField3.getText()).intValue();
		for (int i = 0; i < salto; i++)
			actualizarSOM();
		paintComponent(getGraphics());
	}

	private void jButton3ActionPerformed(ActionEvent evt) {
		this.jButton1.setText("Comenzar");
		this.numCiudades = Integer.valueOf(this.jTextField1.getText())
				.intValue();
		this.numNeuronas = (this.numCiudades * 3);
		this.anchoCuadro = (this.ancho / 10);
		this.altoCuadro = (this.alto / 10);

		this.mapa = new SOM("44", "Prueba", 0.1D, this.numNeuronas / 3, 2,
				this.numNeuronas);
		paintComponent(getGraphics());
	}

	private void jButton2ActionPerformed(ActionEvent evt) {
		this.jButton1.setEnabled(true);
		this.jButton3.setEnabled(true);
		this.jButton4.setEnabled(true);
		this.jButton5.setEnabled(true);
		this.jButton2.setEnabled(false);
		this.jButton1.setText("Continuar");
		stop();

		this.pes = this.mapa.getPesos1D();

		double distancia = 0.0D;

		for (int i = 0; i < this.numNeuronas; i++) {
			this.pes[i][0] += 0.5D;
			this.pes[i][1] += 0.5D;
		}
		for (int i = 0; i < this.numNeuronas - 1; i++) {
			int x1 = (int) (this.pes[i][0] * this.ancho);
			int y1 = (int) (this.pes[i][1] * this.alto);
			int x2 = (int) (this.pes[(i + 1)][0] * this.ancho);
			int y2 = (int) (this.pes[(i + 1)][1] * this.alto);
			distancia += Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)
					* (y1 - y2));
		}

		int x1 = (int) (this.pes[0][0] * this.ancho);
		int y1 = (int) (this.pes[0][1] * this.alto);
		int x2 = (int) (this.pes[(this.numNeuronas - 1)][0] * this.ancho);
		int y2 = (int) (this.pes[(this.numNeuronas - 1)][1] * this.alto);
		distancia += Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		this.jTextField2.setText("" + (int) distancia);
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		this.jButton1.setEnabled(false);
		this.jButton3.setEnabled(false);
		this.jButton4.setEnabled(false);
		this.jButton5.setEnabled(false);
		this.jButton2.setEnabled(true);
		this.runner = new Thread(this);
		this.runner.setPriority(1);
		this.runner.start();
	}

	public void actualizarSOM() {
		int ganadora = this.mapa
				.ejecutar(this.e[(int) (Math.random() * this.numCiudades)]);
	}

	public void paintComponent(Graphics g) {
		int k = 0;

		double distancia = 0.0D;

		g.setColor(Color.white);
		g.fillRect(0, 0, this.ancho, this.alto);

		this.pes = this.mapa.getPesos1D();
		for (int i = 0; i < this.numNeuronas; i++) {
			this.pes[i][0] += 0.5D;
			this.pes[i][1] += 0.5D;
		}

		g.setColor(Color.red);
		for (int i = 0; i < this.numCiudades; i++) {
			g.fillOval((int) ((this.e[i][0] + 0.5D) * this.ancho - 5.0D),
					(int) ((this.e[i][1] + 0.5D) * this.alto - 5.0D), 7, 7);
		}

		g.setColor(Color.lightGray);
		g.drawRect(0, 0, this.ancho - 1, this.alto - 1);

		g.setColor(Color.lightGray);
		for (int i = 0; i < this.ancho; i += this.anchoCuadro)
			g.drawLine(i, 0, i, this.alto - 1);
		for (int j = 0; j < this.alto; j += this.altoCuadro) {
			g.drawLine(0, j, this.ancho, j);
		}

		g.setColor(Color.gray);
		for (int i = 0; i < this.numNeuronas - 1; i++) {
			int x1 = (int) (this.pes[i][0] * this.ancho);
			int y1 = (int) (this.pes[i][1] * this.alto);
			int x2 = (int) (this.pes[(i + 1)][0] * this.ancho);
			int y2 = (int) (this.pes[(i + 1)][1] * this.alto);
			g.drawLine(x1, y1, x2, y2);
		}

		int x1 = (int) (this.pes[0][0] * this.ancho);
		int y1 = (int) (this.pes[0][1] * this.alto);
		int x2 = (int) (this.pes[(this.numNeuronas - 1)][0] * this.ancho);
		int y2 = (int) (this.pes[(this.numNeuronas - 1)][1] * this.alto);
		g.drawLine(x1, y1, x2, y2);

		g.setColor(Color.black);
		for (int i = 0; i < this.numNeuronas; i++) {
			x1 = (int) (this.pes[i][0] * this.ancho);
			y1 = (int) (this.pes[i][1] * this.alto);
			g.fillOval(x1 - 1, y1 - 1, 3, 3);
		}
		this.jTextField2.setText("" + distancia());
	}

	public int distancia() {
		this.pes = this.mapa.getPesos1D();

		double distancia = 0.0D;

		for (int i = 0; i < this.numNeuronas; i++) {
			this.pes[i][0] += 0.5D;
			this.pes[i][1] += 0.5D;
		}
		for (int i = 0; i < this.numNeuronas - 1; i++) {
			int x1 = (int) (this.pes[i][0] * this.ancho);
			int y1 = (int) (this.pes[i][1] * this.alto);
			int x2 = (int) (this.pes[(i + 1)][0] * this.ancho);
			int y2 = (int) (this.pes[(i + 1)][1] * this.alto);
			distancia += Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)
					* (y1 - y2));
		}

		int x1 = (int) (this.pes[0][0] * this.ancho);
		int y1 = (int) (this.pes[0][1] * this.alto);
		int x2 = (int) (this.pes[(this.numNeuronas - 1)][0] * this.ancho);
		int y2 = (int) (this.pes[(this.numNeuronas - 1)][1] * this.alto);
		distancia += Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return (int) distancia;
	}

	public void run() {
		int i = 0;

		while (this.runner != null) {
			actualizarSOM();
			if (i % 100 == 0) {
				paintComponent(getGraphics());
				try {
					Thread.currentThread();
					Thread.sleep(50L);
				} catch (InterruptedException e) {
					System.out.println("Error durante el sleep: "
							+ e.getMessage());
				}
			}
			i++;
		}
	}

	public void stop() {
		this.runner = null;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == jButton1) {
			jButton1ActionPerformed(event);
		} else if (event.getSource() == jButton2) {
			jButton2ActionPerformed(event);
		} else if (event.getSource() == jButton3) {
			jButton3ActionPerformed(event);
		} else if (event.getSource() == jButton4) {
			jButton4ActionPerformed(event);
		} else if (event.getSource() == jButton5) {
			jButton5ActionPerformed(event);
		}
	}
}