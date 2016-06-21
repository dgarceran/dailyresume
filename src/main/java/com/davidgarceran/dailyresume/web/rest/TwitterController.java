package com.davidgarceran.dailyresume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.davidgarceran.dailyresume.config.JHipsterProperties;
import com.davidgarceran.dailyresume.domain.*;
import com.davidgarceran.dailyresume.repository.DrmTwitterRepository;
import com.davidgarceran.dailyresume.repository.SocialUserConnectionRepository;
import com.davidgarceran.dailyresume.repository.TwitterRepository;
import com.davidgarceran.dailyresume.security.SecurityUtils;
import com.davidgarceran.dailyresume.web.rest.util.HeaderUtil;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by davidgarceran.garceran on 3/3/16.
 */
@RestController
@RequestMapping("/api")
public class TwitterController {

    @Inject
    SocialUserConnectionRepository socialUserConnectionRepository;

    @Inject
    DrmTwitterRepository drmTwitterRepository;

    @Inject
    TwitterRepository twitterRepository;

    /* GET HOME TIMELINE */
    @RequestMapping(value = "/twitter/timeline",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<DrmTwitter>> getTwitterTimeline() {
        String login = SecurityUtils.getCurrentUserLogin();
        SocialUserConnection user = socialUserConnectionRepository.findOneByUserIdAndProviderId(login, "twitter");
        Date dateNow = new Date();
        Timestamp timestamp = new Timestamp(dateNow.getTime());
        long date = timestamp.getTime()- 86400000;
        List<DrmTwitter> tweetsHandle = drmTwitterRepository.findFirst10ByUsernameAndCreationdateGreaterThanEqualOrderByDrmDesc(login, date);
        return new ResponseEntity<>(tweetsHandle, HttpStatus.OK);
    }

    /*GET DAILYRESUME ACCOUNT ACTIVITY*/
    @RequestMapping(value = "/twitter/activity",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<DrmInfoTwitter> getAccountActivity() {
        String login = SecurityUtils.getCurrentUserLogin();
        List<TwitterEntity> postsDay = twitterRepository.findAllByCreationdateGreaterThanEqualAndUsername(getTimestamp(24), login);
        //info of the day
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

        int wholeDay = postsDay.size();
        Date date = new Date();
        long timestamp = date.getTime();
        DrmInfoTwitter info = new DrmInfoTwitter(login, wholeDay, hours2, hours4, hours6, hours8, hours10, hours12, hours14, hours16, hours18, hours20, hours22, hours24, timestamp, date);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    private long getTimestamp(int hours){
        long date = new Date().getTime() - 3600000*hours;
        return date;
    }
}
