package wdk.data;

/**
 *This is the player class, that will hold all the statistics
 * of the baseball players that will be added to the team.
 * Note that they consist of Hitters and pitchers.
 * 
 * @author Albert
 */
public class Player {
    //THESE WILL BE THE VARIABLES THAT WILL BELONG
    //TO BOTH THE PITCHERS AND THE HITTERS
    private String lastName;
    private String firstName;
    private String previousTeam;
    private String notes;
    private String yearOfBirth;
    private String nationOfBirth;
    private int H;          //Hits, earned by hitters, given up by pitchers
    
    //THESE WILL BE THE VARIABLES THAT WILL BELONG
    //TO THE PITCHERS
    private double IP;      //Innings pitched
    private int ER;         //Earned Runs
    private int W;          //Wins
    private int SV;         //Saves
    private int BB;         //Bases on Balls earned by hitters, given up by pitchers
    private int K;          //Strikeputs
    
    //THESE WILL BE THE VARIABELS THAT WIL BELONG
    //TO THE HITTERS
    private String QP;      //Qualified Positions
    private int AB;         //At Bats
    private int R;          //Runs
    private int HR;         //Home Runs
    private int RBI;        //Runs Batted in
    private int SB;         //Stolen Bases

    public Player() {
    }

    public Player(String lastName, String firstName, String previousTeam, String notes, String yearOfBirth, String nationOfBirth, int H, double IP, int ER, int W, int SV, int BB, int K, String QP, int AB, int R, int HR, int RBI, int SB) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.previousTeam = previousTeam;
        this.notes = notes;
        this.yearOfBirth = yearOfBirth;
        this.nationOfBirth = nationOfBirth;
        this.H = H;
        this.IP = IP;
        this.ER = ER;
        this.W = W;
        this.SV = SV;
        this.BB = BB;
        this.K = K;
        this.QP = QP;
        this.AB = AB;
        this.R = R;
        this.HR = HR;
        this.RBI = RBI;
        this.SB = SB;
    }

    public int getAB() {
        return AB;
    }

    public int getBB() {
        return BB;
    }

    public int getER() {
        return ER;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getH() {
        return H;
    }

    public int getHR() {
        return HR;
    }

    public double getIP() {
        return IP;
    }

    public int getK() {
        return K;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationOfBirth() {
        return nationOfBirth;
    }

    public String getNotes() {
        return notes;
    }

    public String getPreviousTeam() {
        return previousTeam;
    }

    public String getQP() {
        return QP;
    }

    public int getR() {
        return R;
    }

    public int getRBI() {
        return RBI;
    }

    public int getSB() {
        return SB;
    }

    public int getSV() {
        return SV;
    }

    public int getW() {
        return W;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setAB(int AB) {
        this.AB = AB;
    }

    public void setBB(int BB) {
        this.BB = BB;
    }

    public void setER(int ER) {
        this.ER = ER;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setH(int H) {
        this.H = H;
    }

    public void setHR(int HR) {
        this.HR = HR;
    }

    public void setIP(double IP) {
        this.IP = IP;
    }

    public void setK(int K) {
        this.K = K;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNationOfBirth(String nationOfBirth) {
        this.nationOfBirth = nationOfBirth;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPreviousTeam(String previousTeam) {
        this.previousTeam = previousTeam;
    }

    public void setQP(String QP) {
        this.QP = QP;
    }

    public void setR(int R) {
        this.R = R;
    }

    public void setRBI(int RBI) {
        this.RBI = RBI;
    }

    public void setSB(int SB) {
        this.SB = SB;
    }

    public void setSV(int SV) {
        this.SV = SV;
    }

    public void setW(int W) {
        this.W = W;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
    
    
    
    
}
