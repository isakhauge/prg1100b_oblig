/*
==================================================
    PRELIMINARY DATA: START
==================================================
*/

    // PACKAGE:
    package com.hauge.contact.rsc.db;


    // LIBRARIES:

        // Official libraries:
        import java.sql.*;
        import java.io.*;


/*
==================================================
    PRELIMINARY DATA: END
==================================================
*/


public class DB {

    // === GLOBAL VARIABLES === :

        // STRING EFFICIENCY:

            private static String nl = "\n";
            private static String space = " ";
            private static String delim = "; ";


    // === OBJECT VARIABLES === :

        // JDBC DRIVER:

            String jdbc_driver;


        // DATABASE FILE LOCATION (URL):

            String db_url;


        // DATABASE CONNECTION:

            Connection conn = null;




    // === OBJECT CONSTRUCTOR === :

        // MAIN CONSTRUCTOR:
        public DB ( String param_db_name ) throws SQLException {

            // Declaring the return statement:

                String output;


            // Initialize DB url:

                this.db_url = "jdbc:sqlite:src/com.hauge.contact/rsc/db/" + param_db_name + ".db";


            // Initialize DB (SQLite3) driver:

                this.jdbc_driver = "org.sqlite.JDBC";


            // Initialize DB file:

                File file_db = new File("src/com.hauge.contact/rsc/db/" + param_db_name + ".db");


            // Check existence of DB file:

                if ( file_db.isFile() == false ) {

                    // Establish connection to DB:

                        db_connect();


                    // If DB file does not exist; create one:

                        try {

                            Statement stmt = conn.createStatement();
                            String sql_code = sql_generate_default_table() + space + sql_populate_default_table();
                            stmt.executeUpdate(sql_code);

                            output = "Databasen (" + param_db_name + ") ble opprettet og fylt med demo-data.";

                        }


                    // If there was en error in the SQL query:

                        catch (Exception e) {

                            output = "Databasespørring (query) feilet!";

                        }


                    // Terminate DB connection:

                        db_disconnect();


                    // Message:

                        javax.swing.JOptionPane.showMessageDialog(null, output);


                } else {

                    // Message about the existence of DB file:

                        javax.swing.JOptionPane.showMessageDialog(null, "Filen eksisterer.");

                }

                // Message about the DB connection status:

                    javax.swing.JOptionPane.showMessageDialog(null, getDB_conn_status());

        }




    // === OBJECT SETTERS === :

        // public void setDB_table




    // === OBJECT GETTERS === :

        // GET THE DB CONNECTION STATUS:

            public String getDB_conn_status() throws SQLException {

                // Declaring the return statement:

                    String output = "";


                // Connect to the database:

                    try {
                        Class.forName(this.jdbc_driver);
                        conn = DriverManager.getConnection(db_url);
                        output = "Database er tilkoblet.";
                    }

                    catch ( ClassNotFoundException e ) {
                        if ( conn != null ) { conn.close(); }
                        output = "Fant ikke JDBC-driveren " + this.jdbc_driver + "\n" + e.toString();
                    }

                    catch ( SQLException e ) {
                        if ( conn != null ) { conn.close(); }
                        output = "Oppkobling til databasen " + this.db_url + " feilet.\n" + e.toString();
                    }


                // Return statement:

                    return output;

            }


        // GET ALL DB DATA:

            public String getDB_data_all() throws SQLException {

                // Initialize an empty return statement:

                    String output = "";


                // Declare data variables:

                    String nr, name_first, name_last, address;


                // Extract all data from table:

                    String sql_code = "SELECT * FROM `Person`;";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql_code);

                    while ( rs.next() ) {

                        nr = rs.getString("tlf");
                        name_first = rs.getString("fornavn");
                        name_last = rs.getString("etternavn");
                        address = rs.getString("adresse");

                        output += nr + delim + name_first + delim + name_last + delim + address + nl;

                    }


                // Return statement:

                    return output;

            }


        // GET DB DATA WITH CUSTOM SQL:

            public String getDB_data_sql( String param_sql ) throws SQLException {

                // Initialize empty return statement:

                    String output = "";


                // Declare data variables:

                    String nr, name_first, name_last, address;


                // Extract data from DB:

                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(param_sql);

                    while ( rs.next() ) {

                        nr = rs.getString("tlf");
                        name_first = rs.getString("fornavn");
                        name_last = rs.getString("etternavn");
                        address = rs.getString("adresse");

                        output += nr + delim + name_first + delim + name_last + delim + address + nl;

                    }


                // Return statement:

                    return output;

            }


        // GET DB DATA SPECIFIC PERSON BY NR:

            public String getDB_person_nr( String param_phone_number ) throws SQLException {

                // Initialize an empty return statement:

                    String output = "";


                // Declare data variables:

                    String nr, name_first, name_last, address;


                // Extract all data from table:

                    String sql_code = "SELECT * FROM `Person` WHERE `tlf` = '" + param_phone_number + "' ;";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql_code);

                    while ( rs.next() ) {

                        nr = rs.getString("tlf");
                        name_first = rs.getString("fornavn");
                        name_last = rs.getString("etternavn");
                        address = rs.getString("adresse");

                        output += nr + delim + name_first + delim + name_last + delim + address + nl;

                    }


                // Return statement:

                    return output;

            }




    // === OBJECT METHODS === :

        // DB GENERATE DEFAULT TABLE:

            private static String sql_generate_default_table() {

                String sql;

                sql =   "CREATE TABLE `Person` (" + nl +
                        "`tlf`          VARCHAR(8)      NOT NULL,                   " + nl +
                        "`fornavn`      VARCHAR(60)     NOT NULL,                   " + nl +
                        "`etternavn`    VARCHAR(60)     NOT NULL,                   " + nl +
                        "`adresse`      TINYINT(60)     DEFAULT 'ikke definert',    " + nl +
                        "CONSTRAINT `Person_PK` PRIMARY KEY (`tlf`)                 " + nl +
                        ");";

                return sql;

            }


        // DB POPULATE DEFAULT TABLE:

            private static String sql_populate_default_table() {

                String sql;

                sql =   "INSERT INTO `Person` ( `tlf`, `fornavn`, `etternavn`, `adresse` ) VALUES " + nl +
                        "('91527060', 'Isak', 'Hauge', 'Stasjonsvegen 21B 3800 Bø i Telemark'" + nl +
                        ")";

                return sql;

            }


        // DB CONNECT:

            private void db_connect() throws SQLException {

                // Connection:

                    try {

                        // Enable JDBC Driver:

                            Class.forName(this.jdbc_driver);


                        // Initializing connection:

                            conn = DriverManager.getConnection(this.db_url);

                    }


                // In case the driver was not found:

                    catch ( ClassNotFoundException e ) {
                        if ( conn != null ) { conn.close(); }
                        conn_error("Fant ikke JDBC-driveren " + this.jdbc_driver + "\n" + e.toString());
                    }


                // In case an error occurred during DB connection attempt:

                    catch ( SQLException e ) {
                        if ( conn != null ) { conn.close(); }
                        conn_error("Oppkobling til databasen " + this.db_url + " feilet.\n" + e.toString());
                    }

            }


        // DB DISCONNECT:

            private void db_disconnect() {

                // Close:

                    try {
                        conn.close();
                    }


                // If DB disconnection went wrong:

                    catch (SQLException e) {
                        // Future error message;
                    }

            }


        // DB CONNECTION ERROR MESSAGE:

            private void conn_error ( String param_msg ) throws SQLException {

                // If there is a connection; close it:

                    if ( conn != null ) {

                        conn.close();

                    }


                // Display message if connection is closed:

                    javax.swing.JOptionPane.showMessageDialog(null, param_msg);


                // Close application:
                    System.exit(0);

            }

}