package com.davidgarceran.dailyresume.service;

import com.davidgarceran.dailyresume.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/*
* Modified original service to not create a new user once a registered one tries to connect
* a new social network to its account. Now it just creates a new social connection related
* to the user already registered.
*
* @author  David Garcerán, original file by JHipster
* @version 1.1
* @since 13/06/2016
* */

@Service
public class SocialService {
    private final Logger log = LoggerFactory.getLogger(SocialService.class);

    @Inject
    private UsersConnectionRepository usersConnectionRepository;

    public void createSocialUser(Connection<?> connection, String langKey) {
        String login = SecurityUtils.getCurrentUserLogin();
        if (connection == null) {
            log.error("Cannot create social user because connection is null");
            throw new IllegalArgumentException("Connection cannot be null");
        }
        createSocialConnection(login, connection);
    }

    private void createSocialConnection(String login, Connection<?> connection) {
        ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(login);
        connectionRepository.addConnection(connection);
    }
}
