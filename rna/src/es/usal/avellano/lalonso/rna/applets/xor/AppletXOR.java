package es.usal.avellano.lalonso.rna.applets.xor;

import java.applet.Applet;
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
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.text.DecimalFormat;

import es.usal.avellano.lalonso.rna.redes.Ejemplo;
import es.usal.avellano.lalonso.rna.redes.Juego;
import es.usal.avellano.lalonso.rna.redes.MLP;
import es.usal.avellano.lalonso.rna.utilidades.Utilidad;

public class AppletXOR extends Applet implements ActionListener, TextListener{
	private MLP multi;
	Juego[] jejs = new Juego[1];

	Juego[] jpru = new Juego[1];

	Point[] grafico = null;
	private Button button1;
	private Button button2;
	private Button button4;
	private Choice choice1;
	private Choice choice2;
	private Label label1;
	private Label label10;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;
	private Label label8;
	private Label label9;
	private Label lable1;
	private TextField textField3;
	private TextField textField4;
	private TextField textField5;
	private TextField textField6;
	private TextField textField7;
	private TextField textField8;

	public void init() {
		initComponents();
		this.choice1.add("0");
		this.choice1.add("1");
		this.choice2.add("0");
		this.choice2.add("1");

		Ejemplo[] ejs = new Ejemplo[4];
		Ejemplo[] pru = new Ejemplo[4];
		double[] e = new double[2];
		double[] s = new double[1];

		e[0] = -0.5D;
		e[1] = -0.5D;
		s[0] = -0.5D;
		ejs[0] = new Ejemplo(e, s);
		e[0] = -0.5D;
		e[1] = 0.5D;
		s[0] = 0.5D;
		ejs[1] = new Ejemplo(e, s);
		e[0] = 0.5D;
		e[1] = -0.5D;
		s[0] = 0.5D;
		ejs[2] = new Ejemplo(e, s);
		e[0] = 0.5D;
		e[1] = 0.5D;
		s[0] = -0.5D;
		ejs[3] = new Ejemplo(e, s);

		e[0] = 0.5D;
		e[1] = -0.5D;
		s[0] = 0.5D;
		pru[0] = new Ejemplo(e, s);
		e[0] = -0.5D;
		e[1] = 0.5D;
		s[0] = 0.5D;
		pru[1] = new Ejemplo(e, s);
		e[0] = -0.5D;
		e[1] = -0.5D;
		s[0] = -0.5D;
		pru[2] = new Ejemplo(e, s);
		e[0] = 0.5D;
		e[1] = 0.5D;
		s[0] = -0.5D;
		pru[3] = new Ejemplo(e, s);

		this.jejs[0] = new Juego(ejs);
		this.jpru[0] = new Juego(pru);
	}

	private void initComponents()
  {
    this.lable1 = new Label();
    this.label1 = new Label();
    this.label2 = new Label();
    this.button1 = new Button();
    this.label3 = new Label();
    this.textField3 = new TextField();
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
    this.button4 = new Button();
    this.choice1 = new Choice();
    this.choice2 = new Choice();

    setLayout(null);

    setBackground(new Color(192, 192, 192));
    this.lable1.setFont(new Font("Dialog", 1, 14));
    this.lable1.setForeground(new Color(102, 102, 153));
    this.lable1.setText("EJECUCIÓN");
    add(this.lable1);
    this.lable1.setBounds(20, 20, 110, 23);

    this.label1.setText("Entradas:");
    add(this.label1);
    this.label1.setBounds(20, 50, 60, 20);

    this.label2.setText(" XOR");
    add(this.label2);
    this.label2.setBounds(130, 50, 32, 20);

    this.button1.setLabel("Ejecutar");
//    this.button1.addActionListener(new AppletXOR.1(this));
    this.button1.addActionListener(this);

    add(this.button1);
    this.button1.setBounds(111, 90, 70, 24);

    this.label3.setText("Salida: ");
    add(this.label3);
    this.label3.setBounds(20, 130, 38, 20);

    this.textField3.setEditable(false);
    add(this.textField3);
    this.textField3.setBounds(130, 130, 40, 20);

    this.label4.setFont(new Font("Dialog", 1, 14));
    this.label4.setForeground(new Color(102, 102, 153));
    this.label4.setText("ARQUITECTURA");
    add(this.label4);
    this.label4.setBounds(30, 210, 120, 23);

    this.label5.setText("Nº de neuronas ocultas: ");
    add(this.label5);
    this.label5.setBounds(30, 260, 140, 20);

    this.textField4.setText("3");
//    this.textField4.addTextListener(new AppletXOR.2(this));
    this.textField4.addTextListener(this);

    add(this.textField4);
    this.textField4.setBounds(170, 260, 20, 20);

    this.label6.setFont(new Font("Dialog", 1, 14));
    this.label6.setForeground(new Color(102, 102, 153));
    this.label6.setText("ENTRENAMIENTO");
    add(this.label6);
    this.label6.setBounds(280, 20, 140, 23);

    this.label7.setText("Momento");
    add(this.label7);
    this.label7.setBounds(280, 50, 70, 20);

    this.label8.setText("Factor de Aprendizaje");
    add(this.label8);
    this.label8.setBounds(280, 70, 122, 20);

    this.textField5.setText("0.9");
    add(this.textField5);
    this.textField5.setBounds(430, 50, 40, 20);

    this.textField6.setText("0.3");
    add(this.textField6);
    this.textField6.setBounds(430, 70, 40, 20);

    this.label9.setText("Nº de iteraciones");
    add(this.label9);
    this.label9.setBounds(280, 90, 110, 20);

    this.textField7.setText("30000");
    add(this.textField7);
    this.textField7.setBounds(430, 90, 50, 20);

    this.label10.setText("Error máximo");
    add(this.label10);
    this.label10.setBounds(280, 110, 100, 20);

    this.textField8.setText("0.1");
    add(this.textField8);
    this.textField8.setBounds(430, 110, 50, 20);

    this.button2.setLabel("Entrenar");
//    this.button2.addActionListener(new AppletXOR.3(this));
    this.button2.addActionListener(this);

    add(this.button2);
    this.button2.setBounds(487, 70, 70, 24);

    this.button4.setLabel("Cargar Pesos");
//    this.button4.addActionListener(new AppletXOR.4(this));
    this.button4.addActionListener(this);

    add(this.button4);
    this.button4.setBounds(50, 310, 150, 24);

    add(this.choice1);
    this.choice1.setBounds(80, 50, 40, 20);

    add(this.choice2);
    this.choice2.setBounds(170, 50, 40, 20);
  }

	private void textField4TextValueChanged(TextEvent evt) {
		if (this.textField4.getText().equals("3"))
			this.button4.setEnabled(true);
		else
			this.button4.setEnabled(false);
	}

	private void button4ActionPerformed(ActionEvent evt) {
		try {
			this.multi = new MLP("12", "XOR", this.jejs, this.jpru, new Double(
					this.textField5.getText()).doubleValue(), new Double(
					this.textField6.getText()).doubleValue(), new Double(
					this.textField6.getText()).doubleValue(), 2, 3, 1);
			this.multi.cargarPesos(getClass().getResource("xor231eo.pes"),
					getClass().getResource("xor231os.pes"));
		} catch (Exception e) {
			System.err.println("Error durante la carga de la red: "
					+ e.getMessage());
		}
	}

	private void button1ActionPerformed(ActionEvent evt) {
		double[] ent = new double[2];
		DecimalFormat df = new DecimalFormat("0.00");

		ent[0] = new Double(this.choice1.getSelectedItem()).doubleValue();
		ent[1] = new Double(this.choice2.getSelectedItem()).doubleValue();
		ent[0] -= 0.5D;
		ent[1] -= 0.5D;

		this.multi.ejecutar(ent);

		this.textField3.setText(""
				+ df.format(this.multi.getCapaSalida().getNeurona(0)
						.getSalida() + 0.5D));
	}

	private void button2ActionPerformed(ActionEvent evt) {
		this.multi = new MLP("12", "XOR", this.jejs, this.jpru, new Double(
				this.textField5.getText()).doubleValue(), new Double(
				this.textField6.getText()).doubleValue(), new Double(
				this.textField6.getText()).doubleValue(), 2, new Integer(
				this.textField4.getText()).intValue(), 1);
		this.grafico = this.multi.aprender(getGraphics(), getHeight()
				- this.label10.getY() - 80, getWidth() - this.label10.getX()
				- 80, this.label10.getX() + 20, this.label10.getY() + 35,
				new Integer(this.textField7.getText()).intValue(), 1.0D,
				new Double(this.textField8.getText()).doubleValue());
		paint(getGraphics());
	}

	public void paint(Graphics g) {
		Utilidad.pintarEjes(getGraphics(), getHeight() - this.label10.getY()
				- 80, getWidth() - this.label10.getX() - 80,
				this.label10.getX() + 20, this.label10.getY() + 35,
				new Integer(this.textField7.getText()).intValue(), 1.0D,
				new Double(this.textField8.getText()).doubleValue());
		if (this.grafico != null)
			Utilidad.pintarGrafico(getGraphics(), this.grafico);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button1){
			button1ActionPerformed(event);
		}else if (event.getSource() == button2){
			button2ActionPerformed(event);
		}else if (event.getSource() == button4){
			button4ActionPerformed(event);
		}
		
	}

	@Override
	public void textValueChanged(TextEvent event) {
		if(event.getSource() == textField4){
			textField4TextValueChanged(event);
		}
	}
}