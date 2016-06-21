package com.davidgarceran.dailyresume.repository;

import com.davidgarceran.dailyresume.domain.DrmInfoTumblr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * @author David Garcerán
 * @version 1.0
 * @since 01/06/2016
 */
public interface DrmTumblrActivityRepository extends JpaRepository<DrmInfoTumblr, Long> {
    List<DrmInfoTumblr> findFirst7ByUsernameOrderByTimestampDesc(String username);
}
