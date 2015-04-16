/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.data;

import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Albert
 */
public class Draft {
    private int biddingAmount;
    private String name;
    private String owner;
    
    ObservableList<Player> startupLine;
    ObservableList<Player> taxiSquad;
    ArrayList<String> filledPositions;
    ObservableList<Player> hitters;
    ObservableList<Player> pitchers;
    
    public Draft() {
        hitters = FXCollections.observableArrayList();
        pitchers = FXCollections.observableArrayList();
    }
    
   public void addHitter(Player p) {
        hitters.add(p);
        
    }
   public ObservableList<Player> getHitters() {
        return hitters;
    }
   
   public void removeHitter(Player hitterToRemove){
       hitters.remove(hitterToRemove);
   }
}
