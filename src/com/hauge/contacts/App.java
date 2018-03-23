/**
Project title:      Oblig i Grunnleggende Programmering 2
Project subject:    Contact list application
Project author:     Isak K. Hauge (216745)
Project date:       March 12th 2018

University:         University College of Southeast Norway (USN)
Course:             PRG1100B – Grunnleggende programmering 2
Course subject:     Java

App name:           Hauge Contact Manager
App author:         Isak K. Hauge
App version:        1.0
*/


/*
==================================================
    PRELIMINARY DATA: START
==================================================
*/

    // PACKAGE:
    package com.hauge.contacts;


    // LIBRARIES:

        // Official libraries:
        import javax.swing.*;
        import java.sql.SQLException;

        // Custom libraries:
        import static com.hauge.contacts.rsc.var.Variables.*;
        import com.hauge.contacts.rsc.gui.GUI_create;

/*
==================================================
    PRELIMINARY DATA: END
==================================================
*/


public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {

                // Sets the frame with a title:
                JFrame frame = null;
                try {
                    frame = new GUI_create(WIN_TITLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Sets the frame dimensions:
                frame.setSize(WIN_WIDTH,WIN_HEIGHT);

                // Disable resizability:
                frame.setResizable(false);

                // Sets the default close action:
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                frame.pack();
                frame.setLocationRelativeTo(null);

                // Sets tje
                frame.setVisible(true);
            }
        });

    }

}