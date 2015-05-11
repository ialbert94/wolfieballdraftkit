/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
    private String draftName;
    private String owner;

    ArrayList<String> filledPositions;
    ObservableList<Player> hitters;
    ObservableList<Player> pitchers;
    ObservableList<Player> allPlayers;
    ObservableList<Player> filteredPlayers;
    ObservableList<Team> teams;
    ObservableList<Team> teamsWithTotalStats;
    ObservableList<Player> draftedPlayers;

    public Draft() {
        hitters = FXCollections.observableArrayList();
        pitchers = FXCollections.observableArrayList();
        allPlayers = FXCollections.observableArrayList();
        filteredPlayers = FXCollections.observableArrayList();
        teams = FXCollections.observableArrayList();
        teamsWithTotalStats = FXCollections.observableArrayList();
        draftedPlayers = FXCollections.observableArrayList();
        draftName = "";
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

    public void resetAllTeams() {
        teams.clear();
    }

    public String getDraftName() {
        return draftName;
    }

    public void setDraftName(String draftName) {
        this.draftName = draftName;
    }

    public void resetAllPlayers() {
        allPlayers.clear();
    }

    public void resetFilteredPlayers() {
        filteredPlayers.clear();
    }

    public ObservableList<Player> getFilteredPlayers() {
        return filteredPlayers;
    }

    public Player getPlayer(Player p, String firstName, String lastName) {
        if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
            return p;
        }
        return null;
    }

    public void setFilteredPlayers(ObservableList<Player> filteredPlayers) {
        this.filteredPlayers = filteredPlayers;
    }

    public void removePlayer(Player playerToRemove) {
        allPlayers.remove(playerToRemove);
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

    public void addToTeams(Team teamToAdd) {
        teams.add(teamToAdd);
    }

    public Team getTeamItem(String teamName) {
        for (Team team : teams) {
            if (team.getTeamName().equals(teamName)) {
                return team;
            }
        }
        return null;
    }

    public ObservableList<Team> getTeams() {
        return teams;
    }

    public void removeTeam(Team teamToRemove) {
        teams.remove(teamToRemove);
    }

    public ObservableList<Team> getTeamsWithStats() {
        for (Team team : teams) {

            //  calculateStats(team);
        }
        return teams;
    }

    public ObservableList<Player> getDraftedPlayers() {
        return draftedPlayers;
    }

    public void setDraftedPlayers(ObservableList<Player> draftedPlayers) {
        this.draftedPlayers = draftedPlayers;
    }

    public void sortTeam(Team teamToSort) {
        for (Player p : teamToSort.startupLine) {
            //if (p.getComp() == null) {
            if (p.getP().equals("C")) {
                p.setComparator("a");
            }
            if (p.getP().equals("1B")) {
                p.setComparator("b");
            }
            if (p.getP().equals("3B")) {
                p.setComparator("c");
            }
            if (p.getP().equals("CI")) {
                p.setComparator("d");
            }
            if (p.getP().equals("2B")) {
                p.setComparator("e");
            }
            if (p.getP().equals("SS")) {
                p.setComparator("f");
            }
            if (p.getP().equals("MI")) {
                p.setComparator("g");
            }
            if (p.getP().equals("OF")) {
                p.setComparator("h");
            }
            if (p.getP().equals("U")) {
                p.setComparator("i");
            }
            if (p.getP().equals("P")) {
                p.setComparator("j");
            }
            //}

        }
        Comparator<Player> byComparator = (p1, p2) -> p1.getComp().compareTo(p2.getComp());
        //teamToSort.startupLine.stream().sorted(byComparator);
        Collections.sort(teamToSort.startupLine, byComparator);
    }

    private void clearStats(Team teamToCalculate) {
        teamToCalculate.setPlayersNeeded(23);
        teamToCalculate.setMoneyLeft(Team.TOTAL_MONEY);
        teamToCalculate.setPricePP(Team.getTOTAL_MONEY() / teamToCalculate.getPlayersNeeded());
        teamToCalculate.setTeamR(0);
        teamToCalculate.setTeamHR(0);
        teamToCalculate.setTeamK(0);
        teamToCalculate.setTeamRBI(0);
        teamToCalculate.setTeamSB(0);
        teamToCalculate.setTeamSV(0);
        teamToCalculate.setTeamW(0);
        teamToCalculate.setTeamBA(0);
        teamToCalculate.setTeamERA(0);
        teamToCalculate.setTeamWHIP(0);
    }

    public void setTeamPoints() {
        ArrayList<Integer> R = new ArrayList();
        ArrayList<Integer> HR = new ArrayList();
        ArrayList<Integer> RBI = new ArrayList();
        ArrayList<Integer> SB = new ArrayList();
        ArrayList<Double> BA = new ArrayList();
        ArrayList<Integer> W = new ArrayList();
        ArrayList<Integer> SV = new ArrayList();
        ArrayList<Integer> K = new ArrayList();
        ArrayList<Double> ERA = new ArrayList();
        ArrayList<Double> WHIP = new ArrayList();
        if (!teams.isEmpty()) {
            for (int i = 0; i < teams.size(); i++) {
                R.add(teams.get(i).getTeamR());
                HR.add(teams.get(i).getTeamHR());
                RBI.add(teams.get(i).getTeamRBI());
                SB.add(teams.get(i).getTeamSB());
                BA.add(teams.get(i).getTeamBA());
                W.add(teams.get(i).getTeamW());
                SV.add(teams.get(i).getTeamSV());
                K.add(teams.get(i).getTeamK());
                ERA.add(teams.get(i).getTeamERA());
                WHIP.add(teams.get(i).getTeamWHIP());
            }
            Collections.sort(R);
            Collections.sort(HR);
            Collections.sort(RBI);
            Collections.sort(SB);
            Collections.sort(BA);
            Collections.sort(W);
            Collections.sort(K);
            Collections.sort(SV);
            Collections.sort(ERA);
            Collections.sort(WHIP);
            int val1 = 0;
            for (int i = 0; i < teams.size(); i++) {
                int val = 0;
                if (teams.get(i).getTeamR() == (R.get(0))) {
                    val++;
                }
                if (teams.get(i).getTeamHR() == (HR.get(0))) {
                    val++;
                }
                if (teams.get(i).getTeamRBI() == (RBI.get(0))) {
                    val++;
                }
                if (teams.get(i).getTeamSB() == (SB.get(0))) {
                    val++;
                }
                if (teams.get(i).getTeamBA() == (BA.get(0))) {
                    val++;
                }
                if (teams.get(i).getTeamW() == (W.get(0))) {
                    val++;
                }
                if (teams.get(i).getTeamK() == (K.get(0))) {
                    val++;
                }
                if (teams.get(i).getTeamSV() == (SV.get(0))) {
                    val++;
                }
                if (teams.get(i).getTeamERA() == (ERA.get(0))) {
                    val++;
                }
                if ((teams.get(i).getTeamWHIP()) == (WHIP.get(0))) {
                    val++;
                }
                if (!teams.get(i).getStartupLine().isEmpty()) {
                    if (teams.size() == 1) {
                        teams.get(i).setTeamTotalPoints(0);
                    } else {
                        teams.get(i).setTeamTotalPoints(teams.get(i).getTeamTotalPoints() + teams.size() * val);
                    }
                }
            }
        }

    }
    public void calculatePlayerRanks(){
        for (Player player : getAllPlayers()) {
            int randNum =  (int) (Math.random() * 260);
            player.setEstimatedValue(randNum);
        }
        for(Team team : getTeams()){
            for(Player p : team.startupLine){
                int randNum =  (int) (Math.random() * 260);
                p.setEstimatedValue(randNum);
            }
        }
        for(Team team : getTeams()){
            for(Player p : team.taxiSquad){
                int randNum =  (int) (Math.random() * 260);
                p.setEstimatedValue(randNum);
            }
        }
    }
    public void setTeamPoints2() {

        HashMap<Integer, Team> hmR = new HashMap<Integer, Team>();
        HashMap<Integer, Team> hmHR = new HashMap<Integer, Team>();
        HashMap<Integer, Team> hmRBI = new HashMap<Integer, Team>();
        HashMap<Integer, Team> hmSB = new HashMap<Integer, Team>();
        HashMap<Double, Team> hmBA = new HashMap<Double, Team>();
        HashMap<Integer, Team> hmW = new HashMap<Integer, Team>();
        HashMap<Integer, Team> hmK = new HashMap<Integer, Team>();
        HashMap<Integer, Team> hmSV = new HashMap<Integer, Team>();
        HashMap<Double, Team> hmERA = new HashMap<Double, Team>();
        HashMap<Double, Team> hmWHIP = new HashMap<Double, Team>();

        for (Team team : getTeams()) {
            hmR.put(team.getTeamR(), team);
            hmHR.put(team.getTeamHR(), team);
            hmRBI.put(team.getTeamRBI(), team);
            hmSB.put(team.getTeamSB(), team);
            hmBA.put(team.getTeamBA(), team);
            hmW.put(team.getTeamW(), team);
            hmK.put(team.getTeamK(), team);
            hmSV.put(team.getTeamSV(), team);
            hmERA.put(team.getTeamERA(), team);
            hmWHIP.put(team.getTeamWHIP(), team);
            team.setTeamTotalPoints(0);

            for (int i = 0; i < teams.size(); i++) {
                int Rpts = 0;
                int HRpts = 0;
                int RBIpts = 0;
                int SBpts = 0;
                int BApts = 0;
                int Wpts = 0;
                int Kpts = 0;
                int SVpts = 0;
                int ERApts = 0;
                int WHIPpts = 0;
                double val = 0;
                for (int j = 0; j < teams.size(); j++) {
                    if (teams.get(j).getTeamR() > teams.get(i).getTeamR()) {
                        Rpts++;
                        val++;
                    }
                    if (teams.get(j).getTeamHR() > teams.get(i).getTeamHR()) {
                        HRpts++;
                        val++;
                    }
                    if (teams.get(j).getTeamRBI() > teams.get(i).getTeamRBI()) {
                        RBIpts++;
                        val++;
                    }
                    if (teams.get(j).getTeamSB() > teams.get(i).getTeamSB()) {
                        SBpts++;
                        val++;
                    }
                    if (teams.get(j).getTeamBA() > teams.get(i).getTeamBA()) {
                        BApts++;
                        val++;
                    }
                    if (teams.get(j).getTeamW() > teams.get(i).getTeamW()) {
                        Wpts++;
                        val++;
                    }
                    if (teams.get(j).getTeamK() > teams.get(i).getTeamK()) {
                        Kpts++;
                        val++;
                    }
                    if (teams.get(j).getTeamSV() > teams.get(i).getTeamSV()) {
                        SVpts++;
                        val++;
                    }
                    if (teams.get(j).getTeamERA() > teams.get(i).getTeamERA()) {
                        ERApts++;
                        val++;
                    }
                    if (teams.get(j).getTeamWHIP() > teams.get(i).getTeamWHIP()) {
                        WHIPpts++;
                        val++;
                    }
                    int totalPoints = Rpts + RBIpts + BApts + ERApts + HRpts + Kpts + SBpts + SVpts + WHIPpts + Wpts;
                    teams.get(j).setTeamTotalPoints(teams.get(j).getTeamTotalPoints() + totalPoints);
                }
                if (!teams.get(i).getStartupLine().isEmpty()) {
                    if (teams.size() == 1) {
                        teams.get(i).setTeamTotalPoints(10);
                    } else {
                       // teams.get(i).setTeamTotalPoints(teams.get(i).getTeamTotalPoints() + teams.size() * val);
                    }
                }int totalPoints = Rpts + RBIpts + BApts + ERApts + HRpts + Kpts + SBpts + SVpts + WHIPpts + Wpts;
                    team.setTeamTotalPoints(totalPoints);
                }

            }

        }
    }


