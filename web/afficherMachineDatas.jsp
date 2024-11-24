<%-- 
    Document   : afficherMachinesDatas
    Created on : 20 nov. 2024, 22:47:12
    Author     : Mirado
--%>
    
<%@ page import="java.util.List,back.entities.views.MachineData,java.text.DecimalFormat"%>
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
    <h1 style="padding: 20px; text-align: center">ETU 002741</h1>
    <!-- Main Content -->
    <main class="container">
        <a href="afficherMachines">Voir tout</a>             
        <form action="afficherAnnee" method="get" style="padding: 20px">
            <h1>Choisissez une annee</h1>
            <select name="year">
                <option value="2022">2022</option>
                <option value="2023">2023</option>
                <option value="2024">2024</option>
            </select>
            <input class="btn btn-primary" type="submit" value="Valider">
        </form>
        <% DecimalFormat formatter=new DecimalFormat("#.########"); %>
        <h2><%= request.getParameter("year") %></h2>
        <table class="table-responsive table-bordered">
            <thead>
                <tr>
                    <th>
                        Id Machine
                    </th>
                    <th>
                        Total théorique
                    </th>
                    <th>
                        Total pratique
                    </th>
                    <th>
                        Pertes
                    </th>
                    <th>
                        Volume
                    </th>
                </tr>
            </thead>
            <tbody>
                <%
                List<MachineData> machineDatas=(List<MachineData>)request.getAttribute("machineDatas");
                for(MachineData machineData:machineDatas){ %>
                    <tr>
                        <td><%= machineData.getIdMachine()%></td>
                        <td><%= formatter.format(machineData.getTotalTheorique())%></td>
                        <td><%= formatter.format(machineData.getTotalPratique())%></td>
                        <td><%= formatter.format(machineData.getPerte())%></td>
                        <td><%= formatter.format(machineData.getVolume())%></td>
                    </tr>
                <% } %>
            </tbody>
            <% MachineData bestMachine=machineDatas.get(0); %>
        </table>
            <h4 style="text-align: center;padding: 20px">Minimum de perte : Machine <%= bestMachine.getIdMachine() %> = <%= formatter.format(bestMachine.getPerte()) %></h4>
    </main>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>