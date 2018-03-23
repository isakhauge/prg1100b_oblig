/*
==================================================
    PRELIMINARY DATA: START
==================================================
*/

    // PACKAGE:
    package com.hauge.contacts.rsc.gui;


    // LIBRARIES:

        // Official libraries:
        import com.hauge.contacts.rsc.gui.panels.GUI_panel_footer;
        import com.hauge.contacts.rsc.gui.panels.GUI_panel_main;

        import javax.swing.JFrame;
        import java.awt.*;


        // My libraries:

/*
==================================================
    PRELIMINARY DATA: END
==================================================
*/


public class GUI_create extends JFrame {

    private GUI_panel_main GUIpanelMain;
    private GUI_panel_footer GUIpanelFooter;

    public GUI_create(String title) {

        // TODO: Must find out what this shit means!
        super(title);


        // Set the JFrame layout:
        setLayout( new BorderLayout() );


        // Create Swing components:

            // Main Panel:
            GUIpanelMain = new GUI_panel_main();


            // Footer:
            GUIpanelFooter = new GUI_panel_footer();



        // Add Swing components to content pane:
        Container frame_container = getContentPane();

        frame_container.add(GUIpanelMain, BorderLayout.CENTER);
        frame_container.add(GUIpanelFooter, BorderLayout.SOUTH);

    }


}