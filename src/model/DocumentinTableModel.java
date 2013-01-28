package model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class DocumentinTableModel extends AbstractTableModel {
	
	public static void main(String args[]){
		JFrame frame = new JFrame();
		frame.setVisible(true);
		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTable jTable1 = new javax.swing.JTable();
		
		List<Documentin> list= new ArrayList<Documentin>();
		
		
		list.add(new Documentin(new Date(), "note", null, "test", "dfsfsfs", "oggetto", true));
		list.add(new Documentin(new Date(), "note", null, "test", "dfsfsfs", "oggetto", true));
		list.add(new Documentin(new Date(), "note", null, "test", "dfsfsfs", "oggetto", true));
		list.add(new Documentin(new Date(), "note", null, "test", "dfsfsfs", "oggetto", true));
		list.add(new Documentin(new Date(), "note", null, "test", "dfsfsfs", "oggetto", true));

	    jTable1.setModel(new DocumentinTableModel(list));
	    jScrollPane1.setViewportView(jTable1);
	    frame.add(jScrollPane1); 

	}

    
	private List<Documentin> docs = new ArrayList<Documentin>();
    private String[] columnNames = { "Oggetto", "Protocollo", "Data", "Mittente", "Note", "PDF","Modatità","Tipo"};

    public DocumentinTableModel(List<Documentin> list){
         this.docs = list;
    }

    public String getColumnName(int columnIndex){
         return columnNames[columnIndex];
    }

    public int getRowCount() {
        return docs.size();
    }

    public int getColumnCount() {
        return 8; 
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Documentin d = docs.get(rowIndex);
        switch (columnIndex) {
            case 0: 
                return d.getOggetto();
            case 1:
                return d.getCodiceprotocollo();
            case 2:
                return d.getDataprotocollo();
            case 3:
                return d.getMittente();
            case 4:
                return d.getNote();
            case 5:
                return d.isHasPdf(); 
            case 6:
                return d.getModalita();
            case 7:
                return d.getTipo();
           }
           return null;
   }

   public Class<?> getColumnClass(int columnIndex){
          switch (columnIndex){
             case 0:
            	 return String.class;
             case 1:
            	 return String.class;
             case 2:
            	 return Date.class;
             case 3:
            	 return String.class;
             case 4:
            	 return String.class;
             case 5:
            	 return String.class;
             case 6:
            	 return String.class;
             case 7:
            	 return String.class;
             }
             return null;
      }
 }
