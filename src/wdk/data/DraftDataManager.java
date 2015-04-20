package wdk.data;

import wdk.file.DraftFileManager;

/**
 * This class manages a Course, which means it knows how to reset one with
 * default values and generate useful dates.
 *
 * @author Richard McKenna
 */
public class DraftDataManager {

    // THIS IS THE DRAFT BEING EDITED

    Draft draft;

    // THIS IS THE UI, WHICH MUST BE UPDATED
    // WHENEVER OUR MODEL'S DATA CHANGES
    DraftDataView view;

    // THIS HELPS US LOAD THINGS FOR OUR COURSE
    DraftFileManager fileManager;

    // DEFAULT INITIALIZATION VALUES FOR NEW COURSES
    //static Subject  DEFAULT_COURSE_SUBJECT = Subject.CSE;
    //static int      DEFAULT_NUM = 0;
    //static String   DEFAULT_TEXT = "Unknown";
    //static Semester DEFAULT_SEMESTER = Semester.FALL;
    public DraftDataManager(DraftDataView initView) {
        view = initView;
        draft = new Draft();
    }

    /**
     * Accessor method for getting the Course that this class manages.
     */
    public Draft getDraft() {
        return draft;
    }

    /**
     * Accessor method for getting the file manager, which knows how to read and
     * write course data from/to files.
     */
    public DraftFileManager getFileManager() {
        return fileManager;
    }

    /**
     * Resets the course to its default initialized settings, triggering the UI
     * to reflect these changes.
     */
    public void reset() {
        draft.resetAllPlayers();
        draft.resetFilteredPlayers();
        draft.resetHitters();
        draft.resetPitchers();
//        
//        // AND THEN FORCE THE UI TO RELOAD THE UPDATED COURSE
        view.reloadDraft(draft);
    }

    // PRIVATE HELPER METHODS
}
