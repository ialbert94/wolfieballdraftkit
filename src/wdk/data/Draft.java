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
    ObservableList<Player> allPlayers;
    ObservableList<Player> filteredPlayers;
    public Draft() {
        hitters = FXCollections.observableArrayList();
        pitchers = FXCollections.observableArrayList();
        allPlayers = FXCollections.observableArrayList();
        filteredPlayers = FXCollections.observableArrayList();
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
   
    public void addPitcher(Player p) {
        pitchers.add(p);
        
    }

    public ObservableList<Player> getFilteredPlayers() {
        return filteredPlayers;
    }

    public void setFilteredPlayers(ObservableList<Player> filteredPlayers) {
        this.filteredPlayers = filteredPlayers;
    }
    
    
    public ObservableList<Player> getAllPlayers(){
        return allPlayers;
    }
   public ObservableList<Player> getPitchers() {
        return pitchers;
    }
   public void addToAllPlayers(Player p) {
       allPlayers.add(p);
     
   }
   
   public void addToFilteredPlayers(Player playerToAdd){
       filteredPlayers.addAll(playerToAdd);
   }
   
   public void removePitcher(Player pitcherToRemove){
       pitchers.remove(pitcherToRemove);
   }
   
}
