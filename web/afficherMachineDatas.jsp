<%-- 
    Document   : afficherMachinesDatas
    Created on : 20 nov. 2024, 22:47:12
    Author     : Mirado
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,back.entities.views.MachineData,java.text.DecimalFormat"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% DecimalFormat formatter=new DecimalFormat("#.########"); %>
        <table border="1">
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
    </body>
</html>
