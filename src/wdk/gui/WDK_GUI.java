package wdk.gui;

import java.io.IOException;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import static wdk.WDK_StartupConstants.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
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
import properties_manager.PropertiesManager;
import wdk.WDK_PropertyType;
import wdk.controller.DraftEditController;
import wdk.controller.FileController;
import wdk.controller.PlayerEditController;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import wdk.data.DraftDataView;
import wdk.data.Player;
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
    static final int LARGE_TEXT_FIELD_LENGTH = 60;
    static final int SMALL_TEXT_FIELD_LENGTH = 5;

    //THIS MANAGES ALL OF THE APPLICATION'S DATA
    DraftDataManager dataManager;

    //THIS MANAGES DRAFT FILE I/O
    //REMEMBER THIS IS AN INTERFACT
    DraftFileManager draftFileManager;

    //THIS MANAGES EXPORTING THE DRAFT
    DraftSiteExporter siteExporter;

    //THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS
    FileController fileController;

    //THIS HANDLES INTERACTIONS WITH DRAFT INFO CONTROLS
    DraftEditController draftController;

    //THIS HANDLES REQUESTS TO ADD/EDIT/REMOVE PLAYERS
    PlayerEditController playerController;

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

    //THESE ARE THE CONTROLS FOR THE FANTASY TEAMS SCREEN
    TableView<Player> rostersTable;
    VBox fantasyTeamsScreenBox;
    Label fantasyTeamsScreenHeadingLabel;
    HBox fantasyTeamsScreenToolbar;
    ComboBox teamComboBox;
    Button addTeamButton;
    Button removeTeamButton;
    TextField teamNameTextField;
    TextField teamOwnerTextField;
    GridPane teamsGridPane;

    
    
    //THESE ARE THE CONTROLS FOR THE FANTASY STANDINGS SCREEN
    //THERE WILL BE RADIO BUTTONS ALSO
    TableView<Draft> fantasyTeamsStatsTable;
    VBox fantasyStandingsScreenBox;
    GridPane standingsGridPane;
    Label fantasyStandingsScreenHeadingLabel;
    
    
    
    
    //THESE ARE THE CONTROLS FOR THE DRAFT SCREEN
    TableView<Player> playersDraftedTable;
    VBox draftScreenBox;
    Button autoDraftPlayerButton;
    Button pauseAutomatedDraftButton;
    GridPane draftGridPane;
    Label draftScreenHeadingLabel;
    
    
    
    //THESE ARE THE CONTROLS FOR THE MLB TEAMS SCREEN
    TableView<Player> mlbTeamPlayers;
    ComboBox mlbTeamSelectionComboBox;
    VBox mlbTeamsScreenBox;
    GridPane mlbGridPane;
    Label mlbScreenHeadingLabel;
    
    
    
    
    // AND TABLE COLUMNS   
    public static final String COL_TEAM_NAME = "Name";
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
    public static final String COL_YOB = "Year of Birth";
    public static final String COL_VALUE = "Estimated Value";
    public static final String COL_NOTES = "Notes";

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

    /**
     * This method fully initializes the user interface for use.
     *
     * @param windowTitle The text to appear in the UI window's title bar.
     * @param subjects The list of subjects to choose from.
     * @throws IOException Thrown if any initialization files fail to load.
     */
    public void initGUI(String windowTitle, ArrayList<String> hitters, ArrayList<String> pitchers) throws IOException {
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

    @Override
    public void reloadDraft(Draft draftToReload) {
        // FIRST ACTIVATE THE WORKSPACE IF NECESSARY
        if (!workspaceActivated) {
            activateWorkspace();
        }
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
    private void initAllScreens() {
        initFantasyTeamsScreen();
        initPlayersScreen();
        initStandingsScreen();
        initDraftScreen();
        initMLBScreen();
        
    }
    private void initFantasyTeamsScreen() {



        //FILL THE CONTENT OF THE FANTASY TEAMS SCREEN
        fantasyTeamsScreenBorder = new BorderPane();

        // FIRST OUR SCHEDULE HEADER
        fantasyTeamsScreenBox = new VBox();
        fantasyTeamsScreenHeadingLabel = initChildLabel(fantasyTeamsScreenBox, WDK_PropertyType.FANTASY_TEAMS_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL);
        FlowPane lowerButtons = initLowerToolbar();
        fantasyTeamsScreenBorder.setTop(fantasyTeamsScreenBox);
        fantasyTeamsScreenBorder.setBottom(lowerButtons);

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
        addPlayerButton = initChildButton(playersScreenToolbar, WDK_PropertyType.ADD_ICON, WDK_PropertyType.ADD_ICON, false);
        removePlayerButton = initChildButton(playersScreenToolbar, WDK_PropertyType.MINUS_ICON, WDK_PropertyType.MINUS_ICON, false);
        playerSearchLabel = initGridLabel(playersGridPane, WDK_PropertyType.PLAYER_SEARCH_LABEL, CLASS_PROMPT_LABEL, 2, 2, 1, 1);
        playerSearchTextField = initGridTextField(playersGridPane, LARGE_TEXT_FIELD_LENGTH, EMPTY_TEXT,true, 3,  2, 1, 1);

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
        P.setSelected(true);
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
        
        //ADD THE LABLES TO THE CELL
        playerFirstName.setCellValueFactory(new PropertyValueFactory<>("First"));
        playerLastName.setCellValueFactory(new PropertyValueFactory<>("Last"));
        playerProTeam.setCellValueFactory(new PropertyValueFactory<>("Pro Team"));
        playerPositions.setCellValueFactory(new PropertyValueFactory<>("Positions"));
        playerYearOfBirth.setCellValueFactory(new PropertyValueFactory<>("Year of Birth"));
        playerW.setCellValueFactory(new PropertyValueFactory<>("W"));
        playerSV.setCellValueFactory(new PropertyValueFactory<>("SV"));
        playerK.setCellValueFactory(new PropertyValueFactory<>("K"));
        playerERA.setCellValueFactory(new PropertyValueFactory<>("ERA"));
        playerWHIP.setCellValueFactory(new PropertyValueFactory<>("WHIP"));
        playerEstimatedValue.setCellValueFactory(new PropertyValueFactory<>("Estimated Values"));
        playerNotes.setCellValueFactory(new PropertyValueFactory<>("Notes"));
        
        //AND THEN ADD THE COLUMS TO THE TABLE
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
        
        playersScreenToolbar.getChildren().add(playersGridPane);
        
        topPanePlayer.getChildren().add(playersScreenToolbar);
        topPanePlayer.getChildren().add(radioButtons);
        playersScreenBox.getChildren().add(topPanePlayer);
        playersScreenBox.getChildren().add(playersTable);
        
        playersScreenBorder.setTop(playersScreenBox);
        playersScreenBorder.setBottom(fileToolbarPaneLower);


    }
    private void initStandingsScreen() {
        fantasyStandingsScreenBox = new VBox();
        fantasyStandingsScreenHeadingLabel = initChildLabel(fantasyStandingsScreenBox, WDK_PropertyType.FANTASY_STANDINGS_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL);
        fantasyStandingsScreenBorder = new BorderPane();
        fantasyStandingsScreenBorder.setTop(fantasyStandingsScreenBox);
        fantasyStandingsScreenBorder.setBottom(fileToolbarPaneLower);
    }
    private void initDraftScreen(){
        draftScreenBox = new VBox();
        draftScreenHeadingLabel = initChildLabel(draftScreenBox, WDK_PropertyType.DRAFT_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL);
        draftScreenBorder = new BorderPane();
        draftScreenBorder.setTop(draftScreenBox);
        draftScreenBorder.setBottom(fileToolbarPaneLower);
    }
    private void initMLBScreen() {
        mlbTeamsScreenBox = new VBox();
        mlbScreenHeadingLabel = initChildLabel(mlbTeamsScreenBox, WDK_PropertyType.MLB_TEAMS_SCREEN_HEADING_LABEL, CLASS_HEADING_LABEL);
        mlbTeamsScreenBorder = new BorderPane();
        mlbTeamsScreenBorder.setTop(mlbTeamsScreenBox);
        mlbTeamsScreenBorder.setBottom(fileToolbarPaneLower);
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

    private void initEventHandlers() throws IOException {
        fileController = new FileController(messageDialog, yesNoCancelDialog, draftFileManager, siteExporter);
        newDraftButton.setOnAction(e -> {
            fileController.handleNewDraftRequest(this);
        });
        exitButton.setOnAction(e -> {
            fileController.handleExitRequest(this);
        });
        fantasyTeamsScreenButton.setOnAction(e -> {
            handleScreenSwitchRequest(1);

        });
        playersScreenButton.setOnAction(e -> {
            handleScreenSwitchRequest(2);

        });
        fantasyStandingsScreenButton.setOnAction(e -> {
            handleScreenSwitchRequest(3);

        });
        draftScreenButton.setOnAction(e -> {
            handleScreenSwitchRequest(4);

        });
        mlbTeamsScreenButton.setOnAction(e -> {
            handleScreenSwitchRequest(5);

        });
    }
    // REGISTER THE EVENT LISTENER FOR A TEXT FIELD

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
