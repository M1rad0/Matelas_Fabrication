/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.utils;

import back.baseconfig.utils.GeneralDB;
import back.entities.base.Achat;
import back.entities.base.Produit;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Mirado
 */
public class ConsommationData {
    HashMap<Produit,ArrayList<Achat>> produitsAchats;
    
    public ConsommationData(){
        
    }
    
    /*Getters and Setters*/

    public HashMap<Produit, ArrayList<Achat>> getProduitsAchats() {
        return produitsAchats;
    }

    public void setProduitsAchats(HashMap<Produit, ArrayList<Achat>> produitsAchats) {
        this.produitsAchats = produitsAchats;
    }
    
    
    public ConsommationData(Connection conn) throws Exception{
        GeneralDB<Produit> gdbProd=new GeneralDB<Produit>(Produit.class);
        List<Produit> produits=gdbProd.findAll(conn);
        
        produitsAchats=new HashMap<Produit,ArrayList<Achat>>();
        
        GeneralDB<Achat> gdbAchat=new GeneralDB<Achat>(Achat.class);        
        
        for(Produit prod:produits){
            ArrayList<Achat> achats=(ArrayList)gdbAchat.getWhere(conn,"id_produit=? ORDER BY daty_achat",prod.getIdProduit());
            produitsAchats.put(prod, achats);
        }
    }
    
    public void updateAchats(){
        
    }
}
