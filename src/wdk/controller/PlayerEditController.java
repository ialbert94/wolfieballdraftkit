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

/**
 *
 * @author Albert
 */
public class PlayerEditController {
//    PlayerDialog pd;
//    MessageDialog messageDialog;
//    YesNoCancelDialog yesNoCancelDialog;
//    
//    public PlayerEditController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
//        pd = new PlayerDialog(initPrimaryStage, draft, initMessageDialog);
//        messageDialog = initMessageDialog;
//        yesNoCancelDialog = initYesNoCancelDialog;
//    }
//
//    // THESE ARE FOR SCHEDULE ITEMS
//    
//    public void handleAddAssignmentRequest(WDK_GUI gui) {
//        DraftDataManager ddm = gui.getDataManager();
//        Draft draft = ddm.getDraft();
//        pd.showAddAssignmentDialog(draft);
//        
//        // DID THE USER CONFIRM?
//        if (pd.wasCompleteSelected()) {
//            // GET THE SCHEDULE ITEM
//            Player p = pd.getPlayerItem();
//            
//            // AND ADD IT AS A ROW TO THE TABLE
//            draft.addPlayer(p);
//            gui.updateToolbarControls(false);
//        }
//        else {
//            // THE USER MUST HAVE PRESSED CANCEL, SO
//            // WE DO NOTHING
//        }
//    }
//    
//    public void handleEditAssignmentRequest(WDK_GUI gui, Player playerToEdit) {
//        DraftDataManager ddm = gui.getDataManager();
//        Draft draft = ddm.getCourse();
//        pd.showEditScheduleItemDialog(playerToEdit);
//        
//        // DID THE USER CONFIRM?
//        if (pd.wasCompleteSelected()) {
//            // UPDATE THE SCHEDULE ITEM
//            Player a = pd.getPlayerItem();
//            playerToEdit.setName(a.getName());
//            playerToEdit.setDate(a.getDate());
//            playerToEdit.setTopics(a.getTopics());
//            //course.editAssignments();
//            gui.updateToolbarControls(false);
//        }
//        else {
//            // THE USER MUST HAVE PRESSED CANCEL, SO
//            // WE DO NOTHING
//        }        
//    }
//    
//    public void handleRemoveAssignmentRequest(WDK_GUI gui, Player playerToRemove) {
//        // PROMPT THE USER TO SAVE UNSAVED WORK
//        yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_PLAYER_MESSAGE));
//        
//        // AND NOW GET THE USER'S SELECTION
//        String selection = yesNoCancelDialog.getSelection();
//
//        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
//        if (selection.equals(YesNoCancelDialog.YES)) { 
//            gui.getDataManager().getDraft().removePlayer(playerToRemove);
//            gui.updateToolbarControls(false);
//        }
//    }
}

