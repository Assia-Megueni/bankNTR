<?php
require("../includes/soap.php");
session_start();

if(isset($_POST['submit'])){
	$nom = $_POST['nom'];
	$prenom = $_POST['prenom'];
	$password = $_POST['password'];
    $isClient = true;

	if ($nom&&$password&&$prenom) {
	    /* on suppose que c'est un client */
		if (isIdentOK($nom,$prenom,$password,true)) {
			$_SESSION['nom']=$nom;
			$_SESSION['prenom']=$prenom;
			/* TODO : chercher l'id du client en base */
            $_SESSION['id'] = getIdPersonne($nom,$prenom,$isClient);
            if($_SESSION['id'] == -1)die("Erreur durant la recherche d'id");
            $_SESSION['isClient'] = $isClient;
			/* Redirection vers une page différente du même dossier */
			$host  = $_SERVER['HTTP_HOST'];
			$uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
			$extra = 'boutique.php';
			header("Location: http://$host$uri/$extra");
			exit;
		}else if (isIdentOK($nom,$prenom,$password,false)) {
		    $isClient = false;
            $_SESSION['nom']=$nom;
            $_SESSION['prenom']=$prenom;
            /* TODO : chercher l'id du client en base */
            $_SESSION['id'] = getIdPersonne($nom,$prenom,$isClient);
            if($_SESSION['id'] == -1)die("Erreur durant la recherche d'id");
            $_SESSION['isClient'] = $isClient;
            /* Redirection vers une page différente du même dossier */
            $host  = $_SERVER['HTTP_HOST'];
            $uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
            $extra = 'boutique.php';
            header("Location: http://$host$uri/$extra");
            exit;
        }
		else{
			echo'Identifiants eronnees';
		}
	}
	else {
		echo'Veuillez remplir tous les champs !';
	}
}
?>
<!DOCTYPE html>
<html>
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	    <link href="../style/monstyle.css" type="text/css" rel="stylesheet"/>
	    <link href="../style/bootstrap.min.css" type="text/css" rel="stylesheet"/>
	</head>
    <body>
    	<h1>Administration - Connexion</h1>
    	<div class="container">
	    	<form action = "index.php" method="POST">
				<div class="row">
				  	<div class="col-md-4">
				    	<label for="username" class="form-label">Nom</label>
				    	<input type="text" class="form-control" id="nom" name="nom">
					</div>
				</div>
                <div class="row">
                    <div class="col-md-4">
                        <label for="username" class="form-label">Prénom</label>
                        <input type="text" class="form-control" id="prenom" name="prenom">
                    </div>
                </div>
				<div class="row">
					<div class="col-md-4">
						<label for="inputPassword" class="form-label">Mot de passe</label>
						<input type="password" class="form-control" id="inputPassword"name="password">
					</div>
				</div>
				<br>
				<div class="row">
				  	<div class="col-md-4">
				  		<button type="submit" class="btn btn-primary" name="submit">Valider</button>
				  	</div>
				</div>
			</form>
		</div>
    </body>
</html>
