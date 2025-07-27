/* Collections #2024 */
package com.favourite.collections.infrastructure.service.impl;

import com.favourite.collections.commons.core.data.CommandResult;
import com.favourite.collections.commons.core.data.CommandResultBuilder;
import com.favourite.collections.commons.core.exceptions.AbstractPlatformException;
import com.favourite.collections.commons.useradmin.data.PermissionRequest;
import com.favourite.collections.commons.useradmin.data.PermissionUpdate;
import com.favourite.collections.commons.useradmin.domain.Permission;
import com.favourite.collections.infrastructure.repository.PermissionRepository;
import com.favourite.collections.infrastructure.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

	private final PermissionRepository permissionRepository;

	@Override
	public ResponseEntity<?> getAllPermissions() {
		return ResponseEntity.status(200).body(permissionRepository.findAll());
	}

	@Override
	public ResponseEntity<CommandResult> createNewPermission(PermissionRequest permissionRequest) {
		String grouping = permissionRequest.getGrouping();
		String actionName = permissionRequest.getActionName();
		String entityName = permissionRequest.getEntityName();
		String description = permissionRequest.getDescription();
		boolean isDisabled = permissionRequest.getIsDisabled();
		String displayName = actionName + "_" + entityName;

		if (permissionRepository.existsByDisplayName(displayName))
			throw new AbstractPlatformException("error.permission.already.exists", "Permission already exists");

		Permission permission = Permission.builder().grouping(grouping).actionName(actionName).entityName(entityName)
				.displayName(displayName).description(description).isDisabled(isDisabled).build();
		permissionRepository.saveAndFlush(permission);
		return ResponseEntity.status(201).body(new CommandResultBuilder().entityId(permission.getId())
				.message("Permission created").response("Permission with name '" + actionName + "' created!").build());
	}

	@Override
	public ResponseEntity<CommandResult> updatePermission(Long permissionId, PermissionUpdate permissionUpdate) {
		String grouping = permissionUpdate.getGrouping();
		String actionName = permissionUpdate.getActionName();
		String entityName = permissionUpdate.getEntityName();
		String description = permissionUpdate.getDescription();
		Boolean isDisabled = permissionUpdate.getIsDisabled();

		Permission permission = this.permissionRepository.findById(permissionId)
				.orElseThrow(() -> new AbstractPlatformException("Permission with id: " + permissionId + " not found",
						String.valueOf(permissionId), 404));

		Map<String, Object> changes = new HashMap<>();

		if (grouping != null) {
			if (!is(grouping, permission.getGrouping())) {
				changes.put("grouping", grouping);
			}
			permission.setGrouping(grouping);
		}

		if (actionName != null) {
			if (!is(actionName, permission.getActionName())) {
				changes.put("actionName", actionName);
			}
			permission.setActionName(actionName);
		}

		if (entityName != null) {
			String displayName = actionName + "_" + entityName;
			if (permissionRepository.existsByDisplayName(displayName)) {
				throw new AbstractPlatformException("Permission already exists!", "Create a unique entityName!");
			}
			if (!is(entityName, permission.getEntityName())) {
				changes.put("entityName", entityName);
			}
			permission.setEntityName(entityName);
		}

		if (description != null) {
			log.info("isEqual: {}", is(description, permission.getDescription()));
			if (!is(description, permission.getDescription())) {
				changes.put("description", description);
			}
			permission.setDescription(description);
		}

		if (isDisabled != null) {
			if (isDisabled != permission.getIsDisabled()) {
				changes.put("isDisabled", isDisabled);
			}
			permission.setIsDisabled(isDisabled);
		}

		this.permissionRepository.saveAndFlush(permission);
		return ResponseEntity.status(200).body(new CommandResultBuilder().entityId(permissionId)
				.message("Permission with '" + grouping + "' updated!").changes(changes).build());
	}

	private boolean is(String value, String comparison) {
		return value.equals(comparison);
	}

	@Override
	public ResponseEntity<CommandResult> deletePermission(Long permissionId) {
		Permission permission = this.permissionRepository.findById(permissionId)
				.orElseThrow(() -> new AbstractPlatformException("Permission with id: " + permissionId + " not found",
						String.valueOf(permissionId), 404));
		String permissionName = permission.getDisplayName();
		this.permissionRepository.delete(permission);

		return ResponseEntity.status(200)
				.body(new CommandResultBuilder().message("Permission with display name " + permissionName + " deleted!")
						.entityId(permissionId).build());
	}
}
