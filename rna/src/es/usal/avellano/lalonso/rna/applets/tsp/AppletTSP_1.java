package es.usal.avellano.lalonso.rna.applets.tsp;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import es.usal.avellano.lalonso.rna.redes.SOM;

public class AppletTSP_1 extends JApplet implements Runnable, ActionListener {
	SOM mapa;
	double[][] pes;
	int numNeuronas = 0;
	int ancho;
	int alto;
	int anchoMapa;
	int altoMapa;
	int numCiudad = 0;

	int numCiudades = 0;

	int numCapitales = 9;

	int num10 = 17;

	int num5 = 24;

	int[] ciudadesX = { 100, 95, 100, 190, 180, 170, 250, 220, 340, 40, 80,
			100, 40, 90, 170, 300, 250, 300, 345, 210, 170, 110, 110, 245 };

	int[] ciudadesY = { 90, 200, 250, 150, 180, 290, 120, 250, 180, 100, 120,
			150, 290, 310, 220, 80, 180, 200, 230, 75, 255, 260, 310, 220 };

	int[] distanciaOptima = { 831, 1205, 1394 };

	double[] e = new double[2];

	ImageIcon ie = null;

	private Thread runner = null;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JComboBox jComboBox1;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JTextField jTextField3;

	public void init() {
		initComponents();

		this.numCiudades = 9;
		this.numNeuronas = (this.numCiudades * 3);

		this.jComboBox1.addItem("Capitales");
		this.jComboBox1.addItem("> 10.000 hab");
		this.jComboBox1.addItem("> 5.000 hab");

		this.ie = new ImageIcon(getImage(getDocumentBase(), "mapa.jpg"));

		this.ancho = 400;
		this.alto = 400;

		this.mapa = new SOM("44", "Prueba", 0.1D, this.numNeuronas / 3, 2,
				this.numNeuronas);
		this.pes = this.mapa.getPesos1D();
	}

	private void initComponents()
  {
    this.jLabel2 = new JLabel();
    this.jLabel3 = new JLabel();
    this.jLabel4 = new JLabel();
    this.jButton1 = new JButton();
    this.jButton2 = new JButton();
    this.jButton3 = new JButton();
    this.jButton4 = new JButton();
    this.jTextField3 = new JTextField();
    this.jLabel1 = new JLabel();
    this.jComboBox1 = new JComboBox();
    this.jLabel5 = new JLabel();
    this.jTextField1 = new JTextField();
    this.jTextField2 = new JTextField();

    getContentPane().setLayout(null);

    setBackground(new Color(192, 192, 192));
    this.jLabel2.setText("Ciudades:");
    this.jLabel2.setToolTipText("Número de ciudades a recorrer");
    getContentPane().add(this.jLabel2);
    this.jLabel2.setBounds(420, 90, 130, 17);

    this.jLabel3.setText("ARQUITECTURA");
    getContentPane().add(this.jLabel3);
    this.jLabel3.setBounds(420, 50, 110, 20);

    this.jLabel4.setText("ENTRENAMIENTO");
    getContentPane().add(this.jLabel4);
    this.jLabel4.setBounds(420, 190, 110, 17);

    this.jButton1.setToolTipText("Continúa el entrenamiento");
    this.jButton1.setText("Comenzar");
    this.jButton1.addActionListener(this);

    getContentPane().add(this.jButton1);
    this.jButton1.setBounds(440, 220, 110, 27);

    this.jButton2.setToolTipText("Pausa el entrenamiento");
    this.jButton2.setText("Pausar");
    this.jButton2.addActionListener(this);

    getContentPane().add(this.jButton2);
    this.jButton2.setBounds(440, 250, 110, 27);

    this.jButton3.setToolTipText("Reinicia los pesos del mapa");
    this.jButton3.setText("Reiniciar");
    this.jButton3.addActionListener(this);

    getContentPane().add(this.jButton3);
    this.jButton3.setBounds(440, 280, 110, 27);

    this.jButton4.setText("Salto");
    this.jButton4.addActionListener(this);

    getContentPane().add(this.jButton4);
    this.jButton4.setBounds(440, 320, 70, 30);

    this.jTextField3.setText("1000");
    getContentPane().add(this.jTextField3);
    this.jTextField3.setBounds(510, 320, 40, 30);

    this.jLabel1.setText("Distancia (Km):");
    this.jLabel1.setToolTipText("Distancia del camino calculado");
    getContentPane().add(this.jLabel1);
    this.jLabel1.setBounds(420, 120, 110, 17);

    getContentPane().add(this.jComboBox1);
    this.jComboBox1.setBounds(480, 84, 100, 26);

    this.jLabel5.setText("Distancia óptima: ");
    getContentPane().add(this.jLabel5);
    this.jLabel5.setBounds(420, 150, 110, 17);

    this.jTextField1.setEditable(false);
    getContentPane().add(this.jTextField1);
    this.jTextField1.setBounds(530, 120, 50, 21);

    this.jTextField2.setEditable(false);
    getContentPane().add(this.jTextField2);
    this.jTextField2.setBounds(530, 150, 50, 21);
  }

	private void jButton4ActionPerformed(ActionEvent evt) {
		int salto = Integer.valueOf(this.jTextField3.getText()).intValue();
		for (int i = 0; i < salto; i++)
			actualizarSOM();
		paintComponent(getGraphics());
	}

	private void jButton3ActionPerformed(ActionEvent evt) {
		this.jButton1.setText("Comenzar");
		this.jComboBox1.setEnabled(true);
		switch (this.jComboBox1.getSelectedIndex()) {
		case 0:
			this.jTextField2.setText("" + this.distanciaOptima[0]);
			this.numCiudades = this.numCapitales;
			break;
		case 1:
			this.jTextField2.setText("" + this.distanciaOptima[1]);
			this.numCiudades = this.num10;
			break;
		case 2:
			this.jTextField2.setText("" + this.distanciaOptima[2]);
			this.numCiudades = this.num5;
			break;
		}

		this.numNeuronas = (this.numCiudades * 3);

		this.mapa = new SOM("44", "Prueba", 0.1D, this.numNeuronas / 3, 2,
				this.numNeuronas);
		paintComponent(getGraphics());
	}

	private void jButton2ActionPerformed(ActionEvent evt) {
		this.jButton1.setEnabled(true);
		this.jButton3.setEnabled(true);
		this.jButton4.setEnabled(true);
		this.jButton2.setEnabled(false);
		this.jButton1.setText("Continuar");
		stop();
		this.jTextField1.setText("" + distancia());
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		if (this.jButton1.getText().equals("Comenzar"))
			jButton3ActionPerformed(null);
		this.jButton1.setEnabled(false);
		this.jButton3.setEnabled(false);
		this.jButton4.setEnabled(false);
		this.jComboBox1.setEnabled(false);
		this.jButton2.setEnabled(true);

		this.runner = new Thread(this);
		this.runner.setPriority(1);
		this.runner.start();
	}

	public void actualizarSOM() {
		int n = (int) (Math.random() * this.numCiudades);
		this.e[0] = (this.ciudadesX[n] / 400.0D - 0.5D + 0.01D);
		this.e[1] = (this.ciudadesY[n] / 400.0D - 0.5D + 0.01D);
		int ganadora = this.mapa.ejecutar(this.e);
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

		this.ie.paintIcon(this, g, 0, 0);

		g.setColor(Color.red);
		for (int i = 0; i < this.numCiudades; i++) {
			if (i < this.numCapitales) {
				g.fillRect(this.ciudadesX[i], this.ciudadesY[i], 7, 7);
			} else if (i < this.num10) {
				g.setColor(Color.blue);
				g.fillOval(this.ciudadesX[i], this.ciudadesY[i], 6, 6);
			} else {
				g.setColor(Color.black);
				g.drawOval(this.ciudadesX[i], this.ciudadesY[i], 4, 4);
			}

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

		this.jTextField1.setText("" + distancia());
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
		return (int) (distancia * 1.08D);
	}

	public void run() {
		int i = 0;

		while (this.runner != null) {
			actualizarSOM();
			if (i % 100 == 0)
				paintComponent(getGraphics());
			i++;
		}
	}

	public void stop() {
		this.runner = null;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == jButton1){
			jButton1ActionPerformed(event);
		}else if(event.getSource() == jButton2){
			jButton2ActionPerformed(event);
		}else if(event.getSource() == jButton3){
			jButton3ActionPerformed(event);
		}else if(event.getSource() == jButton4){
			jButton4ActionPerformed(event);
		}
	}
}