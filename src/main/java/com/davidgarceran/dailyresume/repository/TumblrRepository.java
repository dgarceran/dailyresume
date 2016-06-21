package com.davidgarceran.dailyresume.repository;


import com.davidgarceran.dailyresume.domain.TumblrEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 26/05/2016
 */
public interface TumblrRepository  extends JpaRepository<TumblrEntity, Long> {
    TumblrEntity findOneByPostid(long id);

    //Dailyresume by times RTd
    List<TumblrEntity> findAllByUserAndTimestampGreaterThanEqual(String user, long timestamp);
    List<TumblrEntity> findAllByUserAndReblogkey(String user, String reblogKey);
    TumblrEntity findFirstByReblogkeyAndUser(String reblogKey, String username);
}
