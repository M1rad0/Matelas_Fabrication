package back.utils;


import back.baseconfig.utils.DatabaseConnection;
import back.baseconfig.utils.GeneralDB;
import back.entities.base.Bloc;
import java.sql.Connection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    
    public void readCSV(String filePath) throws Exception {
        blocs = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
                    double prixRevientTheorique = 0.0;
                    Timestamp daty = Timestamp.valueOf(LocalDate.parse(data[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay());
                    String idMachine = data[6];

                    // Créer un objet Bloc throw automatic en cas d'erreur
                    Bloc bloc = new Bloc(null, val, idMachine, daty, longueur, largeur, epaisseur,conso,prixRevientPratique,false);
                    
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
    
    public void insert() throws IllegalAccessException,SQLException{
        Connection conn=null;
        try{
            conn=DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            GeneralDB<Bloc> gdbBloc=new GeneralDB<Bloc>(Bloc.class);
            gdbBloc.create(conn, this.blocs, 1000000);
            conn.commit();
        }catch(SQLException ex){
            try{
                conn.rollback(); 
                throw ex;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }finally{
            if(conn!=null){
                conn.close();
            }
        }
    }
    
    public static void main(String[] args) throws SQLException, Exception{
        ConsommationData consos=new ConsommationData(DatabaseConnection.getConnection());
        CSVInserter inserter=new CSVInserter(consos,3000);
        
        inserter.readCSV("C:\\Users\\Mirado\\Downloads\\Bloc.csv");
        System.out.println(inserter.blocs.size());
        System.out.println("Creation objet reussis");
        
        inserter.insert();
        System.out.println("Vita");
                
    }
}
