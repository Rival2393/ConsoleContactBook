package processing_classes;

import data_classes.Contact;

import java.sql.*;
import java.util.HashMap;


public class DataBase
{
    private final static String DB_URL = "jdbc:mysql://localhost:3306/contacts";
    private final static String NO_DB_URL = "jdbc:mysql://localhost/mysql";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "17174dima23";

    private static Connection connection;
    private static Statement statement;

    public static void startConnection()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("DB available");
        } catch (ClassNotFoundException e)
        {
            System.err.println("Driver not found");
        } catch (SQLException e)
        {
            System.out.println("Database is not found, creating new...");
            try {
                connection = DriverManager.getConnection(NO_DB_URL, USERNAME, PASSWORD);
                statement = connection.createStatement();
                statement.executeUpdate("CREATE DATABASE contacts");
                System.out.println("Data base was created");

                String columns = "(Id INT NOT NULL, " +
                        "FirstName VARCHAR (30), " +
                        "LastName VARCHAR (30), " +
                        "PhoneNumber VARCHAR (19), " +
                        "PRIMARY KEY (Id))";

                String createFriends = "CREATE TABLE contacts.friends" + columns;
                String createColleagues = "CREATE TABLE contacts.colleagues" + columns;
                String createMates = "CREATE TABLE contacts.mates" + columns;
                String createServices = "CREATE TABLE contacts.services" + columns;
                String createOthers = "CREATE TABLE contacts.others" + columns;

                statement.execute(createFriends);
                statement.execute(createColleagues);
                statement.execute(createMates);
                statement.execute(createServices);
                statement.execute(createOthers);

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    public static void closeConnection() throws SQLException
    {
        if(!connection.isClosed())
        {
            connection.close();
        }
    }

    /* RUD */

    public static void read(String table)
    {
        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM contacts."+table;
            ResultSet set = statement.executeQuery(query);
            int id = 1;
            while(set.next())
            {
                String setInfo = id++ + ") " + set.getString("FirstName") + " " +
                        set.getString("LastName") + " " + set.getString("PhoneNumber");

                System.out.println(setInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void update() throws SQLException {

        for(int i=0; i<TemporaryStorage.getStorage().size()-1; i++)
        {
            for(HashMap.Entry<Integer, Contact> entry : TemporaryStorage.getStorage().get(i).entrySet())
            {
                statement = connection.createStatement();
                String query = "INSERT INTO contacts.";
                switch (i) {
                    case 0:
                        query+="friends ";
                        break;
                    case 1:
                        query+="colleagues ";
                        break;
                    case 2:
                        query+="mates ";
                        break;
                    case 3:
                        query+="services ";
                        break;
                    case 4:
                        query+="others ";
                        break;
                }
                query+="(Id, FirstName, LastName, PhoneNumber) ";
                query+="VALUES ('"+entry.getKey()+"', '"+entry.getValue().getFirstName()+"', '"+entry.getValue().getSecondName()+
                        "', '"+entry.getValue().getPhoneNumber()+"')";
                System.out.println(query);
                try {
                    statement.execute(query);
                } catch (Exception e) {
                    if (e instanceof NullPointerException)
                    {
                        query = "";
                        continue;
                    }
                }
            }
        }
    }

    public static void delete(String table, String firstName, String lastName)
    {
        try {
            statement = connection.createStatement();
            String query = "DELETE FROM contacts." + table + " WHERE FirstName = '" +
                    firstName + "' AND LastName = '" + lastName + "'";

            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
