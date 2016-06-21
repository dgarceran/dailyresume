package com.davidgarceran.dailyresume.domain;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 27/05/2016
 *
 * Saves filtered tweets.
 */
@Entity
@Table(name = "drm_twitter_filtered")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DrmTwitter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tweetid")
    private Long tweetid;

    @Column(name = "rt")
    private int rt;

    @Column(name = "fav")
    private int fav;

    @Column(name = "username")
    private String username;

    @Column(name = "userid")
    private Long userid;

    @Column(name = "creation")
    private long creationdate;

    @Column(name = "text")
    private String text;

    @Column(name = "idrt")
    private String idrt;

    @Column(name = "profileimage")
    private String profileimage;

    @Column(name = "drm")
    private int drm;

    @Column(name = "type")
    private String type;

    DrmTwitter(){}

    public DrmTwitter(Long tweetid, int rt, int fav, String username, Long userid, long creationDate, String text, String idrt, String profileimage, int drm) {
        this.tweetid = tweetid;
        this.rt = rt;
        this.fav = fav;
        this.username = username;
        this.userid = userid;
        this.creationdate = creationDate;
        this.text = text;
        this.idrt = idrt;
        this.profileimage = profileimage;
        this.drm = drm;
    }

    public DrmTwitter(Long tweetid, int rt, int fav, String username, Long userid, long creationDate, String text, String profileimage, int drm) {
        this.tweetid = tweetid;
        this.rt = rt;
        this.fav = fav;
        this.username = username;
        this.userid = userid;
        this.creationdate = creationDate;
        this.text = text;
        this.profileimage = profileimage;
        this.drm = drm;
    }
    public DrmTwitter(Long tweetid, int rt, int fav, String username, Long userid, long creationDate, String text, String idrt, String profileimage, int drm, String type) {
        this.tweetid = tweetid;
        this.rt = rt;
        this.fav = fav;
        this.username = username;
        this.userid = userid;
        this.creationdate = creationDate;
        this.text = text;
        this.idrt = idrt;
        this.profileimage = profileimage;
        this.drm = drm;
        this.type = type;
    }

    public DrmTwitter(Long tweetid, int rt, int fav, String username, Long userid, long creationDate, String text, String profileimage, int drm, String type) {
        this.tweetid = tweetid;
        this.rt = rt;
        this.fav = fav;
        this.username = username;
        this.userid = userid;
        this.creationdate = creationDate;
        this.text = text;
        this.profileimage = profileimage;
        this.drm = drm;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTweetid() {
        return tweetid;
    }

    public void setTweetid(Long tweetid) {
        this.tweetid = tweetid;
    }

    public int getRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public long getCreationDate() {
        return creationdate;
    }

    public void setCreationDate(long creationDate) {
        this.creationdate = creationDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIdrt() {
        return idrt;
    }

    public void setIdrt(String idrt) {
        this.idrt = idrt;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public int getDrm() {
        return drm;
    }

    public void setDrm(int drm) {
        this.drm = drm;
    }

    public long getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(long creationdate) {
        this.creationdate = creationdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrmTwitter that = (DrmTwitter) o;

        if (rt != that.rt) return false;
        if (fav != that.fav) return false;
        if (creationdate != that.creationdate) return false;
        if (drm != that.drm) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tweetid != null ? !tweetid.equals(that.tweetid) : that.tweetid != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (idrt != null ? !idrt.equals(that.idrt) : that.idrt != null) return false;
        if (profileimage != null ? !profileimage.equals(that.profileimage) : that.profileimage != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tweetid != null ? tweetid.hashCode() : 0);
        result = 31 * result + rt;
        result = 31 * result + fav;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (int) (creationdate ^ (creationdate >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (idrt != null ? idrt.hashCode() : 0);
        result = 31 * result + (profileimage != null ? profileimage.hashCode() : 0);
        result = 31 * result + drm;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DrmTwitter{" +
            "id=" + id +
            ", tweetid=" + tweetid +
            ", rt=" + rt +
            ", fav=" + fav +
            ", username='" + username + '\'' +
            ", userid=" + userid +
            ", creationdate=" + creationdate +
            ", text='" + text + '\'' +
            ", idrt='" + idrt + '\'' +
            ", profileimage='" + profileimage + '\'' +
            ", drm=" + drm +
            ", type='" + type + '\'' +
            '}';
    }
}
