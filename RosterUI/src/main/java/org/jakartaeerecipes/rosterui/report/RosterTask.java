
package org.jakartaeerecipes.rosterui.report;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.enterprise.concurrent.ManagedTask;
import javax.enterprise.concurrent.ManagedTaskListener;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import org.jakartaeerecipes.rosterui.constants.Constants;
import org.jakartaeerecipes.rosterui.model.Roster;
import org.jakartaeerecipes.rosterui.model.Team;
import org.jakartaeerecipes.rosterui.utilities.Utilities;

/**
 *
 * @author Juneau
 */

public class RosterTask implements Callable<RosterInfo>, ManagedTask {
    // The ID of the request to report on demand.
    Integer teamId;
    RosterInfo rosterInfo;
    private WebTarget resource;
    Map<String, String> execProps;

    public RosterTask(Integer id) {
        this.teamId = id;
        execProps = new HashMap<>();
       
        execProps.put(ManagedTask.IDENTITY_NAME, getIdentityName());
    }

    public RosterInfo call() {
        // Web Service Call
        
        resource = Utilities.obtainClient(Constants.ROSTER_URI, "team");
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{teamId}));
        Team team = null;
        team = (resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(new GenericType<Team>() {
                }));
        resource = Utilities.obtainClient(Constants.ROSTER_URI, "roster");
        resource = resource.path(java.text.MessageFormat.format("findByTeam/{0}", new Object[]{teamId}));
        List<Roster> playerList = null;
        playerList = (resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(new GenericType<List<Roster>>() {
                }));
       
        return new RosterInfo(team.getName(), playerList);
    }

    public String getIdentityName() {
        return "RosterTask: TeamID=" + teamId;
    }

    public Map<String, String> getExecutionProperties() {
        return execProps;
    }

    public String getIdentityDescription(Locale locale) { 
        // Use a resource bundle...
        return "RosterTask asynchronous REST service invoker";
    }

    @Override
    public ManagedTaskListener getManagedTaskListener() {
        return new CustomManagedTaskListener();
    }


}
