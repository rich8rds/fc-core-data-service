/* Collections #2024 */
package com.favourite.collections.infrastructure.service;

import com.favourite.collections.commons.core.data.CommandResult;
import com.favourite.collections.commons.useradmin.data.RolePermissionRequest;
import com.favourite.collections.commons.useradmin.data.RoleRequest;
import com.favourite.collections.commons.useradmin.data.RoleResponseData;
import org.springframework.http.ResponseEntity;


public interface RoleService {
	ResponseEntity<?> getAllRoles();

	ResponseEntity<CommandResult> createNewRole(RoleRequest roleRequest);

	ResponseEntity<CommandResult> tieRoleToPermission(RolePermissionRequest rolePermissionRequest);

	ResponseEntity<CommandResult> updateRole(Long roleId, RoleRequest roleRequest);

	ResponseEntity<CommandResult> deleteRole(Long roleId);

    ResponseEntity<RoleResponseData> findOneRole(Long roleId, Boolean isDisabled);

	ResponseEntity<RoleResponseData> findOneRoleByName(String roleName, Boolean isDisabled);
}
