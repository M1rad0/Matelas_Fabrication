<%-- 
    Document   : afficherMachinesDatas
    Created on : 20 nov. 2024, 22:47:12
    Author     : Mirado
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table>
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
                List<MachineData> machineDatas=request.getAttribute("machineDatas");
                for(MachineData machineData:machineDatas){ %>
                    <tr>
                        <td><%= machineData.getIdMachine()%></td>
                        <td><%= machineData.getTotalTheorique()%></td>
                        <td><%= machineData.getTotalPratique()%></td>
                        <td><%= machineData.getPerte()%></td>
                    </tr>
                <% } %>
            </tbody>
            
        </table>
    </body>
</html>
