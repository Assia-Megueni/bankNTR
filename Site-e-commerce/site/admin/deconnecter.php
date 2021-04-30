<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

unset($_SESSION['nom']);unset($_SESSION['prenom']);
unset($_SESSION['id']);unset($_SESSION['isClient']);
unset($_SESSION['username']);


header("Location: index.php");