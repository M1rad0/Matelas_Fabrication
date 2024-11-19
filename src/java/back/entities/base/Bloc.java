package back.entities.base;

import back.error.NotEnoughRessources;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import back.baseconfig.annotations.Column;
import back.baseconfig.annotations.DefaultValue;
import back.baseconfig.annotations.Table;
import back.baseconfig.utils.GeneralDB;
import back.utils.ConsommationData;
import java.sql.Timestamp;
import java.util.HashMap;

@Table(name="Bloc")
public class Bloc extends LLH {
    @DefaultValue
    @Column(name="id_bloc")
    String idBloc;
    @Column(name="val")
    String val;
    @Column(name="prix_revient_pratique")
    double prixRevientPratique;
    @Column(name="prix_revient_theorique")
    double prixRevientTheorique;
    @Column(name="id_machine")
    String idMachine;
    @Column(name="daty_creation")
    Timestamp datyCreation;
    
    /*Getters and Setters*/

    public String getIdBloc() {
        return idBloc;
    }

    public void setIdBloc(String idBloc) {
        this.idBloc = idBloc;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public double getPrixRevientPratique() {
        return prixRevientPratique;
    }

    public void setPrixRevientPratique(double prixRevientPratique) {
        this.prixRevientPratique = prixRevientPratique;
    }

    public double getPrixRevientTheorique() {
        return prixRevientTheorique;
    }

    public void setPrixRevientTheorique(double prixRevientTheorique) {
        this.prixRevientTheorique = prixRevientTheorique;
    }

    public String getIdMachine() {
        return idMachine;
    }

    public void setIdMachine(String idMachine) {
        this.idMachine = idMachine;
    }

    public Timestamp getDatyCreation() {
        return datyCreation;
    }

    public void setDatyCreation(Timestamp datyCreation) {
        this.datyCreation = datyCreation;
    }

    public Bloc() {
    }

    public Bloc(String idBloc, String val, String idMachine, Timestamp datyCreation, double longueur, double largeur, double hauteur, ConsommationData conso, double prixpratiquem3) throws NotEnoughRessources, Exception {
        /*Initial*/
        super(longueur, largeur, hauteur);
        this.idBloc = idBloc;
        this.val = val;
        this.idMachine = idMachine;
        this.datyCreation = datyCreation;
        
        /*Logique*/
        //Prix de revient pratique
        setPrixRevientPratique(prixpratiquem3*this.getVolume());
        setPrixRevientTheorique(calculPrixRevientTheorique(conso));
    }
    
    public Bloc(String idBloc, String val, String idMachine, Timestamp datyCreation, double longueur, double largeur, double hauteur, ConsommationData conso, double prixpratique,boolean perm3) throws NotEnoughRessources, Exception {
        /*Initial*/
        super(longueur, largeur, hauteur);
        this.idBloc = idBloc;
        this.val = val;
        this.idMachine = idMachine;
        this.datyCreation = datyCreation;
        
        /*Logique*/
        //Prix de revient pratique
        if(perm3){
            setPrixRevientPratique(prixpratique*this.getVolume());
        }
        else{
            setPrixRevientPratique(prixpratique);
        }
        setPrixRevientTheorique(calculPrixRevientTheorique(conso));
    }
    
    public double calculPrixRevientTheorique(ConsommationData conso) throws Exception{
        double volume=this.getVolume();
        double prixRevient=0;
        
        HashMap<Produit,ArrayList<Achat>> lsAchats=conso.getProduitsAchats();
        
        for(Produit prod:lsAchats.keySet()){
            double restantConstr=volume*prod.getConsommation();
            
            for(Achat achat:lsAchats.get(prod)){
                if(achat.getQuantite()>=restantConstr){
                    prixRevient+=restantConstr*achat.getPrix_unitaire();
                    achat.setReste(achat.getReste()-restantConstr);
                    break;
                }
                else{
                    prixRevient+=achat.getQuantite()*achat.getPrix_unitaire();
                    achat.setReste(0);
                    restantConstr-=achat.getQuantite();
                }
            }
            if(restantConstr<0){
                throw new NotEnoughRessources();
            }
        }
        return prixRevient;
    }
}
