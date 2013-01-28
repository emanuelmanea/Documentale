package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import app.Scanner;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

@SuppressWarnings("serial")
public class ScannerControl extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private Scanner scanner;
	
	private JCheckBox chckbxAdf;
	private JCheckBox chckbxDuplex;
	private JCheckBox chckbxGrayscale;
	private JCheckBox chckbxBwdpi;

	public ScannerControl(Scanner scan) {
		this.scanner = scan;
		setTitle("Settaggi Scanner");
		setBounds(100, 100, 213, 210);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			chckbxAdf = new JCheckBox("Adf");
			GridBagConstraints gbc_chckbxAdf = new GridBagConstraints();
			gbc_chckbxAdf.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxAdf.gridx = 0;
			gbc_chckbxAdf.gridy = 0;
			contentPanel.add(chckbxAdf, gbc_chckbxAdf);
		}
		{
			chckbxDuplex = new JCheckBox("Duplex");
			GridBagConstraints gbc_chckbxDuplex = new GridBagConstraints();
			gbc_chckbxDuplex.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxDuplex.gridx = 0;
			gbc_chckbxDuplex.gridy = 1;
			contentPanel.add(chckbxDuplex, gbc_chckbxDuplex);
		}
		{
			chckbxGrayscale = new JCheckBox("Grayscale");
			chckbxGrayscale.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if(chckbxGrayscale.isSelected()){
						chckbxBwdpi.setSelected(false);
					}
				}
			});
			GridBagConstraints gbc_chckbxGrayscale = new GridBagConstraints();
			gbc_chckbxGrayscale.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxGrayscale.gridx = 0;
			gbc_chckbxGrayscale.gridy = 2;
			contentPanel.add(chckbxGrayscale, gbc_chckbxGrayscale);
		}
		{
			chckbxBwdpi = new JCheckBox("B&W 150dpi");
			chckbxBwdpi.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(chckbxBwdpi.isSelected()){
						chckbxGrayscale.setSelected(false);
					}
				}
			});
			GridBagConstraints gbc_chckbxBwdpi = new GridBagConstraints();
			gbc_chckbxBwdpi.insets = new Insets(0, 0, 0, 5);
			gbc_chckbxBwdpi.gridx = 0;
			gbc_chckbxBwdpi.gridy = 3;
			contentPanel.add(chckbxBwdpi, gbc_chckbxBwdpi);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Salva");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						scanner.serialize("c:\\documentale\\scanner.xml");
						setVisible(false);
						//System.out.print("on save:"+scanner);
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
						//System.out.print("anulla:"+scanner);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		initDataBindings();
		
	}
	protected void initDataBindings() {
		BeanProperty<Scanner, Boolean> scannerBeanProperty = BeanProperty.create("adf");
		BeanProperty<JCheckBox, Boolean> jCheckBoxBeanProperty = BeanProperty.create("selected");
		AutoBinding<Scanner, Boolean, JCheckBox, Boolean> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, scanner, scannerBeanProperty, chckbxAdf, jCheckBoxBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<Scanner, Boolean> scannerBeanProperty_1 = BeanProperty.create("duplex");
		AutoBinding<Scanner, Boolean, JCheckBox, Boolean> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, scanner, scannerBeanProperty_1, chckbxDuplex, jCheckBoxBeanProperty);
		autoBinding_1.bind();
		//
		BeanProperty<Scanner, Boolean> scannerBeanProperty_2 = BeanProperty.create("grayscale");
		AutoBinding<Scanner, Boolean, JCheckBox, Boolean> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, scanner, scannerBeanProperty_2, chckbxGrayscale, jCheckBoxBeanProperty);
		autoBinding_2.bind();
		//
		BeanProperty<Scanner, Boolean> scannerBeanProperty_3 = BeanProperty.create("blackAndWhite");
		AutoBinding<Scanner, Boolean, JCheckBox, Boolean> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, scanner, scannerBeanProperty_3, chckbxBwdpi, jCheckBoxBeanProperty);
		autoBinding_3.bind();
	}
}