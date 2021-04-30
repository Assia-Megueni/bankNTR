<?php

if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

/*
 * TODO
try
{
	$db = new PDO('mysql:host=localhost;dbname=site','root','');
	$db->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER); //les noms de chmaps seront en caratcéres minuscules
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); //les erreurs lanceront des exeptions
}
catch(Exception $e) {
	echo 'une erreure est survenue';
	die();
}
*/

if (isset($_SESSION['nom']) && isset($_SESSION['prenom']) && isset($_SESSION['isClient']) && isset($_SESSION['id']) && isset($_SESSION['isClient'])){
	if (isset($_GET['action'])) {
		if($_GET['action']=='add'){

			if(isset($_POST['submit'])) {
				$title=$_POST['title'];
				$description=$_POST['description'];
				$price=$_POST['price'];

				if ($title && $description && $price) {
				    /* TODO
					try
					{
						$db = new PDO('mysql:host=localhost;dbname=site','root','');
						$db->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER); //les noms de chmaps en caratcéres minuscules
						$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); //les erreurs lanceront des exeptions
					}
					catch(Exception $e){
						echo 'une erreure est survenue';
						die();
					}*/

					$insert=$db->prepare("INSERT INTO products VALUES('','$title','$description','$price')");
					$insert->execute();

					$host  = $_SERVER['HTTP_HOST'];
					$uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
					$extra = 'admin.php';
					header("Location: http://$host$uri/$extra");
					exit;

				}
				else {
					echo'Veuillez remplir tous les champs';
				}
			}

		} 
		else if($_GET['action']=='modify'){
		    /* TODO
			try
			{
				$db = new PDO('mysql:host=localhost;dbname=site','root','');
				$db->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER); //les noms de chmaps en caratcéres minuscules
				$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); //les erreurs lanceront des exeptions
			}
			catch(Exception $e){
				echo 'une erreure est survenue';
				die();
			}*/
			$id = $_GET['id'];
			$select=$db->prepare("SELECT * FROM products where id = $id");
			$select->execute();

			$produit = $select->fetchAll();

			if(isset($_POST['submitSave'])) {
				$title=$_POST['title'];
				$description=$_POST['description'];
				$price=$_POST['price'];

				if ($title && $description && $price) {
					try
					{
						$db = new PDO('mysql:host=localhost;dbname=site','root','');
						$db->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER); //les noms de chmaps en caratcéres minuscules
						$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); //les erreurs lanceront des exeptions
					}
					catch(Exception $e){
						echo 'une erreure est survenue';
						die();
					}
					$id = $_GET['id'];
					$update=$db->prepare("UPDATE products SET title = '$title', description = '$description', price = '$price' WHERE id = $id");
					$update->execute();

					$host  = $_SERVER['HTTP_HOST'];
					$uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
					$extra = 'admin.php';
					header("Location: http://$host$uri/$extra");
					exit;

				}
				else {
					echo'Veuillez remplir tous les champs';
				}
			}
		}
		else if($_GET['action']=='delete') {
			try
			{
				$db = new PDO('mysql:host=localhost;dbname=site','root','');
				$db->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER); //les noms de chmaps en caratcéres minuscules
				$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); //les erreurs lanceront des exeptions
			}
			catch(Exception $e){
				echo 'une erreure est survenue';
				die();
			}
			$id = $_GET['id'];
			$select=$db->prepare("SELECT * FROM products where id = $id");
			$select->execute();

			$produit = $select->fetchAll();
			try
			{
				$db = new PDO('mysql:host=localhost;dbname=site','root','');
				$db->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER); //les noms de chmaps en caratcéres minuscules
				$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); //les erreurs lanceront des exeptions
			}
			catch(Exception $e){
				echo 'une erreure est survenue';
				die();
			}
			$id = $_GET['id'];
			$update=$db->prepare("DELETE FROM products WHERE id = $id");
			$update->execute();

			$host  = $_SERVER['HTTP_HOST'];
			$uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
			$extra = 'admin.php';
			header("Location: http://$host$uri/$extra");
			exit;
		}
		else {
			die('Une erreur s\'est produite.');
		}
	}
	else {
		try
		{
			$db = new PDO('mysql:host=localhost;dbname=site','root','');
			$db->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER); //les noms de chmaps seront en caratcéres minuscules
			$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); //les erreurs lanceront des exeptions
		}
		catch (Exception $e) {
			echo 'une erreure est survenue';
			die();
		}

		if (isset($_SESSION['username'])) {
			try
			{
				$db = new PDO('mysql:host=localhost;dbname=site','root','');
				$db->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER); //les noms de chmaps en caratcéres minuscules
				$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION); //les erreurs lanceront des exeptions
			}
			catch(Exception $e) {
				echo 'une erreure est survenue';
				die();
			}

			$select=$db->prepare("SELECT * FROM products");
			$select->execute();
			$result = $select->fetchAll();
		}
		else {
			die('Une erreur s\'est produite.');
		}
	}
}else {
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
    	<body>
    	<?php 
		require_once('../includes/header.php');
		?>
		<div class="container">
			<h1>Bienvenue, <?php echo $_SESSION['username']; ?></h1>
			<br/>
			<?php
				if (!isset($_GET['action'])) {
					?>
					<div class="container">
						<div class="row">
							<div class="col-md-9">
								<h2>Liste des produits</h2>
							</div>
							<div class="col-md-3 text-right" style="line-height: 43px;">
								<a href="admin.php?action=add">Ajouter un produit</a>
							</div>
						</div>
			
						<div class="row">
							<div class="col-md-12">
								<?php if (empty($result)) { ?>
									Aucun produit a afficher !
								<?php 
								}
								else { 
								?>
								<table class="table">
						  			<thead class="thead-dark">
						    			<tr>
						    				<th>Nom</th>
						    				<th>Description</th>
						    				<th>Prix</th>
						    				<th colspan="2">Action</th>
						    			</tr>
						    		</thead>
						    		<body>
						    			<?php
						    			foreach ($result as $key => $value) {
						    				?>
						    				<tr>
							    				<td><?php echo $value['title']; ?></td>
							    				<td><?php echo $value['description']; ?></td>
							    				<td><?php echo $value['price']; ?></td>
							    				<td><a href="admin.php?action=modify&id=<?php echo $value['id']?>">Modifier</td>
							    				<td><a href="admin.php?action=delete&id=<?php echo $value['id']?>">Supprimer</td>
							    			</tr>
						    				<?php
						    			}
						    			?>
						    		</body>	
								</table>
								<?php 
								}
								?>
							</div>
						</div>
					</div>
					<?php
				}
				if(isset($_GET['action']) && $_GET['action']=='add') {
			?>
				<h3>Ajouter un produit</h3>
				<form method="post">
					<div class="row">
						<div class="col-md-8">Titre du produit :</div>
					</div>
					<div class="row">
						<div class="col-md-8"><input type="text" name="title"/></div>
					</div>

					<div class="row">
						<div class="col-md-8">Description du produit :</div>
					</div>
					<div class="row">
						<div class="col-md-8"><input type="text" name="description"/></div>
					</div>

					<div class="row">
						<div class="col-md-8">Prix du produit :</div>
					</div>
					<div class="row">
						<div class="col-md-8"><input type="text" name="price"/></div>
					</div>
					<br>
					<div class="row">
						<div class="col-md-8">
							<input type="submit" name="submit" value="Enregistrer"/>
						</div>
					</div>
				</form>
			<?php
				}
				elseif (isset($_GET['action']) && $_GET['action'] == 'modify') {
				 	?>
				 	<h3>Modifier un produit</h3>
					<form method="post">
						<div class="row">
							<div class="col-md-8">Titre du produit :</div>
						</div>
						<div class="row">
							<div class="col-md-8"><input type="text" name="title" value="<?php echo $produit[0]['title']; ?>"/></div>
						</div>

						<div class="row">
							<div class="col-md-8">Description du produit :</div>
						</div>
						<div class="row">
							<div class="col-md-8"><input type="text" name="description" value="<?php echo $produit[0]['description']; ?>"/></div>
						</div>

						<div class="row">
							<div class="col-md-8">Prix du produit :</div>
						</div>
						<div class="row">
							<div class="col-md-8"><input type="text" name="price" value="<?php echo $produit[0]['price']; ?>"/></div>
						</div>
						<br>
						<div class="row">
							<div class="col-md-8">
								<input type="submit" name="submitSave" value="Modifier"/>
							</div>
						</div>
					</form>
				 	<?php
				} 
			?>
			<br>
		</div>
		<?php
		require_once('../includes/footer.php');
		?>
	</body>
</html>