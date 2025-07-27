/* Collections #2024 */
package com.favourite.collections.infrastructure.api;

import com.favourite.collections.commons.core.data.CommandResult;
import com.favourite.collections.commons.core.data.SearchParameters;
import com.favourite.collections.commons.useradmin.data.CodeValueData;
import com.favourite.collections.infrastructure.service.CodeValueReadService;
import com.favourite.collections.infrastructure.service.CodeValueWriteService;
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

@Tag(name = "CodeValues")
@RestController
@RequestMapping("/code-values")
@RequiredArgsConstructor
public class CodeValueController {

	private final CodeValueWriteService codeWriteService;
	private final CodeValueReadService codeReadService;

	@PostMapping
	public ResponseEntity<CommandResult> createCode(@RequestBody CodeValueData codeData) {
		return codeWriteService.createCodeValue(codeData);
	}

	@GetMapping
	public Page<CodeValueData> getRoles(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String name, @RequestParam(defaultValue = "1") Integer offset,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "DESC") String sortOrder,
			@RequestParam(defaultValue = "id") String orderBy) {

		SearchParameters searchParameters = SearchParameters.builder().id(id).offset(offset).limit(limit).name(name)
				.sortOrder(sortOrder).orderBy(orderBy).build();
		return codeReadService.retrieveAllCodeValues(searchParameters);
	}

	@GetMapping("/{codeValueId}")
	public ResponseEntity<CodeValueData> retrieveOneCode(@PathVariable Long codeValueId) {
		return codeReadService.retrieveOneCodeValue(codeValueId);
	}

	@PutMapping("/codeValueId")
	public ResponseEntity<CommandResult> updateCode(@RequestBody CodeValueData codeData,
			@PathVariable Long codeValueId) {
		return codeWriteService.updateCodeValue(codeData, codeValueId);
	}

	@DeleteMapping("/codeValueId")
	public ResponseEntity<CommandResult> deleteCodeValue(@PathVariable Long codeValueId) {
		return codeWriteService.deleteCodeValue(codeValueId);
	}
}
