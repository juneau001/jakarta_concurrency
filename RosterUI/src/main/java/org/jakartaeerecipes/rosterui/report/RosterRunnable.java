/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jakartaeerecipes.rosterui.report;

import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jakartaeerecipes.rosterui.constants.Constants;
import org.jakartaeerecipes.rosterui.model.Roster;
import org.jakartaeerecipes.rosterui.utilities.Utilities;

/**
 *
 * @author juneau
 */
public class RosterRunnable implements Runnable {

    private static Logger log = LogManager.getLogger();
    private WebTarget resource;
    private List<Roster> rosterList;

    public RosterRunnable() {
    }

    /**
     * This method is overridden to execute a named report.
     */
    @Override
    public void run() {
       obtainRoster();
    }

    /**
     * Invokes web service to return roster list.
     */
    protected void obtainRoster() {
        // Web Service Call
        resource = Utilities.obtainClient(Constants.ROSTER_URI, "roster").path("findAll");
       
        setRosterList(resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(new GenericType<List<Roster>>() {
                }));
        rosterList.stream().forEach(r -> System.out.println(r.getFirstName() + " " + r.getLastName() + " - " + r.getPosition()));
    }

    /**
     * @return the rosterList
     */
    public List<Roster> getRosterList() {
        return rosterList;
    }

    /**
     * @param rosterList the rosterList to set
     */
    public void setRosterList(List<Roster> rosterList) {
        this.rosterList = rosterList;
    }

}
