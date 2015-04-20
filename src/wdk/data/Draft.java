/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

    public void removeHitter(Player hitterToRemove) {
        hitters.remove(hitterToRemove);
    }

    public void addPitcher(Player p) {
        pitchers.add(p);

    }

    public void resetPitchers() {
        pitchers.clear();
    }

    public void resetHitters() {
        hitters.clear();
    }
    public void resetAllPlayers(){
        allPlayers.clear();
    }
    
    public void resetFilteredPlayers(){
        filteredPlayers.clear();
    }
    public ObservableList<Player> getFilteredPlayers() {
        return filteredPlayers;
    }
    public Player getPlayer(Player p, String firstName, String lastName){
        if(p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
            return p;
        return null;
    }
    public void setFilteredPlayers(ObservableList<Player> filteredPlayers) {
        this.filteredPlayers = filteredPlayers;
    }

    public ObservableList<Player> getPlayersWithPositions(String pos) {

        filteredPlayers.clear();

        for (Player player : allPlayers) {
            if (pos.equals("MI")) {
                if (player.getQP().contains("2B") || player.getQP().contains("SS")) {
                    filteredPlayers.add(player);
                }
            }
            if (pos.equals("CI")) {
                if (player.getQP().contains("1B") || player.getQP().contains("3B")) {
                    filteredPlayers.add(player);
                }
            } else {
                if (player.getQP().contains(pos)) {
                    filteredPlayers.add(player);
                }
            }
        }

        return filteredPlayers;
    }

    public ObservableList<Player> getAllPlayers() {
        return allPlayers;
    }

    public ObservableList<Player> getPitchers() {
        return pitchers;
    }

    public void addToAllPlayers(Player p) {
        allPlayers.add(p);

    }

    public void addToFilteredPlayers(Player playerToAdd) {
        filteredPlayers.addAll(playerToAdd);
    }

    public void removePitcher(Player pitcherToRemove) {
        pitchers.remove(pitcherToRemove);
    }

}
