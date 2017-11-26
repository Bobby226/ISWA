package com.cours.ws;

import com.dbcommand.Connect;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Path("/cours")
public class main_cours_ws
{
    private String err_msg = "";
   // static String[][][] linkSample ={ "6" { }, "5", "4", "3", "2", "1"};

    @POST
    @Path("/gc")
    public Response getMsg(String id)
    {
        List<String[]> rep = Connect.getCours_to_Database(id);
        for (String[] a: rep)
        {
            for (String c : a)
            {
                System.out.println("mat in pls : " + a);
            }
        }
        return Response.status(200).entity(rep).build();
    }
}
