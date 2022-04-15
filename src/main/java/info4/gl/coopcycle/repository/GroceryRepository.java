package info4.gl.coopcycle.repository;

import info4.gl.coopcycle.domain.Grocery;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Grocery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroceryRepository extends JpaRepository<Grocery, String> {}
