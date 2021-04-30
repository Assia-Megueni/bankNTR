<?php
require("../includes/soap.php");
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

if (isset($_SESSION['nom']) && isset($_SESSION['prenom']) && isset($_SESSION['isClient']) && isset($_SESSION['id'])) {
    /* recherche de tous les produits */
    $result = getAllProducts();
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
					<h2>Boutique</h2>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-12">
					<?php if (empty($result)) { ?>
						Aucun produit a afficher !
					<?php 
					}
					else {
						$i = 0;
						$nb = sizeof($result);
						foreach ($result as $value) {
							if($i === 0)
					        {       
					        	?><div class="row"><?php  
					        }
							?>
								<div class="col-md-3">
									<div class="card" style="width: 200px;">
										<img class="card-img-top" src="../assets/items/<?=$value->id?>.jpg" alt="Produit">
									  	<div class="card-body">
									  		<h5 class="card-title"><?php echo $value->nom; ?></h5>
									    	<p class="card-text"><?php echo $value->categorie; ?></p>
									    	<p class="card-text"><b><?php echo $value->prix; ?> Euros</b></p>
									    	<a href="panier.php?id=<?php echo $value->id; ?>">Ajouter au panier</a>
									  	</div>
									</div>
								</div>
							<?php
							$i++;
							if ($i == 3) {
								?></div><?php
								$i = 0;
							}
						} 
					}
					?>
				</div>
			</div>
		</div>
		<?php
		require_once('../includes/footer.php');
		?>
	</body>
</html>