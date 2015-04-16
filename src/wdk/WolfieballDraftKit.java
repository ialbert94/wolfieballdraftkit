/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static wdk.WDK_PropertyType.PROP_APP_TITLE;
import static wdk.WDK_StartupConstants.JSON_FILE_PATH_HITTERS;
import static wdk.WDK_StartupConstants.JSON_FILE_PATH_PITCHERS;
import static wdk.WDK_StartupConstants.PATH_BASE;
import static wdk.WDK_StartupConstants.PATH_DATA;
import static wdk.WDK_StartupConstants.PATH_SITES;
import static wdk.WDK_StartupConstants.PROPERTIES_FILE_NAME;
import static wdk.WDK_StartupConstants.PROPERTIES_SCHEMA_FILE_NAME;
import wdk.data.DraftDataManager;
import wdk.error.ErrorHandler;
import wdk.file.DraftSiteExporter;
import wdk.file.JsonDraftFileManager;
import wdk.gui.WDK_GUI;
import xml_utilities.InvalidXMLFileFormatException;

/**
 *
 * @author Albert
 */
public class WolfieballDraftKit extends Application {
    //THIS IS THE FULL USER INTERFACE WHICH WILL BE INITIALIZED
    //AFTER THE PROPERTIES FILE IS LOADED
    WDK_GUI gui;
    
    /**
     * This s where our Application begins its initialization, it will
     * create the GUI and initialize all of its components.
     * 
     * @param primaryStage This application's window.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        //LET'S START BY GIVING THE PRIMARY STAGE TO OUR ERRORHANDLER
        ErrorHandler eH = ErrorHandler.getErrorHandler();
        eH.initMessageDialog(primaryStage);
        
        //LOAD APP SETTINS INTO THE GUI AND START IT UP
        boolean success = loadProperties();
        if(success) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String appTitle = props.getProperty(PROP_APP_TITLE);
            try{
                //WE WILL SAVE OUR DRAFT DATA USING THE JSON FILE
                //FORMAT SO WE'LL LET THIS OBJECT DO THIS FOR US
                
                JsonDraftFileManager jsonFileManager = new JsonDraftFileManager();
                
                //AND THIS ONE WILL DO THE DRAFT WEB PAGE EXPORTING
                
                //DraftSiteExporter exporter = new DraftSiteExporter(PATH_BASE, PATH_SITES);
                
                //THIS SHOULD LOAD THE PITCHERS AND HITTERS
                //LETS COMMENT THIS OUT FOR NOW
                
                ArrayList<String> hitters = jsonFileManager.loadHitters(JSON_FILE_PATH_HITTERS);
                ArrayList<String> pitchers = jsonFileManager.loadPitchers(JSON_FILE_PATH_PITCHERS);
                
                jsonFileManager.loadHitter(JSON_FILE_PATH_HITTERS);
                //AND NOW GIVE ALL THIS STUFF TO THE UI
                //INITIALIZE THE USER INTERFACE COMPOINENTS
                gui = new WDK_GUI(primaryStage);
                
                //COMMENT THESE OUR FOR NOW TOO
                //gui.setCourseFileManager(jsonFileManager);
                //gui.setSiteExporter(exporter);
                
                // CONSTRUCT THE DATA MANAGER AND GIVE IT TO THE GUI
                DraftDataManager dataManager = new DraftDataManager(gui); 
                gui.setDataManager(dataManager);
                
                //FINALLY START UP THE ISER INTERFACE WINDOW AFTER ALL
                //REMAINING INITIALIZATION
                gui.initGUI(appTitle, hitters, pitchers);
            } catch (IOException ioe) {
                eH = ErrorHandler.getErrorHandler();
                eH.handlePropertiesFileError();
            }
        }
    }
        
     /**
     * Loads this application's properties file, which has a number of settings
     * for initializing the user interface.
     * 
     * @return true if the properties file was loaded successfully, false otherwise.
     */
    public boolean loadProperties() {
        try {
            // LOAD THE SETTINGS FOR STARTING THE APP
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
            props.loadProperties(PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
            return true;
       } catch (InvalidXMLFileFormatException ixmlffe) {
            // SOMETHING WENT WRONG INITIALIZING THE XML FILE
            ErrorHandler eH = ErrorHandler.getErrorHandler();
            eH.handlePropertiesFileError();
            return false;
        }        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
    
}
