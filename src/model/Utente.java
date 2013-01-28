package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the utente database table.
 * 
 */
@Entity
public class Utente extends AbstractModelObject  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idutente;

	private String nome;

	private String password;

	//bi-directional many-to-one association to Ente
	@ManyToOne
	private Ente ente;

	public Utente() {
	}

	public int getIdutente() {
		return this.idutente;
	}

	public void setIdutente(int idutente) {
		this.idutente = idutente;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Ente getEnte() {
		return this.ente;
	}

	public void setEnte(Ente ente) {
		this.ente = ente;
	}

}