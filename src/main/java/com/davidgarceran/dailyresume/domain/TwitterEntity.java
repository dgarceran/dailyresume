package com.davidgarceran.dailyresume.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 3/5/16
 *
 * Saves every tweet on the Stream.
 */

@Entity
@Table(name = "drm_twitter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TwitterEntity implements Serializable {

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

    public TwitterEntity(){}

    public TwitterEntity(Long tweetid, int rt, int fav, String username, Long userid, long creationDate, String text, String idrt, String profileimage) {
        this.tweetid = tweetid;
        this.rt = rt;
        this.fav = fav;
        this.username = username;
        this.userid = userid;
        this.creationdate = creationDate;
        this.text = text;
        this.idrt = idrt;
        this.profileimage = profileimage;
    }

    public TwitterEntity(Long tweetid, int rt, int fav, String username, Long userid, long creationDate, String text, String profileimage) {
        this.tweetid = tweetid;
        this.rt = rt;
        this.fav = fav;
        this.username = username;
        this.userid = userid;
        this.creationdate = creationDate;
        this.text = text;
        this.profileimage = profileimage;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwitterEntity that = (TwitterEntity) o;

        if (rt != that.rt) return false;
        if (fav != that.fav) return false;
        if (creationdate != that.creationdate) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tweetid != null ? !tweetid.equals(that.tweetid) : that.tweetid != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (idrt != null ? !idrt.equals(that.idrt) : that.idrt != null) return false;
        return profileimage != null ? profileimage.equals(that.profileimage) : that.profileimage == null;

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
        return result;
    }

    @Override
    public String toString() {
        return "TwitterEntity{" +
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
            '}';
    }
}
