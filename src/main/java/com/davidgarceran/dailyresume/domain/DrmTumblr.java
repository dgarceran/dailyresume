package com.davidgarceran.dailyresume.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 27/05/2016
 *
 * Saves filtered posts.
 */
@Entity
@Table(name = "drm_tumblr_filtered")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DrmTumblr implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reblogkey")
    private String reblogkey;

    @Column(name = "postid")
    private Long postid;

    @Column(name = "user")
    private String user;

    @Column(name = "notes")
    private int notes;

    @Column(name = "blogname")
    private String blogname;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "type")
    private String type;

    @Column(name = "posturl")
    private String posturl;

    @Column(name = "extraresource")
    private String extraresource;

    // times this entity has been repeated in DRM
    @Column(name = "drm")
    private int drm;

    public DrmTumblr(){}

    public DrmTumblr(String reblogkey, Long postid, String user, int notes, String blogname, Long timestamp, String type, String posturl, String extraresource, int drm) {
        this.reblogkey = reblogkey;
        this.postid = postid;
        this.user = user;
        this.notes = notes;
        this.blogname = blogname;
        this.timestamp = timestamp;
        this.type = type;
        this.posturl = posturl;
        this.extraresource = extraresource;
        this.drm = drm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReblogkey() {
        return reblogkey;
    }

    public void setReblogkey(String reblogkey) {
        this.reblogkey = reblogkey;
    }

    public Long getPostid() {
        return postid;
    }

    public void setPostid(Long postid) {
        this.postid = postid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getNotes() {
        return notes;
    }

    public void setNotes(int notes) {
        this.notes = notes;
    }

    public String getBlogname() {
        return blogname;
    }

    public void setBlogname(String blogname) {
        this.blogname = blogname;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosturl() {
        return posturl;
    }

    public void setPosturl(String posturl) {
        this.posturl = posturl;
    }

    public String getExtraresource() {
        return extraresource;
    }

    public void setExtraresource(String extraresource) {
        this.extraresource = extraresource;
    }

    public int getDrm() {
        return drm;
    }

    public void setDrm(int drm) {
        this.drm = drm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrmTumblr drmTumblr = (DrmTumblr) o;

        if (notes != drmTumblr.notes) return false;
        if (drm != drmTumblr.drm) return false;
        if (id != null ? !id.equals(drmTumblr.id) : drmTumblr.id != null) return false;
        if (reblogkey != null ? !reblogkey.equals(drmTumblr.reblogkey) : drmTumblr.reblogkey != null) return false;
        if (postid != null ? !postid.equals(drmTumblr.postid) : drmTumblr.postid != null) return false;
        if (user != null ? !user.equals(drmTumblr.user) : drmTumblr.user != null) return false;
        if (blogname != null ? !blogname.equals(drmTumblr.blogname) : drmTumblr.blogname != null) return false;
        if (timestamp != null ? !timestamp.equals(drmTumblr.timestamp) : drmTumblr.timestamp != null) return false;
        if (type != null ? !type.equals(drmTumblr.type) : drmTumblr.type != null) return false;
        if (posturl != null ? !posturl.equals(drmTumblr.posturl) : drmTumblr.posturl != null) return false;
        return extraresource != null ? extraresource.equals(drmTumblr.extraresource) : drmTumblr.extraresource == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (reblogkey != null ? reblogkey.hashCode() : 0);
        result = 31 * result + (postid != null ? postid.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + notes;
        result = 31 * result + (blogname != null ? blogname.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (posturl != null ? posturl.hashCode() : 0);
        result = 31 * result + (extraresource != null ? extraresource.hashCode() : 0);
        result = 31 * result + drm;
        return result;
    }

    @Override
    public String toString() {
        return "DrmTumblr{" +
            "id=" + id +
            ", reblogkey='" + reblogkey + '\'' +
            ", postid=" + postid +
            ", user='" + user + '\'' +
            ", notes=" + notes +
            ", blogname='" + blogname + '\'' +
            ", timestamp=" + timestamp +
            ", type='" + type + '\'' +
            ", posturl='" + posturl + '\'' +
            ", extraresource='" + extraresource + '\'' +
            ", drm=" + drm +
            '}';
    }
}
