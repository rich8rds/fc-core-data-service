/* Collections #2024 */
package com.favourite.collections.infrastructure.service.impl;

import com.favourite.collections.commons.core.data.CommandResult;
import com.favourite.collections.commons.core.data.CommandResultBuilder;
import com.favourite.collections.commons.core.exceptions.AbstractPlatformException;
import com.favourite.collections.commons.useradmin.data.CodeValueData;
import com.favourite.collections.commons.useradmin.domain.CodeValue;
import com.favourite.collections.infrastructure.repository.CodeValueRepository;
import com.favourite.collections.infrastructure.service.CodeValueWriteService;
import com.favourite.collections.util.CodeModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeValueWriteServiceImpl implements CodeValueWriteService {
	private final CodeValueRepository codeValueRepository;
	private CodeModelMapper codeModelMapper = new CodeModelMapper();

	@Override
	public ResponseEntity<CommandResult> createCodeValue(CodeValueData codeValueData) {
		String name = codeValueData.getLabel();

		if (codeValueRepository.existsByLabel(name)) {
			throw new AbstractPlatformException("error.msg.code.exists", "Code Data name already exists", 409);
		}
		CodeValue codeValue = this.codeValueRepository
				.saveAndFlush(codeModelMapper.fromCodeValueDataToCodeValue(codeValueData));
		return ResponseEntity.ok().body(CommandResult.builder().entityId(codeValue.getId()).message("Code created")
				.resourceId("Code with name " + name + " created").build());
	}

	@Override
	public ResponseEntity<CommandResult> updateCodeValue(CodeValueData codeValueData, Long codeValueId) {
		Map<String, Object> changes = new HashMap<>();

		CodeValue code = codeValueRepository.findById(codeValueId)
				.orElseThrow(() -> new AbstractPlatformException("error.msg.code.value.does.not.exist",
						"CodeValue with id " + codeValueId + " does not exist", 409));

		String label = codeValueData.getLabel();
		Integer position = codeValueData.getPosition();
		String description = codeValueData.getDescription();
		Boolean isActive = codeValueData.getIsActive();
		Boolean mandatory = codeValueData.getMandatory();

		if (label != null) {
			changes.put("label", label);
			code.setLabel(label);
		}

		if (position != null) {
			changes.put("position", position);
			code.setPosition(position);
		}

		if (description != null) {
			changes.put("description", description);
			code.setDescription(description);
		}

		if (isActive != null) {
			changes.put("isActive", isActive);
			code.setActive(isActive);
		}

		if (mandatory != null) {
			changes.put("mandatory", label);
			code.setMandatory(mandatory);
		}

		return ResponseEntity.ok()
				.body(new CommandResultBuilder().response("Code with id: " + codeValueId + " updated.")
						.message("Code updated").changes(changes).build());
	}

	@Override
	public ResponseEntity<CommandResult> deleteCodeValue(Long codeValueId) {
		CodeValue code = codeValueRepository.findById(codeValueId).orElseThrow(
				() -> new AbstractPlatformException("error.msg.code.does.not.exist", "Code does not exist", 409));

		this.codeValueRepository.delete(code);

		return ResponseEntity.ok().body(new CommandResultBuilder()
				.response("Code with id: " + codeValueId + " deleted.").message("Code deleted").build());
	}
}
