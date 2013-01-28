package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Ente extends AbstractModelObject  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idente;

	private String nome;

	public Ente(String nome) {
		super();
		this.nome = nome.toUpperCase();
	}

	//bi-directional many-to-one association to Documentin
	@OneToMany(mappedBy="ente",cascade = CascadeType.ALL)
	@javax.persistence.OrderBy("dataprotocollo DESC, noprotocollo DESC")
	private List<Documentin> documentins;

	//bi-directional many-to-one association to Documentout
	@OneToMany(mappedBy="ente",cascade = CascadeType.ALL)
	@javax.persistence.OrderBy("dataprotocollo DESC, noprotocollo DESC")
	private List<Documentout> documentouts;

	//bi-directional many-to-one association to Utente
	@OneToMany(mappedBy="ente",cascade = CascadeType.ALL)
	private List<Utente> utentes;

	public Ente() {
	}

	public int getIdente() {
		return this.idente;
	}

	public void setIdente(int idente) {
		this.idente = idente;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}

	public List<Documentin> getDocumentins() {
		return this.documentins;
	}

	public void setDocumentins(List<Documentin> documentins) {
		this.documentins = documentins;
	}

	public List<Documentout> getDocumentouts() {
		return this.documentouts;
	}

	public void setDocumentouts(List<Documentout> documentouts) {
		this.documentouts = documentouts;
	}

	public List<Utente> getUtentes() {
		return this.utentes;
	}

	public void setUtentes(List<Utente> utentes) {
		this.utentes = utentes;
	}
	
	public int getDocumentCountIn(){
		return documentins.size();
	}

	public int getDocumentCountOut(){
		return documentouts.size();
	}

	public void addDocumentIn(Documentin newValue){
		List<Documentin> oldValue = documentins;
		documentins = new ArrayList<Documentin>(documentins);
		documentins.add(newValue);
		firePropertyChange("documentins", oldValue, newValue);
		firePropertyChange("documentCountIn", oldValue.size(), documentins.size());
	}
	
	public void addDocumentOut(Documentout newValue){
		List<Documentout> oldValue = documentouts;
		documentouts = new ArrayList<Documentout>(documentouts);
		documentouts.add(newValue);
		firePropertyChange("documentouts", oldValue, newValue);
		firePropertyChange("documentCountOut", oldValue.size(), documentouts.size());
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
}