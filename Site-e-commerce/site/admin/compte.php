<?php
require("../includes/soap.php");
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

if (isset($_SESSION['nom']) && isset($_SESSION['prenom']) && isset($_SESSION['isClient']) && isset($_SESSION['id']) && isset($_SESSION['isClient'])) {
    /* recherche de tous les produits */
    if(!$_SESSION['isClient'])header("Location: index.php");
}
else {
    /* l'utilisateur non connecté est redirigé */
    header("Location: index.php");
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
    	<?php 
		require_once('../includes/header.php');
		?>
		<div class="container">
			<div class="row">
				<div class="col-md-9">
					<h2>Compte de <?= $_SESSION['prenom'].' '.$_SESSION['nom']?>
                    </h2>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-12">

				</div>
			</div>
		</div>
		<?php
		require_once('../includes/footer.php');
		?>
	</body>
</html>