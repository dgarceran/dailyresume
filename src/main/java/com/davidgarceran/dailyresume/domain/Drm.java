package com.davidgarceran.dailyresume.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 02/06/2016
 *
 * Compiles the best posts of the day in the database.
 */
@Entity
@Table(name = "drm_filtered")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Drm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "drm")
    int drm;

    @Column(name = "idresource") // id / idrt / reblogkey
        String idresource;

    @Column(name = "timestamp")
    long timestamp;

    @Column(name = "service") // twitter / tumblr
        String service;

    @Column(name = "username")
    String username;

    public Drm(){}

    public Drm(int drm, String idresource, long timestamp, String service, String username) {
        this.drm = drm;
        this.idresource = idresource;
        this.timestamp = timestamp;
        this.service = service;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDrm() {
        return drm;
    }

    public void setDrm(int drm) {
        this.drm = drm;
    }

    public String getIdresource() {
        return idresource;
    }

    public void setIdresource(String idresource) {
        this.idresource = idresource;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Drm drm1 = (Drm) o;

        if (drm != drm1.drm) return false;
        if (timestamp != drm1.timestamp) return false;
        if (id != null ? !id.equals(drm1.id) : drm1.id != null) return false;
        if (idresource != null ? !idresource.equals(drm1.idresource) : drm1.idresource != null) return false;
        if (service != null ? !service.equals(drm1.service) : drm1.service != null) return false;
        return username != null ? username.equals(drm1.username) : drm1.username == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + drm;
        result = 31 * result + (idresource != null ? idresource.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Drm{" +
            "id=" + id +
            ", drm=" + drm +
            ", idresource='" + idresource + '\'' +
            ", timestamp=" + timestamp +
            ", service='" + service + '\'' +
            ", username='" + username + '\'' +
            '}';
    }
}
