<%-- 
    Document   : formulaireInsertCSV.jsp
    Created on : 20 nov. 2024, 22:15:41
    Author     : Mirado
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <form action="traitercsv" method="post" enctype="multipart/form-data">
        <h1>Insérer un fichier</h1>
        <input type="file" name="file" id="file">
        <input type="submit" value="Valider">
    </form>
</html>