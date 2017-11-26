<?php
/**
 * Created by IntelliJ IDEA.
 * User: Arthur
 * Date: 26/11/2017
 * Time: 12:35
 */

function Redirect($url, $permanent = false)
{
    if (headers_sent() === false)
    {
        header('Location: ' . $url, true, ($permanent === true) ? 301 : 302);
    }

    exit();
}

$url="http://localhost:8080/TestWS/test/test/rd/";

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
    return;
}

if ($result == "f")
    Redirect('http://tamer:4242/src/main/php/phptest.php', false);
else
    Redirect('http://tamer:4242/Profil.html', false);

echo $result;