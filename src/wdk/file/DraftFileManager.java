package wdk.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import wdk.data.Draft;

/**
 *
 * @author Albert
 */

//REMEMBER TO MAKE THIS AN INTERFACT AND LOAD ALL
//PROPER METHODS INTO IT

public interface DraftFileManager {
    public void                 saveDraft(Draft draftToSave) throws IOException;
    public void                 loadDraft(Draft draftToLoad, String coursePath) throws IOException;
    public void                 saveTeams(List<Object> Draft, String filePath) throws IOException;
    public ArrayList<String>    loadTeams(String filePath) throws IOException;
    //public ArrayList<String>    loadPlayers(String filePath) throws IOException;
    public ArrayList<String>    loadPitchers(String filePath) throws IOException;
    public ArrayList<String>    loadHitters(String filePath) throws IOException;
}
