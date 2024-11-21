/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package web.servlet;

import back.baseconfig.utils.DatabaseConnection;
import back.baseconfig.utils.GeneralDB;
import back.entities.views.MachineData;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AfficheurMachineAnneeServlet", urlPatterns = {"/afficherAnnee"})
public class AfficheurMachineAnneeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int year=Integer.parseInt(request.getParameter("year"));
        
        GeneralDB<MachineData> gdbMD=new GeneralDB<MachineData>(MachineData.class);
        try(Connection conn=DatabaseConnection.getConnection()){
            request.setAttribute("machineDatas", MachineData.getDataYear(conn, year));
        }
        catch(Exception e){
            throw new ServletException(e);
        }
        request.getRequestDispatcher("afficherMachineDatas.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
