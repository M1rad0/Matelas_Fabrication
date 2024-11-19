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
@Table(name="Achat")
public class Achat {
    @DefaultValue()
    @Column(name="id_achat")
    String idAchat;
    @Column(name="id_produit")
    String idProduit;
    @Column(name="PU")
    double prix_unitaire;
    @Column(name="quantite")
    double quantite;
    @Column(name="reste")
    double reste;
    
    /*Getters and Setters*/

    public String getIdAchat() {
        return idAchat;
    }

    public void setIdAchat(String idAchat) {
        this.idAchat = idAchat;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }
    
    public Achat(){
        
    }
}
