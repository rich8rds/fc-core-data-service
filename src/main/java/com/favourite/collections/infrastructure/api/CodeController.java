/* Collections #2024 */
package com.favourite.collections.infrastructure.api;

import com.favourite.collections.commons.core.data.CommandResult;
import com.favourite.collections.commons.core.data.SearchParameters;
import com.favourite.collections.commons.useradmin.data.CodeData;
import com.favourite.collections.infrastructure.service.CodeReadService;
import com.favourite.collections.infrastructure.service.CodeWriteService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Code")
@RestController
@RequestMapping("/codes")
@RequiredArgsConstructor
public class CodeController {
	private final CodeWriteService codeWriteService;
	private final CodeReadService codeReadService;

	@PostMapping
	public ResponseEntity<CommandResult> createCode(@RequestBody CodeData codeData) {
		return codeWriteService.createCode(codeData);
	}

	@GetMapping
	public Page<CodeData> retrieveAllCodes(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String name, @RequestParam(defaultValue = "1") Integer offset,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "DESC") String sortOrder,
			@RequestParam(defaultValue = "id") String orderBy) {

		SearchParameters searchParameters = SearchParameters.builder().id(id).offset(offset).limit(limit).name(name)
				.sortOrder(sortOrder).orderBy(orderBy).build();
		return codeReadService.retrieveAllCodes(searchParameters);
	}

	@GetMapping("/{codeId}")
	public ResponseEntity<CodeData> retrieveOneCode(@PathVariable Long codeId) {
		return codeReadService.retrieveOneCode(codeId);
	}

	@PutMapping("/codeId")
	public ResponseEntity<CommandResult> updateCode(@RequestBody CodeData codeData, @PathVariable Long codeId) {
		return codeWriteService.updateCode(codeData, codeId);
	}

	@DeleteMapping("/{codeId}")
	public ResponseEntity<CommandResult> deleteCodeValue(@PathVariable Long codeId) {
		return codeWriteService.deleteCode(codeId);
	}

	@PostMapping("/{codeId}/code-values/{codeValueId}")
	public ResponseEntity<CommandResult> assignCodeToCodeValue(@PathVariable Long codeId,
			@PathVariable Long codeValueId) {
		return codeWriteService.assignCodeToCodeValue(codeId, codeValueId);
	}
}
