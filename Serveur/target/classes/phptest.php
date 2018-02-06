<?php
require 'create_survey.php';
require 'correct_survey.php';

function Redirect($url, $permanent = false)
{
    if (headers_sent() === false)
    {
        header('Location: ' . $url, true, ($permanent === true) ? 301 : 302);
    }

    exit();
}
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

$_SESSION['letsgen'] = $letsgen;

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
        {
            if (!ctype_digit($value))
            {
                echo "
                <script type=\"text/javascript\">
                alert('Wrong char');
                window.location.href = \"phptest.php\";
                </script>
                ";
                return;
            }

            else
                $arr1[$key] = $value;

        }
        echo($key . " was " . $value);
    }
    array_push($arr, $arr1);

        // print_r($arr);
    $post_data = json_encode($arr, JSON_FORCE_OBJECT);
    $cor = new correct_survey();
    $cor->correction($post_data);
    echo 'Result : ' . $_POST['q1'];
    Redirect('http://iswa:4242/dashboard.html', false);
}

?>
