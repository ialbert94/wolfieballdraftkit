
package wdk.data;

/**
 *
 * @author Albert
 */
public class Team {
    //THESE WILL BE THE VARIABLES THAT WILL BELONG
    //TO BOTH THE PITCHERS AND THE HITTERS
    String teamName;
    String teamOwner;
    
    //have to be very careful here. this class will represent a team in the draft
    //so we know that a team has to have a certain amount of players, under
    //a certain amount of positions. might make final variables that will
    //represent these characteristics

    
    public Team() {
        teamName = "";
        teamOwner = "";
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamOwner() {
        return teamOwner;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamOwner(String teamOwner) {
        this.teamOwner = teamOwner;
    }
}
