package com.davidgarceran.dailyresume.repository;

import com.davidgarceran.dailyresume.domain.TwitterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 03/05/16
 */
public interface TwitterRepository extends JpaRepository<TwitterEntity, Long> {
    List<TwitterEntity> findAllByCreationdateGreaterThanEqualAndUsername(long date, String username);
    List<TwitterEntity> findAllByIdrtAndUsername(String idRt, String username);
    List<TwitterEntity> findAllByUsernameAndCreationdateBetween(String username, long date1, long date2);
}
