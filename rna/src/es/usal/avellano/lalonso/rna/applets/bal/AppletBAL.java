package es.usal.avellano.lalonso.rna.applets.bal;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.peer.ChoicePeer;
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

public class AppletBAL extends Applet implements Runnable, ActionListener,
		TextListener, ItemListener {
	private MLP multi;
	int numEjs = 168;

	int numPru = 12;

	Ejemplo[] ejs = new Ejemplo[this.numEjs];

	Ejemplo[] pru = new Ejemplo[this.numPru];

	double[] e = new double[15];

	double[] s = new double[12];

	Juego[] jejs = new Juego[1];

	Juego[] jpru = new Juego[1];
	Point[] puntos;
	int numIteraciones = 0;

	boolean continuar = false;

	String[] armas = { "Pistola con munición 1", "Pistola con munición 2",
			"Revólver con munición 3", "Revólver con munición 4",
			"Revólver con munición 5", "Revólver con munición 6",
			"Escopeta con munición 7", "Escopeta con munición 8",
			"Fusil con munición 9", "Fusil con munición 10",
			"Cetme con munición 9", "Cetme con munición 10" };

	private Thread runner = null;
	private Button button1;
	private Button button2;
	private Button button5;
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
	private Label label15;
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

		this.choice1.add("Pistola");
		this.choice1.add("Revólver");
		this.choice1.add("Escopeta");
		this.choice1.add("Fusil");
		this.choice1.add("Cetme");

		this.choice2.add("Lotes");
		this.choice2.add("Individual");

		this.choice3.setEnabled(false);

		this.button1.setEnabled(false);
		try {
			URL fich = getClass().getResource("ejemplosCortas.dat");
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
			String cad;
			while (((cad = in.readLine()) != null) && (i < this.numEjs)) {
				while ((cad.indexOf("\t") != -1) && (j < 15)) {
					this.e[j] = (new Double(cad.substring(0, cad.indexOf("\t")))
							.doubleValue() - 0.5D);
					cad = cad.substring(cad.indexOf("\t") + 1);
					j++;
				}
				for (k = 0; k < 12; k++)
					this.s[k] = -0.5D;
				if (cad.trim().equals("Pist1"))
					this.s[0] = 0.5D;
				if (cad.trim().equals("Pist2"))
					this.s[1] = 0.5D;
				if (cad.trim().equals("Rev3"))
					this.s[2] = 0.5D;
				if (cad.trim().equals("Rev4"))
					this.s[3] = 0.5D;
				if (cad.trim().equals("Rev5"))
					this.s[4] = 0.5D;
				if (cad.trim().equals("Rev6"))
					this.s[5] = 0.5D;
				this.ejs[i] = new Ejemplo(this.e, this.s);
				i++;
				j = 0;
			}
			in.close();
			conn = null;
			in = null;

			fich = getClass().getResource("ejemplosLargas.dat");
			conn = fich.openConnection();
			conn.connect();
			is = conn.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			in.readLine();

			while (((cad = in.readLine()) != null) && (i < this.numEjs)) {
				while ((cad.indexOf("\t") != -1) && (j < 15)) {
					this.e[j] = (new Double(cad.substring(0, cad.indexOf("\t")))
							.doubleValue() - 0.5D);
					cad = cad.substring(cad.indexOf("\t") + 1);
					j++;
				}
				for (k = 0; k < 12; k++)
					this.s[k] = -0.5D;
				if (cad.trim().equals("Esc7"))
					this.s[6] = 0.5D;
				if (cad.trim().equals("Esc8"))
					this.s[7] = 0.5D;
				if (cad.trim().equals("Fus9"))
					this.s[8] = 0.5D;
				if (cad.trim().equals("Fus10"))
					this.s[9] = 0.5D;
				if (cad.trim().equals("Cet9"))
					this.s[10] = 0.5D;
				if (cad.trim().equals("Cet10"))
					this.s[11] = 0.5D;

				this.ejs[i] = new Ejemplo(this.e, this.s);
				i++;
				j = 0;
			}

			in.close();
			this.jejs[0] = new Juego(this.ejs);

			conn = null;
			in = null;

			fich = getClass().getResource("pruebasBAL.dat");
			conn = fich.openConnection();
			conn.connect();
			is = conn.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			in.readLine();

			i = 0;
			while (((cad = in.readLine()) != null) && (i < this.numPru)) {
				while ((cad.indexOf("\t") != -1) && (j < 15)) {
					this.e[j] = (new Double(cad.substring(0, cad.indexOf("\t")))
							.doubleValue() - 0.5D);
					cad = cad.substring(cad.indexOf("\t") + 1);
					j++;
				}

				for (k = 0; k < 12; k++)
					this.s[k] = -0.5D;
				this.s[i] = 0.5D;
				this.pru[i] = new Ejemplo(this.e, this.s);
				i++;
				j = 0;
			}
			in.close();
			this.jpru[0] = new Juego(this.pru);
		} catch (Exception e) {
			System.err.println("Error en la lectura: " + e.getMessage());
		}
	}

	private void initComponents()
  {
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
    this.choice3 = new Choice();
    this.label1 = new Label();
    this.label2 = new Label();
    this.button5 = new Button();
    this.label3 = new Label();
    this.label14 = new Label();
    this.label15 = new Label();
    this.textField2 = new TextField();

    setLayout(null);

    setBackground(new Color(192, 192, 192));
    setForeground(new Color(0, 0, 255));
    this.lable1.setFont(new Font("Dialog", 1, 14));
    this.lable1.setForeground(new Color(100, 100, 150));
    this.lable1.setText("EJECUCIÓN");
    add(this.lable1);
    this.lable1.setBounds(30, 20, 110, 23);

    this.button1.setForeground(new Color(0, 0, 0));
    this.button1.setLabel("Disparar");
    this.button1.addActionListener(this);

    add(this.button1);
    this.button1.setBounds(130, 110, 130, 24);

    this.label4.setFont(new Font("Dialog", 1, 14));
    this.label4.setForeground(new Color(100, 100, 150));
    this.label4.setText("ARQUITECTURA");
    add(this.label4);
    this.label4.setBounds(50, 280, 120, 21);

    this.label5.setForeground(new Color(0, 0, 0));
    this.label5.setText("Nº de neuronas ocultas: ");
    add(this.label5);
    this.label5.setBounds(70, 320, 140, 20);

    this.textField4.setText("16");
    this.textField4.setForeground(new Color(0, 0, 0));
    this.textField4.addTextListener(this);

    add(this.textField4);
    this.textField4.setBounds(210, 320, 30, 20);

    this.label6.setFont(new Font("Dialog", 1, 14));
    this.label6.setForeground(new Color(100, 100, 150));
    this.label6.setText("ENTRENAMIENTO");
    add(this.label6);
    this.label6.setBounds(450, 20, 130, 23);

    this.label7.setForeground(new Color(0, 0, 0));
    this.label7.setText("Momento");
    add(this.label7);
    this.label7.setBounds(450, 50, 70, 20);

    this.label8.setForeground(new Color(0, 0, 0));
    this.label8.setText("Factor de Aprendizaje");
    add(this.label8);
    this.label8.setBounds(450, 70, 122, 20);

    this.textField5.setText("0.01");
    this.textField5.setForeground(new Color(0, 0, 0));
    add(this.textField5);
    this.textField5.setBounds(590, 50, 30, 20);

    this.textField6.setText("0.01");
    this.textField6.setForeground(new Color(0, 0, 0));
    add(this.textField6);
    this.textField6.setBounds(590, 70, 30, 20);

    this.label9.setForeground(new Color(0, 0, 0));
    this.label9.setText("Nº de iteraciones");
    add(this.label9);
    this.label9.setBounds(450, 100, 110, 20);

    this.textField7.setText("400");
    this.textField7.setForeground(new Color(0, 0, 0));
    add(this.textField7);
    this.textField7.setBounds(610, 100, 40, 20);

    this.label10.setForeground(new Color(0, 0, 0));
    this.label10.setText("Error máximo permitido");
    add(this.label10);
    this.label10.setBounds(450, 120, 140, 20);

    this.textField8.setText("0.1");
    this.textField8.setForeground(new Color(0, 0, 0));
    add(this.textField8);
    this.textField8.setBounds(610, 120, 40, 20);

    this.button2.setForeground(new Color(0, 0, 0));
    this.button2.setLabel("Comenzar");
    this.button2.addActionListener(this);

    add(this.button2);
    this.button2.setBounds(670, 70, 70, 24);

    this.label11.setFont(new Font("Dialog", 1, 12));
    this.label11.setForeground(new Color(100, 100, 150));
    this.label11.setText("Gráfica de Error");
    add(this.label11);
    this.label11.setBounds(450, 190, 130, 20);

    this.choice1.setForeground(new Color(0, 0, 0));
    this.choice1.addItemListener(this);

    add(this.choice1);
    this.choice1.setBounds(50, 70, 110, 20);

    this.label12.setForeground(new Color(0, 0, 0));
    this.label12.setText("Error máximo representado");
    add(this.label12);
    this.label12.setBounds(450, 140, 160, 20);

    this.textField1.setText("2");
    this.textField1.setForeground(new Color(0, 0, 0));
    add(this.textField1);
    this.textField1.setBounds(610, 140, 40, 20);

    this.label13.setForeground(new Color(0, 0, 0));
    this.label13.setText("Tipo de Aprendizaje:");
    add(this.label13);
    this.label13.setBounds(70, 340, 140, 20);

    this.choice2.setForeground(new Color(0, 0, 0));
    add(this.choice2);
    this.choice2.setBounds(210, 340, 110, 20);

    this.button7.setForeground(new Color(0, 0, 0));
    this.button7.setLabel("Pausar");
    this.button7.addActionListener(this);

    add(this.button7);
    this.button7.setBounds(670, 100, 70, 24);

    this.button8.setForeground(new Color(0, 0, 0));
    this.button8.setLabel("Cargar Pesos");
    this.button8.addActionListener(this);

    add(this.button8);
    this.button8.setBounds(140, 390, 110, 24);

    this.choice3.setForeground(new Color(0, 0, 0));
    this.choice3.addItemListener(this);

    add(this.choice3);
    this.choice3.setBounds(220, 70, 120, 20);

    this.label1.setFont(new Font("Dialog", 2, 14));
    this.label1.setForeground(new Color(255, 0, 0));
    this.label1.setText("Arma Reconocida (seguridad):");
    add(this.label1);
    this.label1.setBounds(50, 160, 230, 23);

    this.label2.setForeground(new Color(0, 0, 0));
    add(this.label2);
    this.label2.setBounds(150, 190, 250, 20);

    this.button5.setForeground(new Color(0, 0, 0));
    this.button5.setLabel("Reiniciar");
    this.button5.addActionListener(this);

    add(this.button5);
    this.button5.setBounds(670, 150, 70, 24);

    this.label3.setBackground(new Color(192, 192, 192));
    this.label3.setFont(new Font("Dialog", 2, 14));
    this.label3.setForeground(new Color(0, 0, 255));
    this.label3.setText("Munición Reconocida (seguridad):");
    add(this.label3);
    this.label3.setBounds(50, 220, 250, 23);

    this.label14.setForeground(new Color(0, 0, 0));
    add(this.label14);
    this.label14.setBounds(150, 250, 220, 20);

    this.label15.setForeground(new Color(0, 0, 0));
    this.label15.setText("Error actual");
    add(this.label15);
    this.label15.setBounds(450, 160, 100, 20);

    this.textField2.setEditable(false);
    this.textField2.setForeground(new Color(0, 0, 0));
    add(this.textField2);
    this.textField2.setBounds(610, 160, 40, 20);
  }

	private void textField4TextValueChanged(TextEvent evt) {
		if (this.textField4.getText().equals("16"))
			this.button8.setEnabled(true);
		else
			this.button8.setEnabled(false);
	}

	private void button5ActionPerformed(ActionEvent evt) {
		this.multi.reiniciarPesos();
		stop();
		this.button2.setLabel("Comenzar");
		this.textField2.setText("");
		this.textField1.setEnabled(true);
	}

	private void choice3ItemStateChanged(ItemEvent evt) {
		this.button1.setEnabled(true);
	}

	private void choice1ItemStateChanged(ItemEvent evt) {
		this.choice3.removeAll();
		switch (this.choice1.getSelectedIndex()) {
		case 0:
			this.choice3.add("Munición 1");
			this.choice3.add("Munición 2");
			break;
		case 1:
			this.choice3.add("Munición 3");
			this.choice3.add("Munición 4");
			this.choice3.add("Munición 5");
			this.choice3.add("Munición 6");
			break;
		case 2:
			this.choice3.add("Munición 7");
			this.choice3.add("Munición 8");
			break;
		case 3:
			this.choice3.add("Munición 9");
			this.choice3.add("Munición 10");
			break;
		case 4:
			this.choice3.add("Munición 9");
			this.choice3.add("Munición 10");
			break;
		}

		this.choice3.setEnabled(true);
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

			this.multi = new MLP("22", "BAL", this.jejs, this.jpru, new Double(
					this.textField5.getText()).doubleValue(), new Double(
					this.textField6.getText()).doubleValue(), new Double(
					this.textField6.getText()).doubleValue(), 15, new Integer(
					this.textField4.getText()).intValue(), 12);
			this.multi.cargarPesos(
					getClass().getResource(
							"bal" + this.multi.getNne() + this.multi.getNno()
									+ this.multi.getNns() + "eo.pes"),
					getClass().getResource(
							"bal" + this.multi.getNne() + this.multi.getNno()
									+ this.multi.getNns() + "os.pes"));
			this.textField2.setText("" + this.multi.error());

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
			e.printStackTrace();
			System.err.println("Error durante la carga: " + e.getMessage());
		}
	}

	private void button7ActionPerformed(ActionEvent evt) {
		this.continuar = true;
		this.button2.setLabel("Continuar");
		stop();
	}

	private void button1ActionPerformed(ActionEvent evt) {
		AudioClip disparo = null;
		java.net.URL disparoURL;
		switch (this.choice1.getSelectedIndex()) {
		case 0:
//			disparo = getAudioClip(getDocumentBase(), "pistola.wav");
			disparoURL = getClass().getResource("pistola.wav");
			disparo = getAudioClip(disparoURL);
			
			disparo.play();
			if (this.choice3.getSelectedIndex() == 0)
				this.multi.ejecutar(0, 0);
			else
				this.multi.ejecutar(0, 1);
			break;
		case 1:
//			disparo = getAudioClip(getDocumentBase(), "revolver.wav");
			disparoURL = getClass().getResource("revolver.wav");
			disparo = getAudioClip(disparoURL);
			
			disparo.play();
			switch (this.choice3.getSelectedIndex()) {
			case 0:
				this.multi.ejecutar(0, 2);
				break;
			case 1:
				this.multi.ejecutar(0, 3);
				break;
			case 2:
				this.multi.ejecutar(0, 4);
				break;
			case 3:
				this.multi.ejecutar(0, 5);
			}
			break;
		case 2:
//			disparo = getAudioClip(getDocumentBase(), "escopeta.wav");
			disparoURL = getClass().getResource("escopeta.wav");
			disparo = getAudioClip(disparoURL);
			
			disparo.play();
			if (this.choice3.getSelectedIndex() == 0)
				this.multi.ejecutar(0, 6);
			else
				this.multi.ejecutar(0, 7);
			break;
		case 3:
			if (this.choice3.getSelectedIndex() == 0)
				this.multi.ejecutar(0, 8);
			else
				this.multi.ejecutar(0, 9);
			break;
		case 4:
			if (this.choice3.getSelectedIndex() == 0)
				this.multi.ejecutar(0, 10);
			else
				this.multi.ejecutar(0, 11);
			break;
		}

		System.out.println("Los valores para las armas obtenidos son: ");
		int arma = 0;
		double valor = -0.5D;
		for (int i = 0; i < 12; i++)
			System.out.println(this.armas[i] + "="
					+ this.multi.getCapaSalida().getNeurona(i).getSalida());
		for (int i = 0; i < 12; i++) {
			if (this.multi.getCapaSalida().getNeurona(i).getSalida() <= valor)
				continue;
			arma = i;
			valor = this.multi.getCapaSalida().getNeurona(i).getSalida();
		}

		double fiabilidad = 1.0D;

		for (int i = 0; i < 12; i++) {
			switch (arma) {
			case 0:
			case 1:
				if ((i == arma) || (i <= 1))
					break;
				fiabilidad -= Math.abs(-0.5D
						- this.multi.getCapaSalida().getNeurona(i).getSalida());
				break;
			case 2:
			case 3:
			case 4:
			case 5:
				if ((i == arma) || ((i >= 2) && (i <= 5)))
					break;
				fiabilidad -= Math.abs(-0.5D
						- this.multi.getCapaSalida().getNeurona(i).getSalida());
				break;
			case 6:
			case 7:
				if ((i == arma) || ((i >= 6) && (i <= 7)))
					break;
				fiabilidad -= Math.abs(-0.5D
						- this.multi.getCapaSalida().getNeurona(i).getSalida());
				break;
			case 8:
			case 9:
				if ((i == arma) || ((i >= 8) && (i <= 9)))
					break;
				fiabilidad -= Math.abs(0.5D + this.multi.getCapaSalida()
						.getNeurona(i).getSalida());
				break;
			case 10:
			case 11:
				if ((i == arma) || (i >= 10))
					break;
				fiabilidad -= Math.abs(-0.5D
						- this.multi.getCapaSalida().getNeurona(i).getSalida());
			}

			System.out.println(fiabilidad);
		}
		int f = (int) (fiabilidad * 100.0D);
		System.out.println("El arma reconocida por la red es la "
				+ this.armas[arma]);
		switch (arma) {
		case 0:
			this.label2.setText("Pistola Star 28Pk (" + f + "%)");
			this.label14.setText("Tipo 1 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
			break;
		case 1:
			this.label2.setText("Pistola Star 28Pk (" + f + "%)");
			this.label14.setText("Tipo 2 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
			break;
		case 2:
			this.label2.setText("Revólver Astra 4\" (" + f + "%)");
			this.label14.setText("Tipo 3 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
			break;
		case 3:
			this.label2.setText("Revólver Astra 4\" (" + f + "%)");
			this.label14.setText("Tipo 4 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
			break;
		case 4:
			this.label2.setText("Revólver Astra 4\" (" + f + "%)");
			this.label14.setText("Tipo 5 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
			break;
		case 5:
			this.label2.setText("Revólver Astra 4\" (" + f + "%)");
			this.label14.setText("Tipo 6 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
			break;
		case 6:
			this.label2.setText("Escopeta (" + f + "%)");
			this.label14.setText("Tipo 7 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
			break;
		case 7:
			this.label2.setText("Escopeta (" + f + "%)");
			this.label14.setText("Tipo 8 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
			break;
		case 8:
			this.label2.setText("Fusil (" + f + "%)");
			this.label14.setText("Tipo 9 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
			break;
		case 9:
			this.label2.setText("Fusil (" + f + "%)");
			this.label14.setText("Tipo 10 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
			break;
		case 10:
			this.label2.setText("Cetme (" + f + "%)");
			this.label14.setText("Tipo 9 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
			break;
		case 11:
			this.label2.setText("Cetme (" + f + "%)");
			this.label14.setText("Tipo 10 (" + (int) ((valor + 0.5D) * 100.0D)
					+ "%)");
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
				this.multi = new MLP("22", "BAL", this.jejs, this.jpru,
						new Double(this.textField5.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						new Double(this.textField6.getText()).doubleValue(),
						15, new Integer(this.textField4.getText()).intValue(),
						12);
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
			this.choice3.setEnabled(false);

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
		}
		if (event.getSource() == button5) {
			button5ActionPerformed(event);
		}

	}

	@Override
	public void textValueChanged(TextEvent event) {
		if (event.getSource() == textField4) {
			textField4TextValueChanged(event);
		}

	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource() == choice1) {
			choice1ItemStateChanged(event);
		}else if (event.getSource() == choice3) {
			choice3ItemStateChanged(event);
		}
	}
}