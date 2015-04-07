package wdk.gui;

import java.io.IOException;
import java.util.ArrayList;
import static wdk.WDK_StartupConstants.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

    static final String PRIMARY_STYLE_SHEET = PATH_CSS + "csb_style.css";
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_SUBJECT_PANE = "subject_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    static final String CLASS_PROMPT_LABEL = "prompt_label";
    static final String EMPTY_TEXT = "";
    static final int LARGE_TEXT_FIELD_LENGTH = 20;
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

    //THIS PAGE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    //APPLICATION GUI, AND WILL STORE THE 5 PAGES
    StackPane screensPane;
    
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
    TableView<Player> playersTable;
    TextField playerSearchTextField;
    HBox playersScreenToolbar;
    Button addPlayerButton;
    Button removePlayerButton;

    //THESE ARE THE CONTROLS FOR THE FANTASY TEAMS SCREEN
    TableView<Player> rostersTable;
    HBox fantasyTeamsScreenToolbar;
    ComboBox teamComboBox;
    Button addTeamButton;
    Button removeTeamButton;
    TextField teamNameTextField;
    TextField teamOwnerTextField;

    //THESE ARE THE CONTROLS FOR THE FANTASY STANDINGS SCREEN
    //THERE WILL BE RADIO BUTTONS ALSO
    TableView<Draft> fantasyTeamsStatsTable;

    //THESE ARE THE CONTROLS FOR THE DRAFT SCREEN
    TableView<Player> playersDraftedTable;
    HBox draftScreenToolbar;
    Button autoDraftPlayerButton;
    Button pauseAutomatedDraftButton;

    //THESE ARE THE CONTROLS FOR THE MLB TEAMS SCREEN
    TableView<Player> mlbTeamPlayers;
    ComboBox mlbTeamSelectionComboBox;

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
    public DraftDataManager getDataManater() {
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
    public void initGUI(String windowTitle) throws IOException {
        // INIT THE DIALOGS
        initDialogs();

        // INIT THE TOOLBAR
        initFileToolbar();

        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET
        initWorkspace(subjects);

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
    public void activateWorkspace(StackPane screenPane) {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            wdkPane.setCenter(screenPane.getParent());
            workspaceActivated = true;
        }
    }
    
    
    
    /**
     * This method is used to activate/deactivate toolbar buttons when
     * they can and cannot be used so as to provide foolproof design.
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
}
