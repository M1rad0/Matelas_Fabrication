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
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 500, // Taille à partir de laquelle un fichier est écrit sur le disque (10 Mo)
        maxFileSize = 1024 * 1024 * 500,      // Taille maximale d'un fichier (500 Mo)
        maxRequestSize = 1024 * 1024 * 1000   // Taille maximale de la requête (1 Go)
)
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
            inserter.insert(false);
            
            /*Effectuer l'ensemble des updates des achats*/
            inserter.updateAchat(false);
            
            response.getWriter().write("OKAY");
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
