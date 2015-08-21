package es.usal.avellano.lalonso.rna.utilidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DecimalFormat;

public class Utilidad {
	public static Statement crearConexion(String bd) {
		Statement stat = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost/" + bd
							+ "?user=root&password=");
			stat = con.createStatement();
		} catch (Exception e) {
			System.err.println("Error al crear la conexión: " + e.getMessage());
		}
		return stat;
	}

	public static Statement crearConexion(String bd, String usuario,
			String password) {
		Statement stat = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost/" + bd + "?user="
							+ usuario + "&password=" + password);

			stat = con.createStatement();
		} catch (Exception e) {
			System.err.println("Error al crear la conexión" + e.getMessage());
		}
		return stat;
	}

	public static double[][] leerMatriz(URL fich) throws Exception {
		System.out.println("LEER MATRIZ");
		double[][] mat = (double[][]) null;
		int i = 0;
		int j = 0;
		int x = 0;
		int y = 0;
		String cad = "";

		URLConnection conn = null;
		DataInputStream in = null;
		StringBuffer buf = new StringBuffer();
		System.out.println("Abriendo conexión");
		conn = fich.openConnection();
		System.out.println("Conectando...");
		conn.connect();
		System.out.println("Creando flujo de entrada");
		InputStream is = conn.getInputStream();
		System.out.println("Creando la entrada");
		in = new DataInputStream(new BufferedInputStream(is));
		System.out.println("Conexión realizada: " + in);

		if (((cad = in.readLine()) != null) && (cad.indexOf(",") != -1)) {
			x = new Integer(cad.substring(0, cad.indexOf(","))).intValue();
			y = new Integer(cad.substring(cad.indexOf(",") + 1)).intValue();

			mat = new double[x][y];

			for (i = 0; i < x; i++) {
				cad = in.readLine();
				for (j = 0; j < y; j++) {
					if (cad.indexOf(" ") != -1) {
						mat[i][j] = new Double(cad.substring(0, cad.indexOf(" "))).doubleValue();
						cad = cad.substring(cad.indexOf(" ") + 1);
					} else {
						mat[i][j] = new Double(cad).doubleValue();
					}
				}
			}
		}
		in.close();
		return mat;
	}

	public static double[][] leerMatriz(String fich) throws Exception {
		double[][] mat = (double[][]) null;
		int i = 0;
		int j = 0;
		int x = 0;
		int y = 0;
		String cad = "";

		BufferedReader in = new BufferedReader(new FileReader(fich));

		if (((cad = in.readLine()) != null) && (cad.indexOf(",") != -1)) {
			x = new Integer(cad.substring(0, cad.indexOf(","))).intValue();
			y = new Integer(cad.substring(cad.indexOf(",") + 1)).intValue();
			mat = new double[x][y];

			for (i = 0; i < x; i++) {
				cad = in.readLine();
				for (j = 0; j < y; j++) {
					if (cad.indexOf(" ") != -1) {
						mat[i][j] = new Double(cad.substring(0, cad.indexOf(" "))).doubleValue();
						cad = cad.substring(cad.indexOf(" ") + 1);
					} else {
						mat[i][j] = new Double(cad).doubleValue();
					}
				}
			}
		}
		in.close();
		return mat;
	}

	public static void pintarGrafico(Graphics g, Point[] puntos) {
		g.setColor(Color.red);
		int i = 1;
		while (puntos[i].x != 0) {
			g.drawLine(puntos[(i - 1)].x, puntos[(i - 1)].y, puntos[i].x,
					puntos[i].y);
			i++;
		}
		g.setColor(Color.black);
	}

	public static void pintarEjes(Graphics g, int alto, int ancho, int x0,
			int y0, int NItMax, double errMax, double errMin) {
		DecimalFormat df = new DecimalFormat("0.0#");

		g.clearRect(x0 - 25, y0 - 20, 25, alto);
		g.clearRect(x0, y0 + alto, ancho + 40, 20);

		g.setColor(Color.white);
		g.fillRect(x0 - 25, y0 - 10, ancho + 50, alto + 40);
		g.setColor(Color.black);

		g.drawString("" + errMax, x0 - 25, y0);
		g.drawLine(x0 - 10, y0, x0, y0);
		g.drawString("" + df.format(errMax / 2.0D), x0 - 25, y0 + alto / 2);
		g.drawLine(x0 - 10, y0 + alto / 2, x0, y0 + alto / 2);
		g.drawString("" + df.format(errMax / 10.0D), x0 - 25, y0 + (int) (0.9D * alto));
		g.drawLine(x0 - 10, (int) (y0 + 0.9D * alto), x0, y0 + (int) (0.9D * alto));

		g.drawString("" + NItMax / 10, x0 - 15 + ancho / 10, y0 + alto + 20);
		g.drawLine(x0 + ancho / 10, y0 + alto, x0 + ancho / 10, alto + y0 + 10);
		g.drawString("" + NItMax / 2, x0 - 15 + ancho / 2, y0 + alto + 20);
		g.drawLine(x0 + ancho / 2, y0 + alto, x0 + ancho / 2, alto + 10 + y0);
		g.drawString("" + NItMax, ancho - 15 + x0, alto + 20 + y0);
		g.drawLine(x0 + ancho, y0 + alto, x0 + ancho, alto + y0 + 10);

		g.drawLine(x0, y0 + alto, x0 + ancho, y0 + alto);
		g.drawLine(x0, y0 + alto, x0, y0);

		g.setColor(Color.blue);
		g.drawLine(x0, (int) (y0 + alto - alto / errMax * errMin), x0 + ancho, (int) (y0 + alto - alto / errMax * errMin));
		g.setColor(Color.black);
	}

	public static boolean iguales(int[] a, int[] b) {
		if (a.length != b.length)
			return false;
		for (int i = 0; i < a.length; i++)
			if (a[i] != b[i])
				return false;
		return true;
	}

	public static double coincidencia(double[] a, double[] b) {
		double sum = -1.0D;
		double inc = 2.0D / a.length;

		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i])
				continue;
			sum += inc;
		}
		return sum;
	}
}