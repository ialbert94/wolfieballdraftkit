package wdk.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
    HashMap<String, Integer> positionTable;

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
        positionTable = new HashMap<>();
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

    public HashMap<String, Integer> getPositionTable() {
        return positionTable;
    }

    public void setPositionTable(HashMap<String, Integer> positionTable) {
        this.positionTable = positionTable;
    }

    public void resetPositionTable() {
        positionTable.clear();
        positionTable.put("C", 2);
        positionTable.put("1B", 1);
        positionTable.put("3B", 1);
        positionTable.put("CI", 1);
        positionTable.put("2B", 1);
        positionTable.put("SS", 1);
        positionTable.put("MI", 1);
        positionTable.put("OF", 5);
        positionTable.put("U", 1);
        positionTable.put("P", 9);
    }

    public void addPlayerToStartingLineup(Player playerToAdd) {
        startupLine.add(playerToAdd);
    }

    public void calculatePositionTable() {
        for (Player p : startupLine) {
            String position = p.getP();
            updateHashMap(position);
        }
    }

    private void updateHashMap(String position) {
        //CHECK TO SEE IF WE CAN ADD A "C" TO THE COMBO BOX
        if (position.contains("C") && (positionTable.get("C") > 0)) {

            positionTable.replace("C", positionTable.get("C") - 1);
        }

        //CHECK TO SEE IF WE CAN ADD A "1B" TO THE COMBO BOX
        if (position.contains("1B") && (positionTable.get("1B") > 0)) {

            positionTable.replace("1B", positionTable.get("1B") - 1);
        }

        //CHECK TO SEE IF WE CAN ADD A "3B" TO THE COMBO BOX
        if (position.contains("3B") && (positionTable.get("3B") > 0)) {

            positionTable.replace("3B", positionTable.get("3B") - 1);
        }

       //CHECK TO SEE IF WE CAN ADD A "3B" TO THE COMBO BOX
        if (position.contains("CI") && (positionTable.get("CI") > 0)) {

            positionTable.replace("CI", positionTable.get("CI") - 1);
        }
        //CHECK TO SEE IF WE CAN ADD A "2B" TO THE COMBO BOX
        if (position.contains("2B") && (positionTable.get("2B") > 0)) {

            positionTable.replace("2B", positionTable.get("2B") - 1);
        }

        //CHECK TO SEE IF WE CAN ADD A "SS" TO THE COMBO BOX
        if (position.contains("SS") && (positionTable.get("SS") > 0)) {

            positionTable.replace("SS", positionTable.get("SS") - 1);
        }

        //CHECK TO SEE IF WE CAN ADD A "3B" TO THE COMBO BOX
        if (position.contains("MI") && (positionTable.get("MI") > 0)) {

            positionTable.replace("MI", positionTable.get("MI") - 1);
        }
        //CHECK TO SEE IF WE CAN ADD A "OF" TO THE COMBO BOX
        if (position.contains("OF") && (positionTable.get("OF") > 0)) {
            positionTable.replace("OF", positionTable.get("OF") - 1);
        }

        //CHECK TO SEE IF WE CAN ADD A "U" TO THE COMBO BOX
        if (position.contains("U") && (positionTable.get("U") > 0)) {

            positionTable.replace("U", positionTable.get("U") - 1);
        }

        //CHECK TO SEE IF WE CAN ADD A "P" TO THE COMBO BOX
        if (position.contains("P") && (positionTable.get("P") > 0)) {
            positionTable.replace("P", positionTable.get("P") - 1);
        }
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

    public void refreshTeam() {
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
    
    public void calculateStats() {
        clearStats();
        setPlayersNeeded(23 - startupLine.size());
        if (playersNeeded == 0) {
            setPricePP(-1);
        } else {
            setPricePP(getMoneyLeft() / playersNeeded);
        }
        for (Player p : getStartupLine()) {
            if (p.getQP().contains("P") || p.getP().contains("P")) {
                //W SV K ERA WHIP
                setMoneyLeft(getMoneyLeft() - p.getSalary());
                setTeamW(getTeamW() + p.getR_W());
                setTeamSV(getTeamSV() + p.getHR_SV());
                setTeamK(getTeamK() + p.getRBI_K());

                setTeamW(getTeamW() + p.getW());
            } else {
                //R HR RBI SB BA
                setMoneyLeft(getMoneyLeft() - p.getSalary());
                setTeamR(getTeamR() + p.getR_W());
                setTeamHR(getTeamHR() + p.getHR_SV());
                setTeamRBI(getTeamRBI() + p.getRBI_K());
                setTeamSB((int) (getTeamSB() + p.getSB_ERA()));

            }
        }

    }
    private void clearStats() {
        setPlayersNeeded(23);
        setMoneyLeft(Team.TOTAL_MONEY);
        setPricePP(Team.getTOTAL_MONEY()/getPlayersNeeded());
        setTeamR(0);
        setTeamHR(0);
        setTeamK(0);
        setTeamRBI(0);
        setTeamSB(0);
        setTeamSV(0);
        setTeamW(0);
        setTeamBA(0);
        setTeamERA(0);
        setTeamWHIP(0);
    }
    
}
