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
    String lastName;
    String firstName;
    String previousTeam;
    String notes;
    String yearOfBirth;
    String nationOfBirth;
    int H;                  //Hits, earned by hitters, given up by pitchers
    
    
    //THESE WILL BE THE VARIABLES THAT WILL BELONG
    //TO THE PITCHERS
    private double IP;      //Innings pitched
    private int ER;         //Earned Runs
    private int W;          //Wins
    private int SV;         //Saves
    private int BB;         //Bases on Balls earned by hitters, given up by pitchers
    private int K;          //Strikeputs
    double WHIP;            //walks+hits/innings pitched
    double ERA;             //earned runs x9/innings pitched
    
    
    //THESE WILL BE THE VARIABELS THAT WIL BELONG
    //TO THE HITTERS
    private String QP;      //Qualified Positions
    private int AB;         //At Bats
    private int R;          //Runs
    private int HR;         //Home Runs
    private int RBI;        //Runs Batted in
    private int SB;         //Stolen Bases
    double BA;              //calculated batting average as hits/at bats
    
    //THESE WILL BE THE SHARED STATS FOR TABLECOLUMN PURPOSES ONLY
    int R_W;
    int HR_SV;
    int RBI_K;
    double SB_ERA;
    double BA_WHIP;
    
    
    public Player() {
    }

    public double getERA() {
        return ERA;
    }

    public double getWHIP() {
        return WHIP;
    }

    public void setERA(double ERA) {
        this.ERA = ERA;
    }

    public void setWHIP(double WHIP) {
        this.WHIP = WHIP;
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

    public double getBA() {
        return BA;
    }

    public void setBA(double BA) {
        this.BA = BA;
    }

    public void setBA_WHIP(double BA_WHIP) {
        this.BA_WHIP = BA_WHIP;
    }

    public double getBA_WHIP() {
        return BA_WHIP;
    }

    public int getHR_SV() {
        return HR_SV;
    }

    public int getRBI_K() {
        return RBI_K;
    }

    public int getR_W() {
        return R_W;
    }

    public double getSB_ERA() {
        return SB_ERA;
    }

    public void setHR_SV(int HR_SV) {
        this.HR_SV = HR_SV;
    }

    public void setRBI_K(int RBI_K) {
        this.RBI_K = RBI_K;
    }

    public void setR_W(int R_W) {
        this.R_W = R_W;
    }

    public void setSB_ERA(double SB_ERA) {
        this.SB_ERA = SB_ERA;
    }
    
    
   
    
    
    
}
