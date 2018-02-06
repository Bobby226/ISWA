<?php
/**
 * Created by IntelliJ IDEA.
 * User: Arthur
 * Date: 25/11/2017
 * Time: 18:59
 */

class correct_survey
{
    function sendRequest($data,$url)
    {
        $postdata = http_build_query(array('obj'=>$data));
        $opts = array('http' =>
            array(
                'method'  => 'POST',
                'header'  => "Content-type: application/json \r\n",
                //"X-Requested-With: XMLHttpRequest \r\n".
                //"curl/7.9.8 (i686-pc-linux-gnu) libcurl 7.9.8 (OpenSSL 0.9.6b) (ipv6 enabled)\r\n",
                'content' => $postdata,
            )
        );
        $context  = stream_context_create($opts);
        return file_get_contents($url, false, $context);
    }

    function correction($post_data)
    {

        $url="http://localhost:8080/TestWS/test/test/cr/";

        $curl = curl_init($url);
        curl_setopt($curl, CURLOPT_HEADER, false);
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);

        curl_setopt($curl, CURLOPT_POST, true);
        curl_setopt($curl, CURLOPT_POSTFIELDS, $post_data);

        $json_response = curl_exec($curl);
    }
}