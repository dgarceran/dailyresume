package com.davidgarceran.dailyresume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.davidgarceran.dailyresume.domain.*;
import com.davidgarceran.dailyresume.repository.DrmTumblrRepository;
import com.davidgarceran.dailyresume.repository.SocialUserConnectionRepository;
import com.davidgarceran.dailyresume.repository.TumblrRepository;
import com.davidgarceran.dailyresume.security.SecurityUtils;
import com.davidgarceran.dailyresume.web.rest.util.HeaderUtil;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.ListAndCount;
import org.springframework.social.tumblr.api.Post;
import org.springframework.social.tumblr.api.Posts;
import org.springframework.social.tumblr.api.Tumblr;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by David on 28/04/2016.
 */
@RestController
@RequestMapping("/api")
public class TumblrController  {
    @Inject
    SocialUserConnectionRepository socialUserConnectionRepository;

    @Inject
    TumblrRepository tumblrRepository;

    @Inject
    DrmTumblrRepository drmTumblrRepository;


    /*GET DAILYRESUME Repeated*/
    @RequestMapping(value = "/tumblr/drm",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<DrmTumblr>> getTumblrDrm() {
        String login = SecurityUtils.getCurrentUserLogin();
        Date dateNow = new Date();
        Timestamp timestamp = new Timestamp(dateNow.getTime());
        long date = (timestamp.getTime()- 86400000)/1000;
        List<DrmTumblr> dailyResume = drmTumblrRepository.findFirst10ByUserAndTimestampGreaterThanEqualOrderByDrmDesc(login, date);
        return new ResponseEntity<>(dailyResume, HttpStatus.OK);
    }

    /*GET DAILYRESUME ACCOUNT ACTIVITY*/
    @RequestMapping(value = "/tumblr/activity",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<DrmInfoTumblr> getAccountActivity() {
        Date dateNow = new Date();
        Timestamp timestamp = new Timestamp(dateNow.getTime());
        long date = (timestamp.getTime() - 86400000) / 1000;
        String login = SecurityUtils.getCurrentUserLogin();
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
        Date dateToday = new Date();
        long timestampNow = dateToday.getTime();
        DrmInfoTumblr info = new DrmInfoTumblr(wholeDay, hours2, hours4, hours6, hours8, hours10, hours12, hours14, hours16, hours18, hours20, hours22, hours24, timestampNow, dateNow );
        return new ResponseEntity<>(info, HttpStatus.OK);
    }
}
