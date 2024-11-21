<%-- 
    Document   : formulaireInsertCSV.jsp
    Created on : 20 nov. 2024, 22:15:41
    Author     : Mirado
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Header with Navbar -->
    <%@include file="header.jsp" %>

    <!-- Main Content -->
    <main class="container">
        <form action="afficherAnnee" method="get">
            <h1>Choisissez une annee</h1>
            <input type="number" name="year">
            <input class="btn btn-primary" type="submit" value="Valider">
        </form>
    </main>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>