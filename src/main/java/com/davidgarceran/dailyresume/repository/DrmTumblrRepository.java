package com.davidgarceran.dailyresume.repository;

import com.davidgarceran.dailyresume.domain.DrmTumblr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 27/05/2016
 */
public interface DrmTumblrRepository  extends JpaRepository<DrmTumblr, Long> {
    List<DrmTumblr> findFirst10ByUserAndTimestampGreaterThanEqualOrderByDrmDesc(String user, long timestamp);
    List<DrmTumblr> findAllByReblogkeyAndUser(String reblogKey, String user);

    DrmTumblr findFirstByUserAndTimestampGreaterThanEqualOrderByDrmDesc(String user, long timestamp);
    DrmTumblr findFirstByUserAndTimestampBetweenOrderByDrmDesc(String user, long timestamp1, long longtimestamp2);
}
