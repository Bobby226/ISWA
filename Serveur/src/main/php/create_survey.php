<?php
/**
 * Created by IntelliJ IDEA.
 * User: Arthur
 * Date: 22/11/2017
 * Time: 13:57
 */

class create_survey
{
    public $op = array();

    function __construct()
    {
        $_data = file_get_contents(dirname(__FILE__) . "\body_data");
        file_put_contents(dirname(__FILE__) . "\generate_exam_enter.html", $_data);
    }

    function gen_meta_and_body ()
    {
        session_start();
        $url="http://localhost:8080/TestWS/test/test/f1/";

        $data = array('id' => '1');
        //$data = array('id' => $_SESSION["ID"]);

        // use key 'http' even if you send the request to https://...
        $options = array(
            'http' => array(
                'header'  => "Content-type: application/form-data\r\n",
                'method'  => 'POST',
                'content' => http_build_query($data)
            )
        );
        $context  = stream_context_create($options);
        $result = file_get_contents($url, false, $context);
        if ($result === FALSE)
        {
            echo 'Error while getting JsonResult {Create_servey.php}';
            return;
        }
        $json= json_decode($result, true);

        //file_put_contents(dirname(__FILE__) . "\generate_exam_enter.html", $json['enonce'], FILE_APPEND);
        $tmp = 0;
        foreach ($json as $item)
        {
            file_put_contents(dirname(__FILE__) . "\generate_exam_enter.html", $json['guest' . $tmp]['enonce'], FILE_APPEND);
            $this->setop( $json['guest' . $tmp]['operation']);
            foreach ($json['guest' . $tmp]['quest'] as $item2)
                file_put_contents(dirname(__FILE__) . "\generate_exam_enter.html", $item2, FILE_APPEND);
            $tmp++;
        }
       /*$quest = "<p>Q1 : 1 + 1 = <input type=\"text\" name=\"q1\"></p>\r\n";
       file_put_contents(dirname(__FILE__) . "\generate_exam_enter.html", $quest, FILE_APPEND);*/
    }

    function gen_end ()
    {
        $_data = file_get_contents(dirname(__FILE__) . "\last_data");
        file_put_contents(dirname(__FILE__) . "\generate_exam_enter.html", $_data, FILE_APPEND);
    }

    function setop($gtop)
    {
        array_push($this->op, $gtop);
    }

    function getop()
    {
        return ($this->op);
    }
}