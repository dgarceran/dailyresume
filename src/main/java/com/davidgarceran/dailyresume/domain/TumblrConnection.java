package com.davidgarceran.dailyresume.domain;

import org.springframework.social.tumblr.api.Tumblr;
import org.springframework.social.tumblr.api.impl.TumblrTemplate;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 28/04/2016
 *
 * Allows the user to connect to Tumblr after the first connection
 */
public class TumblrConnection {
    private Tumblr tumblr;

    public TumblrConnection(){
        //EMPTY CONSTRUCTOR
    }
    /**
     * Refers to method connectTumblrUser and returns tumblr.
     * @param user needed by connectTumblrUser() method.
     * @return Tumblr tumblr object.
     * */
    public Tumblr getTumblr(SocialUserConnection user) {
        connectTumblrUser(user);
        return tumblr;
    }
    /**
     * Connects with the Tumblr app through the connection already created and saved in the database.
     * @param user helps us to get the connection already saved.
     * */
    public void connectTumblrUser(SocialUserConnection user){
        String consumerKey = "xxx";
        String consumerSecret = "xxx";
        String accessToken = user.getAccessToken();
        String accessTokenSecret = user.getSecret();

        tumblr = new TumblrTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}
