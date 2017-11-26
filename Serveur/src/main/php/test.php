<?php
/**
 * Created by IntelliJ IDEA.
 * User: Arthur
 * Date: 25/11/2017
 * Time: 18:56
 */
if(isset($_POST['OK'])){// button name
    a();
}

function a() {
    //$xml = file_get_contents("http://localhost:8080/CoursWS/rest/cours/Mathematiques-Multiplication");
    echo 'Result : ' . $_POST['q1'];
    $d = array();
    $d[] = array('test' => 'ima');
    array_push($d, array('coucou' => 'cmabite'));
    print_r($d);
}

?>