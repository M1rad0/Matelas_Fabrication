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

    <!-- Main Content -->
    <main class="container">
        <% DecimalFormat formatter=new DecimalFormat("#.########"); %>
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
                    </tr>
                <% } %>
            </tbody>
            <% MachineData bestMachine=machineDatas.get(0); %>
        </table>
        <p>La machine avec le minimum de perte est <%= bestMachine.getIdMachine() %> avec une perte de <%= formatter.format(bestMachine.getPerte()) %></p>
    </main>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>