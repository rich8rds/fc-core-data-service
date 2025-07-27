/* Collections #2024 */
package com.favourite.collections.infrastructure.service;

import com.favourite.collections.commons.core.data.CommandResult;
import com.favourite.collections.commons.useradmin.data.CodeData;
import org.springframework.http.ResponseEntity;

public interface CodeWriteService {
	ResponseEntity<CommandResult> createCode(CodeData codeData);

	ResponseEntity<CommandResult> updateCode(CodeData codeData, Long codeId);

	ResponseEntity<CommandResult> deleteCode(Long codeId);

	ResponseEntity<CommandResult> assignCodeToCodeValue(Long codeId, Long codeValueId);
}
