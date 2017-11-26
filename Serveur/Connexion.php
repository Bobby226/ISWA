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
	$url = 'http://localhost:8080/Profil_WS/rest/post/connexion';
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
	header('Location: http://iswa.dev/Connection.html');
	exit();
}
else
{
	session_start();
	$_SESSION["IdUser"]=$value;
	header('Location: http://iswa.dev/redirect.php');
	exit();
}
?>
