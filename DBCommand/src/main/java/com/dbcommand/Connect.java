package com.dbcommand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connect
{
    private static String err_msg;

    public static String Connect_to_Database(String mat, String cla, String sub)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String host = "jdbc:mysql://127.0.0.1:3306";
            String uName = "root";
            String uPass = "";


            Connection con = DriverManager.getConnection(host, uName, uPass);

            Statement statement = con.createStatement();
            /* Exécution d'une requête de lecture */
            statement.execute("USE cours");
            ResultSet resultat = statement.executeQuery("SELECT n FROM cours WHERE Matiere=" + mat + " AND Sujet=" + cla + ";");
            /*while ( resultat.next() )
            {
            }*/
            resultat.next();
            int index = resultat.getInt("n");

            return ("I found this one : " + sub);
        }
        catch (Exception e)
        {
            err_msg = "Error while connecting to DB : " + e.getMessage(); // + " Param was : " + mat + " && " + cla;
            System.out.println(err_msg);
            return (err_msg);
        }
    }
}
