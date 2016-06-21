package com.davidgarceran.dailyresume.domain;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.List;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 19/4/16
 *
 * Allows the user to connect to Twitter after the first connection
 */

public class TwitterConnection {

    private Twitter twitter;

    public TwitterConnection(){ /* EMPTY CONSTRUCTOR */ }

    /**
     * Refers to connectTwitterUser method and returns twitter connection.
     * @param user needed by connectTwitterUser.
     * @return Twitter twitter object.
     * */
    public Twitter getTwitter(SocialUserConnection user) {
        connectTwitterUser(user);
        return twitter;
    }

    /**
     * Connects with the Twitter app through the connection already created and saved in the database.
     * @param user helps us to get the connection already saved.
     * */
    public void connectTwitterUser(SocialUserConnection user){
        String consumerKey = "xxx";
        String consumerSecret = "xxx";
        String accessToken = user.getAccessToken();
        String accessTokenSecret = user.getSecret();

        twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}
