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
        
        inserter.readCSVOrdered("C:\\Users\\Mirado\\Documents\\ITU\\s5\\Architecture_Logiciel\\Projet_2\\Bloc.csv");
        System.out.println(inserter.getBlocs().size());
        System.out.println("Creation objet reussis");
        
        /*Set shouldCommit to true in real context*/
        inserter.insert(false);
        System.out.println("Vita");
        
        /*Set shouldCommit to true in real context*/
        inserter.updateAchat(false);
        System.out.println("Mise a jour des achats réussie");           
    }
}
