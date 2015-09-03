package es.usal.avellano.lalonso.rna.applets.red;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import es.usal.avellano.lalonso.rna.redes.SOM;

public class AppletRED extends JApplet implements Runnable , ActionListener{
	SOM mapa;
	double[] e = new double[2];

	LinkedList dims = new LinkedList();
	int numNeuronas;
	int ancho;
	int alto;
	int anchoCuadro;
	int altoCuadro;
	double ratio = 0.0D;

	private Thread runner = null;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JComboBox jComboBox1;
	private JComboBox jComboBox2;
	private JLabel jLabel1;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JTextField jTextField2;
	private JTextField jTextField3;

	public void init() {
		initComponents();

		this.jComboBox1.addItem("Cuadrado");
		this.jComboBox1.addItem("Triángulo");
		this.jComboBox1.addItem("Anillo");
		this.jComboBox1.addItem("Cruz");
		this.jComboBox1.addItem("Dos Cuadrados");

		this.jComboBox2.addItem("2D");
		this.jComboBox2.addItem("1D");

		this.ancho = 400;
		this.alto = 400;
	}

	private void initComponents()
  {
    this.jLabel1 = new JLabel();
    this.jLabel3 = new JLabel();
    this.jTextField2 = new JTextField();
    this.jLabel4 = new JLabel();
    this.jButton1 = new JButton();
    this.jButton2 = new JButton();
    this.jButton3 = new JButton();
    this.jLabel5 = new JLabel();
    this.jComboBox1 = new JComboBox();
    this.jButton4 = new JButton();
    this.jTextField3 = new JTextField();
    this.jLabel6 = new JLabel();
    this.jComboBox2 = new JComboBox();

    getContentPane().setLayout(null);

    setBackground(new Color(192, 192, 192));
    this.jLabel1.setText("Número de neuronas:");
    this.jLabel1.setToolTipText("Neuronas en cada dimensión");
    getContentPane().add(this.jLabel1);
    this.jLabel1.setBounds(420, 60, 130, 17);

    this.jLabel3.setText("ARQUITECTURA");
    getContentPane().add(this.jLabel3);
    this.jLabel3.setBounds(420, 20, 110, 20);

    this.jTextField2.setToolTipText("Nº de neuronas de la red en cada columna");
    this.jTextField2.setText("10");
    getContentPane().add(this.jTextField2);
    this.jTextField2.setBounds(560, 60, 30, 21);

    this.jLabel4.setText("ENTRENAMIENTO");
    getContentPane().add(this.jLabel4);
    this.jLabel4.setBounds(420, 200, 110, 17);

    this.jButton1.setToolTipText("Continúa el entrenamiento");
    this.jButton1.setText("Comenzar");
    this.jButton1.addActionListener(this);

    getContentPane().add(this.jButton1);
    this.jButton1.setBounds(440, 230, 110, 27);

    this.jButton2.setToolTipText("Pausa el entrenamiento del mapa");
    this.jButton2.setText("Pausar");
    this.jButton2.addActionListener(this);

    getContentPane().add(this.jButton2);
    this.jButton2.setBounds(440, 260, 110, 27);

    this.jButton3.setToolTipText("Reinicia los pesos del mapa");
    this.jButton3.setText("Reiniciar");
    this.jButton3.addActionListener(this);

    getContentPane().add(this.jButton3);
    this.jButton3.setBounds(440, 290, 110, 27);

    this.jLabel5.setText("Forma de la red:");
    this.jLabel5.setToolTipText("Forma que adaptará la red");
    getContentPane().add(this.jLabel5);
    this.jLabel5.setBounds(420, 130, 100, 17);

    getContentPane().add(this.jComboBox1);
    this.jComboBox1.setBounds(420, 150, 130, 26);

    this.jButton4.setText("Salto");
    this.jButton4.addActionListener(this);

    getContentPane().add(this.jButton4);
    this.jButton4.setBounds(440, 320, 70, 30);

    this.jTextField3.setText("1000");
    getContentPane().add(this.jTextField3);
    this.jTextField3.setBounds(510, 320, 40, 30);

    this.jLabel6.setText("Dimensión de la red:");
    this.jLabel6.setToolTipText("Organización dimensional de la red");
    getContentPane().add(this.jLabel6);
    this.jLabel6.setBounds(420, 100, 120, 17);

    getContentPane().add(this.jComboBox2);
    this.jComboBox2.setBounds(540, 90, 50, 26);
  }

	private void jButton4ActionPerformed(ActionEvent evt) {
		int salto = Integer.valueOf(this.jTextField3.getText()).intValue();
		for (int i = 0; i < salto; i++)
			actualizarSOM();
		this.jButton1.setText("Continuar");
		paintComponent(getGraphics());
		pintarForma(getGraphics());
	}

	private void jButton3ActionPerformed(ActionEvent evt) {
		this.jButton1.setText("Comenzar");
		this.dims = new LinkedList();
		this.numNeuronas = Integer.valueOf(this.jTextField2.getText())
				.intValue();
		this.anchoCuadro = (this.ancho / 10);
		this.altoCuadro = (this.alto / 10);
		this.ratio = (this.numNeuronas / 3);
		if (this.ratio <= 1.0D)
			this.ratio += 1.0D;
		if (this.jComboBox2.getSelectedIndex() == 0) {
			System.out.println("Dos dimensiones");
			this.dims.add(new Integer(this.numNeuronas));
			this.dims.add(new Integer(this.numNeuronas));
		} else {
			System.out.println("Una dimensión");
			this.dims.add(new Integer(this.numNeuronas));
		}

		this.mapa = new SOM("44", "Prueba", 0.1D, this.ratio, this.dims);
		paintComponent(getGraphics());
		pintarForma(getGraphics());
	}

	private void jButton2ActionPerformed(ActionEvent evt) {
		this.jButton1.setEnabled(true);
		this.jButton3.setEnabled(true);
		this.jButton4.setEnabled(true);
		this.jButton2.setEnabled(false);
		this.jButton1.setText("Continuar");
		stop();
		pintarForma(getGraphics());
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		this.jButton1.setEnabled(false);
		this.jButton3.setEnabled(false);
		this.jButton4.setEnabled(false);
		this.jButton2.setEnabled(true);
		if (this.jButton1.getText().equals("Comenzar"))
			jButton3ActionPerformed(null);
		this.runner = new Thread(this);
		this.runner.setPriority(1);
		this.runner.start();
	}

	public void actualizarSOM() {
		this.e = new double[2];

		switch (this.jComboBox1.getSelectedIndex()) {
		case 0:
			this.e[0] = (Math.random() - 0.5D);
			this.e[1] = (Math.random() - 0.5D);
			break;
		case 1:
			this.e[1] = Math.random();
			this.e[0] = (this.ancho / 2 + Math.IEEEremainder(Math.random()
					* this.ancho, this.e[1] * this.alto));
			this.e[1] -= 0.5D;
			this.e[0] = (this.e[0] / this.ancho - 0.5D);
			break;
		case 2:
			double delta = 6.283185307179586D;
			double angulo = Math.random() * delta;
			double amplitud = Math.random() * 10.0D;
			amplitud = (amplitud % 3.0D + 2.0D) / 10.0D;

			this.e[0] = (amplitud * Math.cos(angulo));
			this.e[1] = (amplitud * Math.sin(angulo));
			break;
		case 3:
			this.e[1] = (Math.random() - 0.5D);
			if ((this.e[1] > -0.3D) && (this.e[1] < -0.1D))
				this.e[0] = ((Math.random() - 0.5D) * 10.0D % 4.0D / 10.0D);
			else
				this.e[0] = ((Math.random() - 0.5D) * 10.0D % 1.0D / 10.0D);
			break;
		case 4:
			this.e[0] = ((Math.random() - 0.5D) * 10.0D % 1.0D / 10.0D);
			this.e[1] = (((Math.random() - 0.5D) * 10.0D % 1.0D + 3.0D) / 10.0D);
			if (Math.random() - 0.5D < 0.0D)
				break;
			this.e[1] = (((Math.random() - 0.5D) * 10.0D % 1.0D - 3.0D) / 10.0D);
			break;
		default:
			System.out.println("Default");
		}

		int ganadora = this.mapa.ejecutar(this.e);
	}

	public void paintComponent(Graphics g) {
		int k = 0;

		g.setColor(Color.lightGray);
		g.fillRect(this.ancho, 0, 6, this.alto);
		g.setColor(Color.white);
		g.fillRect(0, 0, this.ancho, this.alto);

		g.setColor(Color.red);
		g.fillOval((int) ((this.e[0] + 0.5D) * this.ancho),
				(int) ((this.e[1] + 0.5D) * this.alto), 7, 7);

		g.setColor(Color.lightGray);
		g.drawRect(0, 0, this.ancho - 1, this.alto - 1);

		g.setColor(Color.lightGray);
		for (int i = 0; i < this.ancho; i += this.anchoCuadro)
			g.drawLine(i, 0, i, this.alto - 1);
		for (int j = 0; j < this.alto; j += this.altoCuadro) {
			g.drawLine(0, j, this.ancho, j);
		}

		if (this.jComboBox2.getSelectedIndex() == 0) {
			double[][][] pes = this.mapa.getPesos();

			for (int i = 0; i < this.numNeuronas; i++)
				for (int j = 0; j < this.numNeuronas; j++) {
					pes[i][j][0] += 0.5D;
					pes[i][j][1] += 0.5D;
				}
			g.setColor(Color.gray);
			for (int i = 0; i < this.numNeuronas; i++) {
				for (int j = 0; j < this.numNeuronas - 1; j++) {
					int x1 = (int) (pes[i][j][0] * this.ancho);
					int y1 = (int) (pes[i][j][1] * this.alto);
					int x2 = (int) (pes[i][(j + 1)][0] * this.ancho);
					int y2 = (int) (pes[i][(j + 1)][1] * this.alto);
					g.drawLine(x1, y1, x2, y2);
				}
			}

			g.setColor(Color.gray);
			for (int j = 0; j < this.numNeuronas; j++) {
				for (int i = 0; i < this.numNeuronas - 1; i++) {
					int x1 = (int) (pes[i][j][0] * this.ancho);
					int y1 = (int) (pes[i][j][1] * this.alto);
					int x2 = (int) (pes[(i + 1)][j][0] * this.ancho);
					int y2 = (int) (pes[(i + 1)][j][1] * this.alto);
					g.drawLine(x1, y1, x2, y2);
				}
			}

			g.setColor(Color.black);
			for (int j = 0; j < this.numNeuronas; j++) {
				for (int i = 0; i < this.numNeuronas; i++) {
					int x1 = (int) (pes[i][j][0] * this.ancho);
					int y1 = (int) (pes[i][j][1] * this.alto);
					g.fillOval(x1 - 1, y1 - 1, 3, 3);
				}

			}

		} else {
			double[][] pes = this.mapa.getPesos1D();
			for (int j = 0; j < this.numNeuronas; j++) {
				pes[j][0] += 0.5D;
				pes[j][1] += 0.5D;
			}
			g.setColor(Color.gray);
			for (int i = 0; i < this.numNeuronas - 1; i++) {
				int x1 = (int) (pes[i][0] * this.ancho);
				int y1 = (int) (pes[i][1] * this.alto);
				int x2 = (int) (pes[(i + 1)][0] * this.ancho);
				int y2 = (int) (pes[(i + 1)][1] * this.alto);
				g.drawLine(x1, y1, x2, y2);
			}
			g.drawLine((int) (pes[0][0] * this.ancho),
					(int) (pes[0][1] * this.alto),
					(int) (pes[(this.numNeuronas - 1)][0] * this.ancho),
					(int) (pes[(this.numNeuronas - 1)][1] * this.alto));

			g.setColor(Color.black);
			for (int i = 0; i < this.numNeuronas; i++) {
				int x1 = (int) (pes[i][0] * this.ancho);
				int y1 = (int) (pes[i][1] * this.alto);
				g.fillOval(x1 - 1, y1 - 1, 3, 3);
			}
		}

		pintarForma(getGraphics());
		g.fillOval((int) ((this.e[0] + 0.5D) * this.ancho),
				(int) ((this.e[1] + 0.5D) * this.alto), 3, 3);
	}

	public void pintarForma(Graphics g) {
		g.setColor(Color.red);
		switch (this.jComboBox1.getSelectedIndex()) {
		case 0:
			g.drawLine(5, 5, 5, this.alto - 5);
			g.drawLine(5, 5, this.ancho - 5, 5);
			g.drawLine(this.ancho - 5, 5, this.ancho - 5, this.alto - 5);
			g.drawLine(5, this.alto - 5, this.ancho - 5, this.alto - 5);
			break;
		case 1:
			g.drawLine(this.ancho / 2, 5, 5, this.alto - 5);
			g.drawLine(this.ancho / 2, 5, this.ancho - 5, this.alto - 5);
			g.drawLine(5, this.alto - 5, this.ancho - 5, this.alto - 5);
			break;
		case 2:
			g.drawOval(this.ancho / 2 - this.ancho * 2 / 10, this.alto / 2
					- this.alto * 2 / 10, this.ancho * 4 / 10,
					this.alto * 4 / 10);
			g.drawOval(this.ancho / 2 - this.ancho * 5 / 10, this.alto / 2
					- this.alto * 5 / 10, this.ancho, this.alto);
			break;
		case 3:
			g.drawLine(this.ancho * 4 / 10, 5, this.ancho * 6 / 10, 5);
			g.drawLine(this.ancho * 4 / 10, 5, this.ancho * 4 / 10,
					this.alto * 2 / 10);
			g.drawLine(this.ancho * 6 / 10, 5, this.ancho * 6 / 10,
					this.alto * 2 / 10);

			g.drawLine(this.ancho * 1 / 10, this.alto * 2 / 10,
					this.ancho * 4 / 10, this.alto * 2 / 10);
			g.drawLine(this.ancho * 6 / 10, this.alto * 2 / 10,
					this.ancho * 9 / 10, this.alto * 2 / 10);
			g.drawLine(this.ancho * 1 / 10, this.alto * 2 / 10,
					this.ancho * 1 / 10, this.alto * 4 / 10);
			g.drawLine(this.ancho * 9 / 10, this.alto * 2 / 10,
					this.ancho * 9 / 10, this.alto * 4 / 10);
			g.drawLine(this.ancho * 1 / 10, this.alto * 4 / 10,
					this.ancho * 4 / 10, this.alto * 4 / 10);
			g.drawLine(this.ancho * 6 / 10, this.alto * 4 / 10,
					this.ancho * 9 / 10, this.alto * 4 / 10);

			g.drawLine(this.ancho * 4 / 10, this.alto * 4 / 10,
					this.ancho * 4 / 10, this.alto - 5);
			g.drawLine(this.ancho * 6 / 10, this.alto * 4 / 10,
					this.ancho * 6 / 10, this.alto - 5);

			g.drawLine(this.ancho * 4 / 10, this.alto - 5, this.ancho * 6 / 10,
					this.alto - 5);

			break;
		case 4:
			g.drawLine(this.ancho * 4 / 10, this.alto * 1 / 10,
					this.ancho * 6 / 10, this.alto * 1 / 10);
			g.drawLine(this.ancho * 4 / 10, this.alto * 1 / 10,
					this.ancho * 4 / 10, this.alto * 3 / 10);
			g.drawLine(this.ancho * 6 / 10, this.alto * 1 / 10,
					this.ancho * 6 / 10, this.alto * 3 / 10);
			g.drawLine(this.ancho * 4 / 10, this.alto * 3 / 10,
					this.ancho * 6 / 10, this.alto * 3 / 10);

			g.drawLine(this.ancho * 4 / 10, this.alto * 7 / 10,
					this.ancho * 6 / 10, this.alto * 7 / 10);
			g.drawLine(this.ancho * 4 / 10, this.alto * 7 / 10,
					this.ancho * 4 / 10, this.alto * 9 / 10);
			g.drawLine(this.ancho * 6 / 10, this.alto * 7 / 10,
					this.ancho * 6 / 10, this.alto * 9 / 10);
			g.drawLine(this.ancho * 4 / 10, this.alto * 9 / 10,
					this.ancho * 6 / 10, this.alto * 9 / 10);

			break;
		default:
			System.out.println("Default");
		}
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