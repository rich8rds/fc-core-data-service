/* Collections #2024 */
package com.favourite.collections.infrastructure.repository;

import com.favourite.collections.commons.useradmin.domain.CodeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeValueRepository extends JpaRepository<CodeValue, Long> {
	boolean existsByLabel(String name);

	Optional<CodeValue> findCodeValueByLabel(String name);
}
