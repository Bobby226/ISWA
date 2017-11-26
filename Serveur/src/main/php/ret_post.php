<?php
/**
 * Created by IntelliJ IDEA.
 * User: Arthur
 * Date: 26/11/2017
 * Time: 05:53
 */
require 'create_survey.php';
require 'correct_survey.php';
session_start();

$letsgen = $_SESSION['letsgen'];

if(isset($_POST['OK'])){// button name
    a($letsgen->getop());
}

function a($op) {
    //$xml = file_get_contents("http://localhost:8080/CoursWS/rest/cours/Mathematiques-Multiplication");
    $arr = array("operation" => $op);
    $arr1 = array();

    foreach ($_POST as $key => $value)
    {
        if ($key != 'OK')
            $arr1[$key] = $value;
        echo($key . " was " . $value);
    }
    array_push($arr, $arr1);

    // print_r($arr);
    $post_data = json_encode($arr, JSON_FORCE_OBJECT);
    $cor = new correct_survey();
    $cor->correction($post_data);
    echo 'Result : ' . $_POST['q1'];

}