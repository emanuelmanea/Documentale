package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import model.Documentin;
import model.DocumentinTableModel;
import model.Documentout;
import model.DocumentoutTableModel;
import model.Ente;
import model.EnteGroup;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import SK.gnome.twain.TwainException;
import app.DbConn;
import app.Scanner;
import app.SequenceGenerator;

import com.itextpdf.text.DocumentException;
import com.toedter.calendar.JDateChooser;



public class Documentale extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser fileChooser;
	private JFrame frmGestioneProtocolli;
	private JTable tableDocIn;
	private JTable tableDocOut;
	private JList list;
	private AddEnte frameAddEnte;
	
	Scanner scanner;
	
	EntityManagerFactory emf;
	static EntityManager em;
	SequenceGenerator sequence;
	
	EnteGroup enteGroup;
	Documentin docIn;
	Documentout docOut;
	Ente ente;
	
	@SuppressWarnings("unused")
	private List<Documentin> searchDocIn;
	@SuppressWarnings("unused")
	private List<Documentout> searchDocOut;
	
	private String documentBase;
	
	private JTextField textFieldOggettoOut;
	private JTextField textFieldOggettoIn;
	private JTextField textFieldProtocolloIn;
	private JDateChooser dateChooser;
	private JTextField textFieldProtocolloOut;
	private JTextArea textAreaNoteOut;
	private JDateChooser dateChooser_1;
	private JTable tableSearch;
	private JTextField textFieldSearchOggetto;
	private JTextField textFieldSearchMittente;
	private JComboBox comboSearchEnte;
	private JTextArea textAreaSearchNote;
	
	@SuppressWarnings("unused")
	private List<Documentin> searchListDocIn;
	@SuppressWarnings("unused")
	private List<Documentout> searchListDocOut;
	private JTextArea textAreaNoteIn;
	private JComboBox comboBoxModalitaIn;
	private JComboBox comboBoxTipoIn;
	private JComboBox comboBoxMittente;
	private JComboBox comboBoxModalitaOut;
	private JComboBox comboBoxTipoOut;
	private String[] modalita = {"FAX","POSTA","RACCOMANDATA"};
	private String[] tipo = {"FATTURA","RICEVUTA","VARI"};
	private List<String> mittenteList = null;
	private List<String> destinatarioList;
	private JComboBox comboBoxDestinatario;
	private JComboBox comboSearchModalita;
	private JComboBox comboSearchTipo;
	static Logger log4j = Logger.getLogger("documentale.main");

	/**
	 * Launch the application.
	 */
	
	public static EntityManager getEm(){
		return em;
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
				} catch (UnsupportedLookAndFeelException e) {
				    e.printStackTrace();
				} catch (ClassNotFoundException e) {
				    e.printStackTrace();
				} catch (InstantiationException e) {
				    e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
				try {					
					Documentale window = new Documentale();
					window.frmGestioneProtocolli.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Documentale() {
		log4j.debug("Starting the application...");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initPersistence();
		initEnteList();
		initSequence();
		initScanner();
		initMittenteList();
		initDestinatarioList();
		
		frmGestioneProtocolli = new JFrame();

		frmGestioneProtocolli.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int exitApp= JOptionPane.showConfirmDialog(null,"Sei sicuro di voler uscire dall'applicativo?", "Please Confirm", JOptionPane.YES_NO_OPTION);
				if (exitApp==JOptionPane.YES_OPTION){

					try {
						em.getTransaction().begin();
						for(Ente lst : enteGroup.getMembers())
							em.merge(lst);
						em.getTransaction().commit();
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
					//System.out.println("Menu Salva");
					log4j.debug("Menu Salva");
					
					em.close();
					
					//System.out.print("Exiting the app....");
					log4j.debug("Exiting the app....");
					
					System.exit(0);
					}
				}
			});
		
		frmGestioneProtocolli.setTitle("Gestione Protocolli");
		
		frmGestioneProtocolli.setBounds(100, 100, 722, 571);
		frmGestioneProtocolli.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmGestioneProtocolli.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelInserimento = new JPanel();
		tabbedPane.addTab("Enti", null, panelInserimento, null);
		panelInserimento.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		panelInserimento.add(splitPane, BorderLayout.CENTER);
		
		JPanel panelListEnte = new JPanel();
		splitPane.setLeftComponent(panelListEnte);
		panelListEnte.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panelListEnte.add(toolBar, BorderLayout.NORTH);
		
		JButton btnNuovoEnte = new JButton("Nuovo Ente");
		btnNuovoEnte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameAddEnte = new AddEnte();
				frameAddEnte.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
				frameAddEnte.setVisible(true);
			}
		});
		toolBar.add(btnNuovoEnte);
		
		JScrollPane scrollPane = new JScrollPane();
		panelListEnte.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList();
		scrollPane.setViewportView(list);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane_1);
		
		JPanel panelEntrata = new JPanel();
		tabbedPane_1.addTab("Entrata", null, panelEntrata, null);
		panelEntrata.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.5);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panelEntrata.add(splitPane_1, BorderLayout.CENTER);
		
		JPanel panel_7 = new JPanel();
		splitPane_1.setLeftComponent(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setFloatable(false);
		panel_7.add(toolBar_1, BorderLayout.NORTH);
		
		JButton btnNuovoEntrata = new JButton("Nuovo Entrata");
		btnNuovoEntrata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null,"Nessun ente selezionato", "Errore", JOptionPane.ERROR_MESSAGE);
				else{	
					try {
						ente = enteGroup.getGroups().get(list.getSelectedIndex());
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
					AddDocumentIn frameDocIn = new AddDocumentIn(ente,em,modalita,tipo,mittenteList);
					frameDocIn.setVisible(true);
				}
				
				//da provare
				initMittenteList();
			}
		});
		toolBar_1.add(btnNuovoEntrata);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_7.add(scrollPane_1, BorderLayout.CENTER);
		
		tableDocIn = new JTable();
		tableDocIn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(tableDocIn);
		
		JPanel panel_9 = new JPanel();
		splitPane_1.setRightComponent(panel_9);
		GridBagLayout gbl_panel_9 = new GridBagLayout();
		gbl_panel_9.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_9.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_9.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_9.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_9.setLayout(gbl_panel_9);
		
		JLabel lblOggettoIn = new JLabel("Oggetto");
		GridBagConstraints gbc_lblOggettoIn = new GridBagConstraints();
		gbc_lblOggettoIn.anchor = GridBagConstraints.EAST;
		gbc_lblOggettoIn.insets = new Insets(0, 0, 5, 5);
		gbc_lblOggettoIn.gridx = 0;
		gbc_lblOggettoIn.gridy = 0;
		panel_9.add(lblOggettoIn, gbc_lblOggettoIn);
		
		textFieldOggettoIn = new JTextField();
		GridBagConstraints gbc_textFieldOggettoIn = new GridBagConstraints();
		gbc_textFieldOggettoIn.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldOggettoIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldOggettoIn.gridx = 1;
		gbc_textFieldOggettoIn.gridy = 0;
		panel_9.add(textFieldOggettoIn, gbc_textFieldOggettoIn);
		textFieldOggettoIn.setColumns(10);
		
		JLabel lblProtocolloIn = new JLabel("Protocollo");
		GridBagConstraints gbc_lblProtocolloIn = new GridBagConstraints();
		gbc_lblProtocolloIn.anchor = GridBagConstraints.EAST;
		gbc_lblProtocolloIn.insets = new Insets(0, 0, 5, 5);
		gbc_lblProtocolloIn.gridx = 0;
		gbc_lblProtocolloIn.gridy = 1;
		panel_9.add(lblProtocolloIn, gbc_lblProtocolloIn);
		
		textFieldProtocolloIn = new JTextField();
		textFieldProtocolloIn.setEnabled(false);
		textFieldProtocolloIn.setEditable(false);
		GridBagConstraints gbc_textFieldProtocolloIn = new GridBagConstraints();
		gbc_textFieldProtocolloIn.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldProtocolloIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldProtocolloIn.gridx = 1;
		gbc_textFieldProtocolloIn.gridy = 1;
		panel_9.add(textFieldProtocolloIn, gbc_textFieldProtocolloIn);
		textFieldProtocolloIn.setColumns(10);
		
		JLabel lblDataIn = new JLabel("Data");
		GridBagConstraints gbc_lblDataIn = new GridBagConstraints();
		gbc_lblDataIn.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataIn.gridx = 0;
		gbc_lblDataIn.gridy = 2;
		panel_9.add(lblDataIn, gbc_lblDataIn);
		
		dateChooser = new JDateChooser();
		dateChooser.setEnabled(false);
		
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.anchor = GridBagConstraints.NORTH;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 2;
		panel_9.add(dateChooser, gbc_dateChooser);
		
		JLabel lblMittenteIn = new JLabel("Mittente");
		GridBagConstraints gbc_lblMittenteIn = new GridBagConstraints();
		gbc_lblMittenteIn.anchor = GridBagConstraints.EAST;
		gbc_lblMittenteIn.insets = new Insets(0, 0, 5, 5);
		gbc_lblMittenteIn.gridx = 0;
		gbc_lblMittenteIn.gridy = 3;
		panel_9.add(lblMittenteIn, gbc_lblMittenteIn);
		
		comboBoxMittente = new JComboBox();
		comboBoxMittente.setEditable(true);
		AutoCompleteDecorator.decorate(comboBoxMittente);
		GridBagConstraints gbc_comboBoxMittente = new GridBagConstraints();
		gbc_comboBoxMittente.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxMittente.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxMittente.gridx = 1;
		gbc_comboBoxMittente.gridy = 3;
		panel_9.add(comboBoxMittente, gbc_comboBoxMittente);
		
		JLabel lblNewLabel_2 = new JLabel("Note");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		panel_9.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_6 = new GridBagConstraints();
		gbc_scrollPane_6.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_6.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_6.gridx = 1;
		gbc_scrollPane_6.gridy = 4;
		panel_9.add(scrollPane_6, gbc_scrollPane_6);
		
		textAreaNoteIn = new JTextArea();
		scrollPane_6.setViewportView(textAreaNoteIn);
		
		JLabel lblModalit = new JLabel("Modalit\u00E0");
		GridBagConstraints gbc_lblModalit = new GridBagConstraints();
		gbc_lblModalit.anchor = GridBagConstraints.EAST;
		gbc_lblModalit.insets = new Insets(0, 0, 5, 5);
		gbc_lblModalit.gridx = 0;
		gbc_lblModalit.gridy = 5;
		panel_9.add(lblModalit, gbc_lblModalit);
		
		comboBoxModalitaIn = new JComboBox(modalita);
		GridBagConstraints gbc_comboBoxModalitaIn = new GridBagConstraints();
		gbc_comboBoxModalitaIn.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxModalitaIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxModalitaIn.gridx = 1;
		gbc_comboBoxModalitaIn.gridy = 5;
		panel_9.add(comboBoxModalitaIn, gbc_comboBoxModalitaIn);
		
		JLabel lblTipo = new JLabel("Tipo");
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.anchor = GridBagConstraints.EAST;
		gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo.gridx = 0;
		gbc_lblTipo.gridy = 6;
		panel_9.add(lblTipo, gbc_lblTipo);
		
		comboBoxTipoIn = new JComboBox(tipo);
		GridBagConstraints gbc_comboBoxTipoIn = new GridBagConstraints();
		gbc_comboBoxTipoIn.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxTipoIn.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTipoIn.gridx = 1;
		gbc_comboBoxTipoIn.gridy = 6;
		panel_9.add(comboBoxTipoIn, gbc_comboBoxTipoIn);
		
		JLabel lblPdfIn = new JLabel("PDF");
		GridBagConstraints gbc_lblPdfIn = new GridBagConstraints();
		gbc_lblPdfIn.insets = new Insets(0, 0, 5, 5);
		gbc_lblPdfIn.gridx = 0;
		gbc_lblPdfIn.gridy = 7;
		panel_9.add(lblPdfIn, gbc_lblPdfIn);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 7;
		panel_9.add(panel, gbc_panel);
		
		JButton btnCaricaPdfIn = new JButton("Carica PDF");
		btnCaricaPdfIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File selectedFile;
				try {
					
										
					Documentin doc = enteGroup.getMembers().get(list.getSelectedIndex()).getDocumentins().get(tableDocIn.getSelectedRow());
					
					if(doc.isHasPdf()){
						int exitApp= JOptionPane.showConfirmDialog(null,"PDF presente, vuoi sovrascrivere?", "Please Confirm", JOptionPane.YES_NO_OPTION);
						
						if(exitApp==JOptionPane.NO_OPTION)
							return;
						
						if (exitApp==JOptionPane.YES_OPTION){
							fileChooser = new JFileChooser();
							int returnVal = fileChooser.showOpenDialog(Documentale.this);
							
							if (returnVal == JFileChooser.APPROVE_OPTION) {
					            selectedFile = fileChooser.getSelectedFile();
					            //System.out.println(selectedFile);
					            //System.out.println(selectedFile.toString());
					            log4j.debug(selectedFile);
					        }
							else{
					            //System.out.println("File Open cancelled by user.");
					            log4j.debug("File Open cancelled by user.");
					            return;
					        }
							
							String file = documentBase + "\\" + doc.getIddocumentin() + doc.getEnte().toString() + "In" + doc.getCodiceprotocollo() + ".pdf";
							FileOutputStream out = new FileOutputStream(file);
							out.write( scanner.writeOverPdf(doc.getEnte().getNome() , doc.getCodiceprotocollo(),doc.getDataprotocollo(),selectedFile.toString()) );
							out.close();
							
							em.getTransaction().begin();
							
							em.find(Documentin.class, doc.getIddocumentin()).setFilename(file);
							em.merge(em.find(Documentin.class, doc.getIddocumentin()));
							
							em.getTransaction().commit();
							doc.setHasPdf(true);
							log4j.debug("Sovrascrittura allegato per il Documento in entrata: " + doc);
						}
					}
					else{
						fileChooser = new JFileChooser();
						int returnVal = fileChooser.showOpenDialog(Documentale.this);
						
						if (returnVal == JFileChooser.APPROVE_OPTION) {
				            selectedFile = fileChooser.getSelectedFile();
				            //System.out.println(selectedFile);
				            //System.out.println(selectedFile.toString());
				            log4j.debug(selectedFile);
				        }
						else{
				            //System.out.println("File Open cancelled by user.");
				            log4j.debug("File Open cancelled by user.");
				            return;
				        }
						
						String file = documentBase + "\\" + doc.getIddocumentin() + doc.getEnte().toString() + "In" + doc.getCodiceprotocollo() + ".pdf";
						FileOutputStream out = new FileOutputStream(file);
						out.write( scanner.writeOverPdf(doc.getEnte().getNome() , doc.getCodiceprotocollo(),doc.getDataprotocollo(),selectedFile.toString()) );
						out.close();
						
						em.getTransaction().begin();
						
						em.find(Documentin.class, doc.getIddocumentin()).setFilename(file);
						em.merge(em.find(Documentin.class, doc.getIddocumentin()));
						
						em.getTransaction().commit();
						doc.setHasPdf(true);
						log4j.debug("Scrittura allegato per il Documento in entrata: " + doc);
					}
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
				
				/*
				 * fileChooser = new JFileChooser();
				
				int returnVal = fileChooser.showOpenDialog(Documentale.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            System.out.println(file);
		        }
				else{
		            System.out.println("File Open cancelled by user.");
		        }
		        */
			}
		});
		panel.add(btnCaricaPdfIn);
		
		JButton btnScanPdfIn = new JButton("Scan Pdf");
		panel.add(btnScanPdfIn);
		
		JButton btnViewPdfIn = new JButton("Visualiza PDF");
		panel.add(btnViewPdfIn);
		btnViewPdfIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Documentin doc = enteGroup.getMembers().get(list.getSelectedIndex()).getDocumentins().get(tableDocIn.getSelectedRow());
				try {
					Runtime.getRuntime().exec( "c:\\Windows\\System32\\rundll32.exe url.dll,FileProtocolHandler " + doc.getFilename());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnScanPdfIn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				try {
					Documentin doc = enteGroup.getMembers().get(list.getSelectedIndex()).getDocumentins().get(tableDocIn.getSelectedRow());
					
					if(doc.isHasPdf()){
						int exitApp= JOptionPane.showConfirmDialog(null,"PDF presente, vuoi sovrascrivere?", "Please Confirm", JOptionPane.YES_NO_OPTION);
						
						if(exitApp==JOptionPane.NO_OPTION)
							return;
						
						if (exitApp==JOptionPane.YES_OPTION){
							String file = documentBase + doc.getIddocumentin() + doc.getEnte().toString() + "In" + doc.getCodiceprotocollo() + ".pdf";
							FileOutputStream out = new FileOutputStream(file);
							out.write( scanner.example( doc.getEnte().getNome() , doc.getCodiceprotocollo(),doc.getDataprotocollo() ) );
							out.close();
							
							em.getTransaction().begin();
							em.find(Documentin.class, doc.getIddocumentin()).setFilename(file);
							em.merge(em.find(Documentin.class, doc.getIddocumentin()));
							em.getTransaction().commit();
							doc.setHasPdf(true);
							log4j.debug("Sovrascrittura allegato per il Documento in entrata: " + doc);
						}
					}
					else{
						String file = documentBase + "\\" + doc.getIddocumentin() + doc.getEnte().toString() + "In" + doc.getCodiceprotocollo() + ".pdf";
						FileOutputStream out = new FileOutputStream(file);
						out.write( scanner.example( doc.getEnte().getNome() , doc.getCodiceprotocollo(),doc.getDataprotocollo() ) );
						out.close();
						
						em.getTransaction().begin();
						em.find(Documentin.class, doc.getIddocumentin()).setFilename(file);
						em.merge(em.find(Documentin.class, doc.getIddocumentin()));

						em.getTransaction().commit();
						doc.setHasPdf(true);
						log4j.debug("Scrittura allegato per il Documento in entrata: " + doc);
					}
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (TwainException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		});
		
		JPanel panelUscita = new JPanel();
		tabbedPane_1.addTab("Uscita", null, panelUscita, null);
		panelUscita.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setResizeWeight(0.5);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panelUscita.add(splitPane_2, BorderLayout.CENTER);
		
		JPanel panel_8 = new JPanel();
		splitPane_2.setLeftComponent(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar_2 = new JToolBar();
		toolBar_2.setFloatable(false);
		panel_8.add(toolBar_2, BorderLayout.NORTH);
		
		JButton btnNuovoUscita = new JButton("Nuovo Uscita");
		btnNuovoUscita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null,"Nessun ente selezionato", "Errore", JOptionPane.ERROR_MESSAGE);
				else{
					try {
						ente = enteGroup.getGroups().get(list.getSelectedIndex());
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					AddDocumentOut frameDocOut = new AddDocumentOut(ente,em,modalita,tipo,destinatarioList);
					frameDocOut.setVisible(true);
				}
				
				//da provare
				initDestinatarioList();
			}
		});
		toolBar_2.add(btnNuovoUscita);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_8.add(scrollPane_2, BorderLayout.CENTER);
		
		tableDocOut = new JTable();
		tableDocOut.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(tableDocOut);
		
		JPanel panel_10 = new JPanel();
		splitPane_2.setRightComponent(panel_10);
		GridBagLayout gbl_panel_10 = new GridBagLayout();
		gbl_panel_10.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_10.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_10.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_10.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_10.setLayout(gbl_panel_10);
		
		JLabel lblOggettoOut = new JLabel("Oggetto");
		GridBagConstraints gbc_lblOggettoOut = new GridBagConstraints();
		gbc_lblOggettoOut.anchor = GridBagConstraints.EAST;
		gbc_lblOggettoOut.insets = new Insets(0, 0, 5, 5);
		gbc_lblOggettoOut.gridx = 0;
		gbc_lblOggettoOut.gridy = 0;
		panel_10.add(lblOggettoOut, gbc_lblOggettoOut);
		
		textFieldOggettoOut = new JTextField();
		GridBagConstraints gbc_textFieldOggettoOut = new GridBagConstraints();
		gbc_textFieldOggettoOut.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldOggettoOut.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldOggettoOut.gridx = 1;
		gbc_textFieldOggettoOut.gridy = 0;
		panel_10.add(textFieldOggettoOut, gbc_textFieldOggettoOut);
		textFieldOggettoOut.setColumns(10);
		
		JLabel lblProtocolloOut = new JLabel("Protocollo");
		GridBagConstraints gbc_lblProtocolloOut = new GridBagConstraints();
		gbc_lblProtocolloOut.anchor = GridBagConstraints.EAST;
		gbc_lblProtocolloOut.insets = new Insets(0, 0, 5, 5);
		gbc_lblProtocolloOut.gridx = 0;
		gbc_lblProtocolloOut.gridy = 1;
		panel_10.add(lblProtocolloOut, gbc_lblProtocolloOut);
		
		textFieldProtocolloOut = new JTextField();
		textFieldProtocolloOut.setEnabled(false);
		textFieldProtocolloOut.setEditable(false);
		GridBagConstraints gbc_textFieldProtocolloOut = new GridBagConstraints();
		gbc_textFieldProtocolloOut.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldProtocolloOut.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldProtocolloOut.gridx = 1;
		gbc_textFieldProtocolloOut.gridy = 1;
		panel_10.add(textFieldProtocolloOut, gbc_textFieldProtocolloOut);
		textFieldProtocolloOut.setColumns(10);
		
		JLabel lblDataOut = new JLabel("Data");
		GridBagConstraints gbc_lblDataOut = new GridBagConstraints();
		gbc_lblDataOut.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataOut.gridx = 0;
		gbc_lblDataOut.gridy = 2;
		panel_10.add(lblDataOut, gbc_lblDataOut);
		
		dateChooser_1 = new JDateChooser();
		dateChooser_1.setEnabled(false);
		
		GridBagConstraints gbc_dateChooser_1 = new GridBagConstraints();
		gbc_dateChooser_1.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser_1.fill = GridBagConstraints.BOTH;
		gbc_dateChooser_1.gridx = 1;
		gbc_dateChooser_1.gridy = 2;
		panel_10.add(dateChooser_1, gbc_dateChooser_1);
		
		JLabel lblDestinatarioOut = new JLabel("Destinatario");
		GridBagConstraints gbc_lblDestinatarioOut = new GridBagConstraints();
		gbc_lblDestinatarioOut.anchor = GridBagConstraints.EAST;
		gbc_lblDestinatarioOut.insets = new Insets(0, 0, 5, 5);
		gbc_lblDestinatarioOut.gridx = 0;
		gbc_lblDestinatarioOut.gridy = 3;
		panel_10.add(lblDestinatarioOut, gbc_lblDestinatarioOut);
		
		comboBoxDestinatario = new JComboBox();
		comboBoxDestinatario.setEditable(true);
		AutoCompleteDecorator.decorate(comboBoxDestinatario);
		GridBagConstraints gbc_comboBoxDestinatario = new GridBagConstraints();
		gbc_comboBoxDestinatario.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxDestinatario.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxDestinatario.gridx = 1;
		gbc_comboBoxDestinatario.gridy = 3;
		panel_10.add(comboBoxDestinatario, gbc_comboBoxDestinatario);
		
		JLabel lblNote = new JLabel("Note");
		GridBagConstraints gbc_lblNote = new GridBagConstraints();
		gbc_lblNote.insets = new Insets(0, 0, 5, 5);
		gbc_lblNote.gridx = 0;
		gbc_lblNote.gridy = 4;
		panel_10.add(lblNote, gbc_lblNote);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 1;
		gbc_scrollPane_3.gridy = 4;
		panel_10.add(scrollPane_3, gbc_scrollPane_3);
		
		textAreaNoteOut = new JTextArea();
		scrollPane_3.setViewportView(textAreaNoteOut);
		
		JLabel lblNewLabel_3 = new JLabel("Modalit\u00E0");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 5;
		panel_10.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		comboBoxModalitaOut = new JComboBox(modalita);//
		GridBagConstraints gbc_comboBoxModalitaOut = new GridBagConstraints();
		gbc_comboBoxModalitaOut.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxModalitaOut.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxModalitaOut.gridx = 1;
		gbc_comboBoxModalitaOut.gridy = 5;
		panel_10.add(comboBoxModalitaOut, gbc_comboBoxModalitaOut);
		
		JLabel lblNewLabel_4 = new JLabel("Tipo");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 6;
		panel_10.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		comboBoxTipoOut = new JComboBox(tipo);//Ricevuta 
		GridBagConstraints gbc_comboBoxTipoOut = new GridBagConstraints();
		gbc_comboBoxTipoOut.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxTipoOut.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTipoOut.gridx = 1;
		gbc_comboBoxTipoOut.gridy = 6;
		panel_10.add(comboBoxTipoOut, gbc_comboBoxTipoOut);
		
		JLabel lblNewLabel_11 = new JLabel("PDF");
		GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
		gbc_lblNewLabel_11.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_11.gridx = 0;
		gbc_lblNewLabel_11.gridy = 7;
		panel_10.add(lblNewLabel_11, gbc_lblNewLabel_11);
		
		JPanel panel_12 = new JPanel();
		GridBagConstraints gbc_panel_12 = new GridBagConstraints();
		gbc_panel_12.insets = new Insets(0, 0, 0, 5);
		gbc_panel_12.gridx = 1;
		gbc_panel_12.gridy = 7;
		panel_10.add(panel_12, gbc_panel_12);
		GridBagLayout gbl_panel_12 = new GridBagLayout();
		gbl_panel_12.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_12.rowHeights = new int[]{0, 0};
		gbl_panel_12.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_12.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_12.setLayout(gbl_panel_12);
		
		JButton btnCaricaPdfOut = new JButton("Carica PDF");
		btnCaricaPdfOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File selectedFile;
				try {
										
					Documentout doc = enteGroup.getMembers().get(list.getSelectedIndex()).getDocumentouts().get(tableDocOut.getSelectedRow());
					
					if(doc.isHasPdf()){
						int exitApp= JOptionPane.showConfirmDialog(null,"PDF presente, vuoi sovrascrivere?", "Please Confirm", JOptionPane.YES_NO_OPTION);
						
						if(exitApp==JOptionPane.NO_OPTION)
							return;
						
						if (exitApp==JOptionPane.YES_OPTION){
							fileChooser = new JFileChooser();
							int returnVal = fileChooser.showOpenDialog(Documentale.this);
							
							if (returnVal == JFileChooser.APPROVE_OPTION) {
					            selectedFile = fileChooser.getSelectedFile();
					            //System.out.println(selectedFile);
					            //System.out.println(selectedFile.toString());
					            log4j.debug(selectedFile);
					        }
							else{
					            //System.out.println("File Open cancelled by user.");
					            log4j.debug("File Open cancelled by user.");
					            return;
					        }
							
							String file = documentBase + "\\" + doc.getIddocumentout() + doc.getEnte().toString() + "Out" + doc.getCodiceprotocollo() + ".pdf";
							FileOutputStream out = new FileOutputStream(file);
							out.write( scanner.writeOverPdf(doc.getEnte().getNome() , doc.getCodiceprotocollo(),doc.getDataprotocollo(),selectedFile.toString()) );
							out.close();
							
							em.getTransaction().begin();
							
							em.find(Documentout.class, doc.getIddocumentout()).setFilename(file);
							em.merge(em.find(Documentout.class, doc.getIddocumentout()));
							
							em.getTransaction().commit();
							doc.setHasPdf(true);
							log4j.debug("Sovrascrittura allegato per il Documento in uscita: " + doc);
						}
					}
					else{
						fileChooser = new JFileChooser();
						int returnVal = fileChooser.showOpenDialog(Documentale.this);
						
						if (returnVal == JFileChooser.APPROVE_OPTION) {
				            selectedFile = fileChooser.getSelectedFile();
				            //System.out.println(selectedFile);
				            //System.out.println(selectedFile.toString());
				            log4j.debug(selectedFile);
				        }
						else{
				            //System.out.println("File Open cancelled by user.");
				            log4j.debug("File Open cancelled by user.");
				            return;
				        }
						
						String file = documentBase + "\\" + doc.getIddocumentout() + doc.getEnte().toString() + "Out" + doc.getCodiceprotocollo() + ".pdf";
						FileOutputStream out = new FileOutputStream(file);
						out.write( scanner.writeOverPdf(doc.getEnte().getNome() , doc.getCodiceprotocollo(),doc.getDataprotocollo(),selectedFile.toString()) );
						out.close();
						
						em.getTransaction().begin();
						
						em.find(Documentout.class, doc.getIddocumentout()).setFilename(file);
						em.merge(em.find(Documentout.class, doc.getIddocumentout()));
						
						em.getTransaction().commit();
						doc.setHasPdf(true);
						log4j.debug("Scrittura allegato per il Documento in uscita: " + doc);
					}
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
				
				/*
				 * fileChooser = new JFileChooser();
				
				int returnVal = fileChooser.showOpenDialog(Documentale.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            System.out.println(file);
		        }
				else{
		            System.out.println("File Open cancelled by user.");
		        }
		        */
			}
		});
		GridBagConstraints gbc_btnCaricaPdfOut = new GridBagConstraints();
		gbc_btnCaricaPdfOut.insets = new Insets(0, 0, 0, 5);
		gbc_btnCaricaPdfOut.gridx = 1;
		gbc_btnCaricaPdfOut.gridy = 0;
		panel_12.add(btnCaricaPdfOut, gbc_btnCaricaPdfOut);
		
		JButton btnPdf = new JButton("Scan PDF");
		btnPdf.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				try {
					Documentout doc = enteGroup.getMembers().get(list.getSelectedIndex()).getDocumentouts().get(tableDocOut.getSelectedRow());
					
					if(doc.isHasPdf()){
						int exitApp= JOptionPane.showConfirmDialog(null,"PDF presente, vuoi sovrascrivere?", "Please Confirm", JOptionPane.YES_NO_OPTION);
						
						if(exitApp==JOptionPane.NO_OPTION)
							return;
						
						if(exitApp==JOptionPane.YES_OPTION){
							String file = documentBase + doc.getIddocumentout() + doc.getEnte().toString() + "Out" + doc.getCodiceprotocollo() + ".pdf";
							FileOutputStream out = new FileOutputStream(file);
							out.write( scanner.example( doc.getEnte().getNome() , doc.getCodiceprotocollo(),doc.getDataprotocollo() ) );
							out.close();
							
							em.getTransaction().begin();
							em.find(Documentout.class, doc.getIddocumentout()).setFilename(file);
							em.merge(em.find(Documentout.class, doc.getIddocumentout()));
							em.getTransaction().commit();
							doc.setHasPdf(true);
							log4j.debug("Sovrascrittura allegato per il Documento in uscita: " + doc);
						}
					}
					else{
						String file = documentBase + "\\" + doc.getIddocumentout() + doc.getEnte().toString() + "Out" + doc.getCodiceprotocollo() + ".pdf";
						FileOutputStream out = new FileOutputStream(file);
						out.write( scanner.example( doc.getEnte().getNome() , doc.getCodiceprotocollo(), doc.getDataprotocollo() ) );
						out.close();
						
						em.getTransaction().begin();
						em.find(Documentout.class, doc.getIddocumentout()).setFilename(file);
						em.merge(em.find(Documentout.class, doc.getIddocumentout()));

						em.getTransaction().commit();
						doc.setHasPdf(true);
						log4j.debug("Scrittura allegato per il Documento in uscita: " + doc);
					}
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (TwainException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		});
			
		GridBagConstraints gbc_btnPdf = new GridBagConstraints();
		gbc_btnPdf.insets = new Insets(0, 0, 0, 5);
		gbc_btnPdf.gridx = 2;
		gbc_btnPdf.gridy = 0;
		panel_12.add(btnPdf, gbc_btnPdf);
		
		JButton btnViewPdfOut = new JButton("Visualiza PDF");
		btnViewPdfOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Documentout doc = enteGroup.getMembers().get(list.getSelectedIndex()).getDocumentouts().get(tableDocOut.getSelectedRow());
				try {
					Runtime.getRuntime().exec( "c:\\Windows\\System32\\rundll32.exe url.dll,FileProtocolHandler " + doc.getFilename());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnViewPdfOut = new GridBagConstraints();
		gbc_btnViewPdfOut.insets = new Insets(0, 0, 0, 5);
		gbc_btnViewPdfOut.gridx = 3;
		gbc_btnViewPdfOut.gridy = 0;
		panel_12.add(btnViewPdfOut, gbc_btnViewPdfOut);
		
		JPanel panelCerca = new JPanel();
		tabbedPane.addTab("Cerca", null, panelCerca, null);
		panelCerca.setLayout(new BoxLayout(panelCerca, BoxLayout.X_AXIS));
		
		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setResizeWeight(0.5);
		splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panelCerca.add(splitPane_3);
		
		JPanel panel_13 = new JPanel();
		splitPane_3.setRightComponent(panel_13);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_5 = new JScrollPane();
		panel_13.add(scrollPane_5,BorderLayout.CENTER);
		
		tableSearch = new JTable();
		scrollPane_5.setViewportView(tableSearch);
		
		JPanel panel_14 = new JPanel();
		splitPane_3.setLeftComponent(panel_14);
		GridBagLayout gbl_panel_14 = new GridBagLayout();
		gbl_panel_14.columnWidths = new int[]{0, 0, 0};
		gbl_panel_14.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_14.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_14.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_14.setLayout(gbl_panel_14);
		
		JLabel lblNewLabel_1 = new JLabel("Tipo Documento");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel_14.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		final JComboBox comboSearchDocType = new JComboBox(new String[] {"Entrata","Uscita"});
		GridBagConstraints gbc_comboSearchDocType = new GridBagConstraints();
		gbc_comboSearchDocType.insets = new Insets(0, 0, 5, 0);
		gbc_comboSearchDocType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboSearchDocType.gridx = 1;
		gbc_comboSearchDocType.gridy = 0;
		panel_14.add(comboSearchDocType, gbc_comboSearchDocType);
		
		JLabel lblEnte = new JLabel("Ente");
		GridBagConstraints gbc_lblEnte = new GridBagConstraints();
		gbc_lblEnte.anchor = GridBagConstraints.EAST;
		gbc_lblEnte.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnte.gridx = 0;
		gbc_lblEnte.gridy = 1;
		panel_14.add(lblEnte, gbc_lblEnte);
		
		comboSearchEnte = new JComboBox();
		comboSearchEnte.setEditable(true);
		AutoCompleteDecorator.decorate(comboSearchEnte);
		GridBagConstraints gbc_comboSearchEnte = new GridBagConstraints();
		gbc_comboSearchEnte.insets = new Insets(0, 0, 5, 0);
		gbc_comboSearchEnte.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboSearchEnte.gridx = 1;
		gbc_comboSearchEnte.gridy = 1;
		panel_14.add(comboSearchEnte, gbc_comboSearchEnte);
		
		JLabel lblOggetto = new JLabel("Oggetto");
		GridBagConstraints gbc_lblOggetto = new GridBagConstraints();
		gbc_lblOggetto.anchor = GridBagConstraints.EAST;
		gbc_lblOggetto.insets = new Insets(0, 0, 5, 5);
		gbc_lblOggetto.gridx = 0;
		gbc_lblOggetto.gridy = 2;
		panel_14.add(lblOggetto, gbc_lblOggetto);
		
		textFieldSearchOggetto = new JTextField();
		GridBagConstraints gbc_textFieldSearchOggetto = new GridBagConstraints();
		gbc_textFieldSearchOggetto.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSearchOggetto.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSearchOggetto.gridx = 1;
		gbc_textFieldSearchOggetto.gridy = 2;
		panel_14.add(textFieldSearchOggetto, gbc_textFieldSearchOggetto);
		textFieldSearchOggetto.setColumns(10);
		
		JLabel lblDataInizio = new JLabel("Data Inizio");
		GridBagConstraints gbc_lblDataInizio = new GridBagConstraints();
		gbc_lblDataInizio.anchor = GridBagConstraints.EAST;
		gbc_lblDataInizio.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataInizio.gridx = 0;
		gbc_lblDataInizio.gridy = 3;
		panel_14.add(lblDataInizio, gbc_lblDataInizio);
		
		//gets previous month for the time period
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		
		final JDateChooser dateChooserSearchInizio = new JDateChooser(cal.getTime());
		GridBagConstraints gbc_dateChooserSearchInizio = new GridBagConstraints();
		gbc_dateChooserSearchInizio.insets = new Insets(0, 0, 5, 0);
		gbc_dateChooserSearchInizio.fill = GridBagConstraints.BOTH;
		gbc_dateChooserSearchInizio.gridx = 1;
		gbc_dateChooserSearchInizio.gridy = 3;
		panel_14.add(dateChooserSearchInizio, gbc_dateChooserSearchInizio);
		
		JLabel lblDataFine = new JLabel("Data Fine");
		GridBagConstraints gbc_lblDataFine = new GridBagConstraints();
		gbc_lblDataFine.anchor = GridBagConstraints.EAST;
		gbc_lblDataFine.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataFine.gridx = 0;
		gbc_lblDataFine.gridy = 4;
		panel_14.add(lblDataFine, gbc_lblDataFine);
		
		final JDateChooser dateChooserSearchFine = new JDateChooser(new Date());
		GridBagConstraints gbc_dateChooserSearchFine = new GridBagConstraints();
		gbc_dateChooserSearchFine.insets = new Insets(0, 0, 5, 0);
		gbc_dateChooserSearchFine.fill = GridBagConstraints.BOTH;
		gbc_dateChooserSearchFine.gridx = 1;
		gbc_dateChooserSearchFine.gridy = 4;
		panel_14.add(dateChooserSearchFine, gbc_dateChooserSearchFine);
		
		JLabel lblDestinatariomittente = new JLabel("Destinatario/Mittente");
		GridBagConstraints gbc_lblDestinatariomittente = new GridBagConstraints();
		gbc_lblDestinatariomittente.anchor = GridBagConstraints.EAST;
		gbc_lblDestinatariomittente.insets = new Insets(0, 0, 5, 5);
		gbc_lblDestinatariomittente.gridx = 0;
		gbc_lblDestinatariomittente.gridy = 5;
		panel_14.add(lblDestinatariomittente, gbc_lblDestinatariomittente);
		
		textFieldSearchMittente = new JTextField();
		GridBagConstraints gbc_textFieldSearchMittente = new GridBagConstraints();
		gbc_textFieldSearchMittente.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSearchMittente.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSearchMittente.gridx = 1;
		gbc_textFieldSearchMittente.gridy = 5;
		panel_14.add(textFieldSearchMittente, gbc_textFieldSearchMittente);
		textFieldSearchMittente.setColumns(10);
		
		JLabel lblModalit_1 = new JLabel("Modalit\u00E0");
		GridBagConstraints gbc_lblModalit_1 = new GridBagConstraints();
		gbc_lblModalit_1.anchor = GridBagConstraints.EAST;
		gbc_lblModalit_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblModalit_1.gridx = 0;
		gbc_lblModalit_1.gridy = 6;
		panel_14.add(lblModalit_1, gbc_lblModalit_1);
		
		comboSearchModalita = new JComboBox(modalita);
		comboSearchModalita.setEditable(true);
		
		GridBagConstraints gbc_comboSearchModalita = new GridBagConstraints();
		gbc_comboSearchModalita.insets = new Insets(0, 0, 5, 0);
		gbc_comboSearchModalita.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboSearchModalita.gridx = 1;
		gbc_comboSearchModalita.gridy = 6;
		panel_14.add(comboSearchModalita, gbc_comboSearchModalita);
		
		JLabel lblTipo_1 = new JLabel("Tipo");
		GridBagConstraints gbc_lblTipo_1 = new GridBagConstraints();
		gbc_lblTipo_1.anchor = GridBagConstraints.EAST;
		gbc_lblTipo_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo_1.gridx = 0;
		gbc_lblTipo_1.gridy = 7;
		panel_14.add(lblTipo_1, gbc_lblTipo_1);
		
		comboSearchTipo = new JComboBox(tipo);
		comboSearchTipo.setEditable(true);
		GridBagConstraints gbc_comboSearchTipo = new GridBagConstraints();
		gbc_comboSearchTipo.insets = new Insets(0, 0, 5, 0);
		gbc_comboSearchTipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboSearchTipo.gridx = 1;
		gbc_comboSearchTipo.gridy = 7;
		panel_14.add(comboSearchTipo, gbc_comboSearchTipo);
		
		JLabel lblNote_1 = new JLabel("Note");
		GridBagConstraints gbc_lblNote_1 = new GridBagConstraints();
		gbc_lblNote_1.anchor = GridBagConstraints.EAST;
		gbc_lblNote_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNote_1.gridx = 0;
		gbc_lblNote_1.gridy = 8;
		panel_14.add(lblNote_1, gbc_lblNote_1);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_4 = new GridBagConstraints();
		gbc_scrollPane_4.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_4.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_4.gridx = 1;
		gbc_scrollPane_4.gridy = 8;
		panel_14.add(scrollPane_4, gbc_scrollPane_4);
		
		textAreaSearchNote = new JTextArea();
		scrollPane_4.setViewportView(textAreaSearchNote);
		
		JButton btnExcel = new JButton("Excel");
		GridBagConstraints gbc_btnExcel = new GridBagConstraints();
		gbc_btnExcel.insets = new Insets(0, 0, 0, 5);
		gbc_btnExcel.gridx = 0;
		gbc_btnExcel.gridy = 9;
		panel_14.add(btnExcel, gbc_btnExcel);
		
		JButton btnCerca = new JButton("Cerca");
		btnCerca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(comboSearchDocType.getSelectedItem().toString() == "Entrata"){
					TypedQuery<Documentin> qry = em.createQuery("select d from Documentin d " 
							+ "where " 
								+ "d.ente.nome like :ente "
								+ "and d.oggetto like :oggetto "
								+ "and d.dataprotocollo between :dataInizio and :dataFine "
								+ "and d.mittente like :mittente "
								+ "and d.note like :note "
								+ "and d.modalita like :modalita "
								+ "and d.tipo like :tipo "
							,Documentin.class);
					
					qry.setParameter("ente", "%" + comboSearchEnte.getSelectedItem().toString() + "%");
					qry.setParameter("oggetto", "%" + textFieldSearchOggetto.getText() + "%");
					if(dateChooserSearchInizio.getDate() != null)
						qry.setParameter("dataInizio",dateChooserSearchInizio.getDate());
					else{
						GregorianCalendar cal = new GregorianCalendar(1970, 1, 1);
						qry.setParameter("dataInizio",cal.getTime());
						//System.out.println("cal inizio:" + cal.getTime());
						log4j.debug("cal inizio:" + cal.getTime());
					}
					if(dateChooserSearchFine.getDate() != null)
						qry.setParameter("dataFine",dateChooserSearchFine.getDate());
					else{
						GregorianCalendar cal = new GregorianCalendar(2100, 1, 1);
						qry.setParameter("dataFine",cal.getTime());
						//System.out.println("cal fine:" + cal.getTime());
						log4j.debug("cal fine:" + cal.getTime());
					}
					qry.setParameter("mittente", "%" + textFieldSearchMittente.getText() + "%" );
					qry.setParameter("note", "%" + textAreaSearchNote.getText()+ "%");
					qry.setParameter("modalita", "%" + comboSearchModalita.getSelectedItem().toString() + "%");
					qry.setParameter("tipo", "%" + comboSearchTipo.getSelectedItem().toString() + "%");
					//System.out.println(qry.toString());
					//System.out.println(qry.getResultList());
					log4j.debug(qry.toString());
					log4j.debug(qry.getResultList());
					searchListDocIn = qry.getResultList();
					tableSearch.setModel(new DocumentinTableModel(qry.getResultList()));
				}
				else{
					TypedQuery<Documentout> qry = em.createQuery("select d from Documentout d "
							+ "where " 
								+ "d.ente.nome like :ente "
								+ "and d.oggetto like :oggetto "
								+ "and d.dataprotocollo between :dataInizio and :dataFine "
								+ "and d.destinatario like :destinatario "
								+ "and d.note like :note "
								+ "and d.modalita like :modalita "
								+ "and d.tipo like :tipo "
							,Documentout.class);
					
					qry.setParameter("ente", "%" + comboSearchEnte.getSelectedItem().toString() + "%");
					qry.setParameter("oggetto", "%" + textFieldSearchOggetto.getText() + "%");
					if(dateChooserSearchInizio.getDate() != null)
						qry.setParameter("dataInizio",dateChooserSearchInizio.getDate());
					else{
						GregorianCalendar cal = new GregorianCalendar(1970, 1, 1);
						qry.setParameter("dataInizio",cal.getTime());
						//System.out.println("cal inizio:" + cal.getTime());
						log4j.debug("cal inizio:" + cal.getTime());
					}
					if(dateChooserSearchFine.getDate() != null)
						qry.setParameter("dataFine",dateChooserSearchFine.getDate());
					else{
						GregorianCalendar cal = new GregorianCalendar(2100, 1, 1);
						qry.setParameter("dataFine",cal.getTime());
						//System.out.println("cal fine:" + cal.getTime());
						log4j.debug("cal fine:" + cal.getTime());
					}
					qry.setParameter("destinatario", "%" + textFieldSearchMittente.getText() + "%" );
					qry.setParameter("note", "%" + textAreaSearchNote.getText()+ "%");
					qry.setParameter("modalita", "%" + comboSearchModalita.getSelectedItem().toString() + "%");
					qry.setParameter("tipo", "%" + comboSearchTipo.getSelectedItem().toString() + "%");
					//System.out.println(qry.getResultList());
					log4j.debug(qry.getResultList());
					searchListDocOut = qry.getResultList();
					tableSearch.setModel(new DocumentoutTableModel(qry.getResultList()));
				}				

				/*
			 	CriteriaBuilder cb = em.getCriteriaBuilder();
			 	CriteriaQuery<Documentin> cq = cb.createQuery(Documentin.class);
				 
				Root<Documentin> root = cq.from(Documentin.class);
				cq.select(root);
				
				Predicate condition = cb.gt(root.get(Documentin_.noprotocollo), 0);
				cq.where(condition);
				
				TypedQuery<Documentin> q = em.createQuery(cq);
				List<Documentin> result = q.getResultList(); 
				*/
			}
		});
		GridBagConstraints gbc_btnCerca = new GridBagConstraints();
		gbc_btnCerca.anchor = GridBagConstraints.WEST;
		gbc_btnCerca.gridx = 1;
		gbc_btnCerca.gridy = 9;
		panel_14.add(btnCerca, gbc_btnCerca);
		btnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jtableToExcel(tableSearch, new File("excel.csv"));
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		frmGestioneProtocolli.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Record");
		mnNewMenu.setMnemonic('R');
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Salva");
		mnNewMenu.add(mntmNewMenuItem);
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					em.getTransaction().begin();
					/*
					for(Ente lst : enteGroup.getMembers()){
						for(Documentin doc1 : lst.getDocumentins()){
							em.merge(doc1);
							}
						for(Documentout doc2 : lst.getDocumentouts())
							em.merge(doc2);
						}
					*/
					for(Ente lst : enteGroup.getMembers())
						em.merge(lst);
					
					em.getTransaction().commit();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				//System.out.println("Menu Salva");
				log4j.debug("Menu Salva");
			}
		});
		
		JMenu mnControlliScanner = new JMenu("Settagi");
		mnControlliScanner.setMnemonic('S');
		menuBar.add(mnControlliScanner);
		
		JMenuItem mntmScanner = new JMenuItem("Scanner");
		mntmScanner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScannerControl ctrl = new ScannerControl(scanner);
				ctrl.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
				ctrl.setVisible(true);
			}
		});

		mnControlliScanner.add(mntmScanner);
		initDataBindings();
	}
	
	private void initEnteList(){
		enteGroup = new EnteGroup();
		try {
			enteGroup.setMembers(em.createQuery("select e from Ente e",Ente.class).getResultList());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		searchListDocIn = new ArrayList<Documentin>();
		searchListDocOut = new ArrayList<Documentout>();
	}
	
	private void initPersistence(){
		// Generate a map containing your properties
		HashMap<String, String> connnectionProperties = new HashMap<String, String>();
		DbConn conn = new DbConn();
		
		conn.deSerialize("c:\\documentale\\DbConn.xml");
		//System.out.print(conn);
		log4j.debug(conn);
		 
		// Set your properties as you like:
		connnectionProperties.put(PersistenceUnitProperties.JDBC_URL, conn.getUrl());
		connnectionProperties.put(PersistenceUnitProperties.JDBC_USER, conn.getUser());
		connnectionProperties.put(PersistenceUnitProperties.JDBC_PASSWORD, conn.getPassword());
		connnectionProperties.put(PersistenceUnitProperties.JDBC_DRIVER, conn.getDriver());
		//Pass the properties map to your EntityManager Factory
		EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("Documentale", connnectionProperties);
		em = emf.createEntityManager();
		
		documentBase = conn.getDocumentBase();
	}
	
	private void initSequence(){
		sequence = new SequenceGenerator();
	}
	
	private void initScanner(){
		scanner = new Scanner();
		scanner.deSerialize("c:\\Documentale\\scanner.xml");
		//System.out.println("after initscanner(): "+scanner);
		log4j.debug("after initscanner(): "+scanner);
	}
	
	public void initMittenteList(){
		try {
			em.getTransaction().begin();
			TypedQuery<String> query = em.createQuery("select distinct d.mittente from Documentin d",String.class);
			List<String> result = query.getResultList();
			em.getTransaction().commit();
			
			mittenteList = result;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initDestinatarioList(){
		try {
			em.getTransaction().begin();
			TypedQuery<String> query = em.createQuery("select distinct d.destinatario from Documentout d",String.class);
			List<String> result = query.getResultList();
			em.getTransaction().commit();
			
			destinatarioList = result;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("serial")
	public class AddEnte extends JDialog {

		private final JPanel contentPanel = new JPanel();
		private JTextField textField;

		/**
		 * Create the dialog.
		 */
		public AddEnte() {
			setTitle("Inserisci Ente");
			setResizable(false);
			setModalityType(ModalityType.APPLICATION_MODAL);
			setBounds(100, 100, 450, 124);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			GridBagLayout gbl_contentPanel = new GridBagLayout();
			gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
			gbl_contentPanel.rowHeights = new int[]{0, 0};
			gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_contentPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			contentPanel.setLayout(gbl_contentPanel);
			{
				JLabel lblNewLabel = new JLabel("Nome Ente: ");
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
				gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel.gridx = 0;
				gbc_lblNewLabel.gridy = 0;
				contentPanel.add(lblNewLabel, gbc_lblNewLabel);
			}
			{
				textField = new JTextField();
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.gridx = 1;
				gbc_textField.gridy = 0;
				contentPanel.add(textField, gbc_textField);
				textField.setColumns(10);
			}
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("Inserisci");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(textField.getText().isEmpty()){
								JOptionPane.showMessageDialog(null,"E neccessario inserire il nome dell'ente", "Errore", JOptionPane.ERROR_MESSAGE);
								return;
							}
							try {
								ente = new Ente(textField.getText());
								ente.setIdente(sequence.enteID(em));
								
								em.getTransaction().begin();
								em.persist(ente);
								em.getTransaction().commit();
								enteGroup.addEnte(ente);
							}
							catch (Exception e2) {
								e2.printStackTrace();
							}
							setVisible(false);
							initDataBindings();
						}
					});
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
				{
					JButton cancelButton = new JButton("Anulla");
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							setVisible(false);
						}
					});
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
				}
			}
		}
	}
	
	public void jtableToExcel(JTable table, File file){
		// to implement if no search was performed
		String dateFormat = "dd-MMM-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    try{
	        TableModel model = table.getModel();
	        FileWriter excel = new FileWriter(file);
	        for(int i = 0; i < model.getColumnCount(); i++){
	            excel.write(model.getColumnName(i) + ";");
	        }
	        excel.write("\n");
	        for(int i=0; i< model.getRowCount(); i++) {
	            for(int j=0; j < model.getColumnCount(); j++) {
	            	if(j != 2){
	            		excel.write(model.getValueAt(i,j)+";");
	            	}
	            	else
	            		excel.write( sdf.format(((Date)model.getValueAt(i, j))) + ";");
	            }
	            excel.write("\n");
	        }
	        excel.close();
	        Runtime.getRuntime().exec( "c:\\Windows\\System32\\rundll32.exe url.dll,FileProtocolHandler " + file);
	    }
	    catch(IOException e){
	    	//System.out.println(e);
	    	log4j.debug(e);
	    }
	}
	protected void initDataBindings() {
		BeanProperty<EnteGroup, List<Ente>> enteGroupBeanProperty = BeanProperty.create("members");
		JListBinding<Ente, EnteGroup, JList> jListBinding = SwingBindings.createJListBinding(UpdateStrategy.READ, enteGroup, enteGroupBeanProperty, list);
		//
		ELProperty<Ente, Object> enteEvalutionProperty = ELProperty.create("${nome}(${documentCountIn},${documentCountOut})");
		jListBinding.setDetailBinding(enteEvalutionProperty);
		//
		jListBinding.bind();
		//
		BeanProperty<JList, List<Documentin>> jListBeanProperty = BeanProperty.create("selectedElement.documentins");
		JTableBinding<Documentin, JList, JTable> jTableBinding = SwingBindings.createJTableBinding(UpdateStrategy.READ, list, jListBeanProperty, tableDocIn);
		//
		BeanProperty<Documentin, String> documentinBeanProperty = BeanProperty.create("oggetto");
		jTableBinding.addColumnBinding(documentinBeanProperty).setColumnName("Oggetto");
		//
		BeanProperty<Documentin, String> documentinBeanProperty_1 = BeanProperty.create("codiceprotocollo");
		jTableBinding.addColumnBinding(documentinBeanProperty_1).setColumnName("Protocollo");
		//
		BeanProperty<Documentin, Date> documentinBeanProperty_2 = BeanProperty.create("dataprotocollo");
		jTableBinding.addColumnBinding(documentinBeanProperty_2).setColumnName("Data").setColumnClass(Date.class);
		//
		BeanProperty<Documentin, String> documentinBeanProperty_3 = BeanProperty.create("mittente");
		jTableBinding.addColumnBinding(documentinBeanProperty_3).setColumnName("Mittente");
		//
		BeanProperty<Documentin, String> documentinBeanProperty_4 = BeanProperty.create("note");
		jTableBinding.addColumnBinding(documentinBeanProperty_4).setColumnName("Note").setColumnClass(String.class);
		//
		BeanProperty<Documentin, Boolean> documentinBeanProperty_5 = BeanProperty.create("hasPdf");
		jTableBinding.addColumnBinding(documentinBeanProperty_5).setColumnName("PDF").setColumnClass(Boolean.class);
		//
		BeanProperty<Documentin, String> documentinBeanProperty_6 = BeanProperty.create("modalita");
		jTableBinding.addColumnBinding(documentinBeanProperty_6).setColumnName("Modalita");
		//
		BeanProperty<Documentin, String> documentinBeanProperty_7 = BeanProperty.create("tipo");
		jTableBinding.addColumnBinding(documentinBeanProperty_7).setColumnName("Tipo");
		//
		jTableBinding.setEditable(false);
		jTableBinding.bind();
		//
		BeanProperty<JList, List<Documentout>> jListBeanProperty_1 = BeanProperty.create("selectedElement.documentouts");
		JTableBinding<Documentout, JList, JTable> jTableBinding_1 = SwingBindings.createJTableBinding(UpdateStrategy.READ, list, jListBeanProperty_1, tableDocOut);
		//
		BeanProperty<Documentout, String> documentoutBeanProperty = BeanProperty.create("oggetto");
		jTableBinding_1.addColumnBinding(documentoutBeanProperty).setColumnName("Oggetto");
		//
		BeanProperty<Documentout, String> documentoutBeanProperty_1 = BeanProperty.create("codiceprotocollo");
		jTableBinding_1.addColumnBinding(documentoutBeanProperty_1).setColumnName("Protocollo");
		//
		BeanProperty<Documentout, Date> documentoutBeanProperty_2 = BeanProperty.create("dataprotocollo");
		jTableBinding_1.addColumnBinding(documentoutBeanProperty_2).setColumnName("Data").setColumnClass(Date.class);
		//
		BeanProperty<Documentout, String> documentoutBeanProperty_3 = BeanProperty.create("destinatario");
		jTableBinding_1.addColumnBinding(documentoutBeanProperty_3).setColumnName("Destinatario");
		//
		BeanProperty<Documentout, String> documentoutBeanProperty_4 = BeanProperty.create("note");
		jTableBinding_1.addColumnBinding(documentoutBeanProperty_4).setColumnName("Note");
		//
		BeanProperty<Documentout, Boolean> documentoutBeanProperty_5 = BeanProperty.create("hasPdf");
		jTableBinding_1.addColumnBinding(documentoutBeanProperty_5).setColumnName("PDF").setColumnClass(Boolean.class);
		//
		BeanProperty<Documentout, String> documentoutBeanProperty_6 = BeanProperty.create("modalita");
		jTableBinding_1.addColumnBinding(documentoutBeanProperty_6).setColumnName("Modalita");
		//
		BeanProperty<Documentout, String> documentoutBeanProperty_7 = BeanProperty.create("tipo");
		jTableBinding_1.addColumnBinding(documentoutBeanProperty_7).setColumnName("Tipo");
		//
		jTableBinding_1.setEditable(false);
		jTableBinding_1.bind();
		//
		BeanProperty<JTable, Date> jTableBeanProperty = BeanProperty.create("selectedElement.dataprotocollo");
		BeanProperty<JDateChooser, Date> jDateChooserBeanProperty = BeanProperty.create("date");
		AutoBinding<JTable, Date, JDateChooser, Date> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocIn, jTableBeanProperty, dateChooser, jDateChooserBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty_1 = BeanProperty.create("selectedElement.oggetto");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<JTable, String, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocIn, jTableBeanProperty_1, textFieldOggettoIn, jTextFieldBeanProperty);
		autoBinding_1.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty_3 = BeanProperty.create("selectedElement.codiceprotocollo");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
		AutoBinding<JTable, String, JTextField, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocIn, jTableBeanProperty_3, textFieldProtocolloIn, jTextFieldBeanProperty_2);
		autoBinding_3.bind();
		//
		BeanProperty<JTextField, String> jTextFieldBeanProperty_3 = BeanProperty.create("text");
		AutoBinding<JTable, String, JTextField, String> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocOut, jTableBeanProperty_1, textFieldOggettoOut, jTextFieldBeanProperty_3);
		autoBinding_4.bind();
		//
		BeanProperty<JTextField, String> jTextFieldBeanProperty_4 = BeanProperty.create("text");
		AutoBinding<JTable, String, JTextField, String> autoBinding_5 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocOut, jTableBeanProperty_3, textFieldProtocolloOut, jTextFieldBeanProperty_4);
		autoBinding_5.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty_5 = BeanProperty.create("selectedElement.note");
		BeanProperty<JTextArea, String> jTextAreaBeanProperty = BeanProperty.create("text");
		AutoBinding<JTable, String, JTextArea, String> autoBinding_7 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocOut, jTableBeanProperty_5, textAreaNoteOut, jTextAreaBeanProperty);
		autoBinding_7.bind();
		//
		AutoBinding<JTable, Date, JDateChooser, Date> autoBinding_8 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocOut, jTableBeanProperty, dateChooser_1, jDateChooserBeanProperty);
		autoBinding_8.bind();
		//
		BeanProperty<JTextArea, String> jTextAreaBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<JTable, String, JTextArea, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocIn, jTableBeanProperty_5, textAreaNoteIn, jTextAreaBeanProperty_1);
		autoBinding_2.bind();
		//
		JComboBoxBinding<Ente, EnteGroup, JComboBox> jComboBinding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ, enteGroup, enteGroupBeanProperty, comboSearchEnte);
		jComboBinding.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty_2 = BeanProperty.create("selectedElement.modalita");
		BeanProperty<JComboBox, Object> jComboBoxBeanProperty = BeanProperty.create("selectedItem");
		AutoBinding<JTable, String, JComboBox, Object> autoBinding_6 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocIn, jTableBeanProperty_2, comboBoxModalitaIn, jComboBoxBeanProperty);
		autoBinding_6.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty_4 = BeanProperty.create("selectedElement.tipo");
		AutoBinding<JTable, String, JComboBox, Object> autoBinding_9 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocIn, jTableBeanProperty_4, comboBoxTipoIn, jComboBoxBeanProperty);
		autoBinding_9.bind();
		//
		AutoBinding<JTable, String, JComboBox, Object> autoBinding_10 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocOut, jTableBeanProperty_2, comboBoxModalitaOut, jComboBoxBeanProperty);
		autoBinding_10.bind();
		//
		AutoBinding<JTable, String, JComboBox, Object> autoBinding_11 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocOut, jTableBeanProperty_4, comboBoxTipoOut, jComboBoxBeanProperty);
		autoBinding_11.bind();
		//
		JComboBoxBinding<String, List<String>, JComboBox> jComboBinding_1 = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ, mittenteList, comboBoxMittente);
		jComboBinding_1.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty_6 = BeanProperty.create("selectedElement.mittente");
		AutoBinding<JTable, String, JComboBox, Object> autoBinding_12 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocIn, jTableBeanProperty_6, comboBoxMittente, jComboBoxBeanProperty);
		autoBinding_12.bind();
		//
		JComboBoxBinding<String, List<String>, JComboBox> jComboBinding_2 = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ, destinatarioList, comboBoxDestinatario);
		jComboBinding_2.bind();
		//
		BeanProperty<JTable, String> jTableBeanProperty_7 = BeanProperty.create("selectedElement.destinatario");
		AutoBinding<JTable, String, JComboBox, Object> autoBinding_13 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, tableDocOut, jTableBeanProperty_7, comboBoxDestinatario, jComboBoxBeanProperty);
		autoBinding_13.bind();
	}
}