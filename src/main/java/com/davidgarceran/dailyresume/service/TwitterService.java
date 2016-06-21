package com.davidgarceran.dailyresume.service;

import com.davidgarceran.dailyresume.domain.*;
import com.davidgarceran.dailyresume.repository.*;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * @author  David Garcerán
 * @version 1.0
 * @since 12/05/2016
 * */
@Service
@EnableAsync
public class TwitterService {

    @Inject
    TwitterRepository twitterRepository;

    @Inject
    DrmTwitterRepository drmTwitterRepository;

    @Inject
    DrmRepository drmRepository;

    @Inject
    DrmTwitterActivityRepository drmTwitterActivityRepository;

    @Inject
    SocialUserConnectionRepository socialUserConnectionRepository;

    private List<SocialUserConnection> connectedUsersTw;

    @Scheduled(fixedDelay = 30000)
    public  void users(){
        List<SocialUserConnection> connectedUsers = socialUserConnectionRepository.findAllByProviderId("twitter");
        connectedUsersTw = connectedUsers;
    }

    @Scheduled(fixedDelay = 30000000)
    public void apiStream(){
        Stream stream = new Stream() {
            @Async
            @Override
            public void open() {
                System.out.println("Twitter connected to DailyResume:");
                List<SocialUserConnection> connectedUsers = connectedUsersTw;
                for(int i = 0; i<connectedUsers.size(); i++){
                    String login = connectedUsers.get(i).getUserId();
                    SocialUserConnection user = socialUserConnectionRepository.findOneByUserIdAndProviderId(login, "twitter");
                    Twitter tw =  new TwitterConnection().getTwitter(user);
                    System.out.println("Stream is working for "+login);
                    List<StreamListener> listeners = new ArrayList<StreamListener>();
                    StreamListener streamListener = new StreamListener() {
                        @Async
                        @Override
                        public void onTweet(Tweet tweet) {
                            System.out.println("I found a new tweet for " + login + " made by " + tweet.getFromUser());
                            if(tweet.getRetweetedStatus() != null){
                                twitterRepository.save(new TwitterEntity(tweet.getId(), tweet.getRetweetCount(), tweet.getFavoriteCount(), login, tweet.getFromUserId(), new Date().getTime(), tweet.getText(), tweet.getRetweetedStatus().getIdStr(), tweet.getProfileImageUrl()));

                            }else{
                                twitterRepository.save(new TwitterEntity(tweet.getId(), tweet.getRetweetCount(), tweet.getFavoriteCount(), login, tweet.getFromUserId(), new Date().getTime(), tweet.getText(), tweet.getProfileImageUrl()));
                            }
                        }

                        @Override
                        public void onDelete(StreamDeleteEvent deleteEvent) {

                        }

                        @Override
                        public void onLimit(int numberOfLimitedTweets) {
                            System.out.println("The Twitter stream now is limited to "+numberOfLimitedTweets);
                        }

                        @Override
                        public void onWarning(StreamWarningEvent warningEvent) {

                        }
                    };
                    listeners.add(streamListener);
                    UserStreamParameters userStreamParameters = new UserStreamParameters();
                    userStreamParameters.includeReplies(true).with(UserStreamParameters.WithOptions.FOLLOWINGS);
                    tw.streamingOperations().user(userStreamParameters, listeners);
                }
            }

            @Override
            public void close() {
                System.out.println("Connection closed");
            }
        };
        stream.open();
    }

    @Async
    public void scheduledDailyResume(String login){
        String type = "";

        DateTime dateTime = new DateTime().minusHours(24);
        long date = dateTime.toDate().getTime();

        List<TwitterEntity> tweetsDay = twitterRepository.findAllByCreationdateGreaterThanEqualAndUsername(date, login);
        List<DrmTwitter> drm = new ArrayList<>();
        List<DrmTwitter> drmFinal = new ArrayList<>();
        List<DrmTwitter> drmDelete = new ArrayList<>();

        List<Drm> drmParse = new ArrayList<>();
        List<Drm> drmParseDelete = new ArrayList<>();

        SocialUserConnection user = socialUserConnectionRepository.findOneByUserIdAndProviderId(login, "twitter");
        Twitter tw =  new TwitterConnection().getTwitter(user);
        CursoredList<TwitterProfile> friends = tw.friendOperations().getFriends();
        for (int i= 0; i<tweetsDay.size(); i++){
            TwitterEntity handleTweet = tweetsDay.get(i);
            /*
            * DRM BY POPULAR TWEET FROM FOLLOW LIST
            */
            if(handleTweet.getRt() > 0 || handleTweet.getFav() > 0){
                List<DrmTwitter> popularFriends = new ArrayList<>();
                while(friends.hasNext()){
                    if(friends.contains(handleTweet.getUserid())){
                        type = "popular";
                        for(int j = 0; j<friends.size(); j++){
                            TwitterProfile friend = friends.get(j);
                            long id = friend.getId();
                            if(id == handleTweet.getUserid()){
                                int drmCounter = 0;
                                List<DrmTwitter> deleteTweetid = drmTwitterRepository.findAllByTweetidAndUsername(handleTweet.getTweetid(), handleTweet.getUsername());
                                List<Drm> deleteTweetIdParse = drmRepository.findAllByIdresourceAndUsernameAndService(handleTweet.getTweetid().toString(), handleTweet.getUsername(), "twitter" );
                                if(handleTweet.getRt()>0){
                                    drmCounter =+ handleTweet.getRt();
                                }
                                if(handleTweet.getFav()>0){
                                    drmCounter =+ (handleTweet.getFav())/2;
                                }
                                if (handleTweet.getIdrt() != null){
                                    List<Drm> deleteTweetRtParse = drmRepository.findAllByIdresourceAndUsernameAndService(handleTweet.getIdrt(), handleTweet.getUsername(), "twitter" );
                                    List <DrmTwitter> deleteTweetRt = drmTwitterRepository.findAllByIdrtAndUsernameAndType(handleTweet.getIdrt(), handleTweet.getUsername(), type);
                                    drmParse.add(new Drm(drmCounter, handleTweet.getIdrt(), new Date().getTime(), "twitter", login));
                                    drmFinal.add(new DrmTwitter(handleTweet.getTweetid(),handleTweet.getRt(),handleTweet.getFav(),handleTweet.getUsername(),handleTweet.getUserid(),handleTweet.getCreationDate(),handleTweet.getText(),handleTweet.getIdrt(),handleTweet.getProfileimage(),drmCounter, type));
                                    if(deleteTweetRt.size()>0){
                                        for(int k = 0; k < deleteTweetRt.size(); k++){
                                            drmDelete.add(deleteTweetRt.get(k));
                                        }
                                    }
                                    if(deleteTweetRtParse.size()>0){
                                        for(int k = 0; k < deleteTweetRtParse.size(); k++){
                                            drmParseDelete.add(deleteTweetRtParse.get(k));
                                        }
                                    }
                                }else{
                                    drmParse.add(new Drm(drmCounter, handleTweet.getTweetid().toString(), new Date().getTime(), "twitter", login));
                                    drmFinal.add(new DrmTwitter(handleTweet.getTweetid(),handleTweet.getRt(),handleTweet.getFav(),handleTweet.getUsername(),handleTweet.getUserid(),handleTweet.getCreationDate(),handleTweet.getText(),handleTweet.getProfileimage(),drmCounter, type));
                                }
                                if(deleteTweetid.size() > 0){
                                    for(int k = 0; k < deleteTweetid.size(); k++){
                                        drmDelete.add(deleteTweetid.get(k));
                                    }
                                }
                                if(deleteTweetIdParse.size()>0){
                                    for(int k = 0; k < deleteTweetIdParse.size(); k++){
                                        drmParseDelete.add(deleteTweetIdParse.get(k));
                                    }
                                }
                            }
                        }
                    }
                    friends.getNextCursor();
                }
            }
            for(int j = 0; j<tweetsDay.size(); j++){
                TwitterEntity secondHandleTweet = tweetsDay.get(j);
                if (i != j){
                    /*
                    * DRM BY TWEET SHARED
                    */

                    //If both have the same idrt
                    boolean check = false;
                    if (handleTweet.getIdrt() != null && secondHandleTweet.getIdrt() != null && secondHandleTweet.getIdrt().equals(handleTweet.getIdrt())) {
                        check = checkData(handleTweet, drm, login);
                        if (check == false) {
                            type = "shared";
                            drm.add(new DrmTwitter(handleTweet.getTweetid(), handleTweet.getRt(), handleTweet.getFav(), handleTweet.getUsername(), handleTweet.getUserid(), handleTweet.getCreationDate(), handleTweet.getText(), handleTweet.getIdrt(), handleTweet.getProfileimage(), 0));
                        }
                    }
                    //if an idrt equals an id
                    if (secondHandleTweet.getIdrt() != null && secondHandleTweet.getIdrt().equals(Long.toString(handleTweet.getTweetid()))){
                        check = checkData(handleTweet, drm, login);
                        if (check == false) {
                            type = "shared";
                            drm.add(new DrmTwitter(handleTweet.getTweetid(), handleTweet.getRt(), handleTweet.getFav(), handleTweet.getUsername(), handleTweet.getUserid(), handleTweet.getCreationDate(), handleTweet.getText(), handleTweet.getProfileimage(), 0));
                        }
                    }
                }
            }
        }
        for (int i = 0; i<drm.size(); i++){
            DrmTwitter handleTweet = drm.get(i);
            List<Drm> deleteTweetIdParse = drmRepository.findAllByIdresourceAndUsernameAndService(handleTweet.getTweetid().toString(), handleTweet.getUsername(), "twitter" );
            List<DrmTwitter> deleteTweetid = drmTwitterRepository.findAllByTweetidAndUsername(handleTweet.getTweetid(), handleTweet.getUsername());
            if (handleTweet.getIdrt() != null){
                List<Drm> deleteTweetRtParse = drmRepository.findAllByIdresourceAndUsernameAndService(handleTweet.getIdrt(), handleTweet.getUsername(), "twitter" );
                List <DrmTwitter> deleteTweetRt = drmTwitterRepository.findAllByIdrtAndUsernameAndType(handleTweet.getIdrt(), handleTweet.getUsername(), type);
                List<TwitterEntity> listTweets = twitterRepository.findAllByIdrtAndUsername(handleTweet.getIdrt(), login);
                drmParse.add(new Drm(listTweets.size(), handleTweet.getIdrt(), new Date().getTime(), "twitter", login));
                drmFinal.add(new DrmTwitter(handleTweet.getTweetid(),handleTweet.getRt(),handleTweet.getFav(),handleTweet.getUsername(),handleTweet.getUserid(),handleTweet.getCreationDate(),handleTweet.getText(),handleTweet.getIdrt(),handleTweet.getProfileimage(),listTweets.size(), type));
                if (deleteTweetRt != null){
                    for(int k = 0; k < deleteTweetRt.size(); k++){
                        drmDelete.add(deleteTweetRt.get(k));
                    }
                }
                if(deleteTweetRtParse.size()>0){
                    for(int k = 0; k < deleteTweetRtParse.size(); k++){
                        drmParseDelete.add(deleteTweetRtParse.get(k));
                    }
                }
            }
            else{
                String id = Long.toString(handleTweet.getTweetid());
                List<TwitterEntity> listTweets = twitterRepository.findAllByIdrtAndUsername(id, login);
                drmParse.add(new Drm(listTweets.size(), handleTweet.getTweetid().toString(), new Date().getTime(), "twitter", login));
                drmFinal.add(new DrmTwitter(handleTweet.getTweetid(),handleTweet.getRt(),handleTweet.getFav(),handleTweet.getUsername(),handleTweet.getUserid(),handleTweet.getCreationDate(),handleTweet.getText(),handleTweet.getIdrt(),handleTweet.getProfileimage(),listTweets.size(), type));
            }
            if(deleteTweetid.size() > 0){
                for(int k = 0; k < deleteTweetid.size(); k++){
                    drmDelete.add(deleteTweetid.get(k));
                }
            }
            if(deleteTweetIdParse.size()>0){
                for(int k = 0; k < deleteTweetIdParse.size(); k++){
                    drmParseDelete.add(deleteTweetIdParse.get(k));
                }
            }
        }
        drmTwitterRepository.save(drmFinal);
        drmTwitterRepository.delete(drmDelete);
        drmRepository.save(drmParse);
        drmRepository.delete(drmParseDelete);
    }

    public boolean checkData(TwitterEntity handleTweet, List<DrmTwitter> drm, String login){
        boolean isInside = false;
        for (int i = 0; i < drm.size(); i++) {
            if(drm.size()!=0){
                DrmTwitter drmHandle = drm.get(i);
                String comparableIdDrm = Long.toString(drmHandle.getTweetid());
                String comparableId = Long.toString(handleTweet.getTweetid());
                if(drmHandle.getIdrt() != null && handleTweet.getIdrt()==null){
                    String idRt = drmHandle.getIdrt();
                    if(comparableId.equals(idRt)){
                        isInside = true;
                    }
                }
                if(handleTweet.getIdrt() != null && drmHandle.getIdrt() == null){
                    String idRt = handleTweet.getIdrt();
                    if(comparableIdDrm.equals(idRt)){
                        isInside = true;
                    }
                }
                if(handleTweet.getIdrt() != null && drmHandle.getIdrt() != null){
                    String idRt = handleTweet.getIdrt();
                    String idRtDrm = drmHandle.getIdrt();
                    if(idRtDrm.equals(idRt)){
                        isInside = true;
                    }
                }
                if(handleTweet.getTweetid().equals(drmHandle.getTweetid())){
                    isInside = true;
                }
            }
        }
        return isInside;
    }

    @Scheduled(cron="0 0 0 * * *")
    public void activityAccounts(){
        List<SocialUserConnection> connectedUsers = socialUserConnectionRepository.findAllByProviderId("twitter");
        for (int i = 0; i<connectedUsers.size(); i++) {
            String login = connectedUsers.get(i).getUserId();
            List<TwitterEntity> postsDay = twitterRepository.findAllByCreationdateGreaterThanEqualAndUsername(getTimestamp(24), login);

            int hours2 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(24), getTimestamp(22)).size();
            int hours4 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(22), getTimestamp(20)).size();
            int hours6 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(20), getTimestamp(18)).size();
            int hours8 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(18), getTimestamp(16)).size();
            int hours10 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(16), getTimestamp(14)).size();
            int hours12 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(14), getTimestamp(12)).size();
            int hours14 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(12), getTimestamp(10)).size();
            int hours16 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(10), getTimestamp(8)).size();
            int hours18 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(8), getTimestamp(6)).size();
            int hours20 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(6), getTimestamp(4)).size();
            int hours22 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(4), getTimestamp(2)).size();
            int hours24 = twitterRepository.findAllByUsernameAndCreationdateBetween(login, getTimestamp(2), getTimestamp(0)).size();

            String popularDay = "";
            String popular2 = "";
            String popular4 = "";
            String popular6 = "";
            String popular8 = "";
            String popular10 = "";
            String popular12 = "";
            String popular14 = "";
            String popular16 = "";
            String popular18 = "";
            String popular20 = "";
            String popular22 = "";
            String popular24 = "";


            if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(24), getTimestamp(22)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(24), getTimestamp(22)).getIdrt() != null) {
                    popular2 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(24), getTimestamp(22)).getIdrt();
                } else {
                    popular2 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(24), getTimestamp(22)).getTweetid().toString();
                }
            }

            if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(22), getTimestamp(20)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(22), getTimestamp(20)).getIdrt() != null) {
                    popular4 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(22), getTimestamp(20)).getIdrt();
                } else {
                    popular4 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(22), getTimestamp(20)).getTweetid().toString();
                }
            }
            if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(20), getTimestamp(18)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(20), getTimestamp(18)).getIdrt() != null) {
                    popular6 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(20), getTimestamp(18)).getIdrt();
                } else {
                    popular6 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(20), getTimestamp(18)).getTweetid().toString();
                }
            }
            if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(18), getTimestamp(16)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(18), getTimestamp(16)).getIdrt() != null) {
                    popular8 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(18), getTimestamp(16)).getIdrt();
                } else {
                    popular8 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(18), getTimestamp(16)).getTweetid().toString();
                }
            }
            if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(16), getTimestamp(14)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(16), getTimestamp(14)).getIdrt() != null) {
                    popular10 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(16), getTimestamp(14)).getIdrt();
                } else {
                    popular10 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(16), getTimestamp(14)).getTweetid().toString();
                }
            }
            if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(14), getTimestamp(12)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(14), getTimestamp(12)).getIdrt() != null) {
                    popular12 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(14), getTimestamp(12)).getIdrt();
                } else {
                    popular12 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(14), getTimestamp(12)).getTweetid().toString();
                }
            }
            if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(12), getTimestamp(10)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(12), getTimestamp(10)).getIdrt() != null) {
                    popular14 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(12), getTimestamp(10)).getIdrt();
                } else {
                    popular14 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(12), getTimestamp(10)).getTweetid().toString();
                }
            }
            if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(10), getTimestamp(8)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(10), getTimestamp(8)).getIdrt() != null) {
                    popular16 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(10), getTimestamp(8)).getIdrt();
                } else {
                    popular16 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(10), getTimestamp(8)).getTweetid().toString();
                }
            }
            if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(8), getTimestamp(6)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(8), getTimestamp(6)).getIdrt() != null) {
                    popular18 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(8), getTimestamp(6)).getIdrt();
                } else {
                    popular18 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(8), getTimestamp(6)).getTweetid().toString();
                }
            }
            if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(6), getTimestamp(4)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(6), getTimestamp(4)).getIdrt() != null) {
                    popular20 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(6), getTimestamp(4)).getIdrt();
                } else {
                    popular20 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(6), getTimestamp(4)).getTweetid().toString();
                }
            }
            if(drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(4), getTimestamp(2)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(4), getTimestamp(2)).getIdrt() != null) {
                    popular22 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(4), getTimestamp(2)).getIdrt();
                } else {
                    popular22 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(4), getTimestamp(2)).getTweetid().toString();
                }
            }
            if(drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(2), getTimestamp(0)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(2), getTimestamp(0)).getIdrt() != null) {
                    popular24 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(2), getTimestamp(0)).getIdrt();
                } else {
                    popular24 = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(2), getTimestamp(0)).getTweetid().toString();
                }
            }
            if(drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(24), getTimestamp(0)) != null) {
                if (drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(24), getTimestamp(0)).getIdrt() != null) {
                    popularDay = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(24), getTimestamp(0)).getIdrt();
                } else {
                    popularDay = drmTwitterRepository.findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(login, getTimestamp(24), getTimestamp(0)).getTweetid().toString();
                }
            }
            int wholeDay = postsDay.size();
            Date date = new Date();
            long timestamp = date.getTime();
            drmTwitterActivityRepository.save(new DrmInfoTwitter(login, wholeDay, hours2, hours4, hours6, hours8, hours10, hours12, hours14, hours16, hours18, hours20, hours22, hours24, popularDay, popular2, popular4, popular6, popular8, popular10, popular12, popular14, popular16, popular18, popular20, popular22, popular24, timestamp, date));
        }
    }

    @Scheduled(fixedDelay = 300000)
    public void scheduledStart(){
        List<SocialUserConnection> connectedUsers = socialUserConnectionRepository.findAllByProviderId("twitter");
        for (int i = 0; i<connectedUsers.size(); i++){
            String login = connectedUsers.get(i).getUserId();
            scheduledDailyResume(login);
        }
    }

    private long getTimestamp(int hours){
        long date = new Date().getTime() - 3600000*hours;
        return date;
    }
}
