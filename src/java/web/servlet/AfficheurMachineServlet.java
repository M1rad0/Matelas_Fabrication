/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package web.servlet;

import back.baseconfig.utils.DatabaseConnection;
import back.baseconfig.utils.GeneralDB;
import back.entities.views.MachineData;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;

/**
 *
 * @author Mirado
 */
@WebServlet(name = "AfficheurMachineServlet", urlPatterns = {"/afficherMachines"})
public class AfficheurMachineServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GeneralDB<MachineData> gdbMD=new GeneralDB<MachineData>(MachineData.class);
        try(Connection conn=DatabaseConnection.getConnection()){
            request.setAttribute("machineDatas", gdbMD.findAll(conn));
        }
        catch(Exception e){
            throw new ServletException(e);
        }
        request.getRequestDispatcher("afficherMachineDatas.jsp").forward(request, response);
    }
}
