package com.davidgarceran.dailyresume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.davidgarceran.dailyresume.domain.*;
import com.davidgarceran.dailyresume.repository.*;
import com.davidgarceran.dailyresume.security.SecurityUtils;
import com.davidgarceran.dailyresume.web.rest.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

/**
 * Created by David on 02/06/2016.
 */

@RestController
@RequestMapping("/api")
public class DrmController {

    @Inject
    DrmRepository drmRepository;

    @Inject
    TumblrRepository tumblrRepository;

    @Inject
    SocialUserConnectionRepository socialUserConnectionRepository;

    @Inject
    DrmTumblrActivityRepository drmTumblrActivityRepository;

    @Inject
    DrmTwitterActivityRepository drmTwitterActivityRepository;

    @RequestMapping(value = "/drm",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<Drm>> getDrm(Pageable pageable) throws URISyntaxException {
        String login = SecurityUtils.getCurrentUserLogin();
        long timestamp = new Date().getTime() - 86400000;
        Page<Drm> result = drmRepository.findAllByUsernameAndTimestampGreaterThanEqualOrderByDrmDesc(login, timestamp, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, "/api/drm");
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/drm/tumblr",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<TumblrEntity> getDrmTumblr(String reblogKey) {
        String login = SecurityUtils.getCurrentUserLogin();
        TumblrEntity result = tumblrRepository.findFirstByReblogkeyAndUser(reblogKey, login);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    //ANON CLASS FOR getDrmSocial()
    class IsConnected{
        private boolean isConnected;

        IsConnected(){}

        public IsConnected(boolean isConnected) {
            this.isConnected = isConnected;
        }

        public boolean isConnected() {
            return isConnected;
        }

        public void setConnected(boolean connected) {
            isConnected = connected;
        }
    }

    @RequestMapping(value = "/drm/utils/accountsCheck",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<IsConnected> getDrmSocial(String service) {
        String login = SecurityUtils.getCurrentUserLogin();
        boolean result = false;
        SocialUserConnection connection = socialUserConnectionRepository.findOneByProviderIdAndUserId(service, login);
        if(connection != null){
            result = true;
        }
        return new ResponseEntity<>(new IsConnected(result), HttpStatus.OK);
    }

    @RequestMapping(value = "/drm/analytics/twitter",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<DrmInfoTwitter>> getDrmActivityTwitter() {
        String login = SecurityUtils.getCurrentUserLogin();
        List<DrmInfoTwitter> result = drmTwitterActivityRepository.findFirst7ByUsernameOrderByTimestampDesc(login);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @RequestMapping(value = "/drm/analytics/tumblr",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<DrmInfoTumblr>> getDrmActivityTumblr() {
        String login = SecurityUtils.getCurrentUserLogin();
        List<DrmInfoTumblr> result = drmTumblrActivityRepository.findFirst7ByUsernameOrderByTimestampDesc(login);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
