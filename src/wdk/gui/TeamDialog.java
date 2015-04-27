/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wdk.data.Draft;
import wdk.data.Player;
import wdk.data.Team;
import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;
import static wdk.gui.WDK_GUI.CLASS_PROMPT_LABEL;
import static wdk.gui.WDK_GUI.PRIMARY_STYLE_SHEET;

/**
 *
 * @author Albert
 */
public class TeamDialog extends Stage {

    //This is the Objct data behind this UI
    Player player;
    Team team;

    //GUI CONTROLS FOR OUR DIALOG
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label nameLabel;
    TextField nameTextField;
    Label ownerLabel;
    TextField ownerTextField;
    Button completeButton;
    Button cancelButton;

    //Message dialog for errors?
    // MessageDialog messageDialog;
    //TIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;

// CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String NAME_PROMPT = "Name: ";
    public static final String OWNER_PROMPT = "Owner: ";
    public static final String POSITION_PROMPT = "Position: ";
    public static final String PRO_TEAM_PROMPT = "Pro team: ";
    public static final String CONTRACT_PROMPT = "Contract: ";
    public static final String SALARY_PROMPT = "Salary: ";
    public static final String R_PROMPT = "Runs: ";
    public static final String HR_PROMPT = "Home Runs: ";
    public static final String RBI_PROMPT = "Runs Batted In: ";
    public static final String SB_PROMPT = "Stolen Bases: ";
    public static final String W_PROMPT = "Wins: ";
    public static final String K_PROMPT = "Strikeouts: ";
    public static final String SV_PROMPT = "Saves: ";
    public static final String IP_PROMPT = "Innings Pitched: ";
    public static final String NOTES_PROMPT = "Notes: ";

    public static final String FANTASY_TEAM_HEADING = "Fantasy Team Details";
    public static final String ADD_TEAM_TITLE = "Add New Fantasy Team";
    public static final String EDIT_TEAM_TITLE = "Edit Fantasy Team";

    public TeamDialog(Stage primaryStage, Draft draft, MessageDialog messageDialog) {
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);

        // FIRST OUR CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(FANTASY_TEAM_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);

        // FIRST THE NAME 
        nameLabel = new Label(NAME_PROMPT);
        nameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        nameTextField = new TextField();
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           
            team.setTeamName(newValue);
        });

        // THEN THE LAST NAME 
        ownerLabel = new Label(OWNER_PROMPT);
        ownerLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        ownerTextField = new TextField();
        ownerTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            
            team.setTeamOwner(newValue);
        });

        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);

        cancelButton = new Button(CANCEL);

        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {

            Button sourceButton = (Button) ae.getSource();

            TeamDialog.this.selection = sourceButton.getText();
            TeamDialog.this.hide();

        };

        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE

        gridPane.add(headingLabel, 0, 0, 1, 1);
        gridPane.add(nameLabel, 0, 1, 1, 1);
        gridPane.add(nameTextField, 1, 1, 1, 1);
        gridPane.add(ownerLabel, 0, 2, 1, 1);
        gridPane.add(ownerTextField, 1, 2, 1, 1);
        gridPane.add(completeButton, 0, 3, 1, 1);
        gridPane.add(cancelButton, 1, 3, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
    }
//
//    public TeamDialog(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }


    /**
     * Accessor method for getting the selection the user made.
     *
     * @return Either YES, NO, or CANCEL, depending on which button the user
     * selected when this dialog was presented.
     */
    public String getSelection() {
        return selection;
    }

    public Team getTeamItem() {
        return team;
    }

    /**
     * This method loads a custom message into the label and then pops open the
     * dialog.
     *
     * @param message Message to appear inside the dialog.
     */
    public Team showAddTeamDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_TEAM_TITLE);

        // RESET THE SCHEDULE ITEM OBJECT WITH DEFAULT VALUES
        team = new Team();

        // LOAD THE UI STUFF
        nameTextField.setText(team.getTeamName());
        ownerTextField.setText(team.getTeamOwner());
        

        // AND OPEN IT UP
        this.showAndWait();

        return team;
    }

    
    public void loadGUIData() {
        // LOAD THE UI STUFF
        nameTextField.setText(team.getTeamName());
        ownerTextField.setText(team.getTeamOwner());       
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
public void showEditTeamDialog(Team teamToEdit) {
        // SET THE DIALOG TITLE
        setTitle(EDIT_TEAM_TITLE);
        
        // LOAD THE SCHEDULE ITEM INTScheduleItemO OUR LOCAL OBJECT
        team = new Team();
        nameTextField.setText(teamToEdit.getTeamName());
        
        ownerTextField.setText(teamToEdit.getTeamOwner()); 
        
        // AND THEN INTO OUR GUI
        loadGUIData();
               
        // AND OPEN IT UP
        this.showAndWait();
    }
}