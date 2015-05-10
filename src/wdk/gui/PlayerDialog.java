/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.gui;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wdk.data.Draft;
import wdk.data.Player;
import wdk.data.Team;
import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;
import static wdk.gui.WDK_GUI.CLASS_PROMPT_LABEL;
import static wdk.gui.WDK_GUI.CLASS_SUBHEADING_LABEL;
import static wdk.gui.WDK_GUI.PRIMARY_STYLE_SHEET;

/**
 *
 * @author Albert
 */
public class PlayerDialog extends Stage {

    //This is the Objct data behind this UI
    Player player;

    //GUI CONTROLS FOR OUR DIALOG
    GridPane addGridPane;
    GridPane editGridPane;
    Scene dialogScene;
    Label headingLabel;
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label proTeamLabel;
    ComboBox proTeamComboBox;
    Button completeButton;
    Button cancelButton;
    MessageDialog dialog;
    CheckBox cbC;
    Label cbCLabel;
    CheckBox cb1B;
    Label cb1BLabel;
    CheckBox cb2B;
    Label cb2BLabel;
    CheckBox cb3B;
    Label cb3BLabel;
    CheckBox cbSS;
    Label cbSSLabel;
    CheckBox cbOF;
    Label cbOFLabel;
    CheckBox cbP;
    Label cbPLabel;

    //Message dialog for errors?
    // MessageDialog messageDialog;
    //TIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;

// CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String FIRST_NAME_PROMPT = "First Name: ";
    public static final String LAST_NAME_PROMPT = "Last Name: ";
    public static final String POSITION_PROMPT = "Position: ";
    public static final String PRO_TEAM_PROMPT = "Pro team: ";
    public static final String FANTASY_TEAM_PROMPT = "Fantasy Team:";
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

    public static final String FLAGS_DIR = "./images/flags/";
    public static final String PLAYERS_DIR = "./images/players/";

    public static final String PLAYER_HEADING = "Player Details";
    public static final String ADD_PLAYER_TITLE = "Add New Player";
    public static final String EDIT_PLAYER_TITLE = "Edit Player";
    private Label playerNameLabel;
    private Label playerPositionLabel;
    private Label fantasyTeamLabel;
    private Label positionLabel;
    private Label contractLabel;
    private Label salaryLabel;
    private ComboBox fantasyTeamComboBox;
    private ComboBox positionComboBox;
    private ComboBox contractComboBox;
    private TextField salaryTextField;
    private Scene editDialogScene;

    public PlayerDialog(Stage primaryStage, Draft draft, MessageDialog messageDialog) {
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);

        cancelButton = new Button(CANCEL);
    }

    /**
     * Accessor method for getting the selection the user made.
     *
     * @return Either YES, NO, or CANCEL, depending on which button the user
     * selected when this dialog was presented.
     */
    public String getSelection() {
        return selection;
    }

    public Player getPlayerItem() {
        return player;
    }

    /**
     * This method loads a custom message into the label and then pops open the
     * dialog.
     *
     * @param message Message to appear inside the dialog.
     */
    public Player showAddPlayerDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_PLAYER_TITLE);

        // RESET THE SCHEDULE ITEM OBJECT WITH DEFAULT VALUES
        player = new Player();

        // FIRST OUR CONTAINER
        addGridPane = new GridPane();
        addGridPane.setPadding(new Insets(10, 20, 20, 20));
        addGridPane.setHgap(10);
        addGridPane.setVgap(10);

        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);

        // NOW THE FIRST NAME 
        firstNameLabel = new Label(FIRST_NAME_PROMPT);
        firstNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        firstNameTextField = new TextField();
        firstNameTextField.autosize();
        firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String firstName = newValue.substring(0, 1).toUpperCase() + newValue.substring(1);
            player.setFirstName(firstName);
        });

        // THEN THE LAST NAME 
        lastNameLabel = new Label(LAST_NAME_PROMPT);
        lastNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        lastNameTextField = new TextField();
        lastNameTextField.autosize();
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String lastName = newValue.substring(0, 1).toUpperCase() + newValue.substring(1);
            player.setLastName(lastName);
        });

        // THEN THE PRO TEAM COMBOBOX 
        proTeamLabel = new Label(PRO_TEAM_PROMPT);
        proTeamLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        proTeamComboBox = new ComboBox();

        //ADD THESE TEAMS IN THIS ORDER
        //ATL, AZ, CHC, CIN, COL, LAD, MIA, MIL, NYM, PHI, PIT, SD, SF, STL, WAS
        proTeamComboBox.getItems().add("ATL");
        proTeamComboBox.getItems().add("AZ");
        proTeamComboBox.getItems().add("CHC");
        proTeamComboBox.getItems().add("CIN");
        proTeamComboBox.getItems().add("COL");
        proTeamComboBox.getItems().add("LAD");
        proTeamComboBox.getItems().add("MIA");
        proTeamComboBox.getItems().add("MIL");
        proTeamComboBox.getItems().add("NYM");
        proTeamComboBox.getItems().add("PHI");
        proTeamComboBox.getItems().add("PIT");
        proTeamComboBox.getItems().add("SD");
        proTeamComboBox.getItems().add("SF");
        proTeamComboBox.getItems().add("STL");
        proTeamComboBox.getItems().add("WAS");
        proTeamComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            player.setPreviousTeam(newValue.toString());
        });

        // AND THEN THE POSITION CHECK BOXES 
        cbC = new CheckBox();
        cbCLabel = new Label("C");
        cb1B = new CheckBox();
        cb1BLabel = new Label("1B");
        cb2B = new CheckBox();
        cb2BLabel = new Label("2B");
        cb3B = new CheckBox();
        cb3BLabel = new Label("3B");
        cbSS = new CheckBox();
        cbSSLabel = new Label("SS");
        cbOF = new CheckBox();
        cbOFLabel = new Label("OF");
        cbP = new CheckBox();
        cbPLabel = new Label("P");

        cbCLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        cb1BLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        cb2BLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        cb3BLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        cbSSLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        cbOFLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        cbPLabel.getStyleClass().add(CLASS_PROMPT_LABEL);

        cbP.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (cbP.isSelected()) {
                cbC.setSelected(false);
                cb1B.setSelected(false);
                cb2B.setSelected(false);
                cb3B.setSelected(false);
                cbSS.setSelected(false);
                cbOF.setSelected(false);
                cbP.setSelected(true);
//                player.setQP(cbPLabel.getText());
            }
        });
        cbC.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (player.getQP() != null) {
                cbP.setSelected(false);
            }
        });
        cb1B.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (player.getQP() != null) {
                cbP.setSelected(false);
            }
        });
        cb2B.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (player.getQP() != null) {
                cbP.setSelected(false);
            }
        });
        cb3B.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (player.getQP() != null) {
                cbP.setSelected(false);
            }
        });
        cbSS.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (player.getQP() != null) {
                cbP.setSelected(false);
            }
        });
        cbOF.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (player.getQP() != null) {
                cbP.setSelected(false);
            }
        });

        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);

        cancelButton = new Button(CANCEL);

        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeHandlerAdd = (EventHandler<ActionEvent>) (ActionEvent ae) -> {

            if (firstNameTextField.getText() != null && lastNameTextField.getText() != null
                    && proTeamComboBox.getValue() != null && (cbC.isSelected()
                    || cb1B.isSelected() || cb2B.isSelected() || cb3B.isSelected()
                    || cbSS.isSelected() || cbOF.isSelected() || cbP.isSelected())) {

                if (cbP.isSelected()) {
                    player.setQP(cbPLabel.getText());
                }

                if (cbC.isSelected()) {
                    if (player.getQP().isEmpty()) {
                        player.setQP(cbCLabel.getText());
                    } else {
                        player.setQP(player.getQP() + "_" + cbCLabel.getText());
                    }
                }

                if (cb1B.isSelected()) {
                    if (player.getQP().isEmpty()) {
                        player.setQP(cb1BLabel.getText());
                    } else {
                        player.setQP(player.getQP() + "_" + cb1BLabel.getText());
                    }
                }
                if (cb2B.isSelected()) {
                    if (player.getQP().isEmpty()) {
                        player.setQP(cb2BLabel.getText());
                    } else {
                        player.setQP(player.getQP() + "_" + cb2BLabel.getText());
                    }
                }
                if (cb3B.isSelected()) {
                    if (player.getQP().isEmpty()) {
                        player.setQP(cb3BLabel.getText());
                    } else {
                        player.setQP(player.getQP() + "_" + cb3BLabel.getText());
                    }
                }
                if (cbSS.isSelected()) {
                    if (player.getQP().isEmpty()) {
                        player.setQP(cbSSLabel.getText());
                    } else {
                        player.setQP(player.getQP() + "_" + cbSSLabel.getText());
                    }
                }
                if (cbOF.isSelected()) {
                    if (player.getQP().isEmpty()) {
                        player.setQP(cbOFLabel.getText());
                    } else {
                        player.setQP(player.getQP() + "_" + cbOFLabel.getText());
                    }
                }
                if (!cbP.isSelected()) {
                    player.setQP(player.getQP() + "_" + "U");
                }
                Button sourceButton = (Button) ae.getSource();

                selection = sourceButton.getText();
                hide();
            } else {
                dialog = new MessageDialog(this, ("Close"));
                dialog.show("Cannot complete, necessary fields not completed");
            }
        };

        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler cancelHandlerAdd = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button) ae.getSource();
            selection = sourceButton.getText();
            hide();
        };

        completeButton.setOnAction(completeHandlerAdd);
        cancelButton.setOnAction(cancelHandlerAdd);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        addGridPane.add(headingLabel, 0, 0, 19, 1);
        addGridPane.add(firstNameLabel, 0, 1, 19, 1);
        addGridPane.add(firstNameTextField, 1, 1, 19, 1);
        addGridPane.add(lastNameLabel, 0, 2, 19, 1);
        addGridPane.add(lastNameTextField, 1, 2, 19, 1);
        addGridPane.add(proTeamLabel, 0, 3, 19, 1);
        addGridPane.add(proTeamComboBox, 1, 3, 19, 1);
        addGridPane.addRow(4, cbC, cbCLabel, cb1B, cb1BLabel, cb2B, cb2BLabel, cb3B, cb3BLabel, cbSS, cbSSLabel, cbOF, cbOFLabel, cbP, cbPLabel);
        addGridPane.add(completeButton, 0, 7, 1, 1);
        addGridPane.add(cancelButton, 1, 7, 1, 1);
        addGridPane.autosize();
        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(addGridPane);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        setScene(dialogScene);

        // LOAD THE UI STUFF
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        proTeamComboBox.valueProperty().set(null);
        cbC.setSelected(false);
        cb1B.setSelected(false);
        cb2B.setSelected(false);
        cb3B.setSelected(false);
        cbSS.setSelected(false);
        cbOF.setSelected(false);
        cbP.setSelected(false);

        // AND OPEN IT UP
        showAndWait();

        return player;
    }

    public void loadGUIData() {
        // LOAD THE UI STUFF
//        nameTextField.setText(assignment.getName());
//        datePicker.setValue(assignment.getDate());
//        topicsTextField.setText(assignment.getTopics());       
    }

    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }

    public Player showEditPlayerDialog(Player playerToEdit, Draft draft) {
        // SET THE DIALOG TITLE
        setTitle(EDIT_PLAYER_TITLE);

        // LOAD THE SCHEDULE ITEM INTScheduleItemO OUR LOCAL OBJECT
        //player = new Player();
        editGridPane = new GridPane();
        editGridPane.setPadding(new Insets(10, 20, 20, 20));
        editGridPane.setHgap(10);
        editGridPane.setVgap(10);

        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);

        String playerPath = "file:" + PLAYERS_DIR + playerToEdit.getLastName() + playerToEdit.getFirstName() + (".jpg");
        String flagPath = "file:" + FLAGS_DIR + playerToEdit.getNationOfBirth() + (".png");

        Image playerImage = new Image(playerPath);
        Image flagImage = new Image(flagPath);
        ImageView iv1 = new ImageView();
        ImageView iv2 = new ImageView();
        iv1.setImage(playerImage);
        iv2.setImage(flagImage);

        String playerName = playerToEdit.getFirstName() + " " + playerToEdit.getLastName();
        playerNameLabel = new Label(playerName);
        playerNameLabel.getStyleClass().add(CLASS_SUBHEADING_LABEL);
        String playerPosition = playerToEdit.getQP();
        playerPositionLabel = new Label(playerPosition);
        playerPositionLabel.getStyleClass().add(CLASS_SUBHEADING_LABEL);

        fantasyTeamLabel = new Label(FANTASY_TEAM_PROMPT);
        positionLabel = new Label(POSITION_PROMPT);
        contractLabel = new Label(CONTRACT_PROMPT);
        salaryLabel = new Label(SALARY_PROMPT);

        ArrayList<String> teamNameList = new ArrayList();
        teamNameList.add("Free Agent");
        for (int i = 0; i < draft.getTeams().size(); i++) {
            teamNameList.add(draft.getTeams().get(i).getTeamName());
        }
        fantasyTeamComboBox = new ComboBox();
        positionComboBox = new ComboBox();
        fantasyTeamComboBox.getItems().addAll(teamNameList);
        fantasyTeamComboBox.setValue(teamNameList.get(0));
        fantasyTeamComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            playerToEdit.setFantasyTeamName(newValue.toString());
            positionComboBox.getSelectionModel().clearSelection();
            positionComboBox.getItems().clear();
            ArrayList<String> position = new ArrayList();
            HashMap<String, Integer> positionTable = new HashMap<>();
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

            String positions = playerToEdit.getQP();
            Team team = draft.getTeamItem(fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString());
            if (team != null) {
                for (Player p : team.getStartupLine()) {
                    String pos = p.getP();
                    positionTable.replace(pos, positionTable.get(pos) - 1);
                }
            }

            //CHECK TO SEE IF WE CAN ADD A "C" TO THE COMBO BOX
            if (positions.contains("C") && (positionTable.get("C") > 0)
                    && !fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")) {
                position.add("C");

                positionTable.replace("C", positionTable.get("C") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "1B" TO THE COMBO BOX
            if (positions.contains("1B") && (positionTable.get("1B") > 0)
                    && !fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")) {
                position.add("1B");

                positionTable.replace("1B", positionTable.get("1B") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "3B" TO THE COMBO BOX
            if (positions.contains("3B") && (positionTable.get("3B") > 0)
                    && !fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")) {
                position.add("3B");

                positionTable.replace("3B", positionTable.get("3B") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "CI" TO THE COMBO BOX
            if ((positions.contains("1B") || position.contains("3B"))
                    && (positionTable.get("CI") > 0) && !fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")) {
                position.add("CI");

                positionTable.replace("CI", positionTable.get("CI") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "2B" TO THE COMBO BOX
            if (positions.contains("2B") && (positionTable.get("2B") > 0)
                    && !fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")) {
                position.add("2B");

                positionTable.replace("2B", positionTable.get("2B") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "SS" TO THE COMBO BOX
            if (positions.contains("SS") && (positionTable.get("SS") > 0)
                    && !fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")) {
                position.add("SS");

                positionTable.replace("SS", positionTable.get("SS") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "2B" TO THE COMBO BOX
            if ((positions.contains("2B") || position.contains("SS"))
                    && (positionTable.get("MI") > 0) && !fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")) {
                position.add("MI");

                positionTable.replace("MI", positionTable.get("MI") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "OF" TO THE COMBO BOX
            if (positions.contains("OF") && (positionTable.get("OF") > 0)
                    && !fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")) {
                position.add("OF");

                positionTable.replace("OF", positionTable.get("OF") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "U" TO THE COMBO BOX
            if (positions.contains("U") && (positionTable.get("U") > 0)
                    && !fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")) {
                position.add("U");

                positionTable.replace("U", positionTable.get("U") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "P" TO THE COMBO BOX
            if (positions.contains("P") && (positionTable.get("P") > 0)
                    && !fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")) {
                position.add("P");

                positionTable.replace("P", positionTable.get("P") - 1);
            }
            if (fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")) {
                positionComboBox.getItems().clear();
            }
            //HERE WE WILL IMPLEMENT FOOL PROOF DESIGN. 
            //FIGURE OUT A WAY TO CHECK ALL AVAILABLE POSITIONS OF THE PLAYER
            //VS ALL AVAILPOSIITONS LEFT FOR THE TEAM AND ONLY ADD THOSE
            positionComboBox.getSelectionModel().clearSelection();
            positionComboBox.getItems().clear();
            positionComboBox.getSelectionModel().clearSelection();
            positionComboBox.getItems().addAll(position);

        });

        positionComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                playerToEdit.setP(newValue.toString());
            }
        });

        ArrayList<String> contract = new ArrayList();

        contract.add("S2");
        contract.add("S1");
        contract.add("X");
        contractComboBox = new ComboBox();
        contractComboBox.prefWidth(100);
        contractComboBox.getItems().addAll(contract);
        contractComboBox.getSelectionModel().clearSelection();
        contractComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            playerToEdit.setContract(newValue.toString());
        });

        salaryTextField = new TextField();

        salaryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int sel = Integer.parseInt(newValue);
                playerToEdit.setSalary(sel);
            } catch (Exception e) {
                if (salaryTextField.getText().length() > 0) {
//                    dialog = new MessageDialog(this, ("Close"));
//                    dialog.show("Numbers only");
                    salaryTextField.setText(newValue.substring(0, newValue.length() - 1));
                }
            }
        });

        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);

        cancelButton = new Button(CANCEL);

        EventHandler completeHandlerEdit = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            if (!fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString().equals("Free Agent")
                    && positionComboBox.getSelectionModel().getSelectedItem() != null
                    && positionComboBox.getSelectionModel().getSelectedItem() != null
                    && !salaryTextField.getText().isEmpty()) {
                String teamName = fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString();
                Team team = draft.getTeamItem(teamName);
                int num = Integer.parseInt(salaryTextField.getText());
                if (num > team.getPricePP()) {
                    dialog = new MessageDialog(this, ("Close"));
                    dialog.show("Price per player is too high, please re-enter.");
                } else {
                    Button sourceButton = (Button) ae.getSource();
                    selection = sourceButton.getText();
                    hide();
                }
            } else {

            }
        };

        EventHandler cancelHandlerEdit = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button) ae.getSource();
            selection = sourceButton.getText();
            hide();
        };

        completeButton.setOnAction(completeHandlerEdit);

        cancelButton.setOnAction(cancelHandlerEdit);

        //gridPane.add(iv2, columnIndex, rowIndex, colspan, rowspan);
        editGridPane.add(headingLabel, 0, 0, 2, 1);
        editGridPane.add(iv1, 0, 1, 1, 3);
        editGridPane.add(iv2, 1, 1, 1, 1);
        editGridPane.add(playerNameLabel, 1, 2, 1, 1);
        editGridPane.add(playerPositionLabel, 1, 3, 1, 1);
        editGridPane.add(fantasyTeamLabel, 0, 4, 1, 1);
        editGridPane.add(fantasyTeamComboBox, 1, 4, 1, 1);
        editGridPane.add(positionLabel, 0, 5, 1, 1);
        editGridPane.add(positionComboBox, 1, 5, 1, 1);
        editGridPane.add(contractLabel, 0, 6, 1, 1);
        editGridPane.add(contractComboBox, 1, 6, 1, 1);
        editGridPane.add(salaryLabel, 0, 7, 1, 1);
        editGridPane.add(salaryTextField, 1, 7, 1, 1);
        editGridPane.add(completeButton, 0, 8, 1, 1);
        editGridPane.add(cancelButton, 1, 8, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        editDialogScene = new Scene(editGridPane);

        editDialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);

        setScene(editDialogScene);

        // AND OPEN IT UP
        showAndWait();
        return playerToEdit;
    }

    public void showEditTeamScreenDialog(Player playerToEdit, Draft draft) {
        // SET THE DIALOG TITLE
        setTitle(EDIT_PLAYER_TITLE);

        // LOAD THE SCHEDULE ITEM INTScheduleItemO OUR LOCAL OBJECT
        player = new Player();
        editGridPane = new GridPane();
        editGridPane.setPadding(new Insets(10, 20, 20, 20));
        editGridPane.setHgap(10);
        editGridPane.setVgap(10);

        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);

        String playerPath = "";
        playerPath = "file:" + PLAYERS_DIR + playerToEdit.getLastName() + playerToEdit.getFirstName() + (".jpg");
        String flagPath = "";
        flagPath = "file:" + FLAGS_DIR + playerToEdit.getNationOfBirth() + (".png");

        Image playerImage = new Image(playerPath);
        Image flagImage = new Image(flagPath);
        ImageView iv1 = new ImageView();
        ImageView iv2 = new ImageView();
        iv1.setImage(playerImage);
        iv2.setImage(flagImage);

        String playerName = playerToEdit.getFirstName() + " " + playerToEdit.getLastName();
        playerNameLabel = new Label(playerName);
        playerNameLabel.getStyleClass().add(CLASS_SUBHEADING_LABEL);
        String playerPosition = playerToEdit.getQP();
        playerPositionLabel = new Label(playerPosition);
        playerPositionLabel.getStyleClass().add(CLASS_SUBHEADING_LABEL);

        fantasyTeamLabel = new Label(FANTASY_TEAM_PROMPT);
        positionLabel = new Label(POSITION_PROMPT);
        contractLabel = new Label(CONTRACT_PROMPT);
        salaryLabel = new Label(SALARY_PROMPT);

        ArrayList<String> teamNameList = new ArrayList();
        teamNameList.add("Free Agent");
        for (int i = 0; i < draft.getTeams().size(); i++) {
            teamNameList.add(draft.getTeams().get(i).getTeamName());
        }
        fantasyTeamComboBox = new ComboBox();
        positionComboBox = new ComboBox();
        fantasyTeamComboBox.getItems().addAll(teamNameList);
        fantasyTeamComboBox.getSelectionModel().clearSelection();
        fantasyTeamComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            //playerToEdit.setFantasyTeamName(newValue.toString());
            player.setFantasyTeamName(newValue.toString());
            positionComboBox.getSelectionModel().clearSelection();
            positionComboBox.getItems().clear();
            ArrayList<String> position = new ArrayList();
            HashMap<String, Integer> positionTable = new HashMap<>();
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

            String positions = playerToEdit.getQP();
            Team team = draft.getTeamItem(fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString());
            if (team != null) {
                for (Player p : team.getStartupLine()) {
                    String pos = p.getP();
                    positionTable.replace(pos, positionTable.get(pos) - 1);
                }
            }

            //CHECK TO SEE IF WE CAN ADD A "C" TO THE COMBO BOX
            if (positions.contains("C") && (positionTable.get("C") > 0)) {
                position.add("C");

                positionTable.replace("C", positionTable.get("C") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "1B" TO THE COMBO BOX
            if (positions.contains("1B") && (positionTable.get("1B") > 0)) {
                position.add("1B");

                positionTable.replace("1B", positionTable.get("1B") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "3B" TO THE COMBO BOX
            if (positions.contains("3B") && (positionTable.get("3B") > 0)) {
                position.add("3B");

                positionTable.replace("3B", positionTable.get("3B") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "CI" TO THE COMBO BOX
            if ((positions.contains("1B") || position.contains("3B"))) {
                position.add("CI");

                positionTable.replace("CI", positionTable.get("CI") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "2B" TO THE COMBO BOX
            if (positions.contains("2B") && (positionTable.get("2B") > 0)) {
                position.add("2B");

                positionTable.replace("2B", positionTable.get("2B") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "SS" TO THE COMBO BOX
            if (positions.contains("SS") && (positionTable.get("SS") > 0)) {
                position.add("SS");

                positionTable.replace("SS", positionTable.get("SS") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "2B" TO THE COMBO BOX
            if ((positions.contains("2B") || position.contains("SS"))) {
                position.add("MI");

                positionTable.replace("MI", positionTable.get("MI") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "OF" TO THE COMBO BOX
            if (positions.contains("OF") && (positionTable.get("OF") > 0)) {
                position.add("OF");

                positionTable.replace("OF", positionTable.get("OF") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "U" TO THE COMBO BOX
            if (positions.contains("U") && (positionTable.get("U") > 0)) {
                position.add("U");

                positionTable.replace("U", positionTable.get("U") - 1);
            }

            //CHECK TO SEE IF WE CAN ADD A "P" TO THE COMBO BOX
            if (positions.contains("P") && (positionTable.get("P") > 0)) {
                position.add("P");

                positionTable.replace("P", positionTable.get("P") - 1);
            }

            //HERE WE WILL IMPLEMENT FOOL PROOF DESIGN. 
            //FIGURE OUT A WAY TO CHECK ALL AVAILABLE POSITIONS OF THE PLAYER
            //VS ALL AVAILPOSIITONS LEFT FOR THE TEAM AND ONLY ADD THOSE
            positionComboBox.getSelectionModel().clearSelection();
            positionComboBox.getItems().clear();
            positionComboBox.getSelectionModel().clearSelection();
            positionComboBox.getItems().addAll(position);

        });

        positionComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                //playerToEdit.setP(newValue.toString());
                player.setP(newValue.toString());
            }
        });

        ArrayList<String> contract = new ArrayList();

        contract.add("S2");
        contract.add("S1");
        contract.add("X");
        contractComboBox = new ComboBox();
        contractComboBox.prefWidth(100);
        contractComboBox.getItems().addAll(contract);
        contractComboBox.getSelectionModel().clearSelection();
        contractComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            // playerToEdit.setContract(newValue.toString());
            player.setContract(newValue.toString());
        });

        salaryTextField = new TextField();

        salaryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int sel = Integer.parseInt(newValue);
                //playerToEdit.setSalary(sel);
                player.setSalary(sel);
            } catch (Exception e) {
                if (salaryTextField.getText().length() > 0) {
                    dialog = new MessageDialog(this, ("Close"));
                    dialog.show("Numbers only");
                    salaryTextField.setText(newValue.substring(0, newValue.length() - 1));
                }
            }
        });

        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);

        cancelButton = new Button(CANCEL);

        EventHandler completeHandlerEdit = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            if (positionComboBox.getSelectionModel().getSelectedItem() != null
                    && positionComboBox.getSelectionModel().getSelectedItem() != null
                    && !salaryTextField.getText().isEmpty()) {
                String teamName = fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString();
                Team team = draft.getTeamItem(teamName);
                int num = Integer.parseInt(salaryTextField.getText());
                if (num > team.getPricePP()) {
                    dialog = new MessageDialog(this, ("Close"));
                    dialog.show("Price per player is too high, please re-enter.");
                } else {
                    Button sourceButton = (Button) ae.getSource();
                    selection = sourceButton.getText();
                    hide();
                }
            } else if (fantasyTeamComboBox.getSelectionModel().getSelectedItem().equals("Free Agent")) {
                Button sourceButton = (Button) ae.getSource();
                selection = sourceButton.getText();
                hide();
            } else {

            }
        };

        EventHandler cancelHandlerEdit = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button) ae.getSource();
            selection = sourceButton.getText();
            hide();
        };

        completeButton.setOnAction(completeHandlerEdit);

        cancelButton.setOnAction(cancelHandlerEdit);

        //gridPane.add(iv2, columnIndex, rowIndex, colspan, rowspan);
        editGridPane.add(headingLabel, 0, 0, 2, 1);
        editGridPane.add(iv1, 0, 1, 1, 3);
        editGridPane.add(iv2, 1, 1, 1, 1);
        editGridPane.add(playerNameLabel, 1, 2, 1, 1);
        editGridPane.add(playerPositionLabel, 1, 3, 1, 1);
        editGridPane.add(fantasyTeamLabel, 0, 4, 1, 1);
        editGridPane.add(fantasyTeamComboBox, 1, 4, 1, 1);
        editGridPane.add(positionLabel, 0, 5, 1, 1);
        editGridPane.add(positionComboBox, 1, 5, 1, 1);
        editGridPane.add(contractLabel, 0, 6, 1, 1);
        editGridPane.add(contractComboBox, 1, 6, 1, 1);
        editGridPane.add(salaryLabel, 0, 7, 1, 1);
        editGridPane.add(salaryTextField, 1, 7, 1, 1);
        editGridPane.add(completeButton, 0, 8, 1, 1);
        editGridPane.add(cancelButton, 1, 8, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        editDialogScene = new Scene(editGridPane);

        editDialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);

        setScene(editDialogScene);

        // AND OPEN IT UP
        showAndWait();
        //return playerToEdit;
    }
}
