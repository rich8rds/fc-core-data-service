/* Collections #2024 */
package com.favourite.collections.infrastructure.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.favourite.collections.commons.core.data.CommandResult;
import com.favourite.collections.commons.core.data.CommandResultBuilder;
import com.favourite.collections.commons.core.exceptions.AbstractPlatformException;
import com.favourite.collections.commons.core.service.ResponseCodeEnum;
import com.favourite.collections.commons.useradmin.data.RolePermissionRequest;
import com.favourite.collections.commons.useradmin.data.RoleRequest;
import com.favourite.collections.commons.useradmin.data.RoleResponseData;
import com.favourite.collections.commons.useradmin.domain.Permission;
import com.favourite.collections.commons.useradmin.domain.Role;

import com.favourite.collections.infrastructure.repository.PermissionRepository;
import com.favourite.collections.infrastructure.repository.RoleRepository;
import com.favourite.collections.infrastructure.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;

	@Override
	public ResponseEntity<?> getAllRoles() {
		return ResponseEntity.status(200).body(roleRepository.findAll());
	}

	@Override
	public ResponseEntity<CommandResult> createNewRole(RoleRequest roleRequest) {
		String name = roleRequest.getName();
		String description = roleRequest.getDescription();
		boolean isDisabled = roleRequest.getIsDisabled();

		if (this.roleRepository.existsByName(name)) {
			throw new AbstractPlatformException("error.role.already.exists", ResponseCodeEnum.ROLE_ALREADY_EXISTS);
		}

		Role role = Role.builder().name(name).description(description).isDisabled(isDisabled).build();
		this.roleRepository.saveAndFlush(role);
		return ResponseEntity.status(201).body(new CommandResultBuilder().resourceId(String.valueOf(role.getId()))
				.message("Role with '" + name + "' created!").build());
	}

	@Override
	public ResponseEntity<CommandResult> tieRoleToPermission(RolePermissionRequest rolePermissionRequest) {
		if (rolePermissionRequest == null)
			return ResponseEntity.status(400).body(new CommandResultBuilder()
					.message("Enter valid inputs:roleName or roleId and " + "permissionName or permissionId required")
					.response("Request must not be null!").build());

		String roleName = rolePermissionRequest.getRoleName();
		String permissionName = rolePermissionRequest.getPermissionName();

		Long roleId = rolePermissionRequest.getRoleId();
		Long permissionId = rolePermissionRequest.getPermissionId();

		Role role = null;
		if (roleName != null) {
			role = roleRepository.findByName(roleName).orElse(null);
		}
		if (role == null && roleId != null) {
			role = roleRepository.findById(roleId).orElse(null);
		}

		if (role == null) {
			throw new IllegalArgumentException("Role with name " + roleName + " does not exist");
		}

		Permission permission = null;
		if (permissionName != null) {
			permission = permissionRepository.findByDisplayName(permissionName).orElse(null);
		}
		if (permission == null && permissionId != null) {
			permission = permissionRepository.findById(permissionId).orElse(null);
		}
		if (permission == null) {
			throw new IllegalArgumentException("Permission does not exist");
		}

		role.getPermissions().add(permission);
		this.roleRepository.save(role);

		return ResponseEntity
				.status(200).body(
						new CommandResultBuilder().resourceId(String.valueOf(roleId)).entityId(permission.getId())
								.message("Invalid role name").response("Role with name '" + role.getName()
										+ "' has permission with name '" + permission.getDisplayName() + "' added.")
								.build());
	}

	@Override
	public ResponseEntity<CommandResult> updateRole(Long roleId, RoleRequest roleRequest) {
		String name = roleRequest.getName();
		String description = roleRequest.getDescription();
		Boolean isDisabled = roleRequest.getIsDisabled();

		Role role = this.roleRepository.findById(roleId)
				.orElseThrow(() -> new AbstractPlatformException("Role with id: " + roleId + " not found",
						String.valueOf(roleId), 404));

		Map<String, Object> changes = new HashMap<>();
		if (name != null) {
			if (is(name, role.getName())) {
				changes.put("name", name);
			}
			role.setName(name);
		}

		if (description != null) {
			log.info("description: {}", is(description, role.getDescription()));
			if (is(description, role.getDescription())) {
				changes.put("description", description);
			}
			role.setDescription(description);
		}

		if (isDisabled != null) {
			if (isDisabled != role.getIsDisabled()) {
				changes.put("isDisabled", isDisabled);
			}
			role.setIsDisabled(isDisabled);
		}

		roleRepository.saveAndFlush(role);
		return ResponseEntity.status(200)
				.body(new CommandResultBuilder().message("Role with '" + name + "' created!").changes(changes).build());
	}

	private boolean is(String value, String comparison) {
		return !value.equals(comparison);
	}

	@Override
	public ResponseEntity<CommandResult> deleteRole(Long roleId) {
		Role role = this.roleRepository.findById(roleId)
				.orElseThrow(() -> new AbstractPlatformException("Role with id: " + roleId + " not found",
						String.valueOf(roleId), 404));
		String roleName = role.getName();
		this.roleRepository.delete(role);

		return ResponseEntity.status(200).body(new CommandResultBuilder()
				.message("Role with name " + roleName + " deleted!").entityId(roleId).build());
	}

	@Override
	public ResponseEntity<RoleResponseData> findOneRole(Long roleId, Boolean isDisabled) {
		Role role = this.roleRepository.findByIdAndIsDisabled(roleId, isDisabled).orElse(new Role());
		ObjectMapper mapper = new ObjectMapper();
		RoleResponseData roleResponseData = new RoleResponseData();
		BeanUtils.copyProperties(role, roleResponseData);
		return ResponseEntity.ok(roleResponseData);
	}

	@Override
	public ResponseEntity<RoleResponseData> findOneRoleByName(String roleName, Boolean isDisabled) {
		Role role = this.roleRepository.findByNameAndAndIsDisabled(roleName, isDisabled).orElse(new Role());
		RoleResponseData roleResponseData = new RoleResponseData();
		BeanUtils.copyProperties(role, roleResponseData);
		return ResponseEntity.ok(roleResponseData);
	}
}
