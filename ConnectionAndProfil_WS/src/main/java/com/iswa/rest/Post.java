package com.iswa.rest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("/post")
public class Post
{
    public Connection Connexion()
    {
        Connection con = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("JDBC driver est introuvable");
        }
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbiswa" ,"root", "root");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return (con);
    }

    @POST
    @Path("/connexion")
    public Response connexion(String Valus)
    {
        Connection con = Connexion();
        try
        {
            try
            {
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(Valus);
                String Email = json.get("Email").toString();
                String Mdp = json.get("Mdp").toString();
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM users");
                while (rs.next())
                {
                    if (Email.equals(rs.getString("email_adresse")) == true && Mdp.equals(rs.getString("password")) == true)
                    {
                        con.close();
                        return Response.status(200).entity(rs.getString("Id")).build();
                    }
                }
                rs.close();
                statement.close();
                con.close();
            }
            catch(org.json.simple.parser.ParseException t){}
        }
        catch (SQLException e) {}
        return Response.status(401).entity("Bad users infos").build();
    }

    @POST
    @Path("/CreationProfil/eleve")
    public Response CreationProfil(String Param)
    {
        Connection con = Connexion();
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(Param);
            String Email = json.get("Email").toString();
            String Mdp = json.get("Mdp").toString();
            String UserName = json.get("UserName").toString();
            String UserLastName = json.get("UserLastName").toString();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next())
            {
                if (Email.equals(rs.getString("email_adresse")) == true)
                {
                    con.close();
                    return Response.status(401).entity("ko").build();
                }
            }
            String query = "INSERT INTO `users`(`email_adresse`, `password`, `Nom`, `Prenom`) VALUES (";
            query += "'" + Email+"', '" + Mdp + "', '" + UserName + "', '" + UserLastName + "')";
            System.out.print(query);
            statement.executeUpdate(query);
            con.close();
        }
        catch(org.json.simple.parser.ParseException t){}catch (SQLException e) {System.out.print(e);}
        return Response.status(200).entity("ok").build();
    }
}
