/* Collections #2024 */
package com.favourite.collections.infrastructure.api;

import com.favourite.collections.commons.core.data.CommandResult;
import com.favourite.collections.commons.useradmin.data.PermissionRequest;
import com.favourite.collections.commons.useradmin.data.PermissionUpdate;
import com.favourite.collections.infrastructure.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Permissions")
@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {
	private final PermissionService permissionService;

	@GetMapping
	public ResponseEntity<?> getAllPermissions() {
		return permissionService.getAllPermissions();
	}

	@PostMapping
	public ResponseEntity<CommandResult> createPermissions(@RequestBody PermissionRequest permissionRequest) {
		return permissionService.createNewPermission(permissionRequest);
	}

	@PutMapping("/{permissionId}")
	public ResponseEntity<CommandResult> updatePermission(@PathVariable Long permissionId,
			@RequestBody PermissionUpdate permissionUpdate) {
		return permissionService.updatePermission(permissionId, permissionUpdate);
	}

	@DeleteMapping("/{permissionId}")
	public ResponseEntity<CommandResult> deletePermission(@PathVariable Long permissionId) {
		return permissionService.deletePermission(permissionId);
	}
}
