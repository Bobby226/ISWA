<?php

$Email = $_POST['Email']; 
$Mdp = $_POST['Mdp'];

function connexion($UserMail, $UserMdp)
{ 
	error_reporting(0);
	$string = "{\"Email\":\"".$UserMail."\",\"Mdp\":\"".$UserMdp."\"}";
	$opts = array('http' =>
	  array(
		'method'  => 'POST',
		'header'  => "Content-Type: application/json",
		'content' => $string
	  )
	);
							
	$context  = stream_context_create($opts);
	$url = 'http://localhost:8080/ProfilWS/rest/post/connexion';
	$result = file_get_contents($url, false, $context);
	if ($result == false)
	{
		return("KO");
	}
	else
	{

        return($result);
	}
}

$value = connexion($Email, $Mdp);

if ($value == "KO")
{
	//header('Location: http://tamer:4242/Connection.html');
    echo "
            <script type=\"text/javascript\">
            alert('Mauvais Nom d\'utilisateur / mot de passe');
            window.location.href = \"Connection.html\";
            </script>
        ";
    exit();
}
else
{
    session_start();
	$_SESSION["IdUser"]=$value;
	header('Location: http://tamer:4242/redirect.php');
	exit();
}
?>
