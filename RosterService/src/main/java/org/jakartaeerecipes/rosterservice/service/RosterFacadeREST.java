/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jakartaeerecipes.rosterservice.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jakartaeerecipes.rosterservice.entity.Roster;
import org.jakartaeerecipes.rosterservice.entity.Team;

/**
 *
 * @author juneau
 */
@Stateless
@Path("roster")
public class RosterFacadeREST extends AbstractFacade<Roster> {

    private static final Logger log = LogManager.getLogger();

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public RosterFacadeREST() {
        super(Roster.class);
    }
    
    @PostConstruct
    public void init(){
        System.out.println("Constructing RosterFacade");
        System.out.println("Count: " + countREST());
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Roster entity) {
        super.create(entity);
    }

    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_XML)
    public Response add(@FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("position") String position) {
        Response result = null;
        Roster player = new Roster();
        player.setEnterDate(new Date());
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setPosition(position);

        super.create(player);
        result = Response.ok().entity(player).build();
        return result;
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Integer id, Roster entity) {
        if(entity.getId() == null){
            return Response.status(400).entity("Please provide player").build();
        }
        super.edit(entity);
        return Response.ok().entity(entity).build();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Roster find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Path("findAll")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Roster> findAll() {
        List<Roster> roster = null;
       
            roster = em.createQuery("select object(o) from Roster o")
                    .getResultList();
            
        return roster;
    }
    
    @GET
    @Path("findByTeam/{team_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Roster> findByTeam(@PathParam("team_id") BigDecimal team) {
        List<Roster> roster = null;
        System.out.println("here in the method: " + team);
            roster = em.createQuery("select object(o) from Roster o "
            + "where o.team.id = :team")
                    .setParameter("team", team)
                    .getResultList();
            
        return roster;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Roster> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
