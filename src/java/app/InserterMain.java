/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import back.baseconfig.utils.DatabaseConnection;
import back.utils.CSVInserter;
import back.utils.ConsommationData;
import java.sql.SQLException;

/**
 *
 * @author Mirado
 */
public class InserterMain {
    public static void main(String[] args) throws SQLException, Exception{
        ConsommationData consos=new ConsommationData(DatabaseConnection.getConnection());
        CSVInserter inserter=new CSVInserter(consos,3000);
        
        inserter.readCSVDisordered("C:\\Users\\Mirado\\Desktop\\Data 1M.csv\\random_data_int_1M.csv");
        System.out.println(inserter.getBlocs().size());
        System.out.println("Creation objet reussis");
        
        //System.out.println(inserter.getBlocs().getLast().getPrixRevientTheorique());
        
        /*Set shouldCommit to true in real context*/
        inserter.insert(true);
        //System.out.println("Vita");
        
        /*Set shouldCommit to true in real context*/
        inserter.updateAchat(true);
        //System.out.println("Mise a jour des achats réussie"); 
        
    }
}
