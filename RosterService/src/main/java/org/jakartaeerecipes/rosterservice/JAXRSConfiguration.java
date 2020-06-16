package org.jakartaeerecipes.rosterservice;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
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
        resources.add(org.jakartaeerecipes.rosterservice.resources.JavaEE8Resource.class);
        resources.add(org.jakartaeerecipes.rosterservice.service.RosterFacadeREST.class);
        resources.add(org.jakartaeerecipes.rosterservice.service.TeamFacadeREST.class);
    }
}
