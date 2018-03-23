/*
==================================================
    PRELIMINARY DATA: START
==================================================
*/

    // PACKAGE:
    package com.hauge.contacts.rsc.var;


    // LIBRARIES:

/*
==================================================
    PRELIMINARY DATA: END
==================================================
*/


public class Variables {

    // === STRING EFFICIENCY === :

        public final static String NL = "\n";
        public final static String SPACE = " ";
        public final static String DELIM = ";";


    // === APPLICATION INFORMAITON === :

        // GENERAL:
        public final static String APP_NAME         = "Hauge Contact Manager";
        public final static String APP_AUTHOR       = "Isak K. Hauge";
        public final static String APP_VERSION      = "1.0";
        public final static String APP_COPYRIGHT    = "Copyright © 2018" + SPACE + APP_AUTHOR;


    // === GRAPHIC USER INTERFACE === :

        // MAIN FRAME WINDOW:
        public final static String WIN_TITLE        = "Hauge Contact Manager" + SPACE + "v" + APP_VERSION;
        public final static int WIN_WIDTH           = 600;
        public final static int WIN_HEIGHT          = 600;

        // FOOTER DIMENSIONS:
        public final static int FOOTER_HEIGHT       = 30;


    // === DATABASE === :

        public final static String DB_NAME              = "kontakter";
        public final static String[] DB_TABLE_COLUMNS   = {"Phone", "First name:", "Last name:", "Address:"};

}