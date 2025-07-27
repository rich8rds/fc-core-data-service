/* Collections #2024 */
package com.favourite.collections.infrastructure.service;

import com.favourite.collections.commons.core.data.CommandResult;
import com.favourite.collections.commons.useradmin.data.PermissionRequest;
import com.favourite.collections.commons.useradmin.data.PermissionUpdate;
import org.springframework.http.ResponseEntity;

public interface PermissionService {
	ResponseEntity<?> getAllPermissions();

	ResponseEntity<CommandResult> createNewPermission(PermissionRequest permissionRequest);

	ResponseEntity<CommandResult> updatePermission(Long permissionId, PermissionUpdate permissionRequest);

	ResponseEntity<CommandResult> deletePermission(Long permissionId);
}
