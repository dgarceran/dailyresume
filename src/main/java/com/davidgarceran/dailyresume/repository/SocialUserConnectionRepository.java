package com.davidgarceran.dailyresume.repository;

import com.davidgarceran.dailyresume.domain.SocialUserConnection;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Social User Connection entity.
 */
public interface SocialUserConnectionRepository extends JpaRepository<SocialUserConnection, Long> {

    List<SocialUserConnection> findAllByProviderIdAndProviderUserId(String providerId, String providerUserId);

    List<SocialUserConnection> findAllByProviderIdAndProviderUserIdIn(String providerId, Set<String> providerUserIds);

    List<SocialUserConnection> findAllByUserIdOrderByProviderIdAscRankAsc(String userId);

    List<SocialUserConnection> findAllByUserIdAndProviderIdOrderByRankAsc(String userId, String providerId);

    List<SocialUserConnection> findAllByUserIdAndProviderIdAndProviderUserIdIn(String userId, String providerId, List<String> provideUserId);

    SocialUserConnection findOneByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);

    void deleteByUserIdAndProviderId(String userId, String providerId);

    void deleteByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);

    // Added by David G - 21/04/2016
    List<SocialUserConnection> findAllByProviderId(String providerId);
    SocialUserConnection findOneByProviderIdAndUserId(String providerId, String userId);
    SocialUserConnection findOneByUserIdAndProviderId(String userId, String providerId);
}
