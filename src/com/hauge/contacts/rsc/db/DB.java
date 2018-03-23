/*
==================================================
    PRELIMINARY DATA: START
==================================================
*/

    // PACKAGE:
    package com.hauge.contacts.rsc.db;


    // LIBRARIES:

        // Official libraries:
        import org.sqlite.SQLiteException;

        import javax.swing.*;
        import java.nio.charset.StandardCharsets;
        import java.sql.*;
        import java.io.*;
        import java.util.*;

        // Unofficial libraries:
        import static com.hauge.contacts.rsc.var.Variables.*;


/*
==================================================
    PRELIMINARY DATA: END
==================================================
*/


public class DB {

    // === GLOBAL VARIABLES === :


        // DB EFFICIENCY:

            // Maybe.




    // === OBJECT VARIABLES === :

        // JDBC DRIVER:

            String jdbc_driver;


        // DATABASE FILE LOCATION (URL):

            String db_url;


        // DATABASE CONNECTION:

            Connection conn = null;


        // DATABASE NAME:

            String db_name;




    // === OBJECT CONSTRUCTOR === :

        // MAIN CONSTRUCTOR:
        public DB ( String param_db_name ) {

            // Declaring the output message:

                String msg_text;


            // Initialize DB url:

                this.db_url = "jdbc:sqlite:src/com/hauge/contacts/rsc/db/" + param_db_name + ".db";


            // Initialize DB (SQLite3) driver:

                this.jdbc_driver = "org.sqlite.JDBC";


            // Initialize DN name:

                this.db_name = param_db_name;


            // Initialize DB file:

                File file_db = new File("src/com/hauge/contacts/rsc/db/" + param_db_name + ".db");


            // Check existence of DB file:

                if ( file_db.isFile() == false ) {

                    // If DB file does not exist; create one:

                        try {

                            db_connect();
                            Statement stmt = conn.createStatement();
                            String sql_code = sql_generate_default_table() + NL + sql_populate_default_table();
                            stmt.executeUpdate(sql_code);
                            db_disconnect();

                            msg_text = "Databasen (" + param_db_name + ") ble opprettet og fylt med demo-data.";

                        }


                    // If there was en error in the SQL query:

                        catch (Exception e) {

                            msg_text = "Databasespørring (query) feilet!";

                        }


                    // Message:

                        msg_out(msg_text,"Database: Created","info");


                } else {

                    // Message about the existence of DB file:
                        msg_text = "The database is alive!";
                        msg_out(msg_text,"Database: File status", "info");

                }

                // Message about the DB connection status:

                    msg_out(getDB_conn_status(), "Database: Connection status", "info");

        }




    // === OBJECT SETTERS === :

        // INSERT PERSON:
        public void setDB_insert_person( String phone, String name_first, String name_last, String address ) {

            // Test requirements:
            if ( phone.matches("[0-9]{8}$") ) {

                // Fill non-required data with default value if not defined:
                if ( name_first.length() < 1 ) { name_first = "n/a"; }
                if ( name_last.length() < 1 ) { name_last = "n/a"; }
                if ( address.length() < 1 ) { address = "n/a"; }

                // Build the SQL code:
                String sql_code = "INSERT INTO `Person` (`tlf`, `fornavn`, `etternavn`, `adresse`) VALUES " + NL;
                sql_code += "( '" + phone + "', '" + name_first + "', '" + name_last + "', '" + address + "' );";

                try {

                    db_connect();
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(sql_code);
                    db_disconnect();

                    msg_out("You have successfully added a new contact", "Contact added", "info");

                } catch ( SQLException e ) {

                    msg_out("You cannot add the same number twice, darling.", "Primary key conflict", "error");
                    e.printStackTrace();

                }

            } else {

                msg_out("Phone number must be 8 digits (0-9).", "Input error", "error");

            }

        }


        // IMPORT DATA FROM CSV:
        public void setDB_table_import_csv( File file ) {

            /*
            * Method usage.
            * ===
            * setDB_table_import_csv( file );
            * */

            // PRELIMINARY STAGE - GETTING THE ROW-AND-COL COUNT OF THE DOC.:

                // Method name:
                String title = "CSV Importer";
                String status;

                // Getting the initial row-and-col count from the DB:
                int old_row_count = getDB_table_row_count();

                // Initializing the File and the Scanner;
                Scanner scanner_01;
                String[] cols_array;
                int col_count = 0;

                try {

                    scanner_01 = new Scanner(file, "UTF8");

                    // Calculating column count in file:
                    cols_array = scanner_01.nextLine().split(";");
                    scanner_01.close();

                    col_count = cols_array.length;

                } catch ( FileNotFoundException e ) {

                    msg_out("The file does not exist.", "File not fount", "error");

                } catch ( ArrayIndexOutOfBoundsException e ) {

                    msg_out("Data transfer from Scanner to array failed.", "Array index out of bounds", "error");

                }

                // Initializing the Scanner again:
                Scanner scanner_02;
                String rows_string = "";
                String[] rows_array;
                int row_count = 0;

                try {

                    scanner_02 = new Scanner(file, "UTF8");

                    // Calculating the row count in file:
                    while ( scanner_02.hasNextLine() ) {
                        rows_string += scanner_02.nextLine() + NL;
                    }
                    scanner_02.close();

                    rows_array = rows_string.split(NL);
                    row_count = rows_array.length;

                } catch ( FileNotFoundException e ) {

                    msg_out("The file does not exist.", "File not fount", "error");

                } catch ( ArrayIndexOutOfBoundsException e ) {

                    msg_out("Data transfer from Scanner to array failed.", "Array index out of bounds", "error");

                }


                String msg = "Columns: " + col_count + NL + "Rows: " + row_count;
                msg_out(msg,title,"info");


            // POPULATING THE ARRAY:

                // Defining the 2 dimensional array:
                String[][] table_data = new String[row_count][];

                // Initializing the Scanner again:
                Scanner scanner_03;
                String delim = ";";

                try {

                    scanner_03 = new Scanner(file, "UTF8");

                    // Populating the array:
                    int row = 0;
                    while ( scanner_03.hasNextLine() ) {

                        String each_line = scanner_03.nextLine();
                        table_data[row++] = each_line.split(delim);

                    }
                    scanner_03.close();

                } catch ( FileNotFoundException e ) {

                    msg_out("The file does not exist.", "File not fount", "error");

                } catch ( ArrayIndexOutOfBoundsException e ) {

                    msg_out("Data transfer from Scanner to array failed.", "Array index out of bounds", "error");

                }


                msg = table_data[row_count-1][col_count-1] + NL + table_data[0][0] + NL + table_data[17][2];
                msg_out(msg,title,"info");


            // PREPARING THE SQL SCRIPT:

                // Declaring the SQL script String variable:
                String sql_code;

                // Populating the first line of the SQL script:
                sql_code = "INSERT INTO `Person` ( `tlf`, `fornavn`, `etternavn`, `adresse` ) VALUES " + NL;

                // Populating the rest of the SQL script through a for loop:
                for ( int i = 0; i < table_data.length; i++ ) {

                    sql_code += "( ";

                    for ( int j = 0; j < table_data[0].length; j++ ) {

                        if ( j == table_data[0].length - 1 ) {

                            sql_code += "'" + table_data[i][j] + "'";

                        } else {

                            sql_code += "'" + table_data[i][j] + "', ";

                        }

                    }

                    if ( i == table_data.length - 1 ) {

                        sql_code += " );";

                    } else {

                        sql_code += " )," + NL;

                    }


                }

                msg = sql_code;
                System.out.println(sql_code);


            // INJECTING DATABASE WITH SQL SCRIPT:

                String msg_text;


                // If DB file does not exist; create one:
                try {

                    db_connect();
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(sql_code);
                    db_disconnect();

                    status = ": Success!";
                    msg_text = "CSV data was successfully imported to database!";
                    msg_out(msg_text,title+status,"info");

                }

                // If there was en error in the SQL query:
                catch ( SQLSyntaxErrorException e ) {

                    status = ": Error!";
                    msg_text = "Syntax error! There is something wrong in the SQL code." + NL + "Error: " + e;
                    msg_out(msg_text,title+status,"error");

                }

                // If there is an error in the SQLite:
                catch ( SQLiteException e ) {

                    String exception = String.valueOf(e);
                    status = ": Error!";

                    if ( exception.indexOf("constraint failed") != -1 ) {

                        msg_text = "You are importing UNIQUE values that already exists.";
                        msg_out(msg_text,title+status,"error");

                    } else {

                        msg_text = "Undefined SQLite exception caught.";
                        msg_out(msg_text,title+status,"error");

                    }

                }

                catch ( SQLException e ) {

                    int added_row_count = getDB_table_row_count() - old_row_count;
                    msg_text = added_row_count + " rows were added.";
                    msg_out(msg_text,title,"info");

                }

        }


        // EXPORT DATA TO CSV:
        public void setDB_table_export_csv( String param_file_name ) {

            /*
             * Method usage.
             * ===
             * setDB_table_export_csv( "folder/file.csv" );
             * */

            // PRELIMINARY STAGE:

                // Method variables:
                String title = "CSV Exporter";
                String status;
                String msg;

                // Initializing the export array:
                String[][] export_array = getDB_data_all_array();

                // Checking whether the table is empty:
                boolean table_empty = false;
                if ( export_array.length <= 0 ) {
                    table_empty = true;
                }

                // Export only if table is not empty:
                if ( table_empty == false ) {

                    // Initializing the export string:
                    String export = "";

                    // Initialize the file name:
                    String path = "src/com/hauge/contacts/rsc/db/";
                    String file_name = path + param_file_name;

                    FileOutputStream    fos;
                    OutputStreamWriter  osw;
                    BufferedWriter      the_writer;

                    try {

                        // Create and prepare the CSV file:
                        fos =           new FileOutputStream( new File(file_name), false );
                        osw =           new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                        the_writer =    new BufferedWriter( osw );


                        // Concatenate array data to String:
                        for ( int row = 0; row < export_array.length; row++ ) {

                            for ( int col = 0; col < export_array[0].length; col++ ) {

                                export += export_array[row][col] + DELIM;

                            }

                            export += NL;

                        }

                        // Write export String to CSV document:
                        the_writer.write(export);

                        // Close the Writer:
                        the_writer.close();

                        msg_out("Backup of the contact list was successful!", "CSV Backup: Success!", "info");

                    } catch ( IOException e ) {

                        msg_out( "The file does not exist.", "File not found", "error" );
                        e.printStackTrace();

                    } catch ( NullPointerException e ) {

                        msg_out( "Null.", "Null pointer exception", "error" );
                        e.printStackTrace();

                    }

                } else {

                    status = ": Error";
                    msg = "The database table is empty. No data was exported.";
                    msg_out(msg,title+status,"info");

                }

        }


        // DELETE ALL TABLE DATA:
        public void setDB_table_delete_all() {

            // PRELIMINARY STAGE:

                // Method data:
                String method_title = "Format table (SQL)";
                String method_status;
                String method_msg;

                // Initialize SQL String:
                String sql_code = "DELETE FROM `Person`";

                // Count row-and-column count:
                int row_count = getDB_table_row_count();




            // SEND DELETE QUERY TO SERVER:

                try{
                    // Establish connection with database:
                    db_connect();

                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(sql_code);

                    // Terminate connection:
                    db_disconnect();

                    method_status = ": Success!";
                    method_msg = "All table entries was successfully deleted.";
                    msg_out(method_msg,method_title+method_status,"info");
                }
                catch ( SQLSyntaxErrorException e ) {
                    method_status = ": Error!";
                    method_msg = "Syntax error. There is an error within the SQL code." + NL + "Tables were not deleted.";
                    msg_out(method_msg,method_title+method_status,"error");
                }
                catch ( SQLiteException e ) {
                    method_status = ": Error!";
                    method_msg = "SQLite Error. There is an error with the SQLite application." + NL + "Tables were not deleted.";
                    msg_out(method_msg,method_title+method_status,"error");
                }
                catch ( SQLException e ) {

                    int error_code = e.getErrorCode();

                    if ( error_code == 101 ) {

                        if ( row_count <= 0 ) {

                            method_status = ": There is nothing here!";
                            method_msg = "Table is empty. No tables were deleted.";
                            msg_out(method_msg,method_title+method_status,"warning");

                        } else {

                            int deleted_row_count = row_count - getDB_table_row_count();
                            method_status = ": Success!";
                            method_msg = deleted_row_count + " table entries was successfully deleted.";
                            msg_out(method_msg,method_title+method_status,"info");

                        }

                    } else {

                        String error_msg = String.valueOf(e);
                        method_status = ": Error!";
                        method_msg = "Undefined error occurred. " + "(ERROR: " + error_code + ")"  + NL + error_msg;
                        msg_out(method_msg,method_title+method_status,"error");

                    }

                }

        }


        // DELETE PERSON BY NUMBER:
        public void setDB_delete_person( String phone ) {

            String sql_code = "DELETE FROM `Person` WHERE `tlf` = ";
            sql_code += "'" + phone + "'";

            try {

                db_connect();
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql_code);
                db_disconnect();

                String msg = "A person with the number (" + phone + ") was deleted.";
                msg_out(msg, "Person deleted", "warning");

            }
            catch ( SQLException e ) {

                msg_out("It worked - perhaps.", "Delete person", "info");
                e.printStackTrace();

            }

        }




    // === OBJECT GETTERS === :

        // GET THE DB CONNECTION STATUS:

            public String getDB_conn_status() {

                // Declaring the return statement:

                    String output = "";


                // Connect to the database:

                    try {
                        Class.forName(this.jdbc_driver);
                        conn = DriverManager.getConnection(db_url);
                        output = "Database er tilkoblet.";
                    }

                    catch ( ClassNotFoundException e ) {
                        if ( conn != null ) {
                            try {
                                conn.close();
                            } catch ( SQLException ex ) {

                            }
                        }
                        output = "Fant ikke JDBC-driveren " + this.jdbc_driver + "\n" + e.toString();
                    }

                    catch ( SQLException e ) {
                        if ( conn != null ) {
                            try {
                                conn.close();
                            } catch ( SQLException ex ) {

                            }
                        }
                        output = "Oppkobling til databasen " + this.db_url + " feilet.\n" + e.toString();
                    }


                // Return statement:

                    return output;

            }


        // GET ALL DB DATA:

            public String getDB_data_all() {

                // Initialize an empty return statement:

                    String output = "";


                // Declare data variables:

                    String nr, name_first, name_last, address;


                // Extract all data from table:

                    Statement stmt;
                    ResultSet rs;

                    try{

                        String sql_code = "SELECT * FROM `Person`;";
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery(sql_code);

                        while ( rs.next() ) {

                            nr = rs.getString("tlf");
                            name_first = rs.getString("fornavn");
                            name_last = rs.getString("etternavn");
                            address = rs.getString("adresse");

                            output += nr + DELIM + name_first + DELIM + name_last + DELIM + address + NL;

                        }

                    } catch ( SQLException e ) {

                        msg_out("Get all DB data.", "Get DB data", "info");

                    }


                // Return statement:

                    return output;

            }


        // GET ALL DB DATA IN AN ARRAY:

            public String[][] getDB_data_all_array() {

                int col_count = getDB_table_col_count();
                int row_count = getDB_table_row_count();
                String[][] output = new String[row_count][col_count];

                Statement stmt;
                ResultSet rs;

                try {
                    db_connect();
                    String sql_code = "SELECT * FROM `Person`;";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql_code);

                    for (int row = 0; row < row_count; row++) {
                        rs.next();
                        for (int col = 0; col < col_count; col++) {
                            output[row][col] = rs.getString(col + 1);
                        }
                    }

                    db_disconnect();

                } catch ( SQLException e ) {

                    msg_out("Something happened","Get all array","info");
                    e.printStackTrace();

                }

                return output;

            }


        // GET TABLE ROW COUNT:

            public int getDB_table_row_count() {

                int row_count = 0;
                Statement stmt;
                ResultSet rs;

                db_connect();

                try{

                    String sql_code = "SELECT COUNT(*) AS `rowcount` FROM `Person`";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql_code);

                    row_count = rs.getInt("rowcount");

                } catch ( SQLException e ) {

                    msg_out("Get table row count.", "Row Counter", "info");

                }

                db_disconnect();

                return row_count;

            }


        // GET TABLE COLUMN COUNT:

            public int getDB_table_col_count() {

                Statement stmt;
                ResultSet rs;
                int col_count = 0;

                db_connect();

                try{

                    String sql_code = "SELECT * FROM `Person`;";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql_code);

                    col_count = rs.getMetaData().getColumnCount();

                } catch ( SQLException e ) {

                    msg_out("Get table col count.", "Col Counter", "info");

                }

                db_disconnect();

                return col_count;
            }




    // === OBJECT METHODS === :

        // DB GENERATE DEFAULT TABLE:
            private static String sql_generate_default_table() {

                String sql;

                sql =   "CREATE TABLE `Person` (" + NL +
                        "`tlf`          VARCHAR(8)      NOT NULL,                   " + NL +
                        "`fornavn`      VARCHAR(60)     NOT NULL,                   " + NL +
                        "`etternavn`    VARCHAR(60)     NOT NULL,                   " + NL +
                        "`adresse`      TINYINT(60)     DEFAULT 'ikke definert',    " + NL +
                        "CONSTRAINT `Person_PK` PRIMARY KEY (`tlf`)                 " + NL +
                        ");";

                return sql;

            }


        // DB POPULATE DEFAULT TABLE:

            private static String sql_populate_default_table() {

                String sql;

                sql =   "INSERT INTO `Person` ( `tlf`, `fornavn`, `etternavn`, `adresse` ) VALUES " + NL +
                        "('91527060', 'Isak', 'Hauge', 'Stasjonsvegen 21B 3800 Bø i Telemark'" + NL +
                        ")";

                return sql;

            }


        // DB CONNECT:

            private void db_connect() {

                // Connection:

                    try {

                        // Enable JDBC Driver:

                            Class.forName(this.jdbc_driver);


                        // Initializing connection:

                            conn = DriverManager.getConnection(this.db_url);

                    }


                // In case the driver was not found:

                    catch ( ClassNotFoundException e ) {
                        if ( conn != null ) {
                            try {
                                conn.close();
                            } catch ( SQLException ex ) {

                            }
                        }
                        conn_error("Fant ikke JDBC-driveren " + this.jdbc_driver + "\n" + e.toString());
                    }


                // In case an error occurred during DB connection attempt:

                    catch ( SQLException e ) {
                        if ( conn != null ) {
                            try {
                                conn.close();
                            } catch ( SQLException ex ) {

                            }
                        }
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

            private void conn_error ( String param_msg ) {

                // If there is a connection; close it:

                    if ( conn != null ) {

                        try {
                            conn.close();
                        } catch ( SQLException ex ) {

                        }

                    }


                // Display message if connection is closed:

                    msg_out(param_msg,"","error");


                // Close application:
                    System.exit(0);

            }


        // DB MESSAGE BOX:

            private void msg_out(String text, String title, String category ) {

                switch ( category ) {

                    case "info":    javax.swing.JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
                    break;
                    case "query":   javax.swing.JOptionPane.showMessageDialog(null, text, title, JOptionPane.QUESTION_MESSAGE);
                    break;
                    case "warning": javax.swing.JOptionPane.showMessageDialog(null, text, title, JOptionPane.WARNING_MESSAGE);
                    break;
                    case "error":   javax.swing.JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
                    break;
                    default:        javax.swing.JOptionPane.showMessageDialog(null, text, title, JOptionPane.PLAIN_MESSAGE);
                    break;

                }

            }


        // DB MESSAGE BOX:

            private String msg_in(String text, String title, String category ) {

            switch ( category ) {

                case "info":    return javax.swing.JOptionPane.showInputDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
                case "query":   return javax.swing.JOptionPane.showInputDialog(null, text, title, JOptionPane.QUESTION_MESSAGE);
                case "warning": return javax.swing.JOptionPane.showInputDialog(null, text, title, JOptionPane.WARNING_MESSAGE);
                case "error":   return javax.swing.JOptionPane.showInputDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
                default:        return javax.swing.JOptionPane.showInputDialog(null, text, title, JOptionPane.PLAIN_MESSAGE);

            }

    }

}