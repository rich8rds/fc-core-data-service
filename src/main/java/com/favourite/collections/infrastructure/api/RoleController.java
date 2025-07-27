/* Collections #2024 */
package com.favourite.collections.infrastructure.api;

import com.favourite.collections.commons.core.data.CommandResult;
import com.favourite.collections.commons.useradmin.data.RolePermissionRequest;
import com.favourite.collections.commons.useradmin.data.RoleRequest;
import com.favourite.collections.commons.useradmin.data.RoleResponseData;
import com.favourite.collections.infrastructure.service.RoleService;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Roles")
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
	private final RoleService roleService;

	@GetMapping
	public ResponseEntity<?> getAllRoles() {
		return this.roleService.getAllRoles();
	}

	@GetMapping("/{roleId}")
	public ResponseEntity<RoleResponseData> findOneRole(@PathVariable(name = "roleId") Long roleId, @RequestParam(name = "isDisabled", defaultValue = "false") Boolean isDisabled) {
		return this.roleService.findOneRole(roleId, isDisabled);
	}

	@GetMapping("/name/{roleName}")
	public ResponseEntity<RoleResponseData> findOneRoleByName(@PathVariable(name = "roleName") String roleName, @RequestParam(name = "isDisabled", defaultValue = "false") Boolean isDisabled) {
		log.info("Called this endpoint from another ....");
		return this.roleService.findOneRoleByName(roleName, isDisabled);
	}

	@PostMapping
	public ResponseEntity<CommandResult> createRole(@RequestBody @Valid RoleRequest roleRequest) {
		return this.roleService.createNewRole(roleRequest);
	}

	@PutMapping("/{roleId}")
	public ResponseEntity<CommandResult> updateRole(@PathVariable Long roleId,
			@RequestBody @Valid RoleRequest roleRequest) {
		return this.roleService.updateRole(roleId, roleRequest);
	}

	@PutMapping("/{roleId}/assign")
	@Operation(summary = "You can assign a permission to a role")
	public ResponseEntity<CommandResult> tieRoleToPermission(@RequestBody RolePermissionRequest rolePermissionRequest) {
		return this.roleService.tieRoleToPermission(rolePermissionRequest);
	}

	@DeleteMapping("/{roleId}")
	public ResponseEntity<CommandResult> deleteRole(@PathVariable Long roleId) {
		return this.roleService.deleteRole(roleId);
	}
}
