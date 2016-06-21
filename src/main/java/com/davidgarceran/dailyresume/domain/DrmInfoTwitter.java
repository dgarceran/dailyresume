package com.davidgarceran.dailyresume.domain;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 01/06/2016
 *
 * Saves the account data of the day.
 */
@Entity
@Table(name = "drm_twitter_analytics")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DrmInfoTwitter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name = "day")
    private int day;

    //every two hours
    @Column(name = "hours2")

    private int hours2;
    @Column(name = "hours4")

    private int hours4;
    @Column(name = "hours6")

    private int hours6;
    @Column(name = "hours8")

    private int hours8;
    @Column(name = "hours10")

    private int hours10;
    @Column(name = "hours12")

    private int hours12;
    @Column(name = "hours14")

    private int hours14;
    @Column(name = "hours16")

    private int hours16;
    @Column(name = "hours18")

    private int hours18;
    @Column(name = "hours20")

    private int hours20;
    @Column(name = "hours22")

    private int hours22;
    @Column(name = "hours24")
    private int hours24;

    //Most popular posts - rtid or tweetid
    @Column(name = "popularday")
    private String popularDay;

    @Column(name = "popularhours2")
    private String popularHours2;

    @Column(name = "popularhours4")
    private String popularHours4;

    @Column(name = "popularhours6")
    private String popularHours6;

    @Column(name = "popularhours8")
    private String popularHours8;

    @Column(name = "popularhours10")
    private String popularHours10;

    @Column(name = "popularhours12")
    private String popularHours12;

    @Column(name = "popularhours14")
    private String popularHours14;

    @Column(name = "popularhours16")
    private String popularHours16;

    @Column(name = "popularhours18")
    private String popularHours18;

    @Column(name = "popularhours20")
    private String popularHours20;

    @Column(name = "popularhours22")
    private String popularHours22;

    @Column(name = "popularhours24")
    private String popularHours24;

    @Column(name = "timestamp")
    private long timestamp;

    @Column(name = "date")
    private Date date;

    public DrmInfoTwitter(){}

    public DrmInfoTwitter(String username, int day, int hours2, int hours4, int hours6, int hours8, int hours10, int hours12, int hours14, int hours16, int hours18, int hours20, int hours22, int hours24, long timestamp, Date date) {
        this.username = username;
        this.day = day;
        this.hours2 = hours2;
        this.hours4 = hours4;
        this.hours6 = hours6;
        this.hours8 = hours8;
        this.hours10 = hours10;
        this.hours12 = hours12;
        this.hours14 = hours14;
        this.hours16 = hours16;
        this.hours18 = hours18;
        this.hours20 = hours20;
        this.hours22 = hours22;
        this.hours24 = hours24;
        this.timestamp = timestamp;
        this.date = date;
    }

    public DrmInfoTwitter(String username, int day, int hours2, int hours4, int hours6, int hours8, int hours10, int hours12, int hours14, int hours16, int hours18, int hours20, int hours22, int hours24, String popularDay, String popularHours2, String popularHours4, String popularHours6, String popularHours8, String popularHours10, String popularHours12, String popularHours14, String popularHours16, String popularHours18, String popularHours20, String popularHours22, String popularHours24, long timestamp, Date date) {
        this.username = username;
        this.day = day;
        this.hours2 = hours2;
        this.hours4 = hours4;
        this.hours6 = hours6;
        this.hours8 = hours8;
        this.hours10 = hours10;
        this.hours12 = hours12;
        this.hours14 = hours14;
        this.hours16 = hours16;
        this.hours18 = hours18;
        this.hours20 = hours20;
        this.hours22 = hours22;
        this.hours24 = hours24;
        this.popularDay = popularDay;
        this.popularHours2 = popularHours2;
        this.popularHours4 = popularHours4;
        this.popularHours6 = popularHours6;
        this.popularHours8 = popularHours8;
        this.popularHours10 = popularHours10;
        this.popularHours12 = popularHours12;
        this.popularHours14 = popularHours14;
        this.popularHours16 = popularHours16;
        this.popularHours18 = popularHours18;
        this.popularHours20 = popularHours20;
        this.popularHours22 = popularHours22;
        this.popularHours24 = popularHours24;
        this.timestamp = timestamp;
        this.date = date;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHours2() {
        return hours2;
    }

    public void setHours2(int hours2) {
        this.hours2 = hours2;
    }

    public int getHours4() {
        return hours4;
    }

    public void setHours4(int hours4) {
        this.hours4 = hours4;
    }

    public int getHours6() {
        return hours6;
    }

    public void setHours6(int hours6) {
        this.hours6 = hours6;
    }

    public int getHours8() {
        return hours8;
    }

    public void setHours8(int hours8) {
        this.hours8 = hours8;
    }

    public int getHours10() {
        return hours10;
    }

    public void setHours10(int hours10) {
        this.hours10 = hours10;
    }

    public int getHours12() {
        return hours12;
    }

    public void setHours12(int hours12) {
        this.hours12 = hours12;
    }

    public int getHours14() {
        return hours14;
    }

    public void setHours14(int hours14) {
        this.hours14 = hours14;
    }

    public int getHours16() {
        return hours16;
    }

    public void setHours16(int hours16) {
        this.hours16 = hours16;
    }

    public int getHours18() {
        return hours18;
    }

    public void setHours18(int hours18) {
        this.hours18 = hours18;
    }

    public int getHours20() {
        return hours20;
    }

    public void setHours20(int hours20) {
        this.hours20 = hours20;
    }

    public int getHours22() {
        return hours22;
    }

    public void setHours22(int hours22) {
        this.hours22 = hours22;
    }

    public int getHours24() {
        return hours24;
    }

    public void setHours24(int hours24) {
        this.hours24 = hours24;
    }

    public String getPopularDay() {
        return popularDay;
    }

    public void setPopularDay(String popularDay) {
        this.popularDay = popularDay;
    }

    public String getPopularHours2() {
        return popularHours2;
    }

    public void setPopularHours2(String popularHours2) {
        this.popularHours2 = popularHours2;
    }

    public String getPopularHours4() {
        return popularHours4;
    }

    public void setPopularHours4(String popularHours4) {
        this.popularHours4 = popularHours4;
    }

    public String getPopularHours6() {
        return popularHours6;
    }

    public void setPopularHours6(String popularHours6) {
        this.popularHours6 = popularHours6;
    }

    public String getPopularHours8() {
        return popularHours8;
    }

    public void setPopularHours8(String popularHours8) {
        this.popularHours8 = popularHours8;
    }

    public String getPopularHours10() {
        return popularHours10;
    }

    public void setPopularHours10(String popularHours10) {
        this.popularHours10 = popularHours10;
    }

    public String getPopularHours12() {
        return popularHours12;
    }

    public void setPopularHours12(String popularHours12) {
        this.popularHours12 = popularHours12;
    }

    public String getPopularHours14() {
        return popularHours14;
    }

    public void setPopularHours14(String popularHours14) {
        this.popularHours14 = popularHours14;
    }

    public String getPopularHours16() {
        return popularHours16;
    }

    public void setPopularHours16(String popularHours16) {
        this.popularHours16 = popularHours16;
    }

    public String getPopularHours18() {
        return popularHours18;
    }

    public void setPopularHours18(String popularHours18) {
        this.popularHours18 = popularHours18;
    }

    public String getPopularHours20() {
        return popularHours20;
    }

    public void setPopularHours20(String popularHours20) {
        this.popularHours20 = popularHours20;
    }

    public String getPopularHours22() {
        return popularHours22;
    }

    public void setPopularHours22(String popularHours22) {
        this.popularHours22 = popularHours22;
    }

    public String getPopularHours24() {
        return popularHours24;
    }

    public void setPopularHours24(String popularHours24) {
        this.popularHours24 = popularHours24;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrmInfoTwitter that = (DrmInfoTwitter) o;

        if (day != that.day) return false;
        if (hours2 != that.hours2) return false;
        if (hours4 != that.hours4) return false;
        if (hours6 != that.hours6) return false;
        if (hours8 != that.hours8) return false;
        if (hours10 != that.hours10) return false;
        if (hours12 != that.hours12) return false;
        if (hours14 != that.hours14) return false;
        if (hours16 != that.hours16) return false;
        if (hours18 != that.hours18) return false;
        if (hours20 != that.hours20) return false;
        if (hours22 != that.hours22) return false;
        if (hours24 != that.hours24) return false;
        if (timestamp != that.timestamp) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (popularDay != null ? !popularDay.equals(that.popularDay) : that.popularDay != null) return false;
        if (popularHours2 != null ? !popularHours2.equals(that.popularHours2) : that.popularHours2 != null)
            return false;
        if (popularHours4 != null ? !popularHours4.equals(that.popularHours4) : that.popularHours4 != null)
            return false;
        if (popularHours6 != null ? !popularHours6.equals(that.popularHours6) : that.popularHours6 != null)
            return false;
        if (popularHours8 != null ? !popularHours8.equals(that.popularHours8) : that.popularHours8 != null)
            return false;
        if (popularHours10 != null ? !popularHours10.equals(that.popularHours10) : that.popularHours10 != null)
            return false;
        if (popularHours12 != null ? !popularHours12.equals(that.popularHours12) : that.popularHours12 != null)
            return false;
        if (popularHours14 != null ? !popularHours14.equals(that.popularHours14) : that.popularHours14 != null)
            return false;
        if (popularHours16 != null ? !popularHours16.equals(that.popularHours16) : that.popularHours16 != null)
            return false;
        if (popularHours18 != null ? !popularHours18.equals(that.popularHours18) : that.popularHours18 != null)
            return false;
        if (popularHours20 != null ? !popularHours20.equals(that.popularHours20) : that.popularHours20 != null)
            return false;
        if (popularHours22 != null ? !popularHours22.equals(that.popularHours22) : that.popularHours22 != null)
            return false;
        if (popularHours24 != null ? !popularHours24.equals(that.popularHours24) : that.popularHours24 != null)
            return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + day;
        result = 31 * result + hours2;
        result = 31 * result + hours4;
        result = 31 * result + hours6;
        result = 31 * result + hours8;
        result = 31 * result + hours10;
        result = 31 * result + hours12;
        result = 31 * result + hours14;
        result = 31 * result + hours16;
        result = 31 * result + hours18;
        result = 31 * result + hours20;
        result = 31 * result + hours22;
        result = 31 * result + hours24;
        result = 31 * result + (popularDay != null ? popularDay.hashCode() : 0);
        result = 31 * result + (popularHours2 != null ? popularHours2.hashCode() : 0);
        result = 31 * result + (popularHours4 != null ? popularHours4.hashCode() : 0);
        result = 31 * result + (popularHours6 != null ? popularHours6.hashCode() : 0);
        result = 31 * result + (popularHours8 != null ? popularHours8.hashCode() : 0);
        result = 31 * result + (popularHours10 != null ? popularHours10.hashCode() : 0);
        result = 31 * result + (popularHours12 != null ? popularHours12.hashCode() : 0);
        result = 31 * result + (popularHours14 != null ? popularHours14.hashCode() : 0);
        result = 31 * result + (popularHours16 != null ? popularHours16.hashCode() : 0);
        result = 31 * result + (popularHours18 != null ? popularHours18.hashCode() : 0);
        result = 31 * result + (popularHours20 != null ? popularHours20.hashCode() : 0);
        result = 31 * result + (popularHours22 != null ? popularHours22.hashCode() : 0);
        result = 31 * result + (popularHours24 != null ? popularHours24.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DrmInfoTwitter{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", day=" + day +
            ", hours2=" + hours2 +
            ", hours4=" + hours4 +
            ", hours6=" + hours6 +
            ", hours8=" + hours8 +
            ", hours10=" + hours10 +
            ", hours12=" + hours12 +
            ", hours14=" + hours14 +
            ", hours16=" + hours16 +
            ", hours18=" + hours18 +
            ", hours20=" + hours20 +
            ", hours22=" + hours22 +
            ", hours24=" + hours24 +
            ", popularDay='" + popularDay + '\'' +
            ", popularHours2='" + popularHours2 + '\'' +
            ", popularHours4='" + popularHours4 + '\'' +
            ", popularHours6='" + popularHours6 + '\'' +
            ", popularHours8='" + popularHours8 + '\'' +
            ", popularHours10='" + popularHours10 + '\'' +
            ", popularHours12='" + popularHours12 + '\'' +
            ", popularHours14='" + popularHours14 + '\'' +
            ", popularHours16='" + popularHours16 + '\'' +
            ", popularHours18='" + popularHours18 + '\'' +
            ", popularHours20='" + popularHours20 + '\'' +
            ", popularHours22='" + popularHours22 + '\'' +
            ", popularHours24='" + popularHours24 + '\'' +
            ", timestamp=" + timestamp +
            ", date=" + date +
            '}';
    }
}
