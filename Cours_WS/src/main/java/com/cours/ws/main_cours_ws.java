package com.cours.ws;

import com.dbcommand.Connect;

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

    @GET
    @Path("/{param}-{param2}")
    public Response getMsg(@PathParam("param") String mat, @PathParam("param2") String cla)
    {
        String output = "Im In";
        String matiere = "'" + mat + "'";
        String classe = "'" + cla + "'";
        String subject = mat + '_' + cla;

        String rep = Connect.Connect_to_Database(matiere, classe, subject);
        return Response.status(200).entity(rep).build();
    }
}
