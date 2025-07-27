/* Collections #2024 */
package com.favourite.collections.infrastructure.repository;

import com.favourite.collections.commons.useradmin.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	boolean existsByName(String name);

	Optional<Role> findByName(String user);

	Optional<Role> findByNameAndAndIsDisabled(String name, boolean isDisabled);

	Optional<Role> findByIdAndIsDisabled(Long id, boolean isDisabled);
}
