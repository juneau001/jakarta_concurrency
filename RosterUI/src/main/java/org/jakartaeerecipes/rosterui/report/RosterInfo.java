
package org.jakartaeerecipes.rosterui.report;

import java.util.List;
import org.jakartaeerecipes.rosterui.model.Roster;

/**
 *
 * @author Juneau
 */
public class RosterInfo {
    
    public String team;
    public List<Roster> players = null;

    public RosterInfo(String team, List<Roster> players){
        this.team = team;
        this.players = players;
    }
    
}
