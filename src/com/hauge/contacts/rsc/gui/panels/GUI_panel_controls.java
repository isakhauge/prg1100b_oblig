package com.hauge.contacts.rsc.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static com.hauge.contacts.rsc.var.Variables.*;

public class GUI_panel_controls extends JPanel {

    public GUI_panel_controls() throws SQLException {

        Dimension size = getPreferredSize();
        size.width = 300;
        size.height = 300;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("Control Panel"));

        JLabel label_name = new JLabel("Name");
        JLabel label_occupation = new JLabel("Occupation: ");
        final JTextField field_name = new JTextField(10);
        final JTextField field_occupation = new JTextField(10);
        JButton button_add = new JButton("Add");

        // Add behaviour:
        button_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String name = field_name.getText();
               String occupation = field_occupation.getText();

               String text = name + ": " + occupation + NL;

            }
        });

        // Set new layout:
        setLayout( new GridBagLayout() );

        // Set a Grid Bag Constraint:
        GridBagConstraints gc = new GridBagConstraints();



        // FIRST GRID COLUMN:

        // Make labels stick to the right:
        gc.anchor = GridBagConstraints.LINE_END;

        // Space allocation (?):
        gc.weightx = 0.5;
        gc.weighty = 0.5;

        gc.gridx = 0;
        gc.gridy = 0;
        add(label_name, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        add(label_occupation, gc);



        // SECOND COLUMN:

        // Make fields stick to the left:
        gc.anchor = GridBagConstraints.LINE_START;

        gc.gridx = 1;
        gc.gridy = 0;
        add(field_name, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        add(field_occupation, gc);



        // FINAL ROW:

        gc.weighty = 10;

        // Handy little thing I might get use for:
//        gc.fill = GridBagConstraints.BOTH;

        gc.anchor = GridBagConstraints.FIRST_LINE_START;

        gc.gridx = 1;
        gc.gridy = 2;
        add(button_add, gc);

    }

}
