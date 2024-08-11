package org.jakartaeerecipes.rosterservice.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("jakartaee")
public class JakartaEEResource {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE 10")
                .build();
    }
}
