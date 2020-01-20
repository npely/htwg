import java.io.*;
import java.sql.*;

public class FerienwohnungSQL {
    public static void main(String args[]) {

        String name = null;
        String passwd = "BTSSuga3";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;

        String anfangsdatum = "";
        String enddatum = "";
        String land = "";
        String ausstattung = "";
        String kundenid = "";
        Boolean neuerKunde = true;
        String Kname = "";
        String IBAN = "";
        String Newsletter = "";
        String Passwort = "";
        String wohnungsid = "";



        //System.out.println("Benutzername: ");
        name = "dbsys13";
        //System.out.println("Passwort: ");

        try {
            System.out.println("Land: ");
            land = in.readLine();

            System.out.println("Anreisedatum: ");
            anfangsdatum = in.readLine();

            System.out.println("Enddatum: ");
            enddatum = in.readLine();

            System.out.println("Aussattung (optional)");
            ausstattung = in.readLine();

        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Eingabe: " + e);
            System.exit(-1);
        }

        System.out.println("");

        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 				// Treiber laden
            String url = "jdbc:oracle:thin:@oracle12c.in.htwg-konstanz.de:1521:ora12c"; // String für DB-Connection
            conn = DriverManager.getConnection(url, name, passwd); 						// Verbindung erstellen

            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); 			// Transaction Isolations-Level setzen
            conn.setAutoCommit(false);													// Kein automatisches Commit

            stmt = conn.createStatement();                                              // Statement-Objekt erzeugen

            String AusstattungView = "";
            String selectView = "";

            if (!ausstattung.isEmpty()) {
                AusstattungView =  "CREATE OR REPLACE VIEW ANZAUS ( WohnungsID, Ausstattungen ) AS " +
                        "SELECT fw.WohnungsID, fw.Ausstattungsname " +
                        "FROM FW_HAT_AUS fw " +
                        "WHERE fw.Ausstattungsname = " + "\'" + ausstattung + "\'";

                stmt.executeQuery(AusstattungView);

                selectView = "AND f.WohnungsID IN " +
                        "(SELECT a.WohnungsID " +
                        "FROM ANZAUS a)";
            }

            String mySelectQuery1 = "SELECT f.Name, AVG(bw.Sterne) AS \"durchschnittliche Bewertung\" " +
                    "FROM Bewertung bw " +
                    "INNER JOIN Buchung b ON b.BuchungsNR = bw.BuchungsNR " +
                    "INNER JOIN Ferienwohnung f ON b.WohnungsID = f.WohnungsID " +
                    "INNER JOIN Adresse a ON f.AdressID = a.AdressID " +
                    "WHERE a.Landname = " + "\'" + land + "\' " + "AND f.WohnungsID NOT IN " +
                    "(SELECT f.WohnungsID " +
                    "FROM Buchung b " +
                    "WHERE b.Anfangsdatum BETWEEN" + "\'" + anfangsdatum + "\' " +  "AND" + "\'" + enddatum + "\' " +
                    "OR  b.Enddatum BETWEEN" + "\'" + anfangsdatum + "\' " +  "AND" + "\'" + enddatum + "\' " +
                    "OR b.Anfangsdatum < " + "\'" + anfangsdatum + "\' " + "AND b.Enddatum >" + "\'" + enddatum + "\' " + ") " +
                    selectView + " " +
                    "GROUP BY f.Name " +
                    "ORDER BY AVG(bw.Sterne) DESC";



            System.out.println(mySelectQuery1);

            rset = stmt.executeQuery(mySelectQuery1);									// Query ausführen

            while(rset.next())
                System.out.println(rset.getString("Name") + " "
                        + rset.getDouble("durchschnittliche Bewertung"));

            System.out.println("Buchen:");
            try {
                System.out.println("KundenID:");
                kundenid = in.readLine();
                System.out.println("Anfangsdatum");
                anfangsdatum = in.readLine();
                System.out.println("Enddatum");
                enddatum = in.readLine();
                System.out.println("WohnungsID");
                wohnungsid = in.readLine();

               /* rset = stmt.executeQuery("SELECT KundenID FROM Kunde");
                while(rset.next()) {
                    if (kundenid.equals(rset.getString("KundenID"))) {
                        neuerKunde = false;
                    }
                }*/
            } catch (IOException e) {
                System.out.println("Fehler beim Lesen der Eingabe: " + e);
                System.exit(-1);
            }

            String updateQuery = "INSERT INTO Buchung (Datum, Anfangsdatum, Enddatum, Buchungsnr, KundenID, WohnungsID) " +
                    "VALUES (SYSDATE, '" + anfangsdatum + "', '" + enddatum + "', Buchungsnr.nextVal, " + kundenid + ", " + wohnungsid + ")";

            rset = stmt.executeQuery(updateQuery);


            // KundenID: 110000000
            // WohnungsID: 100102

            stmt.close();																// Verbindung trennen
            conn.commit();
            conn.close();
        } catch (SQLException se) {														// SQL-Fehler abfangen
            System.out.println();
            System.out
                    .println("SQL Exception occurred while establishing connection to DBS: "
                            + se.getMessage());
            System.out.println("- SQL state  : " + se.getSQLState());
            System.out.println("- Message    : " + se.getMessage());
            System.out.println("- Vendor code: " + se.getErrorCode());
            System.out.println();
            System.out.println("EXITING WITH FAILURE ... !!!");
            System.out.println();
            try {
                conn.rollback();														// Rollback durchführen
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.exit(-1);
        }
    }
}
