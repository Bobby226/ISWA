package com.cours.ws;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Path("/cours")
public class main_cours_ws
{
    private String err_msg = "";

    private String Connect_to_Database(String mat, String cla)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String host = "jdbc:mysql://127.0.0.1:3306";
            String uName = "root";
            String uPass = "poney";


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

            return ("I found this one : " + index);
        }
        catch (Exception e)
        {
            err_msg = "Error while connecting to DB : " + e.getMessage(); // + " Param was : " + mat + " && " + cla;
            System.out.println(err_msg);
            return (err_msg);
        }
    }

    @GET
    @Path("/{param}-{param2}")
    public Response getMsg(@PathParam("param") String mat, @PathParam("param2") String cla)
    {
        String output = "Im In";
        String matiere = "'" + mat + "'";
        String classe = "'" + cla + "'";

        String rep = Connect_to_Database(matiere, classe);
        return Response.status(200).entity(rep).build();
    }
}
