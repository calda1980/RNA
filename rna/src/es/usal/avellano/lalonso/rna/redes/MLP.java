package es.usal.avellano.lalonso.rna.redes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import es.usal.avellano.lalonso.rna.utilidades.Utilidad;

public class MLP extends RedNeuronal {
	private Capa entrada;
	private Capa oculta;
	private Capa salida;
	private int nne;
	private int nno;
	private int nns;
	private double[][] pesosEO;
	private double[][] pesosOS;
	private double bias = 1.0D;

	public MLP(String codigo, String nombre) {
		super(codigo, nombre);
	}

	public MLP(String codigo, String nombre, Juego[] ensayo, Juego[] prueba,
			double mu, double CAO, double CAS) {
		super(codigo, nombre, ensayo, prueba, mu, CAO, CAS);
	}

	public MLP(String codigo, String nombre, Juego[] ensayo, Juego[] prueba,
			double mu, double CAO, double CAS, int nne, int nno, int nns) {
		super(codigo, nombre, ensayo, prueba, mu, CAO, CAS);

		this.nne = nne;
		this.nno = nno;
		this.nns = nns;
		this.entrada = new Capa(nne);
		this.oculta = new Capa(nno);
		this.salida = new Capa(nns);
		this.pesosEO = new double[nne + 1][nno];
		this.pesosOS = new double[nno + 1][nns];

		for (int i = 0; i < nne + 1; i++) {
			for (int j = 0; j < nno; j++)
				this.pesosEO[i][j] = (Math.random() - 0.5D);
		}
		for (int i = 0; i < nno + 1; i++)
			for (int j = 0; j < nns; j++)
				this.pesosOS[i][j] = (Math.random() - 0.5D);
	}

	public MLP(String codigo, String nombre, Juego[] ensayo, Juego[] prueba,
			double mu, double CAO, double CAS, int nne, int nno, int nns,
			double k) {
		super(codigo, nombre, ensayo, prueba, mu, CAO, CAS);

		this.nne = nne;
		this.nno = nno;
		this.nns = nns;
		this.entrada = new Capa(nne, k);
		this.oculta = new Capa(nno, k);
		this.salida = new Capa(nns, k);
		this.pesosEO = new double[nne + 1][nno];
		this.pesosOS = new double[nno + 1][nns];

		for (int i = 0; i < nne + 1; i++) {
			for (int j = 0; j < nno; j++)
				this.pesosEO[i][j] = (Math.random() + k);
		}
		for (int i = 0; i < nno + 1; i++)
			for (int j = 0; j < nns; j++)
				this.pesosOS[i][j] = (Math.random() + k);
	}

	public MLP(ResultSet rs) throws SQLException {
		super(rs.getString("codigo"), rs.getString("nombre"), rs
				.getDouble("mu"), rs.getDouble("cao"), rs.getDouble("cas"));
		try {
			this.pesosEO = Utilidad.leerMatriz(rs.getString("pesosEO"));
			this.pesosOS = Utilidad.leerMatriz(rs.getString("pesosOS"));
			this.nne = (this.pesosEO.length - 1);
			this.nno = (this.pesosOS.length - 1);
			this.nns = this.pesosOS[0].length;
			this.entrada = new Capa(this.nne);
			this.oculta = new Capa(this.nno);
			this.salida = new Capa(this.nns);
		} catch (Exception e) {
			System.err.println("Error, fichero corrupto: " + e.getMessage());
		}
	}

	public MLP(ResultSet rs, ResultSet rse, ResultSet rsp) throws SQLException {
		super(rs.getString("codigo"), rs.getString("nombre"), rs
				.getDouble("mu"), rs.getDouble("cao"), rs.getDouble("cas"));
		try {
			this.pesosEO = Utilidad.leerMatriz(rs.getString("pesosEO"));
			this.pesosOS = Utilidad.leerMatriz(rs.getString("pesosOS"));
			this.nne = (this.pesosEO.length - 1);
			this.nno = (this.pesosOS.length - 1);
			this.nns = this.pesosOS[0].length;

			this.entrada = new Capa(this.nne);
			this.oculta = new Capa(this.nno);
			this.salida = new Capa(this.nns);

			Ejemplo[] ejemplose = null;
			Ejemplo[] ejemplosp = null;
			int i = 0;
			int j = 0;

			while (rse.next())
				i++;
			ejemplose = new Ejemplo[i];
			i = 0;
			while (rsp.next())
				i++;
			ejemplosp = new Ejemplo[i];
			rse.beforeFirst();
			rsp.beforeFirst();
			i = 0;

			while (rse.next()) {
				double[] e = new double[this.nne];
				double[] s = new double[this.nns];

				String cade = rse.getString("entrada");
				String cads = rse.getString("salida");
				for (j = 0; j < this.nne; j++) {
					e[j] = new Double(cade.substring(0, cade.indexOf(" ")))
							.doubleValue();
					cade = cade.substring(cade.indexOf(" ") + 1);
				}
				for (j = 0; j < this.nns; j++) {
					s[j] = new Double(cads.substring(0, cads.indexOf(" ")))
							.doubleValue();
					cads = cads.substring(cads.indexOf(" ") + 1);
				}
				ejemplose[(i++)] = new Ejemplo(e, s);
			}
			i = 0;

			while (rsp.next()) {
				double[] e = new double[this.nne];
				double[] s = new double[this.nns];

				String cade = rsp.getString("entrada");
				String cads = rsp.getString("salida");
				for (j = 0; j < this.nne; j++) {
					e[j] = new Double(cade.substring(0, cade.indexOf(" ")))
							.doubleValue();
					cade = cade.substring(cade.indexOf(" ") + 1);
				}
				for (j = 0; j < this.nns; j++) {
					s[j] = new Double(cads.substring(0, cads.indexOf(" ")))
							.doubleValue();
					cads = cads.substring(cads.indexOf(" ") + 1);
				}
				ejemplosp[(i++)] = new Ejemplo(e, s);
			}

			this.ensayo = new Juego[1];
			this.prueba = new Juego[1];
			this.ensayo[0] = new Juego(ejemplose);
			this.prueba[0] = new Juego(ejemplosp);

			System.out.println("Red Cargada Satisfactoriamente");
		} catch (Exception e) {
			System.err.println("Error en la carga: " + e.getMessage());
		}
	}

	public void almacenar(Statement stat) throws Exception {
		ResultSet rs = null;
		int cod = 0;

		System.out.println("El código es: " + this.codigo);
		rs = stat.executeQuery("select * from MLP where codigo=" + this.codigo);

		if (rs.next()) {
			System.out.println("Actualizamos la red");
			stat.executeUpdate("update MLP set nombre=\"" + this.nombre
					+ "\", mu=" + this.mu + ", cao=" + this.CAO + ", cas="
					+ this.CAS + ", pesosEO=\"C:\\\\neuron\\\\eo" + this.codigo
					+ ".txt\", pesosOS=\"C:\\\\neuron\\\\os" + this.codigo
					+ ".txt\" where codigo=" + this.codigo);

			cod = new Integer(this.codigo).intValue();
		} else {
			System.out.println("Introducimos una nueva red");

			ResultSet rs2 = stat.executeQuery("select max(codigo) from mlp");
			rs2.next();
			cod = rs2.getInt(1) + 1;
			stat.executeUpdate("insert into MLP (codigo, nombre, mu, cao, cas, pesosEO, pesosOS) values ("
					+ cod
					+ ",\""
					+ this.nombre
					+ "\","
					+ this.mu
					+ ","
					+ this.CAO
					+ ","
					+ this.CAS
					+ ",\"C:\\\\neuron\\\\eo"
					+ cod
					+ ".txt\",\"C:\\\\neuron\\\\os" + cod + ".txt\")");
		}

		String cade = "\"";
		String cads = "\"";
		System.out.println("Introducimos los juegos de ensayo");

		System.out.println("Introducimos los juegos de prueba");

		BufferedWriter out = new BufferedWriter(new FileWriter("C:\\neuron\\eo"
				+ cod + ".txt"));

		out.write(this.nne + 1 + "," + this.nno);
		out.newLine();
		for (int i = 0; i < this.nne + 1; i++) {
			for (int j = 0; j < this.nno; j++)
				out.write(this.pesosEO[i][j] + " ");
			out.newLine();
		}
		out.close();

		out = new BufferedWriter(
				new FileWriter("C:\\neuron\\os" + cod + ".txt"));
		out.write(this.nno + 1 + "," + this.nns);
		out.newLine();
		for (int i = 0; i < this.nno + 1; i++) {
			for (int j = 0; j < this.nns; j++)
				out.write(this.pesosOS[i][j] + " ");
			out.newLine();
		}
		out.close();
		System.out.println("Red almacenada Satisfactoriamente");
	}

	public void setMu(double valor) {
		this.mu = valor;
	}

	public Capa getCapaEntrada() {
		return this.entrada;
	}

	public Capa getCapaOculta() {
		return this.oculta;
	}

	public Capa getCapaSalida() {
		return this.salida;
	}

	public double[][] getPesosEO() {
		return this.pesosEO;
	}

	public double[][] getPesosOS() {
		return this.pesosOS;
	}

	public double getPesoEO(int i, int j) {
		return this.pesosEO[i][j];
	}

	public double getPesoOS(int i, int j) {
		return this.pesosOS[i][j];
	}

	public void backpropagation() {
		double sum = 0.0D;

		double[] delta = new double[this.nno];
		double[][] sumError = new double[this.nno + 1][this.nns];
		double[][] sumErrorEO = new double[this.nne + 1][this.nno];

		double[][] antIncPesosOS = new double[this.nno + 1][this.nns];
		double[][] antIncPesosEO = new double[this.nne + 1][this.nno];

		for (int i = 0; i < this.ensayo[0].getEjemplos().length; i++) {
			for (int j = 0; j < this.nne; j++) {
				this.entrada.getNeurona(j).setSalida(
						this.ensayo[0].getEjemplo(i).getEntrada(j));
			}

			int j; // vdc
			for (j = 0; j < this.nno; j++) {
				int k; // vdc
				for (k = 0; k < this.nne; k++)
					sum += this.pesosEO[k][j]
							* this.entrada.getNeurona(k).getSalida();
				sum += this.pesosEO[k][j] * this.bias;
				this.oculta.getNeurona(j).setEntrada(sum);
				this.oculta.getNeurona(j).activar();
				sum = 0.0D;
			}

			for (j = 0; j < this.nns; j++) {
				int k; // vdc
				for (k = 0; k < this.nno; k++)
					sum += this.pesosOS[k][j]
							* this.oculta.getNeurona(k).getSalida();
				sum += this.pesosOS[k][j] * this.bias;
				this.salida.getNeurona(j).setEntrada(sum);
				this.salida.getNeurona(j).activar();
				sum = 0.0D;

				for (k = 0; k < this.nno; k++) {
					sumError[k][j] += (this.ensayo[0].getEjemplo(i)
							.getSalida(j) - this.salida.getNeurona(j)
							.getSalida())
							* this.salida.getNeurona(j).activarDerivada()
							* this.oculta.getNeurona(k).getSalida();
				}
				sumError[k][j] += (this.ensayo[0].getEjemplo(i).getSalida(j) - this.salida
						.getNeurona(j).getSalida())
						* this.salida.getNeurona(j).activarDerivada()
						* this.bias;

				for (k = 0; k < this.nno; k++)
					delta[k] += (this.ensayo[0].getEjemplo(i).getSalida(j) - this.salida
							.getNeurona(j).getSalida())
							* this.salida.getNeurona(j).activarDerivada()
							* this.pesosOS[k][j]
							* this.oculta.getNeurona(k).activarDerivada();
				for (k = 0; k < this.nno; k++) {
					int l; //vdc
					for (l = 0; l < this.nne; l++) {
						sumErrorEO[l][k] += delta[k]
								* this.ensayo[0].getEjemplo(i).getEntrada(l);
					}
					sumErrorEO[l][k] += delta[k] * this.bias;
				}
			}
			for (int k = 0; k < this.nno; k++)
				delta[k] = 0.0D;

		}
		int k;	// vdc
		for (k = 0; k < this.nno; k++) {
			for (int j = 0; j < this.nns; j++) {
				this.pesosOS[k][j] += this.mu * antIncPesosOS[k][j] + this.CAS
						* sumError[k][j];
				antIncPesosOS[k][j] = (this.mu * antIncPesosOS[k][j] + this.CAS
						* sumError[k][j]);
			}
		}
		for (int j = 0; j < this.nns; j++) {
			this.pesosOS[k][j] += this.mu * antIncPesosOS[k][j] + this.CAS
					* sumError[k][j];
			antIncPesosOS[k][j] = (this.mu * antIncPesosOS[k][j] + this.CAS
					* sumError[k][j]);
		}

		for (k = 0; k < this.nne; k++)
			for (int j = 0; j < this.nno; j++) {
				this.pesosEO[k][j] += this.mu * antIncPesosEO[k][j] + this.CAO
						* sumErrorEO[k][j];
				antIncPesosEO[k][j] = (this.mu * antIncPesosEO[k][j] + this.CAO
						* sumErrorEO[k][j]);
			}
	}

	public double backpropagationError() {
		double sum = 0.0D;

		double[] delta = new double[this.nno];
		double[][] sumError = new double[this.nno + 1][this.nns];
		double[][] sumErrorEO = new double[this.nne + 1][this.nno];

		double[][] antIncPesosOS = new double[this.nno + 1][this.nns];
		double[][] antIncPesosEO = new double[this.nne + 1][this.nno];
		double error = 0.0D;

		for (int i = 0; i < this.ensayo[0].getEjemplos().length; i++) {
			int j;
			for (j = 0; j < this.nne; j++) {
				this.entrada.getNeurona(j).setSalida(
						this.ensayo[0].getEjemplo(i).getEntrada(j));
			}

			for (j = 0; j < this.nno; j++) {
				int k; // vdc
				for (k = 0; k < this.nne; k++)
					sum += this.pesosEO[k][j]
							* this.entrada.getNeurona(k).getSalida();
				sum += this.pesosEO[k][j] * this.bias;
				this.oculta.getNeurona(j).setEntrada(sum);
				this.oculta.getNeurona(j).activar();
				sum = 0.0D;
			}
			int k; //vdc
			for (j = 0; j < this.nns; j++) {
				for (k = 0; k < this.nno; k++)
					sum += this.pesosOS[k][j]
							* this.oculta.getNeurona(k).getSalida();
				sum += this.pesosOS[k][j] * this.bias;
				this.salida.getNeurona(j).setEntrada(sum);
				this.salida.getNeurona(j).activar();
				sum = 0.0D;

				error += Math.pow(this.ensayo[0].getEjemplo(i).getSalida(j)
						- this.salida.getNeurona(j).getSalida(), 2.0D);

				for (k = 0; k < this.nno; k++) {
					sumError[k][j] += (this.ensayo[0].getEjemplo(i)
							.getSalida(j) - this.salida.getNeurona(j)
							.getSalida())
							* this.salida.getNeurona(j).activarDerivada()
							* this.oculta.getNeurona(k).getSalida();
				}
				sumError[k][j] += (this.ensayo[0].getEjemplo(i).getSalida(j) - this.salida
						.getNeurona(j).getSalida())
						* this.salida.getNeurona(j).activarDerivada()
						* this.bias;

				for (k = 0; k < this.nno; k++)
					delta[k] += (this.ensayo[0].getEjemplo(i).getSalida(j) - this.salida
							.getNeurona(j).getSalida())
							* this.salida.getNeurona(j).activarDerivada()
							* this.pesosOS[k][j]
							* this.oculta.getNeurona(k).activarDerivada();
				for (k = 0; k < this.nno; k++) {
					int l; //vdc
					for (l = 0; l < this.nne; l++) {
						sumErrorEO[l][k] += delta[k]
								* this.ensayo[0].getEjemplo(i).getEntrada(l);
					}
					sumErrorEO[l][k] += delta[k] * this.bias;
				}
			}
			for (k = 0; k < this.nno; k++)
				delta[k] = 0.0D;

		}
		int k; //vdc
		for (k = 0; k < this.nno; k++) {
			for (int j = 0; j < this.nns; j++) {
				this.pesosOS[k][j] += this.mu * antIncPesosOS[k][j] + this.CAS
						* sumError[k][j];
				antIncPesosOS[k][j] = (this.mu * antIncPesosOS[k][j] + this.CAS
						* sumError[k][j]);
			}
		}
		for (int j = 0; j < this.nns; j++) {
			this.pesosOS[k][j] += this.mu * antIncPesosOS[k][j] + this.CAS
					* sumError[k][j];
			antIncPesosOS[k][j] = (this.mu * antIncPesosOS[k][j] + this.CAS
					* sumError[k][j]);
		}

		for (k = 0; k < this.nne; k++) {
			for (int j = 0; j < this.nno; j++) {
				this.pesosEO[k][j] += this.mu * antIncPesosEO[k][j] + this.CAO
						* sumErrorEO[k][j];
				antIncPesosEO[k][j] = (this.mu * antIncPesosEO[k][j] + this.CAO
						* sumErrorEO[k][j]);
			}
		}
		return error;
	}

	public void backpropagation(Juego[] juego) {
		double sum = 0.0D;

		double[] delta = new double[this.nno];
		double[][] sumError = new double[this.nno + 1][this.nns];
		double[][] sumErrorEO = new double[this.nne + 1][this.nno];

		double[][] antIncPesosOS = new double[this.nno + 1][this.nns];
		double[][] antIncPesosEO = new double[this.nne + 1][this.nno];
		
		int k;	//vdc
		for (int i = 0; i < juego[0].getEjemplos().length; i++) {
			for (int j = 0; j < this.nne; j++) {
				this.entrada.getNeurona(j).setSalida(
						juego[0].getEjemplo(i).getEntrada(j));
			}
			int j; //vdc
			for (j = 0; j < this.nno; j++) {
				for (k = 0; k < this.nne; k++)
					sum += this.pesosEO[k][j]
							* this.entrada.getNeurona(k).getSalida();
				sum += this.pesosEO[k][j] * this.bias;
				this.oculta.getNeurona(j).setEntrada(sum);
				this.oculta.getNeurona(j).activar();
				sum = 0.0D;
			}

			for (j = 0; j < this.nns; j++) {
				for (k = 0; k < this.nno; k++)
					sum += this.pesosOS[k][j]
							* this.oculta.getNeurona(k).getSalida();
				sum += this.pesosOS[k][j] * this.bias;
				this.salida.getNeurona(j).setEntrada(sum);
				this.salida.getNeurona(j).activar();
				sum = 0.0D;

				for (k = 0; k < this.nno; k++) {
					sumError[k][j] += (juego[0].getEjemplo(i).getSalida(j) - this.salida
							.getNeurona(j).getSalida())
							* this.salida.getNeurona(j).activarDerivada()
							* this.oculta.getNeurona(k).getSalida();
				}
				sumError[k][j] += (juego[0].getEjemplo(i).getSalida(j) - this.salida
						.getNeurona(j).getSalida())
						* this.salida.getNeurona(j).activarDerivada()
						* this.bias;

				for (k = 0; k < this.nno; k++)
					delta[k] += (juego[0].getEjemplo(i).getSalida(j) - this.salida
							.getNeurona(j).getSalida())
							* this.salida.getNeurona(j).activarDerivada()
							* this.pesosOS[k][j]
							* this.oculta.getNeurona(k).activarDerivada();
				for (k = 0; k < this.nno; k++) {
					int l; //vdc
					for (l = 0; l < this.nne; l++) {
						sumErrorEO[l][k] += delta[k]
								* juego[0].getEjemplo(i).getEntrada(l);
					}
					sumErrorEO[l][k] += delta[k] * this.bias;
				}
			}
			for (k = 0; k < this.nno; k++)
				delta[k] = 0.0D;

		}

		for (k = 0; k < this.nno; k++) {
			for (int j = 0; j < this.nns; j++) {
				this.pesosOS[k][j] += this.mu * antIncPesosOS[k][j] + this.CAS
						* sumError[k][j];
				antIncPesosOS[k][j] = (this.mu * antIncPesosOS[k][j] + this.CAS
						* sumError[k][j]);
			}
		}
		for (int j = 0; j < this.nns; j++) {
			this.pesosOS[k][j] += this.mu * antIncPesosOS[k][j] + this.CAS
					* sumError[k][j];
			antIncPesosOS[k][j] = (this.mu * antIncPesosOS[k][j] + this.CAS
					* sumError[k][j]);
		}

		for (k = 0; k < this.nne; k++)
			for (int j = 0; j < this.nno; j++) {
				this.pesosEO[k][j] += this.mu * antIncPesosEO[k][j] + this.CAO
						* sumErrorEO[k][j];
				antIncPesosEO[k][j] = (this.mu * antIncPesosEO[k][j] + this.CAO
						* sumErrorEO[k][j]);
			}
	}

	public void backpropagationNoBatch(int indEjemplo, int tipoEjemplo) {
		double sum = 0.0D;

		double[] delta = new double[this.nno];
		double[][] sumError = new double[this.nno + 1][this.nns];
		double[][] sumErrorEO = new double[this.nne + 1][this.nno];

		double[][] antIncPesosOS = new double[this.nno + 1][this.nns];
		double[][] antIncPesosEO = new double[this.nne + 1][this.nno];
		Ejemplo ejemplo = null;

		if (tipoEjemplo == 0)
			ejemplo = this.ensayo[0].getEjemplo(indEjemplo);
		if (tipoEjemplo == 1) {
			ejemplo = this.prueba[0].getEjemplo(indEjemplo);
		}
		int j;	//vdc
		int k;	// vdc
		int l; 	//vdc
		for (j = 0; j < this.nne; j++) {
			this.entrada.getNeurona(j).setSalida(ejemplo.getEntrada(j));
		}

		for (j = 0; j < this.nno; j++) {
			for (k = 0; k < this.nne; k++)
				sum += this.pesosEO[k][j]
						* this.entrada.getNeurona(k).getSalida();
			sum += this.pesosEO[k][j] * this.bias;
			this.oculta.getNeurona(j).setEntrada(sum);
			this.oculta.getNeurona(j).activar();
			sum = 0.0D;
		}

		for (j = 0; j < this.nns; j++) {
			for (k = 0; k < this.nno; k++)
				sum += this.pesosOS[k][j]
						* this.oculta.getNeurona(k).getSalida();
			sum += this.pesosOS[k][j] * this.bias;
			this.salida.getNeurona(j).setEntrada(sum);
			this.salida.getNeurona(j).activar();
			sum = 0.0D;

			for (k = 0; k < this.nno; k++) {
				sumError[k][j] += (ejemplo.getSalida(j) - this.salida
						.getNeurona(j).getSalida())
						* this.salida.getNeurona(j).activarDerivada()
						* this.oculta.getNeurona(k).getSalida();
			}
			sumError[k][j] += (ejemplo.getSalida(j) - this.salida.getNeurona(j)
					.getSalida())
					* this.salida.getNeurona(j).activarDerivada()
					* this.bias;

			for (k = 0; k < this.nno; k++)
				delta[k] += (ejemplo.getSalida(j) - this.salida.getNeurona(j)
						.getSalida())
						* this.salida.getNeurona(j).activarDerivada()
						* this.pesosOS[k][j]
						* this.oculta.getNeurona(k).activarDerivada();
			for (k = 0; k < this.nno; k++) {
				for (l = 0; l < this.nne; l++) {
					sumErrorEO[l][k] += delta[k] * ejemplo.getEntrada(l);
				}
				sumErrorEO[l][k] += delta[k] * this.bias;
			}
		}
		for ( k = 0; k < this.nno; k++)
			delta[k] = 0.0D;

		for (k = 0; k < this.nno; k++) {
			for (j = 0; j < this.nns; j++) {
				this.pesosOS[k][j] += this.mu * antIncPesosOS[k][j] + this.CAS
						* sumError[k][j];
				antIncPesosOS[k][j] = (this.mu * antIncPesosOS[k][j] + this.CAS
						* sumError[k][j]);
			}
		}
		for (j = 0; j < this.nns; j++) {
			this.pesosOS[k][j] += this.mu * antIncPesosOS[k][j] + this.CAS
					* sumError[k][j];
			antIncPesosOS[k][j] = (this.mu * antIncPesosOS[k][j] + this.CAS
					* sumError[k][j]);
		}

		for (k = 0; k < this.nne; k++)
			for (j = 0; j < this.nno; j++) {
				this.pesosEO[k][j] += this.mu * antIncPesosEO[k][j] + this.CAO
						* sumErrorEO[k][j];
				antIncPesosEO[k][j] = (this.mu * antIncPesosEO[k][j] + this.CAO
						* sumErrorEO[k][j]);
			}
	}

	public void ejecutar(int indicePrueba, int indiceEjemplo) {
		double sum = 0.0D;

		Ejemplo ej = new Ejemplo();
		ej = this.prueba[indicePrueba].getEjemplo(indiceEjemplo);
		int j;	//vdc
		int k;	//vdc
		for (j = 0; j < this.nne; j++) {
			this.entrada.getNeurona(j).setEntrada(ej.getEntrada(j));
			this.entrada.getNeurona(j).setSalida(
					this.entrada.getNeurona(j).getEntrada());
		}

		for (j = 0; j < this.nno; j++) {
			for (k = 0; k < this.nne; k++)
				sum += this.pesosEO[k][j]
						* this.entrada.getNeurona(k).getSalida();
			sum += this.pesosEO[k][j] * this.bias;
			this.oculta.getNeurona(j).setEntrada(sum);
			this.oculta.getNeurona(j).activar();
			sum = 0.0D;
		}

		for (j = 0; j < this.nns; j++) {
			for (k = 0; k < this.nno; k++)
				sum += this.pesosOS[k][j]
						* this.oculta.getNeurona(k).getSalida();
			sum += this.pesosOS[k][j] * this.bias;
			this.salida.getNeurona(j).setEntrada(sum);
			this.salida.getNeurona(j).activar();
			sum = 0.0D;
		}
	}

	public void ejecutar(double[] entradas) {
		double sum = 0.0D;
		int j;	//vdc
		int k; 	//vdc
		for (j = 0; j < this.nne; j++) {
			this.entrada.getNeurona(j).setEntrada(entradas[j]);
			this.entrada.getNeurona(j).setSalida(
					this.entrada.getNeurona(j).getEntrada());
		}

		for (j = 0; j < this.nno; j++) {
			for (k = 0; k < this.nne; k++)
				sum += this.pesosEO[k][j]
						* this.entrada.getNeurona(k).getSalida();
			sum += this.pesosEO[k][j] * this.bias;

			this.oculta.getNeurona(j).setEntrada(sum);
			this.oculta.getNeurona(j).activar();
			sum = 0.0D;
		}

		for (j = 0; j < this.nns; j++) {
			for (k = 0; k < this.nno; k++)
				sum += this.pesosOS[k][j]
						* this.oculta.getNeurona(k).getSalida();
			sum += this.pesosOS[k][j] * this.bias;
			this.salida.getNeurona(j).setEntrada(sum);
			this.salida.getNeurona(j).activar();
			sum = 0.0D;
		}
	}

	public void aprender() {
		double error = 1.0D;
		int nits = 0;

		while (error > 0.01D) {
			backpropagation();
			error = error();
			nits++;
		}
		System.out.println("Se han necesitado: " + nits + " iteraciones");
	}

	public Point[] aprender(Graphics g, int alto, int ancho, int x0, int y0,
			int NItMax, double errMax, double errMin) {
		double error = errMin + 1.0D;
		double errorAnt = 1.0D;
		int nits = 1;
		int i = 1;
		Point[] puntos;
		if (ancho > NItMax) {
			puntos = new Point[ancho];
			for (int j = 0; j < ancho; j++)
				puntos[j] = new Point();
		} else {
			puntos = new Point[NItMax + 1];
			for (int j = 0; j <= NItMax; j++)
				puntos[j] = new Point();

		}

		puntos[0].x = x0;
		puntos[0].y = y0;

		Utilidad.pintarEjes(g, alto, ancho, x0, y0, NItMax, errMax, errMin);

		g.setColor(Color.red);

		while ((error > errMin) && (nits < NItMax)) {
			backpropagation();
			error = error();

			if ((NItMax < ancho)
					|| (Math.IEEEremainder(nits, NItMax / ancho) == 0.0D)) {
				puntos[i].x = (x0 + nits * ancho / NItMax);
				puntos[i].y = (y0 + alto - (int) (error * alto / errMax));
				g.drawLine(puntos[(i - 1)].x, puntos[(i - 1)].y, puntos[i].x,
						puntos[i].y);
				i++;
			}
			nits++;
		}

		g.setColor(Color.black);

		return puntos;
	}

	public void addRuido(double escala) {
		for (int i = 0; i < this.ensayo[0].getEjemplos().length; i++)
			if (this.salida.getNeurona(i).getSalida() == 0.5D) {
				this.salida.getNeurona(i).setSalida(
						this.salida.getNeurona(i).getSalida() - escala
								* (Math.random() - 0.5D));
				System.out.println("Modificamos neurona " + i + ": "
						+ this.salida.getNeurona(i).getSalida());
			} else {
				if (this.salida.getNeurona(i).getSalida() != -0.5D)
					continue;
				this.salida.getNeurona(i).setSalida(
						this.salida.getNeurona(i).getSalida() + escala
								* (Math.random() - 0.5D));
				System.out.println("Modificamos neurona " + i + ": "
						+ this.salida.getNeurona(i).getSalida());
			}
	}

	public Point[] aprender(Graphics g, int alto, int ancho, int x0, int y0,
			int NItMax, double errMax, double errMin, Juego[] juego) {
		double error = errMin + 1.0D;
		double errorAnt = 1.0D;
		int nits = 1;
		int i = 1;
		Point[] puntos;
		if (ancho > NItMax) {
			puntos = new Point[ancho];
			for (int j = 0; j < ancho; j++)
				puntos[j] = new Point();
		} else {
			puntos = new Point[NItMax + 1];
			for (int j = 0; j <= NItMax; j++)
				puntos[j] = new Point();

		}

		puntos[0].x = x0;
		puntos[0].y = y0;

		Utilidad.pintarEjes(g, alto, ancho, x0, y0, NItMax, errMax, errMin);

		g.setColor(Color.red);

		while ((error > errMin) && (nits < NItMax)) {
			backpropagation(juego);
			error = error();

			if ((NItMax < ancho)
					|| (Math.IEEEremainder(nits, NItMax / ancho) == 0.0D)) {
				puntos[i].x = (x0 + nits * ancho / NItMax);
				puntos[i].y = (y0 + alto - (int) (error * alto / errMax));
				g.drawLine(puntos[(i - 1)].x, puntos[(i - 1)].y, puntos[i].x,
						puntos[i].y);
				i++;
			}
			nits++;
		}
		g.setColor(Color.black);

		return puntos;
	}

	public Point[] aprenderNoBatch(Graphics g, int alto, int ancho, int x0,
			int y0, int NItMax, double errMax, double errMin) {
		double error = 1.0D;
		double errorAnt = 1.0D;
		int nits = 1;
		int i = 1;
		int indEjemplo = 0;
		Point[] puntos;
		if (ancho > NItMax) {
			puntos = new Point[ancho];
			for (int j = 0; j < ancho; j++)
				puntos[j] = new Point();
		} else {
			puntos = new Point[NItMax + 1];
			for (int j = 0; j <= NItMax; j++)
				puntos[j] = new Point();
		}

		puntos[0].x = x0;
		puntos[0].y = y0;

		Utilidad.pintarEjes(g, alto, ancho, x0, y0, NItMax, errMax, errMin);

		g.setColor(Color.red);

		while ((error > errMin) && (nits < NItMax)) {
			backpropagationNoBatch(indEjemplo, 0);
			error = error();
			System.out.println("error: " + error + " it: " + nits);

			if ((NItMax < ancho)
					|| (Math.IEEEremainder(nits, NItMax / ancho) == 0.0D)) {
				puntos[i].x = (x0 + nits * ancho / NItMax);
				puntos[i].y = (y0 + alto - (int) (error * alto / errMax));
				g.drawLine(puntos[(i - 1)].x, puntos[(i - 1)].y, puntos[i].x,
						puntos[i].y);
				i++;
			}
			nits++;
			indEjemplo++;
			if (indEjemplo < this.ensayo[0].getEjemplos().length)
				continue;
			indEjemplo = 0;
		}
		g.setColor(Color.black);
		g.clearRect(x0, y0 + alto + 20, ancho, 30);

		return puntos;
	}

	public double error() {
		double error = 0.0D;

		for (int i = 0; i < this.prueba.length; i++)
			for (int j = 0; j < this.prueba[i].getEjemplos().length; j++) {
				ejecutar(i, j);
				error += Math.pow(this.salida.getNeurona(0).getSalida()
						- this.prueba[i].getEjemplo(j).getSalida(0), 2.0D);
			}
		return error;
	}

	public double error(Graphics g) {
		double error = 0.0D;

		for (int i = 0; i < this.prueba.length; i++) {
			g.clearRect(30, 280, 300, 400);
			for (int j = 0; j < this.prueba[i].getEjemplos().length; j++) {
				ejecutar(i, j);
				error += Math.pow(this.salida.getNeurona(0).getSalida()
						- this.prueba[i].getEjemplo(j).getSalida(0), 2.0D);
			}
		}
		return error;
	}

	public void imprimirPesos() {
		for (int i = 0; i < this.nne; i++)
			for (int j = 0; j < this.nno; j++)
				System.out.println("pesosEO[" + i + "]" + "[" + j + "]: "
						+ this.pesosEO[i][j]);
		for (int i = 0; i < this.nno; i++)
			for (int j = 0; j < this.nns; j++)
				System.out.println("pesosOS[" + i + "]" + "[" + j + "]: "
						+ this.pesosOS[i][j]);
	}

	public void imprimirPesosBias() {
		for (int i = 0; i < this.nno; i++)
			System.out.println("pesoBiasEO[" + i + "]="
					+ this.pesosEO[this.nne][i]);
		for (int i = 0; i < this.nns; i++)
			System.out.println("pesoBiasOS[" + i + "]="
					+ this.pesosOS[this.nno][i]);
	}

	public double getMu() {
		return this.mu;
	}

	public double getCAO() {
		return this.CAO;
	}

	public void setJuegos(Juego[] ensayoNuevo, Juego[] pruebaNueva) {
		this.ensayo = ensayoNuevo;
		this.prueba = pruebaNueva;
	}

	public void guardarPesos(String ficheo, String fichos) {
		DecimalFormat df = new DecimalFormat("0.0000");
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(ficheo));

			out.write(this.nne + 1 + "," + this.nno);
			out.newLine();
			for (int i = 0; i < this.nne + 1; i++) {
				for (int j = 0; j < this.nno; j++)
					out.write(new Double(this.pesosEO[i][j]).floatValue() + " ");
				out.newLine();
			}
			out.close();

			out = new BufferedWriter(new FileWriter(fichos));
			out.write(this.nno + 1 + "," + this.nns);
			out.newLine();
			for (int i = 0; i < this.nno + 1; i++) {
				for (int j = 0; j < this.nns; j++)
					out.write(new Double(this.pesosOS[i][j]).floatValue() + " ");
				out.newLine();
			}
			out.close();
			System.out.println("Pesos Almacenados Satisfactoriamente");
		} catch (IOException ioe) {
			System.err.println("Error en la lectura de ficheros: "
					+ ioe.getMessage());
		}
	}

	public void cargarPesos(URL ficheo, URL fichos) throws Exception {
		System.out.println("Vamos a leer el primer fichero");
		this.pesosOS = Utilidad.leerMatriz(fichos);
		System.out.println("Leido el fich OS");
		this.pesosEO = Utilidad.leerMatriz(ficheo);
		System.out.println("Leido el fich EO");
	}

	public void cargarPesos(String ficheo, String fichos) throws Exception {
		System.out.println("Vamos a leer el primer fichero");
		this.pesosOS = Utilidad.leerMatriz(fichos);
		System.out.println("Leido el fich OS");
		this.pesosEO = Utilidad.leerMatriz(ficheo);
		System.out.println("Leido el fich EO");
	}

	public void reiniciarPesos() {
		int i;	//vdc
		for (i = 0; i < this.nne + 1; i++) {
			for (int j = 0; j < this.nno; j++)
				this.pesosEO[i][j] = (Math.random() - 0.5D);
		}
		for (i = 0; i < this.nno + 1; i++)
			for (int j = 0; j < this.nns; j++)
				this.pesosOS[i][j] = (Math.random() - 0.5D);
	}

	public int getNne() {
		return this.nne;
	}

	public int getNno() {
		return this.nno;
	}

	public int getNns() {
		return this.nns;
	}
}