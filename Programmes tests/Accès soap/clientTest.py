from suds.client import Client
nomprojet = "servicesoap"
port = "8090"
urlCommandes = "http://localhost:"+port+"/"+nomprojet+"/services/Commandes?wsdl"
urlArticles = "http://localhost:"+port+"/"+nomprojet+"/services/Articles?wsdl"
urlFinances = "http://localhost:"+port+"/"+nomprojet+"/services/Finances?wsdl"
urlComptes = "http://localhost:"+port+"/"+nomprojet+"/services/Comptes?wsdl"

def r():
	client = Client(urlCommandes)
	print(client)
	client = Client(urlArticles)
	print(client)
	client = Client(urlFinances)
	print(client)
	client = Client(urlComptes)
	print(client)


def ajouterCompteClient(nom,prenom,mdp):
        client = Client(urlComptes)
        print(client.service.creerCompte(nom,prenom,mdp,True))
        
def ajouterCompteVendeur(nom,prenom,mdp):
        client = Client(urlComptes)
        print(client.service.creerCompte(nom,prenom,mdp,False))
        
def getComptesVendeur():
        client = Client(urlComptes)
        print(client.service.listeComptesVendeur())

def getComptesClient():
        client = Client(urlComptes)
        print(client.service.listeComptesClient())

def isPasswordOK(nom,prenom,mdp,isClient):
        client = Client(urlComptes)
        print(client.service.isCorrect(nom,prenom,mdp,isClient))

def getId(nom,prenom,isClient):
        client = Client(urlComptes)
        print(client.service.idPersonne(nom,prenom,isClient))

def creerProduit(nom,categorie,prix,idVendeur):
        client = Client(urlArticles)
        print(client.service.ajoutArticle(nom,categorie,prix,idVendeur))

def listerProduits():
        client = Client(urlArticles)
        print(client.service.getAllArticles())

def getAProduit(idd):
        client = Client(urlArticles)
        print(client.service.getArticle(idd))

def changerCategorieProd(idArticle,cate):
        client = Client(urlArticles)
        print(client.service.changerCatégorie(idArticle,cate))
        listerProduits()

def changerPrixProd(idArticle,prix):
        client = Client(urlArticles)
        print(client.service.changerPrix(idArticle,prix))
        listerProduits()

def ajoutStock(idArticle,nb):
        client = Client(urlArticles)
        print(client.service.ajoutStock(idArticle,nb))
        listerProduits()

def rmStock(idArticle,nb):
        client = Client(urlArticles)
        print(client.service.retirerStock(idArticle,nb))
        listerProduits()
# Commandes
def getCommandes():
        client = Client(urlCommandes)
        print(client.service.listeCommandes())
def getCommandesN(nom,prenom):
        client = Client(urlCommandes)
        print(client.service.commandesClient(nom,prenom))

def creerCommande(idClient):
        client = Client(urlCommandes)
        print(client.service.creerCommande(idClient))
        getCommandes()
def ajoutArticle(idCommande,idArticle,qte):
        client = Client(urlCommandes)
        print(client.service.ajoutArticle(idCommande,idArticle,qte))
        getCommandes()

def rmArticle(idCommande,idArticle,qte):
        client = Client(urlCommandes)
        print(client.service.retraitArticle(idCommande,idArticle,qte))
        getCommandes()

#Paiements
def payer(idcommande):
        client = Client(urlFinances)
        print(client.service.payerCommande(idcommande))
        getCommandes()

def rembourser(idcommande):
        client = Client(urlFinances)
        print(client.service.rembourserCommande(idcommande))
        getCommandes()
