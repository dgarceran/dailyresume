package com.davidgarceran.dailyresume.service;

import com.davidgarceran.dailyresume.domain.*;
import com.davidgarceran.dailyresume.repository.*;
import com.davidgarceran.dailyresume.security.SecurityUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.tumblr.api.ChatMessage;
import org.springframework.social.tumblr.api.Post;
import org.springframework.social.tumblr.api.Tumblr;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author  David Garcerán
 * @version 1.0
 * @since 26/05/2016
 * */
@Service
@EnableAsync
public class TumblrService {

    @Inject
    TumblrRepository tumblrRepository;

    @Inject
    DrmTumblrRepository drmTumblrRepository;

    @Inject
    DrmRepository drmRepository;

    @Inject
    DrmTumblrActivityRepository drmTumblrActivityRepository;

    @Inject
    SocialUserConnectionRepository socialUserConnectionRepository;

    @Async
    public void scheduledDashboard(String login){
        System.out.println("Scheduled Dashboard for "+login);
        SocialUserConnection user = socialUserConnectionRepository.findOneByUserIdAndProviderId(login, "tumblr");
        Tumblr tumble = new TumblrConnection().getTumblr(user);
        List<Post> posts = tumble.userOperations().dashboard().getPosts();
        int numberPosts = posts.size();
        for(int i = 0; i<numberPosts; i++) {
            Post handlePost = posts.get(i);
            if(tumblrRepository.findOneByPostid(handlePost.getId())==null){
                String type = handlePost.getType().getType();
                TumblrEntity tumblrEntity;
                switch (type){
                    case "text":
                        String text = handlePost.getBody();
                        tumblrEntity = new TumblrEntity(handlePost.getReblogKey(), handlePost.getId(), login, handlePost.getNoteCount(), handlePost.getBlogName(), handlePost.getTimestamp(), type, handlePost.getPostUrl(), text);
                        tumblrRepository.save(tumblrEntity);
                        break;
                    case "photo":
                        String urlPhoto = handlePost.getPhotos().get(0).getSizes().get(0).getUrl();
                        tumblrEntity = new TumblrEntity(handlePost.getReblogKey(), handlePost.getId(), login, handlePost.getNoteCount(), handlePost.getBlogName(), handlePost.getTimestamp(), type, handlePost.getPostUrl(), urlPhoto);
                        tumblrRepository.save(tumblrEntity);
                        break;
                    case "quote":
                        String textQuote = handlePost.getText();
                        tumblrEntity = new TumblrEntity(handlePost.getReblogKey(), handlePost.getId(), login, handlePost.getNoteCount(), handlePost.getBlogName(), handlePost.getTimestamp(), type, handlePost.getPostUrl(), textQuote);
                        tumblrRepository.save(tumblrEntity);
                        break;
                    case "link":
                        String link = handlePost.getUrl();
                        tumblrEntity = new TumblrEntity(handlePost.getReblogKey(), handlePost.getId(), login, handlePost.getNoteCount(), handlePost.getBlogName(), handlePost.getTimestamp(), type, handlePost.getPostUrl(), link);
                        tumblrRepository.save(tumblrEntity);
                        break;
                    case "chat":
                        String body = handlePost.getBody();
                        tumblrEntity = new TumblrEntity(handlePost.getReblogKey(), handlePost.getId(), login, handlePost.getNoteCount(), handlePost.getBlogName(), handlePost.getTimestamp(), type, handlePost.getPostUrl(), body);
                        tumblrRepository.save(tumblrEntity);
                        break;
                    case "audio":
                        String audioPlayer = handlePost.getAudioPlayer();
                        tumblrEntity = new TumblrEntity(handlePost.getReblogKey(), handlePost.getId(), login, handlePost.getNoteCount(), handlePost.getBlogName(), handlePost.getTimestamp(), type, handlePost.getPostUrl(), audioPlayer);
                        tumblrRepository.save(tumblrEntity);
                        break;
                    case "video":
                        String videoPlayer = handlePost.getVideoPlayers().get(0).getEmbedCode();
                        tumblrEntity = new TumblrEntity(handlePost.getReblogKey(), handlePost.getId(), login, handlePost.getNoteCount(), handlePost.getBlogName(), handlePost.getTimestamp(), type, handlePost.getPostUrl(), videoPlayer);
                        tumblrRepository.save(tumblrEntity);
                        break;
                    case "answer":
                        String question = handlePost.getQuestion();
                        tumblrEntity = new TumblrEntity(handlePost.getReblogKey(), handlePost.getId(), login, handlePost.getNoteCount(), handlePost.getBlogName(), handlePost.getTimestamp(), type, handlePost.getPostUrl(), question);
                        tumblrRepository.save(tumblrEntity);
                        break;
                    default:
                        break;
                }

            }
        }
    }

    @Async
    public void scheduledDRM(String login){
        Date dateNow = new Date();
        Timestamp timestamp = new Timestamp(dateNow.getTime());
        long date = (timestamp.getTime()- 86400000)/1000;
        List <TumblrEntity> posts = tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date);
        List <DrmTumblr> drm = new ArrayList<>();
        List <DrmTumblr> drmDelete = new ArrayList<>();
        List <Drm> drmParse = new ArrayList<>();
        List <Drm> drmDeleteParse = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            TumblrEntity handlePost = posts.get(i);
            for (int j = 0; j < posts.size(); j++) {
                TumblrEntity secondHandlePost = posts.get(j);
                if (i != j && handlePost.getReblogkey() != null && handlePost.getReblogkey().equals(secondHandlePost.getReblogkey())) {
                    boolean isInside = false;
                    for (int k = 0; k < drm.size(); k++) {
                        if (drm.get(k).getReblogkey().equals(handlePost.getReblogkey())) {
                            isInside = true;
                        }
                    }
                    if (isInside == false) {
                        List<DrmTumblr> deletePost = drmTumblrRepository.findAllByReblogkeyAndUser(handlePost.getReblogkey(), handlePost.getUser());
                        List<Drm> deletePostParse = drmRepository.findAllByIdresourceAndUsernameAndService(handlePost.getReblogkey(), handlePost.getUser(), "tumblr");
                        List<TumblrEntity> listReblogs = tumblrRepository.findAllByUserAndReblogkey(login, handlePost.getReblogkey());
                        drm.add(new DrmTumblr(handlePost.getReblogkey(), handlePost.getPostid(), handlePost.getUser(), handlePost.getNotes(), handlePost.getBlogname(), handlePost.getTimestamp(), handlePost.getType(), handlePost.getPosturl(), handlePost.getExtraresource(), listReblogs.size()));
                        drmParse.add(new Drm(listReblogs.size(), handlePost.getReblogkey(), new Date().getTime(), "tumblr", login));
                        if(deletePost.size()>0){
                            for(int k = 0; k < deletePost.size(); k++){
                                drmDelete.add(deletePost.get(k));
                            }
                        }
                        if(deletePostParse.size()>0){
                            for(int k = 0; k < deletePostParse.size(); k++){
                                drmDeleteParse.add(deletePostParse.get(k));
                            }
                        }
                    }

                }
            }
        }
        if(drm.size()>0){
            drmTumblrRepository.save(drm);
        }
        if(drmDelete.size()>0){
            drmTumblrRepository.delete(drmDelete);
        }
        if(drmParse.size()>0){
            drmRepository.save(drmParse);
        }
        if(drmDeleteParse.size()>0){
            drmRepository.delete(drmDeleteParse);
        }
    }

    /*
    * Saves the statistics of the account once a day, at midnight. The data saved is number of
    * posts and best posts of every day, and every two hours during the day.
    * It saves them on DrmTumblrActivityRepository through save() method.
    * */
    @Scheduled(cron="0 0 0 * * *")
    public void activityAccounts(){
        List<SocialUserConnection> connectedUsers = socialUserConnectionRepository.findAllByProviderId("tumblr");
        for (int i = 0; i<connectedUsers.size(); i++){
            String login = connectedUsers.get(i).getUserId();
            Date dateNow = new Date();
            Timestamp timestamp = new Timestamp(dateNow.getTime());
            long date = (timestamp.getTime() - 86400000) / 1000;
            List<TumblrEntity> postsDay = tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date);
            //info of the day
            long timeDay  = 86400;
            long segment = 7200;
            int hours2 = postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment).size();
            int hours4 = (postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment*2).size()) - hours2;
            int hours6 = (postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment*3).size()) - hours2 - hours4;
            int hours8 = (postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment*4).size()) - hours2 - hours4 - hours6;
            int hours10 = (postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment*5).size()) - hours2 - hours4 - hours8 - hours8;
            int hours12 = (postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment*6).size()) - hours2 - hours4 - hours8 - hours8 - hours10;
            int firstHalf = hours2 + hours4+ hours6+ hours8+ hours10+ hours12;
            int hours14 = (postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment*7).size()) - firstHalf;
            int hours16 = (postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment*8).size()) - firstHalf - hours14;
            int hours18 = (postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment*9).size()) - firstHalf - hours14 - hours16;
            int hours20 = (postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment*10).size()) - firstHalf - hours14 - hours16 - hours18;
            int hours22 = (postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment*11).size()) - firstHalf - hours14 - hours16 - hours18 - hours20;
            int hours24 = (postsDay.size() - tumblrRepository.findAllByUserAndTimestampGreaterThanEqual(login, date+segment*12).size()) - firstHalf - hours14 - hours16 - hours18 - hours20 - hours22;

            int wholeDay = postsDay.size();

            String popularDay = "";

            if(drmTumblrRepository.findFirstByUserAndTimestampGreaterThanEqualOrderByDrmDesc(login, date) != null){
                popularDay = drmTumblrRepository.findFirstByUserAndTimestampGreaterThanEqualOrderByDrmDesc(login, date).getReblogkey();
            }

            String popularHours2 = "";
            String popularHours4 = "";
            String popularHours6 = "";
            String popularHours8 = "";
            String popularHours10 = "";
            String popularHours12 = "";
            String popularHours14 = "";
            String popularHours16 = "";
            String popularHours18 = "";
            String popularHours20 = "";
            String popularHours22 = "";
            String popularHours24 = "";

            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date, date+segment) != null){
                popularHours2 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date, date+segment).getReblogkey();
            }
            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment, date+segment*2) != null){
                popularHours4 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment, date+segment*2).getReblogkey();
            }
            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*2, date+segment*3) != null){
                popularHours6 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*2, date+segment*3).getReblogkey();
            }
            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*3, date+segment*4) != null){
                popularHours8 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*3, date+segment*4).getReblogkey();
            }
            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*3, date+segment*4) != null){
                popularHours10 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*4, date+segment*5).getReblogkey();
            }
            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*5, date+segment*6) != null){
                popularHours12 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*5, date+segment*6).getReblogkey();
            }
            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*6, date+segment*7) != null){
                popularHours14 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*6, date+segment*7).getReblogkey();
            }
            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*7, date+segment*8) != null){
                popularHours16 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*7, date+segment*8).getReblogkey();
            }
            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*8, date+segment*9) != null){
                popularHours18 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*8, date+segment*9).getReblogkey();
            }
            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*9, date+segment*10) != null){
                popularHours20 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*9, date+segment*10).getReblogkey();
            }
            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*10, date+segment*11) != null){
                popularHours22 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*10, date+segment*11).getReblogkey();
            }
            if(drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*11, date+segment*12) != null){
                popularHours24 = drmTumblrRepository.findFirstByUserAndTimestampBetweenOrderByDrmDesc(login, date+segment*11, date+segment*12).getReblogkey();
            }
            Date dateToday = new Date();
            long timestampNow = dateToday.getTime();
            DrmInfoTumblr info = new DrmInfoTumblr(login, wholeDay, hours2, hours4, hours6, hours8, hours10, hours12, hours14, hours16, hours18, hours20, hours22, hours24, popularDay, popularHours2, popularHours4, popularHours6, popularHours8, popularHours10, popularHours12, popularHours14, popularHours16, popularHours18, popularHours20, popularHours22, popularHours24, timestampNow, dateToday);
            drmTumblrActivityRepository.save(info);
        }
    }
    @Scheduled(fixedDelay = 120000)
    public void scheduledStart2(){
        List<SocialUserConnection> connectedUsers = socialUserConnectionRepository.findAllByProviderId("tumblr");
        for (int i = 0; i<connectedUsers.size(); i++){
            String login = connectedUsers.get(i).getUserId();
            scheduledDashboard(login);
        }
    }
    @Scheduled(fixedDelay = 300000)
    public void scheduledStart(){
        List<SocialUserConnection> connectedUsers = socialUserConnectionRepository.findAllByProviderId("tumblr");
        for (int i = 0; i<connectedUsers.size(); i++){
            String login = connectedUsers.get(i).getUserId();
            scheduledDRM(login);
        }
    }
}
