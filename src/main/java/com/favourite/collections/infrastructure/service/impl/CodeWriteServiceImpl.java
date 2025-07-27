/* Collections #2024 */
package com.favourite.collections.infrastructure.service.impl;

import com.favourite.collections.commons.core.data.CommandResult;
import com.favourite.collections.commons.core.data.CommandResultBuilder;
import com.favourite.collections.commons.core.exceptions.AbstractPlatformException;
import com.favourite.collections.commons.useradmin.data.CodeData;
import com.favourite.collections.commons.useradmin.domain.Code;
import com.favourite.collections.commons.useradmin.domain.CodeValue;
import com.favourite.collections.infrastructure.repository.CodeRepository;
import com.favourite.collections.infrastructure.repository.CodeValueRepository;
import com.favourite.collections.infrastructure.service.CodeWriteService;
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
public class CodeWriteServiceImpl implements CodeWriteService {
	private final CodeRepository codeRepository;
	private final CodeValueRepository codeValueRepository;
	private final CodeModelMapper codeModelMapper = new CodeModelMapper();

	@Override
	public ResponseEntity<CommandResult> createCode(CodeData codeData) {
		String name = codeData.getName();

		if (codeRepository.existsByName(name)) {
			throw new AbstractPlatformException("error.msg.code.exists", "Code Data name already exists", 409);
		}
		Code code = codeRepository.saveAndFlush(codeModelMapper.fromCodeDataToCode(codeData));
		return ResponseEntity.ok().body(CommandResult.builder().entityId(code.getId()).message("Code created")
				.resourceId("Code with name " + name + " created").build());
	}

	@Override
	public ResponseEntity<CommandResult> updateCode(CodeData codeData, Long codeId) {
		Map<String, Object> changes = new HashMap<>();

		Code code = this.codeRepository.findById(codeId).orElseThrow(
				() -> new AbstractPlatformException("error.msg.code.does.not.exist", "Code does not exist", 409));

		String name = codeData.getName();
		if (name != null) {
			changes.put("name", name);
			codeData.setName(name);
		}
		Integer externalUse = code.getExternalUse();

		if (externalUse != null) {
			changes.put("externalUse", externalUse);
			codeData.setExternalUse(externalUse);
		}

		this.codeRepository.save(code);

		return ResponseEntity.ok().body(new CommandResultBuilder().response("Code with id: " + codeId + " updated.")
				.message("Code updated").changes(changes).build());
	}

	@Override
	public ResponseEntity<CommandResult> deleteCode(Long codeId) {
		Code code = this.codeRepository.findById(codeId).orElseThrow(
				() -> new AbstractPlatformException("error.msg.code.does.not.exist", "Code does not exist", 409));

		this.codeRepository.delete(code);

		return ResponseEntity.ok().body(new CommandResultBuilder().response("Code with id: " + codeId + " deleted.")
				.message("Code deleted").build());
	}

	@Override
	public ResponseEntity<CommandResult> assignCodeToCodeValue(Long codeId, Long codeValueId) {

		Code code = this.codeRepository.findById(codeId).orElseThrow(
				() -> new AbstractPlatformException("error.msg.code.does.not.exist", "Code does not exist", 409));

		CodeValue codeValue = this.codeValueRepository.findById(codeValueId)
				.orElseThrow(() -> new AbstractPlatformException("error.msg.code.value.does.not.exist",
						"CodeValue with id " + codeValueId + " does not exist", 409));

		code.getValues().add(codeValue);

		this.codeRepository.saveAndFlush(code);

		return ResponseEntity.ok()
				.body(new CommandResultBuilder()
						.response("CodeValue with id: " + codeValueId + " assigned to Code with id: " + codeId + ".")
						.message("Assigned successfully").build());
	}
}
