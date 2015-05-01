package wdk.data;

import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Albert
 */
public class Team {

    //THESE WILL BE THE VARIABLES THAT WILL BELONG
    //TO BOTH THE PITCHERS AND THE HITTERS

    String teamName;
    String teamOwner;

    //EVERY TEAM WILL HAVE A STARTING SQUAD AND A TAXISQUAD
    ObservableList<Player> startupLine;
    ObservableList<Player> taxiSquad;

    //have to be very careful here. this class will represent a team in the draft
    //so we know that a team has to have a certain amount of players, under
    //a certain amount of positions. might make final variables that will
    //represent these characteristics
    public Team() {
        teamName = "";
        teamOwner = "";
        startupLine = FXCollections.observableArrayList();
        taxiSquad = FXCollections.observableArrayList();
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamOwner() {
        return teamOwner;
    }

    public void addPlayerToStartingLineup(Player playerToAdd) {
        startupLine.add(playerToAdd);
    }

    public void addPlayerToTaxiSquad(Player playerToAdd) {
        taxiSquad.add(playerToAdd);
    }

    public void removePlayerFromStartingLineup(Player playerToRemove) {
        startupLine.remove(playerToRemove);
    }

    public void removePlayerFromTaxiSquad(Player playerToRemove) {
        startupLine.add(playerToRemove);
    }

    public ObservableList<Player> getStartupLine() {
        return startupLine;
    }

    public ObservableList<Player> getTaxiSquad() {
        return taxiSquad;
    }

    public void setStartupLine(ObservableList<Player> startupLine) {
        this.startupLine = startupLine;
    }

    public void setTaxiSquad(ObservableList<Player> taxiSquad) {
        this.taxiSquad = taxiSquad;
    }
    
    
    public void refreshTeam(){
         Comparator<Player> byComparator = (p1, p2) -> p1.getComp().compareTo(p2.getComp());
        //teamToSort.startupLine.stream().sorted(byComparator);
         Collections.sort(startupLine, byComparator);
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamOwner(String teamOwner) {
        this.teamOwner = teamOwner;
    }
}
