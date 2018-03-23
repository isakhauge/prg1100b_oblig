package com.hauge.contacts.rsc.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import static com.hauge.contacts.rsc.var.Variables.*;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;

import com.hauge.contacts.rsc.db.DB;

public class GUI_panel_results extends JPanel {

    public GUI_panel_results() throws SQLException {

        Dimension size = getPreferredSize();
        size.width = 300;
        size.height = 300;
        setPreferredSize(size);

//        setBorder( BorderFactory.createTitledBorder("ResultSet Data") );

        String[][] the_table_data = new DB("kontakter").getDB_data_all_array();

        JTable the_table = new JTable(the_table_data, DB_TABLE_COLUMNS);


    }

}
