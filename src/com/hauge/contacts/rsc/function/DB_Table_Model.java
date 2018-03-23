/*
==================================================
    PRELIMINARY DATA: START
==================================================
*/

    // PACKAGE:
        package com.hauge.contacts.rsc.function;


    // LIBRARIES:

        // Official Libraries:
        import javax.swing.*;
        import javax.swing.table.AbstractTableModel;

        // My Libraries:
        import com.hauge.contacts.rsc.db.DB;
        import static com.hauge.contacts.rsc.var.Variables.*;

/*
==================================================
    PRELIMINARY DATA: END
==================================================
*/


public class DB_Table_Model extends AbstractTableModel {


    // OBJECT ELEMENTS:

        private String[] columnNames = DB_TABLE_COLUMNS;
        private Object[][] data;




    // OBJECT METHODS:

        // Populate object with database:
        public void populate( DB db ) {

            // Define data object in accordance with database:
            data = new Object[db.getDB_table_row_count()][db.getDB_table_col_count()];

            // Transfer database data to a disposable array:
            String[][] temp = db.getDB_data_all_array();

            // Cloning data onto data object:
            for ( int row = 0; row < db.getDB_table_row_count(); row++ ) {
                for ( int col = 0; col < db.getDB_table_col_count(); col++ ) {
                    data[row][col] = temp[row][col];
                }
            }

        }


        // Default column counter:
        public int getColumnCount() {
            return columnNames.length;
        }


        // Default row counter:
        public int getRowCount() {
            return data.length;
        }


        // Default column name reader:
        public String getColumnName(int col) {
            return columnNames[col];
        }


        // Default value reader:
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }


        // Default column class getter:
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }


        // Default cell configuration:
        public boolean isCellEditable(int row, int col) {
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }


        // Default data injector:
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
            fireTableDataChanged();
        }

}