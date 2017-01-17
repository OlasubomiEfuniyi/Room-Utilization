/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.utilization;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Olasubomi
 */
@Entity
@Table(name = "cis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cis.findAll", query = "SELECT c FROM Cis c"),
    @NamedQuery(name = "Cis.findByStartTime", query = "SELECT c FROM Cis c WHERE c.startTime = :startTime"),
    @NamedQuery(name = "Cis.findByEndTime", query = "SELECT c FROM Cis c WHERE c.endTime = :endTime"),
    @NamedQuery(name = "Cis.findById", query = "SELECT c FROM Cis c WHERE c.id = :id")})
public class Cis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Lob
    @Column(name = "Section")
    private String section;
    @Lob
    @Column(name = "Days")
    private String days;
    @Lob
    @Column(name = "Room")
    private String room;
    @Column(name = "StartTime")
    @Temporal(TemporalType.TIME)
    private String startTime;
    @Column(name = "EndTime")
    @Temporal(TemporalType.TIME)
    private String endTime;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    public Cis() {
    }

    public Cis(Integer id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Cis)) {
            return false;
        }
        Cis other = (Cis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "room.utilization.Cis[ id=" + id + " ]";
    }
    
}
