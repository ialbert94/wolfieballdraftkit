/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.controller;

import java.util.HashMap;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import wdk.data.DraftDataManager;
import wdk.data.Player;
import wdk.data.Team;
import wdk.error.ErrorHandler;
import wdk.gui.MessageDialog;
import wdk.gui.WDK_GUI;

/**
 *
 * @author Albert
 */
public class DraftEditController extends Stage {

    // WE USE THIS TO MAKE SURE OUR PROGRAMMED UPDATES OF UI
    // VALUES DON'T THEMSELVES TRIGGER EVENTS
    private boolean enabled;
    MessageDialog dialog;

    /**
     * Constructor that gets this controller ready, not much to initialize as
     * the methods for this function are sent all the objects they need as
     * arguments.
     */
    public DraftEditController() {
        enabled = true;
    }

    /**
     * This mutator method lets us enable or disable this controller.
     *
     * @param enableSetting If false, this controller will not respond to Course
     * editing. If true, it will.
     */
    public void enable(boolean enableSetting) {
        enabled = enableSetting;
    }

    private void updateHashMap(HashMap<String, Integer> positionTable, String position) {
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

        //CHECK TO SEE IF WE CAN ADD A "CI" TO THE COMBO BOX
        if ((position.contains("1B") || position.contains("3B")) && positionTable.get("CI") > 0) {

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

        //CHECK TO SEE IF WE CAN ADD A "2B" TO THE COMBO BOX
        if ((position.contains("2B") || position.contains("SS")) && positionTable.get("MI") > 0) {
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

    public void handleSelectPlayerRequest(int num, int num2, DraftDataManager dataManager) {

        //do this prior to managing the player to load the hashmap. this is because
        //we need to make sure we only add players that are neeed 
//        int totalPlayers = 0;
//        HashMap<String, Integer> positionTable;
//        for (Team team : dataManager.getDraft().getTeams()) {
//            team.resetPositionTable();
//            positionTable = team.getPositionTable();
//            for (Player p : team.getStartupLine()) {
//                String position = p.getP();
//                updateHashMap(positionTable, position);
//                totalPlayers++;
//            }
//            team.setPositionTable(positionTable);
//        }
//        int i = 0;
//        HashMap<String, Integer> positionTable = new HashMap<>();
//        positionTable.put("C", 2);
//        positionTable.put("1B", 1);
//        positionTable.put("3B", 1);
//        positionTable.put("CI", 1);
//        positionTable.put("2B", 1);
//        positionTable.put("SS", 1);
//        positionTable.put("MI", 1);
//        positionTable.put("OF", 5);
//        positionTable.put("U", 1);
//        positionTable.put("P", 9);
        boolean bool = true;
        int in = 0;
        Player playerToDraft = new Player();
        String position = "";
        HashMap<String, Integer> positionsTable = new HashMap<>();
        Team team = dataManager.getDraft().getTeams().get(num);
        team.setPositionTable(positionsTable);
        team.resetPositionTable();
        team.calculatePositionTable();
        positionsTable = team.getPositionTable();
        boolean playerAvailable = true;
        int numPos = 23;
        Random randNum = new Random();
        in = randNum.nextInt(dataManager.getDraft().getAllPlayers().size() - 1) + 1;
        playerToDraft = dataManager.getDraft().getAllPlayers().get(in);
        position = playerToDraft.getQP();
        //playerAvailable = setPlayerPosition(position, playerToDraft, positionsTable);
        
        //    while (!setPlayerPosition(position, playerToDraft, positionsTable) ) {
//            if (team.getStartupLine().size() == 23) {
//                playerAvailable = false;
//            } else {
                randNum = new Random();
                in = randNum.nextInt(dataManager.getDraft().getAllPlayers().size() - 1) + 1;
                playerToDraft = dataManager.getDraft().getAllPlayers().get(in);
                position = playerToDraft.getQP();
                setPlayerPosition(position, playerToDraft, positionsTable);
            //}
        //}
        if (team.getStartupLine().size() < 23) {

//            if (setPlayerPosition(position, playerToDraft, positionsTable)) {
//                bool = false;
            playerToDraft.setFantasyTeamName(dataManager.getDraft().getTeams().get(num).getTeamName());
            playerToDraft.setContract("S2");
            playerToDraft.setSalary(1);
            dataManager.getDraft().getTeams().get(num).addPlayerToStartingLineup(playerToDraft);
            dataManager.getDraft().removePlayer(playerToDraft);
            dataManager.getDraft().getDraftedPlayers().add(playerToDraft);
            dataManager.getDraft().sortTeam(dataManager.getDraft().getTeams().get(num));
         }else {
            if (dataManager.getDraft().getTeams().get(num2).getTaxiSquad().size() < 8) {
                randNum = new Random();
                in = randNum.nextInt(dataManager.getDraft().getAllPlayers().size() - 1) + 1;
                playerToDraft = dataManager.getDraft().getAllPlayers().get(in);
                //position = playerToDraft.getQP();
                Team team2 = dataManager.getDraft().getTeams().get(num2);
                playerToDraft.setFantasyTeamName(team2.getTeamName());
                playerToDraft.setContract("X");
                playerToDraft.setSalary(0);
                playerToDraft.setP(playerToDraft.getQP());
                team2.addPlayerToTaxiSquad(playerToDraft);
                dataManager.getDraft().removePlayer(playerToDraft);
                dataManager.getDraft().getDraftedPlayers().add(playerToDraft);
            } else {
                dialog = new MessageDialog(this, ("Close"));
                dialog.show("Draft table is filled");
            }
        }
    }

    private boolean setPlayerPosition(String position, Player playerToDraft, HashMap<String, Integer> positionTable) {
        if (position.contains("C") && (positionTable.get("C") > 0)) {
            playerToDraft.setP("C");
            positionTable.replace("C", positionTable.get("C") - 1);
            return true;
        }

        //CHECK TO SEE IF WE CAN ADD A "1B" TO THE COMBO BOX
        if (position.contains("1B") && (positionTable.get("1B") > 0)) {
            playerToDraft.setP("1B");
            positionTable.replace("1B", positionTable.get("1B") - 1);
            return true;
        }

        //CHECK TO SEE IF WE CAN ADD A "3B" TO THE COMBO BOX
        if (position.contains("3B") && (positionTable.get("3B") > 0)) {
            playerToDraft.setP("3B");
            positionTable.replace("3B", positionTable.get("3B") - 1);
            return true;
        }

        //CHECK TO SEE IF WE CAN ADD A "CI" TO THE COMBO BOX
        if ((positionTable.get("1B") == 0) && (positionTable.get("3B") == 0)) {
            if ((position.contains("1B") || position.contains("3B")) && (positionTable.get("CI") > 0)) {
                playerToDraft.setP("CI");
                positionTable.replace("CI", positionTable.get("CI") - 1);
                return true;
            }
        }
        //CHECK TO SEE IF WE CAN ADD A "2B" TO THE COMBO BOX
        if (position.contains("2B") && (positionTable.get("2B") > 0)) {
            playerToDraft.setP("2B");
            positionTable.replace("2B", positionTable.get("2B") - 1);
            return true;
        }

        //CHECK TO SEE IF WE CAN ADD A "SS" TO THE COMBO BOX
        if (position.contains("SS") && (positionTable.get("SS") > 0)) {
            playerToDraft.setP("SS");
            positionTable.replace("SS", positionTable.get("SS") - 1);
            return true;
        }

        //CHECK TO SEE IF WE CAN ADD A "2B" TO THE COMBO BOX
        if ((positionTable.get("2B") == 0) && (positionTable.get("SS") == 0)) {
            if ((position.contains("2B") || position.contains("SS")) && (positionTable.get("MI") > 0)) {
                playerToDraft.setP("MI");
                positionTable.replace("MI", positionTable.get("MI") - 1);
                return true;
            }
        }

        //CHECK TO SEE IF WE CAN ADD A "OF" TO THE COMBO BOX
        if (position.contains("OF") && (positionTable.get("OF") > 0)) {
            playerToDraft.setP("OF");
            positionTable.replace("OF", positionTable.get("OF") - 1);
            return true;
        }

        //CHECK TO SEE IF WE CAN ADD A "U" TO THE COMBO BOX
        if (position.contains("U") && (positionTable.get("U") > 0)) {
            playerToDraft.setP("U");
            positionTable.replace("U", positionTable.get("U") - 1);
            return true;
        }

        //CHECK TO SEE IF WE CAN ADD A "P" TO THE COMBO BOX
        if (position.contains("P") && (positionTable.get("P") > 0)) {
            playerToDraft.setP("P");
            positionTable.replace("P", positionTable.get("P") - 1);
            return true;
        }
        return false;
    }

    /**
     * This controller function is called in response to the user changing
     * course details in the UI. It responds by updating the bound Course object
     * using all the UI values, including the verification of that data.
     *
     * @param gui The user interface that requested the change.
     */
    public void handleDraftChangeRequest(WDK_GUI gui) {
        if (enabled) {
            try {
                // UPDATE THE COURSE, VERIFYING INPUT VALUES
                gui.updateDraftInfo(gui.getDataManager().getDraft());

                // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
                // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
                // THE SAVE BUTTON IS ENABLED
                gui.getFileController().markAsEdited(gui);
            } catch (Exception e) {
//                // SOMETHING WENT WRONG
                ErrorHandler eH = ErrorHandler.getErrorHandler();
                eH.handleUpdateDraftError();
            }
        }
    }

}
