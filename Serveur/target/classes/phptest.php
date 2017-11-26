<?php
require 'create_survey.php';

file_put_contents("generate_exam_enter.html", "");
/*echo $_SESSION["ID"];
$url="http://localhost:8080/TestWS/test/test/post/";

$data = array('msg' => 'bonjour');

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
if ($result === FALSE) { }

echo $result;*/

/*if (isset ($_GET['matiere']))
{
    $matiere = $_GET['matiere'];
    if (isset ($_GET['classe']))
    {
        $classe = $_GET['classe'];
        $response = file_get_contents('http://localhost:8080/TestWS/test/test/' . $matiere . '-' . $classe);
    }
}*/

$letsgen = new create_survey();
// Add question here
$letsgen->gen_meta_and_body();
$letsgen->gen_end();
readfile(dirname(__FILE__) . "\generate_exam_enter.html");

?>


<?php

if(isset($_POST['OK'])){ // button name
    a();
}

function a() {
    //$xml = file_get_contents("http://localhost:8080/CoursWS/rest/cours/Mathematiques-Multiplication");
    echo 'Imatest';

}

?>