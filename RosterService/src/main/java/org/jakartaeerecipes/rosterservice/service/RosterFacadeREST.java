/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jakartaeerecipes.rosterservice.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jakartaeerecipes.rosterservice.entity.Roster;

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

    @Context
    private UriInfo uriInfo;

    public RosterFacadeREST() {
        super(Roster.class);
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
        Roster player = new Roster();
        player.setEnterDate(new Date());
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setPosition(position);

        super.create(player);
        log.info("Created roster entry for {} {}", firstName, lastName);
        return Response.created(uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(player.getId()))
                .build())
                .entity(player)
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Integer id, Roster entity) {
        if (entity.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide player").build();
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
        TypedQuery<Roster> rosterQuery = em.createQuery(
                "SELECT o FROM Roster o ORDER BY o.lastName, o.firstName",
                Roster.class);
        return rosterQuery.getResultList();
    }
    
    @GET
    @Path("findByTeam/{team_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Roster> findByTeam(@PathParam("team_id") BigDecimal teamId) {
        TypedQuery<Roster> rosterQuery = em.createQuery(
                "SELECT o FROM Roster o WHERE o.team.id = :teamId ORDER BY o.lastName, o.firstName",
                Roster.class);
        return rosterQuery.setParameter("teamId", teamId.intValueExact()).getResultList();
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
