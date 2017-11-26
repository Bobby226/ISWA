package com.test.ws;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Calculate
{
    I_Calculate[] myArray = new I_Calculate[10];

    Calculate()
    {
        myArray[0] = new less_than();
        myArray[1] = new divide();
        myArray[2] = new add();

    }

    class less_than extends I_Calculate {
        public String make_calc(List<String> val)
        {
                        System.out.println("coucou");
            Integer[] all_num = new Integer[val.size()];
            int i = 0;
            for (String c : val)
            {
                all_num[i] = Integer.parseInt(c);
                i++;
            }
            Arrays.sort(all_num, new Comparator<Integer>()
            {
                @Override
                public int compare(Integer x, Integer y)
                {
                    return x - y;
                }
            });
            String rep = "";
            i = 0;
            while (i != all_num.length)
            {
                if (i == 0)
                    rep = rep + Integer.toString(all_num[i]) + "<";
                else if (i == 1)
                    rep = rep + Integer.toString(all_num[i]);
                else
                    rep = rep + "<" + Integer.toString(all_num[i]);
                i++;
            }
            System.out.println("rep : " + rep);
            return rep;
        }
    }

    class divide extends I_Calculate {
        public String make_calc(List<String> val)
        {
            try
            {
                String req = val.get(0);
                req = req.replace("[", "");
                req = req.replace("]", "");
                req = req.replace(" ", "");
                String[] new_req = req.split("\\(");
                int i = 0;
                while (i != new_req.length)
                {
                    new_req[i] = new_req[i].replace(")", "");
                    new_req[i] = new_req[i].replace("+", "");
                    i++;
                }

            }
            catch (Exception e)
            {
                System.out.println("RAAAH " + e.toString());
            }
            return null;

        }
    }

    class add extends I_Calculate {
        public String make_calc(List<String> val)
        {
            try
            {
                String req = val.get(0);
                req = req.replace(" ", "");
                int i = 0;
                ScriptEngineManager mgr = new ScriptEngineManager();
                ScriptEngine engine = mgr.getEngineByName("JavaScript");
                System.out.println(engine.eval(req));
                return engine.eval(req).toString();
            }
            catch (Exception e)
            {
                System.out.println("err : " + e.toString());
            }
            return (null);
        }
    }

}
