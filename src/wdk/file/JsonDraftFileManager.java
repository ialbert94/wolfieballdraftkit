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
import wdk.data.Draft;
import wdk.data.Player;
import wdk.file.DraftFileManager;

public class JsonDraftFileManager implements DraftFileManager {

    // JSON FILE READING AND WRITING CONSTANTS

    String JSON_TEAM = "TEAM";
    String JSON_LAST_NAME = "LAST_NAME";
    String JSON_FIRST_NAME = "FIRST_NAME";
    String JSON_QP = "QP";
    String JSON_AB = "AB";
    String JSON_R = "R";
    String JSON_H = "H";
    String JSON_HR = "HR";
    String JSON_RBI = "RBI";
    String JSON_SB = "SB";
    String JSON_NOTES = "NOTES";
    String JSON_YEAR_OF_BIRTH = "YEAR_OF_BIRTH";
    String JSON_NATION_OF_BIRTH = "NATION_OF_BIRTH";
    String JSON_IP = "IP";
    String JSON_ER = "ER";
    String JSON_W = "W";
    String JSON_SV = "SV";
    String JSON_BB = "BB";
    String JSON_K = "K";
    String JSON_PITCHERS = "Pitchers";
    String JSON_HITTERS = "Hitters";

    String JSON_SCHEDULE_ITEM_DESCRIPTION = "description";
    String JSON_SCHEDULE_ITEM_DATE = "date";
    String JSON_SCHEDULE_ITEM_LINK = "link";
    String JSON_LECTURE_TOPIC = "topic";
    String JSON_LECTURE_SESSIONS = "sessions";
    String JSON_ASSIGNMENT_NAME = "name";
    String JSON_ASSIGNMENT_TOPICS = "topics";
    String JSON_ASSIGNMENT_DATE = "date";
    String JSON_EXT = ".json";
    String SLASH = "/";

    /**
     * This method saves all the data associated with a course to a JSON file.
     *
     * @param courseToSave The course whose data we are saving.
     *
     * @throws IOException Thrown when there are issues writing to the JSON
     * file.
     */
    @Override
    public void saveDraft(Draft draftToSave) throws IOException {
//        // BUILD THE FILE PATH
//        String courseListing = "" + draftToSave.getSubject() + draftToSave.getNumber();
//        String jsonFilePath = PATH_COURSES + SLASH + courseListing + JSON_EXT;
//        
//        // INIT THE WRITER
//        OutputStream os = new FileOutputStream(jsonFilePath);
//        JsonWriter jsonWriter = Json.createWriter(os);  
//        
//        // MAKE A JSON ARRAY FOR THE PAGES ARRAY
//        JsonArray pagesJsonArray = makePagesJsonArray(courseToSave.getPages());
//        
//        // AND AN OBJECT FOR THE INSTRUCTOR
//        JsonObject instructorJsonObject = makeInstructorJsonObject(courseToSave.getInstructor());
//        
//        // ONE FOR EACH OF OUR DATES
//        JsonObject startingMondayJsonObject = makeLocalDateJsonObject(courseToSave.getStartingMonday());
//        JsonObject endingFridayJsonObject = makeLocalDateJsonObject(courseToSave.getEndingFriday());
//        
//        // THE LECTURE DAYS ARRAY
//        JsonArray lectureDaysJsonArray = makeLectureDaysJsonArray(courseToSave.getLectureDays());
//        
//        // THE SCHEDULE ITEMS ARRAY
//        JsonArray scheduleItemsJsonArray = makeScheduleItemsJsonArray(courseToSave.getScheduleItems());
//        
//        // THE LECTURES ARRAY
//        JsonArray lecturesJsonArray = makeLecturesJsonArray(courseToSave.getLectures());
//        
//        // THE HWS ARRAY
//        JsonArray hwsJsonArray = makeHWsJsonArray(courseToSave.getAssignments());
//        
//        // NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
//        JsonObject courseJsonObject = Json.createObjectBuilder()
//                                    .add(JSON_SUBJECT, courseToSave.getSubject().toString())
//                                    .add(JSON_NUMBER, courseToSave.getNumber())
//                                    .add(JSON_TITLE, courseToSave.getTitle())
//                                    .add(JSON_SEMESTER, courseToSave.getSemester().toString())
//                                    .add(JSON_YEAR, courseToSave.getYear())
//                                    .add(JSON_PAGES, pagesJsonArray)
//                                    .add(JSON_INSTRUCTOR, instructorJsonObject)
//                                    .add(JSON_STARTING_MONDAY, startingMondayJsonObject)
//                                    .add(JSON_ENDING_FRIDAY, endingFridayJsonObject)
//                                    .add(JSON_LECTURE_DAYS, lectureDaysJsonArray)
//                                    .add(JSON_SCHEDULE_ITEMS, scheduleItemsJsonArray)
//                                    .add(JSON_LECTURES, lecturesJsonArray)
//                                    .add(JSON_HWS, hwsJsonArray)
//                .build();
//        
//        // AND SAVE EVERYTHING AT ONCE
//        jsonWriter.writeObject(courseJsonObject);
    }

    /**
     * Loads the courseToLoad argument using the data found in the json file.
     *
     * @param courseToLoad Course to load.
     * @param jsonFilePath File containing the data to load.
     *
     * @throws IOException Thrown when IO fails.
     */
//    @Override
//    public void loadDraft(Draft draftToLoad, String jsonFilePath) throws IOException {
//        // LOAD THE JSON FILE WITH ALL THE DATA
//        JsonObject json = loadJSONFile(jsonFilePath);
//        
//        // NOW LOAD THE Draft
//        courseToLoad.setSubject(Subject.valueOf(json.getString(JSON_SUBJECT)));
//        courseToLoad.setNumber(json.getInt(JSON_NUMBER));
//        courseToLoad.setSemester(Semester.valueOf(json.getString(JSON_SEMESTER)));
//        courseToLoad.setYear(json.getInt(JSON_YEAR));
//        courseToLoad.setTitle(json.getString(JSON_TITLE));
//        
//        // GET THE PAGES TO INCLUDE 
//        courseToLoad.clearPages();
//        JsonArray jsonPagesArray = json.getJsonArray(JSON_PAGES);
//        for (int i = 0; i < jsonPagesArray.size(); i++)
//            courseToLoad.addPage(CoursePage.valueOf(jsonPagesArray.getString(i)));
//        
//        // GET THE LECTURE DAYS TO INCLUDE
//        courseToLoad.clearLectureDays();
//        JsonArray jsonLectureDaysArray = json.getJsonArray(JSON_LECTURE_DAYS);
//        for (int i = 0; i < jsonLectureDaysArray.size(); i++)
//            courseToLoad.addLectureDay(DayOfWeek.valueOf(jsonLectureDaysArray.getString(i)));
//
//        // LOAD AND SET THE INSTRUCTOR
//        JsonObject jsonInstructor = json.getJsonObject(JSON_INSTRUCTOR);
//        Instructor instructor = new Instructor( jsonInstructor.getString(JSON_INSTRUCTOR_NAME),
//                                                jsonInstructor.getString(JSON_HOMEPAGE_URL));
//        courseToLoad.setInstructor(instructor);
//        
//        // GET THE STARTING MONDAY
//        JsonObject startingMonday = json.getJsonObject(JSON_STARTING_MONDAY);
//        int year = startingMonday.getInt(JSON_YEAR);
//        int month = startingMonday.getInt(JSON_MONTH);
//        int day = startingMonday.getInt(JSON_DAY);
//        courseToLoad.setStartingMonday(LocalDate.of(year, month, day));
//
//        // GET THE ENDING FRIDAY
//        JsonObject endingFriday = json.getJsonObject(JSON_ENDING_FRIDAY);
//        year = endingFriday.getInt(JSON_YEAR);
//        month = endingFriday.getInt(JSON_MONTH);
//        day = endingFriday.getInt(JSON_DAY);
//        courseToLoad.setEndingFriday(LocalDate.of(year, month, day));
//        
//        // GET THE SCHEDULE ITEMS
//        courseToLoad.clearScheduleItems();
//        JsonArray jsonScheduleItemsArray = json.getJsonArray(JSON_SCHEDULE_ITEMS);
//        for (int i = 0; i < jsonScheduleItemsArray.size(); i++) {
//            JsonObject jso = jsonScheduleItemsArray.getJsonObject(i);
//            ScheduleItem si = new ScheduleItem();
//            si.setDescription(jso.getString(JSON_SCHEDULE_ITEM_DESCRIPTION));
//            JsonObject jsoDate = jso.getJsonObject(JSON_SCHEDULE_ITEM_DATE);
//            year = jsoDate.getInt(JSON_YEAR);
//            month = jsoDate.getInt(JSON_MONTH);
//            day = jsoDate.getInt(JSON_DAY);            
//            si.setDate(LocalDate.of(year, month, day));
//            si.setLink(jso.getString(JSON_SCHEDULE_ITEM_LINK));
//            
//            // ADD IT TO THE COURSE
//            courseToLoad.addScheduleItem(si);
//        }
//        
//        // GET THE LECTURES
//        JsonArray jsonLecturesArray = json.getJsonArray(JSON_LECTURES);
//        courseToLoad.clearLectures();
//        for (int i = 0; i < jsonLecturesArray.size(); i++) {
//            JsonObject jso = jsonLecturesArray.getJsonObject(i);
//            Lecture l = new Lecture();
//            l.setTopic(jso.getString(JSON_LECTURE_TOPIC));
//            l.setSessions(jso.getInt(JSON_LECTURE_SESSIONS));
//            
//            // ADD IT TO THE COURSE
//            courseToLoad.addLecture(l);
//        }
//        
//        // GET THE HWS
//        JsonArray jsonHWsArray = json.getJsonArray(JSON_HWS);
//        courseToLoad.clearHWs();
//        for (int i = 0; i < jsonHWsArray.size(); i++) {
//            JsonObject jso = jsonHWsArray.getJsonObject(i);
//            Assignment a = new Assignment();
//            a.setName(jso.getString(JSON_ASSIGNMENT_NAME));
//            JsonObject jsoDate = jso.getJsonObject(JSON_ASSIGNMENT_DATE);
//            year = jsoDate.getInt(JSON_YEAR);
//            month = jsoDate.getInt(JSON_MONTH);
//            day = jsoDate.getInt(JSON_DAY);            
//            a.setDate(LocalDate.of(year, month, day));
//            a.setTopics(jso.getString(JSON_ASSIGNMENT_TOPICS));
//            
//            // ADD IT TO THE COURSE
//            courseToLoad.addAssignment(a);
//        }
//    }
//   
//
//    
//    /**
//     * Saves the subjects list to a json file.
//     * @param subjects List of Subjects to save.
//     * @param jsonFilePath Path of json file.
//     * @throws IOException Thrown when I/O fails.
//     */
//    @Override
//    public void saveSubjects(List<Object> subjects, String jsonFilePath) throws IOException {
//        JsonObject arrayObject = buildJsonArrayObject(subjects);
//        OutputStream os = new FileOutputStream(jsonFilePath);
//        JsonWriter jsonWriter = Json.createWriter(os);  
//        jsonWriter.writeObject(arrayObject);        
//    }
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
    public void loadPlayers(Draft draftToLoad, String jsonHitterPath, String jsonPitcherPath) throws IOException{
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
                playerToLoad.setBA((double)playerToLoad.getH() / (double) playerToLoad.getAB());
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
                playerToLoad.setWHIP((double)(playerToLoad.getH() +playerToLoad.getW()) /  playerToLoad.getIP());
                playerToLoad.setBA_WHIP(playerToLoad.getWHIP());
            }
            if (playerToLoad.getIP() != 0) {
                playerToLoad.setERA((double)(playerToLoad.getER()*9) / playerToLoad.getIP());
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

    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED HITTER
    private JsonObject makeHitterJsonObject(Player player) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_TEAM, player.getAB())
                .build();
        return jso;
    }

    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED PITCHER
    private JsonObject makePitcherJsonObject(Player player) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_ASSIGNMENT_NAME, player.getBB())
                .build();
        return jso;
    }

    // MAKE AN ARRAY OF PLAYERS
    private JsonArray makeHitterJsonArray(ObservableList<Player> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Player p : data) {

            jsb.add(makeHitterJsonObject(p));
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    // MAKE AN ARRAY OF LECTURE ITEMS
    private JsonArray makePitcherJsonArray(ObservableList<Player> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Player p : data) {
            jsb.add(makePitcherJsonObject(p));
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
    public void loadDraft(Draft draftToLoad, String coursePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
