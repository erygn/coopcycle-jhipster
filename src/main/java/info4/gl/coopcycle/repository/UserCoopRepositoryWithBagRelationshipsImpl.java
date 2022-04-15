package info4.gl.coopcycle.repository;

import info4.gl.coopcycle.domain.UserCoop;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class UserCoopRepositoryWithBagRelationshipsImpl implements UserCoopRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserCoop> fetchBagRelationships(Optional<UserCoop> userCoop) {
        return userCoop.map(this::fetchCooperatives);
    }

    @Override
    public Page<UserCoop> fetchBagRelationships(Page<UserCoop> userCoops) {
        return new PageImpl<>(fetchBagRelationships(userCoops.getContent()), userCoops.getPageable(), userCoops.getTotalElements());
    }

    @Override
    public List<UserCoop> fetchBagRelationships(List<UserCoop> userCoops) {
        return Optional.of(userCoops).map(this::fetchCooperatives).orElse(Collections.emptyList());
    }

    UserCoop fetchCooperatives(UserCoop result) {
        return entityManager
            .createQuery(
                "select userCoop from UserCoop userCoop left join fetch userCoop.cooperatives where userCoop is :userCoop",
                UserCoop.class
            )
            .setParameter("userCoop", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<UserCoop> fetchCooperatives(List<UserCoop> userCoops) {
        return entityManager
            .createQuery(
                "select distinct userCoop from UserCoop userCoop left join fetch userCoop.cooperatives where userCoop in :userCoops",
                UserCoop.class
            )
            .setParameter("userCoops", userCoops)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
