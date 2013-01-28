package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Documentout;
import model.Ente;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import app.SequenceGenerator;
import app.VerificaDataProtocollo;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class AddDocumentOut extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textOggetto;
	private JTextField textProtocollo;
	private JDateChooser dateChooser;
	private JComboBox comboBoxDestinatario;
	private EntityManager em;
	private JTextArea textAreaNote;
	private JComboBox comboBoxTipo;
	private JComboBox comboBoxModalita;
	
	private Ente ente;
	private Documentout docOut;
	private SequenceGenerator seq;
	private VerificaDataProtocollo verify;
	static Logger log4j = Logger.getLogger("documentale.addDocOut");

	public SequenceGenerator getSeq() {
		return seq;
	}

	public void setSeq(SequenceGenerator seq) {
		this.seq = seq;
	}

	public Ente getEnte() {
		return ente;
	}

	public void setEnte(Ente ente) {
		this.ente = ente;
	}

	public Documentout getDocOut() {
		return docOut;
	}

	public void setDocOut(Documentout newValue) {
		this.docOut = newValue;
	}

	/**
	 * Create the dialog.
	 */
	public AddDocumentOut(Ente ent,EntityManager emEx, String[] mod, String[] tip,List<String> dest) {
		em = emEx;
		ente = ent;
		
		initPersistence();
		initDocument();
		
		setModalityType(ModalityType.APPLICATION_MODAL);
				
		setResizable(false);
		setTitle("Insericsi Uscita");
		setBounds(100, 100, 450, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Oggetto");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			textOggetto = new JTextField();
			textOggetto.setColumns(10);
			GridBagConstraints gbc_textOggetto = new GridBagConstraints();
			gbc_textOggetto.insets = new Insets(0, 0, 5, 5);
			gbc_textOggetto.fill = GridBagConstraints.HORIZONTAL;
			gbc_textOggetto.gridx = 1;
			gbc_textOggetto.gridy = 0;
			contentPanel.add(textOggetto, gbc_textOggetto);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Protocollo");
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_2.gridx = 0;
			gbc_lblNewLabel_2.gridy = 1;
			contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		}
		{
			textProtocollo = new JTextField();
			textProtocollo.setEditable(false);
			textProtocollo.setEnabled(false);
			GridBagConstraints gbc_textProtocollo = new GridBagConstraints();
			gbc_textProtocollo.insets = new Insets(0, 0, 5, 5);
			gbc_textProtocollo.fill = GridBagConstraints.HORIZONTAL;
			gbc_textProtocollo.gridx = 1;
			gbc_textProtocollo.gridy = 1;
			contentPanel.add(textProtocollo, gbc_textProtocollo);
			textProtocollo.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Data");
			GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
			gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_3.gridx = 0;
			gbc_lblNewLabel_3.gridy = 2;
			contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		}
		{
			dateChooser = new JDateChooser(new Date());
			
			GridBagConstraints gbc_dateChooser = new GridBagConstraints();
			gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
			gbc_dateChooser.fill = GridBagConstraints.BOTH;
			gbc_dateChooser.gridx = 1;
			gbc_dateChooser.gridy = 2;
			contentPanel.add(dateChooser, gbc_dateChooser);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Destinatario");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 3;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			comboBoxDestinatario = new JComboBox(dest.toArray());
			comboBoxDestinatario.setEditable(true);
			AutoCompleteDecorator.decorate(comboBoxDestinatario);
			GridBagConstraints gbc_comboBoxDestinatario = new GridBagConstraints();
			gbc_comboBoxDestinatario.insets = new Insets(0, 0, 5, 5);
			gbc_comboBoxDestinatario.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxDestinatario.gridx = 1;
			gbc_comboBoxDestinatario.gridy = 3;
			contentPanel.add(comboBoxDestinatario, gbc_comboBoxDestinatario);
		}
		{
			JLabel lblModalit = new JLabel("Modalit\u00E0");
			GridBagConstraints gbc_lblModalit = new GridBagConstraints();
			gbc_lblModalit.anchor = GridBagConstraints.EAST;
			gbc_lblModalit.insets = new Insets(0, 0, 5, 5);
			gbc_lblModalit.gridx = 0;
			gbc_lblModalit.gridy = 4;
			contentPanel.add(lblModalit, gbc_lblModalit);
		}
		{
			comboBoxModalita = new JComboBox(mod);
			GridBagConstraints gbc_comboBoxModalita = new GridBagConstraints();
			gbc_comboBoxModalita.insets = new Insets(0, 0, 5, 5);
			gbc_comboBoxModalita.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxModalita.gridx = 1;
			gbc_comboBoxModalita.gridy = 4;
			contentPanel.add(comboBoxModalita, gbc_comboBoxModalita);
		}
		{
			JLabel lblTipo = new JLabel("Tipo");
			GridBagConstraints gbc_lblTipo = new GridBagConstraints();
			gbc_lblTipo.anchor = GridBagConstraints.EAST;
			gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
			gbc_lblTipo.gridx = 0;
			gbc_lblTipo.gridy = 5;
			contentPanel.add(lblTipo, gbc_lblTipo);
		}
		{
			comboBoxTipo = new JComboBox(tip);
			GridBagConstraints gbc_comboBoxTipo = new GridBagConstraints();
			gbc_comboBoxTipo.insets = new Insets(0, 0, 5, 5);
			gbc_comboBoxTipo.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxTipo.gridx = 1;
			gbc_comboBoxTipo.gridy = 5;
			contentPanel.add(comboBoxTipo, gbc_comboBoxTipo);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Note");
			GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
			gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_4.gridx = 0;
			gbc_lblNewLabel_4.gridy = 6;
			contentPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.gridheight = 3;
			gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 1;
			gbc_scrollPane.gridy = 6;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				textAreaNote = new JTextArea();
				scrollPane.setViewportView(textAreaNote);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Salva");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						docOut.setOggetto(textOggetto.getText());
						if(!verify.isAdequateOut(docOut)){
							JOptionPane.showMessageDialog(null,"Verifica la data", "Errore", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						//test
						docOut.setIddocumentout(seq.documentOutID(docOut, ente, em));
						docOut.setNoprotocollo(seq.documentOutNoProtocollo(docOut, ente, em));
						docOut.setCodiceprotocollo(docOut.getYear() + "-U-" + docOut.getNoprotocollo());
						
						docOut.setDataprotocollo(dateChooser.getDate());
						docOut.setDestinatario(comboBoxDestinatario.getSelectedItem().toString());
						docOut.setNote(textAreaNote.getText());
						docOut.setModalita(comboBoxModalita.getSelectedItem().toString().toUpperCase());
						docOut.setTipo(comboBoxTipo.getSelectedItem().toString().toUpperCase());
						try {
														
							em.getTransaction().begin();
							em.merge(ente);
							em.getTransaction().commit();
							ente.addDocumentOut(docOut);
							log4j.debug("Saving DocumenOut : " + docOut);
						}catch (Exception e) {
							e.printStackTrace();
						}
						docOut = null;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Anulla");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		initDataBindings();
	}
	
	private void initPersistence(){
		seq = new SequenceGenerator();
		verify = new VerificaDataProtocollo();
	}
	
	private void initDocument(){
		docOut = new Documentout();
		
		//docOut.setIddocumentout(seq.documentOutID(docOut, ente, em));
		//docOut.setNoprotocollo(seq.documentOutNoProtocollo(docOut, ente, em));
		//docOut.setCodiceprotocollo("U" + docOut.getNoprotocollo());
		
		docOut.setEnte(ente);
	}
	protected void initDataBindings() {
		BeanProperty<Documentout, String> documentoutBeanProperty = BeanProperty.create("codiceprotocollo");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<Documentout, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, docOut, documentoutBeanProperty, textProtocollo, jTextFieldBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<JDateChooser, Date> jDateChooserBeanProperty = BeanProperty.create("date");
		BeanProperty<Documentout, Date> documentoutBeanProperty_1 = BeanProperty.create("dataprotocollo");
		AutoBinding<JDateChooser, Date, Documentout, Date> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, dateChooser, jDateChooserBeanProperty, docOut, documentoutBeanProperty_1);
		autoBinding_1.bind();
	}
}
