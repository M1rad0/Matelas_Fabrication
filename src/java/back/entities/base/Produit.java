/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.entities.base;

import back.baseconfig.annotations.Column;
import back.baseconfig.annotations.DefaultValue;
import back.baseconfig.annotations.Table;

/**
 *
 * @author Mirado
 */
@Table(name="Produit")
public class Produit {
    @DefaultValue()
    @Column(name="id_produit")
    String idProduit;
    @Column(name="val")
    String nomProduit;
    @Column(name="consommation")
    double consommation;
    
    /*Getters and Setters*/
    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public double getConsommation() {
        return consommation;
    }

    public void setConsommation(double consommation) {
        this.consommation = consommation;
    }
    
    
    
    public Produit(){
        
    }
}
