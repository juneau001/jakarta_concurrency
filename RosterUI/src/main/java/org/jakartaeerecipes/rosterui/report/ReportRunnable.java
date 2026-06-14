/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jakartaeerecipes.rosterui.report;

import java.util.List;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jakartaeerecipes.rosterui.constants.Constants;
import org.jakartaeerecipes.rosterui.model.Roster;
import org.jakartaeerecipes.rosterui.utilities.Utilities;

/**
 *
 * @author juneau
 */
public class ReportRunnable implements Runnable {

    private static final Logger log = LogManager.getLogger();
    private final String reportName;
    private List<Roster> rosterList;

    public ReportRunnable(String reportName) {
        this.reportName = reportName;
    }

    /**
     * This method is overridden to execute a named report.
     */
    @Override
    public void run() {
        if ("RosterReport".equals(reportName)) {
            invokeRosterReport();

        } else if ("DifferentReport".equals(reportName)) {
            System.out.println("running different report...");

        }
    }

    /**
     * Invokes web service to return roster list.
     */
    protected void invokeRosterReport() {
        setRosterList(Utilities.obtainClient(Constants.ROSTER_URI, "roster")
                .path("findAll")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Roster>>() {
                }));
        rosterList.forEach(r -> log.info("{} {} - {}", r.getFirstName(), r.getLastName(), r.getPosition()));
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
