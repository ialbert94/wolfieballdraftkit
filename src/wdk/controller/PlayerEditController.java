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
import wdk.data.Team;

/**
 *
 * @author Albert
 */
public class PlayerEditController {
    PlayerDialog pd;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
    public PlayerEditController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        pd = new PlayerDialog(initPrimaryStage, draft, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }

      // THESE ARE FOR PLAUERS
    
    public void handleAddPlayerRequest(WDK_GUI gui) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        pd.showAddPlayerDialog();
        
        // DID THE USER CONFIRM?
        if (pd.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Player p = pd.getPlayerItem();
            
            // AND ADD IT AS A ROW TO THE TABLE
            draft.addToAllPlayers(p);
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
    
    public void handleEditPlayerRequest(WDK_GUI gui, Player playerToEdit) {
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        pd.showEditPlayerDialog(playerToEdit);
        
        // DID THE USER CONFIRM?
        if (pd.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
              Player p = pd.getPlayerItem();
              playerToEdit.setP(p.getP());
              playerToEdit.setContract(p.getContract());
              playerToEdit.setSalary(p.getSalary());
              //draft.getTeamItem(null)
//              teamToEdit.addPlayerToStartingLineup(playerToEdit);
//            playerToEdit.setPreviousTeam(p.getPreviousTeam());
//            playerToEdit.setNotes(p.getNotes());
//            playerToEdit.setYearOfBirth(p.getYearOfBirth());
//            playerToEdit.setH(p.getH());
//            playerToEdit.setContract(p.getContract());
//            playerToEdit.setSalary(p.getSalary());
//            playerToEdit.setIP(p.getIP());
//            playerToEdit.setER(p.getER());
//            playerToEdit.setW(p.getW());
//            playerToEdit.setSV(p.getSV());

            
            //course.editAssignments();
            gui.updateToolbarControls(false);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }
    
    public void handleRemoveAssignmentRequest(WDK_GUI gui, Player playerToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_PLAYER_MESSAGE));
      
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getDraft().removePlayer(playerToRemove);
            gui.updateToolbarControls(false);
        }
    }
}

