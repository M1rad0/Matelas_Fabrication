/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.entities.views;

import back.baseconfig.annotations.Column;
import back.baseconfig.annotations.Table;

/**
 *
 * @author Mirado
 */
@Table(name="v_perte_machine")
public class MachineData {
    @Column(name="id_machine")
    String idMachine;
    @Column(name="totaltheorique")
    double totalTheorique;
    @Column(name="totalpratique")
    double totalPratique;
    @Column(name="perte")
    double perte;
    
    /*Getters and Setters*/
    public String getIdMachine() {
        return idMachine;
    }

    public void setIdMachine(String idMachine) {
        this.idMachine = idMachine;
    }

    public double getTotalTheorique() {
        return totalTheorique;
    }

    public void setTotalTheorique(double totalTheorique) {
        this.totalTheorique = totalTheorique;
    }

    public double getTotalPratique() {
        return totalPratique;
    }

    public void setTotalPratique(double totalPratique) {
        this.totalPratique = totalPratique;
    }

    public double getPerte() {
        return perte;
    }

    public void setPerte(double perte) {
        this.perte = perte;
    }
}
