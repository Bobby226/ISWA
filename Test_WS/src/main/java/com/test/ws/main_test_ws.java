package com.test.ws;

import com.usr.info.User;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.File;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;


@Path("/test")
public class main_test_ws
{
    static User as = new User();
    static String[][] linkSample ={
            { "1.1", "../ressources/1.2"  },
            { "../ressources/1.2" }
    };

    private Connection Connexion()
    {
        Connection con = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("ezpz");
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

    private void getUsr(Connection con, String id)
    {
        try
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE UserID=" + id);
            while (rs.next())
            {
                System.out.println("tet " + rs.getString("UserID"));
                as.ID = Integer.parseInt(rs.getString("UserID"));
                as.Classe = rs.getString("Classe");
            }
            rs.close();
            statement.close();
        }
        catch (SQLException e)
        {
            System.out.println("err : " + e.toString());
        }
    }

    private void getDiff(Connection con)
    {
        try
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user_level_per_subject WHERE UserID=" + as.ID);
            while (rs.next())
            {
                as.lvl[Integer.parseInt(rs.getString("SubjectID"))][Integer.parseInt(rs.getString("TopicID"))] = Integer.parseInt( rs.getString("UserLevel"));

                if (Integer.parseInt( rs.getString("UserLevel")) == -1)
                {
                    try
                    {
                        System.out.println("sub : " + Integer.parseInt(rs.getString("SubjectID")) + " || topic : " + Integer.parseInt(rs.getString("TopicID")));
                        //String tmp = this.getClass().getResource("tamer").toString();
                        //String content = new Scanner(new File(tmp)).useDelimiter("\\Z").next();
                        //System.out.println("Content : " + tmp);
                    }
                    catch (Exception e)
                    {
                        System.out.println("err while scanner : " + e.toString());
                    }
                }
                else
                {
                    // Check lvl for each.
                }
            }
            rs.close();
            statement.close();
        }
        catch (SQLException e)
        {
            System.out.println("err : " + e.toString());
        }
    }

    @POST
    @Path("/post")
    public Response postStrMsg(String msg) {
        String output = "POST:Jersey say : " + msg;

        return Response.status(200).entity(output).build();
    }

    @POST
    @Path("/f1")
    public Response getExamEntree(String id) {
        String output = "POST:Jersey say : ";
        id = id.replace("id=", "");
        System.out.println("ezpz : ");
        Connection con = Connexion();
        try
        {
            getUsr(con, id);
            getDiff(con);
            con.close();
        }
        catch (SQLException e)
        {
            System.out.println("err : " + e.toString());

            return Response.status(401).entity("Ko").build();
        }

        return Response.status(200).entity(output).build();
    }
}
