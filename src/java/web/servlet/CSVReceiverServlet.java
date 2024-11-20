/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package web.servlet;

import back.baseconfig.utils.DatabaseConnection;
import back.utils.CSVInserter;
import back.utils.ConsommationData;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author Mirado
 */
@WebServlet(name = "CSVReceiverServlet", urlPatterns = {"/traitercsv"})
public class CSVReceiverServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            ConsommationData consos = new ConsommationData(conn);
            double permetre3=3000;
            CSVInserter inserter = new CSVInserter(consos , permetre3);
            
            /*Considérer si les données sont ordered ou pas*/
            inserter.readCSVDisordered(filePart.getInputStream());
            
            /*Effectuer l'ensemble des insertions des blocs*/
            inserter.insert(true);
            
            /*Effectuer l'ensemble des updates des achats*/
            inserter.updateAchat(true);
        } catch (Exception e) {
            response.getWriter().write("Erreur ve : " + e.getMessage());
        }finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                }
            }
        }
    }
}
