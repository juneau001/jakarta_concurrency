/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jakartaeerecipes.rosterservice.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juneau
 */
@Entity
@Table(name = "ROSTER")
@NamedQueries({
    @NamedQuery(name = "Roster.findAll", query = "SELECT r FROM Roster r"),
    @NamedQuery(name = "Roster.findById", query = "SELECT r FROM Roster r WHERE r.id = :id"),
    @NamedQuery(name = "Roster.findByEnterDate", query = "SELECT r FROM Roster r WHERE r.enterDate = :enterDate"),
    @NamedQuery(name = "Roster.findByFirstName", query = "SELECT r FROM Roster r WHERE r.firstName = :firstName"),
    @NamedQuery(name = "Roster.findByLastName", query = "SELECT r FROM Roster r WHERE r.lastName = :lastName"),
    @NamedQuery(name = "Roster.findByPosition", query = "SELECT r FROM Roster r WHERE r.position = :position")})
@XmlRootElement
public class Roster implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name = "ROSTER_S_GEN", sequenceName = "ROSTER_S", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROSTER_S_GEN")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ENTER_DATE")
    @Temporal(TemporalType.DATE)
    private Date enterDate;
    @Size(max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Size(max = 50)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Size(max = 100)
    @Column(name = "POSITION")
    private String position;
    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public Roster() {
    }

    public Roster(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
        if (!(object instanceof Roster)) {
            return false;
        }
        Roster other = (Roster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jakartaeerecipes.rosterservice.entity.Roster[ id=" + id + " ]";
    }

    /**
     * @return the team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(Team team) {
        this.team = team;
    }

}
