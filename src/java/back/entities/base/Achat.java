/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.entities.base;

import back.baseconfig.annotations.Column;
import back.baseconfig.annotations.DefaultValue;
import back.baseconfig.annotations.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
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
    @Column(name="daty_achat")
    Timestamp dateAchat;
    
    /*Getters and Setters*/

    public Timestamp getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(Timestamp dateAchat) {
        this.dateAchat = dateAchat;
    }
    
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
    
    public static void updateResteList(Connection conn, ArrayList<Achat> achats) throws SQLException {
        // La requête SQL utilise les annotations des colonnes pour mapper correctement les champs
        String query = "UPDATE Achat SET reste = ? WHERE id_achat = ?";

        // Utilisation de PreparedStatement pour éviter les injections SQL et gérer efficacement les paramètres
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Achat achat : achats) {
                // On remplace les paramètres de la requête par les valeurs des objets Achat
                stmt.setDouble(1, achat.getReste());
                stmt.setString(2, achat.getIdAchat());

                // Exécution de l'update
                stmt.executeUpdate();
            }
        }
    }
}
