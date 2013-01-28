package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Documentin extends AbstractModelObject implements Serializable {
	
	
	
	public Documentin(Date dataprotocollo, String note, Ente ente,
			String mittente, String codiceprotocollo, String oggetto,
			boolean hasPdf) {
		super();
		this.dataprotocollo = dataprotocollo;
		this.note = note;
		this.ente = ente;
		this.mittente = mittente;
		this.codiceprotocollo = codiceprotocollo;
		this.oggetto = oggetto;
		this.hasPdf = hasPdf;
	}

	private static final long serialVersionUID = 1L;

	@Id
	private int iddocumentin;

	@Lob
	private byte[] allegato;

	@Temporal(TemporalType.DATE)
	private Date dataprotocollo;
	
	@Lob
	private String note;

	@ManyToOne
	private Ente ente;

	@Lob
	private String filename;
	
	private String mittente;
	
	private String modalita;
	
	private String tipo;

	private int noprotocollo;

	private String codiceprotocollo;

	private String oggetto;

	private boolean hasPdf;

	public Documentin() {
	}
	
	public Documentin(String oggetto) {
		super();
		this.oggetto = oggetto;
	}
	
	public String getModalita() {
		return modalita;
	}

	public void setModalita(String newValue) {
		String oldValue = modalita;
		modalita = newValue;
		firePropertyChange("modalita", oldValue, newValue);
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String newValue) {
		String oldValue = tipo;
		tipo = newValue;
		firePropertyChange("tipo", oldValue, newValue);
	}

	public int getIddocumentin() {
		return this.iddocumentin;
	}

	public void setIddocumentin(int newValue) {
		int oldValue = iddocumentin;
		iddocumentin = newValue;
		firePropertyChange("iddocumentin", oldValue, newValue);
	}
	
	public String getCodiceprotocollo() {
		return codiceprotocollo;
	}

	public void setCodiceprotocollo(String newValue) {
		//String oldValue = codiceprotocollo;
		codiceprotocollo = newValue.toUpperCase();
		//firePropertyChange("codiceprotocollo", oldValue, newValue);
	}

	public byte[] getAllegato() {
		return this.allegato;
	}

	public void setAllegato(byte[] newValue) {
		byte[] oldValue = allegato;
		allegato = newValue;
		firePropertyChange("allegato", oldValue, newValue);
	}

	public Date getDataprotocollo() {
		return this.dataprotocollo;
	}
	
	public int getYear(){
		Calendar cal = Calendar.getInstance();  
		cal.setTime(this.getDataprotocollo());
		return cal.get(Calendar.YEAR);
	}

	public void setDataprotocollo(Date newValue) {
		/* la verifica si deve fare all salvataggio 
		 * 
		if(!VerificaDataProtocollo.isAdequateIn(this))
			throw new DocumentException("Verifica [NoProt,Data] fallità");
			*/
		Date oldValue = dataprotocollo;
		dataprotocollo = newValue;
		firePropertyChange("dataprotocollo", oldValue, newValue);
	}

	public String getMittente() {
		return this.mittente;
	}

	public void setMittente(String newValue) {
		String oldValue = mittente;
		mittente = newValue.toUpperCase();
		firePropertyChange("mittente", oldValue, newValue);
	}

	public int getNoprotocollo() {
		return this.noprotocollo;
	}

	public void setNoprotocollo(int newValue) {
		int oldValue = noprotocollo;
		noprotocollo = newValue;
		firePropertyChange("noprotocollo", oldValue, newValue);
	}

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String newValue) {
		String oldValue = oggetto;
		oggetto = newValue.toUpperCase();
		firePropertyChange("oggetto", oldValue, newValue);
	}
	
	public String getNote() {
		return this.note;
	}

	public void setNote(String newValue) {
		String oldValue = note;
		note = newValue.toUpperCase();
		firePropertyChange("note", oldValue, newValue);
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String newValue) {
		String oldValue = filename;
		filename = newValue;
		firePropertyChange("pdf", oldValue, newValue);
	}

	public Ente getEnte() {
		return this.ente;
	}

	public void setEnte(Ente newValue) {
		Ente oldValue = ente;
		ente = newValue;
		firePropertyChange("ente", oldValue, newValue);
	}
	
	public boolean isHasPdf() {
		if(filename == null)
			return false;
		else
			return true;
	}

	public void setHasPdf(boolean newValue) {
		boolean oldValue = hasPdf;
		hasPdf = newValue;
		firePropertyChange("hasPdf", oldValue, newValue);
	}
	
	@Override
	public String toString() {
		return "ID:"+iddocumentin + ", Ente:"+ente + ", Protocollo:"+codiceprotocollo + ", Data:"+dataprotocollo + ", Oggetto:"+oggetto;
	}

}