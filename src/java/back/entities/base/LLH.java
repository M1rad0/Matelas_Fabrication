package back.entities.base;

import back.baseconfig.annotations.Column;

public class LLH {
    @Column(name="longueur")
    double longueur;
    @Column(name="largeur")
    double largeur;
    @Column(name="epaisseur")
    double hauteur;

    /*Getters and Setters */
    public double getLongueur() {
        return longueur;
    }
    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }
    public double getLargeur() {
        return largeur;
    }
    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }
    public double getHauteur() {
        return hauteur;
    }
    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    // Constructeurs
    public LLH(double longueur, double largeur, double hauteur) {
        setLongueur(longueur);
        setLargeur(largeur);
        setHauteur(hauteur);
    }
    public LLH() {
    } 

    public double getVolume() {
        return largeur*hauteur*longueur;
    }

    public int canMake(LLH object){
        int qte=0;
        double volumeThis=this.getVolume();
        double volumeObject=object.getVolume();

        while(volumeThis>=volumeObject){
            volumeThis-=volumeObject;
            qte++;
        }
        return qte;
    }
}