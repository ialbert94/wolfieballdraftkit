/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.controller;

import javafx.stage.Stage;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import wdk.data.Player;
import wdk.gui.MessageDialog;
import wdk.gui.PlayerDialog;
import wdk.gui.WDK_GUI;
import wdk.gui.YesNoCancelDialog;
import properties_manager.PropertiesManager;
import static wdk.WDK_PropertyType.REMOVE_PLAYER_MESSAGE;
import static wdk.WDK_PropertyType.REMOVE_TEAM_MESSAGE;
import wdk.data.Team;
import wdk.gui.TeamDialog;

/**
 *
 * @author Albert
 */
public class TeamEditController {
    TeamDialog td;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public TeamEditController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        td = new TeamDialog(initPrimaryStage, draft, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }

      // THESE ARE FOR PLAUERS
    
    public void handleAddTeamRequest(WDK_GUI gui) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        td.showAddTeamDialog();
        
        // DID THE USER CONFIRM?
        if (td.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Team t = td.getTeamItem();
            
            // AND ADD IT AS A ROW TO THE TABLE
            draft.addToTeams(t);
            //I WILL PROBABLY NEED TO DO SOMETHING TO RELOAD AND 
            //REFRESH THE TABLE ONCE I ADD A PLAYER
            //FOR EXAMPLE, IF I AM ON P, AND I ADD A PLAYER TO THE TABLE
            //THAT IS A CATCHER, IT SHOULDN'T SHOW
            //LIKEWISE IF I ADD A PLAYER THAT IS A CATCHER, AND IM ON THE C
            //RADIO BUTTON, HE SHOULD BE THERE
            gui.updateToolbarControls(false);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }
    
    public void handleEditTeamRequest(WDK_GUI gui, Team teamToEdit) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        td.showEditTeamDialog(teamToEdit);
        
        // DID THE USER CONFIRM?
        if (td.wasCompleteSelected()) {
           // UPDATE THE SCHEDULE ITEM
              Team t = td.getTeamItem();
              teamToEdit.setTeamName(t.getTeamName());
              teamToEdit.setTeamOwner(t.getTeamOwner());

            gui.updateToolbarControls(false);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    
    public void handleRemoveTeamRequest(WDK_GUI gui, Team teamToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_TEAM_MESSAGE));
      
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            for(Player player : teamToRemove.getStartupLine()){
                gui.getDataManager().getDraft().addToAllPlayers(player);
            }
            gui.getDataManager().getDraft().removeTeam(teamToRemove);
            gui.updateToolbarControls(false);
        }
    }
}
