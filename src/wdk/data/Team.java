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
    int playersNeeded;
    int moneyLeft;
    int pricePP;
    int teamR;
    int teamHR;
    int teamRBI;
    int teamSB;
    double teamBA;
    double teamW;
    int teamSV;
    int teamK;
    double teamERA;
    double teamWHIP;
    double teamTotalPoints;
    public static final int TOTAL_MONEY = 260;
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
        moneyLeft = TOTAL_MONEY;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamOwner() {
        return teamOwner;
    }

    public static int getTOTAL_MONEY() {
        return TOTAL_MONEY;
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

    public int getMoneyLeft() {
        return moneyLeft;
    }

    public void setPlayersNeeded(int playersNeeded) {
        this.playersNeeded = playersNeeded;
    }

    public int getPlayersNeeded() {
        return playersNeeded;
    }
    
    
    public void setMoneyLeft(int moneyLeft) {
        this.moneyLeft = moneyLeft;
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

    public int getPricePP() {
        return pricePP;
    }

    public double getTeamBA() {
        return teamBA;
    }

    public double getTeamERA() {
        return teamERA;
    }

    public int getTeamHR() {
        return teamHR;
    }

    public int getTeamK() {
        return teamK;
    }

    public int getTeamR() {
        return teamR;
    }

    public int getTeamRBI() {
        return teamRBI;
    }

    public int getTeamSB() {
        return teamSB;
    }

    public int getTeamSV() {
        return teamSV;
    }

    public double getTeamTotalPoints() {
        return teamTotalPoints;
    }

    public double getTeamW() {
        return teamW;
    }

    public void setPricePP(int pricePP) {
        this.pricePP = pricePP;
    }

    public void setTeamBA(double teamBA) {
        this.teamBA = teamBA;
    }

    public void setTeamERA(double teamERA) {
        this.teamERA = teamERA;
    }

    public void setTeamHR(int teamHR) {
        this.teamHR = teamHR;
    }

    public void setTeamK(int teamK) {
        this.teamK = teamK;
    }

    public void setTeamR(int teamR) {
        this.teamR = teamR;
    }

    public void setTeamRBI(int teamRBI) {
        this.teamRBI = teamRBI;
    }

    public void setTeamSB(int teamSB) {
        this.teamSB = teamSB;
    }

    public void setTeamSV(int teamSV) {
        this.teamSV = teamSV;
    }

    public void setTeamTotalPoints(double teamTotalPoints) {
        this.teamTotalPoints = teamTotalPoints;
    }

    public void setTeamW(double teamW) {
        this.teamW = teamW;
    }

    public void setTeamWHIP(double teamWHIP) {
        this.teamWHIP = teamWHIP;
    }

    public double getTeamWHIP() {
        return teamWHIP;
    }
    
    
}
