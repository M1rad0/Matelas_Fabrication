package back.utils;


import back.baseconfig.utils.DatabaseConnection;
import back.baseconfig.utils.GeneralDB;
import back.entities.base.Achat;
import back.entities.base.Bloc;
import back.entities.base.Produit;
import java.sql.Connection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mirado
 */
public class CSVInserter {
    ConsommationData conso;
    double prixRevient_m3;
    List<Bloc> blocs;
    
    public CSVInserter(ConsommationData conso,List<Bloc> init){
        this.conso=conso;
        prixRevient_m3=0;
        for(Bloc bloc:init){
            prixRevient_m3+=bloc.getPrixRevientPratique()/bloc.getVolume();
        }
        prixRevient_m3=prixRevient_m3/init.size();
    }
    
    public CSVInserter(ConsommationData conso,double prixRevient_m3){
        this.conso=conso;
        this.prixRevient_m3=prixRevient_m3;
    }
    
    public void readCSVOrdered(BufferedReader bfr) throws Exception{
        blocs = new ArrayList<>();
        String line;

        try (BufferedReader br = bfr) {
            // Sauter l'en-tête si présent
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Supposons que le séparateur soit une virgule
                String[] data = line.split(";");

                if (data.length == 7) {
                    String val = data[0];
                    double longueur = Double.parseDouble(data[1]);
                    double largeur = Double.parseDouble(data[2]);
                    double epaisseur = Double.parseDouble(data[3]);
                    double prixRevientPratique = Double.parseDouble(data[4]);
                    Timestamp daty = Timestamp.valueOf(LocalDate.parse(data[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay());
                    String idMachine = data[6];

                    // Créer un objet Bloc throw automatic en cas d'erreur
                    Bloc bloc = new Bloc(null, val, idMachine, daty, longueur, largeur, epaisseur,prixRevientPratique,conso);
                    
                    blocs.add(bloc);
                } else {
                    System.err.println("Ligne mal formatée : " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur lors de la conversion des données : " + e.getMessage());
        }
    }
    
    public void readCSVDisordered(BufferedReader bfr) throws Exception {
        blocs = new ArrayList<>();
        String line;

        try (BufferedReader br = bfr) {
            // Sauter l'en-tête si présent
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Supposons que le séparateur soit une virgule
                String[] data = line.split(";");

                if (data.length == 7) {
                    String val = data[0];
                    double longueur = Double.parseDouble(data[1]);
                    double largeur = Double.parseDouble(data[2]);
                    double epaisseur = Double.parseDouble(data[3]);
                    double prixRevientPratique = Double.parseDouble(data[4]);
                    Timestamp daty = Timestamp.valueOf(LocalDate.parse(data[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay());
                    String idMachine = data[6];

                    // Créer un objet Bloc throw automatic en cas d'erreur
                    Bloc bloc = new Bloc(null, val, idMachine, daty, longueur, largeur, epaisseur,prixRevientPratique);
                    
                    blocs.add(bloc);
                } else {
                    System.err.println("Ligne mal formatée : " + line);
                }
            }
            blocs.sort(new CompareByDate());
            
            for(Bloc bloc:blocs){
                bloc.setPrixRevientTheorique(bloc.calculPrixRevientTheorique(conso));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur lors de la conversion des données : " + e.getMessage());
        }
    }
    
    public void readCSVOrdered(InputStream input) throws Exception{
        readCSVOrdered(new BufferedReader(new InputStreamReader(input,"UTF-8")));
    }
    
    public void readCSVDisordered(InputStream input) throws Exception{
        readCSVDisordered(new BufferedReader(new InputStreamReader(input,"UTF-8")));
    }
    
    public void readCSVOrdered(String filePath) throws Exception{
        readCSVOrdered(new BufferedReader(new FileReader(filePath)));
    }
    
    public void readCSVDisordered(String filePath) throws Exception{
        readCSVDisordered(new BufferedReader(new FileReader(filePath)));
    }
    
    public void insert(boolean shouldCommit) throws IllegalAccessException,SQLException{
        Connection conn=null;
        try{
            conn=DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            Bloc.insert(conn, this.blocs, 100000);
            
            //Changer en commit dans un contexte normal
            if(shouldCommit){
                conn.commit();
            }
        }catch(SQLException ex){
            try{
                if(conn!=null){
                    conn.rollback(); 
                }
                throw ex;
            }catch(SQLException e){
                throw e;
            }
        }finally{
            if(conn!=null){
                conn.close();
            }
        }
    }
    
    public void updateAchat(boolean shouldCommit) throws SQLException{
        Connection conn=null;
        try{
            conn=DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            HashMap<Produit,ArrayList<Achat>> map=conso.getProduitsAchats();
            for(Produit product:map.keySet()){
                ArrayList<Achat> lsAchats=map.get(product);
                Achat.updateResteList(conn, lsAchats);
            }
            
            //Changer en commit dans un contexte normal
            conn.commit();
        }catch(SQLException ex){
            try{
                if(conn!=null){
                    conn.rollback(); 
                }
                throw ex;
            }catch(SQLException e){
                throw e;
            }
        }finally{
            if(conn!=null){
                conn.close();
            }
        }
    }

    public List<Bloc> getBlocs() {
        return this.blocs;
    }
}
