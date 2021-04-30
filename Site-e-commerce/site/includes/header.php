<?php

if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
?>

<h1>Site E-Commerce</h1>
<nav class="navbar navbar-expand-lg navbar-light menu">
	<div class="container-fluid">
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav">
				<li class="nav-item">
				  <a class="nav-link active" aria-current="page" href="index.php">Accueil</a>
				</li>
				<li class="nav-item">
				  <a class="nav-link" href="boutique.php">Boutique</a>
				</li>
                <?php
                    if(isset($_SESSION['nom']) && isset($_SESSION['prenom']) && isset($_SESSION['isClient']) && isset($_SESSION['id']) && isset($_SESSION['isClient'])){
                        if($_SESSION['isClient']==true){?>
                            <!-- afficher les accès pour le client -->
                            <li class="nav-item">
                                <a class="nav-link" href="panier.php">Panier</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="compte.php">Mon compte</a>
                            </li>
                         <?php }else{ ?>
                            <!-- afficher les accès pour le vendeur -->
                            <li class="nav-item">
                                <a class="nav-link">Gestion des produits</a>
                            </li>
                        <?php } ?>
                        <li class="nav-item">
                            <a class="nav-link" href="deconnecter.php">Déconnexion</a>
                        </li>
                <?php
                    }else{?>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Inscription</a>
                        </li>
                    <li class="nav-item">
                      <a class="nav-link" href="index.php">Connexion</a>
                    </li>
                    <?php }?>
			</ul>
		</div>
	</div>
</nav>