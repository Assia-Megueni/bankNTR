<?php
    /* permet de normaliser le json */
    function convertJson($str){
        $str = str_replace("idPersonne",'"idPersonne"',$str);
        $str = str_replace("{nom",'{"nom"',$str);
        $str = str_replace("prenom",'"prenom"',$str);
        $str = str_replace("'",'"',$str);
        return $str;
    }
    /* retourne l'ensemble des produits en JSON */
    function getAllProducts(){
        try{
            $clientSOAP = new SoapClient("http://localhost:8090/servicesoap/services/Articles?wsdl");
            //return $clientSOAP->getAllArticles();
            $r = $clientSOAP->getAllArticles();
            $element = "";
            foreach ($r as $re){
                $element = explode("},{",str_replace(array("[","]"),"",$re));
            }
            $nb = sizeof($element);
            for($i = 0 ; $i <  $nb; $i++) {
                $element[$i] = ($i!=0?"{":"").$element[$i].($i<($nb-1)?"}":"");
                $element[$i] = json_decode(convertJson($element[$i]));
            }
            return $element;
        }catch (Exception $e){
            die("Une erreur s'est produite durant la création de l'accès SOAP");
        }
    }

    /**
     * Permet de vérifier si le mot de passe est bon lors d'une connexion
     * @param $nom
     * @param $prenom
     * @param $mdp
     * @return bool
     */
    function isIdentOK($nom,$prenom,$mdp,$isClient = true){
        try {
            $clientSOAP = new SoapClient("http://localhost:8090/servicesoap/services/Comptes?wsdl");
            $r = $clientSOAP->isCorrect(array('nom'=>$nom,'prenom'=>$prenom,'motdepasse'=>$mdp,'isClient'=>$isClient));
            return $r->isCorrectReturn;
        }catch (Exception $e){
            die("Une erreur s'est produite durant l'accès aux comptes");
        }
    }

    /**
     * Permet de récupérer l'identifiant d'une personne
     * @param $nom
     * @param $prenom
     * @param bool $isClient
     * @return int
     */
    function getIdPersonne($nom,$prenom,$isClient = true){
        try {
            $clientSOAP = new SoapClient("http://localhost:8090/servicesoap/services/Comptes?wsdl");
            $functions = $clientSOAP->__getFunctions ();
            var_dump ($functions);
            $r = $clientSOAP->idPersonne(array('nom'=>$nom,'prenom'=>$prenom,'isClient'=>$isClient));
            return $r->idPersonneReturn;
        }catch (Exception $e){
            return 13;
        }
    }

    /**
     * Permet de vérifier si le mot de passe est bon lors d'une connexion
     * @param $nom
     * @param $prenom
     * @param $mdp
     * @return bool
     */
    function getArticle($id){
        try {
            $clientSOAP = new SoapClient("http://localhost:8090/servicesoap/services/Articles?wsdl");
            $r = $clientSOAP->getArticle(array('id'=>$id));
            return json_decode(convertJson($r->getArticleReturn));
        }catch (Exception $e){
            die("Une erreur s'est produite durant l'accès aux articles");
        }
    }

/**
 * Permet de créer un panier
 * @param $idC
 * @return mixed
 */
    function creerPanier($idC){
        try {
            $clientSOAP = new SoapClient("http://localhost:8090/servicesoap/services/Commandes?wsdl");
            $r = $clientSOAP->creerCommande(array('idClient'=>$idC));
            return $r->creerCommandeReturn;
        }catch (Exception $e){
            echo $e;
            die("Une erreur s'est produite durant la création du panier");
        }
    }

/**
 * Permet d'obtenir la commande d'un client pour après ajouter les articles dedans
 * @param $nom
 * @param $prenom
 * @return mixed|string
 */
    function getCommandeClientToAddProducts($nom, $prenom){
        try {
            $clientSOAP = new SoapClient("http://localhost:8090/servicesoap/services/Commandes?wsdl");
            $r = $clientSOAP->commandesClient(array('nom'=>$nom,'prenom'=>$prenom));
            $element = "";
            foreach ($r as $re){
                $element = explode(']},{"',$re);
            }
            $nb = sizeof($element);
            for($i = 0 ; $i <  $nb; $i++) {
                if($i == 0)$element[$i] = str_replace(array('[{"'),"",$element[$i] );
                $element[$i] = "{'" . $element[$i];
                /* retrait de la partie items */
                $positionItem = strpos($element[$i],"items");
                $element[$i] = substr($element[$i],0,$positionItem - 2) . "}";
                /* */
                $element[$i] = json_decode(convertJson($element[$i]));
                if((!$element[$i]->estRembourse)&&(!$element[$i]->estPaye))return $element[$i];
            }
            return "";
        }catch (Exception $e){
            echo $e;
            die("Une erreur s'est produite durant la création du panier");
        }
    }

/**
 * Ajoute un produit à une commande
 * @param $idcommande
 * @param $idprod
 * @param $nb
 * @return mixed
 */
    function ajouterProduit($idcommande,$idprod, $nb){
        try {
            $clientSOAP = new SoapClient("http://localhost:8090/servicesoap/services/Commandes?wsdl");
            $r = $clientSOAP->ajoutArticle(array('idcommande'=>$idcommande,
                'idarticle'=>$idprod,
                'quantité'=>$nb));
            return $r->ajoutArticleReturn;
        }catch (Exception $e){
            return false;
        }
    }

/**
 * Payer une commande d'identifiant idcommande
 * @param $idcommande
 * @return bool
 */
    function payer($idcommande){
        try {
            $clientSOAP = new SoapClient("http://localhost:8090/servicesoap/services/Finances?wsdl");
            $r = $clientSOAP->payerCommande(array('idCommande'=>$idcommande));
            return $r->payerCommandeReturn;
        }catch (Exception $e){
            return false;
        }
    }