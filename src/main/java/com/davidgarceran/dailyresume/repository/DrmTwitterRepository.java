package com.davidgarceran.dailyresume.repository;

import com.davidgarceran.dailyresume.domain.DrmTwitter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 27/05/2016
 */
public interface DrmTwitterRepository extends JpaRepository<DrmTwitter, Long> {
    List<DrmTwitter> findFirst10ByUsernameAndCreationdateGreaterThanEqualOrderByDrmDesc(String user, long timestamp);
    List<DrmTwitter> findAllByTweetidAndUsername(long tweetId, String username);
    List<DrmTwitter> findAllByIdrtAndUsernameAndType(String idRt, String username, String type);

    DrmTwitter findFirstByUsernameAndCreationdateBetweenOrderByDrmDesc(String username, long date1, long date2);
}
