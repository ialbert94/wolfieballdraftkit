package wdk;

/**
 * These are properties that are to be loaded from properties.xml. They
 * will provide custom labels and other UI details for our Course Site Builder
 * application. The reason for doing this is to swap out UI text and icons
 * easily without having to touch our code. It also allows for language
 * independence.
 * 
 * @author Albert Ibragimov
 */
public enum WDK_PropertyType {
        // LOADED FROM properties.xml
        PROP_APP_TITLE,
        
        // APPLICATION ICONS
        NEW_DRAFT_ICON,
        LOAD_DRAFT_ICON,
        SAVE_DRAFT_ICON,
        EXPORT_PAGE_ICON,
        DELETE_ICON,
        EXIT_ICON,
        ADD_ICON,
        MINUS_ICON,
        EDIT_ICON,
        MOVE_UP_ICON,
        MOVE_DOWN_ICON,
        HOME_ICON,
        PLAYERS_ICON,
        STANDINGS_ICON,
        DRAFT_ICON,
        MLB_ICON,
        
        // APPLICATION TOOLTIPS FOR BUTTONS
        NEW_DRAFT_TOOLTIP,
        LOAD_DRAFT_TOOLTIP,
        SAVE_DRAFT_TOOLTIP,
        EXPORT_PAGE_TOOLTIP,
        DELETE_TOOLTIP,
        EXIT_TOOLTIP,
        ADD_PLAYER_TOOLTIP,
        REMOVE_PLAYER_TOOLTIP,
        ADD_TEAM_TOOLTIP,
        REMOVE_TEAM_TOOLTIP,
        EDIT_TEAM_TOOLTIP,
        HOME_TOOLTIP,
        PLAYERS_TOOLTIP,
        STANDINGS_TOOLTIP,
        DRAFT_TOOLTIP,
        MLB_TOOLTIP,
 

        // FOR COURSE EDIT WORKSPACE
        PLAYERS_SCREEN_HEADING_LABEL,
        FANTASY_TEAMS_SCREEN_HEADING_LABEL,
        FANTASY_STANDINGS_SCREEN_HEADING_LABEL,
        DRAFT_SCREEN_HEADING_LABEL,
        MLB_TEAMS_SCREEN_HEADING_LABEL,
        
        
        PLAYER_SEARCH_LABEL,
        DRAFT_NAME_LABEL,
        FANTASY_TEAM_LABEL,
        STARTING_SQUAD_LABEL, 
        TAXI_SQUAD_LABEL,
        SELECT_TEAM_LABEL, 
        
        // ERROR DIALOG MESSAGES
        START_DATE_AFTER_END_DATE_ERROR_MESSAGE,
        START_DATE_NOT_A_MONDAY_ERROR_MESSAGE,
        END_DATE_NOT_A_FRIDAY_ERROR_MESSAGE,
        ILLEGAL_DATE_MESSAGE,
        
        // AND VERIFICATION MESSAGES
        NEW_DRAFT_CREATED_MESSAGE,
        DRAFT_LOADED_MESSAGE,
        DRAFT_SAVED_MESSAGE,
        SITE_EXPORTED_MESSAGE,
        SAVE_UNSAVED_WORK_MESSAGE,
        REMOVE_PLAYER_MESSAGE,
        REMOVE_TEAM_MESSAGE, 
}
