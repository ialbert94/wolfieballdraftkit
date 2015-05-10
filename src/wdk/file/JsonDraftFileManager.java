package wdk.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import static wdk.WDK_StartupConstants.PATH_DRAFTS;
import wdk.data.Draft;
import wdk.data.Player;
import wdk.data.Team;
import wdk.file.DraftFileManager;

public class JsonDraftFileManager implements DraftFileManager {

    // JSON FILE READING AND WRITING CONSTANTS
    //THESE ARE THE CONSTANTS THAT ARE SHARED
    String JSON_TEAM = "TEAM";
    String JSON_LAST_NAME = "LAST_NAME";
    String JSON_FIRST_NAME = "FIRST_NAME";
    String JSON_H = "H";
    String JSON_NOTES = "NOTES";
    String JSON_YEAR_OF_BIRTH = "YEAR_OF_BIRTH";
    String JSON_NATION_OF_BIRTH = "NATION_OF_BIRTH";

    String JSON_R_W = "R/W";
    String JSON_BA_WHIP = "HR/SV";
    String JSON_SB_ERA = "SB/ERA";
    String JSON_HR_SV = "BA/WHIP";
    String JSON_RBI_K = "RBI/K";
    String JsON_ESTIMATED_VALUE = "Estimated Value";

    //CONSTANTS FOR THE HITTERS
    String JSON_HITTERS = "Hitters";
    String JSON_QP = "QP";
    String JSON_AB = "AB";
    String JSON_R = "R";
    String JSON_HR = "HR";
    String JSON_RBI = "RBI";
    String JSON_SB = "SB";

    //CONSTANTS FOR THE PITCHERS
    String JSON_PITCHERS = "Pitchers";
    String JSON_IP = "IP";
    String JSON_ER = "ER";
    String JSON_W = "W";
    String JSON_SV = "SV";
    String JSON_BB = "BB";
    String JSON_K = "K";

    String JSON_TEAM_NAME = "TEAM_NAME";
    String JSON_TEAM_OWNER = "TEAM_OWNER";
    String JSON_TEAM_PLAYERS = "TEAM_PLAYERS";
    String JSON_POSITION = "POSITION";
    String JSON_PRO_TEAM = "PRO_TEAM";
    String JSON_CONTRACT = "CONTRACT";
    String JSON_SALARY = "SALARY";

    String JSON_EXT = ".json";
    String SLASH = "/";

    String JSON_PLAYERS = "Players";
    String JSON_DRAFT_NAME = "Draft Name";
    String JSON_FANTASY_TEAM = "Fantasy Team";

    JsonArray jsonTeamPlayersArray;
    JsonArray teamJsonArray;

    /**
     * This method saves all the data associated with a course to a JSON file.
     *
     * @param draftToSave The course whose data we are saving.
     *
     * @throws IOException Thrown when there are issues writing to the JSON
     * file.
     */
    @Override
    public void saveDraft(Draft draftToSave) throws IOException {
        // BUILD THE FILE PATH
        String draftListing = "" + draftToSave.getDraftName();
        String jsonFilePath = PATH_DRAFTS + draftListing + JSON_EXT;

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);

        // MAKE A JSON ARRAY FOR THE PLAYERS ARRAY
        JsonArray playersJsonArray = makePlayersJsonArray(draftToSave.getAllPlayers());
        JsonArray teamsJsonArray = makeTeamsJsonArray(draftToSave.getTeams());
//        
        // NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
        JsonObject draftJsonObject = Json.createObjectBuilder()
                .add(JSON_PLAYERS, playersJsonArray)
                .add(JSON_FANTASY_TEAM, teamsJsonArray)
                .build();

        // AND SAVE EVERYTHING AT ONCE
        jsonWriter.writeObject(draftJsonObject);

    }

    @Override
    public void loadDraft(Draft draftToLoad, String draftPath) throws IOException {
        JsonObject json = loadJSONFile(draftPath);

        //first worry about loading the players
        JsonArray jsonPlayersArray = json.getJsonArray(JSON_PLAYERS);
        draftToLoad.resetAllPlayers();

        for (int i = 0; i < jsonPlayersArray.size(); i++) {
            JsonObject jso = jsonPlayersArray.getJsonObject(i);

            Player p = new Player();
            p.setFirstName(jso.getString(JSON_FIRST_NAME));
            p.setLastName(jso.getString(JSON_LAST_NAME));
            p.setPreviousTeam(jso.getString(JSON_TEAM));
            p.setQP(jso.getString(JSON_QP));
            p.setYearOfBirth(jso.getString(JSON_YEAR_OF_BIRTH));
            p.setNationOfBirth(jso.getString(JSON_NATION_OF_BIRTH));
            p.setR_W(jso.getInt(JSON_R_W));
            p.setHR_SV(jso.getInt(JSON_HR_SV));
            p.setRBI_K(jso.getInt(JSON_RBI_K));
            p.setSB_ERA(Double.valueOf(jso.getString(JSON_SB_ERA)));
            p.setBA_WHIP(Double.valueOf(jso.getString(JSON_BA_WHIP)));
            p.setNotes(jso.getString(JSON_NOTES));

            draftToLoad.addToAllPlayers(p);
        }

        //then worry about loading the teams
        JsonArray jsonTeamsArray = json.getJsonArray(JSON_FANTASY_TEAM);

        //CLEAR ALL THE PLAYERS FIRST
        for (Team team : draftToLoad.getTeams()) {
            team.getStartupLine().clear();
            team.getTaxiSquad().clear();
        }

        for (int i = 0; i < jsonTeamsArray.size(); i++) {
            JsonObject jso = jsonTeamsArray.getJsonObject(i);
            Team t1 = new Team();
            t1.setTeamName(jso.getString(JSON_FANTASY_TEAM));
            t1.setTeamOwner(jso.getString(JSON_TEAM_OWNER));
            draftToLoad.addToTeams(t1);

            jsonTeamPlayersArray = jso.getJsonArray(JSON_TEAM_PLAYERS);

            for (int j = 0; j < jsonTeamPlayersArray.size(); j++) {
                JsonObject jso1 = jsonTeamPlayersArray.getJsonObject(j);
                Player player = new Player();

                player.setP(jso1.getString(JSON_POSITION));
                player.setFirstName(jso1.getString(JSON_FIRST_NAME));
                player.setLastName(jso1.getString(JSON_LAST_NAME));
                player.setPreviousTeam(jso1.getString(JSON_PRO_TEAM));
                player.setQP(jso1.getString(JSON_QP));
                player.setR_W(jso1.getInt(JSON_R_W));
                player.setHR_SV(jso1.getInt(JSON_HR_SV));
                player.setRBI_K(jso1.getInt(JSON_RBI_K));
                player.setSB_ERA(Double.valueOf(jso1.getString(JSON_SB_ERA)));
                player.setBA_WHIP(Double.valueOf(jso1.getString(JSON_BA_WHIP)));
                player.setContract(jso1.getString(JSON_CONTRACT));
                player.setSalary(jso1.getInt(JSON_SALARY));
                player.setFantasyTeamName(jso.getString(JSON_FANTASY_TEAM));
                draftToLoad.getTeams().get(i).addPlayerToStartingLineup(player);
            }
        }
    }

    /**
     * Loads pitchers from the json file.
     *
     * @param jsonFilePath Json file containing the subjects.
     * @return List full of Subjects loaded from the file.
     * @throws IOException Thrown when I/O fails.
     */
    @Override
    public ArrayList<String> loadPitchers(String jsonFilePath) throws IOException {
        ArrayList<String> subjectsArray = loadArrayFromJSONFile(jsonFilePath, JSON_PITCHERS);
        ArrayList<String> cleanedArray = new ArrayList();
        for (String s : subjectsArray) {
            // GET RID OF ALL THE QUOTE CHARACTERS
            s = s.replaceAll("\"", "");
            cleanedArray.add(s);
        }
        return cleanedArray;
    }

    /**
     * Loads hitters from the json file.
     *
     * @param jsonFilePath Json file containing the subjects.
     * @return List full of Subjects loaded from the file.
     * @throws IOException Thrown when I/O fails.
     */
    @Override
    public ArrayList<String> loadHitters(String jsonFilePath) throws IOException {
        ArrayList<String> subjectsArray = loadArrayFromJSONFile(jsonFilePath, JSON_HITTERS);
        ArrayList<String> cleanedArray = new ArrayList();
        for (String s : subjectsArray) {
            // GET RID OF ALL THE QUOTE CHARACTERS
            s = s.replaceAll("\"", "");
            cleanedArray.add(s);
        }
        return cleanedArray;
    }

    public void loadPlayers(Draft draftToLoad, String jsonHitterPath, String jsonPitcherPath) throws IOException {
        loadHitter(draftToLoad, jsonHitterPath);
        loadPitcher(draftToLoad, jsonPitcherPath);

    }

    public void loadHitter(Draft draftToLoad, String jsonFilePath) throws IOException {

        JsonObject json = loadJSONFile(jsonFilePath);
        JsonArray jsonHittersArray = json.getJsonArray(JSON_HITTERS);
        for (int i = 0; i < jsonHittersArray.size(); i++) {
            JsonObject jsonHitter = jsonHittersArray.getJsonObject(i);
            Player playerToLoad = new Player();
            playerToLoad.setFirstName(jsonHitter.getString(JSON_FIRST_NAME));
            playerToLoad.setLastName(jsonHitter.getString(JSON_LAST_NAME));
            playerToLoad.setPreviousTeam(jsonHitter.getString(JSON_TEAM));
            playerToLoad.setQP(jsonHitter.getString(JSON_QP));
            playerToLoad.setAB(Integer.valueOf(jsonHitter.getString(JSON_AB)));
            playerToLoad.setR(Integer.valueOf(jsonHitter.getString(JSON_R)));
            playerToLoad.setR_W(playerToLoad.getR());
            playerToLoad.setH(Integer.valueOf(jsonHitter.getString(JSON_H)));
            playerToLoad.setHR(Integer.valueOf(jsonHitter.getString(JSON_HR)));
            playerToLoad.setHR_SV(playerToLoad.getHR());
            playerToLoad.setRBI(Integer.valueOf(jsonHitter.getString(JSON_RBI)));
            playerToLoad.setRBI_K(playerToLoad.getRBI());
            playerToLoad.setSB(Integer.valueOf(jsonHitter.getString(JSON_SB)));
            playerToLoad.setSB_ERA(playerToLoad.getSB());
            if (playerToLoad.getAB() != 0) {
                playerToLoad.setBA((double) playerToLoad.getH() / (double) playerToLoad.getAB());
                playerToLoad.setBA_WHIP(playerToLoad.getBA());
            }
            playerToLoad.setNotes(jsonHitter.getString(JSON_NOTES));
            playerToLoad.setYearOfBirth(jsonHitter.getString(JSON_YEAR_OF_BIRTH));
            playerToLoad.setNationOfBirth(jsonHitter.getString(JSON_NATION_OF_BIRTH));
            draftToLoad.addToFilteredPlayers(playerToLoad);
            draftToLoad.addHitter(playerToLoad);
            draftToLoad.addToAllPlayers(playerToLoad);

        }

    }

    public void loadPitcher(Draft draftToLoad, String jsonFilePath) throws IOException {

        JsonObject json = loadJSONFile(jsonFilePath);
        JsonArray jsonHittersArray = json.getJsonArray(JSON_PITCHERS);
        for (int i = 0; i < jsonHittersArray.size(); i++) {
            JsonObject jsonHitter = jsonHittersArray.getJsonObject(i);
            Player playerToLoad = new Player();
            playerToLoad.setFirstName(jsonHitter.getString(JSON_FIRST_NAME));
            playerToLoad.setLastName(jsonHitter.getString(JSON_LAST_NAME));
            playerToLoad.setPreviousTeam(jsonHitter.getString(JSON_TEAM));
            playerToLoad.setQP("P");
            playerToLoad.setYearOfBirth(jsonHitter.getString(JSON_YEAR_OF_BIRTH));
            playerToLoad.setIP(Double.valueOf(jsonHitter.getString(JSON_IP)));
            playerToLoad.setER(Integer.valueOf(jsonHitter.getString(JSON_ER)));
            playerToLoad.setW(Integer.valueOf(jsonHitter.getString(JSON_W)));
            playerToLoad.setR_W(playerToLoad.getW());
            playerToLoad.setSV(Integer.valueOf(jsonHitter.getString(JSON_SV)));
            playerToLoad.setHR_SV(playerToLoad.getSV());
            playerToLoad.setH(Integer.valueOf(jsonHitter.getString(JSON_H)));
            playerToLoad.setBB(Integer.valueOf(jsonHitter.getString(JSON_BB)));
            playerToLoad.setK(Integer.valueOf(jsonHitter.getString(JSON_K)));
            playerToLoad.setRBI_K(playerToLoad.getK());
            if (playerToLoad.getIP() != 0) {
                playerToLoad.setWHIP((double) (playerToLoad.getH() + playerToLoad.getW()) / playerToLoad.getIP());
                playerToLoad.setBA_WHIP(playerToLoad.getWHIP());
            }
            if (playerToLoad.getIP() != 0) {
                playerToLoad.setERA((double) (playerToLoad.getER() * 9) / playerToLoad.getIP());
                playerToLoad.setSB_ERA(playerToLoad.getERA());
            }
            playerToLoad.setNotes(jsonHitter.getString(JSON_NOTES));

            playerToLoad.setNationOfBirth(jsonHitter.getString(JSON_NATION_OF_BIRTH));
            draftToLoad.addToFilteredPlayers(playerToLoad);
            draftToLoad.addPitcher(playerToLoad);
            draftToLoad.addToAllPlayers(playerToLoad);

        }

    }

    // AND HERE ARE THE PRIVATE HELPER METHODS TO HELP THE PUBLIC ONES
    // LOADS A JSON FILE AS A SINGLE OBJECT AND RETURNS IT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    // LOADS AN ARRAY OF A SPECIFIC NAME FROM A JSON FILE AND
    // RETURNS IT AS AN ArrayList FULL OF THE DATA FOUND
    private ArrayList<String> loadArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ArrayList<String> items = new ArrayList();
        JsonArray jsonArray = json.getJsonArray(arrayName);
        for (JsonValue jsV : jsonArray) {
            items.add(jsV.toString());
        }
        return items;
    }

    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED PLAYERS
    private JsonObject makePlayerJsonObject(Player player) {

        String sb_era = Double.toString(player.getSB_ERA());

        String ba_whip = Double.toString(player.getBA_WHIP());
        JsonObject jso;
        try {
            jso = Json.createObjectBuilder().add(JSON_TEAM, player.getPreviousTeam())
                    .add(JSON_LAST_NAME, player.getLastName())
                    .add(JSON_FIRST_NAME, player.getFirstName())
                    .add(JSON_QP, player.getQP())
                    .add(JSON_YEAR_OF_BIRTH, player.getYearOfBirth())
                    .add(JSON_NATION_OF_BIRTH, player.getNationOfBirth())
                    .add(JSON_R_W, player.getR_W())
                    .add(JSON_HR_SV, player.getHR_SV())
                    .add(JSON_RBI_K, player.getRBI_K())
                    .add(JSON_SB_ERA, sb_era)
                    .add(JSON_BA_WHIP, ba_whip)
                    .add(JSON_NOTES, player.getNotes())
                    .build();
        } catch (Exception e) {
            jso = Json.createObjectBuilder().add(JSON_TEAM, player.getPreviousTeam())
                    .add(JSON_LAST_NAME, player.getLastName())
                    .add(JSON_FIRST_NAME, player.getFirstName())
                    .add(JSON_QP, player.getQP())
                    .add(JSON_YEAR_OF_BIRTH, 0)
                    .add(JSON_NATION_OF_BIRTH, "UN")
                    .add(JSON_R_W, 0)
                    .add(JSON_HR_SV, 0)
                    .add(JSON_RBI_K, 0)
                    .add(JSON_SB_ERA, 0)
                    .add(JSON_BA_WHIP, 0)
                    .add(JSON_NOTES, "added player")
                    .build();
        }

        return jso;
    }

    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED TEAM
    private JsonObject makeTeamJsonObject(Team team) {
        JsonArrayBuilder players = Json.createArrayBuilder();
        ObservableList<Player> teamPlayers = team.getStartupLine();
        for (Player p : teamPlayers) {
            players.add(makePlayerOnTeamJsonObject(p));
        }
        JsonObject jso = Json.createObjectBuilder().add(JSON_FANTASY_TEAM, team.getTeamName())
                .add(JSON_TEAM_OWNER, team.getTeamOwner())
                .add(JSON_TEAM_PLAYERS, players)
                .build();
        return jso;
    }

    private JsonObject makePlayerOnTeamJsonObject(Player player) {
        String sb_era = Double.toString(player.getSB_ERA());
        JsonObject jso;
        String ba_whip = Double.toString(player.getBA_WHIP());
        try{
        jso = Json.createObjectBuilder()
                .add(JSON_POSITION, player.getP())
                .add(JSON_FIRST_NAME, player.getFirstName())
                .add(JSON_LAST_NAME, player.getLastName())
                .add(JSON_PRO_TEAM, player.getPreviousTeam())
                .add(JSON_QP, player.getQP())
                .add(JSON_R_W, player.getR_W())
                .add(JSON_HR_SV, player.getHR_SV())
                .add(JSON_RBI_K, player.getRBI_K())
                .add(JSON_SB_ERA, sb_era)
                .add(JSON_BA_WHIP, ba_whip)
                .add(JSON_CONTRACT, player.getContract())
                .add(JSON_SALARY, player.getSalary())
                .build();
        } catch(Exception e){
            jso = Json.createObjectBuilder()
                .add(JSON_POSITION, player.getP())
                .add(JSON_FIRST_NAME, player.getFirstName())
                .add(JSON_LAST_NAME, player.getLastName())
                .add(JSON_PRO_TEAM, player.getPreviousTeam())
                .add(JSON_QP, player.getQP())
                .add(JSON_R_W, 0)
                .add(JSON_HR_SV, 0)
                .add(JSON_RBI_K, 0)
                .add(JSON_SB_ERA, 0)
                .add(JSON_BA_WHIP,0)
                .add(JSON_CONTRACT, player.getContract())
                .add(JSON_SALARY, player.getSalary())
                .build();
        }
        return jso;
    }

    // MAKE AN ARRAY OF PLAYERS
    private JsonArray makePlayersJsonArray(ObservableList<Player> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Player p : data) {

            jsb.add(makePlayerJsonObject(p));
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    // MAKE AN ARRAY OF TEAMS
    private JsonArray makeTeamsJsonArray(ObservableList<Team> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Team t : data) {
            jsb.add(makeTeamJsonObject(t));
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    // BUILDS AND RETURNS A JsonArray CONTAINING THE PROVIDED DATA
    public JsonArray buildJsonArray(List<Object> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Object d : data) {
            jsb.add(d.toString());
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    // BUILDS AND RETURNS A JsonObject CONTAINING A JsonArray
    // THAT CONTAINS THE PROVIDED DATA
    public JsonObject buildJsonArrayObject(List<Object> data) {
        JsonArray jA = buildJsonArray(data);
        JsonObject arrayObject = Json.createObjectBuilder().add(JSON_HITTERS, jA).build();
        return arrayObject;
    }

    @Override
    public void saveTeams(List<Object> Draft, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> loadTeams(String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
