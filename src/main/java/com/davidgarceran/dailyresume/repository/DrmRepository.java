package com.davidgarceran.dailyresume.repository;

import com.davidgarceran.dailyresume.domain.Drm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author David Garcerán
 * @version 1.0
 * @since 02/06/2016
 */
public interface DrmRepository extends JpaRepository<Drm, Long> {
    List<Drm> findAllByIdresourceAndUsernameAndService(String id, String username, String service);
    Page<Drm> findAllByUsernameAndTimestampGreaterThanEqualOrderByDrmDesc(String username, long timestamp, Pageable pageable);
}
