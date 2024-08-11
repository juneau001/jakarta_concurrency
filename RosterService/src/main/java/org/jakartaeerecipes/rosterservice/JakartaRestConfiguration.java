package org.jakartaeerecipes.rosterservice;

import java.util.Set;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jakartaeerecipes.rosterservice.resources.JakartaEEResource;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(JakartaEEResource.class);
        resources.add(org.jakartaeerecipes.rosterservice.service.RosterFacadeREST.class);
        resources.add(org.jakartaeerecipes.rosterservice.service.TeamFacadeREST.class);
    }
}
