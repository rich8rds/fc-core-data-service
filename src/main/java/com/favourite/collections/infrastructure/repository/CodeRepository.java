/* Collections #2024 */
package com.favourite.collections.infrastructure.repository;

import com.favourite.collections.commons.useradmin.domain.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
	boolean existsByName(String name);
}
