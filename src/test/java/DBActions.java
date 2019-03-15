import java.sql.*;

public class DBActions {
    private static Connection con;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connectToDB();
        readDBTable();
        //insertDogs();
        //insertValues("Myname34",22,"MyLast34");
        //deleteNameSecure ("Myname12");
        con.close();

    }

    private static void connectToDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // not MANDATORT IN NEW MY SQL SERVERS
        // Open a connection
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?serverTimezone=UTC", "root", "XMP0512ie");
    }

    private static void readDBTable() throws SQLException {
        String statementToExecute = "";
        Statement stmt = con.createStatement();
        statementToExecute = "SELECT * FROM finelproj.mygooglemap;";

        // iterate over query results
        ResultSet rs = stmt.executeQuery(statementToExecute);
        while (rs.next()) {
            String googleURL = rs.getString("URL");
            String APIkey = rs.getString("api");

            //Display values
            System.out.println("URL: " + googleURL);
            System.out.println(", APIkey: " + APIkey);
        }
        rs.close();
        stmt.close();
    }
    public  String getaInfo(String mycolumn) throws SQLException, ClassNotFoundException {
        connectToDB();
        String statementToExecute = "";
        Statement stmt = con.createStatement();
        statementToExecute = "SELECT * FROM finelproj.mygooglemap;";
        String result="";

        // iterate over query results
        ResultSet rs = stmt.executeQuery(statementToExecute);
        while (rs.next()) {
            result = rs.getString(mycolumn);
        }
        rs.close();
        stmt.close();
        con.close();
        return result;
    }
    private static void insertDogs() throws SQLException {
        //Execute a query
        Statement stmt = con.createStatement();
        //String mySQL= 'INSERT INTO `myfirstschema`.`nametable`(`name`,`age`,`lastname`)VALUES(\'aaa\',99,\'nn\')';
        String statementToExecute1 = "INSERT INTO `myfirstschema`.`nametable` (`name`, `age`, `lastname`) VALUES ('" + "NameAddedFromCode12" + "', '" + 22 + "', '" + "LastAddedFromeCode12" + "');";
        stmt.execute(statementToExecute1);
        //con.commit();
        stmt.close();
    }
    private static void insertValues(String myname,int age, String mylastname) throws SQLException {
        //Execute a query
        Statement stmt = con.createStatement();
        //String mySQL= 'INSERT INTO `myfirstschema`.`nametable`(`name`,`age`,`lastname`)VALUES(\'aaa\',99,\'nn\')';
        String statementToExecute1 = "INSERT INTO `myfirstschema`.`nametable` (`name`, `age`, `lastname`) VALUES ('" + myname + "', '" + age + "', '" + mylastname + "');";
        stmt.execute(statementToExecute1);
        //con.commit();
        stmt.close();
    }
    private static void deleteName(String name1) throws SQLException {
        //Execute a query
        String statementToExecute = "";
        Statement stmt = con.createStatement();
//        statementToExecute = "DELETE FROM `myfirstschema`.`nametable` WHERE `name`='" + name1 +";";
        statementToExecute = "DELETE FROM `myfirstschema`.`nametable` WHERE `name`='"+name1+"'";
        //statementToExecute = "DELETE FROM `myfirstschema`.`nametable` WHERE `name`='NameAddedFromCode12'";
        stmt.execute(statementToExecute);
        stmt.close();
    }
    private static void deleteNameSecure(String name1) throws SQLException {
        //Execute a query
        String statementToExecute = "";
        PreparedStatement stmt = con.prepareStatement("DELETE FROM `myfirstschema`.`nametable` WHERE `name`= ?;");
        stmt.setString(1,"Myname3");
        stmt.executeUpdate();
        stmt.close();
    }

}
