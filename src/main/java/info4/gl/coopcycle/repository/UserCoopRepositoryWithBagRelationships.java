package info4.gl.coopcycle.repository;

import info4.gl.coopcycle.domain.UserCoop;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface UserCoopRepositoryWithBagRelationships {
    Optional<UserCoop> fetchBagRelationships(Optional<UserCoop> userCoop);

    List<UserCoop> fetchBagRelationships(List<UserCoop> userCoops);

    Page<UserCoop> fetchBagRelationships(Page<UserCoop> userCoops);
}
