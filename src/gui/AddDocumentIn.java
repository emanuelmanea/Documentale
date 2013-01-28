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

import model.Documentin;
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
public class AddDocumentIn extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldOggetto;
	private JTextField textFieldProtocollo;
	private JDateChooser dateChooser;
	private JComboBox comboMittente;
	private EntityManager em;
	private JTextArea textAreaNote;
	private JComboBox comboBoxModalita;
	private JComboBox comboBoxTipo;
	
	private Ente ente;
	private Documentin docIn;
	private SequenceGenerator seq;
	private VerificaDataProtocollo verify;
	static Logger log4j = Logger.getLogger("documentale.addDocIn");

	public Documentin getDocIn() {
		return docIn;
	}

	public void setDocIn(Documentin docIn) {
		this.docIn = docIn;
	}

	public SequenceGenerator getSeq() {
		return seq;
	}

	public void setSeq(SequenceGenerator seq) {
		this.seq = seq;
	}
	
	public AddDocumentIn(Ente ent,EntityManager emEx,String[] mod, String[] tip,List<String> mitt) {
		em = emEx;
		ente = ent;
		
		initPersistence();
		initDocument();
		
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Inserisci Entrata");
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
			textFieldOggetto = new JTextField();
			textFieldOggetto.setColumns(10);
			GridBagConstraints gbc_textFieldOggetto = new GridBagConstraints();
			gbc_textFieldOggetto.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldOggetto.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldOggetto.gridx = 1;
			gbc_textFieldOggetto.gridy = 0;
			contentPanel.add(textFieldOggetto, gbc_textFieldOggetto);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Protocollo");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 1;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			textFieldProtocollo = new JTextField();
			textFieldProtocollo.setEnabled(false);
			textFieldProtocollo.setEditable(false);
			GridBagConstraints gbc_textFieldProtocollo = new GridBagConstraints();
			gbc_textFieldProtocollo.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldProtocollo.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldProtocollo.gridx = 1;
			gbc_textFieldProtocollo.gridy = 1;
			contentPanel.add(textFieldProtocollo, gbc_textFieldProtocollo);
			textFieldProtocollo.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Data");
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_2.gridx = 0;
			gbc_lblNewLabel_2.gridy = 2;
			contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		}
		{
			dateChooser = new JDateChooser(new Date());
			
			GridBagConstraints gbc_dateChooser = new GridBagConstraints();
			gbc_dateChooser.anchor = GridBagConstraints.NORTH;
			gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
			gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
			gbc_dateChooser.gridx = 1;
			gbc_dateChooser.gridy = 2;
			contentPanel.add(dateChooser, gbc_dateChooser);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Mittente");
			GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
			gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_3.gridx = 0;
			gbc_lblNewLabel_3.gridy = 3;
			contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		}
		{
			comboMittente = new JComboBox(mitt.toArray());
			comboMittente.setEditable(true);
			AutoCompleteDecorator.decorate(comboMittente);
			GridBagConstraints gbc_comboMittente = new GridBagConstraints();
			gbc_comboMittente.insets = new Insets(0, 0, 5, 5);
			gbc_comboMittente.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboMittente.gridx = 1;
			gbc_comboMittente.gridy = 3;
			contentPanel.add(comboMittente, gbc_comboMittente);
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
			GridBagConstraints gbc_comboBoxModalità = new GridBagConstraints();
			gbc_comboBoxModalità.insets = new Insets(0, 0, 5, 5);
			gbc_comboBoxModalità.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxModalità.gridx = 1;
			gbc_comboBoxModalità.gridy = 4;
			contentPanel.add(comboBoxModalita, gbc_comboBoxModalità);
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
			JLabel lblNote = new JLabel("Note");
			GridBagConstraints gbc_lblNote = new GridBagConstraints();
			gbc_lblNote.insets = new Insets(0, 0, 5, 5);
			gbc_lblNote.gridx = 0;
			gbc_lblNote.gridy = 6;
			contentPanel.add(lblNote, gbc_lblNote);
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
				// OK button
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						docIn.setOggetto(textFieldOggetto.getText());
						if(!verify.isAdequateIn(docIn)){
							JOptionPane.showMessageDialog(null,"Verifica la data $debug", "Errore", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						//test
						docIn.setIddocumentin(seq.documentInID(docIn, ente, em));
						docIn.setNoprotocollo(seq.documentInNoProtocollo(docIn, ente, em));
						docIn.setCodiceprotocollo(docIn.getYear() + "-E-" + docIn.getNoprotocollo());
						
						docIn.setDataprotocollo(dateChooser.getDate());						
						//TODO: exception if mittente empty(warning?)
						docIn.setMittente(comboMittente.getSelectedItem().toString());
						docIn.setNote(textAreaNote.getText());
						docIn.setModalita(comboBoxModalita.getSelectedItem().toString().toUpperCase());
						docIn.setTipo(comboBoxTipo.getSelectedItem().toString().toUpperCase());
						//ente.addDocumentIn(docIn);
						try {	
							em.getTransaction().begin();
							em.merge(ente);
							em.getTransaction().commit();
							ente.addDocumentIn(docIn);
							log4j.debug("Saving DocumenIn : " + docIn);
						}catch (Exception e) {
							e.printStackTrace();
						}
						docIn = null;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Anulla");
				// Cancel Button
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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
		docIn = new Documentin();
		
		//docIn.setIddocumentin(seq.documentInID(docIn, ente, em));
		//docIn.setNoprotocollo(seq.documentInNoProtocollo(docIn, ente, em));
		//docIn.setCodiceprotocollo("E" + docIn.getNoprotocollo());
		
		docIn.setEnte(ente);
	}
	protected void initDataBindings() {
		BeanProperty<Documentin, String> documentinBeanProperty = BeanProperty.create("codiceprotocollo");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<Documentin, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, docIn, documentinBeanProperty, textFieldProtocollo, jTextFieldBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<JDateChooser, Date> jDateChooserBeanProperty = BeanProperty.create("date");
		BeanProperty<Documentin, Date> documentinBeanProperty_1 = BeanProperty.create("dataprotocollo");
		AutoBinding<JDateChooser, Date, Documentin, Date> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, dateChooser, jDateChooserBeanProperty, docIn, documentinBeanProperty_1);
		autoBinding_1.bind();
	}
}
