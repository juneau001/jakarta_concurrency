
package org.jakartaeerecipes.rosterui.report;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import jakarta.enterprise.concurrent.ManagedTask;
import jakarta.enterprise.concurrent.ManagedTaskListener;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
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
    private final Integer teamId;
    private final Map<String, String> execProps;

    public RosterTask(Integer id) {
        this.teamId = id;
        this.execProps = Map.of(ManagedTask.IDENTITY_NAME, getIdentityName());
    }

    @Override
    public RosterInfo call() {
        Team team = Utilities.obtainClient(Constants.ROSTER_URI, "team")
                .path(java.text.MessageFormat.format("{0}", new Object[]{teamId}))
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<Team>() {
                });
        List<Roster> playerList = Utilities.obtainClient(Constants.ROSTER_URI, "roster")
                .path(java.text.MessageFormat.format("findByTeam/{0}", new Object[]{teamId}))
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Roster>>() {
                });

        return new RosterInfo(team.getName(), playerList);
    }

    @Override
    public String getIdentityName() {
        return "RosterTask: TeamID=" + teamId;
    }

    @Override
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
