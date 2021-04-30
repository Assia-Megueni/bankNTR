<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
if (isset($_SESSION['nom']) && isset($_SESSION['prenom']) && isset($_SESSION['isClient']) && isset($_SESSION['id'])) {
    require("../includes/soap.php");
    /* créer le panier */
    $v = creerPanier($_SESSION['id']);
    if($v == true) {
        /* récupére la commande */
        $c = getCommandeClientToAddProducts($_SESSION['nom'],$_SESSION['prenom']);
        if($c != "") {
            /* ajouter les articles dedans */
            $arti = true;
            foreach ($_SESSION['panier'] as $key => $value) {
                $arti = $arti && ajouterProduit($c->id, $key, 1);
            }
            if($arti) {
                /* payer */
                $paye = payer($c->id);
                echo "La transaction s'est passée avec ".($paye?"Succès":"Echec");
                ?>
                <a href="boutique.php">Retour</a>
<?php
                unset($_SESSION['panier']);
            }else{
                echo "Une erreur s'est produite durant la transaction";
                ?>
                <a href="boutique.php">Retour</a>
                <?php
            }
        }else{
            echo "Une erreur s'est produite durant la récupération de la commande";
            ?>
            <a href="boutique.php">Retour</a>
            <?php
        }
    }else{
        echo "Une erreur s'est produite durant la création de la commande";
        ?>
        <a href="boutique.php">Retour</a>
    <?php
    }
}else {
    /* l'utilisateur non connecté est redirigé */
    header("Location: index.php");
}