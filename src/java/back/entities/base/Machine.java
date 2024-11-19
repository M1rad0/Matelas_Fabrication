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
@Table(name="Machine")
public class Machine {
    @DefaultValue()
    @Column(name="id_machine")
    String idMachine;
    @Column(name="val")
    String nomMachine;
    
    /*Getters and Setters*/
    public String getIdMachine() {
        return idMachine;
    }

    public void setIdMachine(String idMachine) {
        this.idMachine = idMachine;
    }

    public String getNomMachine() {
        return nomMachine;
    }

    public void setNomMachine(String nomMachine) {
        this.nomMachine = nomMachine;
    }
    
    public Machine(){
        
    }
}
