package com.hauge.contacts.rsc.gui.panels;


import javax.swing.*;
import java.awt.*;

import static com.hauge.contacts.rsc.var.Variables.*;

public class GUI_panel_footer extends JPanel {

    public GUI_panel_footer() {

        Dimension size = getPreferredSize();
//        size.width = WIN_WIDTH;
//        size.height = FOOTER_HEIGHT;
//        setPreferredSize(size);
//        setMaximumSize(size);

        JLabel copyright = new JLabel(APP_COPYRIGHT);
        Font footer = new Font("Arial", Font.PLAIN,10);
        copyright.setFont(footer);

        // Set the layout:
        setLayout( new FlowLayout() );

        FlowLayout fl = new FlowLayout();

        add(copyright);

    }

}
