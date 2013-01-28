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
public class Documentout extends AbstractModelObject  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int iddocumentout;

	@Temporal(TemporalType.DATE)
	private Date dataprotocollo;
	
	@Lob
	private String note;

	@Lob
	private byte[] allegato;
	
	@ManyToOne
	private Ente ente;
	
	private String oggetto;
	
	private int noprotocollo;
	
	private String modalita;
	
	private String tipo;
	
	private String codiceprotocollo;
	
	private String destinatario;
	
	@Lob
	private String filename;
	
	private boolean hasPdf;

	public Documentout() {
	}
	
	public Documentout(String oggetto) {
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

	public int getIddocumentout() {
		return this.iddocumentout;
	}

	public void setIddocumentout(int newValue) {
		int oldValue = iddocumentout;
		iddocumentout = newValue;
		firePropertyChange("iddocumentout", oldValue, newValue);
	}
	
	public String getCodiceprotocollo() {
		return codiceprotocollo;
	}

	public void setCodiceprotocollo(String newValue) {
		String oldValue = codiceprotocollo;
		codiceprotocollo = newValue.toUpperCase();
		firePropertyChange("codiceprotocollo", oldValue, newValue);
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
		/* la verifica si deve fare al salvataggio dei dati
		 * 
		 * if(!VerificaDataProtocollo.isAdequateOut(this))
			throw new DocumentException("Verifica [NoProt,Data] fallità");
			*/
		Date oldValue = dataprotocollo;
		dataprotocollo = newValue;
		firePropertyChange("dataprotocollo", oldValue, newValue);
	}

	public String getDestinatario() {
		return this.destinatario;
	}

	public void setDestinatario(String newValue) {
		String oldValue = destinatario;
		destinatario = newValue.toUpperCase();
		firePropertyChange("destinatario", oldValue, newValue);
	}

	public int getNoprotocollo() {
		return this.noprotocollo;
	}

	public void setNoprotocollo(int newValue) {
		int oldValue = noprotocollo;
		noprotocollo = newValue;
		firePropertyChange("noprotocollo", oldValue, newValue);
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String newValue) {
		String oldValue = note;
		note = newValue.toUpperCase();
		firePropertyChange("note", oldValue, newValue);
	}

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String newValue) {
		String oldValue = oggetto;
		oggetto = newValue.toUpperCase();
		firePropertyChange("oggetto", oldValue, newValue);
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

	public Documentout(Date dataprotocollo, String note, Ente ente,
			String oggetto, String codiceprotocollo, String destinatario,
			boolean hasPdf) {
		super();
		this.dataprotocollo = dataprotocollo;
		this.note = note;
		this.ente = ente;
		this.oggetto = oggetto;
		this.codiceprotocollo = codiceprotocollo;
		this.destinatario = destinatario;
		this.hasPdf = hasPdf;
	}

	public void setHasPdf(boolean newValue) {
		boolean oldValue = hasPdf;
		hasPdf = newValue;
		firePropertyChange("hasPdf", oldValue, newValue);
	}
	
	@Override
	public String toString() {
		return "ID:"+iddocumentout + ", Ente:"+ente + ", Protocollo:"+codiceprotocollo + ", Data:"+dataprotocollo + ", Oggetto:"+oggetto;
	}

}