/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jakartaeerecipes.rosterui.controller;

import java.io.Serial;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.enterprise.concurrent.ManagedThreadFactory;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jakartaeerecipes.rosterui.constants.Constants;
import org.jakartaeerecipes.rosterui.model.Roster;
import org.jakartaeerecipes.rosterui.report.ReportRunnable;
import org.jakartaeerecipes.rosterui.report.RosterRunnable;
import org.jakartaeerecipes.rosterui.utilities.Utilities;

/**
 *
 * @author juneau
 */
@Named
@ViewScoped
public class RosterController implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Resource
    private ManagedExecutorService mes;
    
    @Resource
    private ManagedThreadFactory mtf;
    
    private transient Thread rosterThread;

    private static final Logger log = LogManager.getLogger();

    private List<Roster> rosterList;

    private Roster current;

    private boolean managePlayer = false;

    public RosterController() {

    }

    @PostConstruct
    public void init() {
        populateRosterList();
    }

    /**
     * Example of ManagedExecutorService. This action method is invoked when the
     * Refresh List button is pressed within the roster list view. It will send
     * the call for the population of the roster list to the
     * ManagedExecutorService for processing.
     *
     */
    public void refreshRosterList() {
        populateRosterList();
    }

    /**
     * Populate the List<Roster>.
     */
    public void populateRosterList() {
        setRosterList(Utilities.obtainClient(Constants.ROSTER_URI, "roster")
                .path("findAll")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Roster>>() {
                }));
    }

    /**
     * Given an ID, return the corresponding <code>Roster</code>.
     *
     * @param id
     */
    public void findById(int id) {
        WebTarget resource = Utilities.obtainClient(Constants.ROSTER_URI, "roster")
                .path(java.text.MessageFormat.format("findById/{0}", new Object[]{id}));
        current = resource.request(MediaType.APPLICATION_XML)
                // .cookie(HttpHeaders.AUTHORIZATION, authenticationController.getSessionToken())
                .get(
                        new GenericType<Roster>() {
                });
    }

    public String addPlayer() {
        String returnPage = null;
        WebTarget resource = Utilities.obtainClient(Constants.ROSTER_URI, "roster").path("add");
        Form form = new Form();
        form.param("firstName", current.getFirstName().toUpperCase());
        form.param("lastName", current.getLastName().toUpperCase());
        form.param("position", current.getPosition().toUpperCase());
        Invocation.Builder invocationBuilder = resource.request(MediaType.APPLICATION_XML);
        //  .cookie(HttpHeaders.AUTHORIZATION, authenticationController.getSessionToken());
        Response response = invocationBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);
        if (response.getStatus() == Status.CREATED.getStatusCode() || response.getStatus() == Status.OK.getStatusCode()) {
            log.info("Successful roster Entry");
            Utilities.addSuccessMessage("Player Successfully Added");
            rosterList = null;
            populateRosterList();
            returnPage = "index";
        } else {
            log.error("Player entry error");
            Utilities.addErrorMessage("Error entering player, please try again.  If issue persists, please contact service desk.");
        }
        return returnPage;
    }

    public void remove(Roster player) {
        WebTarget resource = Utilities.obtainClient(Constants.ROSTER_URI, "roster")
                .path(java.text.MessageFormat.format("/{0}", new Object[]{player.getId()}));
        try {
            resource.request().delete();

            Utilities.addSuccessMessage("Removed Player");
            rosterList = null;
            populateRosterList();
        } catch (Exception e) {
            Utilities.addErrorMessage("Error Removing Player");
        }
    }

    public void updatePlayer() {
        WebTarget resource = Utilities.obtainClient(Constants.ROSTER_URI, "roster")
                .path(java.text.MessageFormat.format("/{0}", new Object[]{current.getId()}));

        Response response
                = resource.request().put(Entity.entity(current, MediaType.APPLICATION_XML));
        if (response.getStatus() == Status.OK.getStatusCode()) {
            Utilities.addSuccessMessage("Updated Player");
            rosterList = null;
            managePlayer = false;
            populateRosterList();
        } else {
            Utilities.addErrorMessage("Error Removing Player");
        }
    }

    public void manage(Roster player) {
        current = player;
        managePlayer = true;
    }

    public void cancelAction() {
        managePlayer = false;
        current = null;
        rosterList = null;
        populateRosterList();
    }

    public void clear(AjaxBehaviorEvent event) {
        cancelAction();
    }

    public void invokeRosterReport() {
        ReportRunnable rosterReport = new ReportRunnable("RosterReport");
        Future<?> reportFuture = mes.submit(rosterReport);
        try {
            reportFuture.get();
            Utilities.addSuccessMessage("Roster report complete");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.error("Roster report interrupted", ex);
            Utilities.addErrorMessage("Roster report interrupted");
        } catch (ExecutionException ex) {
            log.error("Roster report failed", ex);
            Utilities.addErrorMessage("Roster report failed");
        }
    }
    
    public void invokeThreaddedRosterReport() {
        RosterRunnable rosterReport = new RosterRunnable();

        rosterThread = mtf.newThread(rosterReport);
        rosterThread.setName("roster-report-thread");
        rosterThread.start();
        Utilities.addSuccessMessage("Threaded roster report started");
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

    /**
     * @return the current
     */
    public Roster getCurrent() {
        if (current == null) {
            current = new Roster();
        }
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(Roster current) {
        this.current = current;
    }

    /**
     * @return the managePlayer
     */
    public boolean isManagePlayer() {
        return managePlayer;
    }

    /**
     * @param managePlayer the managePlayer to set
     */
    public void setManagePlayer(boolean managePlayer) {
        this.managePlayer = managePlayer;
    }

}
