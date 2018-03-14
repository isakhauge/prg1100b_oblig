/**
Project title:      Oblig i Grunnleggende programmering 2
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
    package com.hauge.contact;


    // LIBRARIES:

        // Official libraries:

// Custom libraries:
        import com.hauge.contact.rsc.db.DB;

/*
==================================================
    PRELIMINARY DATA: END
==================================================
*/


public class Main {

    public static void main(String[] args) throws Exception {

        DB db1 = new DB( "kontakter" );

        javax.swing.JOptionPane.showMessageDialog(null, db1.getDB_data_all() );

    }

}