/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jakartaeerecipes.rosterui.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juneau
 */

@XmlRootElement
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID")
    private Integer id;
    @Size(min = 1, max = 50)
    private String name;
    private List<Roster> roster;

    public Team() {
    }

    public Team(Integer id) {
        this.id = id;
    }

    public Team(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jakartaeerecipes.rosterservice.entity.Team[ id=" + id + " ]";
    }

    /**
     * @return the roster
     */
    public List<Roster> getRoster() {
        return roster;
    }

    /**
     * @param roster the roster to set
     */
    public void setRoster(List<Roster> roster) {
        this.roster = roster;
    }
    
}
