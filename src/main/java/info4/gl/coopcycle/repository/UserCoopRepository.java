package info4.gl.coopcycle.repository;

import info4.gl.coopcycle.domain.UserCoop;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserCoop entity.
 */
@Repository
public interface UserCoopRepository extends UserCoopRepositoryWithBagRelationships, JpaRepository<UserCoop, Long> {
    default Optional<UserCoop> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<UserCoop> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<UserCoop> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
