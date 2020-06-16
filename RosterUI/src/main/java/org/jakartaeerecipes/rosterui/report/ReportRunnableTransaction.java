/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jakartaeerecipes.rosterui.report;

import java.util.List;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;

import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
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
public class ReportRunnableTransaction implements Runnable {

    private static Logger log = LogManager.getLogger();
    private WebTarget resource;
    private String reportName;
    private List<Roster> rosterList;
    
    @Resource
    UserTransaction ut;

    public ReportRunnableTransaction(String reportName) {
        this.reportName = reportName;
    }

    /**
     * This method is overridden to execute a named report.
     */
    @Override
    public void run() {
        if ("RosterReport".equals(reportName)) {
            try {
                ut.begin();
                invokeRosterReport();
                // perform some data transactions here
                ut.commit();
            } catch (NotSupportedException|RollbackException|SystemException|HeuristicMixedException|HeuristicRollbackException|SecurityException|IllegalStateException ex) {
                java.util.logging.Logger.getLogger(ReportRunnableTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if ("DifferentReport".equals(reportName)) {
            System.out.println("running different report...");

        }
    }

    /**
     * Invokes web service to return roster list.
     */
    protected void invokeRosterReport() {
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
