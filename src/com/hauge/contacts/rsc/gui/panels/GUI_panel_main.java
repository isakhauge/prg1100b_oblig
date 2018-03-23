/*
==================================================
    PRELIMINARY DATA: START
==================================================
*/

    // PACKAGE:
    package com.hauge.contacts.rsc.gui.panels;

    // LIBRARIES:

        // Official Libraries:
        import javax.swing.*;
        import javax.swing.plaf.metal.MetalIconFactory;
        import javax.swing.event.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.io.File;

        // My Libraries:
        import static com.hauge.contacts.rsc.var.Variables.*;
        import com.hauge.contacts.rsc.db.DB;
        import com.hauge.contacts.rsc.function.DB_Table_Model;

/*
==================================================
    PRELIMINARY DATA: END
==================================================
*/


public class GUI_panel_main extends JPanel implements ActionListener {


    // OBJECT ELEMENTS:

        // Initialize database model:
        DB the_db = new DB(DB_NAME);

        // Initialize custom table model:
        DB_Table_Model the_model = new DB_Table_Model();

        // Declare variable for selected number:
        String person_selected;

        // Initialize labels:
        JLabel label_title = new JLabel("Your contacts");
        JLabel label_phone = new JLabel("Phone number *");
        JLabel label_name_first = new JLabel("First name");
        JLabel label_name_last = new JLabel("Last name");
        JLabel label_address = new JLabel("Address");

        // Initialize input text fields:
        final int COL_WIDTH = 11;
        JTextField input_phone =        new JTextField(COL_WIDTH);
        JTextField input_name_first =   new JTextField(COL_WIDTH);
        JTextField input_name_last =    new JTextField(COL_WIDTH);
        JTextField input_address =      new JTextField(COL_WIDTH);

        // Initialize action buttons:
        JButton btn_insert = new JButton("Insert person");
        JButton btn_delete = new JButton("Delete person", new MetalIconFactory.PaletteCloseIcon());
        JButton btn_format = new JButton("Format", new MetalIconFactory.PaletteCloseIcon());
        JButton btn_import = new JButton("Import from CSV", new MetalIconFactory().getTreeFolderIcon());
        JButton btn_export = new JButton("Backup to CSV", new MetalIconFactory().getTreeFloppyDriveIcon());

        // Declare the table:
        JTable the_table;

        // Declare the scroll pane:
        JScrollPane the_scroll_pane;

        // Declare the file chooser:
        JFileChooser file_chooser = new JFileChooser("src/com/hauge/contacts/rsc/db");

        // Declare the layout:
        GridBagLayout gbl;
        GridBagConstraints gbc;




    // OBJECT CONSTRUCTOR:
    public GUI_panel_main() {

        // PRELIMINARY STAGE:

            // Initialize panel dimensions:
            Dimension dimension = getPreferredSize();
            dimension.width = WIN_WIDTH;
            dimension.height = WIN_HEIGHT;
            setPreferredSize(dimension);


        // CREATE AND INITIALIZE ELEMENTS:

            // Initialize fonts:
            Font h1 = new Font("Arial", Font.PLAIN,18);
            Font small = new Font("Arial", Font.PLAIN, 10);
            Font action = new Font("Arial", Font.PLAIN, 14);

            // Add fonts to labels:
            this.label_title.setFont(h1);
            this.label_phone.setFont(small);
            this.label_name_first.setFont(small);
            this.label_name_last.setFont(small);
            this.label_address.setFont(small);

            // Add fonts to buttons:
            this.btn_insert.setFont(action);

            // Add tooltips to buttons:
            this.btn_delete.setToolTipText("Select a person from the table first.");

            // Add action listeners:
            this.btn_import.addActionListener(this);
            this.btn_export.addActionListener(this);
            this.btn_insert.addActionListener(this);
            this.btn_delete.addActionListener(this);
            this.btn_format.addActionListener(this);

            // Initializing and populating JTable:
            this.the_model.populate(this.the_db);
            this.the_table = new JTable(the_model) {

                public boolean isCellEditable(int data, int columns){ return false; }

            };
            this.the_table.setAutoCreateRowSorter(true);
            this.the_table.setGridColor(Color.decode("#EEEEEE"));
            this.the_table.getModel().addTableModelListener(this::tableChanged);
            this.the_table.getSelectionModel().addListSelectionListener(this::selectionPerformed);

            // Initializing the JScrollPane:
            this.the_scroll_pane = new JScrollPane(this.the_table);
            this.the_scroll_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            this.the_scroll_pane.setWheelScrollingEnabled(true);


        // SET THE PANEL LAYOUT:

            // Initializing the layout:
            gbl = new GridBagLayout();
            gbc = new GridBagConstraints();

            // Activating the layout:
            setLayout( gbl );


        // ADDING DEFINED ELEMENTS TO THE PANEL:

            // First row ( title label ):

                // Settings:
                gbc.anchor      = gbc.LINE_START;
                gbc.fill        = gbc.BOTH;
                gbc.weightx     = 0.0;
                gbc.weighty     = 2;
                gbc.gridwidth   = 4;
                gbc.gridheight  = 1;

                // Add elements:
                gbc.gridx = 0;
                gbc.gridy = 0;
                add(this.label_title, gbc);


            // First row ( labels ):

                // Settings:
                gbc.anchor      = gbc.LINE_START;
                gbc.fill        = gbc.BOTH;
                gbc.weightx     = 0.0;
                gbc.weighty     = 0;
                gbc.gridwidth   = 1;
                gbc.gridheight  = 1;

                // Add elements:
                gbc.gridx = 0;
                gbc.gridy += 1;
                add(this.label_phone, gbc);
                gbc.gridx += 1;
                add(this.label_name_first, gbc);
                gbc.gridx += 1;
                add(this.label_name_last, gbc);
                gbc.gridx += 1;
                add(this.label_address, gbc);


            // Second row ( input fields ) :

                // Settings:
                gbc.anchor      = gbc.LINE_START;
                gbc.fill        = gbc.BOTH;
                gbc.weightx     = 0.0;
                gbc.weighty     = 0;
                gbc.gridwidth   = 1;
                gbc.gridheight  = 1;

                // Add elements:
                gbc.gridx = 0;
                gbc.gridy += 1;
                add(this.input_phone, gbc);
                gbc.gridx += 1;
                add(this.input_name_first, gbc);
                gbc.gridx += 1;
                add(this.input_name_last, gbc);
                gbc.gridx += 1;
                add(this.input_address, gbc);


            // Third row ( insert button ):

                // Settings:
                gbc.anchor      = gbc.LINE_START;
                gbc.fill        = gbc.BOTH;
                gbc.weightx     = 0.0;
                gbc.weighty     = 1.5;
                gbc.gridwidth   = 4;
                gbc.gridheight  = 1;

                // Add elements:
                gbc.gridx = 0;
                gbc.gridy += 1;
                add(this.btn_insert, gbc);


            // Fourth row ( scroll pane and table ):

                // Settings:
                gbc.anchor      = gbc.LINE_START;
                gbc.fill        = gbc.HORIZONTAL;
                gbc.weightx     = 0.0;
                gbc.weighty     = 2;
                gbc.gridwidth   = 4;
                gbc.gridheight  = 1;

                // Add elements:
                gbc.gridx = 0;
                gbc.gridy += 1;
                add(this.the_scroll_pane, gbc);


            // Fifth row ( buttons ):

                // Buttons:
                gbc.anchor      = gbc.LINE_START;
                gbc.fill        = gbc.BOTH;
                gbc.weightx     = 0.0;
                gbc.weighty     = 0.5;
                gbc.gridwidth   = 1;
                gbc.gridheight  = 1;

                gbc.gridy += 1;
                gbc.gridx = 0;
                add(this.btn_delete, gbc);
                gbc.gridx += 1;
                add(this.btn_format, gbc);
                gbc.gridx += 1;
                add(this.btn_import, gbc);
                gbc.gridx += 1;
                add(this.btn_export, gbc);

    }




    // OBJECT ACTION EVENT LISTENER:
    // Description: Listens for button clicks.
    public void actionPerformed( ActionEvent ae ) {

        // IMPORT BUTTON:

            if ( ae.getSource() == this.btn_import ) {

                // Initialize file with null value:
                File file = null;

                try{
                    // Initialize file:
                    file = new File("src/com/hauge/contacts/rsc/db/import/import_data.csv");
                } catch ( Exception e ) {
                    e.printStackTrace();
                }

                // Run import method:
                this.the_db.setDB_table_import_csv(file);

                // Repopulate table model:
                this.the_model.populate(this.the_db);

            }


        // EXPORT BUTTON:

            else if ( ae.getSource() == this.btn_export ) {

                // Run table export method:
                this.the_db.setDB_table_export_csv("backup/export_data.csv");

            }


        // INSERT BUTTON:

            else if ( ae.getSource() == this.btn_insert ) {

                // Initialize variables for respective columns:
                String pn = this.input_phone.getText();
                String fn = this.input_name_first.getText();
                String ln = this.input_name_last.getText();
                String ad = this.input_address.getText();

                // Run table insert method:
                this.the_db.setDB_insert_person(pn,fn,ln,ad);

                // Repopulate table model:
                this.the_model.populate(this.the_db);

            }


        // DELETE BUTTON:

            else if ( ae.getSource() == this.btn_delete ) {

                // Initialize number:
                String number = this.person_selected.substring(0,8);

                // Run table delete method:
                this.the_db.setDB_delete_person(number);

                // Repopulate table model:
                this.the_model.populate(this.the_db);

            }


        // FORMAT BUTTON:

            else if ( ae.getSource() == this.btn_format ) {

                // Run table format method:
                this.the_db.setDB_table_delete_all();

                // Repopulate table model:
                this.the_model.populate(this.the_db);

            }

    }




    // OBJECT SELECTION EVENT LISTENER:
    // Description: Listens for row selection in JTable.
    private void selectionPerformed ( ListSelectionEvent e ) {

        // Get phone number (PK) of selected row:
        this.person_selected = String.valueOf(this.the_table.getValueAt(this.the_table.getSelectedRow(), 0));

    }




    // OBJECT TABLE MODEL EVENT LISTENER:
    // Description: Listens for changes in the table model.
    public void tableChanged( TableModelEvent e ) {

        the_model.fireTableStructureChanged();

    }

}
