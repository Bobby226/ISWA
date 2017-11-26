<!DOCTYPE HTML>

<html>
<head>
    <title>ISWA - LEÇON</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="studentSpace/assets/css/main.css" />
</head>
<body>

<!-- Page Wrapper -->
<div id="page-wrapper">

    <!-- Header -->
    <header id="header">
        <h1><a href="index.html">INTELLIGENT SCHOOL WEB ASSISTANT</a></h1>
        <nav id="nav">
            <ul>
                <li class="special">
                    <a href="#menu" class="menuToggle"><span>Menu</span></a>
                    <div id="menu">
                        <ul>
                            <img src="studentSpace/images/vincent.f.jpg" id="paraRoot" alt="" />
                            <p id="changePara3">Vincent FAPIENO</p>
                            <p id="changePara3">Classe: 6ème</p>
                            <hr />
                            <li><a href="dashboard.html">Tableau de bord</a></li>
                            <li><a href="classRoom.html">Salon de cours</a></li>
                            <li><a href="lecon.php">Leçon</a></li>
                            <li><a href="exercices.html">Exercices</a></li>
                            <li><a href="accountSetting.html">Gestion du compte</a></li>
                            <li><a href="#">Se déconnecter</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </nav>
    </header>
    <!-- Main -->
    <article id="main">
        <header>
            <h2>LEÇON</h2>
            <p></p>
        </header>
        <section class="wrapper style5">
            <div class="inner">
                <?php
                $url="http://localhost:8080/CoursWS/rest/cours/gc/";
                session_start();

                $id = $_SESSION['IdUser'];
                $data = array('id' => $id);
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
                    echo "Failed to get return from Leçon";
                    return;
                }
                ?>
            </div>
        </section>
    </article>

    <!-- Footer -->
    <footer id="footer">
        <ul class="icons">
        </ul>
        <ul class="copyright">
            <li>&copy; ISWA</li><li>INTELLIGENT SCHOOL WEB ASSISTANT</li>
        </ul>
    </footer>

</div>

<!-- Scripts -->
<script src="studentSpace/assets/js/jquery.min.js"></script>
<script src="studentSpace/assets/js/jquery.scrollex.min.js"></script>
<script src="studentSpace/assets/js/jquery.scrolly.min.js"></script>
<script src="studentSpace/assets/js/skel.min.js"></script>
<script src="studentSpace/assets/js/util.js"></script>
<script src="studentSpace/assets/js/main.js"></script>

</body>
</html>
