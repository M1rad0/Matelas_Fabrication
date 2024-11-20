/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.utils;

import back.entities.base.Bloc;
import java.util.Comparator;

/**
 *
 * @author Mirado
 */
public class CompareByDate implements Comparator<Bloc>{
    @Override
    public int compare(Bloc bloc1,Bloc bloc2){
        return bloc1.getDatyCreation().compareTo(bloc2.getDatyCreation());
    }
}
