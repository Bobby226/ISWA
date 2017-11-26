package com.test.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usr.info.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.*;
import java.sql.*;
import java.util.*;


@Path("/test")
public class main_test_ws
{
    static User as = new User();
    static String[][] linkSample ={
            { "1.1.json", "1.3.json"  },
            { "../ressources/1.2" }
    };
    static String[] ope = {
            "<",
            "/",
            "+"
    };
    static List<String> g_rep = new ArrayList<String>();
    Connection con = Connexion();

    static JSONObject obj2;

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

    private void calc_rep(List<String> val, int operation)
    {
        Calculate C_ca = new Calculate();

        String rep = C_ca.myArray[operation].make_calc(val);
        //for (String c : rep)
        g_rep.add(rep);
    }


    private void genExam(Connection con)
    {
        try
        {
            Statement statement = con.createStatement();
            obj2 = new JSONObject();
            ResultSet rs = statement.executeQuery("SELECT * FROM user_level_per_subject WHERE UserID=" + as.ID);
            int tamer = 0;
            int num_q = 1;
            while (rs.next())
            {
                as.lvl[Integer.parseInt(rs.getString("SubjectID"))][Integer.parseInt(rs.getString("TopicID"))] = Integer.parseInt( rs.getString("UserLevel"));
                String fckyou = "guest" + tamer;
                if (Integer.parseInt( rs.getString("UserLevel")) == -1)
                {
                    try
                    {
                        // get content from file {result} //

                        StringBuilder result = new StringBuilder("");
                        System.out.println("sub : " + Integer.parseInt(rs.getString("SubjectID")) + " || topic : " + Integer.parseInt(rs.getString("TopicID")));
                        //String tmp = this.getClass().getResource(linkSample[0][0]).toString();
                        ClassLoader classLoader = getClass().getClassLoader();
                        System.out.println("echo : " + tamer);
                        File file = new File(classLoader.getResource(linkSample[0][tamer]).getFile());
                        tamer++;
                        JSONParser parser = new JSONParser();

                        //File file = new File(classLoader.getResource(linkSample[Integer.parseInt(rs.getString("SubjectID")) - 1][Integer.parseInt(rs.getString("SubjectID")) - 1]).getFile());
                        Object obj = parser.parse(new FileReader(file));

                        JSONObject jsonObject = (JSONObject) obj;

                        //System.out.println(jsonObject);

                        // get string from Json //

                        String enonce = (String) jsonObject.get("enonce");
                        int op = Integer.parseInt((String) jsonObject.get("operation"));
                        String max = (String) jsonObject.get("max");
                        String min = (String) jsonObject.get("min");

                        System.out.println("test ");
                        List<String> test = new ArrayList<String>();
                        System.out.println("test ");

                        JSONArray msg = (JSONArray) jsonObject.get("sample");
                        Iterator<String> iterator = msg.iterator();
                        while (iterator.hasNext())
                        {
                            test.add(iterator.next());
                            //System.out.println("sample : " + iterator.next());
                        }
                        System.out.println("test ");

                        // get string from Json //

                        /*
                            GetFewSample
                         */
                        System.out.println("test ");

                        List<Integer> nbrs = new ArrayList<Integer>();
                        List<Integer> tmp = new ArrayList<Integer>();
                        Random rand = new Random();
                        tmp.add(-1);
                        int nombreAleatoire = -1;
                        for (int i = 0; i != (test.size()/2); i++)
                        {
                            while (tmp.contains(nombreAleatoire))
                                nombreAleatoire = rand.nextInt(test.size());
                            nbrs.add(nombreAleatoire);
                            tmp.add(nombreAleatoire);
                        }
                        /*
                            GetFewSample
                         */

                        /*
                            GenValue
                         */
                        System.out.println("test ");

                        List<String> allreq = new ArrayList<String>();
                        int count = 0;
                        List<String> val;
                        while (count != nbrs.size())
                        {
                            val = new ArrayList<String>();
                            String req = test.get(nbrs.get(count));
                            count++;
                            String new_req = "<p>Q" + num_q + " :";
                            String[] req_split = req.split(" ");
                            int n = 0;
                            boolean samegave = false;
                            String vac = "";
                            for (String c : req_split)
                            {
                                if (op == 0)
                                {
                                    String value = Integer.toString(rand.nextInt(Integer.parseInt(max) - Integer.parseInt(min)));
                                    if (value.equals("0"))
                                        value = Integer.toString(rand.nextInt(Integer.parseInt(max) - Integer.parseInt(min)));
                                    val.add(value);

                                    new_req += " " + value;
                                    //System.out.println("I got " + c);
                                    n++;
                                }
                                else
                                {

                                    samegave = true;
                                    if (c.matches(".*[a-z].*"))
                                    {
                                        String value = Integer.toString(rand.nextInt(Integer.parseInt(max) - Integer.parseInt(min)));
                                        if (value.equals("0"))
                                            value = Integer.toString(rand.nextInt(Integer.parseInt(max) - Integer.parseInt(min)));
                                        new_req += " " + value;
                                        vac += value;
                                    }
                                    else
                                    {
                                        new_req += " " + c;
                                        vac += c;
                                    }

                                }

                            }

                            if (samegave)
                                val.add(vac);

                            calc_rep(val, op);

                            new_req += "<input type=\"text\" name=\"q" + num_q + "\"></p>";

                            num_q++;

                            allreq.add(new_req);
                        }

                        /*
                            GenValue
                         */

                        System.out.println(enonce);
                        int x = 0;
                        for (String s : allreq)
                        {
                            System.out.println("Q" + x + " : " + s);
                            x++;
                        }

                        System.out.println("test ");


                        JSONObject main = new JSONObject();
                        main.put("enonce", enonce);
                        main.put("operation", op);
                        JSONArray list = new JSONArray();
                        list.addAll(allreq);
                        main.put("quest", list);
                        main.put("topicID", Integer.parseInt(rs.getString("TopicID")));
                        main.put("subjectID", Integer.parseInt(rs.getString("SubjectID")));

                        JSONArray t1 = new JSONArray();
                        obj2.put(fckyou, main);


                        //System.out.println("Obj : " + main.toString());

                        // no need to close it.
                            //bw.close();
                        /*Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine())
                        {
                            String line = scanner.nextLine();
                            result.append(line).append("\n");
                        }
                        scanner.close();*/

                        // get content from file {result} //



                        //String content = new Scanner(new File("C:\\Users\\Arthur\\Documents\\ISWA\\ISWA\\Serveur\\src\\main\\php\\last_data"));
                        //System.out.println("Content : " + result);
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

    private void get_calc(JSONObject ob)
    {
        Object rep_obj = ob.get("0");
        Object operation = ob.get("operation");
        JSONObject rep = (JSONObject) rep_obj;
        JSONObject op = (JSONObject) operation;
        System.out.println("Je suis passé par là : " + rep.size());

        int i = 0;
        int obj_curs = 0;
        int result = 0;
        int count = 0;
        String req = "";
        String req1 = "";
        while (i != rep.size())
        {
            req = "q" + (i + 1);
            req1 = "guest" + obj_curs;
            String tmp = rep.get(req).toString();
            tmp = tmp.replace(" ", "");
            if (tmp.equals(g_rep.get(i)))
            {
                result++;
                System.out.println("T tro for");
            }
            else
            {
                System.out.println("Tu pu la merd : " + g_rep.get(i));
            }
            if (count == 2)
            {
                try
                {
                    Statement statement = con.createStatement();
                    Object gen = obj2.get(req1);
                    JSONObject allg = (JSONObject) gen;
                    String tpic = allg.get("topicID").toString();
                    String sbjectid = allg.get("subjectID").toString();
                    result = result * 33;
                    if (result == 99)
                        result = 100;
                    int rs = statement.executeUpdate("UPDATE `user_level_per_subject` SET `UserID`=" + as.ID + ",`SubjectID`=" + sbjectid + ",`TopicID`=" + tpic + ",`UserLevel`=" + result + " WHERE `UserID`=" + as.ID +  " AND `SubjectID`=" + sbjectid + " AND `TopicID`=" + tpic);
                    System.out.println("tpid " + tpic + " || sbjecitd " + sbjectid);
                    System.out.println("fck " + obj2.get("guest1"));
                    result = 0;
                    obj_curs++;
                    count = -1;

                }
                catch (Exception e)
                {
                    System.out.println("Err : " + e.toString());
                }
            }
            i++;
            count++;
        }
        g_rep = new ArrayList<String>();
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
        con = Connexion();
        try
        {
            getUsr(con, id);
            genExam(con);
            System.out.println("Obj : " + obj2.toString());

            con.close();
        }
        catch (SQLException e)
        {
            System.out.println("err : " + e.toString());

            return Response.status(401).entity("Ko").build();
        }

        return Response.status(200).entity(obj2).build();
    }

    @POST
    @Path("/cr")
    public Response correctExam(String obj)
    {
        String output = "ok";

        try
        {
            /*ObjectMapper mapper = new ObjectMapper();
            HashMap value = mapper.readValue(obj, HashMap.class);
            System.out.println("tasoeur " + value);
            JSONObject ob = new JSONObject(value);

*/
            JSONParser parser = new JSONParser();
            Object ob = parser.parse(obj);
            JSONObject jsonObject = (JSONObject) ob;

            get_calc(jsonObject);
        } catch (Exception e)
        {
            System.out.println("err : " + e.toString());
        }

        return Response.status(200).entity(output).build();
    }

        @POST
        @Path("/rd")
        public Response checkforfirstco(String id)
        {
            String output = "ok";

            try
            {
                if (con == null)
                    con = Connexion();
                id = id.replace("id=", "");
                getUsr(con, id);
                Statement statement = con.createStatement();
                obj2 = new JSONObject();
                ResultSet rs = statement.executeQuery("SELECT * FROM user_level_per_subject WHERE UserID=" + as.ID);
                while (rs.next())
                {
                    as.lvl[Integer.parseInt(rs.getString("SubjectID"))][Integer.parseInt(rs.getString("TopicID"))] = Integer.parseInt(rs.getString("UserLevel"));
                    if (Integer.parseInt(rs.getString("UserLevel")) == -1)
                    {
                        output = "f";
                        return Response.status(200).entity(output).build();
                    }
                }
                output = "n";
                return Response.status(200).entity(output).build();
            }
            catch (Exception e)
            {
                System.out.println("err : " + e.toString());
            }

            return Response.status(200).entity(output).build();
    }
}
