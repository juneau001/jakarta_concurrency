/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jakartaeerecipes.rosterui.report;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author juneau
 */
@Startup
@Singleton
@ApplicationScoped
public class ScheduledRosterReportRunner {

    Future rosterHandle = null;

    @Resource(name = "concurrent/_defaultManagedScheduledExecutorService")
    ManagedScheduledExecutorService mes;

    public ScheduledRosterReportRunner(){
        
    }
    
    @PostConstruct
    public void rosterScheduler() {
        System.out.println("Scheduling report...");
        ReportRunnable rosterReport = new ReportRunnable("RosterReport");

        rosterHandle = mes.scheduleAtFixedRate(rosterReport, 5L, 5L, TimeUnit.MINUTES);

        System.out.println("Report scheduled....");

    }

}
