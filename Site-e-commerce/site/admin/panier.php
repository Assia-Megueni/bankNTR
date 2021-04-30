<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
require("../includes/soap.php");
if (isset($_SESSION['nom']) && isset($_SESSION['prenom']) && isset($_SESSION['isClient']) && isset($_SESSION['id'])) {
	if(!$_SESSION['isClient'])header("Location: boutique.php");
    if (isset($_GET['id'])) {
		$id = $_GET['id'];

		//Remove
		if (isset($_GET['action']) && $_GET['action']=='remove') {
			if (isset($_SESSION['panier'][$id])) {
				$_SESSION['panier'][$id] = 0;
				unset($_SESSION['panier'][$id]);

				$host  = $_SERVER['HTTP_HOST'];
				$uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
				$extra = 'panier.php';
				header("Location: http://$host$uri/$extra");
				exit;
			}
		}

		// Add
		if ($id) {
			if (!isset($_SESSION['panier'][$id])) {
				$_SESSION['panier'][$id] = 0;
			}
			$_SESSION['panier'][$id] = $_SESSION['panier'][$id] + 1;
		}
	}
}else{
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
					<h2>Mon panier</h2>
				</div>
				<div class="col-md-3 text-right" style="line-height: 43px;">
					<a href="boutique.php">Retouner à la boutique</a>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-12">
					<?php if (empty($_SESSION['panier'])) { ?>
						Oops ! Votre panier est vide.
					<?php 
					}
					else {
					    $prixTotal = 0;
						?>
						<table class="table">
							<thead>
								<th>Produit</th>
								<th>Quantité</th>
								<th>Prix unitaire</th>
                                <th>Prix total</th>
								<th></th>
							</thead>
							<tbody>
							<?php
							foreach ($_SESSION['panier'] as $key => $value) {
                                $article = getArticle($key);
                                if($article != null) {
                                    ?>
                                    <tr>
                                        <td>
                                            <b><?= $article->nom; ?></b>
                                            <br>
                                            <?php echo $value['description']; ?>
                                        </td>
                                        <td><?= $value ?></td>
                                        <td><?= $article->prix; ?> Euros</td>
                                        <td><?= $article->prix * $value; ?> Euros</td>
                                        <td>
                                            <a href="panier.php?action=remove&id=<?php echo $key; ?>">Supprimer</a>
                                        </td>
                                    </tr>
                                    <?php
                                    $prixTotal += $article->prix * $value;
                                }
							}
							?>
							<tr>
								<td colspan="2"><b>Prix total :</b></td>
								<td><?php echo $prixTotal; $_SESSION['prix'] = $prixTotal;?> Euros</td><td></td><td></td>
							</tr>
							</tbody>
						</table>
                        <br>
                        <a href="payer.php">Payer</a>
						<?php
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