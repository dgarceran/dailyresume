package com.davidgarceran.dailyresume.repository;

import com.davidgarceran.dailyresume.domain.DrmInfoTwitter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * @author David Garcerán
 * @version 1.0
 * @since 01/06/2016
 */
public interface DrmTwitterActivityRepository  extends JpaRepository<DrmInfoTwitter, Long> {
    List<DrmInfoTwitter> findFirst7ByUsernameOrderByTimestampDesc(String login);
}
