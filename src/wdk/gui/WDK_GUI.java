package wdk.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import static javafx.application.ConditionalFeature.FXML;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import static wdk.WDK_StartupConstants.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import properties_manager.PropertiesManager;
import wdk.WDK_PropertyType;
import wdk.controller.DraftEditController;
import wdk.controller.FileController;
import wdk.controller.PlayerEditController;
import wdk.controller.TeamEditController;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import wdk.data.DraftDataView;
import wdk.data.Player;
import wdk.data.Team;
import wdk.file.DraftFileManager;
import wdk.file.DraftSiteExporter;

/**
 *
 * @author Albert
 */
public class WDK_GUI implements DraftDataView {

    static final String PRIMARY_STYLE_SHEET = PATH_CSS + "wdk_style.css";
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_SUBJECT_PANE = "subject_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    static final String CLASS_PROMPT_LABEL = "prompt_label";
    static final String EMPTY_TEXT = "";
    static final int LARGE_TEXT_FIELD_LENGTH = 30;
    static final int SMALL_TEXT_FIELD_LENGTH = 5;

    //THIS MANAGES ALL OF THE APPLICATION'S DATA
    DraftDataManager dataManager;

    //THIS MANAGES DRAFT FILE I/O
    //REMEMBER THIS IS AN INTERFACE
    DraftFileManager draftFileManager;

    //THIS MANAGES EXPORTING THE DRAFT
    DraftSiteExporter siteExporter;

    //THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS
    FileController fileController;

    //THIS HANDLES INTERACTIONS WITH DRAFT INFO CONTROLS
    DraftEditController draftController;

    //THIS HANDLES REQUESTS TO ADD/EDIT/REMOVE PLAYERS
    PlayerEditController playerController;

    //THIS HANDLES REQUESTS TO ADD/EDIT/REMOVE TEAMS FROM THE DRAFT
    TeamEditController teamController;
    // THIS IS THE APPLICATION WINDOW
    Stage primaryStage;

    // THIS IS THE STAGE'S SCENE GRAPH
    Scene primaryScene;

    StackPane screensPane;

    //THIS PAGE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    //APPLICATION GUI, AND WILL STORE THE 5 PAGES
    BorderPane wdkPane;

    //THESE WILL BE THE PANES FOR EACH PAGE
    BorderPane playersScreenBorder;
    BorderPane fantasyTeamsScreenBorder;
    BorderPane fantasyStandingsScreenBorder;
    BorderPane draftScreenBorder;
    BorderPane mlbTeamsScreenBorder;

    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    FlowPane fileToolbarPaneUpper;
    Button newDraftButton;
    Button loadDraftButton;
    Button saveDraftButton;
    Button exportSiteButton;
    Button exitButton;

    // THIS IS THE LOW TOOLBAR AND ITS CONTROLS
    FlowPane fileToolbarPaneLower;
    Button playersScreenButton;
    Button fantasyTeamsScreenButton;
    Button fantasyStandingsScreenButton;
    Button draftScreenButton;
    Button mlbTeamsScreenButton;

    // WE'LL ORGANIZE OUR WORKSPACE COMPONENTS USING A BORDER PANE
    BorderPane workspacePane;
    boolean workspaceActivated;

    // WE'LL PUT THE WORKSPACE INSIDE A SCROLL PANE
    ScrollPane workspaceScrollPane;
    //THIS WILL REFERENCE ALL THE RADIOBUTTONS THAT WE NEED
    ButtonToolbar radioButtonToolbar;

    // WE'LL PUT THIS IN THE TOP OF THE WORKSPACE, IT WILL
    // HOLD TWO OTHER PANES FULL OF CONTROLS AS WELL AS A LABEL
    VBox topWorkspacePane;
    VBox bottomWorkspacePane;
    Label courseHeadingLabel;
    SplitPane topWorkspaceSplitPane;

    //THESE ARE THE CONTROLS FOR THE PLAYERS SCREEN
    //RADIO BUTTONS HANDLED BY ButtonToolbar UNLESS OTHERWISE IMPLEMENTED
    VBox topPanePlayer;
    GridPane playersGridPane;
    TableView<Player> playersTable;
    TextField playerSearchTextField;
    Label playersScreenHeadingLabel;
    Label playerSearchLabel;
    VBox playersScreenBox;
    HBox playersScreenToolbar;
    Button addPlayerButton;
    Button removePlayerButton;
    FilteredList<Player> filteredData;
    SortedList<Player> sortedData;
    HBox radioButtons;
    RadioButton All;
    RadioButton C;
    RadioButton B1;
    RadioButton CI;
    RadioButton B3;
    RadioButton B2;
    RadioButton MI;
    RadioButton SS;
    RadioButton OF;
    RadioButton U;
    RadioButton P;
    ToggleGroup toggle;

    //these are the columns
    TableColumn playerFirstName;
    TableColumn playerLastName;
    TableColumn playerProTeam;
    TableColumn playerPositions;
    TableColumn playerYearOfBirth;
    TableColumn playerW;
    TableColumn playerSV;
    TableColumn playerK;
    TableColumn playerERA;
    TableColumn playerWHIP;
    TableColumn playerEstimatedValue;
    TableColumn playerNotes;
    TableColumn playerR;
    TableColumn playerHR;
    TableColumn playerRBI;
    TableColumn playerSB;
    TableColumn playerBA;
    TableColumn playerRW;
    TableColumn playerHRSV;
    TableColumn playerRBIK;
    TableColumn playerSBERA;
    TableColumn playerBAWHIP;
    TableColumn playerSalary;
    TableColumn playerContract;

    //THESE ARE THE CONTROLS FOR THE FANTASY TEAMS SCREEN
    ScrollPane fantasyTeamScrollPane;
    SplitPane fantasyTeamSplitPane;
    TableView<Player> startingTable;
    TableView<Player> taxiSquadTable;
    VBox fantasyTeamsScreenBox;
    VBox startingSquadBox;
    VBox taxiSquadBox;
    VBox topPaneFantasyTeams;
    Label fantasyTeamsScreenHeadingLabel;
    Label draftNameLabel;
    Label startingLineupLabel;
    Label taxiSquadLabel;
    Label selectTeamLabel;
    HBox fantasyTeamsScreenToolbar;
    ComboBox<Team> teamComboBox;
    Button addTeamButton;
    Button removeTeamButton;
    Button editTeamButton;
    TextField draftNameTextField;
    GridPane teamsGridPane;

    TableColumn playerPosition_TS;
    TableColumn playerFirstName_TS;
    TableColumn playerLastName_TS;
    TableColumn playerProTeam_TS;
    TableColumn playerPositions_TS;
    TableColumn playerRW_TS;
    TableColumn playerHRSV_TS;
    TableColumn playerRBIK_TS;
    TableColumn playerSBERA_TS;
    TableColumn playerBAWHIP_TS;
    TableColumn playerEstimatedValue_TS;
    TableColumn playerSalary_TS;
    TableColumn playerContract_TS;

    TableColumn playerPosition_SL;
    TableColumn playerFirstName_SL;
    TableColumn playerLastName_SL;
    TableColumn playerProTeam_SL;
    TableColumn playerPositions_SL;
    TableColumn playerRW_SL;
    TableColumn playerHRSV_SL;
    TableColumn playerRBIK_SL;
    TableColumn playerSBERA_SL;
    TableColumn playerBAWHIP_SL;
    TableColumn playerEstimatedValue_SL;
    TableColumn playerSalary_SL;
    TableColumn playerContract_SL;

    //THESE ARE THE CONTROLS FOR THE FANTASY STANDINGS SCREEN
    //THERE WILL BE RADIO BUTTONS ALSO
    TableView<Team> fantasyTeamsStatsTable;
    VBox fantasyStandingsScreenBox;
    GridPane standingsGridPane;
    Label fantasyStandingsScreenHeadingLabel;
    VBox topPaneMLBScreen;
    HBox mlbScreenToolbar;
    Label mlbTeamLabel;
    TableColumn teamName;
    TableColumn playersNeeded;
    TableColumn moneyLeft;
    TableColumn pricePP;
    TableColumn teamR;
    TableColumn teamHR;
    TableColumn teamRBI;
    TableColumn teamSB;
    TableColumn teamBA;
    TableColumn teamW;
    TableColumn teamSV;
    TableColumn teamK;
    TableColumn teamERA;
    TableColumn teamWHIP;
    TableColumn teamTotalPoints;

    //THESE ARE THE CONTROLS FOR THE DRAFT SCREEN
    TableView<Player> playersDraftedTable;
    VBox draftScreenBox;
    Button selectPlayerButton;
    Button autoDraftPlayerButton;
    Button pauseAutomatedDraftButton;
    GridPane draftGridPane;
    Label draftScreenHeadingLabel;
    HBox draftScreenToolbar;
    TableColumn pickNum;
    int pickNumber;
    //THESE ARE THE CONTROLS FOR THE MLB TEAMS SCREEN
    TableView<Player> mlbTeamPlayers;
    ComboBox mlbTeamsComboBox;
    VBox mlbTeamsScreenBox;
    GridPane mlbGridPane;
    Label mlbScreenHeadingLabel;

    // AND TABLE COLUMNS   
    public static final String COL_TEAM_NAME = "Team Name";
    public static final String COL_PLAYERS_NEEDED = "Players Needed";
    public static final String COL_MONEY_LEFT = "$ Left";
    public static final String COL_MONEY_PER_PLAYER = "$ PP";
    public static final String CONTRACT_PROMPT = "Contract: ";
    public static final String SALARY_PROMPT = "Salary: ";
    public static final String COL_R = "R";
    public static final String COL_HR = "HR";
    public static final String COL_RBI = "RBI";
    public static final String COL_SB = "SB";
    public static final String COL_BA = "BA";
    public static final String COL_W = "W";
    public static final String COL_K = "K";
    public static final String COL_SV = "SV";
    public static final String COL_ERA = "ERA";
    public static final String COL_WHIP = "WHIP";
    public static final String COL_TOTAL_PTS = "Total Pts";
    public static final String COL_FIRST_NAME = "First";
    public static final String COL_LAST_NAME = "Last";
    public static final String COL_PREV_TEAM = "Prev Team";
    public static final String COL_POSITIONS = "Positions";
    public static final String COL_POSITION = "Position";
    public static final String COL_CONTRACT = "Contract";
    public static final String COL_SALARY = "Salary";
    public static final String COL_YOB = "Year of Birth";
    public static final String COL_VALUE = "Estimated Value";
    public static final String COL_NOTES = "Notes";
    public static final String COL_R_W = "R/W";
    public static final String COL_HR_SV = "HR/SV";
    public static final String COL_RBI_K = "RBI/K";
    public static final String COL_SB_ERA = "SB/ERA";
    public static final String COL_BA_WHIP = "BA/WHIP";
    public static final String COL_PICK_NUM = "Pick #";
    // HERE ARE OUR DIALOGS
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;

    /**
     * Constructor for making this GUI, note that it does not initialize the UI
     * controls. To do that, call initGUI.
     *
     * @param initPrimaryStage Window inside which the GUI will be displayed.
     */
    public WDK_GUI(Stage initPrimaryStage) {
        primaryStage = initPrimaryStage;
    }

    /**
     * Accessor method for the data manager.
     *
     * @return The DraftDataManager used by this UI.
     */
    public DraftDataManager getDataManager() {
        return dataManager;

    }

    /**
     * Accessor method for the file controller.
     *
     * @return The FileController used by this UI.
     */
    public FileController getFileController() {
        return fileController;
    }

    /**
     * Accessor method for the draft file manager.
     *
     * @return The DraftFileManager used by this UI.
     */
    public DraftFileManager getDraftFileManager() {
        return draftFileManager;
    }

    /**
     * Accessor method for the site exporter.
     *
     * @return The DraftSiteExporter used by this UI.
     */
    public DraftSiteExporter getSiteExporter() {
        return siteExporter;
    }

    /**
     * Accessor method for the window (i.e. stage).
     *
     * @return The window (i.e. Stage) used by this UI.
     */
    public Stage getWindow() {
        return primaryStage;
    }

    public MessageDialog getMessageDialog() {
        return messageDialog;
    }

    public YesNoCancelDialog getYesNoCancelDialog() {
        return yesNoCancelDialog;
    }

    /**
     * Mutator method for the data manager.
     *
     * @param initDataManager The DraftDataManager to be used by this UI.
     */
    public void setDataManager(DraftDataManager initDataManager) {
        dataManager = initDataManager;
    }

    /**
     * Mutator method for the site exporter.
     *
     * @param initSiteExporter The DraftSiteExporter to be used by this UI.
     */
    public void setSiteExporter(DraftSiteExporter initSiteExporter) {
        siteExporter = initSiteExporter;
    }

    public void setDraftController(DraftEditController draftController) {
        this.draftController = draftController;
    }

    public void setDraftFileManager(DraftFileManager draftFileManager) {
        this.draftFileManager = draftFileManager;
    }

    /**
     * This method fully initializes the user interface for use.
     *
     * @param windowTitle The text to appear in the UI window's title bar.
     * @param subjects The list of subjects to choose from.
     * @throws IOException Thrown if any initialization files fail to load.
     */
    public void initGUI(String windowTitle) throws IOException {
        // INIT THE DIALOGS
        initDialogs();

        // INIT THE TOOLBAR
        initFileToolbar();
        initLowerToolbar();
        // INIT THE LOWER TOOLBAR
        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET
        initWorkspace();

        // NOW SETUP THE EVENT HANDLERS
        initEventHandlers();

        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow(windowTitle);
    }

    /**
     * When called this function puts the workspace into the window, revealing
     * the controls for editing a Course.
     */
    //USE THIS METHOD TO SET WHAT PANE YOU WANT TO SET IN THE CENTER
    public void activateWorkspace() {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            wdkPane.setCenter(workspacePane);
            wdkPane.setBottom(fileToolbarPaneLower);
            workspaceActivated = true;
        }
    }

    /**
     * This method is used to activate/deactivate toolbar buttons when they can
     * and cannot be used so as to provide foolproof design.
     *
     * @param saved Describes whether the loaded Course has been saved or not.
     */
    public void updateToolbarControls(boolean saved) {
        // THIS TOGGLES WITH WHETHER THE CURRENT DRAFT
        // HAS BEEN SAVED OR NOT
        saveDraftButton.setDisable(saved);

        // ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        // ONCE EDITING THAT FIRST DRAFT BEGINS
        loadDraftButton.setDisable(false);
        exportSiteButton.setDisable(false);

        // NOTE THAT THE NEW, LOAD, AND EXIT BUTTONS
        // ARE NEVER DISABLED SO WE NEVER HAVE TO TOUCH THEM
    }

    public void updateDraftInfo(Draft draft) {
        draft.setDraftName(draftNameTextField.getText());
    }

    @Override
    public void reloadDraft(Draft draftToReload) {
        // FIRST ACTIVATE THE WORKSPACE IF NECESSARY
        if (!workspaceActivated) {
            activateWorkspace();
        }

        draftController.enable(false);
        playerSearchTextField.clear();
        All.setSelected(true);

        //ADDED THE FILTERD PLAYED TO SORTED DATA
        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(playersTable.comparatorProperty());

        //SET THE SORTED DATA TO PLAYER'S TABLE
        playersTable.setItems(draftToReload.getAllPlayers());

        teamComboBox.getItems().clear();
        for (Team team : draftToReload.getTeams()) {
            teamComboBox.getItems().add(team);
            team.calculateStats();
            for (Player p : team.getStartupLine()) {
                if (p.getContract().contains("S2")) {
                    draftToReload.getDraftedPlayers().add(p);
                }
            }
            for (Player p : team.getTaxiSquad()) {
                draftToReload.getDraftedPlayers().add(p);
            }
        }
        sortAndUpdatePickNum();

        teamComboBox.getSelectionModel().clearSelection();
        draftNameTextField.setText(draftToReload.getDraftName());
        draftController.enable(true);
    }

    /**
     * *************************************************************************
     */
    /* BELOW ARE ALL THE PRIVATE HELPER METHODS WE USE FOR INITIALIZING OUR GUI */
    /**
     * *************************************************************************
     */
    private void initDialogs() {
        messageDialog = new MessageDialog(primaryStage, CLOSE_BUTTON_LABEL);
        yesNoCancelDialog = new YesNoCancelDialog(primaryStage);
    }

    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initFileToolbar() {
        fileToolbarPaneUpper = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        newDraftButton = initChildButton(fileToolbarPaneUpper, WDK_PropertyType.NEW_DRAFT_ICON, WDK_PropertyType.NEW_DRAFT_TOOLTIP, false);
        loadDraftButton = initChildButton(fileToolbarPaneUpper, WDK_PropertyType.LOAD_DRAFT_ICON, WDK_PropertyType.LOAD_DRAFT_TOOLTIP, false);
        saveDraftButton = initChildButton(fileToolbarPaneUpper, WDK_PropertyType.SAVE_DRAFT_ICON, WDK_PropertyType.SAVE_DRAFT_TOOLTIP, true);
        exportSiteButton = initChildButton(fileToolbarPaneUpper, WDK_PropertyType.EXPORT_PAGE_ICON, WDK_PropertyType.EXPORT_PAGE_TOOLTIP, true);
        exitButton = initChildButton(fileToolbarPaneUpper, WDK_PropertyType.EXIT_ICON, WDK_PropertyType.EXIT_TOOLTIP, false);
    }

    private FlowPane initLowerToolbar() {
        fileToolbarPaneLower = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS FOR THE SCREENS, 
        //NOTE THAT ALL WILL START AS ENABLED (false)
        fantasyTeamsScreenButton = initChildButton(fileToolbarPaneLower, WDK_PropertyType.HOME_ICON, WDK_PropertyType.HOME_TOOLTIP, false);
        playersScreenButton = initChildButton(fileToolbarPaneLower, WDK_PropertyType.PLAYERS_ICON, WDK_PropertyType.PLAYERS_TOOLTIP, false);
        fantasyStandingsScreenButton = initChildButton(fileToolbarPaneLower, WDK_PropertyType.STANDINGS_ICON, WDK_PropertyType.STANDINGS_TOOLTIP, false);
        draftScreenButton = initChildButton(fileToolbarPaneLower, WDK_PropertyType.DRAFT_ICON, WDK_PropertyType.DRAFT_TOOLTIP, false);
        mlbTeamsScreenButton = initChildButton(fileToolbarPaneLower, WDK_PropertyType.MLB_ICON, WDK_PropertyType.MLB_TOOLTIP, false);
        return fileToolbarPaneLower;
    }

    //CREATES AND SETS UP ALLTHE CONTROLS TO GO IN THE APP WORKSPACE
    private void initWorkspace() throws IOException {

        //CSB HAS A METHOD FOR SELECTING PAGE LINKS TO INCLUDE
        //WE DONT NEED THAT
        //THEY HAVE A METHOD TO INITIATE TOP WORKSPACE
        //I BELIEVE EVERYTING WILL BE IN THE CENTER WORKSPACE
        //AND IT WONT NEED TO BE SPLIT SO WE WON'T INCLUDE THAT EITHER
        //THERE IS A METHOD CALLED initScheduleItemsControls() which
        //sets up all the controls inside the center. 
        //not sure if i will do it this way or will split per screen
        initAllScreens();

        //THIS HOLDS ALL OUR WORKSPACE COMPONENTS, SO NOW WE MUST
        //ADD ALL THE COMPONENTS WE JUST INITIALIZED.
        //there should probably be a method here to 
        //initialize the workspace default screen, will
        //figure that out after
        workspacePane = new BorderPane();

        //set the top, this will hold our 5 buttons to load save new etc..
        workspacePane.setTop(topWorkspacePane);

        workspacePane.setCenter(playersScreenBorder);

        workspacePane.getStyleClass().add(CLASS_BORDERED_PANE);

        //AND NOW PUT IT IN THE WORKSPACE
        workspaceScrollPane = new ScrollPane();
        workspaceScrollPane.setContent(workspacePane);
        workspaceScrollPane.setFitToWidth(true);

        //NOTE THAT WE HAVE NOT PUT THE WORKSPACE INTO THE WINDOW,
        //THAT WILL BE DONE WHEN THE USER EITHER CREATES A NEW
        //DRAFT OR LOADS AN EXISTING ONE FOR EDITING
        workspaceActivated = false;
    }

    private void initAllScreens() throws IOException {
        initFantasyTeamsScreen();
        initPlayersScreen();
        initStandingsScreen();
        initDraftScreen();
        initMLBScreen();

    }

    private void initFantasyTeamsScreen() throws IOException {

        //FILL THE CONTENT OF THE FANTASY TEAMS SCREEN
        fantasyTeamsScreenBorder = new BorderPane();

        fantasyTeamsScreenBox = new VBox();
        topPaneFantasyTeams = new VBox();
        startingSquadBox = new VBox();
        taxiSquadBox = new VBox();
        topPaneFantasyTeams.getStyleClass().add(CLASS_BORDERED_PANE);

        fantasyTeamsScreenToolbar = new HBox();
        addTeamButton = new Button();
        removeTeamButton = new Button();
        editTeamButton = new Button();
        fantasyTeamsScreenToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
        addTeamButton = initChildButton(fantasyTeamsScreenToolbar, WDK_PropertyType.ADD_ICON, WDK_PropertyType.ADD_TEAM_TOOLTIP, false);
        removeTeamButton = initChildButton(fantasyTeamsScreenToolbar, WDK_PropertyType.MINUS_ICON, WDK_PropertyType.REMOVE_TEAM_TOOLTIP, false);
        editTeamButton = initChildButton(fantasyTeamsScreenToolbar, WDK_PropertyType.EDIT_ICON, WDK_PropertyType.EDIT_TEAM_TOOLTIP, false);

        teamsGridPane = new GridPane();
        draftNameTextField = new TextField();
        teamComboBox = new ComboBox();
        selectTeamLabel = initGridLabel(teamsGridPane, WDK_PropertyType.FANTASY_TEAM_LABEL, CLASS_PROMPT_LABEL, 0, 1, 1, 1);
        teamComboBox = initGridComboBox(teamsGridPane, 1, 1, 1, 1);
        draftNameLabel = initGridLabel(teamsGridPane, WDK_PropertyType.DRAFT_NAME_LABEL, CLASS_PROMPT_LABEL, 2, 1, 1, 1);
        draftNameTextField = initGridTextField(teamsGridPane, LARGE_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 3, 1, 1, 1);

        startingTable = new TableView<Player>();
        taxiSquadTable = new TableView<Player>();

        startingLineupLabel = initChildLabel(startingSquadBox, WDK_PropertyType.STARTING_SQUAD_LABEL, CLASS_SUBHEADING_LABEL);
        startingSquadBox.getChildren().add(startingTable);

        //INITIALIZE THE TABLE COLUMNS
        playerPosition_SL = new TableColumn(COL_POSITION);
        playerFirstName_SL = new TableColumn(COL_FIRST_NAME);
        playerLastName_SL = new TableColumn(COL_LAST_NAME);
        playerProTeam_SL = new TableColumn(COL_PREV_TEAM);
        playerPositions_SL = new TableColumn(COL_POSITIONS);
        playerRW_SL = new TableColumn(COL_R_W);
        playerHRSV_SL = new TableColumn(COL_HR_SV);
        playerRBIK_SL = new TableColumn(COL_RBI_K);
        playerSBERA_SL = new TableColumn(COL_SB_ERA);
        playerBAWHIP_SL = new TableColumn(COL_BA_WHIP);
        playerEstimatedValue_SL = new TableColumn(COL_VALUE);
        playerContract_SL = new TableColumn(COL_CONTRACT);
        playerSalary_SL = new TableColumn(COL_SALARY);

        playerPosition_SL.setCellValueFactory(new PropertyValueFactory<>("P"));
        playerFirstName_SL.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        playerLastName_SL.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        playerProTeam_SL.setCellValueFactory(new PropertyValueFactory<>("previousTeam"));
        playerPositions_SL.setCellValueFactory(new PropertyValueFactory<>("QP"));
        playerRW_SL.setCellValueFactory(new PropertyValueFactory<>("R_W"));
        playerHRSV_SL.setCellValueFactory(new PropertyValueFactory<>("HR_SV"));
        playerRBIK_SL.setCellValueFactory(new PropertyValueFactory<>("RBI_K"));
        playerSBERA_SL.setCellValueFactory(new PropertyValueFactory<>("SB_ERA"));
        playerBAWHIP_SL.setCellValueFactory(new PropertyValueFactory<>("BA_WHIP"));
        playerEstimatedValue_SL.setCellValueFactory(new PropertyValueFactory<>("Estimated Values"));
        playerContract_SL.setCellValueFactory(new PropertyValueFactory<>("contract"));
        playerSalary_SL.setCellValueFactory(new PropertyValueFactory<>("salary"));

        //INITIALIZE THE TABLE COLUMNS FOR STARTING LINEUP
        playerPosition_TS = new TableColumn(COL_POSITION);
        playerFirstName_TS = new TableColumn(COL_FIRST_NAME);
        playerLastName_TS = new TableColumn(COL_LAST_NAME);
        playerProTeam_TS = new TableColumn(COL_PREV_TEAM);
        playerPositions_TS = new TableColumn(COL_POSITIONS);
        playerRW_TS = new TableColumn(COL_R_W);
        playerHRSV_TS = new TableColumn(COL_HR_SV);
        playerRBIK_TS = new TableColumn(COL_RBI_K);
        playerSBERA_TS = new TableColumn(COL_SB_ERA);
        playerBAWHIP_TS = new TableColumn(COL_BA_WHIP);
        playerEstimatedValue_TS = new TableColumn(COL_VALUE);
        playerContract_TS = new TableColumn(COL_CONTRACT);
        playerSalary_TS = new TableColumn(COL_SALARY);

        playerPosition_TS.setCellValueFactory(new PropertyValueFactory<>("P"));
        playerFirstName_TS.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        playerLastName_TS.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        playerProTeam_TS.setCellValueFactory(new PropertyValueFactory<>("previousTeam"));
        playerPositions_TS.setCellValueFactory(new PropertyValueFactory<>("QP"));
        playerRW_TS.setCellValueFactory(new PropertyValueFactory<>("R_W"));
        playerHRSV_TS.setCellValueFactory(new PropertyValueFactory<>("HR_SV"));
        playerRBIK_TS.setCellValueFactory(new PropertyValueFactory<>("RBI_K"));
        playerSBERA_TS.setCellValueFactory(new PropertyValueFactory<>("SB_ERA"));
        playerBAWHIP_TS.setCellValueFactory(new PropertyValueFactory<>("BA_WHIP"));
        playerEstimatedValue_TS.setCellValueFactory(new PropertyValueFactory<>("Estimated Values"));
        playerContract_TS.setCellValueFactory(new PropertyValueFactory<>("contract"));
        playerSalary_TS.setCellValueFactory(new PropertyValueFactory<>("salary"));

        startingTable.getColumns().add(playerPosition_SL);
        startingTable.getColumns().add(playerFirstName_SL);
        startingTable.getColumns().add(playerLastName_SL);
        startingTable.getColumns().add(playerProTeam_SL);
        startingTable.getColumns().add(playerPositions_SL);
        startingTable.getColumns().add(playerRW_SL);
        startingTable.getColumns().add(playerHRSV_SL);
        startingTable.getColumns().add(playerRBIK_SL);
        startingTable.getColumns().add(playerSBERA_SL);
        startingTable.getColumns().add(playerBAWHIP_SL);
        startingTable.getColumns().add(playerEstimatedValue_SL);
        startingTable.getColumns().add(playerContract_SL);
        startingTable.getColumns().add(playerSalary_SL);

        taxiSquadTable.getColumns().add(playerPosition_TS);
        taxiSquadTable.getColumns().add(playerFirstName_TS);
        taxiSquadTable.getColumns().add(playerLastName_TS);
        taxiSquadTable.getColumns().add(playerProTeam_TS);
        taxiSquadTable.getColumns().add(playerPositions_TS);
        taxiSquadTable.getColumns().add(playerRW_TS);
        taxiSquadTable.getColumns().add(playerHRSV_TS);
        taxiSquadTable.getColumns().add(playerRBIK_TS);
        taxiSquadTable.getColumns().add(playerSBERA_TS);
        taxiSquadTable.getColumns().add(playerBAWHIP_TS);
        taxiSquadTable.getColumns().add(playerEstimatedValue_TS);
        taxiSquadTable.getColumns().add(playerContract_TS);
        taxiSquadTable.getColumns().add(playerSalary_TS);

        startingTable.setEditable(true);
        taxiSquadTable.setEditable(true);

        // FIRST OUR SCHEDULE HEADER
        taxiSquadLabel = initChildLabel(taxiSquadBox, WDK_PropertyType.TAXI_SQUAD_LABEL, CLASS_SUBHEADING_LABEL);
        taxiSquadBox.getChildren().add(taxiSquadTable);

        fantasyTeamsScreenBox = new VBox();
        fantasyTeamsScreenHeadingLabel = initChildLabel(fantasyTeamsScreenBox, WDK_PropertyType.FANTASY_TEAMS_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL);

        fantasyTeamsScreenToolbar.getChildren().add(teamsGridPane);

        fantasyTeamsScreenBox.getChildren().add(fantasyTeamsScreenToolbar);
        topPaneFantasyTeams.getChildren().add(startingSquadBox);
        topPaneFantasyTeams.getChildren().add(taxiSquadBox);

        fantasyTeamScrollPane = new ScrollPane();
        fantasyTeamScrollPane.setContent(topPaneFantasyTeams);
        fantasyTeamScrollPane.setFitToWidth(true);

        fantasyTeamsScreenBox.getChildren().add(fantasyTeamScrollPane);
        fantasyTeamsScreenBorder.setCenter(fantasyTeamsScreenBox);

    }

    private void initPlayersScreen() {//FILL THE CONTENT OF THE PLAYERS SCREEN;
        //INITIALIZE THE BORDERPANE THAT WILL HOLD EVERYTHING
        playersScreenBorder = new BorderPane();

        playersScreenBox = new VBox();
        topPanePlayer = new VBox();
        topPanePlayer.getStyleClass().add(CLASS_BORDERED_PANE);

        playersScreenToolbar = new HBox();
        addPlayerButton = new Button();
        removePlayerButton = new Button();
        playerSearchTextField = new TextField();
        playerSearchLabel = new Label();
        playersGridPane = new GridPane();
        playersTable = new TableView<Player>();
        playersScreenHeadingLabel = initChildLabel(playersScreenBox, WDK_PropertyType.PLAYERS_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL);
        addPlayerButton = initChildButton(playersScreenToolbar, WDK_PropertyType.ADD_ICON, WDK_PropertyType.ADD_PLAYER_TOOLTIP, false);
        removePlayerButton = initChildButton(playersScreenToolbar, WDK_PropertyType.MINUS_ICON, WDK_PropertyType.REMOVE_PLAYER_TOOLTIP, false);
        playerSearchLabel = initGridLabel(playersGridPane, WDK_PropertyType.PLAYER_SEARCH_LABEL, CLASS_PROMPT_LABEL, 2, 2, 1, 1);
        playerSearchTextField = initGridTextField(playersGridPane, LARGE_TEXT_FIELD_LENGTH, EMPTY_TEXT, true, 3, 2, 1, 1);

        //initialize the radio buttons
        All = new RadioButton("All");
        C = new RadioButton("C");
        B1 = new RadioButton("B1");
        CI = new RadioButton("CI");
        B3 = new RadioButton("B3");
        B2 = new RadioButton("B2");
        MI = new RadioButton("MI");
        SS = new RadioButton("SS");
        OF = new RadioButton("OF");
        U = new RadioButton("U");
        P = new RadioButton("P");

        //add them to a group
        toggle = new ToggleGroup();
        All.setToggleGroup(toggle);
        C.setToggleGroup(toggle);
        B1.setToggleGroup(toggle);
        CI.setToggleGroup(toggle);
        B3.setToggleGroup(toggle);
        B2.setToggleGroup(toggle);
        MI.setToggleGroup(toggle);
        SS.setToggleGroup(toggle);
        OF.setToggleGroup(toggle);
        U.setToggleGroup(toggle);
        P.setToggleGroup(toggle);
        All.setSelected(true);
        radioButtons = new HBox(25, All, C, B1, CI, B3, B2, MI, SS, OF, U, P);
        radioButtons.setPadding(new Insets(20));

        //INITIALIZE THE TABLE COLUMNS
        playerFirstName = new TableColumn(COL_FIRST_NAME);
        playerLastName = new TableColumn(COL_LAST_NAME);
        playerProTeam = new TableColumn(COL_PREV_TEAM);
        playerPositions = new TableColumn(COL_POSITIONS);
        playerYearOfBirth = new TableColumn(COL_YOB);
        playerW = new TableColumn(COL_W);
        playerSV = new TableColumn(COL_SV);
        playerK = new TableColumn(COL_K);
        playerERA = new TableColumn(COL_ERA);
        playerWHIP = new TableColumn(COL_WHIP);
        playerEstimatedValue = new TableColumn(COL_VALUE);
        playerNotes = new TableColumn(COL_NOTES);
        playerR = new TableColumn(COL_R);
        playerHR = new TableColumn(COL_HR);
        playerRBI = new TableColumn(COL_RBI);
        playerSB = new TableColumn(COL_SB);
        playerBA = new TableColumn(COL_BA);
        playerRW = new TableColumn(COL_R_W);
        playerHRSV = new TableColumn(COL_HR_SV);
        playerRBIK = new TableColumn(COL_RBI_K);
        playerSBERA = new TableColumn(COL_SB_ERA);
        playerBAWHIP = new TableColumn(COL_BA_WHIP);

        //ADD THE LABLES TO THE CELL
        playerFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        playerLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        playerProTeam.setCellValueFactory(new PropertyValueFactory<>("previousTeam"));
        playerPositions.setCellValueFactory(new PropertyValueFactory<>("QP"));
        playerYearOfBirth.setCellValueFactory(new PropertyValueFactory<>("yearOfBirth"));
        playerR.setCellValueFactory(new PropertyValueFactory<>("R"));
        playerHR.setCellValueFactory(new PropertyValueFactory<>("HR"));
        playerRBI.setCellValueFactory(new PropertyValueFactory<>("RBI"));
        playerSB.setCellValueFactory(new PropertyValueFactory<>("SB"));
        playerW.setCellValueFactory(new PropertyValueFactory<>("W"));
        playerSV.setCellValueFactory(new PropertyValueFactory<>("SV"));
        playerBA.setCellValueFactory(new PropertyValueFactory<>("BA"));
        playerK.setCellValueFactory(new PropertyValueFactory<>("K"));
        playerERA.setCellValueFactory(new PropertyValueFactory<>("ERA"));
        playerWHIP.setCellValueFactory(new PropertyValueFactory<>("WHIP"));
        playerEstimatedValue.setCellValueFactory(new PropertyValueFactory<>("Estimated Values"));
        playerNotes.setCellValueFactory(new PropertyValueFactory<>("Notes"));
        playerRW.setCellValueFactory(new PropertyValueFactory<>("R_W"));
        playerHRSV.setCellValueFactory(new PropertyValueFactory<>("HR_SV"));
        playerRBIK.setCellValueFactory(new PropertyValueFactory<>("RBI_K"));
        playerSBERA.setCellValueFactory(new PropertyValueFactory<>("SB_ERA"));
        playerBAWHIP.setCellValueFactory(new PropertyValueFactory<>("BA_WHIP"));

        //AND THEN ADD THE COLUMS TO THE TABLE
        playersTable.getColumns().add(playerFirstName);
        playersTable.getColumns().add(playerLastName);
        playersTable.getColumns().add(playerProTeam);
        playersTable.getColumns().add(playerPositions);
        playersTable.getColumns().add(playerYearOfBirth);
        playersTable.getColumns().add(playerRW);
        playersTable.getColumns().add(playerHRSV);
        playersTable.getColumns().add(playerRBIK);
        playersTable.getColumns().add(playerSBERA);
        playersTable.getColumns().add(playerBAWHIP);
        playersTable.getColumns().add(playerEstimatedValue);
        playersTable.getColumns().add(playerNotes);
        playersTable.setEditable(true);

        filteredData = new FilteredList<>(dataManager.getDraft().getAllPlayers(), p -> true);

        playersTable.setItems(dataManager.getDraft().getFilteredPlayers());
        playersScreenToolbar.getChildren().add(playersGridPane);

        topPanePlayer.getChildren().add(playersScreenToolbar);
        topPanePlayer.getChildren().add(radioButtons);
        playersScreenBox.getChildren().add(topPanePlayer);
        playersScreenBox.getChildren().add(playersTable);

        playersScreenBorder.setCenter(playersScreenBox);
    }

    private void initStandingsScreen() {
        fantasyStandingsScreenBox = new VBox();
        fantasyStandingsScreenHeadingLabel = initChildLabel(fantasyStandingsScreenBox, WDK_PropertyType.FANTASY_STANDINGS_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL);

        fantasyTeamsStatsTable = new TableView<Team>();

        teamName = new TableColumn(COL_TEAM_NAME);
        playersNeeded = new TableColumn(COL_PLAYERS_NEEDED);
        moneyLeft = new TableColumn(COL_MONEY_LEFT);
        pricePP = new TableColumn(COL_MONEY_PER_PLAYER);
        teamR = new TableColumn(COL_R);
        teamHR = new TableColumn(COL_HR);
        teamRBI = new TableColumn(COL_RBI);
        teamSB = new TableColumn(COL_SB);
        teamBA = new TableColumn(COL_BA);
        teamW = new TableColumn(COL_W);
        teamSV = new TableColumn(COL_SV);
        teamK = new TableColumn(COL_K);
        teamERA = new TableColumn(COL_ERA);
        teamWHIP = new TableColumn(COL_WHIP);
        teamTotalPoints = new TableColumn(COL_TOTAL_PTS);

        teamName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        playersNeeded.setCellValueFactory(new PropertyValueFactory<>("playersNeeded"));
        moneyLeft.setCellValueFactory(new PropertyValueFactory<>("moneyLeft"));
        pricePP.setCellValueFactory(new PropertyValueFactory<>("pricePP"));
        teamR.setCellValueFactory(new PropertyValueFactory<>("teamR"));
        teamHR.setCellValueFactory(new PropertyValueFactory<>("teamHR"));
        teamRBI.setCellValueFactory(new PropertyValueFactory<>("teamRBI"));
        teamSB.setCellValueFactory(new PropertyValueFactory<>("teamSB"));
        teamBA.setCellValueFactory(new PropertyValueFactory<>("teamBA"));
        teamW.setCellValueFactory(new PropertyValueFactory<>("teamW"));
        teamSV.setCellValueFactory(new PropertyValueFactory<>("teamSV"));
        teamK.setCellValueFactory(new PropertyValueFactory<>("teamK"));
        teamERA.setCellValueFactory(new PropertyValueFactory<>("teamERA"));
        teamWHIP.setCellValueFactory(new PropertyValueFactory<>("teamWHIP"));
        teamTotalPoints.setCellValueFactory(new PropertyValueFactory<>("teamTotalPoints"));

        fantasyTeamsStatsTable.getColumns().add(teamName);
        fantasyTeamsStatsTable.getColumns().add(playersNeeded);
        fantasyTeamsStatsTable.getColumns().add(moneyLeft);
        fantasyTeamsStatsTable.getColumns().add(pricePP);
        fantasyTeamsStatsTable.getColumns().add(teamR);
        fantasyTeamsStatsTable.getColumns().add(teamHR);
        fantasyTeamsStatsTable.getColumns().add(teamRBI);
        fantasyTeamsStatsTable.getColumns().add(teamSB);
        fantasyTeamsStatsTable.getColumns().add(teamBA);
        fantasyTeamsStatsTable.getColumns().add(teamW);
        fantasyTeamsStatsTable.getColumns().add(teamSV);
        fantasyTeamsStatsTable.getColumns().add(teamK);
        fantasyTeamsStatsTable.getColumns().add(teamERA);
        fantasyTeamsStatsTable.getColumns().add(teamWHIP);
        fantasyTeamsStatsTable.getColumns().add(teamTotalPoints);

        fantasyTeamsStatsTable.setEditable(false);

        fantasyTeamsStatsTable.setItems(dataManager.getDraft().getTeams());
        fantasyStandingsScreenBox.getChildren().add(fantasyTeamsStatsTable);
        fantasyStandingsScreenBorder = new BorderPane();
        fantasyStandingsScreenBorder.setTop(fantasyStandingsScreenBox);
    }

    private void initDraftScreen() {
        draftScreenBox = new VBox();
        draftScreenHeadingLabel = initChildLabel(draftScreenBox, WDK_PropertyType.DRAFT_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL);
        draftScreenToolbar = new HBox();

        selectPlayerButton = new Button();
        autoDraftPlayerButton = new Button();
        pauseAutomatedDraftButton = new Button();

        selectPlayerButton = initChildButton(draftScreenToolbar, WDK_PropertyType.SELECT_PLAYER_ICON, WDK_PropertyType.SELECT_PLAYER_TOOLTIP, false);
        autoDraftPlayerButton = initChildButton(draftScreenToolbar, WDK_PropertyType.AUTO_DRAFT_ICON, WDK_PropertyType.AUTO_DRAFT_TOOLTIP, false);
        pauseAutomatedDraftButton = initChildButton(draftScreenToolbar, WDK_PropertyType.PAUSE_DRAFT_ICON, WDK_PropertyType.PAUSE_DRAFT_TOOLTIP, false);

        playersDraftedTable = new TableView<Player>();

        pickNum = new TableColumn(COL_PICK_NUM);
        playerFirstName = new TableColumn(COL_FIRST_NAME);
        playerLastName = new TableColumn(COL_LAST_NAME);
        teamName = new TableColumn(COL_TEAM_NAME);
        playerContract = new TableColumn(COL_CONTRACT);
        playerSalary = new TableColumn(COL_SALARY);

        pickNum.setCellValueFactory(new PropertyValueFactory<>("pickNum"));
        playerFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        playerLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        teamName.setCellValueFactory(new PropertyValueFactory<>("fantasyTeamName"));
        playerContract.setCellValueFactory(new PropertyValueFactory<>("contract"));
        playerSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        playersDraftedTable.getColumns().add(pickNum);
        playersDraftedTable.getColumns().add(playerFirstName);
        playersDraftedTable.getColumns().add(playerLastName);
        playersDraftedTable.getColumns().add(teamName);
        playersDraftedTable.getColumns().add(playerContract);
        playersDraftedTable.getColumns().add(playerSalary);

        playersDraftedTable.setItems(dataManager.getDraft().getDraftedPlayers());

        draftScreenBorder = new BorderPane();
        draftScreenBox.getChildren().add(draftScreenToolbar);
        draftScreenBox.getChildren().add(playersDraftedTable);

        draftScreenBorder.setTop(draftScreenBox);
    }

    private void initMLBScreen() throws IOException {
        mlbTeamsScreenBox = new VBox();
        topPaneMLBScreen = new VBox();
        topPaneFantasyTeams.getStyleClass().add(CLASS_BORDERED_PANE);
        mlbScreenToolbar = new HBox();

        //instantiate the combobox, label, and gridpane that will hold it
        mlbTeamsComboBox = new ComboBox();
        mlbTeamLabel = new Label();
        mlbGridPane = new GridPane();
        mlbTeamLabel = initGridLabel(mlbGridPane, WDK_PropertyType.MLB_SCREEN_LABEL, CLASS_PROMPT_LABEL, 2, 1, 1, 1);
        mlbTeamsComboBox = initGridComboBox(mlbGridPane, 3, 1, 1, 1);

        mlbTeamsComboBox.getItems().add("ATL");
        mlbTeamsComboBox.getItems().add("AZ");
        mlbTeamsComboBox.getItems().add("CHC");
        mlbTeamsComboBox.getItems().add("CIN");
        mlbTeamsComboBox.getItems().add("COL");
        mlbTeamsComboBox.getItems().add("LAD");
        mlbTeamsComboBox.getItems().add("MIA");
        mlbTeamsComboBox.getItems().add("MIL");
        mlbTeamsComboBox.getItems().add("NYM");
        mlbTeamsComboBox.getItems().add("PHI");
        mlbTeamsComboBox.getItems().add("PIT");
        mlbTeamsComboBox.getItems().add("SD");
        mlbTeamsComboBox.getItems().add("SF");
        mlbTeamsComboBox.getItems().add("STL");
        mlbTeamsComboBox.getItems().add("WAS");
        mlbTeamsComboBox.getSelectionModel().clearSelection();

        //instantiate the players
        mlbTeamPlayers = new TableView<Player>();

        //instantiate the columns
        playerFirstName = new TableColumn(COL_FIRST_NAME);
        playerLastName = new TableColumn(COL_LAST_NAME);
        playerPositions = new TableColumn(COL_POSITIONS);

        //set the values
        playerFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        playerLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        playerPositions.setCellValueFactory(new PropertyValueFactory<>("QP"));

        //add the columns
        mlbTeamPlayers.getColumns().add(playerFirstName);
        mlbTeamPlayers.getColumns().add(playerLastName);
        mlbTeamPlayers.getColumns().add(playerPositions);

        //no editing for this table
        startingTable.setEditable(false);

        mlbScreenHeadingLabel = initChildLabel(mlbTeamsScreenBox, WDK_PropertyType.MLB_TEAMS_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL);
        mlbScreenToolbar.getChildren().add(mlbGridPane);
        mlbScreenToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
        topPaneMLBScreen.getChildren().add(mlbTeamPlayers);
        mlbTeamsScreenBox.getChildren().add(mlbScreenToolbar);
        mlbTeamsScreenBox.getChildren().add(topPaneMLBScreen);
        mlbTeamsScreenBorder = new BorderPane();
        mlbTeamsScreenBorder.setTop(mlbTeamsScreenBox);
    }

    private void handleScreenSwitchRequest(int num) {
        switch (num) {
            case 1:
                workspacePane.setCenter(fantasyTeamsScreenBorder);
                break;
            case 2:
                workspacePane.setCenter(playersScreenBorder);
                break;
            case 3:
                //fantasyTeamsStatsTable.getItems().clear();
                for (Team team : dataManager.getDraft().getTeams()) {
                    team.calculateStats();
                }
                initStandingsScreen();
                workspacePane.setCenter(fantasyStandingsScreenBorder);

                break;
            case 4:
                workspacePane.setCenter(draftScreenBorder);
                break;
            case 5:
                workspacePane.setCenter(mlbTeamsScreenBorder);
                break;

        }
    }

    private void updatePlayersTableColumns(String pos) {
        //AND THEN ADD THE COLUMS TO THE TABLE
        playerFirstName = new TableColumn(COL_FIRST_NAME);
        playerLastName = new TableColumn(COL_LAST_NAME);
        playerProTeam = new TableColumn(COL_PREV_TEAM);
        playerPositions = new TableColumn(COL_POSITIONS);
        playerYearOfBirth = new TableColumn(COL_YOB);
        playerW = new TableColumn(COL_W);
        playerSV = new TableColumn(COL_SV);
        playerK = new TableColumn(COL_K);
        playerERA = new TableColumn(COL_ERA);
        playerWHIP = new TableColumn(COL_WHIP);
        playerEstimatedValue = new TableColumn(COL_VALUE);
        playerNotes = new TableColumn(COL_NOTES);
        playerR = new TableColumn(COL_R);
        playerHR = new TableColumn(COL_HR);
        playerRBI = new TableColumn(COL_RBI);
        playerSB = new TableColumn(COL_SB);
        playerBA = new TableColumn(COL_BA);
        playerRW = new TableColumn(COL_R_W);
        playerHRSV = new TableColumn(COL_HR_SV);
        playerRBIK = new TableColumn(COL_RBI_K);
        playerSBERA = new TableColumn(COL_SB_ERA);
        playerBAWHIP = new TableColumn(COL_BA_WHIP);

        //ADD THE LABLES TO THE CELL
        playerFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        playerLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        playerProTeam.setCellValueFactory(new PropertyValueFactory<>("previousTeam"));
        playerPositions.setCellValueFactory(new PropertyValueFactory<>("QP"));
        playerYearOfBirth.setCellValueFactory(new PropertyValueFactory<>("yearOfBirth"));
        playerR.setCellValueFactory(new PropertyValueFactory<>("R"));
        playerHR.setCellValueFactory(new PropertyValueFactory<>("HR"));
        playerRBI.setCellValueFactory(new PropertyValueFactory<>("RBI"));
        playerSB.setCellValueFactory(new PropertyValueFactory<>("SB"));
        playerW.setCellValueFactory(new PropertyValueFactory<>("W"));
        playerSV.setCellValueFactory(new PropertyValueFactory<>("SV"));
        playerBA.setCellValueFactory(new PropertyValueFactory<>("BA"));
        playerK.setCellValueFactory(new PropertyValueFactory<>("K"));
        playerERA.setCellValueFactory(new PropertyValueFactory<>("ERA"));
        playerWHIP.setCellValueFactory(new PropertyValueFactory<>("WHIP"));
        playerEstimatedValue.setCellValueFactory(new PropertyValueFactory<>("Estimated Values"));
        playerNotes.setCellValueFactory(new PropertyValueFactory<>("Notes"));
        playerRW.setCellValueFactory(new PropertyValueFactory<>("R_W"));
        playerHRSV.setCellValueFactory(new PropertyValueFactory<>("HR_SV"));
        playerRBIK.setCellValueFactory(new PropertyValueFactory<>("RBI_K"));
        playerSBERA.setCellValueFactory(new PropertyValueFactory<>("SB_ERA"));
        playerBAWHIP.setCellValueFactory(new PropertyValueFactory<>("BA_WHIP"));

        switch (pos) {
            case "P":
                playersTable.getColumns().clear();
                playersTable.getColumns().add(playerFirstName);
                playersTable.getColumns().add(playerLastName);
                playersTable.getColumns().add(playerProTeam);
                playersTable.getColumns().add(playerPositions);
                playersTable.getColumns().add(playerYearOfBirth);
                playersTable.getColumns().add(playerW);
                playersTable.getColumns().add(playerSV);
                playersTable.getColumns().add(playerK);
                playersTable.getColumns().add(playerERA);
                playersTable.getColumns().add(playerWHIP);
                playersTable.getColumns().add(playerEstimatedValue);
                playersTable.getColumns().add(playerNotes);
                playerNotes.setEditable(true);
                break;
            case "All":
                playersTable.getColumns().clear();
                playersTable.getColumns().add(playerFirstName);
                playersTable.getColumns().add(playerLastName);
                playersTable.getColumns().add(playerProTeam);
                playersTable.getColumns().add(playerPositions);
                playersTable.getColumns().add(playerYearOfBirth);
                playersTable.getColumns().add(playerRW);
                playersTable.getColumns().add(playerHRSV);
                playersTable.getColumns().add(playerRBIK);
                playersTable.getColumns().add(playerSBERA);
                playersTable.getColumns().add(playerBAWHIP);
                playersTable.getColumns().add(playerEstimatedValue);
                playersTable.getColumns().add(playerNotes);
                playerNotes.setEditable(true);
                break;
            default:
                playersTable.getColumns().clear();
                playersTable.getColumns().add(playerFirstName);
                playersTable.getColumns().add(playerLastName);
                playersTable.getColumns().add(playerProTeam);
                playersTable.getColumns().add(playerPositions);
                playersTable.getColumns().add(playerYearOfBirth);
                playersTable.getColumns().add(playerR);
                playersTable.getColumns().add(playerHR);
                playersTable.getColumns().add(playerRBI);
                playersTable.getColumns().add(playerSB);
                playersTable.getColumns().add(playerBA);
                playersTable.getColumns().add(playerEstimatedValue);
                playersTable.getColumns().add(playerNotes);
                playerNotes.setEditable(true);
                break;

        }
    }

    private void enableRadioButton() {
        toggle.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            RadioButton position = (RadioButton) newValue.getToggleGroup().getSelectedToggle();
            switch (position.getText()) {
                case "P":
                    updatePlayersTableColumns(position.getText());
                    playerSearchTextField.clear();
                    filteredData = new FilteredList<Player>(dataManager.getDraft().getPitchers(), p -> true);
                    sortedData = new SortedList<Player>(filteredData);
                    sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
                    playersTable.setItems(sortedData);
                    enableNotesHandler();
                    break;
                case "All":
                    updatePlayersTableColumns(position.getText());
                    playerSearchTextField.clear();
                    filteredData = new FilteredList<Player>(dataManager.getDraft().getAllPlayers(), p -> true);
                    sortedData = new SortedList<Player>(filteredData);
                    sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
                    playersTable.setItems(sortedData);
                    enableNotesHandler();
                    break;
                case "C":
                    updatePlayersTableColumns(position.getText());
                    playerSearchTextField.clear();
                    filteredData = new FilteredList<Player>(dataManager.getDraft().getPlayersWithPositions("C"), p -> true);
                    sortedData = new SortedList<Player>(filteredData);
                    sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
                    playersTable.setItems(sortedData);
                    enableNotesHandler();
                    break;
                case "B1":
                    updatePlayersTableColumns(position.getText());
                    playerSearchTextField.clear();
                    filteredData = new FilteredList<Player>(dataManager.getDraft().getPlayersWithPositions("1B"), p -> true);
                    sortedData = new SortedList<Player>(filteredData);
                    sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
                    playersTable.setItems(sortedData);
                    enableNotesHandler();
                    break;
                case "CI":
                    updatePlayersTableColumns(position.getText());
                    playerSearchTextField.clear();
                    filteredData = new FilteredList<Player>(dataManager.getDraft().getPlayersWithPositions("CI"), p -> true);
                    sortedData = new SortedList<Player>(filteredData);
                    sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
                    playersTable.setItems(sortedData);
                    enableNotesHandler();
                    break;
                case "B3":
                    updatePlayersTableColumns(position.getText());
                    playerSearchTextField.clear();
                    filteredData = new FilteredList<Player>(dataManager.getDraft().getPlayersWithPositions("3B"), p -> true);
                    sortedData = new SortedList<Player>(filteredData);
                    sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
                    playersTable.setItems(sortedData);
                    enableNotesHandler();
                    break;
                case "B2":
                    updatePlayersTableColumns(position.getText());
                    playerSearchTextField.clear();
                    filteredData = new FilteredList<Player>(dataManager.getDraft().getPlayersWithPositions("2B"), p -> true);
                    sortedData = new SortedList<Player>(filteredData);
                    sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
                    playersTable.setItems(sortedData);
                    enableNotesHandler();
                    break;
                case "MI":
                    updatePlayersTableColumns(position.getText());
                    playerSearchTextField.clear();
                    filteredData = new FilteredList<Player>(dataManager.getDraft().getPlayersWithPositions("MI"), p -> true);
                    sortedData = new SortedList<Player>(filteredData);
                    sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
                    playersTable.setItems(sortedData);
                    enableNotesHandler();
                    break;
                case "SS":
                    updatePlayersTableColumns(position.getText());
                    playerSearchTextField.clear();
                    filteredData = new FilteredList<Player>(dataManager.getDraft().getPlayersWithPositions("SS"), p -> true);
                    sortedData = new SortedList<Player>(filteredData);
                    sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
                    playersTable.setItems(sortedData);
                    enableNotesHandler();
                    break;
                case "OF":
                    updatePlayersTableColumns(position.getText());
                    playerSearchTextField.clear();
                    filteredData = new FilteredList<Player>(dataManager.getDraft().getPlayersWithPositions("OF"), p -> true);
                    sortedData = new SortedList<Player>(filteredData);
                    sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
                    playersTable.setItems(sortedData);
                    enableNotesHandler();
                    break;
                case "U":
                    updatePlayersTableColumns(position.getText());
                    playerSearchTextField.clear();
                    filteredData = new FilteredList<Player>(dataManager.getDraft().getHitters(), p -> true);
                    sortedData = new SortedList<Player>(filteredData);
                    sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
                    playersTable.setItems(sortedData);
                    enableNotesHandler();
                    break;
            }
        });
    }
    // INITIALIZE THE WINDOW (i.e. STAGE) PUTTING ALL THE CONTROLS
    // THERE EXCEPT THE WORKSPACE, WHICH WILL BE ADDED THE FIRST
    // TIME A NEW Course IS CREATED OR LOADED

    private void initWindow(String windowTitle) {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(windowTitle);

        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // ADD THE TOOLBAR ONLY, NOTE THAT THE WORKSPACE
        // HAS BEEN CONSTRUCTED, BUT WON'T BE ADDED UNTIL
        // THE USER STARTS EDITING A COURSE
        wdkPane = new BorderPane();
        wdkPane.setTop(fileToolbarPaneUpper);

        primaryScene = new Scene(wdkPane);

        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
        // WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
        primaryScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    private void enableNotesHandler() {
        playerNotes.setCellFactory(TextFieldTableCell.forTableColumn());
        playerNotes.setOnEditCommit(new EventHandler<CellEditEvent<Player, String>>() {
            @Override
            public void handle(CellEditEvent<Player, String> s) {
                ((Player) s.getTableView().getItems().get(
                        s.getTablePosition().getRow())).setNotes(s.getNewValue());
            }
        });
    }

    private void initEventHandlers() throws IOException {
        fileController = new FileController(messageDialog, yesNoCancelDialog, draftFileManager, siteExporter);
        newDraftButton.setOnAction((ActionEvent e) -> {
            fileController.handleNewDraftRequest(WDK_GUI.this);
            startingTable.getItems().clear();
        });
        loadDraftButton.setOnAction((ActionEvent e) -> {
            fileController.handleLoadDraftRequest(this);
        });
        saveDraftButton.setOnAction((ActionEvent e) -> {
            fileController.handleSaveDraftRequest(this, dataManager.getDraft());
        });
        exitButton.setOnAction((ActionEvent e) -> {
            fileController.handleExitRequest(WDK_GUI.this);
        });
        fantasyTeamsScreenButton.setOnAction((ActionEvent e) -> {
            handleScreenSwitchRequest(1);

        });
        playersScreenButton.setOnAction((ActionEvent e) -> {
            handleScreenSwitchRequest(2);

        });
        fantasyStandingsScreenButton.setOnAction((ActionEvent e) -> {
            handleScreenSwitchRequest(3);

        });
        draftScreenButton.setOnAction((ActionEvent e) -> {
            handleScreenSwitchRequest(4);

        });
        mlbTeamsScreenButton.setOnAction((ActionEvent e) -> {
            handleScreenSwitchRequest(5);

        });

        //THEN THE DRAFT EDITING CONTROLS
        draftController = new DraftEditController();
        registerTextFieldController(draftNameTextField);

        enableRadioButton();
        enableNotesHandler();
        playerSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Player>) player -> {
                //if filter text is empty, display all players
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (player.getFirstName().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true;
                } else if (player.getLastName().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        // AND PLAYER CONTROLLER FOR ADDING REMOVING AND EDITING PLAYER CONTROLS
        playerController = new PlayerEditController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        addPlayerButton.setOnAction(e -> {
            playerController.handleAddPlayerRequest(this);
        });
        removePlayerButton.setOnAction(e -> {
            playerController.handleRemovePlayerRequest(this, playersTable.getSelectionModel().getSelectedItem());
        });
        // AND NOW THE EDIT PLAYERS
        playersTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                Player p = playersTable.getSelectionModel().getSelectedItem();
                playerController.handleEditPlayerScreenRequest(this, p);
                teamComboBoxActionHandler();
                sortAndUpdatePickNum();
                for (Team item : teamComboBox.getItems()) {
                    item.refreshTeam();
                }
                //startingTable.getItems().clear();
                if (teamComboBox.getSelectionModel().getSelectedItem() != null) {
                    startingTable.setItems(teamComboBox.getSelectionModel().getSelectedItem().getStartupLine());
                    taxiSquadTable.setItems(teamComboBox.getSelectionModel().getSelectedItem().getTaxiSquad());
                }

                teamComboBoxActionHandler();
                for (Team team : dataManager.getDraft().getTeams()) {
                    team.calculateStats();
                }
//dataManager.getDraft().sortTeam(dataManager.getDraft().getTeamItem(p.getFantasyTeamName()));
            }

        });

        //AND TEAM CONTROLLER FOR ADDING REMOVING AND EDITING TEAM CONTROLS
        teamController = new TeamEditController(primaryStage, dataManager.getDraft(), messageDialog, yesNoCancelDialog);
        // AND NOW THE EDIT PLAYERS
        startingTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                Player p = startingTable.getSelectionModel().getSelectedItem();
                playerController.handleEditTeamScreenRequest(this, p);
                sortAndUpdatePickNum();
                teamComboBoxActionHandler();
                for (Team item : teamComboBox.getItems()) {
                    item.refreshTeam();
                }
                //startingTable.getItems().clear();
                startingTable.getColumns().get(0).setVisible(false);
                startingTable.getColumns().get(0).setVisible(true);
                if (teamComboBox.getSelectionModel().getSelectedItem() != null) {
                    startingTable.setItems(teamComboBox.getSelectionModel().getSelectedItem().getStartupLine());
                    taxiSquadTable.setItems(teamComboBox.getSelectionModel().getSelectedItem().getTaxiSquad());
                }
                for (Team team : dataManager.getDraft().getTeams()) {
                    team.calculateStats();
                }
//dataManager.getDraft().sortTeam(dataManager.getDraft().getTeamItem(p.getFantasyTeamName()));
            }

        });
        teamComboBoxActionHandler();
        addTeamButton.setOnAction(e -> {
            teamController.handleAddTeamRequest(this);
            teamComboBox.getSelectionModel().clearSelection();
            teamComboBox.getItems().clear();
            for (Team team : dataManager.getDraft().getTeams()) {
                teamComboBox.getItems().add(team);
                team.calculateStats();

            }
        });
        removeTeamButton.setOnAction(e -> {
            //the VARIABLE AFTER THIS WILL BE THE SELECTED TEAM FROM THE COMBO BOX
            if (teamComboBox.getSelectionModel().getSelectedItem() == null) {
                messageDialog.show("No team has been selected");
            } else {
                teamController.handleRemoveTeamRequest(this, teamComboBox.getSelectionModel().getSelectedItem());
                sortAndUpdatePickNum();
                //startingTable.getItems().clear();
                teamComboBox.getSelectionModel().clearSelection();

                teamComboBox.getItems().clear();
                startingTable.getColumns().get(0).setVisible(false);
                startingTable.getColumns().get(0).setVisible(true);
                taxiSquadTable.getColumns().get(0).setVisible(false);
                taxiSquadTable.getColumns().get(0).setVisible(true);

                for (Team team : dataManager.getDraft().getTeams()) {
                    teamComboBox.getItems().add(team);
                    team.calculateStats();
                }

            }
        });

        editTeamButton.setOnAction(e -> {
            if (teamComboBox.getSelectionModel().getSelectedItem() == null) {
                messageDialog.show("No team has been selected");
            } else {
                teamController.handleEditTeamRequest(this, teamComboBox.getSelectionModel().getSelectedItem());
                teamComboBox.getSelectionModel().clearSelection();
                teamComboBox.getItems().clear();
                for (Team team : dataManager.getDraft().getTeams()) {
                    teamComboBox.getItems().add(team);
                    team.calculateStats();
                }
            }

        });
        teamComboBox.setCellFactory(new Callback<ListView<Team>, ListCell<Team>>() {
            @Override
            public ListCell<Team> call(ListView<Team> team) {
                return new ListCell<Team>() {
                    @Override
                    protected void updateItem(Team item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getTeamName());
                        }
                    }
                };
            }
        });
        teamComboBox.setConverter(new StringConverter<Team>() {
            @Override
            public String toString(Team team) {
                if (team == null) {
                    return null;
                } else {
                    return team.getTeamName();
                }
            }

            @Override
            public Team fromString(String teamName) {
                return null;
            }
        });
        mlbComboBoxActionHandler();
        draftScreenEventHandlers();
        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(playersTable.comparatorProperty());
        playersTable.setItems(sortedData);
        Team team = teamComboBox.getSelectionModel().getSelectedItem();
        if (team != null) {
            startingTable.setItems(team.getStartupLine());

        }
    }

    public void draftScreenEventHandlers() {
        //this screen will handle the three buttons and there events
        //the select player will have its own setOnAction, while the
        //autodraft will consist of the threading and the pausing

        autoDraftPlayerButton.setOnAction(e -> {
            // startDraft = true;

            Task<Void> task = new Task<Void>() {
                ReentrantLock draftLock = new ReentrantLock();
                private boolean startDraft;

                @Override
                protected Void call() throws Exception {
                    draftLock.lock();
                    try {
                        startDraft = true;
                        pauseAutomatedDraftButton.setOnAction(e -> {
                            startDraft = false;
                        });
                        for (int k = 0; k < 500; k++) {

                            if (startDraft) {
                                Platform.runLater(() -> {

                                    selectPlayer();
                                    sortAndUpdatePickNum();
                                });
                                Thread.sleep(100);
                            }
                        }

                    } finally {
                        draftLock.unlock();

                    }
                    return null;
                }

            };

            //THIS GETS THE THREAD STARTED
            Thread thread = new Thread(task);
            thread.start();

        });

        selectPlayerButton.setOnAction(e -> {
//            //pickNumber++;
//            int numTeams = dataManager.getDraft().getTeams().size();
//            int num1 = 0;
//            int num2 = 0;
//            boolean bool = true;
//            boolean bool2 = false;
//            while (bool) {
//                if (num1 == numTeams) {
//                    num1--;
//                    bool = false;
//                    bool2 = true;
//
//                } else if (dataManager.getDraft().getTeams().get(num1).getStartupLine().size() < 23) {
//                    bool = false;
//                } else {
//                    num1++;
//                }
//            }
//            while (bool2) {
//                if (num2 == numTeams) {
//                    num2--;
//                    bool2 = false;
//                } else if (dataManager.getDraft().getTeams().get(num2).getTaxiSquad().size() < 8) {
//                    bool2 = false;
//                } else {
//                    num2++;
//                }
//            }
//            draftController.handleSelectPlayerRequest(num1, num2, dataManager);
//            
            selectPlayer();
            sortAndUpdatePickNum();
        });
    }

    public void selectPlayer() {
        boolean addedPlayer = false;
        for (Team team : dataManager.getDraft().getTeams()) {
            if (team.getStartupLine().size() < 23) {
                HashMap<String, Integer> positionsTable = new HashMap<>();
                team.setPositionTable(positionsTable);
                team.resetPositionTable();
                team.calculatePositionTable();
                positionsTable = team.getPositionTable();
                for (String pos : positionsTable.keySet()) {
                    if (positionsTable.get(pos) > 0 && addedPlayer == false) {
                        while (addedPlayer == false) {
                            int randPlayer = (int) (Math.random() * dataManager.getDraft().getAllPlayers().size());
                            Player player = dataManager.getDraft().getAllPlayers().get(randPlayer);
                            player.setFantasyTeamName(team.getTeamName());
                            player.setContract("S2");
                            player.setSalary(1);
                            if (pos.equals("CI")) {
                                if ((player.getQP().contains("1B") || player.getQP().contains("3B")) && positionsTable.get("CI") > 0) {
                                    player.setP("CI");
                                    addedPlayer = true;
                                    team.addPlayerToStartingLineup(player);
                                    dataManager.getDraft().getAllPlayers().remove(player);
                                    dataManager.getDraft().getDraftedPlayers().add(player);
                                }
                            } else if (pos.equals("MI")) {
                                if ((player.getQP().contains("2B") || player.getQP().contains("SS")) && positionsTable.get("MI") > 0) {
                                    player.setP("MI");
                                    addedPlayer = true;
                                    team.addPlayerToStartingLineup(player);
                                    dataManager.getDraft().getAllPlayers().remove(player);
                                    dataManager.getDraft().getDraftedPlayers().add(player);
                                }
                            } else {
                                if (positionsTable.get(pos) > 0) {
                                    if (player.getQP().contains(pos)) {

                                        player.setP(pos);
                                        addedPlayer = true;
                                        team.addPlayerToStartingLineup(player);
                                        dataManager.getDraft().getAllPlayers().remove(player);
                                        dataManager.getDraft().getDraftedPlayers().add(player);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (addedPlayer == false) {
            for (Team team : dataManager.getDraft().getTeams()) {
                if (team.getTaxiSquad().size() < 8 && addedPlayer == false) {
                    int randPlay = (int) (Math.random() * dataManager.getDraft().getAllPlayers().size() + 1);
                    Player player = dataManager.getDraft().getAllPlayers().get(randPlay);
                    player.setFantasyTeamName(team.getTeamName());
                    player.setContract("X");
                    player.setSalary(1);
                    addedPlayer = true;
                    player.setP(player.getQP());
                    addedPlayer = true;
                    team.getTaxiSquad().add(player);
                    dataManager.getDraft().getAllPlayers().remove(player);
                    dataManager.getDraft().getDraftedPlayers().add(player);
                }
            }
        }

    }

    private void sortAndUpdatePickNum() {
        for (int i = 0; i < dataManager.getDraft().getDraftedPlayers().size(); i++) {
            dataManager.getDraft().getDraftedPlayers().get(i).setPickNum(i + 1);
        }
        for (Team team : dataManager.getDraft().getTeams()) {
            dataManager.getDraft().sortTeam(team);
        }
    }

    private void mlbComboBoxActionHandler() {
        mlbTeamsComboBox.setOnAction(e -> {
            mlbTeamPlayers.getColumns().get(0).setVisible(false);
            mlbTeamPlayers.getColumns().get(0).setVisible(true);
            ObservableList<Player> mlbPlayers = FXCollections.observableArrayList();

            String mlbTeam = mlbTeamsComboBox.getSelectionModel().getSelectedItem().toString();

            for (Player p : sortedData) {
                if (p.getPreviousTeam().equals(mlbTeam)) {
                    mlbPlayers.add(p);
                }
            }
            Comparator<Player> byFirstName = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName());
            Comparator<Player> byLastName = (p1, p2) -> p1.getLastName().compareTo(p2.getLastName());
            Collections.sort(mlbPlayers, byFirstName);
            Collections.sort(mlbPlayers, byLastName);
            mlbTeamPlayers.setItems(mlbPlayers);
        });
    }

    private void teamComboBoxActionHandler() {
        teamComboBox.setOnAction(e -> {
            startingTable.getColumns().get(0).setVisible(false);
            startingTable.getColumns().get(0).setVisible(true);
            //           startingTable.getItems().clear();
            Team team = teamComboBox.getSelectionModel().getSelectedItem();
            if (team != null) {
                startingTable.setItems(team.getStartupLine());
                taxiSquadTable.setItems(team.getTaxiSquad());
            }
        });
    }

    private void registerTextFieldController(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            draftController.handleDraftChangeRequest(this);
        });
    }

    // INIT A BUTTON AND ADD IT TO A CONTAINER IN A TOOLBAR
    private Button initChildButton(Pane toolbar, WDK_PropertyType icon, WDK_PropertyType tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }

    // INIT A LABEL AND SET IT'S STYLESHEET CLASS
    private Label initLabel(WDK_PropertyType labelProperty, String styleClass) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String labelText = props.getProperty(labelProperty);
        Label label = new Label(labelText);
        label.getStyleClass().add(styleClass);
        return label;
    }

    // INIT A LABEL AND PLACE IT IN A GridPane INIT ITS PROPER PLACE
    private Label initGridLabel(GridPane container, WDK_PropertyType labelProperty, String styleClass, int col, int row, int colSpan, int rowSpan) {
        Label label = initLabel(labelProperty, styleClass);
        container.add(label, col, row, colSpan, rowSpan);
        return label;
    }

    // INIT A LABEL AND PUT IT IN A TOOLBAR
    private Label initChildLabel(Pane container, WDK_PropertyType labelProperty, String styleClass) {
        Label label = initLabel(labelProperty, styleClass);
        container.getChildren().add(label);
        return label;
    }

    // INIT A COMBO BOX AND PUT IT IN A GridPane
    private ComboBox initGridComboBox(GridPane container, int col, int row, int colSpan, int rowSpan) throws IOException {
        ComboBox comboBox = new ComboBox();
        container.add(comboBox, col, row, colSpan, rowSpan);
        return comboBox;
    }

    // INIT A TEXT FIELD AND PUT IT IN A GridPane
    private TextField initGridTextField(GridPane container, int size, String initText, boolean editable, int col, int row, int colSpan, int rowSpan) {
        TextField tf = new TextField();
        tf.setPrefColumnCount(size);
        tf.setText(initText);
        tf.setEditable(editable);
        container.add(tf, col, row, colSpan, rowSpan);
        return tf;
    }

    // INIT A CheckBox AND PUT IT IN A TOOLBAR
    // THIS MIGHT NEED TO BE DONE WITH RADIOBUTTONS, NOT CHECKBOXES
    private CheckBox initChildCheckBox(Pane container, String text) {
        CheckBox cB = new CheckBox(text);
        container.getChildren().add(cB);
        return cB;
    }
}
